package com.cloudminds.camera2.model.capture;

import android.util.Log;

import com.cloudminds.camera2.CameraActivity;

public class PhotoModule extends CameraModule {
    private static final String TAG = "PhotoModule";
    private boolean mPaused;
    private int mCameraId;

    @Override
    public void init(CameraActivity activity, boolean isSecureCamera, boolean isCaptureIntent) {
        super.init(activity, isSecureCamera, isCaptureIntent);
    }

    @Override
    public void resume() {
        super.resume();
        mPaused = false;
        requestCameraOpen();
    }

    @Override
    public void pause() {
        super.pause();
    }

    private void requestCameraOpen() {
        Log.v(TAG, "requestCameraOpen");
        mActivity.getCameraController().openCamera(0);
    }

}
