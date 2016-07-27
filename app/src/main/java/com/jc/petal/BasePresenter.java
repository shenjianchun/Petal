package com.jc.petal;


/**
 * Presenter基类
 * Created by JC on 2015/9/28.
 */
public interface BasePresenter {
    /**
     * Called when the presenter is initialized, this method represents the start of the presenter
     * lifecycle.
     */
    void initialize();

    /**
     * Called when the presenter is started. After the initialization and when the presenter comes
     * from a stop state.
     */
    void start();

    /**
     * Called when the presenter is stopped.
     */
    void stop();
}
