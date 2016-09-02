package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户信息 Json格式
 * Created by JC on 2016-07-30.
 */
public class User implements Parcelable {

    public String user_id;
    public String username;
    public String urlname;

    public ImageFile avatar;
    public String email;
    public int created_at;
    public int board_count;
    public int pin_count;
    public int like_count;
    public int boards_like_count;
    public int follower_count;
    public int following_count;
    public int explore_following_count;
    public int commodity_count;
    public int creations_count;

    public Profile profile;
    public UserBindings bindings;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.username);
        dest.writeString(this.urlname);
        dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.email);
        dest.writeInt(this.created_at);
        dest.writeInt(this.board_count);
        dest.writeInt(this.pin_count);
        dest.writeInt(this.like_count);
        dest.writeInt(this.boards_like_count);
        dest.writeInt(this.follower_count);
        dest.writeInt(this.following_count);
        dest.writeInt(this.explore_following_count);
        dest.writeInt(this.commodity_count);
        dest.writeInt(this.creations_count);
        dest.writeParcelable(this.profile, flags);
        dest.writeParcelable(this.bindings, flags);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.user_id = in.readString();
        this.username = in.readString();
        this.urlname = in.readString();
        this.avatar = in.readParcelable(ImageFile.class.getClassLoader());
        this.email = in.readString();
        this.created_at = in.readInt();
        this.board_count = in.readInt();
        this.pin_count = in.readInt();
        this.like_count = in.readInt();
        this.boards_like_count = in.readInt();
        this.follower_count = in.readInt();
        this.following_count = in.readInt();
        this.explore_following_count = in.readInt();
        this.commodity_count = in.readInt();
        this.creations_count = in.readInt();
        this.profile = in.readParcelable(Profile.class.getClassLoader());
        this.bindings = in.readParcelable(UserBindings.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", urlname='" + urlname + '\'' +
                ", avatar=" + avatar +
                ", email='" + email + '\'' +
                ", created_at=" + created_at +
                ", board_count=" + board_count +
                ", pin_count=" + pin_count +
                ", like_count=" + like_count +
                ", boards_like_count=" + boards_like_count +
                ", follower_count=" + follower_count +
                ", following_count=" + following_count +
                ", explore_following_count=" + explore_following_count +
                ", commodity_count=" + commodity_count +
                ", creations_count=" + creations_count +
                ", profile=" + profile +
                ", bindings=" + bindings +
                '}';
    }
}
