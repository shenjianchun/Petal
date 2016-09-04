package com.jc.petal.category;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.jc.petal.utils.SpannableTextUtils;
import com.jc.petal.widget.recyclerview.BaseAdapter;
import com.jc.petal.widget.recyclerview.BaseHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
 * specified {@link CategoryPinListFragment.OnListFragmentInteractionListener}.
 */
public class CategoryPinListAdapter2 extends BaseAdapter<Pin, CategoryPinListAdapter2
        .ContentViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private Context mContext;

    public CategoryPinListAdapter2(Context context, List<Pin> list) {
        super(context, list);
    }


    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    private OnItemClickListener mClickListener;

    @Override
    public ContentViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pins_list, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void bindCustomViewHolder(ContentViewHolder holder, int position) {

        onBindData(holder, position);
    }

    public interface OnItemClickListener {
        void onImageClick(View itemView, int position);

        void onPinInfoClick(View itemView, int position);
    }



    public void setClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder recyclerHolder, int position) {
//
//        if (getItemViewType(position) == BASE_ITEM_TYPE_HEADER) {
//            return;
//        }
//
//        final ContentViewHolder holder = (ContentViewHolder) recyclerHolder;
//
//        final int realPosition = getRealPosition(recyclerHolder);
//
//        Pin pin = mPins.get(realPosition);
//
//        onBindData(holder, pin);
//    }

    private void onBindData(ContentViewHolder holder, int  position) {

        Pin pin = getItem(position);

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

        Glide.with(mContext).load(pin.file.getFW192()).fitCenter().into(holder.mImageIv);

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
        if (pin.user.avatar != null) {

            Glide.with(mContext).load(pin.user.avatar.getSquare()).placeholder(R.drawable
                    .account_circle_grey_36x36)
                    .fitCenter().into(holder.mAvatarIv);
        }

    }


    public class ContentViewHolder extends BaseHolder {
        @BindView(R.id.image)
        ImageView mImageIv;
        @BindView(R.id.tv_image_description)
        TextView mContentTv;
        @BindView(R.id.iv_avatar)
        ImageView mAvatarIv;
        @BindView(R.id.rl_pin_info)
        View mPinInfoLayout;
        @BindView(R.id.tv_pin_info)
        TextView mPinInfoTv;
        public Pin mItem;

        public ContentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            mImageIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onImageClick(itemView, getLayoutPosition());
                    }
                }
            });

            mPinInfoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onPinInfoClick(itemView, getAdapterPosition());
                    }
                }
            });

        }
    }

}
