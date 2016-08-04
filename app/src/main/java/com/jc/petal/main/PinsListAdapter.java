package com.jc.petal.main;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.data.model.PinEntity;
import com.jc.petal.main.PinsListFragment.OnListFragmentInteractionListener;
import com.jc.petal.utils.SpannableTextUtils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import my.nouilibrary.utils.ScreenUtils;
import my.nouilibrary.utils.SizeUtils;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PinEntity} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class PinsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;

    private Context mContext;
    private final List<PinEntity> mPins;
    private final OnListFragmentInteractionListener mListener;

    public PinsListAdapter(Context context, List<PinEntity> pins,
                           OnListFragmentInteractionListener
                                   listener) {
        mContext = context;
        mPins = pins;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BASE_ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_banner,
                    parent, false);
            return new HeaderViewHolder(view);
        } else {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pins_list, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder recyclerHolder, int position) {

        if (getItemViewType(position) == BASE_ITEM_TYPE_HEADER) {
            return;
        }

        final ViewHolder holder = (ViewHolder) recyclerHolder;

        PinEntity pin = mPins.get(position);

        holder.mItem = pin;

        // 设置ImageView的宽高比
        int screenWidth = ScreenUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth - SizeUtils.dp2px(mContext, 8)) / 2;
        float scale = (float) pin.file.width / (float) pin.file.height;
        if (scale < 0.7f) {
            scale = 0.7f;
        }
        int imgHeight = (int) (imgWidth / scale);
        holder.mImageIv.setLayoutParams(new LinearLayout.LayoutParams(imgWidth, imgHeight));

        // image url
        String imageUrl = mContext.getString(R.string.url_image_general, pin.file.key);
        Glide.with(mContext).load(imageUrl).fitCenter().into(holder.mImageIv);

        if (!TextUtils.isEmpty(pin.raw_text)) {
            holder.mContentTv.setVisibility(View.VISIBLE);
            holder.mContentTv.setText(pin.raw_text);
        } else {
            holder.mContentTv.setVisibility(View.GONE);
        }

        // 采集信息
        String info = mContext.getString(R.string.collection_info, SpannableTextUtils.color(Color
                .BLACK, pin.user.urlname), SpannableTextUtils.color(Color.BLACK, pin.board.title));
        holder.mCollectionInfoTv.setText(Html.fromHtml(info));

//        holder.mCollectionInfoTv.setText(SpannableTextUtils.color(Color.BLACK, pin.user.urlname));

        // avatar url
        String avatarUrl = mContext.getString(R.string.url_image_small, pin.user.avatar.key);
        Glide.with(mContext).load(avatarUrl).placeholder(R.drawable.account_circle_grey_36x36)
                .fitCenter().into(holder.mAvatarIv);


        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPins.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return BASE_ITEM_TYPE_HEADER;
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // Header Item 横跨一行
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager
                .LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            lp.setFullSpan(holder.getLayoutPosition() == 0);
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mItemView;
        public final ImageView mImageIv;
        public final TextView mContentTv;
        public final ImageView mAvatarIv;
        public final TextView mCollectionInfoTv;
        public PinEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mItemView = view;
            mImageIv = ButterKnife.findById(view, R.id.image);
            mContentTv = ButterKnife.findById(view, R.id.content);
            mAvatarIv = ButterKnife.findById(view, R.id.iv_avatar);
            mCollectionInfoTv = ButterKnife.findById(view, R.id.tv_collection_info);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentTv.getText() + "'";
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }


    }


}
