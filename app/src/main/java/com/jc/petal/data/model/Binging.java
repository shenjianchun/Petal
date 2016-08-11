package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Use -> UserBindings -> Binging , 用户的社交网络账户绑定类
 * Created by JC on 2016-08-04.
 */
public class Binging implements Parcelable {

    public String bind_id;
    public int user_id;
    public String service_name;
    public String auth_type;
    public UserInfo user_info;
    public int created_at;

    @Override
    public String toString() {
        return "Binging{" +
                "bind_id='" + bind_id + '\'' +
                ", user_id=" + user_id +
                ", service_name='" + service_name + '\'' +
                ", auth_type='" + auth_type + '\'' +
                ", user_info=" + user_info +
                ", created_at=" + created_at +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bind_id);
        dest.writeInt(this.user_id);
        dest.writeString(this.service_name);
        dest.writeString(this.auth_type);
        dest.writeParcelable(this.user_info, flags);
        dest.writeInt(this.created_at);
    }

    public Binging() {
    }

    protected Binging(Parcel in) {
        this.bind_id = in.readString();
        this.user_id = in.readInt();
        this.service_name = in.readString();
        this.auth_type = in.readString();
        this.user_info = in.readParcelable(UserInfo.class.getClassLoader());
        this.created_at = in.readInt();
    }

    public static final Parcelable.Creator<Binging> CREATOR = new Parcelable.Creator<Binging>() {
        @Override
        public Binging createFromParcel(Parcel source) {
            return new Binging(source);
        }

        @Override
        public Binging[] newArray(int size) {
            return new Binging[size];
        }
    };
}
