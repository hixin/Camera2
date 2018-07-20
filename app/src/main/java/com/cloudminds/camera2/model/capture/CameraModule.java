package com.cloudminds.camera2.model.capture;

import android.util.Log;

import com.cloudminds.camera2.CameraActivity;

public abstract class CameraModule implements ModuleController{
    protected CameraActivity mActivity;
    @Override
    public void init(CameraActivity activity, boolean isSecureCamera, boolean isCaptureIntent) {
        Log.i(this.getClass().getSimpleName(), "init: ");
        mActivity = activity;
    }

    @Override
    public void resume() {
        Log.i(this.getClass().getSimpleName(), "resume: ");
    }

    @Override
    public void pause() {
        Log.i(this.getClass().getSimpleName(), "pause: ");
    }
}
