package com.jc.petal.pin;

import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.PetalRepository;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 用采集详情中的ViewPager
 * Created by JC on 2016-08-08.
 */
public class PinDetailAdapter extends FragmentPagerAdapter {

//    private Context mContext;

    private final List<Pin> mPins;
    private final PetalRepository mRepository;

    public PinDetailAdapter(FragmentManager fm, List<Pin> pins, PetalRepository repository) {
        super(fm);
        mPins = pins;
        mRepository = repository;
    }


    @Override
    public Fragment getItem(int position) {
        Pin pin = mPins.get(position);

        PinDetailFragment fragment = PinDetailFragment.newInstance(pin);
        new PinDetailPresenterImpl(fragment, mRepository);

        return fragment;
    }

    @Override
    public int getCount() {
        return mPins != null ? mPins.size() : 0;
    }
}
