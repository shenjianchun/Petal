package com.jc.petal.board;

import com.google.common.base.Preconditions;

import com.jc.petal.R;
import com.jc.petal.category.PinsListFragment;
import com.jc.petal.data.model.Pin;
import com.jc.petal.pin.PinDetailActivity;
import com.jc.petal.widget.SpacesItemDecoration;
import com.uilibrary.app.BaseActivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 画板详情 Activity
 * Created by JC on 2016-08-11.
 */
public class BoardDetailActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Pin mPin;

    @Override
    protected void initViewsAndEvents() {

        setHomeButtonEnabled();

        mPin = getIntent().getParcelableExtra("pin");

        Preconditions.checkNotNull(mPin);




        final ArrayList<Pin> boardPins = (ArrayList<Pin>) mPin.board.pins;


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加间隔
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.space_item_decoration)));
        // Set the adapter
        BoardDetailAdapter adapter = new BoardDetailAdapter(this, boardPins, new PinsListFragment.OnImageClickListener() {
            @Override
            public void onClick(Pin pin, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("pins", boardPins);
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

        adapter.setHeaderView(inflateHeader());

        mRecyclerView.setAdapter(adapter);
    }


    private View inflateHeader() {

        View headerView = getLayoutInflater().inflate(R.layout.view_board_detail_info, mRecyclerView,
                false);

        TextView mBoardDesc = ButterKnife.findById(headerView, R.id.tv_board_describe);
        TextView mUserTv = ButterKnife.findById(headerView,R.id.tv_board_describe);
        TextView mBoardFollowCount = ButterKnife.findById(headerView, R.id.tv_board_follow_count);
        TextView mBoardPinCount = ButterKnife.findById(headerView, R.id.tv_board_pin_count);


        mUserTv.setText(mPin.user.username);
        mBoardDesc.setText(mPin.board.title);
        mBoardFollowCount.setText(String.valueOf(mPin.board.follow_count));
        mBoardPinCount.setText(String.valueOf(mPin.board.pin_count));

        return headerView;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_board_detail;
    }
}
