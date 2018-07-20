package com.cloudminds.camera2.ui.component.shutterbutton.drawable;

import android.graphics.drawable.Drawable;

import com.cloudminds.camera2.R;
import com.cloudminds.camera2.ui.component.shutterbutton.ShutterButton;
import com.cloudminds.camera2.utils.AppUtil;

public class PhotoModeDrawable extends CommonDrawable {
    public PhotoModeDrawable(ShutterButton shutterButton) {
        super(shutterButton);
    }

    @Override
    protected Drawable getDefaultDrawable() {
        return AppUtil.getDrawable(R.drawable.shutter_button_normal_dr);
    }

}
