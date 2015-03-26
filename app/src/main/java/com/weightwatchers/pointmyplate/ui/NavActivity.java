package com.weightwatchers.pointmyplate.ui;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.weightwatchers.pointmyplate.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class NavActivity extends BaseActivity implements MaterialTabListener {

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.tabHost)
    MaterialTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        ButterKnife.inject(this);

        NavPagerAdapter adapter = new NavPagerAdapter();
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });
        adapter.initTabs();
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        // when the tab is clicked the pager swipe content to the tab position
        pager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    private class NavPagerAdapter extends FragmentPagerAdapter {

        public NavPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                default:
                    return HomeFragment.newInstance();
                case 1:
                    return SearchFragment.newInstance();
                case 2:
                    return PhotoFragment.newInstance();
                case 3:
                    return ActivityFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        private Drawable getIcon(int position) {
            switch (position) {
                case 0:
                default:
                    return getResources().getDrawable(R.drawable.home);
                case 1:
                    return getResources().getDrawable(R.drawable.search);
                case 2:
                    return getResources().getDrawable(R.drawable.camera);
                case 3:
                    return getResources().getDrawable(R.drawable.man);
            }
        }

        private void initTabs() {
            for (int i = 0; i < getCount(); i++) {
                tabHost.addTab(
                        tabHost.newTab()
                                .setIcon(getIcon(i))
                                .setTabListener(NavActivity.this)
                );
            }

        }
    }

}
