package com.anding.shipvideo.utils;

import android.util.Log;

public class LogUtils {

    public static String TAG = "anding";
    
    /** 日志总开关  */
    private static boolean sLogOn = true;

    public static final String COLON = ": ";

    public static final boolean DEBUGV = true;
    
    // public static final boolean DEBUGD = Log.isLoggable(CotaLog.TAG, Log.DEBUG);
    public static final boolean DEBUGD = true;
    
    public static final boolean DEBUGI = true;
    
    public static final boolean DEBUGW = true;
    
    public static void setTag(String tag){
    	TAG = tag;
    }
    
    /**
     * 设置日志总开关
     *
     * @param bOn 日志开关
     */
    public static void setLogOn(boolean bOn){
    	sLogOn = bOn;
    }
    
    public static void v(String tag, String msg) {
        if (DEBUGV) {
            Log.v(TAG, tag + COLON + msg);
        }        
    }

    public static void i(String tag, String msg) {
        if (DEBUGI) {
            Log.i(TAG, tag + COLON + msg);
        }
    }
    
    public static void d(String tag, String msg) {
        if (sLogOn && DEBUGD) {
            Log.d(TAG, tag + COLON + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUGW) {
            Log.w(TAG, tag + COLON + msg);
        }
    }

    public static void e(String tag, String msg) {
    	Log.e(TAG, tag + COLON + msg);
    }

    public static void e(String tag, String msg, Exception e) {
    	Log.e(TAG, tag + COLON + msg, e);
    }
}
