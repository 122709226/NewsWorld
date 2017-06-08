package com.example.newapp;

import android.app.Application;

import com.example.newapp.util.HttpURLconnectionUtil;
import com.example.newapp.util.LocalDefaultSharePreference;
import com.example.newapp.util.MyApplicationContext;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2017/5/25.
 */

public class NewsApplication extends Application {
    public NewsApplication() {
        super();
    }
    ImageLoader imageLoader; //初始化imageLoader
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplicationContext.setContext(getApplicationContext());
        //必须初始化imageLoader，否则不可用。官方初始化是在application中
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        new Thread(runnable).start();

    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (HttpURLconnectionUtil.getHttpClientUtilInstance().isConnectionOk("textConnection")) {
                LocalDefaultSharePreference.setBooleanLocalSharePreference("ConnectionState",true);
            }
        }
    };
}
