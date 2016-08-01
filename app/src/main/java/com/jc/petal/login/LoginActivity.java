package com.jc.petal.login;

import com.jc.petal.R;
import com.jc.petal.data.source.PetalRepository;
import com.uilibrary.app.BaseActivity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Login Activity
 * Created by JC on 2016-07-29.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    @Bind(R.id.et_username)
    EditText mUsernameEt;
    @Bind(R.id.et_password)
    EditText mPasswordEt;

    @Override
    protected void initViewsAndEvents() {

        setHomeButtonEnabled();

        new LoginPresenter(this, PetalRepository.getInstance());

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {

        attemptLogin();

    }


    private void attemptLogin() {

        mUsernameEt.setError(null);
        mPasswordEt.setError(null);

        String username = mUsernameEt.getText().toString();
        String password = mPasswordEt.getText().toString();

        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(username)) {
            mUsernameEt.setError(getString(R.string.error_field_required_name));
            focusView = mUsernameEt;
            cancel = true;
        }

        if (!TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            mPasswordEt.setError(getString(R.string.error_field_required_name));
            focusView = mPasswordEt;
            cancel = true;
        }



        if (cancel) {
            focusView.requestFocus();

        } else {
            mPresenter.login(username, password);
        }

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
