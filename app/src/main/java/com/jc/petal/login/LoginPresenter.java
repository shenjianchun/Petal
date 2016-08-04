package com.jc.petal.login;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.User;
import com.jc.petal.data.source.PetalRepository;
import com.orhanobut.logger.Logger;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 * Created by JC on 2016-07-29.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;

    private final PetalRepository mRepository;

    public LoginPresenter(LoginContract.View view, PetalRepository repository) {
        mView = view;
        mRepository = repository;

        mView.setPresenter(this);
    }

    @Override
    public void login(String username, String password) {
        mView.showLoading();
        mRepository.login(username, password, new RequestCallback<User>() {
            @Override
            public void onSuccess(User data) {
                Logger.d(data);

                mView.hideLoading();
                mView.loginSuccess(data);
            }

            @Override
            public void onError(String msg) {
                Logger.d(msg);

                mView.hideLoading();
                mView.showError(msg);
            }
        });
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
}
