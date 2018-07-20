package com.cloudminds.camera2.model.capture;

import com.cloudminds.camera2.CameraActivity;

public interface ModuleController {

    /**
     * Initializes the module.
     *
     * @param activity The camera activity.
     * @param isSecureCamera Whether the app is in secure camera mode.
     * @param isCaptureIntent Whether the app is in capture intent mode.
     */
    public void init(CameraActivity activity, boolean isSecureCamera, boolean isCaptureIntent);

    /**
     * Resumes the module. Always call this method whenever it's being put in
     * the foreground.
     */
    public void resume();

    /**
     * Pauses the module. Always call this method whenever it's being put in the
     * background.
     */
    public void pause();
}
