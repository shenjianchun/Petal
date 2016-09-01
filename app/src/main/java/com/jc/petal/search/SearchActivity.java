package com.jc.petal.search;

import com.jc.petal.Constants;
import com.jc.petal.R;
import com.uilibrary.app.BaseActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import butterknife.BindView;

/**
 * 搜索结果界面
 * Created by JC on 2016-08-31.
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.actv_search)
    AutoCompleteTextView mAutoCompleteTv;

    @Override
    protected void initViewsAndEvents() {

        setHomeButtonEnabled();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:

                String searchKey = mAutoCompleteTv.getText().toString();
                if (!TextUtils.isEmpty(searchKey)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ARG_SEARCH_KEY, searchKey);
                    readyGo(SearchResultActivity.class, bundle);
                }

                break;

        }

        return true;
    }
}
