package com.jc.petal.pin;

import com.google.common.base.Preconditions;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.PetalRepository;

/**
 * Pin Detail Presenter
 * Created by JC on 2016-08-10.
 */
public class PinDetailPresenterImpl implements PinContract.PinDetailPresenter {

    private final PinContract.PinDetailView mView;
    private final PetalRepository mRepository;

    public PinDetailPresenterImpl(PinContract.PinDetailView view, PetalRepository repository) {
        mView = Preconditions.checkNotNull(view);
        mRepository = Preconditions.checkNotNull(repository);
        view.setPresenter(this);
    }

    @Override
    public void getPin(String pinId) {
        mRepository.getPin(pinId, new RequestCallback<Pin>() {
            @Override
            public void onSuccess(Pin data) {
//                Logger.d(data);
                mView.showPinInfo(data);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    public void like(String pinId, boolean flag) {

        mRepository.like(pinId, flag, new RequestCallback<Pin>() {
            @Override
            public void onSuccess(Pin data) {
                mView.likeSuccess();
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
