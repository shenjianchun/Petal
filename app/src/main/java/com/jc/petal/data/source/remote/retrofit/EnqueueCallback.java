package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.RequestCallback;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by 14110105 on 2016-08-11.
 */
public abstract class EnqueueCallback<T> implements Callback<T> {

    private final RequestCallback<T> mRequestCallback;

    public EnqueueCallback(RequestCallback<T> requestCallback) {
        mRequestCallback = requestCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 200 && response.body() != null) {
            T object = response.body();
            Logger.d(object);
            mRequestCallback.onSuccess(object);

        } else {
            Logger.d(response.errorBody());
            mRequestCallback.onError(String.valueOf(response.code()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Logger.d(t.toString());
        mRequestCallback.onError("网络或者服务器有问题！");
    }
}
