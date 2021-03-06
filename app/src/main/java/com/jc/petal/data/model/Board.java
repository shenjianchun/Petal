package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Pin实体中的Board实体
 * Created by JC on  2016/08/03
 */

public class Board implements Parcelable {
    public String board_id;
    public int user_id;
    public String title;
    public String description;
    public String category_id;
    public int seq;
    public int pin_count;
    public int follow_count;
    public int like_count;
    public int created_at;
    public int updated_at;
    public int deleting;
    public int is_public;
    public List<Pin> pins;
    public User user;

    public Board() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.board_id);
        dest.writeInt(this.user_id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.category_id);
        dest.writeInt(this.seq);
        dest.writeInt(this.pin_count);
        dest.writeInt(this.follow_count);
        dest.writeInt(this.like_count);
        dest.writeInt(this.created_at);
        dest.writeInt(this.updated_at);
        dest.writeInt(this.deleting);
        dest.writeInt(this.is_public);
        dest.writeTypedList(this.pins);
        dest.writeParcelable(this.user, flags);
    }

    protected Board(Parcel in) {
        this.board_id = in.readString();
        this.user_id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.category_id = in.readString();
        this.seq = in.readInt();
        this.pin_count = in.readInt();
        this.follow_count = in.readInt();
        this.like_count = in.readInt();
        this.created_at = in.readInt();
        this.updated_at = in.readInt();
        this.deleting = in.readInt();
        this.is_public = in.readInt();
        this.pins = in.createTypedArrayList(Pin.CREATOR);
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel source) {
            return new Board(source);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

    @Override
    public String toString() {
        return "Board{" +
                "board_id=" + board_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category_id='" + category_id + '\'' +
                ", seq=" + seq +
                ", pin_count=" + pin_count +
                ", follow_count=" + follow_count +
                ", like_count=" + like_count +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleting=" + deleting +
                ", is_public=" + is_public +
                ", pins=" + pins +
                ", user=" + user +
                '}';
    }
}
