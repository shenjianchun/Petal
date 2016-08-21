package com.jc.petal.data.source.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.jc.petal.Constants;
import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.AuthToken;
import com.jc.petal.data.model.BoardDetail;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.model.User;
import com.jc.petal.data.model.Weekly;
import com.jc.petal.data.source.PetalDataSource;
import com.jc.petal.utils.IOUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import my.nouilibrary.utils.SPUtils;
import my.nouilibrary.utils.StorageUtils;

/**
 * 本地缓冲 数据源
 * Created by JC on 2016-08-06.
 */
public class LocalDataSource implements PetalDataSource {

    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "bitmap";

    private Context mContext;

    private AuthToken mToken;

    private static LocalDataSource INSTANCE;

    private LocalDataSource(Context context) {
        mContext = context;
    }

    public static LocalDataSource getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSource(context);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void setToken(AuthToken token) {
        mToken = token;

        // 保存在本地
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SPUtils.FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(Constants.IS_LOGIN, true);
        editor.putString(Constants.TOKEN_ACCESS, token.access_token);
        editor.putString(Constants.TOKEN_REFRESH, token.refresh_token);
        editor.putString(Constants.TOKE_EXPIRES_IN, token.expires_in);
        editor.putString(Constants.TOKEN_TYPE, token.token_type);

        editor.apply();

    }

    @Override
    public AuthToken getToken() {

        boolean isLogin = (boolean) SPUtils.get(mContext, Constants.IS_LOGIN, false);

        if (isLogin) {

            mToken = new AuthToken();

            mToken.access_token = (String) SPUtils.get(mContext, Constants.TOKEN_ACCESS, "");
            mToken.refresh_token = (String) SPUtils.get(mContext, Constants.TOKEN_REFRESH, "");
            mToken.expires_in = (String) SPUtils.get(mContext, Constants.TOKE_EXPIRES_IN, "");
            mToken.token_type = (String) SPUtils.get(mContext, Constants.TOKEN_TYPE, "");

            return mToken;
        } else {
            return null;
        }
    }

    @Override
    public void login(@NonNull String name, @NonNull String password, @NonNull
    RequestCallback<AuthToken> callback) {
        if (mToken != null) {
            callback.onSuccess(mToken);
        } else {
            callback.onError("本地没有缓存");
        }
    }

    @Override
    public void getSelf(@NonNull RequestCallback<User> callback) {


    }

    @Override
    public void setSelf(User user) {
        // 保存在本地
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SPUtils.FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(Constants.IS_LOGIN, true);
        editor.putString(Constants.USER_ID, user.user_id);
        editor.putString(Constants.USER_NAME, user.username);
        editor.putString(Constants.USER_EMAIL, user.email);
        editor.putString(Constants.USER_AVATAR_KEY, user.avatar.key);

        editor.apply();
    }

    @Override
    public void getUser(final String userId, final RequestCallback<User> callback) {

    }

    @Override
    public void refreshPinsList() {

    }

    @Override
    public void refreshPinsListLocalDataSource(@NonNull PinList pins) {

        String jsonPins = new GsonBuilder().serializeNulls().create().toJson(pins);
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        StringReader stringReader = null;
        try {
//            fileOutputStream = mContext.openFileOutput("all_current", Context.MODE_PRIVATE);
//            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter = new BufferedWriter(new FileWriter(getDiskCacheDir()));
            stringReader = new StringReader(jsonPins);

//            char[] buffer = new char[1024];
            int c;
            while ( (c =  stringReader.read()) != -1) {
                bufferedWriter.write((char)c);
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeReader(stringReader);
            IOUtils.closeWriter(bufferedWriter);
        }


    }

    @Override
    public void getAllPins(@Nullable String category, int limit, @NonNull String key,
                           @Nullable String pinId, RequestCallback<PinList> callback) {

        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
//            fileInputStream = mContext.openFileInput("all_current");
//            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            bufferedReader = new BufferedReader(new FileReader(getDiskCacheDir()));
//            char[] buffer = new char[1024];
            int c;
            while ( (c = bufferedReader.read()) != -1) {
                stringBuilder.append((char)c);
            }

        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(e.toString());
        } finally {
            IOUtils.closeReader(bufferedReader);
            IOUtils.closeInputStream(fileInputStream);
        }


        Gson gson = new GsonBuilder().create();
        PinList pins = gson.fromJson(stringBuilder.toString(), PinList.class);

        if (pins != null && pins.pins != null) {
            callback.onSuccess(pins);
        } else {
            callback.onError("Local data error!");
        }

    }

    @Override
    public void getFavoritePins(@Nullable String category, int limit,
                                @NonNull String key, @Nullable String pinId,
                                RequestCallback<PinList> callback) {

    }

    public void savePinsList(List<Pin> pins) {
//
//        try {
//            DiskLruCache diskLruCache = DiskLruCache.open(getDiskCacheDir(),
//                    AppUtils.getVerCode(mContext), 1, DISK_CACHE_SIZE);
//            DiskLruCache.Editor editor;
//
//            for (PinEntity pinEntity : pins) {
//
//                String key = pinEntity.file.key;
//                editor = diskLruCache.edit(key);
//                try {
//                    File file = Glide.with(mContext).load(key).into(500, 500).downloadOnly
// (pinEntity.file.width,
//                            pinEntity.file.height).get();
//
//
//                    FileOutputStream editor.newOutputStream(0);
//                    diskLruCache.get(key).get
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } finally {
//
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    private File getDiskCacheDir() {

        return StorageUtils.getDiskCache(mContext, DISK_CACHE_SUBDIR);

    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public void deletePinsList() {


    }

    @Override
    public void getPin(int pinId, RequestCallback<Pin> callback) {

    }

    @Override
    public void getWeeklies(String max, RequestCallback<List<Weekly>> callback) {

    }

    @Override
    public void getBoard(String boardId, RequestCallback<BoardDetail> callback) {

    }

    @Override
    public void getBoardPins(String boardId, int current, int limit, RequestCallback<PinList>
            callback) {

    }

    @Override
    public void getUserBoards(String userId, RequestCallback<BoardList> callback) {

    }

    @Override
    public void getUserPins(@Nullable String userId, int limit, @NonNull String key, @Nullable
    String pinId, RequestCallback<PinList> callback) {

    }
}
