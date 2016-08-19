package com.jc.petal.data.source;

/**
 * Created by 14110105 on 2016-08-19.
 */
public enum QueryKey {

    Current(1, "Current", "current"),
    Newer(1, "Newer", "since"),
    Older(1, "Older", "max");


    private int index;
    private String key;
    private String value;

    QueryKey(int index, String key, String value) {
        this.index = index;
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
