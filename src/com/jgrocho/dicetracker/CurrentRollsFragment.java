package com.jgrocho.dicetracker;

import android.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CurrentRollsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.current_rolls_fragment, container,
                false);
    }

}
