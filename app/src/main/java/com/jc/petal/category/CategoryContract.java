package com.jc.petal.category;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.Weekly;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 * Created by JC on 2016-08-01.
 */
public interface CategoryContract {

    interface View extends BaseView<Presenter> {
        void showPins(List<Pin> pins);
        void showBanners(List<Weekly> weeklies);
    }

    interface Presenter extends BasePresenter {
        void fetchPinsByType(boolean forceUpdate,String type, int limit);
        void fetchMaxPinsByType(String type, int max, int limit);
        void fetchWeeklies(String max);
    }

}
