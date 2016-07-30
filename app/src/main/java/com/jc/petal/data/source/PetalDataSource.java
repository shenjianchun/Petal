package com.jc.petal.data.source;

import com.jc.petal.RequestCallback;

/**
 * Main entry point for accessing data.
 * Created by 14110105 on 2016-07-29.
 */
public interface PetalDataSource {

    /**
     * 用户登录
     *
     * @param name     username
     * @param password password
     * @param callback 成功或失败后的回调函数
     */
    void login(String name, String password, RequestCallback<String> callback);

    /**
     * 获取用户信息
     *
     * @param authorization 登录凭证
     */
    void getUserInfo(String authorization);

}
