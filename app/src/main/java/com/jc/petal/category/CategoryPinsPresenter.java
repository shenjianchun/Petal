package com.jc.petal.category;

import com.jc.petal.Constants;
import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.model.Weekly;
import com.jc.petal.data.source.PetalRepository;

import org.apache.commons.collections4.CollectionUtils;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Main Presenter
 * Created by JC on 2016-08-01.
 */
public class CategoryPinsPresenter implements CategoryContract.Presenter {

    private final CategoryContract.View mView;
    private final PetalRepository mRepository;

    public CategoryPinsPresenter(CategoryContract.View view, PetalRepository repository) {
        mView = view;
        mRepository = repository;
        view.setPresenter(this);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {
//        fetchPinsByCategory(true, "all", 20);
    }

    @Override
    public void stop() {

    }

    @Override
    public void getPins(boolean forceUpdate, String category, int limit, @NonNull final String key,
                        String pinId) {

        if (forceUpdate) {
            mRepository.refreshPinsList();
        }

        RequestCallback<PinList> callback = new RequestCallback<PinList>() {
            @Override
            public void onSuccess(PinList data) {
                mView.hideLoading();
                if (!CollectionUtils.isEmpty(data.pins)) {
                    if (key.equals(Constants.QUERY_KEY_CURRENT)) {
                        mView.showPins(true, data.pins);
                    } else {
                        mView.showPins(false, data.pins);
                    }
                }
            }

            @Override
            public void onError(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }
        };

        if (category.equals(Constants.DEFAULT_CATEGORY)) {
            mRepository.getAllPins("", limit, key, pinId, callback);
        } else {
            mRepository.getFavoritePins(category, limit, key, pinId, callback);
        }

    }

    @Override
    public void fetchWeeklies(String max) {
        mRepository.getWeeklies(max, new RequestCallback<List<Weekly>>() {
            @Override
            public void onSuccess(List<Weekly> data) {

                if (data.size() > 4) {
                    data = data.subList(0, 3);
                }

                mView.showBanners(data);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


}
