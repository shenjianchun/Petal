package com.jc.petal.user;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 * Created by JC on 2016-07-29.
 */
public interface UserContract {

    interface View extends BaseView<Presenter> {
//        void showBoards(List<Board> boards);
    }

    interface Presenter extends BasePresenter {
    }
}
