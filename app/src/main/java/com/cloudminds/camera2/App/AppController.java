package com.cloudminds.camera2.App;

import android.net.Uri;

public interface AppController {
    /**
     * This gets called when mode is changed.
     *
     * @param moduleIndex index of the new module to switch to
     */
    void onModeSelected(int moduleIndex);

    void gotoGallery(Uri uri);

    void takePicture();
}
