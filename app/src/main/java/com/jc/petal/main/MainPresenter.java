package com.jc.petal.main;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.PinEntity;
import com.jc.petal.data.source.PetalRepository;

import java.util.List;

/**
 * Main Presenter
 * Created by JC on 2016-08-01.
 */
public class MainPresenter implements MainContract.Presenter {


    private final MainContract.View mView;
    private final PetalRepository mRepository;

    public MainPresenter(MainContract.View view, PetalRepository repository) {
        mView = view;
        mRepository = repository;
        view.setPresenter(this);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {
        fetchPinsByType("all", 20);
    }

    @Override
    public void stop() {

    }

    @Override
    public void fetchPinsByType(String type, int limit) {
        mRepository.getPinsListByType(type, limit, new RequestCallback<List<PinEntity>>() {
            @Override
            public void onSuccess(List<PinEntity> data) {

                mView.showPins(data);
                mView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
            }
        });
    }

    @Override
    public void fetchMaxPinsByType(String type, int max, int limit) {
        mRepository.getPinsListByType(type, limit, new RequestCallback<List<PinEntity>>() {
            @Override
            public void onSuccess(List<PinEntity> data) {

//                Logger.d(data);
                mView.showPins(data);
                mView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
            }
        });
    }
}
