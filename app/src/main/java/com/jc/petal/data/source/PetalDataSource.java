package com.jc.petal.data.source;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.module.PinEntity;

import java.util.List;

/**
 * Main entry point for accessing data.
 * Created by JC on 2016-07-29.
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

    /**
     * 根据传入类型获取图片列表
     * @param type 类型
     * @param limit 每页多少个
     */
    void getPinsListByType(String type, int limit, final RequestCallback<List<PinEntity>> callback);

    void getMaxPinsListByType(String type, int max, int limit, final RequestCallback<List<PinEntity>> callback);

}
