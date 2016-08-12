package com.jc.petal.widget;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.data.model.Weekly;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import my.nouilibrary.utils.T;

/**
 * 轮播的Banner
 * Created by JC on 2016-08-05.
 */
public class BannerView extends FrameLayout {

    private List<Weekly> mWeeklies;

    private int[] mImageIDs;
    private List<ImageView> mImageViews;

    private ViewPager mViewPager;
    private LinearLayout mIndicatorLayout;

    private int preDotPosition;

    private int scrollTimeOffset = 5000; // ms

    /** Banner滚动线程是否销毁的标志，默认不销毁 */
    private boolean isStop = false;

    public BannerView(Context context) {
        super(context);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {

        View layout = LayoutInflater.from(context).inflate(R.layout
                .header_banner, this, true);

        mViewPager = ButterKnife.findById(layout, R.id.banner);
        mIndicatorLayout = ButterKnife.findById(layout, R.id.indicator);

    }

    public void setWeeklies(List<Weekly> weeklies) {
        mWeeklies = weeklies;
        setupViewpager();
    }

    private void setupViewpager() {

        mImageIDs = new int[]{R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3, R
                .drawable.banner_4};

//        mImageViews = new ArrayList<>();
//        for (int id : mImageIDs) {
//
//            ImageView imageView = new ImageView(getContext());
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
//                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            imageView.setBackgroundResource(id);
//            mImageViews.add(imageView);
//
//            // 添加指示器
//            View dot = new View(getContext());
//            dot.setBackgroundResource(R.drawable.shape_dot);
//            dot.setEnabled(false);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
//            layoutParams.leftMargin = 10;
//            mIndicatorLayout.addView(dot, layoutParams);
//
//        }

        mImageViews = new ArrayList<>();

        for (int i = 0; i < mWeeklies.size(); i++) {

            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setBackgroundResource(id);
            mImageViews.add(imageView);

            // 添加指示器
            View dot = new View(getContext());
            dot.setBackgroundResource(R.drawable.shape_dot);
            dot.setEnabled(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
            layoutParams.leftMargin = 10;
            mIndicatorLayout.addView(dot, layoutParams);

        }

        mIndicatorLayout.getChildAt(0).setEnabled(true);

        mViewPager.setAdapter(new BannerAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newDotPosition = position % mImageViews.size();

                mIndicatorLayout.getChildAt(preDotPosition).setEnabled(false);
                mIndicatorLayout.getChildAt(newDotPosition).setEnabled(true);

                preDotPosition = newDotPosition;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isStop = false;
        new Thread(new ScrollRunnable()).start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStop = true;
    }


    private class BannerAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Weekly weekly = mWeeklies.get(position % mWeeklies.size());
            View view = mImageViews.get(position % mImageViews.size());

//            View view = mImageViews.get(position);
            Glide.with(getContext()).load("http://hbfile.b0.upaiyun.com/" + weekly.cover).into(
                    (ImageView) view);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    T.showShort(getContext(), "点击图片！！");
                }
            });

            if (view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeView(view);
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = mImageViews.get(position % mImageViews.size());
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    public class ScrollRunnable implements Runnable {

        @Override
        public void run() {
            while (!isStop) {
                // View可见的情况下才进行更新
                if (BannerView.this.isShown()) {

                    SystemClock.sleep(scrollTimeOffset);

                    BannerView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            int newPosition = mViewPager.getCurrentItem() + 1;
                            mViewPager.setCurrentItem(newPosition);
                        }
                    });
                }
            }

        }
    }


}
