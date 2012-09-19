package com.jgrocho.dicetracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.jgrocho.layout.FlowLayout;

public class CurrentRollsFragment extends Fragment {

    private Button[] mNumeralButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            mNumeralButtons[i] = button;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_rolls_fragment,
                container, false);
        FlowLayout buttonLayout = (FlowLayout) view
                .findViewById(R.id.buttonFlow);
        for (int i = 0; i < 11; i++)
            buttonLayout.addView(mNumeralButtons[i]);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) getView().findViewById(R.id.buttonFlow)).removeAllViews();
    }
}
