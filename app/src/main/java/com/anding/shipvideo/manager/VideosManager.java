package com.anding.shipvideo.manager;

import com.anding.shipvideo.data.Video;
import com.anding.shipvideo.utils.DatabaseUtils;

import java.util.List;

public class VideosManager {
    private static final byte[] LOCKER = new byte[0];
    private static VideosManager mInstance;

    public static VideosManager getInstance() {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new VideosManager();
                }
            }
        }
        return mInstance;
    }

    /*
     * 获取所有的视频
     * */
    public List<Video> queryAll() {
        List<Video> videos = DatabaseUtils.getInstance().queryAll();
        return videos;
    }

    /*
     * 获取对应分类的视频
     * */
    public List<Video> queryVideosByCategory(String category) {
        List<Video> videos = DatabaseUtils.getInstance().queryVideosByCategory(category);
        return videos;
    }
}
