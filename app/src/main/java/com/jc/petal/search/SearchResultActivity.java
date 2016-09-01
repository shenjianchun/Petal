package com.jc.petal.search;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.data.source.PetalRepository;
import com.uilibrary.app.BaseActivity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索结果界面
 * Created by 14110105 on 2016-09-01.
 */
public class SearchResultActivity extends BaseActivity {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private String mTitle;
    private String mSearchKey;
    private String[] mTabTitles;
    private List<Fragment> mFragmentList;
    private PetalRepository mRepository;

    @Override
    protected void initViewsAndEvents() {


        mSearchKey = getIntent().getStringExtra(Constants.ARG_SEARCH_KEY);
        mTitle = getResources().getString(R.string.search_result, mSearchKey);
        setTitle(mTitle);

        mTabTitles = getResources().getStringArray(R.array.search_result_section);


        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);


        mFragmentList = new ArrayList<>(3);

        mRepository = PetalRepository.getInstance(this);

        SearchPinListFragment pinListFragment = SearchPinListFragment.newInstance(mSearchKey);
        new SearchPinListPresenter(pinListFragment, mRepository);
        mFragmentList.add(pinListFragment);

        SearchBoardListFragment boardListFragment = SearchBoardListFragment.newInstance(mSearchKey);
        new SearchBoardListPresenter(boardListFragment, mRepository);
        mFragmentList.add(boardListFragment);

        SearchPinListFragment pinListFragment3 = SearchPinListFragment.newInstance(mSearchKey);
        new SearchPinListPresenter(pinListFragment3, mRepository);
        mFragmentList.add(pinListFragment3);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search_result;
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
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
