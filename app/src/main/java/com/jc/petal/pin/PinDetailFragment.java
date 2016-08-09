package com.jc.petal.pin;

import com.bumptech.glide.Glide;
import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.uilibrary.app.BaseFragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Use the {@link PinDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PinDetailFragment extends BaseFragment {

    private static final String ARG_PIN = "pin";

    @Bind(R.id.tv_image_description)
    TextView mDescTv;

    @Bind(R.id.tv_image_user_label)
    TextView mImageUserTv;
    @Bind(R.id.tv_image_board_title)
    TextView mImageBoardTv;
    @Bind(R.id.iv_user_avatar)
    ImageView mUserAvatarIv;

    private Pin mPin;


    public PinDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pin Parameter 1.
     * @return A new instance of fragment PinDetailFragment.
     */
    public static PinDetailFragment newInstance(Pin pin) {
        PinDetailFragment fragment = new PinDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PIN, pin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPin = getArguments().getParcelable(ARG_PIN);
        }
    }

    @Override
    protected void initViewsAndEvents() {

        mDescTv.setText(mPin.raw_text);
        mImageUserTv.setText(mPin.user.username);
        mImageBoardTv.setText(mPin.board.title);


        // avatar url
        String avatarUrl = getString(R.string.url_image_small, mPin.user.avatar.key);
        Glide.with(this).load(avatarUrl).placeholder(R.drawable.account_circle_grey_36x36)
                .fitCenter().into(mUserAvatarIv);

    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_pin_detail;
    }

}
