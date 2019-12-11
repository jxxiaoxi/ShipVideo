package com.anding.shipvideo.utils;

import com.anding.shipvideo.ShipvideoApplication;
import com.anding.shipvideo.data.CategorySub;
import com.anding.shipvideo.data.Video;
import com.anding.shipvideo.database.CategorySubDao;
import com.anding.shipvideo.database.VideoDao;

import java.util.ArrayList;
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
        ShipvideoApplication.getDaoSession().getVideoDao().insertOrReplace(video);
    }

    public void insertCategorySub(CategorySub categorySub) {
        ShipvideoApplication.getDaoSession().getCategorySubDao().insertOrReplace(categorySub);
    }

    public void insertCategorySubs(List<CategorySub> categorySubs) {
        ShipvideoApplication.getDaoSession().getCategorySubDao().insertOrReplaceInTx(categorySubs);
    }

    /*
     * 获取所有的视频
     * */
    public List<Video> queryAllVideos() {
        List<Video> videos = ShipvideoApplication.getDaoSession().getVideoDao().loadAll();
        return videos;
    }

    /*
     * 获取对应所有的二级分类
     * */
    public List<CategorySub> queryCategorySubs(String category) {
        List<CategorySub> categorySubs = ShipvideoApplication.getDaoSession().getCategorySubDao().queryBuilder().where(CategorySubDao.Properties.Category.eq(category)).list();

        return categorySubs;
    }

    /*
     * 获取所有的二级分类
     * */
    public List<CategorySub> queryAllCategorySub() {
        List<CategorySub> categorySubs = ShipvideoApplication.getDaoSession().getCategorySubDao().loadAll();
        return categorySubs;
    }
//
//    public void insertVideos(Video videos){
//        ShipvideoApplication.getDaoSession().getVideoDao().insertOrReplace(videos);
//    }

    //    public List<Video> queryVideosByCategory(long category) {
//        List<Video> videos = ShipvideoApplication.getDaoSession().getVideoDao().queryBuilder().where(VideoDao.Properties.Category.eq(category)).list();
//        return videos;
//    }
    /*
     * 删除所有的视频
     * */
    public void deleteVideos(ArrayList<Video> videos) {
        ShipvideoApplication.getDaoSession().getVideoDao().deleteInTx(videos);
    }

    /*
     * 删除所有的视频
     * */
    public void deleteAllVideos() {
        ShipvideoApplication.getDaoSession().getVideoDao().deleteAll();
    }

    /*
     * 删除所有的二级分类
     * */
    public void deleteAllCategorySubs() {
        ShipvideoApplication.getDaoSession().getCategorySubDao().deleteAll();
    }
}
