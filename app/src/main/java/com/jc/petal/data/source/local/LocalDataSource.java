package com.jc.petal.data.source.local;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.User;
import com.jc.petal.data.model.Weekly;
import com.jc.petal.data.source.PetalDataSource;

import android.content.Context;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import my.nouilibrary.utils.StorageUtils;

/**
 * 本地缓冲 数据源
 * Created by JC on 2016-08-06.
 */
public class LocalDataSource implements PetalDataSource {

    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "bitmap";

    private Context mContext;

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
    public void login(String name, String password, RequestCallback<User> callback) {

    }

    @Override
    public void getSelf(RequestCallback<User> callback) {

    }

    @Override
    public void getUserInfo(String authorization) {

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
//                    File file = Glide.with(mContext).load(key).into(500, 500).downloadOnly(pinEntity.file.width,
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
    public void getPinsListByType(String type, int limit, RequestCallback<List<Pin>>
            callback) {

    }

    @Override
    public void getMaxPinsListByType(String type, int max, int limit, RequestCallback<List
            <Pin>> callback) {

    }

    @Override
    public void getPin(int pinId, RequestCallback<Pin> callback) {

    }

    @Override
    public void getWeeklies(String max, RequestCallback<List<Weekly>> callback) {

    }
}
