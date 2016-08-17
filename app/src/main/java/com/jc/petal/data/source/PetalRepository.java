package com.jc.petal.data.source;

import com.google.common.base.Preconditions;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.AuthToken;
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

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load data from the data sources into a cache.
 * Created by JC on 2016-07-29.
 */
public class PetalRepository implements PetalDataSource {

    private static PetalRepository sInstance = null;

    private static PetalAPI mPetalAPI = new PetalAPI();

//    private AuthToken mToken;
    private User mSelf;

    private PetalDataSource mLocalDataSource;
    private PetalDataSource mRemoteDataSource;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This
     * variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    boolean mLoginIsDirty = true;

    private PetalRepository(PetalDataSource localDataSource, PetalDataSource remoteDataSource) {
        this(null, localDataSource, remoteDataSource);
    }

    private PetalRepository(Context context, PetalDataSource localDataSource, PetalDataSource
            remoteDataSource) {

        mLocalDataSource = checkNotNull(localDataSource);
        mRemoteDataSource = checkNotNull(remoteDataSource);

        fetchLocalToken();
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
                            new RetrofitRemoteDataSource());
                }
            }
        }
        return sInstance;
    }


    public void destroyInstance() {
        sInstance = null;
    }

    private void fetchLocalToken() {
        AuthToken token = getToken();
        if (token != null) {
            setToken(token);
        }
    }

    /**
     * 更新本地Access Token
     */
    @Override
    public void setToken(AuthToken token) {
        mLocalDataSource.setToken(token);
        mRemoteDataSource.setToken(token);
    }

    @Override
    public AuthToken getToken() {
        return mLocalDataSource.getToken();
    }

    public boolean isLogin() {
        return mLocalDataSource.getToken() != null;
    }

    @Override
    public void login(String name, String password, final RequestCallback<AuthToken> callback) {

        mRemoteDataSource.login(name, password, new RequestCallback<AuthToken>() {
            @Override
            public void onSuccess(AuthToken data) {

                callback.onSuccess(data);

                setToken(data);
            }

            @Override
            public void onError(String msg) {
                callback.onError(msg);
            }
        });

    }

    @Override
    public void getSelf(final RequestCallback<User> callback) {

        mRemoteDataSource.getSelf(new RequestCallback<User>() {
            @Override
            public void onSuccess(User data) {
                callback.onSuccess(data);
                // 更新本地 用户信息
                mLocalDataSource.setSelf(data);
            }

            @Override
            public void onError(String msg) {
                callback.onError(msg);
            }
        });


    }

    @Override
    public void setSelf(User user) {

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
