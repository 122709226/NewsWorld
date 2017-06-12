package com.example.newsapp.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.example.newsapp.R;

public class PictureDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_detail, menu);
		return true;
	}

}
