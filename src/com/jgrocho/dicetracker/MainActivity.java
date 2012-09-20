package com.jgrocho.dicetracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private int[] mRolls;
    private int[] mHistoricRolls;

    private CurrentRollsFragment mCurrentRollsFragment;
    private HistoricRollsFragment mHistoricRollsFragment;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int tabIdx = 0;
        if (savedInstanceState != null) {
            mRolls = savedInstanceState.getIntArray("rolls");
            mHistoricRolls = savedInstanceState.getIntArray("historic");
            tabIdx = savedInstanceState.getInt("tab");
        } else {
            mRolls = new int[11];
            mHistoricRolls = new int[11];
        }

        if (mCurrentRollsFragment == null)
            mCurrentRollsFragment = new CurrentRollsFragment(mRolls);
        if (mHistoricRollsFragment == null)
            mHistoricRollsFragment = new HistoricRollsFragment();

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tab = actionBar
                .newTab()
                .setText(R.string.current_rolls_title)
                .setTabListener(mCurrentRollsFragment);
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.historic_rolls_title)
                .setTabListener(mHistoricRollsFragment);
        actionBar.addTab(tab);

        actionBar.setSelectedNavigationItem(tabIdx);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putIntArray("rolls", mRolls);
        savedInstanceState.putIntArray("historic", mHistoricRolls);
        savedInstanceState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
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

    protected void resetRolls() {
        for (int i = 0; i < 11; i++)
            mRolls[i] = 0;
        mCurrentRollsFragment.setRolls(mRolls);
    }

    protected void saveRolls() {
        for (int i = 0; i < 11; i++)
            mHistoricRolls[i] += mRolls[i];
        resetRolls();
        mHistoricRollsFragment.setRolls(mHistoricRolls);
    }

    private void showClearDialog() {
        DialogFragment newFragment = ClearDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    private void showSaveDialog() {
        DialogFragment newFragment = SaveDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }
}
