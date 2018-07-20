package com.cloudminds.camera2.device;
import android.hardware.Camera;

public abstract interface ICamera extends IAndroidCamera{
    public abstract Camera.Parameters getParameters();
}
