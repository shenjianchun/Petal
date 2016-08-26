package com.jc.petal.data.source;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.AuthToken;
import com.jc.petal.data.model.BoardDetail;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.model.CategoryList;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.model.User;
import com.jc.petal.data.model.Weekly;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Main entry point for accessing data.
 * Created by JC on 2016-07-29.
 */
public interface PetalDataSource {

    void setToken(AuthToken token);

    AuthToken getToken();

    /**
     * 用户登录
     *
     * @param name     username
     * @param password password
     * @param callback 成功或失败后的回调函数
     */
    void login(@NonNull String name, String password, @NonNull final RequestCallback<AuthToken>
            callback);


    void getSelf(@NonNull final RequestCallback<User> callback);

    void setSelf(User user);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     */
    void getUser(final String userId, final RequestCallback<User> callback);


    void refreshPinsList();

    void refreshPinsListLocalDataSource(@NonNull PinList pins);

    /**
     * 查询所有的采集
     *
     * @param category 目录类型
     * @param limit    个数
     * @param key      当前pins = "current" , "max"以后的pins ， "since"以前的pins
     * @param pinId    如果key = "max"，代表获取从pinId之后的最新pins；
     *                 如果key = "since"，代表获取从pinId之前的最新pins
     *                 如果key = "current", 为空
     * @param callback 回调函数
     */
    void getAllPins(@Nullable String category, int limit, @NonNull String key, @Nullable String
            pinId, final RequestCallback<PinList> callback);

    /**
     * 查询受欢迎的采集列表
     *
     * @param category 目录类型
     * @param limit    个数
     * @param key      当前pins = "current" , "max"以后的pins ， "since"以前的pins
     * @param pinId    如果key = "max"，代表获取从pinId之后的最新pins；
     *                 如果key = "since"，代表获取从pinId之前的最新pins
     *                 如果key = "current", 为空
     * @param callback 回调函数
     */
    void getFavoritePins(@Nullable String category, int limit, @NonNull String key,
                         @Nullable String pinId, final RequestCallback<PinList> callback);


    /**
     * 采集别人采集过的图片
     * @param viaPinId 别人采集图片id
     * @param boardId  选择的画板id
     * @param desc    描述
     * @param mediaType 类型
     * @param origSource 原始来源
     * @param callback 回调函数
     */
    void repin(@NonNull String viaPinId, @Nullable String boardId, String desc,
               String mediaType, String origSource, final RequestCallback<Pin> callback);

    /**
     *
     * @param pinId 采集id
     * @param flag like = tre, unlike = false;
     * @param callback 回调函数
     */
    void like(@NonNull String pinId, boolean flag, @NonNull final RequestCallback<Pin> callback);

    /**
     * 获取采集信息
     *
     * @param pinId 采集ID
     */
    void getPin(String pinId, final RequestCallback<Pin> callback);

    /**
     * 获取周刊
     */
    void getWeeklies(String max, RequestCallback<List<Weekly>> callback);

    /**
     * 获取画板信息
     */
    void getBoard(String boardId, RequestCallback<BoardDetail> callback);

    /**
     * 获取画板包含的采集图片列表
     *
     * @param boardId  画板ID
     * @param current  当前页
     * @param limit    一页多少个
     * @param callback 回调函数
     */
    void getBoardPins(String boardId, int current, int limit, RequestCallback<PinList> callback);

    /**
     * 获取用户拥有的画板集合
     *
     * @param userId   用户ID
     * @param callback 回调函数
     */
    void getUserBoards(String userId, RequestCallback<BoardList> callback);

    /**
     * 查询用户的采集列表
     *
     * @param userId   用户ID
     * @param limit    个数
     * @param key      当前pins = "current" , "max"以后的pins ， "since"以前的pins
     * @param pinId    如果key = "max"，代表获取从pinId之后的最新pins；
     *                 如果key = "since"，代表获取从pinId之前的最新pins
     *                 如果key = "current", 为空
     * @param callback 回调函数
     */
    void getUserPins(@Nullable String userId, int limit, @NonNull String key, @Nullable String
            pinId, final RequestCallback<PinList> callback);

    /**
     * 查询用户赞过的采集列表
     *
     * @param userId   用户ID
     * @param limit    个数
     * @param key      当前pins = "current" , "max"以后的pins ， "since"以前的pins
     * @param pinId    如果key = "max"，代表获取从pinId之后的最新pins；
     *                 如果key = "since"，代表获取从pinId之前的最新pins
     *                 如果key = "current", 为空
     * @param callback 回调函数
     */
    void getUserLikes(@Nullable String userId, int limit, @NonNull String key, @Nullable String
            pinId, final RequestCallback<PinList> callback);


    /**
     * 获取目录分类列表
     *
     * @param callback 回调函数
     */
    void getCategoryList(final RequestCallback<CategoryList> callback);
}
