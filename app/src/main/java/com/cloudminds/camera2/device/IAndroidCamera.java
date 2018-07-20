package com.cloudminds.camera2.device;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.location.Location;
import java.util.List;

public abstract interface IAndroidCamera
{
  public abstract void addCallbackBuffer(byte[] paramArrayOfByte);

  public abstract void addRawImageCallbackBuffer(byte[] paramArrayOfByte);

  public abstract void autoFocus(Camera.AutoFocusCallback paramAutoFocusCallback);

  public abstract void cancelAutoFocus();

  public abstract void cancelPreviewCallback(Camera.PreviewCallback paramPreviewCallback);

  public abstract Camera getAndroidCamera();

  public abstract float getExposureCompensationStep();

  public abstract List getFrontFlashLevelRatios();

  public abstract long getGPSTimestamp();

  public abstract int getMaxExposureCompensation();

  public abstract int getMaxNumMeteringAreas();

  public abstract int getMaxZoom();

  public abstract int getMinExposureCompensation();

  public abstract int getPreviewFormat();

  public abstract boolean getRecordingHint();

  public abstract List getSupportedEffects();

  public abstract List getSupportedFlashModes();

  public abstract List getSupportedFocusModes();

  public abstract List getSupportedLCDCompensateValues();

  public abstract List getSupportedMakeupEffects();

  public abstract List getSupportedPictureSizes();

  public abstract List getSupportedPreviewFrameRates();

  public abstract List getSupportedPreviewSizes();

  public abstract List getSupportedSceneModes();

  public abstract List getSupportedWhiteBalance();

  public abstract List getZoomRatios();

  public abstract boolean isAutoExposuresLockSupported();

  public abstract boolean isAutoWhiteBalanceLockSupported();

  public abstract boolean isVideoSnapshotSupported();

  public abstract boolean isVideoStabiliserSupported();

  public abstract boolean isZoomSupported();

  public abstract void lock();

  public abstract void release();

  public abstract void setAutoExposureLock(Boolean paramBoolean);

  public abstract void setAutoFocusMoveCallback(Camera.AutoFocusMoveCallback paramAutoFocusMoveCallback);

  public abstract void setAutoWhiteBalanceLock(Boolean paramBoolean);

  public abstract void setDisplayOrientation(int paramInt);

  public abstract void setEffect(String paramString);

  public abstract void setExposureCompensation(int paramInt);

  public abstract void setExposureCompensation(String paramString);

  public abstract void setFaceDetectionListener(Camera.FaceDetectionListener paramFaceDetectionListener);

  public abstract void setFlashMode(String paramString);

  public abstract void setFocusAreas(List paramList);

  public abstract void setFocusMode(String paramString);

  public abstract void setFrontFlashLevel(String String);

  public abstract void setGPSeter(Location paramLocation);

  public abstract void setJpegQuality(int paramInt);

  public abstract void setMakeupEffect(String paramString);

  public abstract void setMakeupModeState(String paramString);

  public abstract void setMeteringAreas(List paramList);

  public abstract void setOneShotPreviewCallback(Camera.PreviewCallback paramPreviewCallback);

  public abstract void setParameters();

  public abstract void setPictureSize(int paramInt1, int paramInt2);

  public abstract void setPreviewCallback(Camera.PreviewCallback paramPreviewCallback);

  public abstract void setPreviewCallbackWithBuffer(Camera.PreviewCallback paramPreviewCallback);

  public abstract void setPreviewFrameRate(int paramInt);

  public abstract void setPreviewSize(int paramInt1, int paramInt2);

  public abstract void setPreviewTexture(SurfaceTexture paramSurfaceTexture);

  public abstract void setRecordingHint(boolean paramBoolean);

  public abstract void setRotation(int paramInt);

  public abstract void setSceneMode(String paramString);

  public abstract void setVideoSize(int paramInt1, int paramInt2);

  public abstract void setVideoStabilization(boolean paramBoolean);

  public abstract void setWhiteBalance(String paramString);

  public abstract void setZoom(int paramInt);

  public abstract void startFaceDetection();

  public abstract void startPreview(Camera.ErrorCallback paramErrorCallback);

  public abstract void stopFaceDetection();

  public abstract void stopPreview();

  public abstract void takePicture(Camera.ShutterCallback paramShutterCallback, Camera.PictureCallback paramPictureCallback1, Camera.PictureCallback paramPictureCallback2);

  public abstract void unlock();

  public abstract void updateParameters();
}
