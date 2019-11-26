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


import static android.media.session.PlaybackState.STATE_BUFFERING;
import static android.media.session.PlaybackState.STATE_FAST_FORWARDING;
import static android.media.session.PlaybackState.STATE_PLAYING;
import static android.media.session.PlaybackState.STATE_REWINDING;
import static com.anding.videopalyer.Jzvd.STATE_NORMAL;

/**
 * Loads {@link PlaybackVideoFragment}.
 */
public class PlaybackActivity extends FragmentActivity {
    private Player mJzvdStd;

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
        mJzvdStd = (Player) findViewById(R.id.jz_video);
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String uri = intent.getStringExtra("uri");
            String pic = intent.getStringExtra("pic");
            LogUtils.d("liuwei", "title : " + title + "  ;uri:  " + uri + "  ;pic : " + pic);
            mJzvdStd.setUp(uri
                    , title);
            Glide.with(this).load(pic).into(mJzvdStd.thumbImageView);
            mJzvdStd.startVideo();
            mJzvdStd.gotoScreenFullscreen();
        } else {
            mJzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
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
            LogUtils.d("liuwei", "state --> " + mJzvdStd.state);
            if (mJzvdStd.state == STATE_NORMAL || mJzvdStd.state == STATE_BUFFERING) {
                mJzvdStd.startVideo();
            } else if (mJzvdStd.state == STATE_PLAYING || mJzvdStd.state == STATE_FAST_FORWARDING) {
                mJzvdStd.goOnPlayOnPause();
            } else if (mJzvdStd.state == STATE_REWINDING) {
                mJzvdStd.goOnPlayOnResume();
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {//向左键
            // mJzvdStd.

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {//向右键
            LogUtils.d("liuwei", "state --> " + keyCode);
            //mJzvdStd.goOnPlayOnPause();
            mJzvdStd.seekToInAdvance = 20000;
        }
//这里只有开始播放时才生效
        // mJzvdStd.seekToInAdvance = 20000;

//跳转制定位置播放


        return super.onKeyDown(keyCode, event);
    }
}