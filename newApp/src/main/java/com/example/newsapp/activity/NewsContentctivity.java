package com.example.newsapp.activity;



import com.example.newsapp.R;
import com.example.newsapp.util.URLImageParser;
import com.example.newsapp.util.UploadDataToServert;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsContentctivity extends Activity {
     
	private String newsId,newsid,title,author,editTime,comments,tontent,catagaryname;
	private String userNickName,Count,Gender,Head;
	InputMethodManager imm;
	private EditText news_reply_edittext;
	private View presentRootView;
	private int havaRealEdittext=0;//是否加载了PopuWindow
	private ScrollView scrollView;
	private Intent intentToCommentDetail;
	private ImageLoader imageLoader; //图片容器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_contctivity);
		//presentRootView=LayoutInflater.from(NewsContentctivity.this).inflate(R.layout.activity_news_contctivity, null);
		presentRootView=findViewById(R.id.NewsContentctivity_Root_mainRelativeLayout);
		scrollView=(ScrollView)findViewById(R.id.NewsContentctivity_scrollView);

		//获得当前用户对象
	   final SharedPreferences userdemo = getSharedPreferences("userDate", 0);  
	   //如果有用户记录 拿出用户对象
       if (userdemo.getBoolean("userDate", false)) {
    	   userNickName =  userdemo.getString("userNickName", "用户名") ;
    	   Count =userdemo.getString("Count", "用户id") ;
    	   Gender = userdemo.getString("Gender", "性别") ;
    	   Head = userdemo.getString("Head", "头像") ;
    	   
         }
		//获取传过来的新闻具体数据
		Intent intent  = this.getIntent();
		Bundle bundle =intent.getExtras();
		// newsId = bundle.getString("newsId");
		 newsid = bundle.getString("newsid");
		 title = bundle.getString("title");
		 author = bundle.getString("author");
		 editTime = bundle.getString("editTime");
		 comments = bundle.getString("comments");
		 tontent = bundle.getString("content");
		 catagaryname = bundle.getString("catagaryname");
		 //要传到评论详情页的数据
		 intentToCommentDetail = new Intent(NewsContentctivity.this , CommentDetailsActivity.class);
		 intentToCommentDetail.putExtra("newsid", newsid);
		 intentToCommentDetail.putExtra("title", title);
		 intentToCommentDetail.putExtra("author", author);
		 intentToCommentDetail.putExtra("editTime", editTime);
		 intentToCommentDetail.putExtra("comments", comments);
			
		//设置头顶的标题
		TextView topCenterTitle = (TextView)findViewById(R.id.topCentaiTitle);
		topCenterTitle.setText("       "+catagaryname+"新闻");
		topCenterTitle.setGravity(Gravity.CENTER_HORIZONTAL);
		//新闻标题
		TextView newsTitle = (TextView)findViewById(R.id.textTitle);
		newsTitle.setText(title);
		//作者和时间
		TextView newsAuthor = (TextView)findViewById(R.id.textAuthor);
		newsAuthor.setText(author+"    "+editTime);
		newsAuthor.setTextColor(0xffadb2ad);
		newsAuthor.setTextSize(13);
		//评论数量
		TextView newsComment = (TextView)findViewById(R.id.textCommentNumber);
		newsComment.setText(comments+"评");
		newsComment.setTextColor(0xffadb2ad);
		newsComment.setTextSize(13);
		
		
		//新闻内容
		TextView newsContemt = (TextView)findViewById(R.id.textNewsCntent);
		newsContemt.setText(Html.fromHtml(tontent.replace("\\n",""), new URLImageParser(newsContemt, NewsContentctivity.this ), null));
		//假评论输入框 是个button
		Button editComment_button = (Button)findViewById(R.id.editComment_button);
		//真的评论输入框
	    //	View linerla LayoutInflater.from(NewsContentctivity.this).inflate(R.layout.edit_comment_replay, null);
		 news_reply_edittext = (EditText)findViewById(R.id.news_reply_edittext);

		//拿出真的评论布局
		final LinearLayout news_reply_edit_layout =(LinearLayout)findViewById(R.id.news_reply_edit_layout);
		//拿出假的评论布局
		final RelativeLayout fakerelativeLayout = (RelativeLayout)findViewById(R.id.feckless_comment_relativeLayout);
		//点击显示真的评论输入框
		editComment_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				news_reply_edit_layout.setVisibility(View.VISIBLE);
				fakerelativeLayout.setVisibility(View.GONE);
				//显示软键盘
				imm=(InputMethodManager)news_reply_edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				news_reply_edittext.requestFocus();
				imm.showSoftInput(news_reply_edittext, 0);
				havaRealEdittext=2;
				
			}
		});
		

		//真的评论提交按钮
		Button news_reply_post = (Button)findViewById(R.id.news_reply_post);
		news_reply_post.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果有登录的用户对象，则储存评论
				if (userdemo.getBoolean("userDate", false)) {
					
					//将用户评论信息存入数据库
					String comments =news_reply_edittext.getText().toString();
					if (!comments.isEmpty()) {
						new commmentsAsyc().execute(newsid,Count,userNickName,Gender,Head,comments); 
						news_reply_edit_layout.setVisibility(View.GONE);
						fakerelativeLayout.setVisibility(View.VISIBLE);
						// 评论成功 隐藏软键盘
						imm.hideSoftInputFromWindow(news_reply_edittext.getWindowToken() ,0);
						//跳转到评论区
						startActivity(intentToCommentDetail);
					}
				}else {
					//没登录去登录界面
					Intent intent = new Intent(NewsContentctivity.this, LoginngActivity.class);
					startActivity(intent);

				}
			}
		});
		
		
		//输入软键盘消失改变界面
		presentRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int heightDiff = presentRootView.getRootView().getHeight() - presentRootView.getHeight();	
				if (heightDiff > 200) {
					// 如果高度差超过100像素，就很有可能是有软键盘...scrollToBottom();
					news_reply_edit_layout.setVisibility(View.VISIBLE);
					fakerelativeLayout.setVisibility(View.GONE); 
				   } else {
									if (havaRealEdittext==2) {
										news_reply_edit_layout.setVisibility(View.GONE);
										fakerelativeLayout.setVisibility(View.VISIBLE);
									}
		                      }
			}
		});
		
		scrollView.setOnTouchListener(new OnTouchListener() {
		    @Override
			public boolean onTouch(View v, MotionEvent event) {
		    	switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					int height = presentRootView.findViewById(R.id.edit_comments_reply_faame).getTop();
					int y = (int) event.getY();
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
							if (y < height&&havaRealEdittext==2) {
								//  隐藏软键盘
								imm.hideSoftInputFromWindow(news_reply_edittext.getWindowToken() ,0);
							}
					  }	
					break;
				default:
					break;
				}	
				return false;
			}
		});
		//点击回复外部改变界面
		presentRootView.setOnTouchListener(new OnTouchListener() {
			@Override
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {

				int height = presentRootView.findViewById(R.id.edit_comments_reply_faame).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (y < height&&havaRealEdittext==2) {
						// 隐藏软键盘
						imm.hideSoftInputFromWindow(news_reply_edittext.getWindowToken() ,0);
					}
				}
				return true;
			}
		});
		
		//commentEdit.clearFocus();
		//评论跳转按钮内容
		TextView buttonShowComments =(TextView)findViewById(R.id.buttonShowComments);
		buttonShowComments.setText(comments+"评");
		//评论跳转按钮小图标
		ImageView imageImageComment = (ImageView)findViewById(R.id.imageImageComment);
		imageImageComment.setImageResource(R.drawable.comment_lajiao2_icon);
		//设置按返回键返回
		ImageView image_itlebar_btn_back = (ImageView)findViewById(R.id.image_itlebar_btn_back);
		image_itlebar_btn_back.setClickable(true);
		image_itlebar_btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsContentctivity.this, MainActivity.class);
				startActivity(intent);
			  }
		 });
		
		//评论跳转块跳转
		RelativeLayout  klickGotoCommentDetails =(RelativeLayout) findViewById(R.id.relativeLayout3);
		klickGotoCommentDetails.setClickable(true);
		klickGotoCommentDetails.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//跳转到评论区
				startActivity(intentToCommentDetail);
			}
		});
		
	 }
	
	
	//异步任务
	private class commmentsAsyc extends AsyncTask<String, Integer, Integer>{

		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case 1:
				Toast.makeText(NewsContentctivity.this, "发表成功", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
			super.onPostExecute(result);
		}

		@Override
		protected Integer doInBackground(String... params) {
			Log.d("Homg_Activity",params[0].toString()+params[1]+ params[2]+params[3]+params[4]+params[5]);
			//Integer result = saveComments(params[0], params[1], params[2], params[3], params[4], params[5]);
			UploadDataToServert dafDataToServert = UploadDataToServert.createInstance();
			Integer result = dafDataToServert. saveComments(params[0], params[1], params[2], params[3], params[4], params[5]);
			return result ;
		}
}
	
	 
//	   //将用户评论内容存入数据库
//		 public Integer saveComments(String nid,String userCount,String nickName,String usergender,String userHead, String comments){
//			     MyLog.d("Homg_Activity", "到数据处理来了 ");
//			     int result =0;   
//			     String url= Constant.BASEURL+"/ServertForComments" ;  
//			     String params = "nid="+nid+"&userCount="+userCount+"&nickName="+nickName+"&usergender="+usergender+"&userHead="+userHead+"&comments="+comments+"&difference="+"saveing";
//				 	
//			       try {
//					//实例Http链接
//						SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
//						MyLog.d("Homg_Activity", "到实例化链接来了来了 "+comments+nid);
//						//发送请求
//						String jsonstr = httpUtil.httpPost(url,params);
//						//把数据转换为fastjson类型
//						JSONObject  myjsonObject = (JSONObject)JSON.parse(jsonstr);
//						 result =Integer.valueOf( myjsonObject.get("result").toString()) ;
//						
//						
//				} catch (Exception e) {
//					result=1;
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//					return result;
//		 }
//		 
		 
		 
		 
		 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_contctivity, menu);
		return true;
	}

}
