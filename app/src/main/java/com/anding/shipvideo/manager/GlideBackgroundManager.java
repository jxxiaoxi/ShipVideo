package com.anding.shipvideo.manager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.anding.shipvideo.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 管理app背景
 */
public class GlideBackgroundManager {

    private static final String TAG = GlideBackgroundManager.class.getSimpleName();

    private static int BACKGROUND_UPDATE_DELAY = 500;
    private final int DEFAULT_BACKGROUND_RES_ID = R.drawable.app_bg;
    private static Drawable mDefaultBackground;
    // Handler attached with main thread
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private Activity mActivity;
    private BackgroundManager mBackgroundManager = null;
    private DisplayMetrics mMetrics;
    private URI mBackgroundURI;
    private GlideBackgroundManagerTarget mBackgroundTarget;

    Timer mBackgroundTimer; // null when no UpdateBackgroundTask is running.

    public GlideBackgroundManager(Activity activity) {
        mActivity = activity;
        mDefaultBackground = activity.getDrawable(DEFAULT_BACKGROUND_RES_ID);
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        mBackgroundTarget = new GlideBackgroundManagerTarget(mBackgroundManager);
        mBackgroundManager.setDrawable(mDefaultBackground);
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

    }

    /**
     * if UpdateBackgroundTask is already running, cancel this task and start new task.
     */
    private void startBackgroundTimer() {
        if (mBackgroundTimer != null) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        /* set delay time to reduce too much background image loading process */
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }


    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            /* Here is TimerTask thread, may not be UI thread */
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    /* Here is main (UI) thread */
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI);
                    }
                }
            });
        }
    }

    public void updateBackgroundWithDelay(String url) {
        try {
            URI uri = new URI(url);
            updateBackgroundWithDelay(uri);
        } catch (URISyntaxException e) {
            /* skip updating background */
            Log.e(TAG, e.toString());
        }
    }

    /**
     * updateBackground with delay
     * delay time is measured in other Timer task thread.
     *
     * @param uri
     */
    public void updateBackgroundWithDelay(URI uri) {
        mBackgroundURI = uri;
        startBackgroundTimer();
    }

    private void updateBackground(URI uri) {
        try {
            Glide.with(mActivity)
                    .load(uri.toString())
                    .centerCrop()
                    .error(mDefaultBackground)
                    .into(mBackgroundTarget);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public class GlideBackgroundManagerTarget extends SimpleTarget<GlideDrawable> {
        BackgroundManager mBackgroundManager;

        //接收目标view
        public GlideBackgroundManagerTarget(BackgroundManager backgroundManager) {
            this.mBackgroundManager = backgroundManager;
        }


        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            Drawable drawable = resource.getCurrent();
            mBackgroundManager.setDrawable(drawable);
        }
    }


}