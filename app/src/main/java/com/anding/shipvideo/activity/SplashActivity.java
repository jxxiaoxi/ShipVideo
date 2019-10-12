package com.anding.shipvideo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.anding.shipvideo.R;
import com.anding.shipvideo.been.Splash;
import com.anding.shipvideo.been.Video;
import com.anding.shipvideo.utils.Constants;
import com.anding.shipvideo.utils.DatabaseUtils;
import com.anding.shipvideo.utils.HttpUtils;
import com.anding.shipvideo.utils.LogUtils;
import com.anding.shipvideo.utils.SerializableUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SplashActivity extends Activity {
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
        showAndDownSplash();
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
            Log.d("SplashDemo", "SplashActivity 获取本地序列化成功" + mSplash);
            Glide.with(this).load(mSplash.savePath).dontAnimate().into(mSpBgImage);
            startClock();
        } else {
            mSpJumpBtn.setVisibility(View.INVISIBLE);
            mSpJumpBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoMainActivity();
                }
            }, 1000);
        }
    }

    private void startImageDownLoad() {
        //    SplashDownLoadService.startDownLoadSplashImage(this, Constants.DOWNLOAD_SPLASH);
    }

    private Splash getLocalSplash() {
        Splash splash = null;
        try {
            Log.d("存储路径", Constants.SPLASH_PATH);//修改为存储到内存卡中，不需要动态申请权限
            // /data/user/0/com.example.wsj.splashdemo/files/alpha/splash
            File serializableFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH,
                    Constants.SPLASH_FILE_NAME);
            splash = (Splash) SerializableUtils.readObject(serializableFile);
        } catch (IOException e) {
            Log.d("SplashDemo", "SplashActivity 获取本地序列化闪屏失败" + e.getMessage());
        }
        return splash;
    }


    private void startClock() {
        mSpJumpBtn.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }


    private void gotoMainActivity() {
        initVideosFromRemote();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
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
                for(int i =0;i<videos.size();i++){
                    LogUtils.d(TAG,"name ==> "+videos.get(i).getVname());
                    DatabaseUtils.getInstance().insertVideo(videos.get(i));
                }
            }

            @Override
            public void failed(Call call, IOException e) {
                LogUtils.d(TAG, "initVideosFromRemote failed " + e.toString());
            }
        });

    }
}
