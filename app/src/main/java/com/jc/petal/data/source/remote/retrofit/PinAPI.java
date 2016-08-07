package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constant;
import com.jc.petal.data.model.Pin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * 采集详情
 * Created by JC on 2016-08-07.
 */
public interface PinAPI {

    /**
     * 获取单个图片采集详情
     * @param authorization 授权
     * @param pinId id
     * @return 详情
     */
    @GET("{id}")
    Call<Pin> getPin(@Header(Constant.Authorization) String authorization, @Path("id") String pinId);

}
