package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constant;
import com.jc.petal.data.model.Weeklies;
import com.jc.petal.data.model.Weekly;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 花瓣周刊API
 * Created by JC on 2016-08-04.
 */
public interface WeeklyAPI {

    @GET("weekly")
    Call<Weeklies> getWeekies(@Header(Constant.Authorization) String authorization,
                              @Query("max") String max);

    @GET("weekly/{number}")
    Call<Weekly> getWeeklyDetail(@Header(Constant.Authorization) String authorization,
                                 @Path("number") String number);

}
