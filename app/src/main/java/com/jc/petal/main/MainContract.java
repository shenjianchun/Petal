package com.jc.petal.main;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.module.PinEntity;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 * Created by JC on 2016-08-01.
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showPins(List<PinEntity> pins);
    }

    interface Presenter extends BasePresenter {
        void fetchPinsByType(String type, int limit);
        void fetchMaxPinsByType(String type, int max, int limit);
    }

}
