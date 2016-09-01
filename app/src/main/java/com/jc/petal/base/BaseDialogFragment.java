package com.jc.petal.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * DialogFragment 基础类
 * Created by JC on 2016-08-25.
 */
public abstract class BaseDialogFragment extends DialogFragment {


    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        if (getLayoutResource() != 0) {
            return inflater.inflate(getLayoutResource(), container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        initViewsAndEvents();
    }

    /**
     * get layout resource
     * @return layout resource id
     */
    public abstract int getLayoutResource();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();


}
