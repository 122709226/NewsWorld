package com.example.newsapp.appconfigs;

import com.nostra13.universalimageloader.utils.L;

/**
 * Created by Administrator on 2017/6/7.
 */

public class AppConfigs {
    //设定一个debug开关，控制是否打印log
    private static boolean init = false;

    private static boolean mDebug = false;

    public static void loadConfig() {
        if(!init) {
            //load the debug switch
            Object debug = GetApplicationMeta.getApplicationMeta("debug");
            if(debug!=null) {
                try {
                    mDebug =  (Boolean)debug;
                } catch (Exception e) {
                    L.e("debug tag is error");
                }
            }
            init = true;
        }
    }

    public static boolean isDebug() {
        if(!init) {
            loadConfig();
        }
        return mDebug;
    }
}
