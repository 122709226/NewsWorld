package com.example.newapp;

import java.util.ArrayList;

import com.example.newapp.util.PubuAdapter;
import com.huewu.pla.lib.MultiColumnPullToRefreshListView;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Picture_Activity extends Fragment {
	private MultiColumnPullToRefreshListView mypubuview;  
	private Context context;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		  ArrayList<String> imageList = new ArrayList<String>();
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/1.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/2.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/3.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/4.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/5.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/6.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/7.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/8.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/9.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/10.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/11.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/12.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/13.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/14.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/15.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/16.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/17.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/18.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/19.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/20.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/21.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/22.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/23.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/24.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/25.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/26.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/27.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/28.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/29.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/31.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/32.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/33.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/34.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/354/35.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/1.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/2.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/3.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/4.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/5.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/6.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/7.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/8.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/9.jpg");
			imageList.add("http://t2.27270.com/uploads/tu/201512/331/10.jpg");
			
		PubuAdapter adapter = new PubuAdapter(imageList,context);
		mypubuview.setAdapter(adapter);
		
		mypubuview.setOnRefreshListener(new MultiColumnPullToRefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				//下拉刷新要做的事
				
				//刷新完成后记得调用这个
				mypubuview.onRefreshComplete();
			}
		}); 
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		context=Picture_Activity.this.getActivity();
		//初始化图片加载库
		 DisplayImageOptions defaultOptions =
			        new DisplayImageOptions.Builder()
			            .cacheOnDisc(true)//图片存本地
			            .cacheInMemory(true)
			            .displayer(new FadeInBitmapDisplayer(50))
			            .bitmapConfig(Bitmap.Config.RGB_565)
			            .imageScaleType(ImageScaleType.EXACTLY) // default
			            .build();
			    ImageLoaderConfiguration config =new ImageLoaderConfiguration.Builder(context.getApplicationContext())
			            .memoryCache(new UsingFreqLimitedMemoryCache(16 * 1024 * 1024))
			            .defaultDisplayImageOptions(defaultOptions).build();
			    ImageLoader.getInstance().init(config);
		
		super.onCreate(savedInstanceState);
	}
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		    View view=inflater.inflate(R.layout.picture_activaty, container,false);
		    
		    mypubuview = (MultiColumnPullToRefreshListView)view.findViewById(R.id.pubulist);
		  
     		return view;
	}
             
}
