package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.AuthTokenBean;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinDetail;
import com.jc.petal.data.model.PinsListBean;
import com.jc.petal.data.model.User;
import com.jc.petal.data.model.Weeklies;
import com.jc.petal.data.model.Weekly;
import com.jc.petal.data.source.PetalDataSource;
import com.jc.petal.data.source.remote.PetalAPI;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Implementation of the data source that adds a latency network.
 * Created by JC on 2016-07-29.
 */
public class RetrofitRemoteDataSource extends PetalAPI implements PetalDataSource {

    private static Retrofit sClient;

    public RetrofitRemoteDataSource() {
        super();
        sClient = RetrofitClient.getRetrofit();
    }

    private Object getServiceAPI(Class<?> clazz) {
        return sClient.create(clazz);
    }

    /**
     * 用户登录
     *
     * @param name     username
     * @param password password
     * @param callback 成功或失败后的回调函数
     */
    @Override
    public void login(String name, String password, final RequestCallback<User> callback) {

        Retrofit client = RetrofitClient.getRetrofit();

        OAuthAPI service = client.create(OAuthAPI.class);
        //联网的授权字段
//        String authorization = "Basic " +
//                "MWQ5MTJjYWU0NzE0NGZhMDlkODg6Zjk0ZmNjMDliNTliNDM0OWExNDhhMjAzYWIyZjIwYzc=";

        Call<AuthTokenBean> call = service.getToken(mAuthorization, "password", name, password);
        call.enqueue(new Callback<AuthTokenBean>() {
            @Override
            public void onResponse(Call<AuthTokenBean> call, Response<AuthTokenBean> response) {

                if (response.code() == 200 && response.body() != null) {
                    // 保存用户Token
                    mToken = response.body();
                    mAccessOauth = mToken.token_type + " " + mToken.access_token;
                    Logger.d(mToken);

                    getSelf(callback);
//                    callback.onSuccess(mToken.access_token);
                } else {
                    callback.onError("账号或者密码不对！");
                }

            }

            @Override
            public void onFailure(Call<AuthTokenBean> call, Throwable t) {
                callback.onError("网络或者服务器有问题！");
            }
        });
    }

    /**
     * 获取登陆用户的个人信息
     */
    @Override
    public void getSelf(final RequestCallback<User> callback) {
        Retrofit client = RetrofitClient.getRetrofit();
        final UserAPI userAPI = client.create(UserAPI.class);
        userAPI.getSelf(mAccessOauth).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.code() == 200 && response.body() != null) {
                    User user = response.body();
                    Logger.d(user);

                    mSelf = user;
                    callback.onSuccess(user);
                } else {
                    Logger.d(response.errorBody());
                    callback.onError(String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Logger.d(t.toString());
                callback.onError("网络或者服务器有问题！");
            }
        });


    }

    @Override
    public void getUser(final String userId, final RequestCallback<User> callback) {

        UserAPI service = (UserAPI) getServiceAPI(UserAPI.class);
        Call<User> call = service.getUser(mAccessOauth, userId);

        call.enqueue(new EnqueueCallback<User>(callback) {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    @Override
    public void getPinsListByType(String type, int limit, final RequestCallback<List<Pin>>
            callback) {

        Retrofit client = RetrofitClient.getRetrofit();
        CategoryAPI service = client.create(CategoryAPI.class);
        Call<PinsListBean> call = service.httpsTypeLimit(mAccessOauth, type, limit);
        call.enqueue(new Callback<PinsListBean>() {
            @Override
            public void onResponse(Call<PinsListBean> call, Response<PinsListBean> response) {
                if (response.code() == 200) {
                    List<Pin> pinEntities = response.body().pins;
                    if (pinEntities != null) {
                        callback.onSuccess(pinEntities);
                    }
                } else {
                    Logger.d(response.errorBody());
                    callback.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<PinsListBean> call, Throwable t) {
                callback.onError(t.toString());
            }
        });
    }

    @Override
    public void getMaxPinsListByType(String type, int max, int limit,
                                     final RequestCallback<List<Pin>> callback) {
        Retrofit client = RetrofitClient.getRetrofit();
        CategoryAPI service = client.create(CategoryAPI.class);

        Call<PinsListBean> call = service.httpsTypeMaxLimitRx(mAccessOauth, type, max, limit);
        call.enqueue(new Callback<PinsListBean>() {
            @Override
            public void onResponse(Call<PinsListBean> call, Response<PinsListBean> response) {
                if (response.code() == 200) {
                    List<Pin> pinEntities = response.body().pins;
                    if (pinEntities != null) {
                        callback.onSuccess(pinEntities);
                    }
                } else {
                    Logger.d(response.errorBody());

                    callback.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<PinsListBean> call, Throwable t) {
                callback.onError(t.toString());
            }
        });
    }

    @Override
    public void getPin(int pinId, final RequestCallback<Pin> callback) {

        Retrofit retrofit = RetrofitClient.getRetrofit();
        PinAPI service = retrofit.create(PinAPI.class);

        Call<PinDetail> call = service.getPin(mAccessOauth, String.valueOf(pinId));
        call.enqueue(new Callback<PinDetail>() {
            @Override
            public void onResponse(Call<PinDetail> call, Response<PinDetail> response) {
                if (response.code() == 200 && response.body() != null) {
                    Logger.d(response.body());
                    callback.onSuccess(response.body().pin);

                } else {
                    Logger.d(response.errorBody());
                    callback.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<PinDetail> call, Throwable t) {

            }
        });
    }

    @Override
    public void getWeeklies(String max, final RequestCallback<List<Weekly>> callback) {
        Retrofit retrofit = RetrofitClient.getRetrofit();

        WeeklyAPI service = retrofit.create(WeeklyAPI.class);

        Call<Weeklies> call = service.getWeekies(mAccessOauth, max);
        call.enqueue(new Callback<Weeklies>() {
            @Override
            public void onResponse(Call<Weeklies> call, Response<Weeklies> response) {
                if (response.code() == 200 && response.body() != null) {

//                    Logger.d(response.body().weeklies);

                    callback.onSuccess(response.body().weeklies);

                } else {
                    Logger.d(response.errorBody());
                    callback.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Weeklies> call, Throwable t) {
                callback.onError(t.toString());
            }
        });

    }

    @Override
    public void getBoard(String boardId, final RequestCallback<Board> callback) {
        BoardAPI service = (BoardAPI) getServiceAPI(Board.class);
        service.getBoard(mAccessOauth, boardId).enqueue(new EnqueueCallback<Board>(callback) {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                super.onResponse(call, response);


            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }


}
