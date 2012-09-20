package com.jgrocho.dicetracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoricRollsFragment extends Fragment implements
        ActionBar.TabListener {

    private boolean mFresh;

    public HistoricRollsFragment() {
        mFresh = true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.historic_rolls_fragment, container,
                false);
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
