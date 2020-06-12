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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

//import androidx.fragment.app.FragmentActivity;

import com.alibaba.fastjson.JSON;
import com.anding.shipvideo.R;
import com.anding.shipvideo.data.Video;
import com.anding.shipvideo.fragment.PlaybackVideoFragment;
import com.anding.shipvideo.manager.VideosManager;
import com.anding.shipvideo.player.Player;
import com.anding.shipvideo.utils.Constants;
import com.anding.shipvideo.utils.DatabaseUtils;
import com.anding.shipvideo.utils.HttpUtils;
import com.anding.shipvideo.utils.LogUtils;
import com.anding.videopalyer.Jzvd;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import static com.anding.videopalyer.Jzvd.STATE_AUTO_COMPLETE;
import static com.anding.videopalyer.Jzvd.STATE_NORMAL;
import static com.anding.videopalyer.Jzvd.STATE_PAUSE;
import static com.anding.videopalyer.Jzvd.STATE_PLAYING;
import static com.anding.videopalyer.Jzvd.STATE_PREPARED;

/**
 * Loads {@link PlaybackVideoFragment}.
 */
public class PlaybackActivity extends FragmentActivity {
    private static final int PLAY_VIDEO = 2;
    private Player mJzvdStd;
    public static final long SEEK_TIME = 20000;//快进退的间隔时间
    public boolean isChangeUrl = true;
    public static final String TAG = "PlaybackVideo";
    public static final int REQUEST_KEY = 1;
    String URI = "http://vv.video.qq.com/getinfo?vids=d3032vbxllc&platform=101001&charge=0&otype=json";
    String URIKEY = "http://vv.video.qq.com/getkey?format=2&otype=json&vt=150&vid=d3032vbxllc&ran=0\\%2E9477521511726081\\\\&charge=0&filename=d3032vbxllc.mp4&platform=11";

    public String mPicUrl = null;
    public String  mUrl= null;
    public String mTitle = null;
    //创建一个Handler
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_KEY:
                    //在这里可以进行UI操作
                    //对msg.obj进行String强制转换
                    String uri = (String) msg.obj;
                    requestKey(uri);
                    break;
                case PLAY_VIDEO:
                    String realUri = (String) msg.obj;
                    if (realUri != null && mPicUrl != null && mTitle != null) {
                        mJzvdStd.setUp(realUri
                                , mTitle);
                        Glide.with(PlaybackActivity.this).load(mPicUrl).into(mJzvdStd.thumbImageView);
                        mJzvdStd.startVideo();
                    }
                break;
                default:
                    break;
            }
        }

    };

    private void requestKey(String videouri) {
        if (videouri == null) {
            return;
        }
        HttpUtils.getInstance().getDataAsyn(URIKEY, new HttpUtils.NetWorkCallBack() {

            @Override
            public void success(Call call, Response response) throws IOException {
                if (response == null) {
                    LogUtils.d(TAG, "initVideosFromRemote response is null ");
                    return;
                }
                // 2 解析JSON数据
                String strResponse = null;
                strResponse = response.body().string();
                strResponse = strResponse.substring(0, strResponse.length() - 1);
                strResponse = strResponse.replace("QZOutputJson=", "");
                LogUtils.d(TAG, "requestKey ===>" + strResponse);
                JSONObject jsonResp = null;
                String key = null;
                String filename = null;
                String realUri = null;
                try {
                    jsonResp = new JSONObject(strResponse);
                    if (jsonResp.has("key")) {
                        key = (String) jsonResp.get("key");
                    }
                    if (jsonResp.has("filename")) {
                        filename = (String) jsonResp.get("filename");
                    }
                    realUri = videouri + filename + "?vkey=" + key;
                    LogUtils.d(TAG, "realUri ===>" + realUri);
                    Message message = new Message();
                    message.what = PLAY_VIDEO;
                    message.obj = realUri;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_playback_video);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(android.R.id.content, new PlaybackVideoFragment())
//                    .commit();
//        }
        mJzvdStd = findViewById(R.id.jz_video);
        Intent intent = getIntent();
        if (intent == null) {
            LogUtils.d(TAG,"--------------play type 1----------intent is null ---");
            return;
//            String title = intent.getStringExtra("title");
//            String uri = intent.getStringExtra("uri");
//            String pic = intent.getStringExtra("pic");
//            mJzvdStd.setUp(uri, title);
//            Glide.with(this).load(pic).into(mJzvdStd.thumbImageView);
//            mJzvdStd.startVideo();
//            mJzvdStd.gotoScreenFullscreen();
        }

        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("uri");
        mPicUrl = intent.getStringExtra("pic");

        if(!TextUtils.isEmpty(mUrl)&& mUrl.startsWith("http:")){
            isChangeUrl = false;
        }else{
            isChangeUrl = true;
        }
         if (isChangeUrl) {
            LogUtils.d(TAG,"--------------play type 2-------------");
            changeUrl();
        } else {
            LogUtils.d(TAG,"--------------play type 3-------------");
//            mJzvdStd.setUp("\n" +
//
//                    , "饺子闭眼睛");
//            Glide.with(this).load(mPicUrl).into(mJzvdStd.thumbImageView);
             mJzvdStd.setUp(mUrl
                     , mTitle);
             Glide.with(PlaybackActivity.this).load(mPicUrl).into(mJzvdStd.thumbImageView);
             mJzvdStd.startVideo();
        }
        // jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    private void changeUrl() {
        HttpUtils.getInstance().getDataAsyn(URI, new HttpUtils.NetWorkCallBack() {
            @Override
            public void success(Call call, Response response) throws IOException {
                if (response == null) {
                    LogUtils.d(TAG, "initVideosFromRemote response is null ");
                    return;
                }
                // 2 解析JSON数据
                String strResponse = null;
                strResponse = response.body().string();
                if (strResponse == null) {
                    return;
                }
                LogUtils.d(TAG, "start success==> " + strResponse);
                strResponse = strResponse.substring(0, strResponse.length() - 1);
                strResponse = strResponse.replace("QZOutputJson=", "");
                LogUtils.d(TAG, " success==> " + strResponse);
                try {
                    JSONObject jsonResp = new JSONObject(strResponse);
                    if (jsonResp.has("vl")) {
                        JSONObject vl = (JSONObject) jsonResp.get("vl");
                        JSONArray kk = (JSONArray) vl.get("vi");
                        if (kk.length() > 0) {
                            JSONObject jj = kk.getJSONObject(0);
                            JSONObject gg = jj.getJSONObject("ul");
                            JSONArray dd = (JSONArray) gg.get("ui");
                            if (dd.length() > 0) {
                                JSONObject mm = (JSONObject) dd.get(0);
                                String url = (String) mm.get("url");
                                LogUtils.d(TAG, " success== url ===> " + url);
                                Message message = new Message();
                                message.what = REQUEST_KEY;
                                message.obj = url;
                                handler.sendMessage(message);
                            }
                        }
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        Jzvd.releaseAllVideos();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Toast.makeText(this, "this key code -》 " + keyCode, Toast.LENGTH_SHORT).show();
        if (keyCode == KeyEvent.KEYCODE_BUTTON_R1) {
            //mPlaybackFragment.skipToNext();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_L1) {
            //mPlaybackFragment.skipToPrevious();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_L2) {
            //mPlaybackFragment.rewind();
        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_R2) {
            // mPlaybackFragment.fastForward();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {//ok键
            if (mJzvdStd.state == STATE_NORMAL || mJzvdStd.state == STATE_AUTO_COMPLETE) {
                mJzvdStd.startVideo();
            } else if (mJzvdStd.state == STATE_PREPARED || mJzvdStd.state == STATE_PLAYING) {
                mJzvdStd.goOnPlayOnPause();
            } else if (mJzvdStd.state == STATE_PAUSE) {
                mJzvdStd.goOnPlayOnResume();
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {//向左键
            mJzvdStd.seekToTime(-SEEK_TIME);
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {//向右键
            mJzvdStd.seekToTime(SEEK_TIME);

        }
        return super.onKeyDown(keyCode, event);
    }

}