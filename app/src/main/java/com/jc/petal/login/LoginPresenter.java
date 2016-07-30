package com.jc.petal.login;

import com.jc.petal.data.source.PetalRepository;

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
        mRepository.login(username, password, null);
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
