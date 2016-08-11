package com.jc.petal.board;

import com.google.common.base.Preconditions;

import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.uilibrary.app.BaseActivity;

import android.widget.TextView;

import butterknife.Bind;

/**
 * 画板 Activity
 * Created by JC on 2016-08-11.
 */
public class BoardActivity extends BaseActivity {


    @Bind(R.id.tv_board_describe)
    TextView mBoardDesc;
    @Bind(R.id.tv_board_user)
    TextView mUserTv;
    @Bind(R.id.tv_board_follow_count)
    TextView mBoardFollowCount;
    @Bind(R.id.tv_board_pin_count)
    TextView mBoardPinCount;

    private Pin mPin;

    @Override
    protected void initViewsAndEvents() {

        setHomeButtonEnabled();

        mPin = getIntent().getParcelableExtra("pin");

        Preconditions.checkNotNull(mPin);

        mUserTv.setText(mPin.user.username);

        mBoardDesc.setText(mPin.board.title);
        mBoardFollowCount.setText(String.valueOf(mPin.board.follow_count));
        mBoardPinCount.setText(String.valueOf(mPin.board.pin_count));
    }




    @Override
    protected int getLayoutResource() {
        return R.layout.activity_board;
    }
}
