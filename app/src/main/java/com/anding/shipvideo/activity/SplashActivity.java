package com.anding.shipvideo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.anding.shipvideo.R;
import com.anding.shipvideo.data.CategorySub;
import com.anding.shipvideo.data.Splash;
import com.anding.shipvideo.data.Video;
import com.anding.shipvideo.manager.VideosManager;
import com.anding.shipvideo.utils.Constants;
import com.anding.shipvideo.utils.DatabaseUtils;
import com.anding.shipvideo.utils.HttpUtils;
import com.anding.shipvideo.utils.LogUtils;
import com.anding.shipvideo.utils.NetWorkUtils;
import com.anding.shipvideo.utils.SerializableUtils;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class SplashActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";

    private Splash mSplash;
    ImageView mSpBgImage;
    Button mSpJumpBtn;


    //由于CountDownTimer有一定的延迟，所以这里设置3400
    private CountDownTimer countDownTimer = new CountDownTimer(3400, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSpJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mSpJumpBtn.setText("跳过(" + 0 + "s)");
            gotoMainActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSpBgImage = findViewById(R.id.sp_bg);
        mSpJumpBtn = findViewById(R.id.sp_jump_btn);
        mSpJumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initVideosFromRemote();
        List<CategorySub> categorySubs = DatabaseUtils.getInstance().queryAllCategorySub();
        if (categorySubs != null && categorySubs.size() > 0) {
            DatabaseUtils.getInstance().deleteAllCategorySubs();
        }
        initCategorySub(Constants.SERVER_HY_CATEGORY_URL);
        initCategorySub(Constants.SERVER_AQ_CATEGORY_URL);
        initCategorySub(Constants.SERVER_GZ_CATEGORY_URL);
        initCategorySub(Constants.SERVER_ZH_CATEGORY_URL);
        showAndDownSplash();
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sp_bg:
                // gotoWebActivity();
                break;
            case R.id.sp_jump_btn:
                gotoMainActivity();
                break;
        }
    }

    private void gotoWebActivity() {

//        if (mSplash != null && mSplash.click_url != null) {
//            Intent intent = new Intent(this, WebActivity.class);
//            intent.putExtra("url", mSplash.click_url);
//            intent.putExtra("title", mSplash.title);
//            intent.putExtra("fromSplash", true);
//            startActivity(intent);
//            finish();
        //       }
    }

    private void showAndDownSplash() {
        showSplash();
        startImageDownLoad();
    }

    private void showSplash() {
        mSplash = getLocalSplash();
        if (mSplash != null && !TextUtils.isEmpty(mSplash.savePath)) {
            LogUtils.d(TAG, "SplashActivity 获取本地序列化成功" + mSplash);
            Glide.with(this).load(mSplash.savePath).dontAnimate().into(mSpBgImage);
            startClock();
        } else {
            mSpJumpBtn.setVisibility(View.INVISIBLE);
            mSpJumpBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!NetWorkUtils.isNetworkAvailable()) {
                        Toast.makeText(SplashActivity.this, "您的电视盒子没有联网，程序暂时没法使用，请先联网", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        gotoMainActivity();
                    }
                }
            }, 4000);
        }
    }

    private void startImageDownLoad() {
        //    SplashDownLoadService.startDownLoadSplashImage(this, Constants.DOWNLOAD_SPLASH);
    }

    private Splash getLocalSplash() {
        Splash splash = null;
        try {
            LogUtils.d("存储路径", Constants.SPLASH_PATH);//修改为存储到内存卡中，不需要动态申请权限
            // /data/user/0/com.example.wsj.splashdemo/files/alpha/splash
            File serializableFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH,
                    Constants.SPLASH_FILE_NAME);
            splash = (Splash) SerializableUtils.readObject(serializableFile);
        } catch (IOException e) {
            LogUtils.d("SplashDemo", "SplashActivity 获取本地序列化闪屏失败" + e.getMessage());
        }
        return splash;
    }


    private void startClock() {
        mSpJumpBtn.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }


    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    /*
     * 从服务器拉取所有的视频资源
     * */
    private void initVideosFromRemote() {
        //判断网络是否可用
//        if (!HttpUtils.isNetworkAvailable(this)) {
//            showNeedNetWorkDialog();
//            return;
//        }
        HttpUtils.getInstance().getDataAsyn(Constants.SERVER_URL, new HttpUtils.NetWorkCallBack() {
            @Override
            public void success(Call call, Response response) throws IOException {
                if (response == null) {
                    LogUtils.d(TAG, "initVideosFromRemote response is null ");
                    return;
                }
                // 2 解析JSON数据
                String videosStr = response.body().string();
                LogUtils.d(TAG, "initVideosFromRemote success==> " + videosStr);
                List<Video> videos = JSON.parseArray(videosStr, Video.class);
                VideosManager.getInstance().deleteAllVideos();
//                ArrayList<Video> videos = new ArrayList<>();
//                for (int j = 0; j < videos.size(); j++) {
//                    videos.add(new Video("http://1257476497.vod2.myqcloud.com/d11999f6vodcq1257476497/f8a941f95285890794792703672/wbbPyeKk43UA.mp4", "船舶安全视频" + j,"这是关于船舶安全介绍"+j,"http://132.232.111.161/storage/video/0.jpg", 0));
//                }
//                VideosManager.getInstance().insertVideos(videos);
                for (int i = 0; i < videos.size(); i++) {
                    LogUtils.d(TAG, "视频名字 ==> " + videos.get(i).getVname());
                    DatabaseUtils.getInstance().insertVideo(videos.get(i));
                }
            }

            @Override
            public void failed(Call call, IOException e) {
                LogUtils.d(TAG, "initVideosFromRemote failed " + e.toString());
                Toast.makeText(SplashActivity.this, "视频获取失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initCategorySub(String url) {
        HttpUtils.getInstance().getDataAsyn(url, new HttpUtils.NetWorkCallBack() {
            @Override
            public void success(Call call, Response response) throws IOException {
                if (response == null) {
                    LogUtils.d(TAG, "initHYCategorySub response is null ");
                    return;
                }
                // 2 解析JSON数据
                String categorySubsStr = response.body().string();
                LogUtils.d(TAG, "initHYCategorySub success==----------> " + categorySubsStr);
                List<CategorySub> categorySubs = JSON.parseArray(categorySubsStr, CategorySub.class);
                //DatabaseUtils.getInstance().deleteAllCategorySubs();
                DatabaseUtils.getInstance().insertCategorySubs(categorySubs);
                for (int i = 0; i < categorySubs.size(); i++) {
                    LogUtils.d(TAG, "二级分类 ==> " + categorySubs.get(i).getLabel());
                    //DatabaseUtils.getInstance().insertVideo(categorySubs.get(i));
                }
            }

            @Override
            public void failed(Call call, IOException e) {
                LogUtils.d(TAG, "initVideosFromRemote failed " + e.toString());
            }
        });

    }


    private void showNeedNetWorkDialog() {
//        new XPopup.Builder(this).asConfirm(getString(R.string.no_network_title),
//                getString(R.string.no_network_content), getString(R.string.cancal), getString(R.string.confirm),
//                new OnConfirmListener() {
//                    @Override
//                    public void onConfirm() {
//                        showToast("确定");
//                    }
//                }, new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
//                        showToast("取消");
//                    }
//                }, false)
//                .show();
    }
}
