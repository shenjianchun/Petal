package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.module.PinEntity;
import com.jc.petal.data.module.PinsListBean;
import com.jc.petal.data.module.TokenBean;
import com.jc.petal.data.source.PetalDataSource;
import com.orhanobut.logger.Logger;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the data source that adds a latency network.
 * Created by 14110105 on 2016-07-29.
 */
public class RetrofitRemoteDataSource implements PetalDataSource {

    /**
     * 用户登录
     * @param name username
     * @param password password
     * @param callback 成功或失败后的回调函数
     */
    @Override
    public void login(String name, String password, final RequestCallback<String> callback) {

        PetalService service = PetalAPI.getPetalService();
        //联网的授权字段
         String authorization = "Basic MWQ5MTJjYWU0NzE0NGZhMDlkODg6Zjk0ZmNjMDliNTliNDM0OWExNDhhMjAzYWIyZjIwYzc=";

        Call<TokenBean> call = service.httpsToken(authorization,"password", name, password);
        call.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {

                if (response.code() == 200 && response.body() != null) {
                    Log.d("login", response.body().toString());

                } else {
                    callback.onError("账号或者密码不对！");
                }

            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {
                callback.onError("网络或者服务器有问题！");
            }
        });
    }

    @Override
    public void getUserInfo(String authorization) {

    }

    @Override
    public void getPinsListByType(String type, int limit, final RequestCallback<List<PinEntity>>
            callback) {

        PetalService service = PetalAPI.getPetalService();
        Call<PinsListBean>  call = service.httpsTypeLimit(" ", type, limit);
        call.enqueue(new Callback<PinsListBean>() {
            @Override
            public void onResponse(Call<PinsListBean> call, Response<PinsListBean> response) {
                if (response.code() == 200) {
                    List<PinEntity> pinEntities = response.body().pins;
                    if (pinEntities != null) {
//                        Logger.d(pinEntities);
                        callback.onSuccess(pinEntities);
                    }
                }
            }

            @Override
            public void onFailure(Call<PinsListBean> call, Throwable t) {

            }
        });
    }


}
