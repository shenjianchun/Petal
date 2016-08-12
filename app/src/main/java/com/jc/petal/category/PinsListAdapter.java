package com.jc.petal.category;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.jc.petal.utils.SpannableTextUtils;

import org.apache.commons.collections4.CollectionUtils;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import my.nouilibrary.utils.ScreenUtils;
import my.nouilibrary.utils.SizeUtils;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Pin} and makes a call to the
 * specified {@link PinsListFragment.OnListFragmentInteractionListener}.
 */
public class PinsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private Context mContext;
    private final List<Pin> mPins;
    private final PinsListFragment.OnImageClickListener mImageListener;
    private final PinsListFragment.OnPinInfoClickListener mPinInfoListener;

    private View mHeaderView = null;

    public PinsListAdapter(Context context, List<Pin> pins,
                           PinsListFragment.OnImageClickListener imageListener,
                           PinsListFragment.OnPinInfoClickListener pinInfoListener) {
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
                    .inflate(R.layout.item_pins_list, parent, false);
            return new ContentViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder recyclerHolder, int position) {

        if (getItemViewType(position) == BASE_ITEM_TYPE_HEADER) {
            return;
        }

        final ContentViewHolder holder = (ContentViewHolder) recyclerHolder;

        final int realPosition = getRealPosition(recyclerHolder);

        Pin pin = mPins.get(realPosition);

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
                .BLACK, pin.user.username), SpannableTextUtils.color(Color.BLACK, pin.board.title));
        holder.mPinInfoTv.setText(Html.fromHtml(info));

//        holder.mLikeCountTv.setText(SpannableTextUtils.color(Color.BLACK, pin.user.urlname));

        // avatar url
        String avatarUrl = mContext.getString(R.string.url_image_small, pin.user.avatar.key);
        Glide.with(mContext).load(avatarUrl).placeholder(R.drawable.account_circle_grey_36x36)
                .fitCenter().into(holder.mAvatarIv);


        holder.mImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mImageListener) {
                    mImageListener.onClick(holder.mItem, realPosition);
                }
            }
        });

        holder.mPinInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPinInfoListener != null) {
                    mPinInfoListener.onClick(holder.mItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        if (CollectionUtils.isEmpty(mPins)) {
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
        @BindView(R.id.image) ImageView mImageIv;
        @BindView(R.id.tv_image_description)  TextView mContentTv;
        @BindView(R.id.iv_avatar) ImageView mAvatarIv;
        @BindView(R.id.rl_pin_info) View mPinInfoLayout;
        @BindView(R.id.tv_pin_info) TextView mPinInfoTv;
        public Pin mItem;

        public ContentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

    }


}
