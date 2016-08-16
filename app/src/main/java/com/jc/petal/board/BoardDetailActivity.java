package com.jc.petal.board;

import com.google.common.base.Preconditions;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.category.PinsListFragment;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.PetalRepository;
import com.jc.petal.pin.PinDetailActivity;
import com.jc.petal.widget.SpacesItemDecoration;
import com.uilibrary.app.BaseActivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.nouilibrary.utils.T;

/**
 * 画板详情 Activity
 * Created by JC on 2016-08-11.
 */
public class BoardDetailActivity extends BaseActivity implements BoardContract.BoardDetailView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    TextView mBoardDesc;
    TextView mUserTv;
    TextView mBoardFollowCount;
    TextView mBoardPinCount;

    private String mBoardId;
    private ArrayList<Pin> mPins;
    private BoardDetailAdapter mAdapter;
    private BoardContract.BoardDetailPresenter mPresenter;

    private int mCurrent = 0;
    private int mLimit = 20;

    @Override
    protected void initViewsAndEvents() {

        setHomeButtonEnabled();

        mPins = new ArrayList<>();

        mBoardId = getIntent().getStringExtra(Constants.ARG_BOARD_ID);

        Preconditions.checkNotNull(mBoardId);


        new BoardDetailPresenterImpl(this, PetalRepository.getInstance(getApplicationContext()));

        initRecyclerView();

        mPresenter.getBoard(mBoardId);
        mPresenter.getBoardPins(mBoardId, mCurrent, mLimit);
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
        mAdapter = new BoardDetailAdapter(this, mPins, new PinsListFragment.OnImageClickListener() {
            @Override
            public void onClick(Pin pin, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("pins", mPins);
                bundle.putInt("position", position);
                readyGo(PinDetailActivity.class, bundle);
            }
        }, new PinsListFragment.OnPinInfoClickListener() {
            @Override
            public void onClick(Pin pin) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("pins", pin);
                // TODO: 2016-08-07  修改需要跳转的类名
                readyGo(PinDetailActivity.class, bundle);
            }
        });

        mAdapter.setHeaderView(inflateHeader());

        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 画板详情的头部View
     * @return Header View
     */
    private View inflateHeader() {

        View headerView = getLayoutInflater().inflate(R.layout.view_board_detail_info, mRecyclerView,
                false);

        mBoardDesc = ButterKnife.findById(headerView, R.id.tv_board_describe);
        mUserTv = ButterKnife.findById(headerView,R.id.tv_board_describe);
        mBoardFollowCount = ButterKnife.findById(headerView, R.id.tv_board_follow_count);
        mBoardPinCount = ButterKnife.findById(headerView, R.id.tv_board_pin_count);

        return headerView;
    }

    private void updateHeader(Board board) {

        mUserTv.setText(board.user.username);
        mBoardDesc.setText(board.title);
        mBoardFollowCount.setText(String.valueOf(board.follow_count));
        mBoardPinCount.setText(String.valueOf(board.pin_count));
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_board_detail;
    }

    @Override
    public void showBoardInfo(Board board) {

        updateHeader(board);
    }

    @Override
    public void showBoardPins(List<Pin> pins) {
        int curSize = mAdapter.getItemCount();
        mPins.addAll(pins);
        mAdapter.notifyItemRangeInserted(curSize, pins.size());
    }

    @Override
    public void setPresenter(BoardContract.BoardDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        showLoadingDialog("正在加载...");
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showError(String msg) {
        T.showShort(this, msg);
    }
}
