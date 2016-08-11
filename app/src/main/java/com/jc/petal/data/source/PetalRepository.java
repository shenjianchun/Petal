package com.jc.petal.data.source;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.User;
import com.jc.petal.data.model.Weekly;
import com.jc.petal.data.source.local.LocalDataSource;
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

    private PetalDataSource mLocalDataSource;
    private PetalDataSource mRemoteDataSource;

    private PetalRepository(PetalDataSource localDataSource, PetalDataSource remoteDataSource) {

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

        if (sInstance == null) {
            synchronized (PetalRepository.class) {
                if (sInstance == null) {
                    sInstance = new PetalRepository(LocalDataSource.getInstance(context),
                            new RetrofitRemoteDataSource());
                }
            }
        }
        return sInstance;
    }


    public void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void login(String name, String password, final RequestCallback<User> callback) {
        mRemoteDataSource.login(name, password, callback);
    }

    @Override
    public void getSelf(final RequestCallback<User> callback) {

        mRemoteDataSource.getSelf(callback);
    }

    @Override
    public void getUser(final String userId, final RequestCallback<User> callback) {

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

}
