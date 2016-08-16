package com.jc.petal.login;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.User;

/**
 * This specifies the contract between the view and the presenter.
 * Created by JC on 2016-07-29.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void loginSuccess(User user);
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
        void getSelfInfo();
    }
}
