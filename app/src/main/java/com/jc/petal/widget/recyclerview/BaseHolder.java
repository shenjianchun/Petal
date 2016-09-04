package com.jc.petal.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 基础的ViewHolder<br>
 * ViewHolder只作View的缓存,不关心数据内容
 * Created by DevWiki on 2016/5/17.
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    /**
     * 构建ViewHolder
     *
     * @param view 布局View
     */
    public BaseHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

    }



    /**
     * 获取Context实例
     *
     * @return context
     */
    protected Context getContext() {
        return itemView.getContext();
    }
}
