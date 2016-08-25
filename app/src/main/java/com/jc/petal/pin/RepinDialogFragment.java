package com.jc.petal.pin;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.Pin;
import com.orhanobut.logger.Logger;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import my.nouilibrary.utils.SPUtils;
import my.nouilibrary.utils.T;


/**
 * 采集Dialog
 */
public class RepinDialogFragment extends DialogFragment implements PinContract.RepinView {

    PinContract.RepinPresenter mPresenter;

    EditText mDescEt;
    TextView mWarningTv;
    Spinner mBoardTitleSpinner;


    private Context mContext;
    private Pin mPin;
//    private String mViaId;
//    private String mDesc;
//    private String[] mBoardTitleArray;

    private int mSelectPosition = 0;//默认的选中项
    private String mSelectBoardId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    public static RepinDialogFragment newInstance(Pin pin) {
        Bundle bundle = new Bundle();
//        bundle.putString(Constants.ARG_PIN_ID, pinId);
//        bundle.putString(Constants.ARG_DESCRIPTION, description);
        bundle.putParcelable(Constants.ARG_PIN, pin);
        RepinDialogFragment fragment = new RepinDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {

//            mViaId = args.getString(Constants.ARG_PIN_ID);
//            mDesc = args.getString(Constants.ARG_DESCRIPTION);
            mPin = args.getParcelable(Constants.ARG_PIN);
        }

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_title_pin));
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.dialog_repin, null);
        initView(dialogView);
        builder.setView(dialogView);

        builder.setNegativeButton(R.string.dialog_negative, null);
        builder.setPositiveButton(R.string.dialog_pin_positive, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //取出输入字符串 没有输入用hint文本作为默认值
                String input = mDescEt.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    input = mDescEt.getHint().toString();
                }


                mPresenter.repin(mPin.pin_id, mSelectBoardId, input, mPin.media_type, mPin.orig_source);

            }
        });


        String userId = (String) SPUtils.get(mContext, Constants.USER_ID, "");
        if (TextUtils.isEmpty(userId)) {
            T.showShort(mContext, "没有登录哦！");
        } else {
            mPresenter.getUserBoards(userId);
        }

        return builder.create();
    }


    //可能需要保存数据的回调 一般是按下Home键
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Logger.d();
    }

    //取消的回调
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Logger.d(dialog.toString());
    }

    private void initView(View dialogView) {
        mDescEt = ButterKnife.findById(dialogView, R.id.et_describe);
        mWarningTv = ButterKnife.findById(dialogView, R.id.tv_repin_warning);
        mBoardTitleSpinner = ButterKnife.findById(dialogView, R.id.spinner_title);

        if (!TextUtils.isEmpty(mPin.raw_text)) {
            mDescEt.setHint(mPin.raw_text);
        } else {
            mDescEt.setHint(R.string.text_image_describe_null);
        }

    }

    private void initSpinner(final List<Board> boards) {
        List<String> boardTitles = new ArrayList<>();
        for (Board board : boards) {
            boardTitles.add(board.title);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.support_simple_spinner_dropdown_item, boardTitles);
        mBoardTitleSpinner.setAdapter(adapter);
        mBoardTitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("position=" + position);
                mSelectPosition = position;
                mSelectBoardId = boards.get(position).board_id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showBoards(List<Board> boards) {
        initSpinner(boards);
    }

    @Override
    public void repinSuccess() {
        T.showShort(mContext, "采集成功！");
    }

    @Override
    public void setPresenter(PinContract.RepinPresenter presenter) {
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
}
