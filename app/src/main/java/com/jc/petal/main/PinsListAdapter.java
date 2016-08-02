package com.jc.petal.main;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.data.module.PinEntity;
import com.jc.petal.main.PinsListFragment.OnListFragmentInteractionListener;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PinEntity} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class PinsListAdapter extends RecyclerView.Adapter<PinsListAdapter
        .ViewHolder> {
    private Fragment mFragment;
    private final List<PinEntity> mPins;
    private final OnListFragmentInteractionListener mListener;

    public PinsListAdapter(Fragment fragment, List<PinEntity> pins,
                           OnListFragmentInteractionListener
                                   listener) {
        mFragment = fragment;
        mPins = pins;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pins_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        PinEntity pin = mPins.get(position);

        holder.mItem = pin;

        String imageUrl = "http://img.hb.aicdn.com/" + pin.file.key + "_fw320sf";

        Glide.with(mFragment).load(imageUrl).fitCenter().into(holder.mImageView);


        holder.mContentView.setText(mPins.get(position).raw_text);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mItemView;
        public final ImageView mImageView;
        public final TextView mContentView;
        public PinEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mItemView = view;
            mImageView = ButterKnife.findById(view, R.id.image);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
