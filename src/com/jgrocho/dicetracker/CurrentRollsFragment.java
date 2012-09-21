package com.jgrocho.dicetracker;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.jgrocho.layout.FlowLayout;

public class CurrentRollsFragment extends SherlockFragment {

    private Button[] mNumeralButtons;
    private BarChartView mBarChartView;
    private Rolls mRolls;

    public CurrentRollsFragment() {
        this(new Rolls());
    }

    public CurrentRollsFragment(Rolls rolls) {
        mRolls = rolls;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        final Resources r = getResources();
        final SherlockFragmentActivity activity = getSherlockActivity();

        mNumeralButtons = new Button[11];
        String[] numerals = r.getStringArray(R.array.numerals);
        final float scale = r.getDisplayMetrics().density;
        int minSize = (int) (48 * scale + 0.5f);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 11; i++) {
            final int key = i;
            Button button = new Button(activity);
            button.setLayoutParams(lp);
            button.setText(numerals[i]);
            button.setMinHeight(minSize);
            button.setMinimumHeight(minSize);
            button.setMinWidth(minSize);
            button.setMinimumWidth(minSize);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    mRolls.increaseAt(key);
                }
            });
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
        mRolls.registerListener(mBarChartView);
        for (int i = 0; i < 11; i++)
            buttonLayout.addView(mNumeralButtons[i]);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.current_rolls, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_current_clear:
                showClearDialog();
                break;
            case R.id.menu_save:
                showSaveDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showClearDialog() {
        SherlockDialogFragment newFragment = ClearDialogFragment.newInstance(ClearDialogFragment.CURRENT);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private void showSaveDialog() {
        SherlockDialogFragment newFragment = SaveDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRolls.unregisterListener(mBarChartView);
        ((ViewGroup) getView().findViewById(R.id.buttonFlow)).removeAllViews();
    }
}
