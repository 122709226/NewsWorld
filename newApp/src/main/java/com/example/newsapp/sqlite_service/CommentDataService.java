package com.example.newsapp.sqlite_service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.newsapp.sqlite_util.FeedReaderDbHelper;
import com.example.newsapp.util.MyApplicationContext;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import static com.example.newsapp.util.Constants.LOG_ERROR;

/**
 * Created by Administrator on 2017/5/27.
 */

public class CommentDataService {

    /*"CREATE TABLE `comment` (\n" +
            "  `Id` int(11) NOT NULL,\n" +
            "  `usercount` varchar(200) DEFAULT NULL,\n" +
            "  `username` varchar(50) DEFAULT NULL,\n" +
            "  `usergender` varchar(25) NOT NULL DEFAULT '男',\n" +
            "  `userhead` varchar(200) DEFAULT '空',\n" +
            "  `newid` int(11) DEFAULT NULL,\n" +
            "  `comments` text,\n" +
            "  `praise` int(11) DEFAULT '0',\n" +
            "  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  PRIMARY KEY (`Id`)\n" +
            ")"*/


    /**
     * 从数据库获取特定新闻的评论
     *
     * @param nid              特定新闻的Id
     * @param commentsDataList 装载评论数据的List
     * @param startnid         开始位置
     * @param isFirstLoad      是否初次加载
     * @return create at 2017/6/5 19:59
     * @throws
     * @author create by Administrator
     */
    public static HashMap<String, Object> getSpeCateComments(int nid,
                                                             LinkedList<HashMap<String, Object>> commentsDataList,
                                                             int startnid,
                                                             boolean isFirstLoad) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB = feedReaderDbHelper.getReadableDatabase();
        HashMap<String, Object> returnStateMap = new HashMap<>();
        int tatalCount = getCommentTotalCountByNewsid(nid);
        String[] selectionArges = {nid + ""};
        Cursor cs = writeableDB.query("comment", null, "newid = ?", selectionArges, null, null, "time", startnid + "," + 8);
        if (isFirstLoad)
            commentsDataList.clear();
        if (cs.moveToFirst()) {
            do {
                HashMap<String, Object> newsData = new HashMap<String, Object>();
                newsData.put("Userhead", MyApplicationContext.getMyApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + File.separator + cs.getString(cs.getColumnIndex("userhead")));
                newsData.put("commentsId", cs.getInt(cs.getColumnIndex("Id")));
                newsData.put("commentsPraise", cs.getInt(cs.getColumnIndex("praise")));
                newsData.put("Username", cs.getString(cs.getColumnIndex("username")));
                newsData.put("Usergender", cs.getString(cs.getColumnIndex("usergender")));
                newsData.put("commentsTime", cs.getString(cs.getColumnIndex("time")));
                newsData.put("Comments", cs.getString(cs.getColumnIndex("comments")));

                if (isFirstLoad) {
                    commentsDataList.add(newsData);
                } else {
                    commentsDataList.addLast(newsData);
                }
                Log.e(LOG_ERROR, commentsDataList.toString());
                returnStateMap.put("returnState", "SUCCESS");
                returnStateMap.put("tatalCount", tatalCount);
            } while (cs.moveToNext());
        } else {
            returnStateMap.put("tatalCount", 0);
            returnStateMap.put("returnState", "FALSE");
        }
        writeableDB.close();
        return returnStateMap;

    }

    //将用户评论内容存入数据库
    //new commmentsAsync().execute(newsidString, Count, userNickName, Gender, Head, comment);
    public static Integer insertComments(String nid, String userCount, String nickName, String usergender, String userHead, String comments) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB = feedReaderDbHelper.getReadableDatabase();
        int result = 0;
        int Id = selectMaxIDfromComment() + 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", Id);
        contentValues.put("usercount", userCount);
        contentValues.put("username", nickName);
        contentValues.put("usergender", usergender);
        contentValues.put("userhead", userHead);
        contentValues.put("newid", nid);
        contentValues.put("comments", comments);
        contentValues.put("praise", 0);
       // contentValues.put("time", System.currentTimeMillis());
        Long returnStatewrite = writeableDB.insert("comment", null, contentValues);

        return (returnStatewrite == -1) ? 2 : 1;
    }


    /**
     * @param newsId 新闻类别
     * @return 该类新闻的评论总数量
     */
    public static String getNewsCommentCount(String newsId) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB = feedReaderDbHelper.getReadableDatabase();
        String newsCountComment = "";
        String[] selectionArges = {newsId};
        String[] columns = {"count(praise)"};
        Cursor cs = writeableDB.query("comment", columns, "newid = ?", selectionArges, null, null, null, null);
        if (cs.moveToFirst()) {
            newsCountComment = cs.getString(cs.getColumnIndex("count(praise)"));
        }
        writeableDB.close();
        return newsCountComment;
    }

    public static int selectMaxIDfromComment() {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase readableDB;
        readableDB = feedReaderDbHelper.getReadableDatabase();
        int MaxIDfromComment = -10;
        String getUsetPsdSQL = "select max(Id) from comment";
        Cursor cs = readableDB.rawQuery(getUsetPsdSQL, null);
        if (cs.moveToFirst()) {
            MaxIDfromComment = cs.getInt(cs.getColumnIndex("max(Id)"));
        }
        readableDB.close();
        return MaxIDfromComment;
    }

    /**
     * @param newsid 新闻类别
     * @return 该类新闻的总数量
     */
    public static int getCommentTotalCountByNewsid(int newsid) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB = feedReaderDbHelper.getReadableDatabase();
        int CommentTotalCount = 0;
        String[] selectionArges = {newsid + ""};
        Cursor cs = writeableDB.query("comment", null, "newid = ?", selectionArges, null, null, null, null);
        if (cs.moveToFirst()) {
            CommentTotalCount = cs.getCount();
        }
        writeableDB.close();
        return CommentTotalCount;
    }

    /**
     * 更新保存某个评论的点赞数
     *
     * @param commentsId   评论ID
     * @param praiseNumber 点赞数
     * @return the number of rows affected
     */
    public static int updateCommentsPraise(String commentsId, String praiseNumber) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase readableDB;
        readableDB = feedReaderDbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("praise", praiseNumber);
        String[] args = {commentsId};
        int updateOperateStation = readableDB.update("comment", contentValues, "Id=?", args);
        readableDB.close();
        return (updateOperateStation >= 0) ? 1 : 2;
    }
}
