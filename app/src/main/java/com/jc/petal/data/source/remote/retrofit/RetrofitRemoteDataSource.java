package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constants;
import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.AuthTokenBean;
import com.jc.petal.data.model.BoardDetail;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinDetail;
import com.jc.petal.data.model.PinList;
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
public class RetrofitRemoteDataSource implements PetalDataSource {

    private static Retrofit sClient;

    private final PetalAPI mPetalAPI;

    public RetrofitRemoteDataSource(PetalAPI api) {
        super();
        sClient = RetrofitClient.getRetrofit();
        mPetalAPI = api;
    }

    private <T> T getServiceAPI(Class<T> clazz) {
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
    public void login(String name, String password, final RequestCallback<AuthTokenBean> callback) {

        Retrofit client = RetrofitClient.getRetrofit();

        OAuthAPI service = client.create(OAuthAPI.class);

        Call<AuthTokenBean> call = service.getToken(mPetalAPI.getAuthorization(), Constants
                .GRANT_TYPE_PASSWORD, name, password);
        call.enqueue(new Callback<AuthTokenBean>() {
            @Override
            public void onResponse(Call<AuthTokenBean> call, Response<AuthTokenBean> response) {

                if (response.code() == 200 && response.body() != null) {

                    AuthTokenBean token = response.body();
                    // 保存用户Token
                    mPetalAPI.setToken(token);

                    String accessOauth = token.token_type +  " " + token.access_token;
                    mPetalAPI.setAccessOauth(accessOauth);

                    Logger.d(token);

                    callback.onSuccess(token);
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
        userAPI.getSelf(mPetalAPI.getAccessOauth()).enqueue(new EnqueueCallback<User>(callback) {
            @Override
            protected void refreshLocal(User object) {
                super.refreshLocal(object);

                mPetalAPI.setSelf(object);
            }
        });


    }

    @Override
    public void getUser(final String userId, final RequestCallback<User> callback) {

        UserAPI service = getServiceAPI(UserAPI.class);
        Call<User> call = service.getUser(mPetalAPI.getAccessOauth(), userId);

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
        Call<PinList> call = service.httpsTypeLimit(mPetalAPI.getAccessOauth(), type, limit);
        call.enqueue(new Callback<PinList>() {
            @Override
            public void onResponse(Call<PinList> call, Response<PinList> response) {
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
            public void onFailure(Call<PinList> call, Throwable t) {
                callback.onError(t.toString());
            }
        });
    }

    @Override
    public void getMaxPinsListByType(String type, int max, int limit,
                                     final RequestCallback<List<Pin>> callback) {
        Retrofit client = RetrofitClient.getRetrofit();
        CategoryAPI service = client.create(CategoryAPI.class);

        Call<PinList> call = service.httpsTypeMaxLimitRx(mPetalAPI.getAccessOauth(), type, max, limit);
        call.enqueue(new Callback<PinList>() {
            @Override
            public void onResponse(Call<PinList> call, Response<PinList> response) {
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
            public void onFailure(Call<PinList> call, Throwable t) {
                callback.onError(t.toString());
            }
        });
    }

    @Override
    public void getPin(int pinId, final RequestCallback<Pin> callback) {

        Retrofit retrofit = RetrofitClient.getRetrofit();
        PinAPI service = retrofit.create(PinAPI.class);

        Call<PinDetail> call = service.getPin(mPetalAPI.getAccessOauth(), String.valueOf(pinId));
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

        WeeklyAPI service = getServiceAPI(WeeklyAPI.class);

        Call<Weeklies> call = service.getWeekies(mPetalAPI.getAccessOauth(), max);
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
    public void getBoard(String boardId, final RequestCallback<BoardDetail> requestCallback) {
        BoardAPI service = getServiceAPI(BoardAPI.class);
        service.getBoard(mPetalAPI.getAccessOauth(), boardId).enqueue(new EnqueueCallback<BoardDetail>
                (requestCallback) {
            @Override
            public void onResponse(Call<BoardDetail> call, Response<BoardDetail> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<BoardDetail> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    @Override
    public void getBoardPins(String boardId, int current, int limit, RequestCallback<PinList>
            requestCallback) {
        BoardAPI service = getServiceAPI(BoardAPI.class);
        service.getBoardPins(mPetalAPI.getAccessOauth(), boardId, current, limit).enqueue(new EnqueueCallback<PinList>(requestCallback) {
            @Override
            public void onResponse(Call<PinList> call, Response<PinList> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<PinList> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    @Override
    public void getUserBoards(String userId, RequestCallback<BoardList> callback) {
        UserAPI service = getServiceAPI(UserAPI.class);
        service.getUserBoards(mPetalAPI.getAccessOauth(), userId).enqueue(new EnqueueCallback<BoardList>
                (callback) {
            @Override
            public void onResponse(Call<BoardList> call, Response<BoardList> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<BoardList> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }


}
