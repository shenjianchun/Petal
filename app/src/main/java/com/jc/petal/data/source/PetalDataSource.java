package com.jc.petal.data.source;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.User;
import com.jc.petal.data.model.Weekly;

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
    void login(String name, String password,final RequestCallback<User> callback);


    void getSelf(final RequestCallback<User> callback);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     */
    void getUser(final String userId, final RequestCallback<User> callback);

    /**
     * 根据传入类型获取图片列表
     * @param type 类型
     * @param limit 每页多少个
     */
    void getPinsListByType(String type, int limit, final RequestCallback<List<Pin>> callback);

    void getMaxPinsListByType(String type, int max, int limit, final RequestCallback<List<Pin>> callback);

    /**
     * 获取采集信息
     * @param pinId 采集ID
     */
    void getPin(int pinId, final RequestCallback<Pin> callback);

    void getWeeklies(String max , final RequestCallback<List<Weekly>> callback);


}
