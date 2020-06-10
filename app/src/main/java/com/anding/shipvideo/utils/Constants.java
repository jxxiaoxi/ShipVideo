package com.anding.shipvideo.utils;


import com.anding.shipvideo.ShipvideoApplication;

public class Constants {

    //服务器地址，请求所有的视频
    public static final String SERVER_URL = "http://132.232.111.161/gift/vlist?cate=0";
    //行业具体分类
    public static final String SERVER_HY_CATEGORY_URL = "http://132.232.111.161/gift/hy";
    //安全具体分类
    public static final String SERVER_AQ_CATEGORY_URL = "http://132.232.111.161/gift/aq";
    //工种具体分类
    public static final String SERVER_GZ_CATEGORY_URL = "http://132.232.111.161/gift/gz";
    //综合具体分类
    public static final String SERVER_ZH_CATEGORY_URL = "http://132.232.111.161/gift/zh";

    //apk版本请求地址
    public static final String SERVER_APK_URL = "https://alissl.ucdl.pp.uc.cn/fs08/2019/07/05/1/110_17e4089aa3a4b819b08069681a9de74b.apk";

    public static final String EXTRA_KEY_EXIT = "extra_key_exit";

    public static final String DOWNLOAD_SPLASH = "download_splash";
    public static final String EXTRA_DOWNLOAD = "extra_download";

    //动态闪屏序列化地址
    public static final String SPLASH_PATH = ShipvideoApplication.getContext().getFilesDir().getAbsolutePath() + "/alpha/splash";

    public static final String SPLASH_FILE_NAME = "splash.srr";
    public static final int CATEGORY_TRADE = 1;
    public static final int CATEGORY_TIME = 2;
    public static final int CATEGORY_REGION = 3;
    public static final int CATEGORY_COLLIGATE = 4;

    public static final String CATEGORY_VALUE = "category_value";
    public static final String CATEGORY_DESCRIPTION = "category_description";

}
