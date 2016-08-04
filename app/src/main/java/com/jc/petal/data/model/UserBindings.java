package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户绑定过的社交账户集合
 * Created by JC on 2016-08-04.
 */
public class UserBindings implements Parcelable {
    /**
     * bind_id : weibo-1581635801
     * user_id : 14015812
     * service_name : weibo
     * auth_type : oauth2
     * user_info : {"id":1581635801,"username":"我看着你在笑","email":"","avatar":{"id":45254635,
     * "farm":"farm1","bucket":"hbimg",
     * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
     * "width":180,"height":180,"frames":1},"verified":false,"verified_reason":"",
     * "verified_type":-1,"location":"上海 闵行区","gender":"m","url":"http://blog.sina.com
     * .cn/siobhan","about":"后知后觉"}
     * created_at : 1403098595
     */

    public Binging weibo;

    public Binging tqq;

    public Binging qzone;

    public Binging douban;

    public Binging renren;


    @Override
    public String toString() {
        return "UserBindings{" +
                "weibo=" + weibo +
                ", tqq=" + tqq +
                ", qzone=" + qzone +
                ", douban=" + douban +
                ", renren=" + renren +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.weibo, flags);
        dest.writeParcelable(this.tqq, flags);
        dest.writeParcelable(this.qzone, flags);
        dest.writeParcelable(this.douban, flags);
        dest.writeParcelable(this.renren, flags);
    }

    public UserBindings() {
    }

    protected UserBindings(Parcel in) {
        this.weibo = in.readParcelable(Binging.class.getClassLoader());
        this.tqq = in.readParcelable(Binging.class.getClassLoader());
        this.qzone = in.readParcelable(Binging.class.getClassLoader());
        this.douban = in.readParcelable(Binging.class.getClassLoader());
        this.renren = in.readParcelable(Binging.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserBindings> CREATOR = new Parcelable
            .Creator<UserBindings>() {
        @Override
        public UserBindings createFromParcel(Parcel source) {
            return new UserBindings(source);
        }

        @Override
        public UserBindings[] newArray(int size) {
            return new UserBindings[size];
        }
    };
}
