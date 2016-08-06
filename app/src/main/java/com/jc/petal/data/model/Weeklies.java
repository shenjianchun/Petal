package com.jc.petal.data.model;

import java.util.List;

/**
 * 周刊数组
 * Created by JC on 2016-08-04.
 */
public class Weeklies {

    public String imgHost;

    public String newImg;

    public List<Weekly> weeklies;

    @Override
    public String toString() {
        return "Weeklies{" +
                "imgHost='" + imgHost + '\'' +
                ", newImg='" + newImg + '\'' +
                ", weeklies=" + weeklies +
                '}';
    }
}
