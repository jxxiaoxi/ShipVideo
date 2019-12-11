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
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

//import androidx.fragment.app.FragmentActivity;

import com.anding.shipvideo.R;
import com.anding.shipvideo.fragment.PlaybackVideoFragment;
import com.anding.shipvideo.player.Player;
import com.anding.shipvideo.utils.LogUtils;
import com.anding.videopalyer.Jzvd;
import com.bumptech.glide.Glide;


import static com.anding.videopalyer.Jzvd.STATE_AUTO_COMPLETE;
import static com.anding.videopalyer.Jzvd.STATE_NORMAL;
import static com.anding.videopalyer.Jzvd.STATE_PAUSE;
import static com.anding.videopalyer.Jzvd.STATE_PLAYING;
import static com.anding.videopalyer.Jzvd.STATE_PREPARED;

/**
 * Loads {@link PlaybackVideoFragment}.
 */
public class PlaybackActivity extends FragmentActivity {
    private Player mJzvdStd;
    public static final long SEEK_TIME = 20000;//快进退的间隔时间

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
        mJzvdStd =  findViewById(R.id.jz_video);
        Intent intent = getIntent();
        if (intent == null) {
            String title = intent.getStringExtra("title");
            String uri = intent.getStringExtra("uri");
            String pic = intent.getStringExtra("pic");
            LogUtils.d("liuwei", "title : " + title + "  ;uri:  " + uri + "  ;pic : " + pic);
            mJzvdStd.setUp(uri
                    , title);
            Glide.with(this).load(pic).into(mJzvdStd.thumbImageView);
            mJzvdStd.startVideo();
            //mJzvdStd.gotoScreenFullscreen();+
        } else {
            mJzvdStd.setUp("\n" +
                            "http://180.153.119.11/vhot2.qqvideo.tc.qq.com/AbhcsTY0HQmqakmg8DXC8OXiSLntF9vObyZCmauthmt8/uwMROfz2r5zAoaQXGdGnC2dfhzkrvqBxjTHFA2hC4m2Yr1g7/d3032vbxllc.mp4?vkey=86741CA0D63DBD2F3BE0F0EB4E51F5C79CBBA8199949247C3FE3B730922D463A7D0A498090C3A885770A123D9A1A0B056C65E80D648E0CDD1DA0B9FDEFD4A489B87998E26D11F37AC3F85374F83F19B0B14DDBF6CB65EEED28E0A96562F437516192AB1436DAD3C578B4762488F3B450"
                    , "饺子闭眼睛");
            Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(mJzvdStd.thumbImageView);
        }
        // jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    @Override
    protected void onResume() {
        super.onResume();

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