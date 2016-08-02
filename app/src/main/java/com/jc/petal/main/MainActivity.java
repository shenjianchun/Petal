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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    public static final String DEFAULT_TYPE = "all";

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    ActionBarDrawerToggle mDrawerToggle;

    // 初始化第一个Fragment
    PinsListFragment mFragment;
    MainContract.Presenter mPresenter;
    private PetalRepository mRepository;

    private String[] mTypeTitles;
    private String[] mTypeValues;

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

        mFragment = (PinsListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (mFragment == null) {
            mFragment = PinsListFragment.newInstance(DEFAULT_TYPE);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mFragment, R.id.contentFrame);
        }

        // 获取DataSource
        mRepository = PetalRepository.getInstance();

        // 初始化 Presenter
        mPresenter = new MainPresenter(mFragment, mRepository);

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

        final Menu menu = navigationView.getMenu();
        mTypeTitles = getResources().getStringArray(R.array.array_type_title);
        mTypeValues = getResources().getStringArray(R.array.array_type_value);
        int order = 0;
        for (String title : mTypeTitles) {
            menu.add(R.id.group_type_title, Menu.NONE, order++, title).setIcon(R.drawable
                    .ic_loyalty_black_36dp).setCheckable(true);
        }
        menu.getItem(0).setChecked(true);

        // menu item 添加事件
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        if (menuItem.getGroupId() == R.id.group_type_title) {
                            setTitle(menuItem.getTitle());

                            int order = menuItem.getOrder();
                            setCurrentFragment(mTypeValues[order]);
                        } else {
                            // TODO: 2016-08-02  添加设置、关于的处理

                            switch (menuItem.getItemId()) {

                                case R.id.nav_about:

                                    break;
                                case R.id.nav_set:
                                    break;
                                case R.id.nav_exit:
                                    finish();
                                    break;
                            }
                        }

                        // Close the navigation drawer when an item is selected.
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        // 用户信息栏
        View headerView = navigationView.getHeaderView(0);

        if (headerView != null) {
            ImageView pictureIv = ButterKnife.findById(headerView, R.id.iv_picture);
            pictureIv.setOnClickListener(this);
        }
    }

    /**
     * 根据不同的type类型切换对应的Fragment
     * @param type 图片类型
     */
    private void setCurrentFragment(String type) {
        PinsListFragment fragment = PinsListFragment.newInstance(type);
        new MainPresenter(fragment, mRepository);
        ActivityUtils.ReplaceFragmentToActivity(getSupportFragmentManager(), fragment, R.id
                .contentFrame);
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
