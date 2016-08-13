package com.jc.petal.user;

import com.google.common.base.Preconditions;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jc.petal.Constant;
import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.jc.petal.utils.FastBlurUtil;
import com.uilibrary.app.BaseActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 用户信息 Activity
 * Created by JC on 2016-08-11.
 */
public class UserActivity extends BaseActivity {

    @BindView(R.id.iv_user_avatar)
    ImageView mAvatarIv;
    @BindView(R.id.tv_user_name)
    TextView mUserNameTv;
    @BindView(R.id.tv_user_profile)
    TextView mUserProfileTv;
    @BindView(R.id.tv_user_fans_follows)
    TextView mFansFollowsTv;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private Pin mPin;

    private String[] mTitleList;
    private List<Fragment> mFragmentList;

    @Override
    protected void initViewsAndEvents() {

        setHomeButtonEnabled();
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        initHeader();
        initViewPager();

    }


    private void initViewPager() {
        mTitleList = getResources().getStringArray(R.array.user_section);
        mFragmentList = new ArrayList<>(4);
        mFragmentList.add(UserAboutFragment.newInstance());
        mFragmentList.add(UserAboutFragment.newInstance());
        mFragmentList.add(UserAboutFragment.newInstance());
        mFragmentList.add(UserAboutFragment.newInstance());


        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initHeader() {
        // TODO: 2016-08-12 需要重新拉取数据，而不是从Intent中获取
        mPin = getIntent().getParcelableExtra(Constant.ARG_PIN);
        Preconditions.checkNotNull(mPin);

        mUserNameTv.setText(mPin.user.username);
        mUserProfileTv.setText(mPin.user.user_id);

        String userFansFollows = getString(R.string.user_fans_follows, mPin.like_count, mPin.repin_count);
        mFansFollowsTv.setText(userFansFollows);

        // avatar url
        String avatarUrl = getString(R.string.url_image_small, mPin.user.avatar.key);
        Glide.with(this).load(avatarUrl).asBitmap().placeholder(R.drawable.account_circle_grey_36x36)
                .fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mAvatarIv.setImageBitmap(resource);

                // 加载高斯模糊图片作为背景
                Drawable backDrawable = new BitmapDrawable(getResources(), FastBlurUtil.doBlur(resource, 25, false));
                mAppBar.setBackground(backDrawable);
            }
        });


    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_user;
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mTitleList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList[position];
        }


    }

}
