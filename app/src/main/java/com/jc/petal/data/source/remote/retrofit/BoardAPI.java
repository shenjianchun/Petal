package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constants;
import com.jc.petal.data.model.BoardDetail;
import com.jc.petal.data.model.PinList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 画板相关 API
 * Created by JC on 2016-08-11.
 */
public interface BoardAPI {

    @GET("boards/{boardId}")
    Call<BoardDetail> getBoard(@Header(Constants.Authorization) String authorization,
                               @Path("boardId") String boardId);

    @GET("boards/{boardId}/pins")
    Call<PinList> getBoardPins(@Header(Constants.Authorization) String authorization,
                               @Path("boardId") String boardId,
                               @Query("current") int current,
                               @Query("limit") int limit);
}
