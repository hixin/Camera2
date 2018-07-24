package com.cloudminds.camera2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cloudminds.camera2.App.AppController;
import com.cloudminds.camera2.App.CameraAppUI;
import com.cloudminds.camera2.control.CameraController;
import com.cloudminds.camera2.model.CameraHolder;
import com.cloudminds.camera2.model.capture.CameraModule;
import com.cloudminds.camera2.model.capture.ModuleManager;
import com.cloudminds.camera2.model.capture.VideoModule;
import com.cloudminds.camera2.settings.Keys;
import com.cloudminds.camera2.settings.SettingsManager;
import com.cloudminds.camera2.utils.AppUtil;
import java.lang.ref.WeakReference;

import static com.cloudminds.camera2.App.CameraActions.SHOW_RECENT_THUMBNAIL;
import static com.cloudminds.camera2.App.CameraActions.UPDATE_THUMBNAIL;

public class CameraActivity extends AppCompatActivity implements AppController{
    private static String TAG = "CameraActivity";
    public CameraController getCameraController() {
        return mCameraController;
    }

    private static final String INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE =
            "android.media.action.STILL_IMAGE_CAMERA_SECURE";
    public static final String ACTION_IMAGE_CAPTURE_SECURE =
            "android.media.action.IMAGE_CAPTURE_SECURE";
    public static final String SECURE_CAMERA_EXTRA = "secure_camera";
    private CameraAppUI mCameraAppUI;
    private CameraController mCameraController;
    private ModuleManager moduleManager;
    private SettingsManager mSettingsManager;
    private ErrorCallBack errorCallBack = new ErrorCallBack();

    private int mCurrentModeIndex;
    private CameraModule mCurrentModule;
    private Handler mMainHandler;
    private boolean mPaused;
    private Uri recentPhoto;
    private boolean initFirstTime = false;
    private TextureView mTextureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setFullScreen();
        AppUtil.initialize(this);
        moduleManager = new ModuleManager();
        mSettingsManager = new SettingsManager(this);
        mMainHandler = new MainHandler(this, getMainLooper());
        mCameraController = new CameraController(this, mMainHandler);

        if (isStartRequestPermission()) {
            finish();
            return;
        }

        setContentView(R.layout.camera_main);
        mCameraAppUI = new CameraAppUI(this, findViewById(R.id.camera_root), isCaptureIntent());
        mTextureView = findViewById(R.id.textureview);
        mCameraController.bindTextureView(mTextureView);
        setModuleFromModeIndex(0);
        mCurrentModule.init(this, isSecureCamera(), isCaptureIntent());
    }

    private void setFullScreen() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.setAttributes(params);
    }

    private void setModuleFromModeIndex(int modeIndex) {
        mCurrentModeIndex = modeIndex;
        mCameraController.closeCamera();
        mCurrentModule = moduleManager.createModule(modeIndex, getIntent());
    }

    public void initFirstTime() {
        if(initFirstTime) {
            return;
        }
        recentPhoto  = getLastImageUri();
        if (recentPhoto != null) {
            mMainHandler.sendEmptyMessage(SHOW_RECENT_THUMBNAIL);
        }
        initFirstTime = true;


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (isStartRequestPermission()) {
            finish();
            return;
        }
        initFirstTime();
        mPaused = false;
        mCurrentModule.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mPaused = true;
     /*   if (mCameraController.isRecordingVideo()) {
            mCameraController.stopRecording();
            mRecordButton.setBackgroundResource(R.drawable.btn_shutter_video_default);
            mTimer.stop();
            mTimeLayout.setVisibility(View.GONE);
        }*/
        if (mCameraController != null && mCameraController.getOpenedCamera()!=-1) {
            mCameraController.closeCamera();
        }
    }


    @Override
    protected void onDestroy() {
        mCameraController.closeCamera();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onModeSelected(int moduleIndex) {
        if (mCurrentModeIndex == moduleIndex) {
            return;
        }
        closeModule(mCurrentModule);
        setModuleFromModeIndex(moduleIndex);
        openModule(mCurrentModule);
    }

    @Override
    public void gotoGallery(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    public void takePicture() {
        if (mCameraController.isCaptureFinished()) {
            mCameraController.takePicture();
        }
    }

    @Override
    public VideoModule.State getRecordState() {
        return mCameraController.getVideoState();
    }

    @Override
    public void startRecording() {
        mCameraController.startRecording();
        mCameraAppUI.onStartRecording();
    }

    @Override
    public void stopRecording() {
        mCameraController.stopRecording();
        mCameraAppUI.onStopRecording();
    }

    @Override
    public void pauseRecording() {
        mCameraController.pauseRecording();
        mCameraAppUI.onPauseRecording();
    }

    @Override
    public void resumeRecording() {
        mCameraController.resumeRecording();
        mCameraAppUI.onResumeRecording();
    }

    private void openModule(CameraModule module) {
        module.init(this, isSecureCamera(), isCaptureIntent());
        if (!mPaused) {
            module.resume();
        }
    }

    private void closeModule(CameraModule module) {
        module.pause();
    }


    private boolean isCaptureIntent() {
        if (MediaStore.ACTION_VIDEO_CAPTURE.equals(getIntent().getAction())
                || MediaStore.ACTION_IMAGE_CAPTURE.equals(getIntent().getAction())
                || MediaStore.ACTION_IMAGE_CAPTURE_SECURE.equals(getIntent().getAction())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSecureCamera() {
        // Check if this is in the secure camera mode.
        boolean mSecureCamera = false;
        Intent intent = getIntent();
        String action = intent.getAction();
        if (INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE.equals(action)
                || ACTION_IMAGE_CAPTURE_SECURE.equals(action)) {
            mSecureCamera = true;
        } else {
            mSecureCamera = intent.getBooleanExtra(SECURE_CAMERA_EXTRA, false);
        }

        return  mSecureCamera;
    }

    class  ErrorCallBack implements CameraHolder.CameraOpenErrorCallback{
        @Override
        public void onError() {
            Toast.makeText(CameraActivity.this, "open error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    private class MainHandler extends Handler {
        final WeakReference<CameraActivity> mActivity;

        public MainHandler(CameraActivity activity, Looper looper) {
            super(looper);
            mActivity = new WeakReference<CameraActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CameraActivity activity = mActivity.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case UPDATE_THUMBNAIL:
                    mCameraAppUI.updateThumbnail((Uri) msg.obj);
                    break;
                case SHOW_RECENT_THUMBNAIL:
                    mCameraAppUI.updateThumbnail(recentPhoto);
                default :
                    break;
            }
        }
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


    private boolean isStartRequestPermission() {

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRequestShown = prefs.getBoolean(Keys.KEY_REQUEST_PERMISSION, false);
        if (isRequestShown && hasCriticalPermissions()) {
            return false;
        }

        Intent intent = new Intent(this, PermissionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        // Scene: Start from launcher, user do not allow the location permission. Set first request flag
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Keys.KEY_REQUEST_PERMISSION, true);
        editor.apply();
        return true;
    }

    private boolean hasCriticalPermissions() {
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

}
