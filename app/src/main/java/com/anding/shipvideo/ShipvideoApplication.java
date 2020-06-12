package com.anding.shipvideo;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.anding.shipvideo.database.DaoMaster;
import com.anding.shipvideo.database.DaoSession;
import com.anding.shipvideo.utils.Constants;
import com.umeng.commonsdk.UMConfigure;

public class ShipvideoApplication extends Application {
    private static Context context;
    private static DaoSession daoSession;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initGreenDao();
        UMConfigure.init(context, Constants.U_APPKEY, Constants.U_CHANNEL, UMConfigure.DEVICE_TYPE_BOX,null);
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aserbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

}
