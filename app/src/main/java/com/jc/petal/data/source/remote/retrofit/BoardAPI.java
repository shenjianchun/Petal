package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constant;
import com.jc.petal.data.model.BoardDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * 画板相关 API
 * Created by JC on 2016-08-11.
 */
public interface BoardAPI {

    @GET("boards/{boardId}")
    Call<BoardDetail> getBoard(@Header(Constant.Authorization) String authorization, @Path("boardId") String boardId);
}
