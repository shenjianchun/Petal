package com.jc.petal;

import com.jc.petal.data.model.AuthToken;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.remote.retrofit.RetrofitRemoteDataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.concurrent.CountDownLatch;

/**
 * Created by JC on 2016-08-11.
 */
//@RunWith(MockitoJUnitRunner.class)
public class RetrofitRemoteTest {

//    @Mock
    private RetrofitRemoteDataSource mRetrofitDataSource;

    @Captor
    private ArgumentCaptor<RequestCallback<AuthToken>> mCallbackCaptor;

    private AuthToken mAuthToken = new AuthToken();

    @Before
    public void setup() {
        mRetrofitDataSource = new RetrofitRemoteDataSource();
//        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void login_isSuccess() throws InterruptedException {

        final CountDownLatch signal = new CountDownLatch(1);

        mRetrofitDataSource.login("shenjianchun@gmail.com", "880212", new RequestCallback<AuthToken>() {

            @Override
            public void onSuccess(AuthToken data) {
                Assert.assertNotNull(data);
                signal.countDown();
            }

            @Override
            public void onError(String msg) {
                Assert.fail();
                signal.countDown();
            }
        });

        signal.await();

//        mCallbackCaptor.getValue().onSuccess(mAuthToken);

    }

    @Test
    public void pin_getPin() throws InterruptedException {

//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.huaban.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        try {
//             Response response = retrofit.create(PinAPI.class).getPin("","829493065").execute();
//            System.out.println(response.code());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        final CountDownLatch signal = new CountDownLatch(1);

        mRetrofitDataSource.getPin("829493065", new RequestCallback<Pin>() {
            @Override
            public void onSuccess(Pin data) {
                Assert.assertNotNull(data);
                signal.countDown();
            }

            @Override
            public void onError(String msg) {
                Assert.fail();
                signal.countDown();
            }
        });

        signal.await();

    }


    @After
    public void destroy() {
        mRetrofitDataSource = null;
    }

}
