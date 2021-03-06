package com.jc.petal.main;

import com.bumptech.glide.Glide;
import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.category.CategoryContract;
import com.jc.petal.category.CategoryPinListFragment;
import com.jc.petal.category.CategoryPinsPresenter;
import com.jc.petal.data.model.User;
import com.jc.petal.data.source.PetalRepository;
import com.jc.petal.login.LoginActivity;
import com.jc.petal.search.SearchActivity;
import com.jc.petal.user.UserActivity;
import com.jc.petal.utils.ActivityUtils;
import com.uilibrary.app.BaseActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.nouilibrary.utils.SPUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_LOGIN = 1;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.fab_repin)
    FloatingActionButton mFloatingBtn;

    ActionBarDrawerToggle mDrawerToggle;

    // 初始化第一个Fragment
    CategoryPinListFragment mFragment;
    CategoryContract.Presenter mCategoryPinListPresenter;
    private PetalRepository mRepository;

    private String[] mTypeTitles;
    private String[] mTypeValues;

    @Override
    protected void initViewsAndEvents() {

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 获取DataSource
        mRepository = PetalRepository.getInstance(getApplicationContext());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R
                .string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        setupDrawerContent(mNavigationView);

        mFragment = (CategoryPinListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (mFragment == null) {
            mFragment = CategoryPinListFragment.newInstance(Constants.DEFAULT_CATEGORY);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mFragment, R.id.contentFrame);
        }

        // 初始化 Presenter
        mCategoryPinListPresenter = new CategoryPinsPresenter(mFragment, mRepository);

        // TODO: 2016-08-17  需要删除，重构
        if (mRepository.isLogin()) {
            String userName = (String) SPUtils.get(this, Constants.USER_NAME, "");
            String avatarKey = (String) SPUtils.get(this, Constants.USER_AVATAR_KEY, "");

            View headerView = mNavigationView.getHeaderView(0);
            TextView nameTv = ButterKnife.findById(headerView, R.id.tv_name);
            ImageView imageView = ButterKnife.findById(headerView, R.id.iv_picture);

            nameTv.setText(userName);
            if (!TextUtils.isEmpty(avatarKey)) {
                String url = getString(R.string.url_image_small, avatarKey);
                Glide.with(this).load(url).into(imageView);
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userId = (String) SPUtils.get(MainActivity.this, Constants.USER_ID, "");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ARG_USER_ID, userId);
                    readyGo(UserActivity.class, bundle);

                }
            });

        }

        mFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(SearchActivity.class);
            }
        });

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
                    .ic_loyalty_white_36dp).setCheckable(true);
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
     *
     * @param type 图片类型
     */
    private void setCurrentFragment(String type) {
        CategoryPinListFragment fragment = CategoryPinListFragment.newInstance(type);
        new CategoryPinsPresenter(fragment, mRepository);
        ActivityUtils.ReplaceFragmentToActivity(getSupportFragmentManager(), fragment, R.id
                .contentFrame);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            User user = data.getParcelableExtra("user");
            updateUserInfo(user);
        }
    }

    private void updateUserInfo(User user) {

        // 用户信息栏
        View headerView = mNavigationView.getHeaderView(0);

        if (headerView != null) {
            TextView nameTv = ButterKnife.findById(headerView, R.id.tv_name);
            ImageView imageView = ButterKnife.findById(headerView, R.id.iv_picture);

            nameTv.setText(user.username);

            String url = getString(R.string.url_image_small, user.avatar.key);
            Glide.with(this).load(url).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userId = (String) SPUtils.get(MainActivity.this, Constants.USER_ID, "");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ARG_USER_ID, userId);
                    readyGo(UserActivity.class, bundle);

                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture:
                readyGoForResult(LoginActivity.class, REQUEST_CODE_LOGIN);

                break;
            default:
                break;
        }
    }
}
