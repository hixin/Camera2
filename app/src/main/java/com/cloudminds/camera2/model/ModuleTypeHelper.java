package com.cloudminds.camera2.model;
import com.cloudminds.camera2.R;

public class ModuleTypeHelper {
    public static int ModuleType2Name(CameraModuleType moduleType) {
        switch (moduleType) {
            case PHOTO_MODULE:
                return R.string.photo;
            case VIDEO_MODULE:
                return R.string.video;
            case STEREO_PHOTO:
                return R.string.stereo_photo;
            case STEREO_VIDEO:
                return R.string.stereo_video;
            case PANAROMA:
                return R.string.panorama;
            default:
                return R.string.photo;
        }
    }

}
