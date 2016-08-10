package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Pin实体中的Board实体
 * Created by JC on  2016/08/03
 */

public class PinBoardEntity implements Parcelable {
    public int board_id;
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

    public PinBoardEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.board_id);
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
    }

    protected PinBoardEntity(Parcel in) {
        this.board_id = in.readInt();
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
    }

    public static final Creator<PinBoardEntity> CREATOR = new Creator<PinBoardEntity>() {
        @Override
        public PinBoardEntity createFromParcel(Parcel source) {
            return new PinBoardEntity(source);
        }

        @Override
        public PinBoardEntity[] newArray(int size) {
            return new PinBoardEntity[size];
        }
    };

    @Override
    public String toString() {
        return "PinBoardEntity{" +
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
                '}';
    }
}
