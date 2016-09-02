package com.jc.petal.search;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.UserList;
import com.jc.petal.data.source.PetalRepository;
import com.jc.petal.user.UserContract;

import org.apache.commons.collections4.CollectionUtils;

import android.support.annotation.NonNull;

/**
 * SearchUserListPresenter
 * Created by 14110105 on 2016-09-02.
 */
public class SearchUserListPresenter implements UserContract.SearchUserListPresenter {

    private PetalRepository mRepository;
    private UserContract.SearchUserListView mView;

    private int mCurrentPage;

    public SearchUserListPresenter(UserContract.SearchUserListView view, PetalRepository
            repository) {
        mView = view;
        mRepository = repository;
        mView.setPresenter(this);
    }

    @Override
    public void searchUsers(final boolean isRefresh, @NonNull String key, int limit) {

        mView.showLoading();

        if (isRefresh) {
            mCurrentPage = 0;
        } else {
            mCurrentPage++;
        }

        mRepository.searchUsers(key, 20, String.valueOf(mCurrentPage), new RequestCallback<UserList>() {
            @Override
            public void onSuccess(UserList data) {
                if (!CollectionUtils.isEmpty(data.users)) {
                    mView.hideLoading();
                    mView.showUsers(isRefresh, data.users);
                }
            }

            @Override
            public void onError(String msg) {
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
