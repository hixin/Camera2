package com.cloudminds.camera2.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class AppUtil {
    private static final String TAG = "CAMERA3_" + AppUtil.class.getSimpleName();
    private static Context mContext;

    public static void initialize(Context context) {
        mContext = context;
    }

    public static boolean isUiThread()
    {
        if (mContext == null) {
            return false;
        }
        return Thread.currentThread() == mContext.getMainLooper().getThread();
    }

    public static int getColor(int id)
    {
        return mContext.getResources().getColor(id);
    }

    public static ContentResolver getContentResolver()
    {
        return mContext.getContentResolver();
    }

    public static Context getContext()
    {
        return mContext;
    }

    public static Drawable getDrawable(int id)
    {
        if (mContext == null) {
            return null;
        }
        return mContext.getResources().getDrawable(id);
    }

}
