package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constants;
import com.jc.petal.data.model.PinList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
}
