package com.jc.petal.data.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 采集的图片信息
 * Created by JC on  2016/05/05  21:27
 */

public class ImageFile implements Parcelable {
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

    public ImageFile() {
    }

    protected ImageFile(Parcel in) {
        this.farm = in.readString();
        this.bucket = in.readString();
        this.key = in.readString();
        this.type = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.frames = in.readInt();
    }

    public static final Creator<ImageFile> CREATOR = new Creator<ImageFile>() {
        @Override
        public ImageFile createFromParcel(Parcel source) {
            return new ImageFile(source);
        }

        @Override
        public ImageFile[] newArray(int size) {
            return new ImageFile[size];
        }
    };

    @Override
    public String toString() {
        return "PinFileEntity{" +
                "farm='" + farm + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", frames=" + frames +
                '}';
    }


    protected static final String FW192 = "_fw192";
    protected static final String FW192W = "_fw192w";
    protected static final String FW554 = "_fw554";
    protected static final String FW554W = "_fw554w";

    protected static final String SQ140 = "_sq140";
    protected static final String SQ140W = "_sq140w";
    protected static final String SQ75 = "_sq75";
    protected static final String SQ75W = "_sq75w";


    public String getSQ75()
    {
        if (Build.VERSION.SDK_INT >= 14)
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append(SQ75W).toString();
        } else
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append(SQ75).toString();
        }
    }

    public String getFW192()
    {
        if (Build.VERSION.SDK_INT >= 14)
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_fw192w").toString();
        } else
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_fw192").toString();
        }
    }

    public String getFW554()
    {
        if (Build.VERSION.SDK_INT >= 14)
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_fw554w").toString();
        } else
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_fw554").toString();
        }
    }

    public String getOrignal()
    {
        return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).toString();
    }

    public String getSq140()
    {
        if (Build.VERSION.SDK_INT >= 14)
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_sq140w").toString();
        } else
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_sq140").toString();
        }
    }

    public String getSq140Origin()
    {
        return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_sq140").toString();
    }

    public String getSquare()
    {
        if (Build.VERSION.SDK_INT >= 14)
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_sq75w").toString();
        } else
        {
            return (new StringBuilder()).append("http://img.hb.aicdn.com/").append(key).append("_sq75").toString();
        }
    }
}
