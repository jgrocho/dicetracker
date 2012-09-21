package com.jgrocho.dicetracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

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
            mRolls = savedInstanceState.getIntArray("current");
            mHistoricRolls = savedInstanceState.getIntArray("historic");
            tabIdx = savedInstanceState.getInt("tab");
        } else {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            mRolls = new int[11];
            mHistoricRolls = new int[11];
            for (int i = 0; i < 11; i++) {
                mRolls[i] = prefs.getInt("current" + String.valueOf(i), 0);
                mHistoricRolls[i] = prefs.getInt("historic" + String.valueOf(i), 0);
            }
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
        savedInstanceState.putIntArray("current", mRolls);
        savedInstanceState.putIntArray("historic", mHistoricRolls);
        savedInstanceState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Editor prefEditor = prefs.edit();
        for (int i = 0; i < 11; i++) {
            prefEditor.putInt("current" + String.valueOf(i), mRolls[i]);
            prefEditor.putInt("historic" + String.valueOf(i), mHistoricRolls[i]);
        }
        prefEditor.apply();
    }

    protected void resetCurrentRolls() {
        for (int i = 0; i < 11; i++)
            mRolls[i] = 0;
        mCurrentRollsFragment.setRolls(mRolls);
        saveData();
    }

    protected void resetHistoricRolls() {
        for (int i = 0; i < 11; i++)
            mHistoricRolls[i] = 0;
        mHistoricRollsFragment.setRolls(mHistoricRolls);
        saveData();
    }

    protected void saveRolls() {
        for (int i = 0; i < 11; i++)
            mHistoricRolls[i] += mRolls[i];
        resetCurrentRolls();
        mHistoricRollsFragment.setRolls(mHistoricRolls);
        saveData();
    }
}
