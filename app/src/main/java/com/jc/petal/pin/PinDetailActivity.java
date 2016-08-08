package com.jc.petal.pin;

import com.google.common.base.Preconditions;

import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.uilibrary.app.BaseActivity;

import android.support.v4.view.ViewPager;

import java.util.List;

import butterknife.Bind;

/**
 * 图片采集详情Activity
 * Created by JC on 2016-08-07.
 */
public class PinDetailActivity extends BaseActivity {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private PinDetailAdapter mAdapter;

    private List<Pin> mPins;

    @Override
    protected void initViewsAndEvents() {

        setHomeButtonEnabled();

        mPins = getIntent().getParcelableArrayListExtra("pins") ;
        // 禁止为空，为空则为异常情况
        Preconditions.checkNotNull(mPins);
        mAdapter = new PinDetailAdapter(getSupportFragmentManager(), mPins);

        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pin_detail;
    }
}