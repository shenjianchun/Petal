package com.jc.petal.main;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.CategoryList;
import com.jc.petal.data.source.PetalRepository;

/**
 * Created by JC on 2016-08-24.
 */
public class NavigationPresenterImpl implements MainContract.NavigationPresenter {

    private final MainContract.NavigationView mView;
    private final PetalRepository mRepository;

    public NavigationPresenterImpl(MainContract.NavigationView view, PetalRepository repository) {
        mView = view;
        mRepository = repository;
        mView.setPresenter(this);
    }

    @Override
    public void getCategoryList() {

        mRepository.getCategoryList(new RequestCallback<CategoryList>() {
            @Override
            public void onSuccess(CategoryList data) {
                mView.showCategoryList(data.categories);
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
