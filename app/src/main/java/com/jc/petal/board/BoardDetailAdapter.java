package com.jc.petal.board;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.category.CategoryPinListFragment;
import com.jc.petal.data.model.Pin;
import com.jc.petal.utils.CompatUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
 *
 * 画板详情界面中的RecyclerView对应的Adapter
 *
 */
public class BoardDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private Context mContext;
    private final List<Pin> mPins;
    private final CategoryPinListFragment.OnImageClickListener mImageListener;
    private final CategoryPinListFragment.OnPinInfoClickListener mPinInfoListener;

    private View mHeaderView = null;

    public BoardDetailAdapter(Context context, List<Pin> pins,
                              CategoryPinListFragment.OnImageClickListener imageListener,
                              CategoryPinListFragment.OnPinInfoClickListener pinInfoListener) {
        mContext = context;
        mPins = pins;
        mImageListener = imageListener;
        mPinInfoListener = pinInfoListener;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public int getRealPosition(final RecyclerView.ViewHolder viewHolder) {

        int position = viewHolder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == BASE_ITEM_TYPE_HEADER) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_banner,
//                    parent, false);

            return new HeaderViewHolder(mHeaderView);
        } else {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_board_detail_pins_list, parent, false);

            ContentViewHolder holder = new  ContentViewHolder(view);

            holder.mLikeCountTv.setCompoundDrawablesWithIntrinsicBounds(
                    CompatUtils.getTintListDrawable(mContext, R.drawable.ic_favorite_black_18dp, R.color.tint_list_grey),
                    null,
                    null,
                    null);
            holder.mPinCountTv.setCompoundDrawablesWithIntrinsicBounds(
                    CompatUtils.getTintListDrawable(mContext, R.drawable.ic_camera_black_18dp, R.color.tint_list_grey),
                    null,
                    null,
                    null);

            return holder;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder recyclerHolder, int position) {

        if (getItemViewType(position) == BASE_ITEM_TYPE_HEADER) {
            return;
        }

        ContentViewHolder holder = (ContentViewHolder) recyclerHolder;

        int realPosition = getRealPosition(recyclerHolder);
        Pin pin = mPins.get(realPosition);
        holder.mItem = pin;

        onBindData(holder, pin);

        onBindListener(holder, realPosition);
    }


    private void onBindData(final ContentViewHolder holder, Pin pin) {

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

        holder.mPinCountTv.setText(String.valueOf(pin.repin_count));
        holder.mLikeCountTv.setText(String.valueOf(pin.like_count));
    }


    private void onBindListener(final ContentViewHolder holder, final int position) {

        holder.mImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mImageListener) {
//                    mImageListener.onClick(holder.mItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (mPins == null) {
            return 0;
        }

        return mHeaderView == null ? mPins.size() : mPins.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderView != null && position == 0) {
            return BASE_ITEM_TYPE_HEADER;
        }

        return super.getItemViewType(position);
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // Header Item 横跨一行
        if (mHeaderView != null) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager
                    .LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager
                        .LayoutParams) layoutParams;
                lp.setFullSpan(holder.getLayoutPosition() == 0);
            }
        }

    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        public final View mItemView;
        public final ImageView mImageIv;
        public final TextView mContentTv;

        public final TextView mPinCountTv;
        public final TextView mLikeCountTv;
        public Pin mItem;

        public ContentViewHolder(View view) {
            super(view);
            mItemView = view;
            mImageIv = ButterKnife.findById(view, R.id.image);
            mContentTv = ButterKnife.findById(view, R.id.tv_image_description);
            mPinCountTv = ButterKnife.findById(view, R.id.tv_pin_count);
            mLikeCountTv = ButterKnife.findById(view, R.id.tv_like_count);
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
