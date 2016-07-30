package com.jc.petal;

/**
 * Created by 14110105 on 2016-07-29.
 */
public interface RequestCallback<T> {
    void onSuccess(T data);

    void onError(String msg);
}
