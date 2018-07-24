package com.cloudminds.camera2.App;

import android.net.Uri;

import com.cloudminds.camera2.model.capture.VideoModule;

public interface AppController {
    /**
     * This gets called when mode is changed.
     *
     * @param moduleIndex index of the new module to switch to
     */
    void onModeSelected(int moduleIndex);

    void gotoGallery(Uri uri);

    void takePicture();

    VideoModule.State getRecordState();

    void startRecording();

    void stopRecording();

    void pauseRecording();

    void resumeRecording();
}
