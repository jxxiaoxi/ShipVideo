package com.anding.shipvideo.utils;


import com.anding.shipvideo.ShipvideoApplication;

/**
 * Created by wangshijia on 2017/2/8 下午2:52.
 * Copyright (c) 2017. alpha, Inc. All rights reserved.
 */

public class Constants {

    public static final String API_DEBUG_SERVER_URL = "http://beta.goldenalpha.com.cn/";

    //服务器地址
    public static final String SERVER_URL = "http://132.232.111.161/gift/vlist?hy=0&aq=0&gz=0&zh=0";

    public static final String EXTRA_KEY_EXIT = "extra_key_exit";

    public static final String DOWNLOAD_SPLASH = "download_splash";
    public static final String EXTRA_DOWNLOAD = "extra_download";

    //动态闪屏序列化地址
    public static final String SPLASH_PATH = ShipvideoApplication.getContext().getFilesDir().getAbsolutePath() + "/alpha/splash";

    public static final String SPLASH_FILE_NAME = "splash.srr";
}
