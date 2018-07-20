package com.cloudminds.camera2.model;

import android.hardware.Camera;

import com.cloudminds.camera2.device.ICamera;
import com.cloudminds.camera2.device.camera1.RedCamera;

public class CameraHolder{
    // Use a singleton.
    private static CameraHolder sHolder;
    private int mCameraId = -1;  // current camera id
    public static synchronized CameraHolder instance() {
        if (sHolder == null) {
            sHolder = new CameraHolder();
        }
        return sHolder;
    }

    public ICamera open(int cameraId, CameraOpenErrorCallback cb) {
        Camera localCamera = null;
        try {
            localCamera = Camera.open(cameraId);
        } catch (Exception e) {
            cb.onError();
        }
        mCameraId = cameraId;
        if(localCamera == null) {
            cb.onError();
            return null;
        }
        return new RedCamera(localCamera);
    }


    public interface CameraOpenErrorCallback {
        void onError();
    }

}
