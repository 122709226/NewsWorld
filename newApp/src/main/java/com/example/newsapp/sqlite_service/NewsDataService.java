package com.example.newsapp.sqlite_service;

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
 * Created by Administrator on 2017/5/26.
 */

public class NewsDataService {
    private static SQLiteDatabase writeableDB, readableDB;

    static {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        writeableDB = feedReaderDbHelper.getWritableDatabase();
        readableDB = feedReaderDbHelper.getReadableDatabase();
    }

    /*//创建新闻表
            writeableDB.execSQL("CREATE TABLE `news` (\n" +
                    "  `Id` int(11) NOT NULL,\n" +
                    "  `category` int(11) NOT NULL DEFAULT '0',\n" +
                    "  `title` varchar(50) DEFAULT '',\n" +
                    "  `part` text,\n" +
                    "  `content` text,\n" +
                    "  `picture` varchar(255) DEFAULT NULL,\n" +
                    "  `author` varchar(255) DEFAULT NULL,\n" +
                    "  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,\n" +
                    "  PRIMARY KEY (`Id`)\n" +
                    ")");*/

    /**
     * 获取指定类别的新闻列表
     *
     * @param tid              新闻类型
     * @param startNid         起始编号
     * @param counthaoManyRows 返回数量
     * @param newsDataList     引用传递装置数据
     * @return newsDataList
     */
    public static LinkedList<HashMap<String, Object>> getSomoeNewsDatabyCondition(int tid, int startNid, int counthaoManyRows, int reFreshStation, LinkedList<HashMap<String, Object>> newsDataList) {

        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB = feedReaderDbHelper.getReadableDatabase();
        String[] selectionArges = {tid + ""};
        Cursor cs = writeableDB.query("news", null, "category = ?", selectionArges, null, null, "time", startNid + "," + counthaoManyRows);
        if (reFreshStation == 1)
            newsDataList.clear();
        if (cs.moveToFirst()) {
            do {
                HashMap<String, Object> newsData = new HashMap<String, Object>();
                newsData.put("nid", cs.getString(cs.getColumnIndex("Id")));
                newsData.put("category", cs.getString(cs.getColumnIndex("category")));
                newsData.put("title", cs.getString(cs.getColumnIndex("title")));
                newsData.put("part", cs.getString(cs.getColumnIndex("part")));
                newsData.put("content", cs.getString(cs.getColumnIndex("content")));
                newsData.put("picture", MyApplicationContext.getMyApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + File.separator + cs.getString(cs.getColumnIndex("picture")));
                newsData.put("author", cs.getString(cs.getColumnIndex("author")));
                newsData.put("time", cs.getString(cs.getColumnIndex("time")));

                String newsID = cs.getString(cs.getColumnIndex("Id"));
                newsData.put("news_comments_cuont", CommentDataService.getNewsCommentCount(newsID));
                switch (reFreshStation) {
                    case 1: //第一次加载
                        newsDataList.add(newsData);
                        break;
                    case 2: //不是第一次加载
                        newsDataList.addLast(newsData);

                        break;
                    case 3: //向上刷新新闻
                        break;
                    default:
                        break;
                }
                Log.e(LOG_ERROR, newsDataList.toString());
            } while (cs.moveToNext());
        }
        writeableDB.close();
        return newsDataList;
    }

    /**
     * @param cid 新闻类别
     * @return 该类新闻的总数量
     */
    public static int getNewDataTotalCountByCategory(int cid) {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(MyApplicationContext.getMyApplicationContext());
        SQLiteDatabase writeableDB = feedReaderDbHelper.getReadableDatabase();
        int newsTotalCount = 0;
        String[] selectionArges = {cid + ""};
        Cursor cs = writeableDB.query("news", null, "category = ?", selectionArges, null, null, null, null);
        if (cs.moveToFirst()) {
            newsTotalCount = cs.getCount();
        }
        writeableDB.close();
        return newsTotalCount;
    }

}
