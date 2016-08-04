package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constant;
import com.jc.petal.data.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * 用户信息API
 * Created by JC on 2016-08-04.
 */
public interface UserAPI {

    /**
     * 根据获取到的Token来获取用户信息
     *
     * @param authorization 格式为 token_type + " " + access_token
     * @return 用户信息
     */
    @GET("users/me")
    Call<User> getSelf(@Header(Constant.Authorization) String authorization);
}
