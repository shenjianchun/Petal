package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.model.UserList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 搜索相关API
 * Created by JC on 2016-08-31.
 */
public interface SearchAPI {

    /**
     * 查询采集图片
     *
     * @param query   关键字
     * @param limit 每页个数
     * @param page    当前页
     * @return 采集列表
     */
    @GET("search")
    Call<PinList> searchPins(@Query("q") String query,
                             @Query("per_page") int limit,
                             @Query("page") String page);


    @GET("search/boards")
    Call<BoardList> searchBoards(@Query("q") String query,
                                 @Query("per_page") int limit,
                                 @Query("page") String page);

    @GET("search/people")
    Call<UserList> searchUsers(@Query("q") String query,
                               @Query("per_page") int limit,
                               @Query("page") String page);

}
