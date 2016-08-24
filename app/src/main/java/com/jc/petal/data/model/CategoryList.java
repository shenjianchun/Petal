package com.jc.petal.data.model;

import java.util.List;

/**
 * 分类目录List Json
 * Created by JC on 2016-08-19.
 */
public class CategoryList {

    public List<Category> categories;

    @Override
    public String toString() {
        return "CategoryList{" +
                "categories=" + categories +
                '}';
    }
}
