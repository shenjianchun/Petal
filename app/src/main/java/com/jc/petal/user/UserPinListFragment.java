package com.jc.petal.user;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.board.BoardPinListAdapter;
import com.jc.petal.category.CategoryPinListFragment;
import com.jc.petal.data.model.Pin;
import com.jc.petal.pin.PinDetailActivity;
import com.jc.petal.widget.SpacesItemDecoration;
import com.uilibrary.app.BaseFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import my.nouilibrary.utils.T;

/**
 * 用户采集过的图片列表
 * Created by JC on 2016-08-21.
 */
public class UserPinListFragment extends BaseFragment implements UserContract.UserPinListView {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    BoardPinListAdapter mAdapter;

    private UserContract.UserPinListPresenter mPresenter;
    private String mUserId;
    private List<Pin> mPins;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_pins_list;
    }

    @Override
    protected void initViewsAndEvents() {
        if (getArguments() != null) {
            mUserId = getArguments().getString(Constants.ARG_USER_ID);
        }

        mPins = new ArrayList<>();

        initRecyclerView();

        mPresenter.getUserPins(mUserId, Constants.LIMIT, Constants.QUERY_KEY_CURRENT, "");
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加间隔
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.space_item_decoration)));
        // Set the adapter
        mAdapter = new BoardPinListAdapter(getContext(), mPins, new CategoryPinListFragment.OnImageClickListener() {
            @Override
            public void onClick(Pin pin, int position) {
                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("pins", mPins);
                bundle.putInt("position", position);
                readyGo(PinDetailActivity.class, bundle);
            }
        }, new CategoryPinListFragment.OnPinInfoClickListener() {
            @Override
            public void onClick(Pin pin) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("pins", pin);
                // TODO: 2016-08-07  修改需要跳转的类名
                readyGo(PinDetailActivity.class, bundle);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    public static UserPinListFragment newInstance(String userId) {
        UserPinListFragment fragment = new UserPinListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showUserPins(List<Pin> pins) {
        int curSize = mAdapter.getItemCount();
        mPins.addAll(pins);
        mAdapter.notifyItemRangeInserted(curSize, pins.size());
    }

    @Override
    public void setPresenter(UserContract.UserPinListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        T.showShort(getContext(), msg);
    }
}
