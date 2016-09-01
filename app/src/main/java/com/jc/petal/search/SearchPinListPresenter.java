package com.jc.petal.search;

import com.google.common.base.Preconditions;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.source.PetalRepository;
import com.jc.petal.pin.PinContract;

import org.apache.commons.collections4.CollectionUtils;

import android.support.annotation.NonNull;

/**
 * Created by 14110105 on 2016-09-01.
 */
public class SearchPinListPresenter implements PinContract.SearchPinListPresenter {

    private final PinContract.SearchPinListView mView;
    protected final PetalRepository mRepository;

    private int mCurrentPage = 0;

    public SearchPinListPresenter(@NonNull PinContract.SearchPinListView view, @NonNull PetalRepository
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
    public void searchPins(final boolean isRefresh, @NonNull String key, int limit) {
        mView.showLoading();

        if (isRefresh) {
            mCurrentPage = 0;
        } else {
            mCurrentPage++;
        }

        RequestCallback<PinList> requestCallback = new RequestCallback<PinList>() {
            @Override
            public void onSuccess(PinList data) {
                mView.hideLoading();
                if (!CollectionUtils.isEmpty(data.pins)) {
                    mView.showPins(isRefresh, data.pins);
                }
            }

            @Override
            public void onError(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }
        };

        mRepository.searchPins(key, limit, String.valueOf(mCurrentPage), requestCallback);
    }
}
