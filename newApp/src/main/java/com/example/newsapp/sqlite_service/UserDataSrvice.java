package com.example.newsapp.sqlite_service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.newsapp.sqlite_util.FeedReaderDbHelper;
import com.example.newsapp.util.MyApplicationContext;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class UserDataSrvice {

    public void getUserpassWord() {


    }

    /* "CREATE TABLE `user` (\n" +
             "  `Id` int(11) NOT NULL,\n" +
             "  `count` varchar(100) NOT NULL DEFAULT '',\n" +
             "  `gender` varchar(50) DEFAULT NULL,\n" +
             "  `username` varchar(50) NOT NULL DEFAULT '',\n" +
             "  `password` varchar(50) NOT NULL DEFAULT '',\n" +
             "  `head` varchar(100) NOT NULL DEFAULT 'header/user_icon_pi.png',\n" +
             "  PRIMARY KEY (`Id`)\n" +
             ")"*/
    public static int insertUserData(String count, String gender, String username, String password, String head) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB, readableDB;
        writeableDB = feedReaderDbHelper.getWritableDatabase();
        int Id = selectMaxIDfromUser() + 1;
        ContentValues values = new ContentValues();
        values.put("Id", Id);
        values.put("count", count);
        values.put("gender", gender);
        values.put("username", username);
        values.put("password", password);
        values.put("head", head);
        Long state = writeableDB.insert("user", null, values);
        writeableDB.close();
        return (state == -1) ? 4 : 3;
    }

    public static HashMap<String, Object> getUserData(String userCount) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB, readableDB;
        writeableDB = feedReaderDbHelper.getWritableDatabase();
        readableDB = feedReaderDbHelper.getReadableDatabase();
        HashMap<String, Object> userData = new HashMap<String, Object>();
        String getUserDataSQL = "SELECT u.count,u.gender,u.username,u.password,u.head FROM user u WHERE u.count=" + "?";
        String[] selectionArges = {userCount};
        Cursor cs = readableDB.rawQuery(getUserDataSQL, selectionArges);
        if (cs.moveToFirst()) {
            userData.put("Count", cs.getString(cs.getColumnIndex("count")));
            userData.put("Gender", cs.getString(cs.getColumnIndex("gender")));
            userData.put("usrname", cs.getString(cs.getColumnIndex("username")));
            userData.put("password", cs.getString(cs.getColumnIndex("password")));
            userData.put("Head", cs.getString(cs.getColumnIndex("head")));
        }
        readableDB.close();
        return userData;

    }

    public static String getUserPSD(String userCount) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB, readableDB;
        String psd = "";
        writeableDB = feedReaderDbHelper.getWritableDatabase();
        readableDB = feedReaderDbHelper.getReadableDatabase();
        if (userCount == null || userCount.trim() == "") {
            throw new IllegalArgumentException("Parameter info is null");
        }
        String getUsetPsdSQL = "SELECT u.password FROM user u WHERE u.count=" + "?";
        String[] selectionAges = {userCount};
        Cursor cs = readableDB.rawQuery(getUsetPsdSQL, selectionAges);
        if (cs.moveToFirst()) {
            psd = cs.getString(cs.getColumnIndex("password"));
        }
        readableDB.close();
        return psd;
    }

    public static String getUserNickName(String userCount) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB, readableDB;
        writeableDB = feedReaderDbHelper.getWritableDatabase();
        readableDB = feedReaderDbHelper.getReadableDatabase();
        String username = "";
        String getUsetPsdSQL = "SELECT u.username FROM user u WHERE u.count=" + "?";
        String[] selectionArges = {userCount};
        Cursor cs = readableDB.rawQuery(getUsetPsdSQL, selectionArges);
        if (cs.moveToFirst()) {
            username = cs.getString(cs.getColumnIndex("username"));
        }
        readableDB.close();
        return username;
    }

    /**
     * Returns
     * default
     * {@link }
     *
     * @param
     * @return create at 2017/5/18 22:38
     * @author create by Administrator
     */
    public static int checkUserCountExist(String userCount) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB, readableDB;
        writeableDB = feedReaderDbHelper.getWritableDatabase();
        readableDB = feedReaderDbHelper.getReadableDatabase();
        boolean userCountExist = false;
        String getUsetPsdSQL = "SELECT u.Id FROM user u WHERE u.count=" + "?";
        String[] selectionArges = {userCount};
        Cursor cs = readableDB.rawQuery(getUsetPsdSQL, selectionArges);
        if (cs.moveToFirst()) {
            userCountExist = true;
        }
        readableDB.close();
        return (userCountExist) ? 2 : 1;
    }


    public static int selectMaxIDfromUser() {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase readableDB;
        readableDB = feedReaderDbHelper.getReadableDatabase();
        int userCountExist = -10;
        String getUsetPsdSQL = "select max(ID) from user";
        Cursor cs = readableDB.rawQuery(getUsetPsdSQL, null);
        if (cs.moveToFirst()) {
            userCountExist = cs.getInt(cs.getColumnIndex("max(ID)"));
        }
        readableDB.close();
        return userCountExist;
    }

    public static int upasetUserheader(String count, String headerPath) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase readableDB;
        readableDB = feedReaderDbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("head", headerPath);
        String[] args = {count};
        int updateOperateStation = readableDB.update("user", contentValues, "count=?", args);
        readableDB.close();
        return updateOperateStation;
    }

}
