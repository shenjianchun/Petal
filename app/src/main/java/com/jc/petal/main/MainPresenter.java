package com.jc.petal.main;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.module.PinEntity;
import com.jc.petal.data.source.PetalRepository;
import com.orhanobut.logger.Logger;

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
        fetchPins("all", 20);
    }

    @Override
    public void stop() {

    }

    private void fetchPins(String type, int limit) {
        mRepository.getPinsListByType(type, limit, new RequestCallback<List<PinEntity>>() {
            @Override
            public void onSuccess(List<PinEntity> data) {

                Logger.d(data);
                mView.showPins(data);
                mView.hideLoading();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
