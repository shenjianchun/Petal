package com.jc.petal.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 采集实体
 * Created by JC on 2016-08-01.
 */
public class Pin implements Parcelable {

    public String pin_id;
    public int user_id;
    public int board_id;
    public int file_id;

    /**
     * farm : farm1
     * bucket : hbimg
     * key : a2945bdf440f492c5144d24eebe45f23b82f3ff84d81f-4vhC2L
     * type : image/jpeg /type=image/gif
     * width : 900
     * height : 1350
     * frames : 1
     */

    public ImageFile file;


    public String media_type;
    public String source;
    public String link;
    public String raw_text;
    public int via;
    public int via_user_id;
    public int original;
    public int created_at;
    public int like_count;
    public int seq;
    public int comment_count;
    public int repin_count;
    public int is_public;
    public String orig_source;
    public boolean liked;

    public PinUserEntity user;
    public PinUserEntity via_user;
    public Board board;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pin_id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.board_id);
        dest.writeInt(this.file_id);
        dest.writeParcelable(this.file, flags);
        dest.writeString(this.media_type);
        dest.writeString(this.source);
        dest.writeString(this.link);
        dest.writeString(this.raw_text);
        dest.writeInt(this.via);
        dest.writeInt(this.via_user_id);
        dest.writeInt(this.original);
        dest.writeInt(this.created_at);
        dest.writeInt(this.like_count);
        dest.writeInt(this.seq);
        dest.writeInt(this.comment_count);
        dest.writeInt(this.repin_count);
        dest.writeInt(this.is_public);
        dest.writeString(this.orig_source);
        dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.via_user, flags);
        dest.writeParcelable(this.board, flags);
    }

    public Pin() {
    }

    protected Pin(Parcel in) {
        this.pin_id = in.readString();
        this.user_id = in.readInt();
        this.board_id = in.readInt();
        this.file_id = in.readInt();
        this.file = in.readParcelable(ImageFile.class.getClassLoader());
        this.media_type = in.readString();
        this.source = in.readString();
        this.link = in.readString();
        this.raw_text = in.readString();
        this.via = in.readInt();
        this.via_user_id = in.readInt();
        this.original = in.readInt();
        this.created_at = in.readInt();
        this.like_count = in.readInt();
        this.seq = in.readInt();
        this.comment_count = in.readInt();
        this.repin_count = in.readInt();
        this.is_public = in.readInt();
        this.orig_source = in.readString();
        this.liked = in.readByte() != 0;
        this.user = in.readParcelable(PinUserEntity.class.getClassLoader());
        this.via_user = in.readParcelable(PinUserEntity.class.getClassLoader());
        this.board = in.readParcelable(Board.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pin> CREATOR = new Parcelable.Creator<Pin>() {
        @Override
        public Pin createFromParcel(Parcel source) {
            return new Pin(source);
        }

        @Override
        public Pin[] newArray(int size) {
            return new Pin[size];
        }
    };


    @Override
    public String toString() {
        return "Pin{" +
                "pin_id=" + pin_id +
                ", user_id=" + user_id +
                ", board_id=" + board_id +
                ", file_id=" + file_id +
                ", file=" + file +
                ", media_type=" + media_type +
                ", source='" + source + '\'' +
                ", link='" + link + '\'' +
                ", raw_text='" + raw_text + '\'' +
                ", via=" + via +
                ", via_user_id=" + via_user_id +
                ", original=" + original +
                ", created_at=" + created_at +
                ", like_count=" + like_count +
                ", seq=" + seq +
                ", comment_count=" + comment_count +
                ", repin_count=" + repin_count +
                ", is_public=" + is_public +
                ", orig_source='" + orig_source + '\'' +
                ", liked=" + liked +
                ", user=" + user +
                ", via_user=" + via_user +
                ", board=" + board +
                '}';
    }
}
