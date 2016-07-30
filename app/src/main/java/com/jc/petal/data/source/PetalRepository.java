package com.jc.petal.data.source;

import com.jc.petal.RequestCallback;

/**
 * Concrete implementation to load data from the data sources into a cache.
 * Created by JC on 2016-07-29.
 */
public class PetalRepository implements PetalDataSource {

    private static PetalRepository sInstance = null;

    private PetalDataSource mLocalDataSource;
    private PetalDataSource mRemoteDataSource;

    private PetalRepository(PetalDataSource localDataSource, PetalDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
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

    @Override
    public void login(String name, String password, RequestCallback<String> callback) {
        mRemoteDataSource.login(name, password, callback);
    }
}
