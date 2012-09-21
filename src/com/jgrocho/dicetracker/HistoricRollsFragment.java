package com.jgrocho.dicetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class HistoricRollsFragment extends SherlockFragment {

    private Rolls mRolls;
    private BarChartView mBarChartView;

    public HistoricRollsFragment() {
        this(new Rolls());
    }

    public HistoricRollsFragment(Rolls rolls) {
        mRolls = rolls;
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
        mRolls.registerListener(mBarChartView);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRolls.unregisterListener(mBarChartView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.historic_rolls, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_historic_clear:
                showClearDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showClearDialog() {
        SherlockDialogFragment newFragment = ClearDialogFragment.newInstance(ClearDialogFragment.HISTORIC);
        newFragment.show(getFragmentManager(), "dialog");
    }
}
