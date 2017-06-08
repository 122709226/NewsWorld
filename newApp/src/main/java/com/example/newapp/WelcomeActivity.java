package com.example.newapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.Window;

import com.example.newapp.sqliteUtil.InitializeSQLiteData;
import com.example.newapp.util.InitializeImage;

public class WelcomeActivity extends Activity {
    Boolean isFirstUse = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    SharedPreferences sp = getSharedPreferences("isFirstUse", MODE_PRIVATE);
                    isFirstUse = sp.getBoolean("isFirstUse", true);
                    if (isFirstUse) {
                        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                        InitializeSQLiteData.initializationData();
                        InitializeImage.initializeImage(WelcomeActivity.this);
                        // ExternalStorageUtil.saveExternalImage(WelcomeActivity.this,);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        WelcomeActivity.this.finish();
                    }
                    Editor et = sp.edit();

                    et.putBoolean("isFirstUse", false);
                    et.commit();


                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }



    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

}
