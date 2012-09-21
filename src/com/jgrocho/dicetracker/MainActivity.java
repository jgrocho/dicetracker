package com.jgrocho.dicetracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class MainActivity extends Activity {

    private Rolls mCurrentRolls;
    private Rolls mHistoricRolls;
    private CurrentRollsFragment mCurrentRollsFragment;
    private HistoricRollsFragment mHistoricRollsFragment;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int tabIdx = 0;
        mCurrentRolls = new Rolls();
        mHistoricRolls = new Rolls();
        if (savedInstanceState != null) {
            mCurrentRolls.setData(savedInstanceState.getIntArray("current"));
            mHistoricRolls.setData(savedInstanceState.getIntArray("historic"));
            tabIdx = savedInstanceState.getInt("tab");
        } else {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            for (int i = 0; i < 11; i++) {
                mCurrentRolls.setAt(i, prefs.getInt("current" + String.valueOf(i), 0));
                mHistoricRolls.setAt(i, prefs.getInt("historic" + String.valueOf(i), 0));
            }
        }

        mCurrentRollsFragment = new CurrentRollsFragment(mCurrentRolls);
        mHistoricRollsFragment = new HistoricRollsFragment(mHistoricRolls);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tab = actionBar.newTab().setText(R.string.current_rolls_title)
                .setTabListener(new TabListener(mCurrentRollsFragment, "current"));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText(R.string.historic_rolls_title)
                .setTabListener(new TabListener(mHistoricRollsFragment, "historic"));
        actionBar.addTab(tab);

        actionBar.setSelectedNavigationItem(tabIdx);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putIntArray("current", mCurrentRolls.getRolls());
        savedInstanceState.putIntArray("historic", mHistoricRolls.getRolls());
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
            prefEditor.putInt("current" + String.valueOf(i), mCurrentRolls.getAt(i));
            prefEditor.putInt("historic" + String.valueOf(i), mHistoricRolls.getAt(i));
        }
        prefEditor.apply();
    }

    protected void resetCurrentRolls() {
        for (int i = 0; i < 11; i++)
            mCurrentRolls.setAt(i, 0);
        saveData();
    }

    protected void resetHistoricRolls() {
        for (int i = 0; i < 11; i++)
            mHistoricRolls.setAt(i, 0);
        saveData();
    }

    protected void saveRolls() {
        for (int i = 0; i < 11; i++)
            mHistoricRolls.increateAtBy(i, mCurrentRolls.getAt(i));
        resetCurrentRolls();
        saveData();
    }

    public static class TabListener implements ActionBar.TabListener {
        private Fragment mFragment;
        private boolean mAdded;
        private final String mTag;

        public TabListener(Fragment fragment, String tag) {
            mFragment = fragment;
            mAdded = false;
            mTag = tag;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (!mAdded) {
                mAdded = true;
                ft.replace(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.detach(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }
    }
}
