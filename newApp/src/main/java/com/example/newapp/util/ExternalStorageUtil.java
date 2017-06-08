package com.example.newapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.newapp.util.Constants.LOG_ERROR;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ExternalStorageUtil {
    public static final String JPGTYPE = ".jpg";
    public static final String PNGTYPE = ".png";
    public static final String JPEGTYPE = ".jpeg";

    /**
     * @param context
     * @param bitmap
     * @param imageFolder 保存图片所在文件夹
     * @param imageName   保存图片名称
     * @param imageType   保存的图片格式
     * @return 返回-1 表示存储图片失败
     * @throws
     */
    public static int saveExternalImage(Context context, Bitmap bitmap, String imageFolder, String imageName, String imageType) {
        File createDirecter = null;
        try {
            // Get the directory for the app's private pictures directory.
            if (CheckAppRuningDeviceState.isExternalStorageWritable()) {
                createDirecter = new File(context.getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES), imageFolder);
                if (!createDirecter.exists()) {
                    if (!createDirecter.mkdirs()) {
                        Log.e(LOG_ERROR, "Directory not created 目录创建失败");
                    }
                } else {
                    Log.e(LOG_ERROR, "Directory not created 目录已存在");
                }
                /*File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "Haohaoci"+File.separator+"image"+File.separator+"5555555555");
                if (!file.mkdirs()) {
                    MyLog.e("LOG_ERROR", "Directory not created");
                }
                File file3 = new File(Environment.getExternalStorageDirectory(),"com.hongfa.beihuan.haohoace/"+"666666");
                if (!file3.mkdirs()){
                    MyLog.e("LOG_ERROR", "Directory not created  file3");*/

            }
            File imageFile = new File(createDirecter, imageName + imageType);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return 10;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static Bitmap getExternalImage(Context context, String path) {
        Log.e(Constants.LOG_ERROR, "  图片 " + path);
        Bitmap bitmap = null;
        File imagePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + path);
        try {
            FileInputStream fis = new FileInputStream(imagePath);
            bitmap = BitmapFactory.decodeStream(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @param context
     * @param Floder    目标文件夹
     * @param bitmaps   需要处理的Bitmap集
     * @param imageType 储存的文件类型
     * @author create by Administrator
     */
    public static void seveImagesToExternal(Context context, String Floder, HashMap<String, Bitmap> bitmaps, String imageType) {
        FileOutputStream fops = null;
        try {
            File newsImageFloder = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), Floder);
            if (CheckAppRuningDeviceState.isExternalStorageWritable()) {
                if (newsImageFloder.exists()) {
                    Log.e(LOG_ERROR, "newsImageFloder 该文件夹已存在");
                } else {
                    if (!newsImageFloder.mkdir()) {
                        Log.e(LOG_ERROR, "newsImageFloder 该文件夹创建失败");
                    }
                }
            }


            Iterator<String> keySetiterator = bitmaps.keySet().iterator();
            while (keySetiterator.hasNext()) {
                String imageNamegs = keySetiterator.next();
                Bitmap bitmap = bitmaps.get(imageNamegs);
                File imageFiles = new File(newsImageFloder, imageNamegs + imageType);
                fops = new FileOutputStream(imageFiles);
                BufferedOutputStream bops = new BufferedOutputStream(fops);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bops);
                bops.flush();
                bops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
