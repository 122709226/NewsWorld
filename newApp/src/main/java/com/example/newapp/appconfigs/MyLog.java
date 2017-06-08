package com.example.newapp.appconfigs;

import android.util.Log;

import com.example.newapp.util.Constants;
import com.example.newapp.util.CreatFileUtil;
import com.example.newapp.util.DateUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2017/6/7.
 */

public class MyLog {
    private static final String TAG = Constants.APP_NAME;
    /**
     * log文件名字
     */
    public static final String filename = Constants.LOG_DIR
            + File.separator + "JIYOsNews_log.txt";

    /**
     * 根据Log不同等级
     */

    public static void v(String text) {
        if (AppConfigs.isDebug()) {
            Log.v(TAG, text);
            write(text, Log.VERBOSE);
        }
    }

    public static void d(String text) {
        if (AppConfigs.isDebug()) {
            Log.d(TAG, text);
            write(text, Log.DEBUG);
        }
    }

    public static void i(String text) {
        if (AppConfigs.isDebug()) {
            Log.i(TAG, text);
            write(text, Log.INFO);
        }
    }

    public static void w(String text) {
        if (AppConfigs.isDebug()) {
            Log.w(TAG, text);
            write(text, Log.WARN);
        }
    }

    public static void e(String text) {
        if (AppConfigs.isDebug()) {
            Log.e(TAG, text);
            write(text, Log.ERROR);
        }
    }

    public static void e(String text, Throwable throwable) {
        StackTraceElement[] elements = throwable.getStackTrace();
        for (StackTraceElement e : elements) {
            e(e.toString());
        }
    }

    /**
     * 将Log写到日志文件中
     *
     * @param text
     * @param level
     */
    private static synchronized void write(String text, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append("["
                + DateUtil.toTime(System.currentTimeMillis(),
                DateUtil.format24) + "]");
        switch (level) {
            case Log.VERBOSE:
                sb.append("[V]\t");
                break;
            case Log.DEBUG:
                sb.append("[D]\t");
                break;
            case Log.INFO:
                sb.append("[I]\t");
                break;
            case Log.WARN:
                sb.append("[W]\t");
                break;
            case Log.ERROR:
                sb.append("[E]\t");
                break;
        }
        //可以任意的访问文件的任何地方
        RandomAccessFile raf = null;
        try {
            //String fileName =filename+ "_"+ DateUtil.toTime(System.currentTimeMillis(),DateUtil.format24);
            CreatFileUtil.createDir(Constants.LOG_DIR);
            String fileName = filename;
            File logFile = new File(fileName);
            if (!logFile.exists()) {
                // Utils.initExternalDir(false);
                logFile.createNewFile();
            }
            raf = new RandomAccessFile(fileName, "rw");
            raf.seek(raf.length());//将文件记录指针定位到pos位置。
            raf.writeBytes(sb.toString() + text + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
