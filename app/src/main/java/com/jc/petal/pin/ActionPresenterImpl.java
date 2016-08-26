package com.jc.petal.pin;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.PetalRepository;

/**
 * Pin Detail Action Presenter
 * Created by JC on 2016-08-26.
 */
public class ActionPresenterImpl implements PinContract.ActionPresenter {

    private PinContract.ActionView mView;
    private PetalRepository mRepository;

    public ActionPresenterImpl(PinContract.ActionView view, PetalRepository repository) {
        mView = view;
        mRepository = repository;
        mView.setPresenter(this);
    }

    @Override
    public void like(String pinId, final boolean flag) {

        mRepository.like(pinId, flag, new RequestCallback<Pin>() {
            @Override
            public void onSuccess(Pin data) {
                mView.onLikeResult(flag);
            }

            @Override
            public void onError(String msg) {
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
