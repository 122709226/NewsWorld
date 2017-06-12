package com.example.newsapp.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.newsapp.R;

public class GuideActivity extends Activity implements OnClickListener {
     List<View> views;
     ImageView[] dots;
     int currentindex;
     ViewPager vp;
     
     class myPageAdapter extends PagerAdapter{

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));	
			
		//	super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
     }
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_guide);
	    View view1 =	getLayoutInflater().inflate(R.layout.guide_view1, null);
	    View view2 =  getLayoutInflater().inflate(R.layout.guide_view2, null);
	    View view3 =  getLayoutInflater().inflate(R.layout.guide_view3, null);
	    views=new ArrayList<View>();
	    views.add(view1);
	    views.add(view2);
	    views.add(view3);
		
	    Button btn =(Button)view3.findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GuideActivity.this, MainActivity.class);
				startActivity(intent);
				GuideActivity.this.finish();
			  }
		   });
	    
	vp =(ViewPager)findViewById(R.id.myviewpager);
	vp.setAdapter(new myPageAdapter());
	vp.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					dots[currentindex].setEnabled(true);
					dots[position].setEnabled(false);
					currentindex=position;
					
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    initDot();
		    
	}
    	//按小圆点切换
	  public void  initDot() {
		LinearLayout dotlit=  (LinearLayout)findViewById(R.id.dotlist);
		    dots= new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			dots[i]=(ImageView)dotlit.getChildAt(i);
			dots[i].setEnabled(true);
			dots[i].setClickable(true);
			dots[i].setTag(i);
			dots[i].setOnClickListener(this) ;
				
		}
		currentindex=0;
		dots[currentindex].setEnabled(false);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guide, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	//Toast.makeText(GuideActivity.this, "dasdsadf", 2000).show();
	  int position =(Integer)v.getTag();
	  vp.setCurrentItem(position);
	}

}
