/*
package com.cloudminds.camera2.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.cloudminds.camera2.CameraApp;
import com.cloudminds.camera2.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

*/
/**
 * Created by wj8706 on 2018/5/7.
 *//*


public class FileUtil {
    private static String TAG = "wj-FileUtil";
    public static int SAVE_SUCCESS = 0;
    public static int NOSDCARD_ERROR = 1;
    public static int SAVEFILE_ERROR = 2;
    SimpleDateFormat mFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
    // The date (in milliseconds) used to generate the last name.
    private long mLastDate;

    // Number of names generated for the same second.
    private int mSameSecondCount;

    */
/**
     * 保存照片数据到SD卡
     *
     * @param image
     *//*

    public static int saveImage(Image image) {
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
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        long dateTaken = System.currentTimeMillis();
        String picturePath = generateName(dateTaken) + ".jpg";
        File file = new File(filePath, picturePath);
        try {
            //存到本地相册
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.close();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, file.getName());
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());
            values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, picturePath);
            ContentResolver resolver = CameraApp.getContext().getContentResolver();
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            // resolver.update(uri, values, null, null);
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


    public String generateName(long dateTaken) {
        Date date = new Date(dateTaken);
        String result = mFormat.format(date);

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

*/
