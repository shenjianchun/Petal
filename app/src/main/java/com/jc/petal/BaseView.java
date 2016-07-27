package com.jc.petal;

/**
 * View Interface
 * Created by JC on 2015/10/9.
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();

    void showError(String msg);
}
