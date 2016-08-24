package com.jc.petal.main;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.Category;

import java.util.List;

/**
 * MainContract
 * Created by 14110105 on 2016-08-24.
 */
public class MainContract {

    interface NavigationView extends BaseView<NavigationPresenter> {
        void showCategories(List<Category> categories);

    }

    interface NavigationPresenter extends BasePresenter {
        void getCategories();
    }

}
