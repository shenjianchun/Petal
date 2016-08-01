package com.jc.petal.data.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LiCola on  2016/05/05  21:27
 */

public class PinsFileEntity implements Parcelable {
    public String farm;
    public String bucket;
    public String key;
    public String type;
    public int width;
    public int height;
    public int frames;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.farm);
        dest.writeString(this.bucket);
        dest.writeString(this.key);
        dest.writeString(this.type);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.frames);
    }

    public PinsFileEntity() {
    }

    protected PinsFileEntity(Parcel in) {
        this.farm = in.readString();
        this.bucket = in.readString();
        this.key = in.readString();
        this.type = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.frames = in.readInt();
    }

    public static final Creator<PinsFileEntity> CREATOR = new Creator<PinsFileEntity>() {
        @Override
        public PinsFileEntity createFromParcel(Parcel source) {
            return new PinsFileEntity(source);
        }

        @Override
        public PinsFileEntity[] newArray(int size) {
            return new PinsFileEntity[size];
        }
    };
}
