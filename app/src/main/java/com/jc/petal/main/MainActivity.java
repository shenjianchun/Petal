package com.jc.petal.main;

import com.jc.petal.R;
import com.jc.petal.data.source.PetalRepository;
import com.jc.petal.login.LoginActivity;
import com.jc.petal.utils.ActivityUtils;
import com.uilibrary.app.BaseActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    ActionBarDrawerToggle mDrawerToggle;

    private PetalRepository mRepository;

    @Override
    protected void initViewsAndEvents() {

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R
                .string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        setupDrawerContent(mNavigationView);

        // 初始化第一个Fragment
        PinsListFragment fragment = (PinsListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = PinsListFragment.newInstance(2);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.contentFrame);
        }

        // 获取DataSource
        mRepository = PetalRepository.getInstance();

        // 初始化 Presenter
        new MainPresenter(fragment, mRepository);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    /**
     * 初始化侧滑栏
     *
     * @param navigationView NavigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        // menu item 添加事件
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_drawer_home:
                                setTitle(R.string.drawer_home);


                                break;
                            case R.id.menu_drawer_hot:
                                setTitle(R.string.drawer_hot);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        View headerView = navigationView.getHeaderView(0);

        if (headerView != null) {
            ImageView pictureIv = ButterKnife.findById(headerView, R.id.iv_picture);
            pictureIv.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture:
                readyGo(LoginActivity.class);
                break;
            default:
                break;
        }
    }
}
