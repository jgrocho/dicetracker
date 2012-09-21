package com.jgrocho.dicetracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

public class HistoricRollsFragment extends Fragment implements
        ActionBar.TabListener {

    private int[] mRolls;
    private boolean mFresh;
    private BarChartView mBarChartView;

    public HistoricRollsFragment() {
        this(new int[11]);
    }

    public HistoricRollsFragment(int[] rolls) {
        mRolls = rolls;
        mFresh = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(
                R.layout.historic_rolls_fragment, container, false);

        mBarChartView = (BarChartView) view.getChildAt(0);
        mBarChartView.setRolls(mRolls);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.historic_rolls, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                showClearDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showClearDialog() {
        DialogFragment newFragment = ClearDialogFragment.newInstance(ClearDialogFragment.HISTORIC);
        newFragment.show(getFragmentManager(), "dialog");
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
