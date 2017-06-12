package com.example.newsapp.sqlite_util;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2017/5/25.
 */

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME_ENTRY= "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String SDFSD_SDF = "";

    }


    private static final String DATA_TYPE_TEXT = " TEXT";
    private static final String DATA_TYPE_INTEGER = "INTEGER";
    private static final String DATA_TYPE_NULL = " NULL";
    private static final String DATA_TYPE_REAL = " REAL";
    private static final String DATA_TYPE_BLOB = " BLOB";
    private static final String DATA_TYPE_date = " date";
    private static final String DATA_TYPE_char  = "char(n) ";  //n长度字符串，不能超过254
    private static final String DATA_TYPE_time = " time";
    private static final String DATA_TYPE_varchar10 = "varchar(10)"; //长度不固定最大字符串长度为n，n不超过4000
    private static final String DATA_TYPE_double = "double";
    private static final String DATA_TYPE_integer = "integer";


    public static final String COMMA_SEP = ",";
    //创建初始空表
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_ENTRY + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + DATA_TYPE_TEXT + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_SUBTITLE + DATA_TYPE_TEXT + " )";

    //创建用户表
    public static final String SQL_CREATE_USRS =
            "CREATE TABLE " + "users"+ " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    "user_count" + DATA_TYPE_TEXT + COMMA_SEP +
                    "user_password varchar(30)," +
                    "user_nickname varchar(30),"+
                    "user_age integer,"+
                    "user_iconURL varchar(30),"+
                    "user_comment_id INTEGER"+" )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME_ENTRY;

}
