package com.jgrocho.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jgrocho.dicetracker.R;

public class FlowLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int horizontalSpacing = 0;
    private int verticalSpacing = 0;
    private int orientation = 0;

    public FlowLayout(Context context) {
        super(context);
        readStyleParameters(context, null);
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        readStyleParameters(context, attributeSet);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        readStyleParameters(context, attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
                - getPaddingRight() - getPaddingLeft();
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
                - getPaddingBottom() - getPaddingTop();
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int size = sizeWidth;
        int mode = modeWidth;
        if (orientation == VERTICAL) {
            size = sizeHeight;
            mode = modeHeight;
        }

        int lineThicknessWithSpacing = 0;
        int lineThickness = 0;
        int lineLengthWithSpacing = 0;
        int lineLength;
        int prevLinePosition = 0;
        int controlMaxThickness = 0;
        int controlMaxLength = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;

            child.measure(
                    MeasureSpec.makeMeasureSpec(
                        sizeWidth,
                        modeWidth == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeWidth),
                    MeasureSpec.makeMeasureSpec(
                        sizeHeight,
                        modeHeight == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeHeight)
                    );

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int hSpacing = getHorizontalSpacing(lp);
            int vSpacing = getVerticalSpacing(lp);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int childLength = childWidth;
            int childThickness = childHeight;
            int spacingLength = hSpacing;
            int spacingThickness = vSpacing;
            if (orientation == VERTICAL) {
                childLength = childHeight;
                childThickness = childWidth;
                spacingLength = vSpacing;
                spacingThickness = hSpacing;
            }

            lineLength = lineLengthWithSpacing + childLength;
            lineLengthWithSpacing = lineLength + spacingLength;

            if (lp.newLine || (mode != MeasureSpec.UNSPECIFIED && lineLength > size)) {
                prevLinePosition = prevLinePosition + lineThicknessWithSpacing;
                lineThickness = childThickness;
                lineLength = childLength;
                lineThicknessWithSpacing = childThickness + spacingThickness;
                lineLengthWithSpacing = lineLength + spacingLength;
            }

            lineThicknessWithSpacing = Math.max(lineThicknessWithSpacing,
                    childThickness + spacingThickness);
            lineThickness = Math.max(lineThickness, childThickness);

            int posX = getPaddingLeft() + lineLength - childLength;
            int posY = getPaddingTop() + prevLinePosition;
            if (orientation == VERTICAL) {
                posX = getPaddingLeft() + prevLinePosition;
                posY = getPaddingTop() + lineLength - childHeight;
            }
            lp.setPosition(posX, posY);

            controlMaxLength = Math.max(controlMaxLength, lineLength);
            controlMaxThickness = prevLinePosition + lineThickness;

            if (orientation == HORIZONTAL)
                setMeasuredDimension(
                        resolveSize(controlMaxLength, widthMeasureSpec),
                        resolveSize(controlMaxThickness, heightMeasureSpec));
            else
                setMeasuredDimension(
                        resolveSize(controlMaxThickness, widthMeasureSpec),
                        resolveSize(controlMaxLength, heightMeasureSpec));
        }
    }

    private int getVerticalSpacing(LayoutParams lp) {
        if (lp.verticalSpacingSpecified())
            return lp.verticalSpacing;
        else
            return verticalSpacing;
    }

    private int getHorizontalSpacing(LayoutParams lp) {
        if (lp.horizontalSpacingSpecified())
            return lp.horizontalSpacing;
        else
            return horizontalSpacing;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    private void readStyleParameters(Context context, AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout);
        try {
            horizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_horizontalSpacing, 0);
            verticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_verticalSpacing, 0);
            orientation = a.getInteger(R.styleable.FlowLayout_orientation, HORIZONTAL);
        } finally {
            a.recycle();
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        private static int NO_SPACING = -1;

        private int x;
        private int y;
        private int horizontalSpacing = NO_SPACING;
        private int verticalSpacing = NO_SPACING;
        private boolean newLine = false;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            readStyleParameters(context, attributeSet);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public boolean horizontalSpacingSpecified() {
            return horizontalSpacing != NO_SPACING;
        }

        public boolean verticalSpacingSpecified() {
            return verticalSpacing != NO_SPACING;
        }

        public void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private void readStyleParameters(Context context, AttributeSet attributeSet) {
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout_LayoutParams);
            try {
                horizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_LayoutParams_layout_horizontalSpacing, 0);
                verticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_LayoutParams_layout_verticalSpacing, 0);
                newLine = a.getBoolean(R.styleable.FlowLayout_LayoutParams_layout_newLine, false);
            } finally {
                a.recycle();
            }
        }
    }
}
