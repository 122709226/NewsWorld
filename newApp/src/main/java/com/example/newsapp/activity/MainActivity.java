package com.example.newsapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.fragment.HomeFragment;
import com.example.newsapp.widgets.TabFragmentHost;

public class MainActivity extends FragmentActivity {
    private TabFragmentHost mTabHost;

    private final String BASEURL = "http://192.168.1.100:8080/ServertForAppNews/servlet";
    //private final String BASEURL ="http://192.168.56.1:8080/ServertForAppNews/servlet";
    private final String IMAGEBASEURL = "http://192.168.1.100:8080/ServertForAppNews/";
    //private final String IMAGEBASEURL ="http://192.168.56.1:8080/ServertForAppNews/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        mTabHost = (TabFragmentHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtablecontent);

        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(getTabItemView(R.drawable.news_tableitem_seleter, "新闻")),
                HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("image").setIndicator(getTabItemView(R.drawable.reader_tableitem_seleter, "图片")),
                Picture_Activity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("user").setIndicator(getTabItemView(R.drawable.user_tableitem_seleter, "用户")),
                USer_Activity.class, null);
        //去掉分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.getTabWidget().setBackgroundResource(R.drawable.abc_table_widget_bg);
        //当前的默认位置

        Intent intent = getIntent();
        String goToUser = intent.getStringExtra("goToUser");
        if ("goToUser".equals(goToUser)) {
            mTabHost.setCurrentTab(2);
        } else {
            mTabHost.setCurrentTab(0);
        }
    }

    public View getTabItemView(int imageSelectorId, String text) {
        View v = getLayoutInflater().inflate(R.layout.tab_item_view, null);
        ImageView iv = (ImageView) v.findViewById(R.id.main_tabitem_img);
        iv.setImageResource(imageSelectorId);
        TextView tv = (TextView) v.findViewById(R.id.main_tabitem_text);
        tv.setText(text);
        return v;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
