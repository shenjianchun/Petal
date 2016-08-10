package com.jc.petal.pin;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.Pin;

/**
 * This specifies the contract between the view and the presenter.
 * Created by JC on 2016-08-10.
 */
public interface PinDetailContract {

    interface View extends BaseView<Presenter> {
        void showPinInfo(Pin pin);
    }


    interface Presenter extends BasePresenter {
        void getPin(int pinId);
    }


}
