package com.jc.petal.pin;

import com.jc.petal.data.model.Pin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 用采集详情中的ViewPager
 * Created by JC on 2016-08-08.
 */
public class PinDetailAdapter extends FragmentStatePagerAdapter {

//    private Context mContext;

    private final List<Pin> mPins;

    public PinDetailAdapter(FragmentManager fm, List<Pin> pins) {
        super(fm);
        mPins = pins;
    }


    @Override
    public Fragment getItem(int position) {
        Pin pin = mPins.get(position);

        Fragment fragment = PinDetailFragment.newInstance(pin);

        return fragment;
    }

    @Override
    public int getCount() {
        return mPins != null ? mPins.size() : 0;
    }
}
