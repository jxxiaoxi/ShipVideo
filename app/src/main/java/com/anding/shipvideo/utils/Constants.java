package com.anding.shipvideo.utils;


import com.anding.shipvideo.ShipvideoApplication;

/**
 * Created by wangshijia on 2017/2/8 下午2:52.
 * Copyright (c) 2017. alpha, Inc. All rights reserved.
 */

public class Constants {

    //服务器地址
    public static final String SERVER_URL = "http://132.232.111.161/gift/vlist?hy=0&aq=0&gz=0&zh=0";
    //行业具体分类
    public static final String SERVER_HY_CATEGORY_URL = "http://132.232.111.161/gift/hy";
    //安全具体分类
    public static final String SERVER_AQ_CATEGORY_URL = "http://132.232.111.161/gift/aq";
    //工种具体分类
    public static final String SERVER_GZ_CATEGORY_URL = "http://132.232.111.161/gift/gz";
    //综合具体分类
    public static final String SERVER_ZH_CATEGORY_URL = "http://132.232.111.161/gift/zh";

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

}
