package com.cloudminds.camera2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cloudminds.camera2.control.CameraController;
import com.cloudminds.camera2.model.CameraModuleType;
import com.cloudminds.camera2.model.ModuleTypeHelper;
import com.cloudminds.camera2.ui.widget.RotateImageView;
import com.cloudminds.camera2.ui.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import static com.cloudminds.camera2.control.CameraController.REQUEST_PICTURE_PERMISSION;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "CameraActivity";

    private CameraController mCameraController = null;

    private RotateImageView mCaptureButton;
    private RotateImageView mSwitchButton;
    private WheelView wheelView;
    private RotateImageView mThumbnail;
    private ImageButton mRecordButton;
    private TextureView mTextureView;
    private Chronometer mTimer;
    private LinearLayout mTimeLayout;
    private Uri lastImageUri;


    private final CameraModuleType[] mModuleTypes = new CameraModuleType[]{
            CameraModuleType.PHOTO_MODULE,
            CameraModuleType.VIDEO_MODULE,
            CameraModuleType.STEREO_PHOTO,
            CameraModuleType.STEREO_VIDEO,
            CameraModuleType.PANAROMA,
    };

    //uiHandler在主线程中创建，所以自动绑定主线程
    private Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    lastImageUri = (Uri) msg.obj;
                    Glide.with(CameraActivity.this).load(lastImageUri).into(mThumbnail);
                    break;
                case 2:
                    Glide.with(CameraActivity.this).load(lastImageUri).into(mThumbnail);
                    break;
            }
        }
    };
    private TextureView.SurfaceTextureListener mSurfaceListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if (mTextureView.isAvailable()) {
                mCameraController.openCamera(0);
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_main);
        initView();
        final List<String> items = new ArrayList<>();
        for (int i = 0; i < mModuleTypes.length; i++) {
            items.add(getResources().getString(ModuleTypeHelper.ModuleType2Name(mModuleTypes[i])));
        }
        wheelView.setItems(items);
        wheelView.setMinSelectableIndex(0);
        wheelView.setMaxSelectableIndex(items.size() - 1);
    }

    private void setFullScreen() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.setAttributes(params);
    }

    private void initView() {
        mCaptureButton = findViewById(R.id.cap_btn);
        mTextureView = findViewById(R.id.textureview);
        mSwitchButton = findViewById(R.id.switch_btn);
        mRecordButton = findViewById(R.id.record_btn);
        mTimeLayout = findViewById(R.id.time_layout);
        mTimer = findViewById(R.id.timer);
        mThumbnail = findViewById(R.id.preview_thumb);
        wheelView = findViewById(R.id.wheelview);
        mCaptureButton.setOnClickListener(this);
        mSwitchButton.setOnClickListener(this);
        mRecordButton.setOnClickListener(this);
        mThumbnail.setOnClickListener(this);
        mRecordButton.setBackgroundResource(R.drawable.btn_shutter_video_default);
        mTimeLayout.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_btn:
                mCameraController.switchCamera();
                if (mCameraController.getOpenedCamera() == 0) {
                    mSwitchButton.setBackgroundResource(R.drawable.ic_switch_front);
                } else {
                    mSwitchButton.setBackgroundResource(R.drawable.ic_switch_back);
                }
                break;
            case R.id.cap_btn:
                if (mCameraController.isCaptureFinished()) {
                    mCameraController.takePicture();
                }
                break;

            case R.id.record_btn:
                if (!mCameraController.isRecordingVideo()){
                    mCameraController.startRecording();
                    mRecordButton.setBackgroundResource(R.drawable.btn_shutter_video_recording);
                    mCaptureButton.setBackgroundResource(R.drawable.btn_shutter_pressed_disabled);
                    mCaptureButton.setEnabled(false);
                    mSwitchButton.setEnabled(false);
                    mTimeLayout.setVisibility(View.VISIBLE);
                    mTimer.setBase(SystemClock.elapsedRealtime());
                    int hour = (int) ((SystemClock.elapsedRealtime()-mTimer.getBase()) / 1000 / 60);
                    mTimer.setFormat("0"+String.valueOf(hour)+":%s");
                    mTimer.start();
                } else {
                    mCameraController.stopRecording();
                    mRecordButton.setBackgroundResource(R.drawable.btn_shutter_video_default);
                    mCaptureButton.setBackgroundResource(R.drawable.btn_shutter_default);
                    mCaptureButton.setEnabled(true);
                    mSwitchButton.setEnabled(true);
                    mTimer.stop();
                    mTimeLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.preview_thumb:
                gotoGallery(lastImageUri);
                break;
        }

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        if (mTextureView.isAvailable()) {
            Log.d(TAG, "onResume: isAvailable!");
            mCameraController.openCamera(0);
        } else {
            Log.d(TAG, "onResume: not available!");
            mCameraController = new CameraController(mTextureView, this, mUIHandler);
            mTextureView.setSurfaceTextureListener(mSurfaceListener);
            mCameraController.setOrientationListener();
        }

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        if (mCameraController.isRecordingVideo()) {
            mCameraController.stopRecording();
            mRecordButton.setBackgroundResource(R.drawable.btn_shutter_video_default);
            mTimer.stop();
            mTimeLayout.setVisibility(View.GONE);
        }
        if (mCameraController != null && mCameraController.getOpenedCamera()!=-1) {
            mCameraController.closeCamera();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraController.closeCamera();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PICTURE_PERMISSION) {
            if((grantResults.length > 0)  && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Log.i(TAG, "onRequestPermissionsResult: ok");
                lastImageUri = getLastImageUri();
            } else {
                Log.i(TAG, "onRequestPermissionsResult: error");
            }
        }
    }

    public void gotoGallery(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    public Uri getLastImageUri() {
        Cursor cursor = null;
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
        };
        try {
            cursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
            if (cursor != null && cursor.moveToFirst()) {
                String imageLocation = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (imageLocation.contains("DCIM")) {
                    int id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.MediaColumns._ID));
                    Uri baseUri = Uri.parse("content://media/external/images/media");
                    mUIHandler.sendEmptyMessage(2);
                    return Uri.withAppendedPath(baseUri, "" + id);
                }

            } while (cursor.moveToNext());
        } finally {
            if(cursor != null) {
                cursor.close();
            }

        }
        return null;
    }

}
