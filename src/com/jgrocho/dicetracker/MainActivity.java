package com.jgrocho.dicetracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

    public static class TabListener<T extends Fragment> implements
            ActionBar.TabListener {
        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        public TabListener(Activity activity, String tag, Class<T> klass) {
            mActivity = activity;
            mTag = tag;
            mClass = klass;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null)
                ft.detach(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tab = actionBar
                .newTab()
                .setText(R.string.current_rolls_title)
                .setTabListener(
                        new TabListener<CurrentRollsFragment>(this, "current",
                                CurrentRollsFragment.class));
        actionBar.addTab(tab);
    }
}
