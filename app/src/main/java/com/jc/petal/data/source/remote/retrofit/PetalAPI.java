package com.jc.petal.data.source.remote.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 提供PetalService对象
 * Created by 14110105 on 2016-07-29.
 */
public class PetalAPI {

    private static Retrofit sRetrofit = null;
    private static PetalService sPetalService = null;

    public static final String BASE_URL = "https://api.huaban.com/";


    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (PetalAPI.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(PetalAPI.BASE_URL)
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    public static PetalService getPetalService() {

        if (sPetalService == null) {
            synchronized (PetalAPI.class) {
                if (sPetalService == null) {
                    sPetalService = new Retrofit.Builder()
                            .baseUrl(PetalAPI.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(PetalService.class);
                }
            }
        }
        return sPetalService;

    }

}
