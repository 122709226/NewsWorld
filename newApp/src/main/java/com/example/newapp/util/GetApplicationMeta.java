package com.example.newapp.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.example.newapp.appconfigs.MyLog;

/**
 * Created by Administrator on 2017/6/7.
 */

public class GetApplicationMeta {
    /**
     * 用来获取Application meta data（这个在AndroidManifest中设置）
     */
    public static Object getApplicationMeta(String key) {
        Object applicationMeta = null;

        Context context = MyApplicationContext.getMyApplicationContext();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                applicationMeta = applicationInfo.metaData.get(key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            MyLog.e("can not found the meta in the application attributes:" + key);
        }
        return applicationMeta;
    }


}
