package com.jc.petal.user;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.User;

/**
 * This specifies the contract between the view and the presenter.
 * Created by JC on 2016-07-29.
 */
public interface UserContract {

    interface View extends BaseView<Presenter> {
        void showUserInfo(User user);
    }

    interface Presenter extends BasePresenter {
        void getUserInfo(String userId);
    }

}
