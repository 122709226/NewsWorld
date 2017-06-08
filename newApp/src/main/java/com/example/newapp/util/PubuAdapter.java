package com.example.newapp.util;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newapp.PictureDetailActivity;
import com.example.newapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class PubuAdapter extends BaseAdapter {

	ArrayList<String> list;
	Context context;
	private Drawable drawable;
	
	public PubuAdapter(ArrayList<String> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		this.drawable = context.getResources().getDrawable(R.drawable.load_default);
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.image_item, position);

		final ImageView iv_icon = (ImageView)holder.getView(R.id.row_icon);
		final ProgressBar pdload = (ProgressBar)holder.getView(R.id.pb_load);
		String url = list.get(position);
		ImageLoader.getInstance().displayImage(url, iv_icon,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						
						//这儿初先初始化出来image所占的位置的大小，先把瀑布流固定住，这样瀑布流就不会因为图片加载出来后大小变化了
						//LayoutParams lp = (LayoutParams) holder.ivIcon.getLayoutParams();
						//多屏幕适配
						//int dWidth = 480;
						//int dHeight = 800;
						//float wscale = dWidth / 480.0f;
						//float hscale = dHeight / 800.0f;
						//lp.height = (int) (yourImageHeight * hscale);
						//lp.width = (int) (yourImageWidth * wscale);
						//holder.ivIcon.setLayoutParams(lp);
						
						iv_icon.setImageDrawable(drawable);
						pdload.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
							break;
						case DECODING_ERROR:
							message = "can not be decoding";
							Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
							break;
						case OUT_OF_MEMORY:
							message = "内存不足";
							Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
							break;
						case UNKNOWN:
							message = "Unknown error";
							Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
							break;
						}
						pdload.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						pdload.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingCancelled(String paramString,
							View paramView) {
					}
				});
		
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle msg = new Bundle();
				msg.putInt("aid", (Integer)iv_icon.getTag());
				Intent i = new Intent(context, PictureDetailActivity.class);
				i.putExtra("msg", msg);
				//context.startActivity(i);
				//Toast.makeText(context, "弹出新信息", Toast.LENGTH_SHORT).show();
			}
		});
		
		return holder.getConvertView();
	}

}

