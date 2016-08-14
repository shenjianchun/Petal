package com.jc.petal.board;

import com.jc.petal.R;
import com.jc.petal.data.model.Board;

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

    private final List<Board> mBoards;

    public BoardListAdapter(@NonNull List<Board> items) {
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
