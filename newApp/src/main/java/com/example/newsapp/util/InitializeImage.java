package com.example.newsapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.newsapp.R;

import java.util.HashMap;


/**
 * Created by Administrator on 2017/5/29.
 */

public class InitializeImage {
    private static String[] imageNeams = {"a00_01", "a1gongyeyuan", "a1jiaodian9", "a1jiaodian10", "a1jiaodian12",
            "a1jiaodian15", "a1jiaodian9", "a1jiaodian1001", "a1keji1", "a1news17",
            "a2news17", "a2shehui", "a2shehui2", "a3news17", "a4news17",
            "a5news17", "a7yule1", "a7yule2", "a45g", "a82ad", "a702d",
            "a_120", "tuhaofenh", "tuhaofenh101", "wangqisan", "yule003"};
    private static int[] imageResourceId = {R.raw.a00_01, R.raw.a1gongyeyuan, R.raw.a1jiaodian9, R.raw.a1jiaodian10, R.raw.a1jiaodian12,
            R.raw.a1jiaodian15, R.raw.a1jiaodian9, R.raw.a1jiaodian1001, R.raw.a1keji1, R.raw.a1news17,
            R.raw.a2news17, R.raw.a2shehui, R.raw.a2shehui2, R.raw.a3news17, R.raw.a4news17,
            R.raw.a5news17, R.raw.a7yule1, R.raw.a7yule2, R.raw.a45g, R.raw.a82ad, R.raw.a702d,
            R.raw.a_120, R.raw.tuhaofenh, R.raw.tuhaofenh101, R.raw.wangqisan, R.raw.yule003};
    private static String[] headerNames = {"image1", "penguin", "phppg002", "user_icon_pi"};
    private static int[] headerResourceId = {R.drawable.image1, R.drawable.penguin, R.drawable.phppg002, R.drawable.user_icon_pi};
    private static HashMap<String, Bitmap> newHeaderbitmaps;
    private static HashMap<String, Bitmap> newImagebitmaps;


    public static void initializeImage(Context context) {
        newImagebitmaps = new HashMap<String, Bitmap>();
        for (int i = 0; i < imageNeams.length; i++) {
            newImagebitmaps.put(imageNeams[i], BitmapFactory.decodeResource(context.getResources(), imageResourceId[i]));
            int n = 0;
            if (newImagebitmaps.size() >= 7) {
                ExternalStorageUtil.seveImagesToExternal(context, "image", newImagebitmaps, ExternalStorageUtil.JPGTYPE);
                newImagebitmaps = new HashMap<String, Bitmap>();
                n = 0;
            }
            n++;
        }
        ExternalStorageUtil.seveImagesToExternal(context, "image", newImagebitmaps, ExternalStorageUtil.JPGTYPE);
        newHeaderbitmaps = new HashMap<String, Bitmap>();
        for (int i = 0; i < headerNames.length; i++) {
            newHeaderbitmaps.put(headerNames[i], BitmapFactory.decodeResource(context.getResources(), headerResourceId[i]));
            int n = 0;
            if (newImagebitmaps.size() >= 7) {
                ExternalStorageUtil.seveImagesToExternal(context, "header", newHeaderbitmaps, ExternalStorageUtil.PNGTYPE);
                newHeaderbitmaps = new HashMap<String, Bitmap>();
                n = 0;
            }
            n++;
        }
        ExternalStorageUtil.seveImagesToExternal(context, "header", newHeaderbitmaps, ExternalStorageUtil.PNGTYPE);
           /* newImagebitmaps.put("1gongyeyuan", MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.a00_01)));
            //bitmaps.put("a1news17",BitmapFactory.decodeFile(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a1news17).getPath()));
            newImagebitmaps.put("a1gongyeyuan", BitmapFactory.decodeResource(context.getResources(), R.raw.a1gongyeyuan));
            newImagebitmaps.put("a1jiaodian9", BitmapFactory.decodeResource(context.getResources(), a1jiaodian9));
            newImagebitmaps.put("a1jiaodian10", BitmapFactory.decodeResource(context.getResources(), a1jiaodian10));
            newImagebitmaps.put("a1jiaodian12", BitmapFactory.decodeResource(context.getResources(), R.raw.a1jiaodian12));
            newImagebitmaps.put("a1jiaodian15", BitmapFactory.decodeResource(context.getResources(), R.raw.a1jiaodian15));
            newImagebitmaps.put("a1news17", BitmapFactory.decodeResource(context.getResources(), R.raw.a1news17));
            newImagebitmaps.put("a1gongyeyuan", BitmapFactory.decodeResource(context.getResources(), R.raw.a2news17));
            newImagebitmaps.put("a2shehui", BitmapFactory.decodeResource(context.getResources(), R.raw.a2shehui));
            newImagebitmaps.put("a2shehui2", BitmapFactory.decodeResource(context.getResources(), R.raw.a2shehui2));
            newImagebitmaps.put("a1news17", BitmapFactory.decodeResource(context.getResources(), R.raw.a1news17));
            newImagebitmaps.put("a2news17", BitmapFactory.decodeResource(context.getResources(), R.raw.a2news17));
            newImagebitmaps.put("a3news17", BitmapFactory.decodeResource(context.getResources(), R.raw.a3news17));
            newImagebitmaps.put("a4news17", BitmapFactory.decodeResource(context.getResources(), R.raw.a4news17));
            newImagebitmaps.put("a5news17", BitmapFactory.decodeResource(context.getResources(), R.raw.a5news17));
            newImagebitmaps.put("a82ad", BitmapFactory.decodeResource(context.getResources(), R.raw.a82ad));
            newImagebitmaps.put("a702d", BitmapFactory.decodeResource(context.getResources(), R.raw.a702d));
            newImagebitmaps.put("a_120", BitmapFactory.decodeResource(context.getResources(), R.raw.a_120));
            newImagebitmaps.put("tuhaofenh", BitmapFactory.decodeResource(context.getResources(), R.raw.tuhaofenh));
            newImagebitmaps.put("tuhaofenh101", BitmapFactory.decodeResource(context.getResources(), R.raw.tuhaofenh101));
            newImagebitmaps.put("wangqisan", BitmapFactory.decodeResource(context.getResources(), R.raw.wangqisan));
            newImagebitmaps.put("yule003", BitmapFactory.decodeResource(context.getResources(), R.raw.yule003));

            headerbitmaps.put("image1", BitmapFactory.decodeResource(context.getResources(), R.drawable.image1));
            headerbitmaps.put("penguin", BitmapFactory.decodeResource(context.getResources(), R.drawable.penguin));
            headerbitmaps.put("phppg002", BitmapFactory.decodeResource(context.getResources(), R.drawable.phppg002));
            headerbitmaps.put("user_icon_pi", BitmapFactory.decodeResource(context.getResources(), R.drawable.user_icon_pi));
            ExternalStorageUtil.seveImagesToExternal(context,"image",newImagebitmaps,ExternalStorageUtil.JPGTYPE);
            ExternalStorageUtil.seveImagesToExternal(context,"header",headerbitmaps,ExternalStorageUtil.PNGTYPE);*/

    }
}
