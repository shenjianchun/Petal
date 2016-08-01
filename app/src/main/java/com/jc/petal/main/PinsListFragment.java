package com.jc.petal.main;

import com.jc.petal.R;
import com.jc.petal.data.module.PinEntity;
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
public class PinsListFragment extends BaseFragment implements MainContract.View{

    private static final String ARG_TYPE = "type";

    private MainContract.Presenter mPresenter;

    private int mType = 1;
    private OnListFragmentInteractionListener mListener;

    private List<PinEntity> mPins;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.list)
    RecyclerView mRecyclerView;
    PinsListAdapter mAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PinsListFragment() {
    }

    public static PinsListFragment newInstance(int columnCount) {
        PinsListFragment fragment = new PinsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mType = getArguments().getInt(ARG_TYPE);
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
                mPresenter.start();
            }
        });

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(6));
        // Set the adapter
        mAdapter = new PinsListAdapter(this, mPins, mListener);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();


        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        }, 100);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
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
    }

    @Override
    public void showPins(List<PinEntity> pins) {
        int curSize = mPins.size();
        mPins.addAll(pins);
        mAdapter.notifyItemRangeChanged(curSize, pins.size());
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
