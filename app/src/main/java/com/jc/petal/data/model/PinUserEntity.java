package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Pin实体中的User实体
 * Created by JC on 2016-08-03.
 */
public class PinUserEntity implements Parcelable {
    public String user_id;
    public String username;
    public String urlname;
    public int created_at;
    public AvatarEntity avatar;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.username);
        dest.writeString(this.urlname);
        dest.writeInt(this.created_at);
        dest.writeParcelable(this.avatar, flags);
    }

    public PinUserEntity() {
    }

    protected PinUserEntity(Parcel in) {
        this.user_id = in.readString();
        this.username = in.readString();
        this.urlname = in.readString();
        this.created_at = in.readInt();
        this.avatar = in.readParcelable(AvatarEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<PinUserEntity> CREATOR = new Parcelable
            .Creator<PinUserEntity>() {
        @Override
        public PinUserEntity createFromParcel(Parcel source) {
            return new PinUserEntity(source);
        }

        @Override
        public PinUserEntity[] newArray(int size) {
            return new PinUserEntity[size];
        }
    };

    @Override
    public String toString() {
        return "PinUserEntity{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", urlname='" + urlname + '\'' +
                ", created_at=" + created_at +
                ", avatar=" + avatar +
                '}';
    }
}
