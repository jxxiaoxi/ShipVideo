package com.anding.shipvideo.utils;

import android.text.TextUtils;

import com.anding.shipvideo.ShipvideoApplication;
import com.anding.shipvideo.data.Video;
import com.anding.shipvideo.database.VideoDao;

import java.util.List;

public class DatabaseUtils {
    private static final byte[] LOCKER = new byte[0];
    private static DatabaseUtils mInstance;

    public static DatabaseUtils getInstance() {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new DatabaseUtils();
                }
            }
        }
        return mInstance;
    }

    public void insertVideo(Video video) {
        ShipvideoApplication.getDaoSession().getVideoDao().insert(video);
    }

    /*
     * 获取所有的视频
     * */
    public List<Video> queryAll() {
        List<Video> videos = ShipvideoApplication.getDaoSession().getVideoDao().loadAll();
        return videos;
    }
//
//    public void insertVideos(Video videos){
//        ShipvideoApplication.getDaoSession().getVideoDao().insertOrReplace(videos);
//    }

    public List<Video> queryVideosByCategory(String category) {
        List<Video> videos = ShipvideoApplication.getDaoSession().getVideoDao().queryBuilder().where(VideoDao.Properties.Category.eq(category)).list();
        return videos;
    }

}
