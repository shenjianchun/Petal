package com.jc.petal.category;

import com.jc.petal.R;
import com.jc.petal.data.model.PinEntity;
import com.jc.petal.data.model.Weekly;
import com.jc.petal.widget.BannerView;
import com.jc.petal.widget.EndlessRecyclerViewScrollListener;
import com.jc.petal.widget.SpacesItemDecoration;
import com.uilibrary.app.BaseFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PinsListFragment extends BaseFragment implements CategoryContract.View {

    private static final String ARG_TYPE = "type";

    private CategoryContract.Presenter mPresenter;

    private String mType;
    private OnListFragmentInteractionListener mListener;

    private List<PinEntity> mPins;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.list)
    RecyclerView mRecyclerView;
    PinsListAdapter mAdapter;

    private BannerView mBannerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PinsListFragment() {
    }

    public static PinsListFragment newInstance(String type) {
        PinsListFragment fragment = new PinsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_pins_list;
    }

    @Override
    protected void initViewsAndEvents() {

        mPins = new ArrayList<>();

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchPinsByType(mType, 20);
            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加间隔
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.space_item_decoration)));
        // Set the adapter
        mAdapter = new PinsListAdapter(getContext(), mPins, mListener);

        // 首页加上一个Banner
        if (mType.equals("all")) {

            mPresenter.fetchWeeklies("");

            // 设置BannerView
            mBannerView = new BannerView(getContext());
            mAdapter.setHeaderView(mBannerView);
        }

        mRecyclerView.setAdapter(mAdapter);

        // 添加加载更多接口
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPresenter.fetchMaxPinsByType(mType, mPins.get(mPins.size() - 1).pin_id, 20);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchPinsByType(mType, 20);

        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        }, 100);
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String msg) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showPins(List<PinEntity> pins) {
        int curSize = mPins.size();
        mPins.addAll(pins);
        mAdapter.notifyItemRangeChanged(curSize, pins.size());
    }

    @Override
    public void showBanners(List<Weekly> weeklies) {
        mBannerView.setWeeklies(weeklies);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PinEntity item);
    }
}
