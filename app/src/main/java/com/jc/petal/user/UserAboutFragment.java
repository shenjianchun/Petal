package com.jc.petal.user;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.data.model.User;
import com.uilibrary.app.BaseFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 关于用户 Fragment
 */
public class UserAboutFragment extends BaseFragment {


    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.tv_job)
    TextView mTvJob;
    @BindView(R.id.tv_url)
    TextView mTvUrl;
    @BindView(R.id.tv_about)
    TextView mTvAbout;


    public UserAboutFragment() {
        // Required empty public constructor
    }

    public static UserAboutFragment newInstance(@NonNull User user) {
        UserAboutFragment fragment = new UserAboutFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_user_about;
    }

    @Override
    protected void initViewsAndEvents() {
        if (getArguments() != null) {
            User user = getArguments().getParcelable(Constants.ARG_USER);
            if (user != null && user.profile != null) {
                mTvLocation.setText(user.profile.location);
                mTvSex.setText(user.profile.sex);
                mTvBirthday.setText(user.profile.birthday);
                mTvJob.setText(user.profile.job);
                mTvUrl.setText(user.profile.url);
                mTvAbout.setText(user.profile.about);
            } else {
//                mUserAboutTv.setText("空空如也！");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
