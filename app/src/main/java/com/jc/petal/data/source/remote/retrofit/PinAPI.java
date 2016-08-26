package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constants;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinDetail;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 采集详情
 * Created by JC on 2016-08-07.
 */
public interface PinAPI {

    /**
     * 获取单个图片采集详情
     *
     * @param authorization 授权
     * @param pinId         id
     * @return 详情
     */
    @GET("pins/{id}")
    Call<PinDetail> getPin(@Header(Constants.Authorization) String authorization, @Path("id")
    String pinId);

    /**
     * 采集别人采集过的图片
     *
     * @param authorization 授权
     * @param viaPinId      别人采集图片id
     * @param boardId       选择的画板id
     * @param text          描述
     * @param mediaType     类型
     * @param origSource    原始来源
     */
    @FormUrlEncoded
    @POST("pins/")
    Call<Pin> repin(@Header(Constants.Authorization) String authorization,
                    @Field("via") String viaPinId,
                    @Field("board_id") String boardId,
                    @Field("text") String text,
                    @Field("media_type") String mediaType,
                    @Field("video") String origSource);

//    @FormUrlEncoded
    @POST("pins/{pinId}/like/")
    Call<Pin> like(@Header(Constants.Authorization) String authorization,
                   @Path("pinId") String pinId);

//    @FormUrlEncoded
    @POST("pins/{pinId}/unlike/")
    Call<Pin> unlike(@Header(Constants.Authorization) String authorization,
                   @Path("pinId") String pinId);
}
