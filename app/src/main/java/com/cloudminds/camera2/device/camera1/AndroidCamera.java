package com.cloudminds.camera2.device.camera1;


import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.location.Location;
import com.cloudminds.camera2.device.ICamera;
import java.util.List;

public abstract class AndroidCamera implements ICamera {
    protected Camera mCamera;
    protected Camera.Parameters mParameters;

    protected AndroidCamera(Camera paramCamera)
    {
        mCamera = paramCamera;
        mParameters = paramCamera.getParameters();
    }

    public Camera.Parameters getParameters()
    {
        return mCamera.getParameters();
    }

    @Override
    public void addCallbackBuffer(byte[] paramArrayOfByte) {
        mCamera.addCallbackBuffer(paramArrayOfByte);
    }

    @Override
    public void addRawImageCallbackBuffer(byte[] paramArrayOfByte) {

    }

    @Override
    public void autoFocus(Camera.AutoFocusCallback paramAutoFocusCallback) {
        mCamera.autoFocus(paramAutoFocusCallback);
    }

    @Override
    public void cancelAutoFocus() {
        mCamera.cancelAutoFocus();
    }

    @Override
    public void cancelPreviewCallback(Camera.PreviewCallback paramPreviewCallback) {

    }

    @Override
    public Camera getAndroidCamera() {
        return mCamera;
    }

    @Override
    public float getExposureCompensationStep() {
        return mParameters.getExposureCompensationStep();
    }

    @Override
    public List getFrontFlashLevelRatios() {
        return null;
    }

    @Override
    public long getGPSTimestamp() {
        String str = mParameters.get("gps-timestamp");
        if (str == null) {
            return 0L;
        }
        return Long.valueOf(str).longValue();
    }

    @Override
    public int getMaxExposureCompensation() {
        return mParameters.getMaxExposureCompensation();
    }

    @Override
    public int getMaxNumMeteringAreas() {
        return mParameters.getMaxNumMeteringAreas();
    }

    @Override
    public int getMaxZoom() {
        return mParameters.getMaxZoom();
    }

    @Override
    public int getMinExposureCompensation() {
        return mParameters.getMinExposureCompensation();
    }

    @Override
    public int getPreviewFormat() {
        return mParameters.getPreviewFormat();
    }

    @Override
    public boolean getRecordingHint() {
        return "true".equals(mParameters.get("recording-hint"));
    }

    @Override
    public List getSupportedEffects() {
        return null;
    }

    @Override
    public List getSupportedFlashModes() {
        List localList = mParameters.getSupportedFlashModes();
        if ((localList != null) && (localList.size() == 1) && ("off".equals(localList.get(0)))) {
            return null;
        }
        return localList;
    }

    @Override
    public List getSupportedFocusModes() {
        return mParameters.getSupportedFocusModes();
    }

    @Override
    public List getSupportedLCDCompensateValues() {
       return null;
    }

    @Override
    public List getSupportedMakeupEffects() {
        return  null;
    }

    @Override
    public List getSupportedPictureSizes() {
        return mParameters.getSupportedPreviewSizes();
    }

    @Override
    public List getSupportedPreviewFrameRates() {
        return  mParameters.getSupportedPreviewFrameRates();
    }

    @Override
    public List getSupportedPreviewSizes() {
        return mParameters.getSupportedPreviewSizes();
    }

    @Override
    public List getSupportedSceneModes() {
        return mParameters.getSupportedSceneModes();
    }

    @Override
    public List getSupportedWhiteBalance() {
        return mParameters.getSupportedWhiteBalance();
    }

    @Override
    public List getZoomRatios() {
        return mParameters.getZoomRatios();
    }

    @Override
    public boolean isAutoExposuresLockSupported() {
        return mParameters.isAutoExposureLockSupported();
    }

    @Override
    public boolean isAutoWhiteBalanceLockSupported() {
        return mParameters.isAutoWhiteBalanceLockSupported();
    }

    @Override
    public boolean isVideoSnapshotSupported() {
        return false;
    }

    @Override
    public boolean isVideoStabiliserSupported() {
        return false;
    }

    @Override
    public boolean isZoomSupported() {
        return false;
    }

    @Override
    public void lock() {

    }

    @Override
    public void release() {

    }

    @Override
    public void setAutoExposureLock(Boolean paramBoolean) {

    }

    @Override
    public void setAutoFocusMoveCallback(Camera.AutoFocusMoveCallback paramAutoFocusMoveCallback) {

    }

    @Override
    public void setAutoWhiteBalanceLock(Boolean paramBoolean) {

    }

    @Override
    public void setDisplayOrientation(int paramInt) {
        mCamera.setDisplayOrientation(paramInt);
    }

    @Override
    public void setEffect(String paramString) {

    }

    @Override
    public void setExposureCompensation(int paramInt) {

    }

    @Override
    public void setExposureCompensation(String paramString) {

    }

    @Override
    public void setFaceDetectionListener(Camera.FaceDetectionListener paramFaceDetectionListener) {

    }

    @Override
    public void setFlashMode(String paramString) {
        mParameters.setFlashMode(paramString);
    }

    @Override
    public void setFocusAreas(List paramList) {

    }

    @Override
    public void setFocusMode(String paramString) {
        mParameters.setFocusMode(paramString);
    }

    @Override
    public void setFrontFlashLevel(String String) {

    }

    @Override
    public void setGPSeter(Location paramLocation) {

    }

    @Override
    public void setJpegQuality(int paramInt) {

    }

    @Override
    public void setMakeupEffect(String paramString) {

    }

    @Override
    public void setMakeupModeState(String paramString) {

    }

    @Override
    public void setMeteringAreas(List paramList) {

    }

    @Override
    public void setOneShotPreviewCallback(Camera.PreviewCallback paramPreviewCallback) {

    }

    @Override
    public void setParameters() {
        mCamera.setParameters(mParameters);
    }

    @Override
    public void setPictureSize(int paramInt1, int paramInt2) {
        mParameters.setPictureSize(paramInt1, paramInt2);
    }

    @Override
    public void setPreviewCallback(Camera.PreviewCallback paramPreviewCallback) {

    }

    @Override
    public void setPreviewCallbackWithBuffer(Camera.PreviewCallback paramPreviewCallback) {

    }

    @Override
    public void setPreviewFrameRate(int paramInt) {

    }

    @Override
    public void setPreviewSize(int paramInt1, int paramInt2) {

    }

    @Override
    public void setPreviewTexture(SurfaceTexture paramSurfaceTexture) {

    }

    @Override
    public void setRecordingHint(boolean paramBoolean) {

    }

    @Override
    public void setRotation(int paramInt) {

    }

    @Override
    public void setSceneMode(String paramString) {

    }

    @Override
    public void setVideoSize(int paramInt1, int paramInt2) {

    }

    @Override
    public void setVideoStabilization(boolean paramBoolean) {

    }

    @Override
    public void setWhiteBalance(String paramString) {

    }

    @Override
    public void setZoom(int paramInt) {

    }

    @Override
    public void startFaceDetection() {

    }

    @Override
    public void startPreview(Camera.ErrorCallback paramErrorCallback) {

    }

    @Override
    public void stopFaceDetection() {

    }

    @Override
    public void stopPreview() {

    }

    @Override
    public void takePicture(Camera.ShutterCallback paramShutterCallback, Camera.PictureCallback paramPictureCallback1, Camera.PictureCallback paramPictureCallback2) {

    }

    @Override
    public void unlock() {

    }

    @Override
    public void updateParameters() {

    }
}
