package com.jc.petal.pin;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.board.BoardDetailActivity;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.PetalRepository;
import com.jc.petal.user.UserActivity;
import com.uilibrary.app.BaseFragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.nouilibrary.utils.ScreenUtils;
import my.nouilibrary.utils.T;

/**
 * Use the {@link PinDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PinDetailFragment extends BaseFragment implements PinContract.PinDetailView {

    private static final String ARG_PIN = "pin";

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.iv_header)
    ImageView mImageIv;
    @BindView(R.id.tv_image_description)
    TextView mDescTv;
    @BindView(R.id.fab_repin)
    FloatingActionButton mFloatingButton;

    private PetalRepository mRepository;
    private PinContract.PinDetailPresenter mPresenter;

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

        mRepository = PetalRepository.getInstance(getActivity().getApplicationContext());
        mPresenter = new PinDetailPresenterImpl(this, mRepository);

        mDescTv.setText(mPin.raw_text);

        loadImage();

        // owner
        View ownerLayout = ButterKnife.findById(getView(), R.id.include_owner);
        initUserInfo(ownerLayout);

        View viaLayout = ButterKnife.findById(getView(), R.id.include_via);
        // via_user为空说明没有采集来源者的信息
        if (mPin.via_user != null) {
            // via
            initUserInfo(viaLayout);
        } else {
            viaLayout.setVisibility(View.GONE);
        }

        // 获取采集详情
        mPresenter.getPin(mPin.pin_id);
    }

    /**
     * 加载图片
     */
    private void loadImage() {

        // 设置ImageView的宽高比
        int imgWidth = ScreenUtils.getScreenWidth(getContext());
        float scale = (float) mPin.file.width / (float) mPin.file.height;
        if (scale < 0.7f) {
            scale = 0.7f;
        }
        int imgHeight = (int) (imgWidth / scale);
        ViewGroup.LayoutParams layoutParams =
                mAppBarLayout.getLayoutParams();
//        layoutParams.width = imgWidth;
        layoutParams.height = imgHeight;
        mAppBarLayout.setLayoutParams(layoutParams);

        String imageUrl;

        if (mPin.file.type.toLowerCase().contains("gif")) {

            imageUrl = mPin.file.getFW554();

            Glide.with(this).load(imageUrl).asGif().error(R.drawable
                    .account_circle_grey_36x36).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop().into(mImageIv);
        } else {

            imageUrl = mPin.file.getFW192();

            Glide.with(this).load(imageUrl).placeholder(R.drawable.account_circle_grey_36x36)
                    .fitCenter().into(mImageIv);
        }

    }

    /**
     * 加载用户信息
     */
    private void initUserInfo(View layout) {

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ARG_USER_ID, String.valueOf(mPin.user_id));
                readyGo(UserActivity.class, bundle);
            }
        });

        TextView actionTv = ButterKnife.findById(layout, R.id.tv_action);
        // 用户名字
        TextView imageUserTv = ButterKnife.findById(layout, R.id.tv_image_user);
        // 用户头像
        ImageView userAvatarIv = ButterKnife.findById(layout, R.id.iv_user_avatar);

        if (layout.getId() == R.id.include_owner) {
            actionTv.setText(R.string.who);
            imageUserTv.setText(mPin.user.username);

            String avatarUrl = getString(R.string.url_image_small, mPin.user.avatar.key);
            Glide.with(this).load(avatarUrl).placeholder(R.drawable.account_circle_grey_36x36)
                    .fitCenter().into(userAvatarIv);

        } else if (layout.getId() == R.id.include_via) {
            actionTv.setText(R.string.from);
            imageUserTv.setText(mPin.via_user.username);

            String avatarUrl = getString(R.string.url_image_small, mPin.via_user.avatar.key);
            Glide.with(this).load(avatarUrl).placeholder(R.drawable.account_circle_grey_36x36)
                    .fitCenter().into(userAvatarIv);
        }

    }

    /**
     * 初始化采集画板信息
     */
    private void initBoardInfo(final Pin pin) {

        if (getView() != null) {

            View boardLayout = ButterKnife.findById(getView(), R.id.include_to);

            TextView boardTv = ButterKnife.findById(boardLayout, R.id.tv_image_board_title);
            boardTv.setText(mPin.board.title);

            int[] boardIds = {R.id.iv_board_1, R.id.iv_board_2, R.id.iv_board_3, R.id.iv_board_4};
            int picSize = pin.board.pins.size() > 4 ? 4 : pin.board.pins.size();
            for (int i = 0; i < picSize; i++) {
                ImageView boardIv = ButterKnife.findById(boardLayout, boardIds[i]);
                String avatarUrl = getString(R.string.url_image_small, pin.board.pins.get(i).file
                        .key);
                Glide.with(this).load(avatarUrl).placeholder(android.R.drawable.ic_secure)
                        .fitCenter().into(boardIv);
            }

            boardLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ARG_BOARD_ID, String.valueOf(pin.board_id));
                    readyGo(BoardDetailActivity.class, bundle);
                }
            });
        }

    }


    @OnClick(R.id.fab_repin)
    public void onClick() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("repin_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.commit();

        RepinDialogFragment dialogFragment = RepinDialogFragment.newInstance(mPin);
        new RepinPresenterImpl(dialogFragment, mRepository);

        dialogFragment.show(getFragmentManager(), "repin_dialog");

    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_pin_detail;
    }

    @Override
    public void setPresenter(PinContract.PinDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showPinInfo(Pin pin) {
        initBoardInfo(pin);
    }

    @Override
    public void likeSuccess() {
        T.showShort(getContext(), "喜欢成功！");
    }
}
