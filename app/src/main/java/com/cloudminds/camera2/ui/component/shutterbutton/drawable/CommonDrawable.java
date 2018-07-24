package com.cloudminds.camera2.ui.component.shutterbutton.drawable;

import android.graphics.drawable.Drawable;
import com.cloudminds.camera2.ui.component.shutterbutton.ShutterButton;

public abstract class CommonDrawable {
    private static final String CREATE_HITS_TABLE = "CAMERA3_" + CommonDrawable.class.getSimpleName();
    protected Drawable mDrawable;
    protected ShutterButton mShutterButton;

    protected abstract Drawable getDefaultDrawable();

    public CommonDrawable(ShutterButton shutterButton)
    {
        mShutterButton = shutterButton;
    }

    public void onResume()
    {
        setDrawable(getDefaultDrawable());
    }

    protected void setDrawable(Drawable drawable)
    {

        mDrawable = drawable;
        mShutterButton.setImageDrawable(mDrawable);
        mShutterButton.getBackground().setAlpha(255);
        mDrawable.setAlpha(255);
    }

    public void startVideoRecord() {

    }

    public void stopVideoRecord() {

    }
}
