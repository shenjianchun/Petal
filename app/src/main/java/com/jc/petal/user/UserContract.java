package com.jc.petal.user;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.User;

import android.support.annotation.NonNull;

import java.util.List;

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


    interface SerchUserListView extends BaseView<SearchUserListPresenter> {
        void showUsers(boolean isRefresh,List<User> users);
    }

    interface SearchUserListPresenter extends BasePresenter {
        void searchUsers(final boolean isRefresh, @NonNull String key, int limit);
    }

}
