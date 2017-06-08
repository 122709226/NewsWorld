package com.example.newapp.overwriteAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newapp.R;
import com.example.newapp.util.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/5/28.
 */

public class myBaseAdapter extends BaseAdapter {
    private LinkedList<HashMap<String, Object>> newsDataListForAdapter = new LinkedList<HashMap<String, Object>>();
    private Context context ;
    DisplayImageOptions options;

    public myBaseAdapter(Context context) {
        this.context = context;
    }

    public myBaseAdapter(LinkedList<HashMap<String, Object>> newsDataListForAdapter, Context context) {
        this.newsDataListForAdapter = newsDataListForAdapter;
        this.context = context;
    }

    public void setNewsDataListForAdapter(LinkedList<HashMap<String, Object>> newsDataListForAdapter) {
        this.newsDataListForAdapter = newsDataListForAdapter;
    }

    public LinkedList<HashMap<String, Object>> getNewsDataListForAdapter() {
        return newsDataListForAdapter;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return newsDataListForAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return newsDataListForAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        long nid = Integer.valueOf( newsDataListForAdapter.get(position).get("nid").toString());
        return nid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Homg_Activity", "到布局ui来了 ");
        HashMap<String, Object> map = newsDataListForAdapter.get(position);
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.newslist_item_view, position);

        //标题
        TextView tv_title = (TextView) holder.getView(R.id.textView1);
        tv_title.setText((String) map.get("title"));
        //摘要
        TextView tv_digest = (TextView) holder.getView(R.id.textView2);
        tv_digest.setText((String) map.get("part"));
        tv_digest.setTextColor(0xffadb2ad);
        //来源
        TextView tv_source = (TextView) holder.getView(R.id.textView3);
        tv_source.setText((String) map.get("author"));
        tv_source.setTextColor(0xffadb2ad);
        //评论
        TextView tv_comment = (TextView) holder.getView(R.id.textView4);
        tv_comment.setText(map.get("news_comments_cuont") + "评");
        tv_comment.setTextColor(0xffadb2ad);
        //图片

        //修改图片内容
             /*
             *	下面的加载网络图片中，用到了Android-Universal-Image-Loader框架
	         */
        //显示图片的配置
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_ptr_flip)        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)            // 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)   //图片会缩放到目标大小完全。非常重要，也就是说，这个view有多大，图片就会缩放到多大
//	                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//	                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();

        ImageView imageView = (ImageView) holder.getView(R.id.imageViewNewsList);
        ImageLoader.getInstance().displayImage(Uri.fromFile(new File(map.get("picture").toString())).toString(), imageView, options);
        //ImageLoader.getInstance().
        return holder.getConvertView();

    }
}
