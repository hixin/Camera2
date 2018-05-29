package com.cloudminds.camera2;

import android.app.Application;
import android.content.Context;

import com.cloudminds.camera2.utils.CameraUtil;

public class CameraApp extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CameraUtil.initialize(context);
    }

    public static Context getContext() {
        return context;
    }


}
