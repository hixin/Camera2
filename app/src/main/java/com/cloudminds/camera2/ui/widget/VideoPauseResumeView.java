package com.cloudminds.camera2.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.cloudminds.camera2.model.capture.VideoModule;
import java.util.HashMap;
import java.util.Map;
import com.cloudminds.camera2.R;

public class VideoPauseResumeView extends RotateImageView{
    private Map<VideoModule.State, Integer> mViews = new HashMap();
    public VideoPauseResumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViews.put(VideoModule.State.RESUME, Integer.valueOf(R.drawable.btn_pause_in_video_dr));
        mViews.put(VideoModule.State.PAUSE, Integer.valueOf(R.drawable.btn_resume_in_video_dr));
        mViews.put(VideoModule.State.STOP, Integer.valueOf(R.drawable.btn_pause_in_video_dr));
    }

    public void onStateChanged(VideoModule.State state)
    {
        setImageResource(mViews.get(state));
    }
}
