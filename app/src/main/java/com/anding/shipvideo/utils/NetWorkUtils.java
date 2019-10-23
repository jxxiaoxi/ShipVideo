package com.anding.shipvideo.utils;

import com.anding.shipvideo.ShipvideoApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关辅助类
 */
public class NetWorkUtils {

    /**
     * 判断网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) ShipvideoApplication
                .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断WIFI是否可用
     *
     * @return
     */
    public static boolean isWifiAvailable() {
        ConnectivityManager manager = (ConnectivityManager) ShipvideoApplication
                .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断移动数据流量是否可用
     *
     * @return
     */
    public static boolean isMobileDataAvailable() {
        ConnectivityManager manager = (ConnectivityManager) ShipvideoApplication
                .getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected(); // 可用不一定连接，连接一定可用
        }
        return false;
    }
}
