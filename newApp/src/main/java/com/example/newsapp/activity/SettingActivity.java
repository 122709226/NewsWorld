package com.example.newsapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.R;


public   class SettingActivity  extends Activity {
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	    final SharedPreferences userData=SettingActivity.this.getSharedPreferences("userDate", 0);
	    
        //退出登录
        TextView outLoging = (TextView)findViewById(R.id.outof_loging);
        outLoging.setClickable(true);
        outLoging.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				  Editor editor =  userData.edit();
		          editor.clear();
		          editor.commit();		
		          //并返回用户界面
			         Intent intent = new Intent(SettingActivity.this, MainActivity.class);
			         intent.putExtra("goToUser", "goToUser");
				     startActivity(intent); 
			}
		});
        //返回图标返回
        ImageView setingBack = (ImageView)findViewById(R.id.image_seting_back);
        setingBack.setClickable(true);
        setingBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//返回用户界面
				 Intent intent = new Intent(SettingActivity.this, MainActivity.class);
		         intent.putExtra("goToUser", "goToUser");
			     startActivity(intent); 
			}
		});
        
        
        // Display the fragment as the main content.
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new SettingsFragment())
//                .commit();
    }
    	
    	
    
    
}
