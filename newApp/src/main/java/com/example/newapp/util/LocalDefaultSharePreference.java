package com.example.newapp.util;

import android.content.SharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by Administrator on 2017/5/25.
 */

public class LocalDefaultSharePreference {
    public static boolean getBooleanSharePreference(String getBoolean){
        return getDefaultSharedPreferences(MyApplicationContext.getMyApplicationContext()).getBoolean(getBoolean, false);
    }

    public static String getStringSharePreference(String getString){
        return  getDefaultSharedPreferences(MyApplicationContext.getMyApplicationContext()).getString(getString, "");
    }

    public static void  setBooleanLocalSharePreference(String key,boolean value){
        SharedPreferences.Editor localEditor = getDefaultSharedPreferences(MyApplicationContext.getMyApplicationContext()).edit();
        localEditor.putBoolean(key,value);
        localEditor.commit();
    }

    public static void  setStringLocalSharePreference(String key,String value){
        SharedPreferences.Editor localEditor = getDefaultSharedPreferences(MyApplicationContext.getMyApplicationContext()).edit();
        localEditor.putString(key,value);
        localEditor.commit();
    }

    public static void  setIntLocalSharePreference(String key,int value){
        SharedPreferences.Editor localEditor = getDefaultSharedPreferences(MyApplicationContext.getMyApplicationContext()).edit();
        localEditor.putInt(key,value);
        localEditor.commit();
    }

    public static void  removeLocalSharePreferencedata(String key){
        SharedPreferences.Editor localEditor = getDefaultSharedPreferences(MyApplicationContext.getMyApplicationContext()).edit();
        localEditor.remove(key);
        localEditor.commit();
    }

}
