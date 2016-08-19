package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constants;
import com.jc.petal.data.model.CategoryList;
import com.jc.petal.data.model.PinList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 分类查询图片相关API
 * Created by JC on 2016-08-03.
 */
public interface CategoryAPI {


    //https//api.huaban.com/favorite/food_drink?limit=20
    // 模板类型
    @GET("favorite/{type}")
    Call<PinList> httpsTypeLimit(@Header(Constants.Authorization) String authorization,
                                 @Path("type") String type,
                                 @Query("limit") int limit);

    //    https//api.huaban.com/favorite/food_drink?max=5445324325&limit=20
//    模板类型 的后续联网 max
    @GET("favorite/{type}")
    Call<PinList> httpsTypeMaxLimitRx(@Header(Constants.Authorization) String
                                              authorization,
                                      @Path("type") String type,
                                      @Query("max") int max,
                                      @Query("limit") int limit);

    /**
     * 查询所有的采集列表
     *
     * @param authorization 授权
     * @param category      目录类型
     * @param limit         个数
     * @param params        如果key = "max"，代表获取从pinId之后的最新pins；
     *                      如果key = "since"，代表获取从pinId之前的最新pins
     *                      如果key = "current", 为空
     */
    @GET("all/{category}")
    Call<PinList> getAllPins(@Header(Constants.Authorization) String authorization,
                             @Path("category") String category,
                             @Query("limit") int limit,
                             @QueryMap Map<String, String> params);


    /**
     * 查询目录类型下的的采集列表
     *
     * @param authorization 授权
     * @param category      目录类型
     * @param limit         个数
     * @param params        如果key = "max"，代表获取从pinId之后的最新pins；
     *                      如果key = "since"，代表获取从pinId之前的最新pins
     *                      如果key = "current", 为空
     */
    @GET("favorite/{category}")
    Call<PinList> getFavoritePins(@Header(Constants.Authorization) String authorization,
                                  @Path("category") String category,
                                  @Query("limit") int limit,
                                  @QueryMap Map<String, String> params);


    /**
     * 查询所有的分类目录
     */
    @GET("categories")
    Call<CategoryList> getCatetoryList();


}
