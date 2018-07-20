package com.cloudminds.camera2.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.cloudminds.camera2.R;

/**
 * Created by wj8706 on 2018/5/7.
 */

public class PermissionUtil {
    private Activity activity;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    //判断当前是否获取到了权限
    public boolean checkSelfPermission(String[] permissions, final int requestCode){
        for (String permission : permissions) {
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.need_permissions), Toast.LENGTH_SHORT).show();
                }
                activity.requestPermissions(new String[]{permission}, requestCode);
            }
        }
        return true;
    }

}
