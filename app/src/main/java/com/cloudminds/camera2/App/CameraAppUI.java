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
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cloudminds.camera2.R;
import com.cloudminds.camera2.model.CameraModuleType;
import com.cloudminds.camera2.model.ModuleTypeHelper;
import com.cloudminds.camera2.ui.component.shutterbutton.ShutterButton;
import com.cloudminds.camera2.ui.widget.RotateImageView;
import com.cloudminds.camera2.ui.widget.Switcher;

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

    // App level views:
    private final FrameLayout mCameraRootView;
    private ShutterButton mShutterButton;
    private RotateImageView mSwitchButton;
    private Switcher wheelView;
    private RotateImageView mThumbnail;
    private ImageButton mRecordButton;
    private TextureView mTextureView;
    private Chronometer mTimer;
    private LinearLayout mTimeLayout;



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
        mSwitchButton = mCameraRootView.findViewById(R.id.effect);
        mRecordButton = mCameraRootView.findViewById(R.id.record_btn);
        mTimeLayout = mCameraRootView.findViewById(R.id.time_layout);
        mTimer = mCameraRootView.findViewById(R.id.timer);
        mThumbnail = mCameraRootView.findViewById(R.id.preview_thumb);
        wheelView = mCameraRootView.findViewById(R.id.wheelview);
        mShutterButton.setOnClickListener(this);
        mSwitchButton.setOnClickListener(this);
        mRecordButton.setOnClickListener(this);
        mThumbnail.setOnClickListener(this);
        wheelView.setOnItemSelectedListener(this);
        mRecordButton.setBackgroundResource(R.drawable.btn_shutter_video_default);
        mTimeLayout.setVisibility(View.GONE);
        final List<String> items = new ArrayList<>();
        for (int i = 0; i < mModuleTypes.length; i++) {
            items.add(CameraApp.getContext().getString(ModuleTypeHelper.ModuleType2Name(mModuleTypes[i])));
        }
        wheelView.setData(items);
    }

    public void updateThumbnail(Uri uri) {
        lastImageUri = uri;
        Glide.with(CameraApp.getContext()).load(lastImageUri).into(mThumbnail);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shutter_button:
                mController.takePicture();
                break;
            case R.id.preview_thumb:
                mController.gotoGallery(lastImageUri);
        }
    }


    @Override
    public void onItemChanged(int lastPos, int currentPos) {

    }

    @Override
    public void onItemSelected(Switcher view, int position) {
        Log.i(TAG, "onItemSelected: " + position);
        mController.onModeSelected(position);
        mShutterButton.changeState(position);
    }
}
