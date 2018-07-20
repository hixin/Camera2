package com.cloudminds.camera2.model.capture;

public abstract interface CaptureStateListener {
    public abstract void resume();

    public abstract void pause();

    public abstract void stop();
}