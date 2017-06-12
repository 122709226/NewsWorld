package com.example.newsapp.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

import static java.io.File.separator;


public class URLImageParser implements Html.ImageGetter {
    TextView mTextView;
    Context context;

    public URLImageParser(TextView textView, Context context) {
        this.mTextView = textView;
        this.context = context;
    }


    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        Log.d("ChapterActivity", Constants.IMAGEBASEURL + source);
        final Matrix matrix = new Matrix();

        String externalImagePath = Uri.fromFile(new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + separator + source.split("\"")[1].replace("\\",""))).toString();
        Log.d("ChapterActivity", externalImagePath);
        String thisURL = (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) ? Constants.IMAGEBASEURL + source : externalImagePath;
        ImageLoader.getInstance().loadImage(thisURL, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // urlDrawable.bitmap = loadedImage;
                //设置图片高度为原始图片高度
                // urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                //设置图片宽度为屏幕宽度
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display defaultdisDisplay = wm.getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                defaultdisDisplay.getMetrics(metrics);


                //获得屏幕宽度
                float screenWith = metrics.widthPixels;
                float scaleWith = screenWith / loadedImage.getWidth();
                //matrix.postScale(scaleWidth, scaleWidth);
                matrix.setScale(scaleWith, 1.0f);
                // urlDrawable.setBounds(0, 0, wm.getDefaultDisplay().getWidth(), loadedImage.getHeight());
                Bitmap changiImg = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage.getHeight(), matrix, true);
                urlDrawable.bitmap = changiImg;
                urlDrawable.setBounds(0, 0,loadedImage.getWidth(), loadedImage.getHeight());
                // 解决图文重叠
                mTextView.invalidate();//请求重新draw() textview
                mTextView.setText(mTextView.getText()); //重新设置textview的值
            }
        });
        return urlDrawable;
    }
}

