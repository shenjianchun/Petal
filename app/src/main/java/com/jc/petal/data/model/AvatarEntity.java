package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 头像实体类
 * Created by JC on 2016-08-03.
 */
public class AvatarEntity implements Parcelable {
    public int id;
    public String farm;
    public String bucket;
    public String key;
    public String type;
    public int width;
    public int height;
    public int frames;

    @Override
    public String toString() {
        return "AvatarEntity{" +
                "id=" + id +
                ", farm='" + farm + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", frames=" + frames +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.farm);
        dest.writeString(this.bucket);
        dest.writeString(this.key);
        dest.writeString(this.type);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.frames);
    }

    public AvatarEntity() {
    }

    protected AvatarEntity(Parcel in) {
        this.id = in.readInt();
        this.farm = in.readString();
        this.bucket = in.readString();
        this.key = in.readString();
        this.type = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.frames = in.readInt();
    }

    public static final Parcelable.Creator<AvatarEntity> CREATOR = new Parcelable.Creator<AvatarEntity>() {
        @Override
        public AvatarEntity createFromParcel(Parcel source) {
            return new AvatarEntity(source);
        }

        @Override
        public AvatarEntity[] newArray(int size) {
            return new AvatarEntity[size];
        }
    };
}
