package com.jc.petal.pin;

import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.PetalRepository;
import com.uilibrary.app.BaseActivity;

import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 图片采集详情Activity
 * Created by JC on 2016-08-07.
 */
public class PinDetailActivity extends BaseActivity implements PinDetailFragment
        .OnPinDetailFragmentInteractionListener, PinContract.ActionView {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private PetalRepository mRepository;
    private PinContract.ActionPresenter mPresenter;

    private PinDetailAdapter mAdapter;

    private List<Pin> mPins;

    @Override
    protected void initViewsAndEvents() {

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setHomeButtonEnabled();

        mRepository = PetalRepository.getInstance(this);

        mPins = getIntent().getParcelableArrayListExtra("pins");
        int position = getIntent().getIntExtra("position", 0);

        // 禁止为空，为空则为异常情况
        checkNotNull(mPins);
        mAdapter = new PinDetailAdapter(getSupportFragmentManager(), mPins, mRepository);

        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(position);

        new ActionPresenterImpl(this, mRepository);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pin_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pin_detail, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_like:
                String pinId = mPins.get(mViewPager.getCurrentItem()).pin_id;
                boolean flag = mPins.get(mViewPager.getCurrentItem()).liked;
                mPresenter.like(pinId, !flag);

                break;

        }

        return true;
    }

    @Override
    public void updateLikeState(boolean flag) {
        MenuItem item = mToolbar.getMenu().findItem(R.id.action_like);
        setIconDynamic(item, flag);
    }


    /**
     * 设置动态的icon图标 反向设置
     * 如果为true 显示undo图片
     * 为false 显示do图标
     * 所以传入当前状态值就可以 内部已经做判断
     *
     * @param item
     * @param isLike
     */
    private void setIconDynamic(MenuItem item, boolean isLike) {
        AnimatedVectorDrawableCompat drawableCompat;
        drawableCompat = AnimatedVectorDrawableCompat.create(this,
                isLike ? R.drawable.drawable_animation_favorite_undo : R.drawable.drawable_animation_favorite_do);
        item.setIcon(drawableCompat);
    }

    @Override
    public void onRepinClicked(Pin pin) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("repin_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.commit();

        RepinDialogFragment dialogFragment = RepinDialogFragment.newInstance(pin);
        new RepinPresenterImpl(dialogFragment, mRepository);

        dialogFragment.show(getSupportFragmentManager(), "repin_dialog");
    }

    @Override
    public void onLikeResult(boolean flag) {
        updateLikeState(flag);
//        T.showShort(this, "Like Success!");
    }

    @Override
    public void setPresenter(PinContract.ActionPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }
}
