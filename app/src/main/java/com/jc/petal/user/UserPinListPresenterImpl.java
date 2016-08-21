package com.jc.petal.user;

import com.google.common.base.Preconditions;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.source.PetalRepository;

import android.support.annotation.NonNull;

/**
 * Board detail presenter
 * Created by JC on 2016-08-15.
 */
public class UserPinListPresenterImpl implements UserContract.UserPinListPresenter {

    private final UserContract.UserPinListView mView;
    private final PetalRepository mRepository;

    public UserPinListPresenterImpl(@NonNull UserContract.UserPinListView view, @NonNull PetalRepository
            repository) {
        mView = Preconditions.checkNotNull(view);
        mRepository = Preconditions.checkNotNull(repository);
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
    public void getUserPins(String userId, int limit, @NonNull String key, String pinId) {
        mView.showLoading();
        mRepository.getUserPins(userId, limit, key, pinId, new RequestCallback<PinList>() {
            @Override
            public void onSuccess(PinList data) {
                mView.hideLoading();
                mView.showUserPins(data.pins);
            }

            @Override
            public void onError(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }
        });
    }
}
