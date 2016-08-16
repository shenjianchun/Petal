package com.jc.petal.data.source;

import com.google.common.base.Preconditions;

import com.jc.petal.Constants;
import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.AuthTokenBean;
import com.jc.petal.data.model.BoardDetail;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.model.User;
import com.jc.petal.data.model.Weekly;
import com.jc.petal.data.source.local.LocalDataSource;
import com.jc.petal.data.source.remote.PetalAPI;
import com.jc.petal.data.source.remote.retrofit.RetrofitRemoteDataSource;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import my.nouilibrary.utils.SPUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load data from the data sources into a cache.
 * Created by JC on 2016-07-29.
 */
public class PetalRepository implements PetalDataSource {

    private static PetalRepository sInstance = null;

    private static PetalAPI mPetalAPI  = new PetalAPI();

    private Context mContext;
    private PetalDataSource mLocalDataSource;
    private PetalDataSource mRemoteDataSource;

    private PetalRepository( PetalDataSource localDataSource, PetalDataSource remoteDataSource) {
        this(null, localDataSource, remoteDataSource);
    }

    private PetalRepository(Context context, PetalDataSource localDataSource, PetalDataSource remoteDataSource) {
        mContext = context;

        mLocalDataSource = checkNotNull(localDataSource);
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static PetalRepository getInstance(PetalDataSource localDataSource, PetalDataSource
            remoteDataSource) {

        if (sInstance == null) {
            synchronized (PetalRepository.class) {
                if (sInstance == null) {
                    sInstance = new PetalRepository(localDataSource, remoteDataSource);
                }
            }
        }
        return sInstance;
    }

    public static PetalRepository getInstance(Context context) {
        Preconditions.checkNotNull(context);

        if (sInstance == null) {
            synchronized (PetalRepository.class) {
                if (sInstance == null) {
                    sInstance = new PetalRepository(context, LocalDataSource.getInstance(context),
                            new RetrofitRemoteDataSource(mPetalAPI));
                }
            }
        }
        return sInstance;
    }


    public void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void login(String name, String password, final RequestCallback<AuthTokenBean> callback) {
        mRemoteDataSource.login(name, password, new RequestCallback<AuthTokenBean>() {
            @Override
            public void onSuccess(AuthTokenBean data) {

                callback.onSuccess(data);
                refreshAuthToken(data);
            }

            @Override
            public void onError(String msg) {
                callback.onError(msg);
            }
        });
    }

    /**
     * 更新本地Access Token
     */
    private void refreshAuthToken(AuthTokenBean token) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SPUtils.FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(Constants.IS_LOGIN, true);
        editor.putString(Constants.TOKEN_ACCESS, token.access_token);
        editor.putString(Constants.TOKEN_REFRESH, token.refresh_token);
        editor.putString(Constants.TOKE_EXPIRES_IN, token.expires_in);
        editor.putString(Constants.TOKEN_TYPE, token.token_type);

        editor.apply();
    }

    public boolean isLogin() {
        return (boolean) SPUtils.get(mContext, Constants.IS_LOGIN, false);
    }

    @Override
    public void getSelf(final RequestCallback<User> callback) {
        mRemoteDataSource.getSelf(callback);
    }

    @Override
    public void getUser(final String userId, final RequestCallback<User> callback) {
        mRemoteDataSource.getUser(userId, callback);
    }

    @Override
    public void getPinsListByType(String type, int limit, final RequestCallback<List<Pin>>
            callback) {

        mRemoteDataSource.getPinsListByType(type, limit, callback);
    }

    public void getLocalPinsList(String type, int limit, final RequestCallback<List<Pin>>
            callback) {
        mLocalDataSource.getPinsListByType(type, limit, callback);
    }

    @Override
    public void getMaxPinsListByType(String type, int max, int limit,
                                     RequestCallback<List<Pin>> callback) {
        mRemoteDataSource.getMaxPinsListByType(type, max, limit, callback);
    }

    @Override
    public void getPin(int pinId, RequestCallback<Pin> callback) {
        mRemoteDataSource.getPin(pinId, callback);
    }

    @Override
    public void getWeeklies(String max, RequestCallback<List<Weekly>> callback) {
        mRemoteDataSource.getWeeklies(max, callback);
    }

    @Override
    public void getBoard(String boardId, RequestCallback<BoardDetail> callback) {
        mRemoteDataSource.getBoard(boardId, callback);
    }

    @Override
    public void getBoardPins(String boardId, int current, int limit, RequestCallback<PinList>
            callback) {
        mRemoteDataSource.getBoardPins(boardId, current, limit, callback);
    }

    @Override
    public void getUserBoards(String userId, RequestCallback<BoardList> callback) {
        mRemoteDataSource.getUserBoards(userId, callback);
    }

}
