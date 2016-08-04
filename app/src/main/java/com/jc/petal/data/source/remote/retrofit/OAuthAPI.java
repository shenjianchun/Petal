package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constant;
import com.jc.petal.data.model.AuthTokenBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 花瓣网对应的API接口
 * Created by JC on 2016-07-29.
 */
public interface OAuthAPI {

    /**
     * 获取通话AuthToken
     * Header：Authorization 报头一个固定的值,{@link com.jc.petal.data.source.remote.PetalAPI} 中的
     *         mAuthorization
     * Content：grant_type=password&password=密码&username=账号
     *
     * @param authorization 一个固定值
     * @param grant         固定值 "password"
     * @param username      用户名
     * @param password      用户密码
     * @return 登录Token
     */
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Call<AuthTokenBean> getToken(@Header(Constant.Authorization) String authorization,
                                 @Field("grant_type") String grant,
                                 @Field("username") String username,
                                 @Field("password") String password);

}
