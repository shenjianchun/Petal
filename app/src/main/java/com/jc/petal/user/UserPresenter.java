package com.jc.petal.user;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.User;
import com.jc.petal.data.source.PetalRepository;

/**
 * User Presenter
 * Created by JC on 2016-08-13.
 */
public class UserPresenter implements UserContract.Presenter {

    private final UserContract.View mView;
    private final PetalRepository mRepository;

    public UserPresenter(UserContract.View view, PetalRepository repository) {
        mView = view;
        mRepository = repository;
        mView.setPresenter(this);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getUserInfo(String userId) {
        mView.showLoading();
        mRepository.getUser(userId, new RequestCallback<User>() {
            @Override
            public void onSuccess(User data) {
                mView.showUserInfo(data);
                mView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }
        });
    }
}
