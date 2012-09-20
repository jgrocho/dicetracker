package com.jgrocho.dicetracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jgrocho.layout.FlowLayout;

public class CurrentRollsFragment extends Fragment implements
        ActionBar.TabListener {

    private Button[] mNumeralButtons;
    private BarChartView mBarChartView;
    private int[] mRolls;
    private boolean mFresh;

    public CurrentRollsFragment() {
        this(new int[11]);
    }

    public CurrentRollsFragment(int[] rolls) {
        mRolls = rolls;
        mFresh = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        final Resources r = getResources();
        final Activity activity = getActivity();

        mNumeralButtons = new Button[11];
        String[] numerals = r.getStringArray(R.array.numerals);
        final float scale = r.getDisplayMetrics().density;
        int minSize = (int) (48 * scale + 0.5f);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 11; i++) {
            Button button = new Button(activity);
            button.setLayoutParams(lp);
            button.setText(numerals[i]);
            button.setMinHeight(minSize);
            button.setMinimumHeight(minSize);
            button.setMinWidth(minSize);
            button.setMinimumWidth(minSize);
            button.setTag(i);
            mNumeralButtons[i] = button;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(
                R.layout.current_rolls_fragment, container, false);

        mBarChartView = (BarChartView) view.getChildAt(1);
        final FlowLayout buttonLayout = (FlowLayout) view
                .findViewById(R.id.buttonFlow);

        mBarChartView.setRolls(mRolls);
        for (int i = 0; i < 11; i++) {
            mNumeralButtons[i].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mBarChartView.increaseAt(((Integer) v.getTag()).intValue());
                }
            });
            buttonLayout.addView(mNumeralButtons[i]);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.current_rolls, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                showClearDialog();
                break;
            case R.id.menu_save:
                showSaveDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showClearDialog() {
        DialogFragment newFragment = ClearDialogFragment.newInstance(ClearDialogFragment.CURRENT);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private void showSaveDialog() {
        DialogFragment newFragment = SaveDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) getView().findViewById(R.id.buttonFlow)).removeAllViews();
    }

    public void setRolls(int[] rolls) {
        mBarChartView.setRolls(rolls);
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        if (mFresh) {
            mFresh = false;
            ft.replace(android.R.id.content, this, "current");
        } else {
            ft.attach(this);
        }
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (!mFresh)
            ft.detach(this);
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
}
