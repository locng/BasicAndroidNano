package com.udaicty.myapplication;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private String TAG = getClass().getSimpleName();

    /**
     * Create an activity that does the following things:

     Sets the content view to be the layout with the ViewPager.
     Creates a class that extends the FragmentStatePagerAdapter abstract class and
     implements the getItem() method to supply instances of ScreenSlidePageFragment as new pages.
     The pager adapter also requires that you implement the getCount() method,
     which returns the amount of pages the adapter will create (five in the example).
     Hooks up the PagerAdapter to the ViewPager.
     Handles the device's back button by moving backwards in the virtual stack of fragments.
     If the user is already on the first page, go back on the activity back stack.
     * */
    /**
     * The number of pages to show (playlist/artists/albums/songs)
     */
    private static final int NUM_PAGES = 4;
    /**
     * The page widget
     */
    private ViewPager mViewPager;
    Intent intent;
    /**
     * The page adapter which provides the pages to view pager widget
     */
    private PagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPagerAdapter = new PlaceHolderPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mTabLayout.setOnTabSelectedListener(this);
        getSupportActionBar().setIcon(R.drawable.ic_music);
        TextView tx = (TextView) findViewById(R.id.tx_toolbar);
        tx.setText(R.string.app_name);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);

            String[] tabDescription = getResources().getStringArray(R.array.tab_lit);
            tab.setText(tabDescription[i]);
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            //Otherwise, select the previous step
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int currentTabSelected = tab.getPosition();
        Log.d(TAG, "onTabSelected() Tab " + currentTabSelected + "clicked");
        mViewPager.setCurrentItem(currentTabSelected);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.d(TAG, "onTabUnselected()");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.d(TAG, "onTabReselected()");
    }

    /**
     * Simple pager adapter that represents 4 page object in sequence
     */
    private class PlaceHolderPagerAdapter extends FragmentPagerAdapter {
        public PlaceHolderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Log.d(TAG, "getItem() " + position);
            //TODO: hardcoded here, need to update later
            if (position == 0)
                return PlaylistFragment.newInstance(null, 0);
            else if (position == 3)
                return SongsFragment.newInstance(1);
            else
                return PlaylistFragment.newInstance(null, 0);

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
