package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户身份信息
 * Created by JC on 2016-08-16.
 */
public class Profile implements Parcelable {

    /**
     * location : 深圳
     * sex : 1
     * birthday :
     * job : 视觉设计师
     * url : http://even0826.lofter.com
     * about :
     */

    public String location;
    public String sex;
    public String birthday;
    public String job;
    public String url;
    public String about;




    @Override
    public String toString() {
        return "Profile{" +
                "location='" + location + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", job='" + job + '\'' +
                ", url='" + url + '\'' +
                ", about='" + about + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.location);
        dest.writeString(this.sex);
        dest.writeString(this.birthday);
        dest.writeString(this.job);
        dest.writeString(this.url);
        dest.writeString(this.about);
    }

    public Profile() {
    }

    protected Profile(Parcel in) {
        this.location = in.readString();
        this.sex = in.readString();
        this.birthday = in.readString();
        this.job = in.readString();
        this.url = in.readString();
        this.about = in.readString();
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
