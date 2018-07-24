/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudminds.camera2.App;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudminds.camera2.R;
import com.cloudminds.camera2.model.CameraModuleType;
import com.cloudminds.camera2.model.ModuleTypeHelper;
import com.cloudminds.camera2.model.capture.VideoModule;
import com.cloudminds.camera2.ui.component.shutterbutton.ShutterButton;
import com.cloudminds.camera2.ui.widget.RotateImageView;
import com.cloudminds.camera2.ui.widget.Switcher;
import com.cloudminds.camera2.ui.widget.VideoPauseResumeView;

import java.util.ArrayList;
import java.util.List;

/**
 * CameraAppUI centralizes control of views shared across modules. Whereas module
 * specific views will be handled in each Module UI. For example, we can now
 * bring the flash animation and capture animation up from each module to app
 * level, as these animations are largely the same for all modules.
 *
 * This class also serves to disambiguate touch events. It recognizes all the
 * swipe gestures that happen on the preview by attaching a touch listener to
 * a full-screen view on top of preview TextureView. Since CameraAppUI has knowledge
 * of how swipe from each direction should be handled, it can then redirect these
 * events to appropriate recipient views.
 */
public class CameraAppUI  implements View.OnClickListener, Switcher.OnItemSelectedListener{
    private static final String TAG = "CameraAppUI";
    private AppController mController;
    private final boolean mIsCaptureIntent;

    private Uri lastImageUri;
    private int curentModuleIndex;
    private long recordingTime = 0;

    // App level views:
    private final FrameLayout mCameraRootView;
    private ShutterButton mShutterButton;
    private RotateImageView mEffectView;
    private Switcher wheelView;
    private RotateImageView mThumbnail;
    private TextureView mTextureView;
    private RotateImageView mNavigationSpot;

    private Chronometer mTimer;
    private VideoPauseResumeView mVideoPauseResumeView;
    private RotateImageView mCaptureInVideo;
    private ImageView mVideoRecordIndicator;

    private final CameraModuleType[] mModuleTypes = new CameraModuleType[]{
            CameraModuleType.PHOTO_MODULE,
            CameraModuleType.VIDEO_MODULE,
            CameraModuleType.STEREO_PHOTO,
            CameraModuleType.STEREO_VIDEO,
            CameraModuleType.PANAROMA,
    };

    public CameraAppUI(AppController controller, View appRootView,
                       boolean isCaptureIntent) {
        mController = controller;
        mCameraRootView = (FrameLayout) appRootView;
        mIsCaptureIntent = isCaptureIntent;
        initView();
    }

    private void initView() {
        mShutterButton = mCameraRootView.findViewById(R.id.shutter_button);
        mTextureView = mCameraRootView.findViewById(R.id.textureview);
        mEffectView = mCameraRootView.findViewById(R.id.effect);
        mThumbnail = mCameraRootView.findViewById(R.id.preview_thumb);
        wheelView = mCameraRootView.findViewById(R.id.wheelview);
        mNavigationSpot = mCameraRootView.findViewById(R.id.navigation_spot);
        mTimer = mCameraRootView.findViewById(R.id.video_recording_time);
        mVideoPauseResumeView = mCameraRootView.findViewById(R.id.pause_in_video);
        mCaptureInVideo = mCameraRootView.findViewById(R.id.capture_in_video);
        mVideoRecordIndicator = mCameraRootView.findViewById(R.id.video_recording_icon);
        mShutterButton.setOnClickListener(this);
        mEffectView.setOnClickListener(this);
        mThumbnail.setOnClickListener(this);
        mVideoPauseResumeView.setOnClickListener(this);
        mCaptureInVideo.setOnClickListener(this);
        wheelView.setOnItemSelectedListener(this);
        final List<String> items = new ArrayList<>();
        for (int i = 0; i < mModuleTypes.length; i++) {
            items.add(CameraApp.getContext().getString(ModuleTypeHelper.ModuleType2Name(mModuleTypes[i])));
        }
        wheelView.setData(items);
    }

    public void updateThumbnail(Uri uri) {
        lastImageUri = uri;
        RequestOptions options = new RequestOptions().circleCrop();
        Glide.with(CameraApp.getContext()).load(lastImageUri).apply(options).into(mThumbnail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shutter_button:
                if (curentModuleIndex ==  CameraModuleType.PHOTO_MODULE.ordinal()) {
                    mController.takePicture();
                } else if(curentModuleIndex ==  CameraModuleType.VIDEO_MODULE.ordinal()){
                    if (mController.getRecordState() == VideoModule.State.STOP) {
                        mController.startRecording();
                    } else {
                        mController.stopRecording();
                    }
                }
                break;
            case R.id.preview_thumb:
                mController.gotoGallery(lastImageUri);
                break;
            case R.id.pause_in_video:
                if (mController.getRecordState() == VideoModule.State.RESUME) {
                    mController.pauseRecording();
                } else if (mController.getRecordState() == VideoModule.State.PAUSE){
                    mController.resumeRecording();
                }
                break;
        }
    }

    public void onStartRecording() {
        mShutterButton.startVideoRecord();
        mVideoPauseResumeView.onStateChanged(VideoModule.State.RESUME);
        timerStart();
        hideViews(new View[] {wheelView, mThumbnail, mEffectView, mNavigationSpot});
        showViews(new View[] {mTimer, mVideoPauseResumeView, mCaptureInVideo, mVideoRecordIndicator});

    }

    public void onPauseRecording() {
        mVideoPauseResumeView.onStateChanged(VideoModule.State.PAUSE);
        timerPause();
    }

    public void onResumeRecording() {
        mVideoPauseResumeView.onStateChanged(VideoModule.State.RESUME);
        timerStart();
    }

    public void onStopRecording() {
        mShutterButton.stopVideoRecord();
        mVideoPauseResumeView.onStateChanged(VideoModule.State.STOP);
        timerStop();
        hideViews(new View[] {mVideoPauseResumeView, mCaptureInVideo, mTimer, mVideoRecordIndicator});
        showViews(new View[] {wheelView, mThumbnail, mEffectView, mNavigationSpot});
    }

    private void timerStart() {
        mTimer.setBase(SystemClock.elapsedRealtime() - recordingTime);// 跳过已经记录了的时间，起到继续计时的作用
        mTimer.start();
    }
    private void timerPause() {
        mTimer.stop();
        recordingTime = SystemClock.elapsedRealtime()- mTimer.getBase();// 保存这次记录了的时间
    }
    private void timerStop() {
        recordingTime = 0;
        mTimer.setBase(SystemClock.elapsedRealtime());
    }

    @Override
    public void onItemChanged(int lastPos, int currentPos) {

    }

    @Override
    public void onItemSelected(Switcher view, int position) {
        Log.i(TAG, "onItemSelected: " + position);
        curentModuleIndex = position;
        mController.onModeSelected(position);
        mShutterButton.changeState(position);
    }


    private void showViews(View[] views) {
        int size = views.length;
        for (int i = 0; i < size; i++) {
            View view = views[i];
            view.setVisibility(View.VISIBLE);
            Log.d(TAG, "show view : " + view.toString());
        }
    }

    private void hideViews(View[] views) {
        int size = views.length;
        for (int i = 0; i < size; i++) {
            View view = views[i];
            view.setVisibility(View.INVISIBLE);
            Log.d(TAG, "show view : " + view.toString());
        }
    }



    public void showViews(int[] ids) {
        int size = ids.length;
        for (int i = 0; i < size; i++) {
            View view = mCameraRootView.findViewById(ids[i]);
            if (view != null) {
                view.setVisibility(View.VISIBLE);
                Log.d(TAG, "show view : " + view.toString());
            }
        }
    }

    public void hideViews(int[] ids) {
        int size = ids.length;
        for (int i = 0; i < size; i++) {
            View view = mCameraRootView.findViewById(ids[i]);
            if (view != null) {
                view.setVisibility(View.INVISIBLE);
                Log.d(TAG, "show view : " + view.toString());
            }
        }
    }
}
