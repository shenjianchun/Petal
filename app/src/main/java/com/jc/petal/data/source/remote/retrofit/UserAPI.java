package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constants;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Call<User> getSelf(@Header(Constants.Authorization) String authorization);

    @GET("users/{userId}")
    Call<User> getUser(@Header(Constants.Authorization) String authorization, @Path("userId")
    String userId);

    @GET("users/{userId}/boards")
    Call<BoardList> getUserBoards(@Header(Constants.Authorization) String authorization, @Path
            ("userId") String userId);

    /**
     * 查询用户的采集列表
     *
     * @param authorization 授权
     * @param userId        用户ID
     * @param limit         个数
     * @param params        如果key = "max"，代表获取从pinId之后的最新pins；
     *                      如果key = "since"，代表获取从pinId之前的最新pins
     *                      如果key = "current", 为空
     */
    @GET("users/{userId}/pins")
    Call<PinList> getUserPins(@Header(Constants.Authorization) String authorization,
                              @Path("userId") String userId,
                              @Query("limit") int limit,
                              @QueryMap Map<String, String> params);

    /**
     * 查询用户赞过的采集列表
     *
     * @param authorization 授权
     * @param userId        用户ID
     * @param limit         个数
     * @param params        如果key = "max"，代表获取从pinId之后的最新pins；
     *                      如果key = "since"，代表获取从pinId之前的最新pins
     *                      如果key = "current", 为空
     */
    @GET("users/{userId}/likes")
    Call<PinList> getUserLikes(@Header(Constants.Authorization) String authorization,
                               @Path("userId") String userId,
                               @Query("limit") int limit,
                               @QueryMap Map<String, String> params);

    /**
     * 查询用户的Following的用户列表
     *
     * @param authorization 授权
     * @param userId        用户ID
     * @param limit         个数
     * @param params        如果key = "max"，代表获取从pinId之后的最新pins；
     *                      如果key = "since"，代表获取从pinId之前的最新pins
     *                      如果key = "current", 为空
     */
    @GET("users/{userId}/following")
    Call<PinList> getUserFollows(@Header(Constants.Authorization) String authorization,
                                 @Path("userId") String userId,
                                 @Query("limit") int limit,
                                 @QueryMap Map<String, String> params);
}
