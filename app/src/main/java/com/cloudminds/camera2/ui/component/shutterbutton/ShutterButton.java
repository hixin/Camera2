package com.cloudminds.camera2.ui.component.shutterbutton;

import android.content.Context;
import android.util.AttributeSet;

import com.cloudminds.camera2.ui.component.shutterbutton.drawable.CommonDrawable;
import com.cloudminds.camera2.ui.component.shutterbutton.drawable.PhotoModeDrawable;
import com.cloudminds.camera2.ui.component.shutterbutton.drawable.VideoModeDrawable;
import com.cloudminds.camera2.ui.widget.RotateImageView;

public class ShutterButton extends RotateImageView{
    CommonDrawable commonDrawable;
    public ShutterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void setDrawable(CommonDrawable drawable) {
        commonDrawable = drawable;
        commonDrawable.onResume();
    }

    public void changeState(int state) {
        if (state == 0) {
            setDrawable(new PhotoModeDrawable(this));
        } else if (state == 1) {
            setDrawable(new VideoModeDrawable(this));
        } else {
            setDrawable(new PhotoModeDrawable(this));
        }
    }
}
