package com.jc.petal.user;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.source.PetalRepository;
import com.jc.petal.pin.AbstractPinListPresenter;
import com.jc.petal.pin.PinContract;

import android.support.annotation.NonNull;

/**
 * 用户采集列表 Presenter
 * Created by JC on 2016-08-22.
 */
public class UserLikesPresenter extends AbstractPinListPresenter {

    public UserLikesPresenter(@NonNull PinContract.PinListView view, @NonNull PetalRepository
            repository) {
        super(view, repository);
    }

    @Override
    public void callRepository(String id, int limit, String key, String pinId, RequestCallback<PinList> callback) {
        mRepository.getUserLikes(id, limit, key, pinId, callback);
    }
}
