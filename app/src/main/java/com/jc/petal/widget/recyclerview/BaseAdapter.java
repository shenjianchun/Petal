package com.jc.petal.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础的Adapter
 * <p>
 * Created by DevWiki on 2016/7/13.
 */

public abstract class BaseAdapter<M, VH extends BaseHolder> extends RecyclerView.Adapter<VH> {

    private List<M> dataList;

    public BaseAdapter(Context context) {
        this.dataList = new ArrayList<>();
    }

    public BaseAdapter(Context context, List<M> list) {
        this.dataList = list;
    }

    /**
     * 根据位置获取一条数据
     *
     * @param position View的位置
     * @return 数据
     */
    public M getItem(int position) {
        return dataList.get(position);
    }

    /**
     * 根据ViewHolder获取数据
     *
     * @param holder ViewHolder
     * @return 数据
     */
    public M getItem(VH holder) {
        return getItem(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindData(holder, position);
        onBindListener(holder, position);
    }

    protected abstract void onBindData(VH holder, int position);

    protected abstract void onBindListener(VH holder, int position);


    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

}
