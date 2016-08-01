package com.jc.petal.data.module;

/**
 * Created by 14110105 on 2016-08-01.
 */
public class PinEntity {

    public int pin_id;
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

    public PinsFileEntity file;


    public int media_type;
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
}
