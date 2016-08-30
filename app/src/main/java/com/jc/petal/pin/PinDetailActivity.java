package com.jc.petal.pin;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jc.petal.R;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinFileEntity;
import com.jc.petal.data.source.PetalRepository;
import com.uilibrary.app.BaseActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import my.nouilibrary.utils.T;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 图片采集详情Activity
 * Created by JC on 2016-08-07.
 */
public class PinDetailActivity extends BaseActivity implements PinDetailFragment
        .OnPinDetailFragmentInteractionListener, PinContract.ActionView {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private PetalRepository mRepository;
    private PinContract.ActionPresenter mPresenter;

    private PinDetailAdapter mAdapter;

    private List<Pin> mPins;

    @Override
    protected void initViewsAndEvents() {

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setHomeButtonEnabled();

        mRepository = PetalRepository.getInstance(this);

        mPins = getIntent().getParcelableArrayListExtra("pins");
        int position = getIntent().getIntExtra("position", 0);

        // 禁止为空，为空则为异常情况
        checkNotNull(mPins);
        mAdapter = new PinDetailAdapter(getSupportFragmentManager(), mPins, mRepository);

        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                updateLikeState(mPins.get(position).liked);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        new ActionPresenterImpl(this, mRepository);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pin_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pin_detail, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_like:
                if (!mRepository.isLogin()) {
                    T.showShort(this, "请登录！");
                    break;
                }

                String pinId = mPins.get(mViewPager.getCurrentItem()).pin_id;
                boolean flag = mPins.get(mViewPager.getCurrentItem()).liked;
                mPresenter.like(pinId, !flag);

                break;

            case R.id.action_download:
                downloadFile();
                break;
            case R.id.action_share:


                AsyncTask<String, Integer, Bitmap> asyncTask = new AsyncTask<String, Integer,
                        Bitmap>
                        () {

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);

                        share(bitmap);
                    }

                    @Override
                    protected Bitmap doInBackground(String... params) {
                        Bitmap bitmap = null;
                        String url = params[0];
                        try {
                            bitmap = Glide.with(PinDetailActivity.this).load(url).asBitmap().into
                                    (Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return bitmap;
                    }
                };

                asyncTask.execute(mPins.get(mViewPager.getCurrentItem()).file.getFW554());


                break;

            default:
                break;

        }

        return true;
    }


    private void share(Bitmap bitmap) {

        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
//        share_intent.setType(mPins.get(mViewPager.getCurrentItem()).file.type);//设置分享内容的类型
        share_intent.setType("image/jpeg");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT,
                mPins.get(mViewPager.getCurrentItem()).raw_text); //添加分享内容


        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, mPins.get
                (mViewPager.getCurrentItem()).raw_text, null);

        share_intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));


        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "分享至");
        startActivityForResult(share_intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 下载照片
     */
    private void downloadFile() {

        PinFileEntity entity = mPins.get(mViewPager.getCurrentItem()).file;
        String downloadUrl = entity.getFW554();

        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, entity.key + "" +
                "." + entity.type
                .split("/")[1]);

        request.setNotificationVisibility(DownloadManager.Request
                .VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context
                .DOWNLOAD_SERVICE);
//将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);

    }

    @Override
    public void updateLikeState(boolean flag) {
        MenuItem item = mToolbar.getMenu().findItem(R.id.action_like);
        setIconDynamic(item, flag);
    }


    /**
     * 设置动态的icon图标 反向设置
     * 如果为true 显示undo图片
     * 为false 显示do图标
     * 所以传入当前状态值就可以 内部已经做判断
     */
    private void setIconDynamic(MenuItem item, boolean isLike) {
        AnimatedVectorDrawableCompat drawableCompat;
        drawableCompat = AnimatedVectorDrawableCompat.create(this,
                isLike ? R.drawable.drawable_animation_favorite_undo : R.drawable
                        .drawable_animation_favorite_do);
        item.setIcon(drawableCompat);
    }

    @Override
    public void onRepinClicked(Pin pin) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("repin_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.commit();

        RepinDialogFragment dialogFragment = RepinDialogFragment.newInstance(pin);
        new RepinPresenterImpl(dialogFragment, mRepository);

        dialogFragment.show(getSupportFragmentManager(), "repin_dialog");
    }

    @Override
    public void onLikeResult(boolean flag) {
        updateLikeState(flag);
//        T.showShort(this, "Like Success!");
    }

    @Override
    public void setPresenter(PinContract.ActionPresenter presenter) {
        mPresenter = checkNotNull(presenter);
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
