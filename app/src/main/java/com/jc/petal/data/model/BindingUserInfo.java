package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Binging -> UserInfo ， 绑定的社交网络账户的用户信息
 * Created by JC on 2016-08-04.
 */
public class BindingUserInfo implements Parcelable {

    private String id;
    private String username;
    private String email;
    /**
     * id : 45254635
     * farm : farm1
     * bucket : hbimg
     * key : cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW
     * type : image/jpeg
     * width : 180
     * height : 180
     * frames : 1
     */

    private AvatarEntity avatar;
//    private boolean verified;
//    private String verified_reason;
//    private int verified_type;
    private String location;
    private String gender;
    private String url;
    private String about;


    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", avatar=" + avatar +
//                ", verified=" + verified +
//                ", verified_reason='" + verified_reason + '\'' +
//                ", verified_type=" + verified_type +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
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
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeParcelable(this.avatar, flags);
//        dest.writeByte(this.verified ? (byte) 1 : (byte) 0);
//        dest.writeString(this.verified_reason);
//        dest.writeInt(this.verified_type);
        dest.writeString(this.location);
        dest.writeString(this.gender);
        dest.writeString(this.url);
        dest.writeString(this.about);
    }

    public BindingUserInfo() {
    }

    protected BindingUserInfo(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.avatar = in.readParcelable(AvatarEntity.class.getClassLoader());
//        this.verified = in.readByte() != 0;
//        this.verified_reason = in.readString();
//        this.verified_type = in.readInt();
        this.location = in.readString();
        this.gender = in.readString();
        this.url = in.readString();
        this.about = in.readString();
    }

    public static final Parcelable.Creator<BindingUserInfo> CREATOR = new Parcelable.Creator<BindingUserInfo>() {
        @Override
        public BindingUserInfo createFromParcel(Parcel source) {
            return new BindingUserInfo(source);
        }

        @Override
        public BindingUserInfo[] newArray(int size) {
            return new BindingUserInfo[size];
        }
    };
}
