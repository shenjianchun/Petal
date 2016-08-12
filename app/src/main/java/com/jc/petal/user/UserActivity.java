package com.jc.petal.user;

import com.google.common.base.Preconditions;

import com.bumptech.glide.Glide;
import com.jc.petal.Constant;
import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.uilibrary.app.BaseActivity;

import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

/**
 * 用户信息 Activity
 * Created by JC on 2016-08-11.
 */
public class UserActivity extends BaseActivity {

    @BindView(R.id.iv_user_avatar)
    ImageView mAvatarIv;
    @BindView(R.id.tv_user_name)
    TextView mUserNameTv;
    @BindView(R.id.tv_user_profile)
    TextView mUserProfileTv;
    @BindView(R.id.tv_user_fans_follows)
    TextView mFansFollowsTv;

    private Pin mPin;

    @Override
    protected void initViewsAndEvents() {

        // TODO: 2016-08-12 需要重新拉取数据，而不是从Intent中获取
        mPin = getIntent().getParcelableExtra(Constant.ARG_PIN);
        Preconditions.checkNotNull(mPin);

        mUserNameTv.setText(mPin.user.username);
        mUserProfileTv.setText(mPin.user.user_id);

        String userFansFollows = getString(R.string.user_fans_follows, mPin.like_count, mPin.repin_count);
        mFansFollowsTv.setText(userFansFollows);

        // avatar url
        String avatarUrl = getString(R.string.url_image_small, mPin.user.avatar.key);
        Glide.with(this).load(avatarUrl).placeholder(R.drawable.account_circle_grey_36x36)
                .fitCenter().into(mAvatarIv);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_user;
    }
}
