package com.jc.petal.pin;

import com.google.common.base.Preconditions;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.source.PetalRepository;

import android.support.annotation.NonNull;

/**
 * Abstract PinList presenter
 * Created by JC on 2016-08-15.
 */
public abstract class AbstractPinListPresenter implements PinContract.PinListPresenter {

    private final PinContract.PinListView mView;
    protected final PetalRepository mRepository;

    public AbstractPinListPresenter(@NonNull PinContract.PinListView view, @NonNull PetalRepository
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
    public void getPins(final boolean isRefresh, String id, int limit, @NonNull String key,
                        String pinId) {
        mView.showLoading();

        RequestCallback<PinList> requestCallback = new RequestCallback<PinList>() {
            @Override
            public void onSuccess(PinList data) {
                mView.hideLoading();
                mView.showPins(isRefresh, data.pins);
            }

            @Override
            public void onError(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }
        };

        callRepository(id, limit, key, pinId, requestCallback);

    }

    public abstract void callRepository(String id, int limit, String key, String pinId,
                                        RequestCallback<PinList> callback);
}
