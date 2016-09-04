package com.jc.petal.search;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.board.BoardContract;
import com.jc.petal.board.BoardListAdapter;
import com.jc.petal.data.model.Board;
import com.jc.petal.widget.recyclerview.EndlessRecyclerViewScrollListener;
import com.jc.petal.widget.recyclerview.SpacesItemDecoration;
import com.uilibrary.app.BaseFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * BoardList Fragment
 */
public class SearchBoardListFragment extends BaseFragment implements BoardContract.SearchBoardListView {

    private String mParam;
    private BoardContract.SearchBoardListPresenter mPresenter;
    private List<Board> mBoards;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchBoardListFragment() {
    }

    BoardListAdapter mAdapter;

    @SuppressWarnings("unused")
    public static SearchBoardListFragment newInstance(@NonNull String userId) {
        SearchBoardListFragment fragment = new SearchBoardListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_SEARCH_KEY, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_board_list;
    }

    @Override
    protected void initViewsAndEvents() {
        if (getArguments() != null) {
            mParam = getArguments().getString(Constants.ARG_SEARCH_KEY);
        }


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加间隔
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.space_item_decoration)));
        // Set the adapter
        mBoards = new ArrayList<>();
        mAdapter = new BoardListAdapter(getContext(), mBoards);

        mRecyclerView.setAdapter(mAdapter);


        // 添加加载更多接口
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPresenter.searchBoards(false, mParam, Constants.LIMIT);
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.searchBoards(true, mParam, Constants.LIMIT);
            }
        });

        mPresenter.searchBoards(true, mParam, Constants.LIMIT);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showBoards(boolean isRefresh, List<Board> boards) {

        int curSize = mAdapter.getItemCount();

        if (isRefresh) {
            mBoards.clear();
            mAdapter.notifyDataSetChanged();
        }

        mBoards.addAll(boards);

        mAdapter.notifyItemRangeInserted(curSize, boards.size());

    }

    @Override
    public void setPresenter(BoardContract.SearchBoardListPresenter presenter) {
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

    }


}
