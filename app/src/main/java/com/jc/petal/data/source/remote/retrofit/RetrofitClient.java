package com.jc.petal.data.source.remote.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 提供PetalService对象
 * Created by JC on 2016-07-29.
 */
public class RetrofitClient {

    private static Retrofit sRetrofit = null;
    private static OAuthAPI sPetalService = null;

    public static final String BASE_URL = "https://api.huaban.com/";


    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (RetrofitClient.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(RetrofitClient.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    public static Object getPetalService(Class<?> clazz) {

        return new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz);
    }

}
