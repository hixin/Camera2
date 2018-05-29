package com.cloudminds.camera2.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.cloudminds.camera2.CameraApp;
import com.cloudminds.camera2.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtil {
    private static final String TAG = "CameraUtil";
    private static float sPixelDensity = 1;
    private static ImageFileNamer sImageFileNamer;
    public static int SAVE_SUCCESS = 0;
    public static int NOSDCARD_ERROR = 1;
    public static int SAVEFILE_ERROR = 2;

    public static void initialize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        sPixelDensity = metrics.density;
        sImageFileNamer = new ImageFileNamer(
                context.getString(R.string.image_file_name_format));
    }

    public static String createJpegName(long dateTaken) {
        synchronized (sImageFileNamer) {
            return sImageFileNamer.generateName(dateTaken);
        }
    }
    public  interface  onSaveCallBackListener{
        void onSave(Uri uri);
    }

    public static int saveImage(Image image, final onSaveCallBackListener listener) {
        //检查手机是否有sdcard
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return NOSDCARD_ERROR;
        }
        // 获取捕获的照片数据
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        //手机拍照都是存到这个路径
        long time = System.currentTimeMillis();
        String directory = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        String title = createJpegName(time) ;
        String picturePath = directory + title + ".jpg";
        File file = new File(picturePath);
        try {
            //存到本地相册
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.flush();
            fileOutputStream.close();

            ContentValues values = new ContentValues();
            ContentResolver resolver = CameraApp.getContext().getContentResolver();
            values.put(MediaStore.Images.ImageColumns.DATA, picturePath);
            values.put(MediaStore.Images.ImageColumns.TITLE, title);
            values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, title + ".jpg");
            values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, time);
            values.put(MediaStore.Images.ImageColumns.SIZE, file.length());
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.ImageColumns.WIDTH, image.getWidth());
            values.put(MediaStore.Images.ImageColumns.HEIGHT, image.getHeight());
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            // update file size in the database
            values.clear();
            values.put(MediaStore.Images.ImageColumns.SIZE, file.length());
            resolver.update(uri, values, null, null);
            listener.onSave(uri);
            return SAVE_SUCCESS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return SAVEFILE_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return SAVEFILE_ERROR;
        } finally {
            Log.d(TAG, "saveImage: finally");
            image.close();
        }
    }

    private static class ImageFileNamer {
        private final SimpleDateFormat mFormat;
        // The date (in milliseconds) used to generate the last name.
        private long mLastDate;

        // Number of names generated for the same second.
        private int mSameSecondCount;

        public ImageFileNamer(String format) {
            mFormat = new SimpleDateFormat(format);
        }

        public String generateName(long dateTaken) {
            Date date = new Date(dateTaken);
            String result = mFormat.format(date);
            Log.d(TAG, "generateName: "+result);
            // If the last name was generated for the same second,
            // we append _1, _2, etc to the name.
            if (dateTaken / 1000 == mLastDate / 1000) {
                mSameSecondCount++;
                result += "_" + mSameSecondCount;
            } else {
                mLastDate = dateTaken;
                mSameSecondCount = 0;
            }
            return result;
        }
    }
}

