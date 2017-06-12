package com.example.newsapp.util;

import android.content.Context;

/**
 * Created by Administrator on 2017/5/25.
 */

public class  MyApplicationContext{
    private static Context context;

    //单例模式
  /* private static class getInstance{
    private static MyApplicationContext myApplicationContext = new MyApplicationContext();
    }

    public static synchronized MyApplicationContext getMyApplicationContexInstance(){
        return getInstance.myApplicationContext;
    }*/

    public static void setContext(Context context) {
        MyApplicationContext.context = context.getApplicationContext();
    }

    public static Context getMyApplicationContext() {
        return context ;
    }

}

