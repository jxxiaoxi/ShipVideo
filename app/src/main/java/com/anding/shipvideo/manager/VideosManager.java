package com.anding.shipvideo.manager;

import com.anding.shipvideo.data.Video;
import com.anding.shipvideo.utils.DatabaseUtils;
import com.anding.shipvideo.utils.LogUtils;

import java.util.ArrayList;
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

//    /*
//     * 获取对应分类的视频
//     * */
//    public List<Video> queryVideosByCategory(long category) {
//        List<Video> videos = DatabaseUtils.getInstance().queryVideosByCategory(category);
//        LogUtils.d("liuwei", "queryVideosByCategory--->" + videos.size());
//        return videos;
//    }

    /*
     * 插入视频到数据库
     * */
    public void insertVideo(Video video) {
        DatabaseUtils.getInstance().insertVideo(video);
    }

    /*
     * 批量插入视频到数据库
     * */
    public void insertVideos(List<Video> videos) {
        DatabaseUtils.getInstance().insertVideos(videos);
    }

    /*
     * 批量删除视频
     * */
    public void deleteVideos(ArrayList<Video> videos) {
        DatabaseUtils.getInstance().deleteVideos(videos);
    }

    /*
     * 删除所有视频
     * */
    public void deleteAllVideos() {
        DatabaseUtils.getInstance().deleteAllVideos();
    }
}
