package com.jc.petal.data.model;

/**
 * 分类目录
 * Created by JC on 2016-08-19.
 */
public class Category {


    /**
     * id : web_app_icon
     * name : UI/UX
     * col : 1
     * nav_link : /favorite/web_app_icon/
     * urlname : web_app_icon
     */

    public String id;
    public String name;
    public int col;
    public String nav_link;
    public String urlname;

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", col=" + col +
                ", nav_link='" + nav_link + '\'' +
                ", urlname='" + urlname + '\'' +
                '}';
    }
}
