package com.cloudminds.camera2.ui.component.shutterbutton.drawable;

import android.graphics.drawable.Drawable;

import com.cloudminds.camera2.R;
import com.cloudminds.camera2.ui.component.shutterbutton.ShutterButton;
import com.cloudminds.camera2.utils.AppUtil;

public class VideoModeDrawable extends CommonDrawable {
    public VideoModeDrawable(ShutterButton shutterButton) {
        super(shutterButton);
    }

    @Override
    protected Drawable getDefaultDrawable() {
        return AppUtil.getDrawable(R.drawable.shutter_button_video_dr);
    }

    protected Drawable getVideoRecordDrawable()
    {
        return AppUtil.getDrawable(R.drawable.btn_shutter_video_stop_dr);
    }

    @Override
    public void startVideoRecord() {
        setDrawable(getVideoRecordDrawable());
    }

    @Override
    public void stopVideoRecord() {
        setDrawable(getDefaultDrawable());
    }
}
