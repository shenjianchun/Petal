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

    private String id;
    private String name;
    private int col;
    private String nav_link;
    private String urlname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getNav_link() {
        return nav_link;
    }

    public void setNav_link(String nav_link) {
        this.nav_link = nav_link;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }
}
