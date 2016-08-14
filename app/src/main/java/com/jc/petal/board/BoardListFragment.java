package com.jc.petal.board;

import com.jc.petal.Constant;
import com.jc.petal.R;
import com.jc.petal.data.model.Board;
import com.jc.petal.widget.SpacesItemDecoration;
import com.uilibrary.app.BaseFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * BoardList Fragment
 */
public class BoardListFragment extends BaseFragment implements BoardContract.BoardListView {

    private int mUserId;
    private BoardContract.BoardListPresenter mPresenter;
    private List<Board> mBoards;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BoardListFragment() {
    }

    BoardListAdapter mAdapter;

    @SuppressWarnings("unused")
    public static BoardListFragment newInstance(int userId) {
        BoardListFragment fragment = new BoardListFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.ARG_USER_ID, userId);
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
            mUserId = getArguments().getInt(Constant.ARG_USER_ID);
        }


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加间隔
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.space_item_decoration)));
        // Set the adapter
        mBoards = new ArrayList<>();
        mAdapter = new BoardListAdapter(mBoards);

        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getUserBoards(String.valueOf(mUserId));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showBoards(List<Board> boards) {

        int curSize = mBoards.size();
        mBoards.addAll(boards);
        mAdapter.notifyItemRangeChanged(curSize, boards.size());

    }

    @Override
    public void setPresenter(BoardContract.BoardListPresenter presenter) {
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

    }
}
