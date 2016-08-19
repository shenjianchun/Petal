package com.jc.petal.data.source.remote.retrofit;

import com.jc.petal.Constants;
import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.AuthToken;
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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private AuthToken mToken = new AuthToken();

    public RetrofitRemoteDataSource() {
        super();
        sClient = RetrofitClient.getRetrofit();
    }

    private <T> T getServiceAPI(Class<T> clazz) {
        return sClient.create(clazz);
    }

    @Override
    public void setToken(AuthToken token) {
        mToken = token;
    }

    @Override
    public AuthToken getToken() {
        return null;
    }

    /**
     * 用户登录
     *
     * @param name     username
     * @param password password
     * @param callback 成功或失败后的回调函数
     */
    @Override
    public void login(@NonNull String name, String password, @NonNull final
    RequestCallback<AuthToken> callback) {

        Retrofit client = RetrofitClient.getRetrofit();

        OAuthAPI service = client.create(OAuthAPI.class);

        Call<AuthToken> call = service.getToken(PetalAPI.mAuthorization, Constants
                .GRANT_TYPE_PASSWORD, name, password);
        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {

                if (response.code() == 200 && response.body() != null) {

                    AuthToken token = response.body();
                    Logger.d(token);

                    callback.onSuccess(token);
                } else {
                    callback.onError("账号或者密码不对！");
                }

            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                callback.onError("网络或者服务器有问题！");
            }
        });
    }

    /**
     * 获取登陆用户的个人信息
     */
    @Override
    public void getSelf(@NonNull final RequestCallback<User> callback) {
        Retrofit client = RetrofitClient.getRetrofit();
        final UserAPI userAPI = client.create(UserAPI.class);
        userAPI.getSelf(mToken.getAccessOauth()).enqueue(new EnqueueCallback<User>(callback) {
            @Override
            protected void refreshLocal(User object) {
                super.refreshLocal(object);

//                mPetalAPI.setSelf(object);
            }
        });


    }

    @Override
    public void setSelf(User user) {

    }

    @Override
    public void getUser(final String userId, final RequestCallback<User> callback) {

        UserAPI service = getServiceAPI(UserAPI.class);
        Call<User> call = service.getUser(mToken.getAccessOauth(), userId);

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
    public void refreshPinsList() {

    }

    @Override
    public void refreshPinsListLocalDataSource(@NonNull List<Pin> pins) {

    }

    /**
     * 查询所有的采集
     *
     * @param category 目录类型
     * @param limit    个数
     * @param key      当前pins = "current" , "max"以后的pins ， "since"以前的pins
     * @param pinId    如果key = "max"，代表获取从pinId之后的最新pins；
     *                 如果key = "since"，代表获取从pinId之前的最新pins
     *                 如果key = "current", 为空
     * @param callback 回调函数
     */
    @Override
    public void getAllPins(@Nullable String category, int limit, @NonNull String key, @Nullable
    String pinId, final RequestCallback<List<Pin>> callback) {

        Map<String, String> params = new HashMap<>();
        if (key.equals(Constants.QUERY_KEY_MAX)) {
            params.put(Constants.QUERY_KEY_MAX, pinId);
        } else if (key.equals(Constants.QUERY_KEY_SINCE)) {
            params.put(Constants.QUERY_KEY_SINCE, pinId);
        }

        Retrofit client = RetrofitClient.getRetrofit();
        CategoryAPI service = client.create(CategoryAPI.class);
        service.getAllPins(mToken.getAccessOauth(), category, limit, params);

    }



    /**
     * 查询所有的采集
     *
     * @param category 目录类型
     * @param limit    个数
     * @param key      当前pins = "current" , "max"以后的pins ， "since"以前的pins
     * @param pinId    如果key = "max"，代表获取从pinId之后的最新pins；
     *                 如果key = "since"，代表获取从pinId之前的最新pins
     *                 如果key = "current", 为空
     * @param callback 回调函数
     */
    @Override
    public void getFavoritePins(@Nullable String category, int limit, @NonNull String key, @Nullable
    String pinId, final RequestCallback<List<Pin>> callback) {

        Map<String, String> params = new HashMap<>();
        if (key.equals(Constants.QUERY_KEY_MAX)) {
            params.put(Constants.QUERY_KEY_MAX, pinId);
        } else if (key.equals(Constants.QUERY_KEY_SINCE)) {
            params.put(Constants.QUERY_KEY_SINCE, pinId);
        }

        Retrofit client = RetrofitClient.getRetrofit();
        CategoryAPI service = client.create(CategoryAPI.class);
        service.getFavoritePins(mToken.getAccessOauth(), category, limit, params);

    }


    @Override
    public void getPinsListByType(@NonNull String type, int limit, @NonNull final
    RequestCallback<List<Pin>> callback) {

        Retrofit client = RetrofitClient.getRetrofit();
        CategoryAPI service = client.create(CategoryAPI.class);
        Call<PinList> call = service.httpsTypeLimit(mToken.getAccessOauth(), type, limit);
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

        Call<PinList> call = service.httpsTypeMaxLimitRx(mToken.getAccessOauth(), type, max, limit);
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

        Call<PinDetail> call = service.getPin(mToken.getAccessOauth(), String.valueOf(pinId));
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

        Call<Weeklies> call = service.getWeekies(PetalAPI.mAuthorization, max);
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
        service.getBoard(mToken.getAccessOauth(), boardId).enqueue(new EnqueueCallback<BoardDetail>
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
        HashMap<String, String> params = new HashMap<>();

//        if (key.equals(Constants.QUERY_KEY_MAX)) {
//            params.put(Constants.QUERY_KEY_MAX, pinId);
//        } else if (key.equals(Constants.QUERY_KEY_SINCE)) {
//            params.put(Constants.QUERY_KEY_SINCE, pinId);
//        }

        service.getBoardPins(mToken.getAccessOauth(), boardId, current, limit, params).enqueue
                (new EnqueueCallback<PinList>(requestCallback) {
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
        service.getUserBoards(mToken.getAccessOauth(), userId).enqueue(new EnqueueCallback<BoardList>
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
