package com.jc.petal.search;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.data.model.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.OnClickListener;


/**
 * Created by LiCola on  2016/03/22  18:00
 * 负责展示  图片CardView 的adapter
 */
public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolderGeneral> {

    private Context mContext;
    private List<User> mUsers;

    private OnAdapterListener mListener;


    public interface OnAdapterListener {
        void onClickUser(User user, View view);
    }

    public UsersRecyclerAdapter(Context context, List<User> users) {
        mContext = context;
        mUsers = users;
    }

    public void setOnClickItemListener(OnAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ViewHolderGeneral onCreateViewHolder(ViewGroup parent, int viewType) {
        //Logger.d(life);
        ViewHolderGeneral holder;//ViewHolder的子类

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_user, parent, false);
        holder = new ViewHolderGeneral(view);   //使用子类初始化ViewHolder

//        holder.ibtn_image_user_chevron_right.setImageDrawable(
//                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_chevron_right_black_36dp, R.color.tint_list_grey));

        return holder;
    }


    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolderGeneral holder, int position) {
        User user = mUsers.get(position);

        onBindData(holder, user);
        onBindListener(holder, user);
    }

    private void onBindData(final ViewHolderGeneral holder, User user) {

        holder.imageUserTv.setText(user.username);

        if (user.avatar != null) {

            Glide.with(mContext).load(user.avatar.getSquare()).placeholder(R.drawable.account_circle_grey_36x36)
                    .fitCenter().into(holder.userAvatarIv);
        }
    }

    private void onBindListener(ViewHolderGeneral holder, final User bean) {
        holder.mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickUser(bean, v);
                }
            }
        });

    }

    public static class ViewHolderGeneral extends RecyclerView.ViewHolder {
        //这个CardView采用两层操作
        public final View mView;

        @BindView(R.id.tv_image_user)
        TextView imageUserTv;
        @BindView(R.id.iv_user_avatar)
        ImageView userAvatarIv;


        public ViewHolderGeneral(View view) {
            super(view);
            mView = view;

            ButterKnife.bind(this, view);
        }

    }


}
