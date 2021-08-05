package com.idax.ventax.Extra;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.idax.ventax.R;

import static com.idax.ventax.Extra.Constansts.CODE_PERMISSION_INTERNET;
import static com.idax.ventax.Extra.Constansts.CODE_PERMISSION_STORAGE;

public class Permissions {

    /**
     * PERMISSIONS INTERNET
     */
    public static boolean checkPermissionInternet(Context context) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                && PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE));
    }
    public static void requestPermissionsInternet(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE},
                    CODE_PERMISSION_INTERNET);
        else
            Toast.makeText(activity, R.string.require_internet_permissions, Toast.LENGTH_SHORT).show();
    }

    /**
     * PERMISSIONS READ STORAGE
     */
    public static boolean checkPermissionReadStorage(Context context) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE));
    }
    public static void requestPermissionsReadStorage(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODE_PERMISSION_STORAGE);
        else
            Toast.makeText(activity, R.string.require_read_storage_permissions, Toast.LENGTH_SHORT).show();
    }
}
