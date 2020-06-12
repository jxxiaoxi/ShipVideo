/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.anding.shipvideo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Config;
import android.view.View;
import android.widget.ProgressBar;

import com.anding.shipvideo.BuildConfig;
import com.anding.shipvideo.R;
import com.anding.shipvideo.callback.DownloadListener;
import com.anding.shipvideo.utils.Constants;
import com.anding.shipvideo.utils.HttpUtils;
import com.anding.shipvideo.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";
    public static final int UPDATE_PRO = 1;//更新进度
    private static final int UPDATE_COMPLETE = 2;//更新完成
    boolean isNeedUpdate;//apk版本是否需要更新
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkApkVersion();
       // installApk("/storage/emulated/0/downapp.apk");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private void checkApkVersion() {
        String version = getVersionName();
        HttpUtils.getInstance().getDataAsyn(Constants.CHECK_VERSION + "=" + "1.0.0.8", new HttpUtils.NetWorkCallBack() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String strResponse = null;
                strResponse = response.body().string();
                if (strResponse == null) {
                    return;
                }
                LogUtils.d(TAG, "start success==> " + strResponse);

                try {
                    JSONObject jsonObject = new JSONObject(strResponse);
                    String result = jsonObject.optString("result");
                    String url = null;
                    LogUtils.d(TAG, "result==> " + result);
                    if("yes".equals(result)){
                        url =  jsonObject.optString("url");
                        //url =  "https://alissl.ucdl.pp.uc.cn/fs08/2019/07/05/1/110_17e4089aa3a4b819b08069681a9de74b.apk";
                    }

                    if(!TextUtils.isEmpty(url)){
                        downlodApkVersion(url);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Call call, IOException e) {
                LogUtils.d(TAG, "initVideosFromRemote failed " + e.toString());
            }
        });


    }

    /**
     * 得到当前应用版本名称的方法
     */
    public  String getVersionName(){
        // 获取packagemanager的实例
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packInfo = null;
        String version = null;
        try {
            packInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            version = packInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG,"APP VersionName : "+version);
        return version;
    }

    private void downlodApkVersion(String url) {
        LogUtils.d(TAG,"url :  "+url);
        HttpUtils.getInstance().downloadFile(url, new DownloadListener() {
            @Override
            public void start(long max) {

            }

            @Override
            public void loading(int progress) {
                LogUtils.d(TAG, "loading :  " + progress);
                Message msg = new Message();
                msg.what = UPDATE_PRO;
                msg.arg1 =  (new Double(progress)).intValue();;
                mHandler.sendMessage(msg);
            }

            @Override
            public void complete(String path) {
                LogUtils.d(TAG, "complete :  " + path);
                Message msg = new Message();
                msg.what = UPDATE_COMPLETE;
                msg.obj = path;
                mHandler.sendMessage(msg);
            }

            @Override
            public void fail(int code, String message) {

            }

            @Override
            public void loadfail(String message) {

            }
        });
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_PRO:
                    updatePro(msg.arg1);
                    break;
                case UPDATE_COMPLETE:
                    installApk((String) msg.obj);
                    mProgressDialog.cancel();
                    mProgressDialog = null;
                    break;
            }
        }
    };

    public void installApk(String url) {
        LogUtils.d(TAG,"installApk url :  "+url);
        File apkfile = new File(url);
        if (!apkfile.exists()) return;
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkUri = null;
        //判断版本是否是 7.0 及 7.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(this, "com.anding.shipvideo.provider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            apkUri = Uri.fromFile(apkfile);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        this.startActivity(intent);
    }

    public void updatePro(int progress) {
        if(mProgressDialog!= null) {
            mProgressDialog.setProgress(progress);
        }else{
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle(R.string.dialog_title);
            mProgressDialog.setMessage(getString(R.string.dialog_new_apk_download));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setProgress(progress);
            mProgressDialog.show();
        }
    }
}
