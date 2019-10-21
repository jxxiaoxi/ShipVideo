package com.anding.shipvideo.data;

import com.anding.shipvideo.utils.DatabaseUtils;
import com.anding.shipvideo.utils.LogUtils;

import java.util.ArrayList;

/*
 * 视频的管理类.
 * */
public class VideosManager {
    private static final byte[] LOCKER = new byte[0];
    private static VideosManager INSTANCE = null;

    public static VideosManager getInstance() {
        if (null == INSTANCE) {
            synchronized (LOCKER) {
                LogUtils.v("BteBinder", "BluetoothManger  INIT");
                INSTANCE = new VideosManager();
            }
        }
        return INSTANCE;
    }

    /*
     * 获取特定分类的所有视频
     * */
    public ArrayList<Video> getVideosByCategory(String category) {
        ArrayList<Video> videos = new ArrayList<>();
        DatabaseUtils.getInstance().queryVideosByCategory(category);

        return videos;
    }

}
