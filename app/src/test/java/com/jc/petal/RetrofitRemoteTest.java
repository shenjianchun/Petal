package com.jc.petal;

import com.jc.petal.data.model.User;
import com.jc.petal.data.source.remote.retrofit.RetrofitRemoteDataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by JC on 2016-08-11.
 */
public class RetrofitRemoteTest {

    private RetrofitRemoteDataSource mRetrofitDataSource;

    @Before
    public void setup() {
        mRetrofitDataSource = new RetrofitRemoteDataSource();
    }

    @Test
    public void login_isSuccess() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        mRetrofitDataSource.login("shenjianchun@gmail.com", "880212", new RequestCallback<User>() {
            @Override
            public void onSuccess(User data) {
                signal.countDown();
                System.out.println(data);
                Assert.assertNotNull(data);
            }

            @Override
            public void onError(String msg) {
                signal.countDown();
                Assert.fail();
            }
        });

        signal.await();

    }


    @After
    public void destroy() {
        mRetrofitDataSource = null;
    }

}
