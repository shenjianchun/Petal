package com.jc.petal.search;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.base.CommonPinListAdapter;
import com.jc.petal.category.CategoryPinListFragment;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.User;
import com.jc.petal.pin.PinDetailActivity;
import com.jc.petal.user.UserContract;
import com.jc.petal.widget.EndlessRecyclerViewScrollListener;
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
public class SearchUserListFragment extends BaseFragment implements UserContract.SerchUserListView {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    CommonPinListAdapter mAdapter;

    private UserContract.SearchUserListPresenter mPresenter;
    private String mParam;
    private ArrayList<Pin> mPins;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_pins_list;
    }

    @Override
    protected void initViewsAndEvents() {
        if (getArguments() != null) {
            mParam = getArguments().getString(Constants.ARG_SEARCH_KEY);
        }

        mPins = new ArrayList<>(20);

        initViews();

        mPresenter.searchUsers(true, mParam, Constants.LIMIT);
    }


    /**
     * 初始化RecyclerView
     */
    private void initViews() {

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加间隔
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.space_item_decoration)));
        // Set the adapter
        mAdapter = new CommonPinListAdapter(getContext(), mPins, new CategoryPinListFragment.OnImageClickListener() {
            @Override
            public void onClick(Pin pin, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("pins", mPins);
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

        // 添加加载更多接口
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPresenter.searchUsers(false, mParam, Constants.LIMIT);
            }
        });


        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.searchUsers(true, mParam, Constants.LIMIT);
            }
        });

    }

    public static SearchUserListFragment newInstance(String id) {
        SearchUserListFragment fragment = new SearchUserListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_SEARCH_KEY, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showUsers(boolean isRefresh, List<User> users) {

        if (isRefresh) {
            mPins.clear();
            mAdapter.notifyDataSetChanged();
        }

        int curSize = mAdapter.getItemCount();

//        mPins.addAll(pins);
//        mAdapter.notifyItemRangeInserted(curSize, pins.size());
    }

    @Override
    public void setPresenter(UserContract.SearchUserListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showError(String msg) {
        T.showShort(getContext(), msg);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
