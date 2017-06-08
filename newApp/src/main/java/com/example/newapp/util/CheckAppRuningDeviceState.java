package com.example.newapp.util;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CheckAppRuningDeviceState {

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isIntentCanBeResolve(Context context , Intent intent ){
        return (intent.resolveActivity(context.getPackageManager()) != null)? true :false;
    }

}
