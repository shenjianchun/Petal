package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constant;
import com.jc.petal.data.module.AuthTokenBean;
import com.jc.petal.data.module.UserBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 花瓣网对应的API接口
 * Created by JC on 2016-07-29.
 */
public interface OAuthAPI {

    /**
     * 获取用户登录的Token
     * Header：Authorization 报头一个固定的值
     * Content：grant_type=password&password=密码&username=账号
     *
     * @param authorization 一个固定值
     * @param grant         固定值 password
     * @param username      用户名
     * @param password      用户密码
     * @return 登录Token
     */
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Call<AuthTokenBean> httpsToken(@Header(Constant.Authorization) String authorization,
                                   @Field("grant_type") String grant,
                                   @Field("username") String username,
                                   @Field("password") String password);


    /**
     * 根据获取到的Token来获取用户信息
     *
     * @param authorization 格式为 token_type + " " + access_token
     * @return 用户信息
     */
    @GET("users/me")
    Call<UserBean> getUserInfo(@Header(Constant.Authorization) String authorization);

}
