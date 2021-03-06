package com.jc.petal.main;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.Category;

import java.util.List;

/**
 * MainContract
 * Created by JC on 2016-08-24.
 */
public class MainContract {

    interface NavigationView extends BaseView<NavigationPresenter> {
        void showCategoryList(List<Category> categories);

    }

    interface NavigationPresenter extends BasePresenter {
        void getCategoryList();
    }

}
