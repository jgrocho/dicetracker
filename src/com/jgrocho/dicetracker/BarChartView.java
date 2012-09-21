package com.jgrocho.dicetracker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class BarChartView extends View implements Rolls.OnChangeListener {
    private Rolls mRolls;

    private Paint mBarPaint;
    private Paint mBarBorderPaint;
    private TextPaint mBarTextPaint;
    private TextPaint mLabelPaint;

    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mContentWidth;
    private int mContentHeight;
    private int mBarBottom;
    private int mChartWidth;
    private int mChartHeight;
    private float mBarWidth;

    public BarChartView(Context context) {
        super(context);
        init(null, 0);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context);
        init(attrs, 0);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BarChartView, defStyle, 0);
        final Resources r = getResources();

        int barColor = a.getColor(R.styleable.BarChartView_barColor,
                r.getColor(R.color.barColor));
        int barBorderColor = a.getColor(
                R.styleable.BarChartView_barBorderColor,
                r.getColor(R.color.barBorderColor));
        int barTextColor = a.getColor(R.styleable.BarChartView_barTextColor,
                r.getColor(R.color.barTextColor));
        float barTextSize = a.getDimension(
                R.styleable.BarChartView_barTextSize,
                r.getDimension(R.dimen.barTextSize));
        int labelColor = a.getColor(R.styleable.BarChartView_labelColor,
                r.getColor(R.color.labelColor));
        float labelSize = a.getDimension(R.styleable.BarChartView_labelSize,
                r.getDimension(R.dimen.labelSize));
        a.recycle();

        mBarPaint = createPaint(barColor);
        mBarBorderPaint = createPaint(barBorderColor);
        mBarTextPaint = createTextPaint(barTextColor, barTextSize);
        mLabelPaint = createTextPaint(labelColor, labelSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();

        mContentWidth = getWidth() - mPaddingLeft - mPaddingRight;
        mContentHeight = getHeight() - mPaddingTop - mPaddingBottom;

        mBarBottom = 50;
        mChartWidth = mContentWidth;
        mChartHeight = mContentHeight - mBarBottom;
        mBarWidth = mChartWidth / 11.0f;

        canvas.drawLine(0, mChartHeight, mChartWidth, mChartHeight,
                mBarBorderPaint);
        // Draw labels
        for (int i = 0; i < 11; i++) {
            float left = i * mBarWidth;
            canvas.drawText(String.valueOf(i + 2), left + (mBarWidth / 2.0f),
                    mContentHeight - 15, mLabelPaint);
        }

        // Draw bars
        int maxCount = max(mRolls.getRolls());
        if (maxCount > 0) {
            float segmentHeight = mChartHeight / maxCount;
            for (int i = 0; i < 11; i++) {
                if (mRolls.getAt(i) > 0) {
                    float left = i * mBarWidth;
                    float right = left + mBarWidth;
                    float top = mChartHeight - mRolls.getAt(i) * segmentHeight;
                    float bottom = mChartHeight;
                    canvas.drawRect(left + 1, top + 1, right - 1, bottom, mBarBorderPaint);
                    canvas.drawRect(left + 2, top + 2, right - 2, bottom, mBarPaint);
                    canvas.drawText(String.valueOf(mRolls.getAt(i)), left + (mBarWidth / 2), top + mBarTextPaint.getFontMetrics().bottom * 3 + 5, mBarTextPaint);
                }
            }
        }
    }

    public void setRolls(Rolls rolls) {
        mRolls = rolls;
        invalidate();
    }

    private static int max(int[] xs) {
        int x = xs[0];
        for (int i = 1; i < xs.length; i++)
            x = Math.max(x, xs[i]);
        return x;
    }

    private static Paint createPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        return paint;
    }

    private static TextPaint createTextPaint(int color, float size) {
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(color);
        paint.setTextSize(size);
        return paint;
    }

    public void onSet() {
        invalidate();
    }

    public void onChange(int idx) {
        invalidate();
    }
}
