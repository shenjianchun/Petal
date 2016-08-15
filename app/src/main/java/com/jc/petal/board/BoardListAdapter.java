package com.jc.petal.board;

import com.bumptech.glide.Glide;
import com.jc.petal.Constant;
import com.jc.petal.R;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.Pin;

import org.apache.commons.collections4.CollectionUtils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User board list adapter
 */
public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder> {
    private Context mContext;
    private final List<Board> mBoards;

    public BoardListAdapter(@NonNull Context context, @NonNull List<Board> items) {
        mContext = context;
        mBoards = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Board board = mBoards.get(position);

        holder.mDesTv.setText(board.description);


        List<Pin> pins = board.pins;

        onBindData(holder, pins);
        onBindListener(holder, position);

    }

    private void onBindData(final ViewHolder holder, final List<Pin> pins) {

        if (!CollectionUtils.isEmpty(pins)) {

            for (int i = 0; i < pins.size(); i++) {

                Pin pin = pins.get(i);
                String imageUrl = mContext.getString(R.string.url_image_general, pin.file.key);

                ImageView target;

                switch (i) {

                    case 0:
                        target = holder.mMainIv;
                        break;
                    case 1:
                        target = holder.mBottomIV0;
                        break;
                    case 2:
                        target = holder.mBottomIV1;
                        break;
                    case 3:
                        target = holder.mBottomIV2;
                        break;
                    case 4:
                        target = holder.mBottomIV3;
                        break;
                    default:
                        target = holder.mMainIv;
                        break;
                }

                Glide.with(mContext).load(imageUrl).centerCrop().into(target);

                if (i == 4) {
                    break;
                }
            }

        }
    }


    private void onBindListener(final ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.ARG_BOARD_ID, String.valueOf(mBoards.get(position).board_id));
                intent.setClass(mContext, BoardDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBoards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_des)
        TextView mDesTv;
        @BindView(R.id.iv_main)
        ImageView mMainIv;
        @BindView(R.id.iv_user_board_bottom_0)
        ImageView mBottomIV0;
        @BindView(R.id.iv_user_board_bottom_1)
        ImageView mBottomIV1;
        @BindView(R.id.iv_user_board_bottom_2)
        ImageView mBottomIV2;
        @BindView(R.id.iv_user_board_bottom_3)
        ImageView mBottomIV3;
        @BindView(R.id.btn_follow)
        Button mFollowBtn;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
