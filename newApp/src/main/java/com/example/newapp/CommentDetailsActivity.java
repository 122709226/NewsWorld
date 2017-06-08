package com.example.newapp;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.newapp.sqliteService.CommentDataService;
import com.example.newapp.util.Constants;
import com.example.newapp.util.DeepCopyList;
import com.example.newapp.util.EditCommentPopupWindow;
import com.example.newapp.util.LocalDefaultSharePreference;
import com.example.newapp.util.SyncHttpUtil;
import com.example.newapp.util.UploadDataToServert;
import com.example.newapp.util.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentDetailsActivity extends Activity implements OnClickListener {

    private int tatalCount;// 该新闻的总评论数
    // private int loadCount = 5; //每次加载新闻的数量
    private int firstLoad = 0;// 加载成功
    private int startPosition = 0; // 加载新闻的起始位置
    private int totalNum;// 当前返回的总记录数
    private int havaPopuWindow = 0;//是否加载了PopuWindow

    private LinkedList<HashMap<String, Object>> commentsDataList; // 参数传递新闻数据集合
    private LinkedList<HashMap<String, Object>> commentsDataListForAdapter; // 给Adapter的新闻数据集合
    private String newsidString, title, author, editTime, comments;
    private String userNickName, Count, Gender, Head;
    private int newsid;
    private PullToRefreshListView mPullToRefreshListView; // 可下拉的listview
    private myBaseAdapter sAdapter;
    private EditCommentPopupWindow editWindow;
    private Context context;
    private View activity_comment_details_View_RelativeLayout;
    private EditText realEditText; //真的回复编辑框
    private InputMethodManager imm;
    private CheckBox selector_support_thumb_icon_RadioButton;//点赞的CheckBox
    private TextView digit_support_thumb_icon_textView;//显示点赞Textview
    private Intent intentToRefresh;
    private SharedPreferences userdemo;//获得当前用户对象
    DisplayImageOptions options;
    ImageLoader imageLoader;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_details);
        // 必须初始化imageLoader，否则不可用。官方初始化是指application中
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration
                .createDefault(CommentDetailsActivity.this));
        context = CommentDetailsActivity.this;
        //根布局文件
        activity_comment_details_View_RelativeLayout = (RelativeLayout) findViewById(R.id.activity_comment_details_View_RelativeLayout);

        //获得当前用户对象
        userdemo = getSharedPreferences("userDate", 0);
        //如果有用户记录 拿出用户对象
        if (userdemo.getBoolean("userDate", false)) {
            userNickName = userdemo.getString("userNickName", "用户名");
            Count = userdemo.getString("Count", "用户id");
            Gender = userdemo.getString("Gender", "性别");
            Head = userdemo.getString("Head", "头像");

        }

        Bundle bundle = getIntent().getExtras();
        newsidString = bundle.getString("newsid");
        newsid = Integer.valueOf(newsidString);
        title = bundle.getString("title");
        author = bundle.getString("author");
        editTime = bundle.getString("editTime");
        comments = bundle.getString("comments");
        //刷新要传递的的数据
        intentToRefresh = new Intent(context, CommentDetailsActivity.class);
        intentToRefresh.putExtra("newsid", newsid);
        intentToRefresh.putExtra("title", title);
        intentToRefresh.putExtra("author", author);
        intentToRefresh.putExtra("editTime", editTime);
        intentToRefresh.putExtra("comments", comments);

        // 标题
        TextView Comment_itextTitle = (TextView) findViewById(R.id.Comment_itextTitle);
        Comment_itextTitle.setText(title);
        // 作者和时间
        TextView newsAuthor = (TextView) findViewById(R.id.Comment_itextAuthor);
        newsAuthor.setText(author + "    " + editTime);
        newsAuthor.setTextColor(0xffadb2ad);
        newsAuthor.setTextSize(13);
        // 评论数量
        TextView newsComment = (TextView) findViewById(R.id.Comment_itextCommentNumber);
        newsComment.setText(comments + "评");
        newsComment.setTextColor(0xffadb2ad);
        newsComment.setTextSize(13);
        // 返回图标
        ImageView Comment_image_itlebar_btn_back = (ImageView) findViewById(R.id.Comment_image_itlebar_btn_back);
        Comment_image_itlebar_btn_back
                .setOnClickListener(CommentDetailsActivity.this);
        // 返回原文按钮
        Button Comment_image_backto_content = (Button) findViewById(R.id.Comment_image_backto_content);
        Comment_image_backto_content
                .setOnClickListener(CommentDetailsActivity.this);
        // 假的评论编辑框
        Button Comment_fale_editComment_button = (Button) findViewById(R.id.Comment_fale_editComment_button);
        Comment_fale_editComment_button
                .setOnClickListener(CommentDetailsActivity.this);

        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.comments_pull_refresh_list);
        mPullToRefreshListView.setMode(Mode.BOTH);// 向下拉刷新
        // 下拉提示
        ILoadingLayout startlabels = mPullToRefreshListView
                .getLoadingLayoutProxy();
        startlabels.setLastUpdatedLabel("下拉刷新...");
        startlabels.setPullLabel("");// 刚下拉时显示的提示
        startlabels.setRefreshingLabel("正在刷新...");// 刷新时的提示
        startlabels.setReleaseLabel("放开刷新...");// 下来到达一定距离时，显示的提示
        // 上拉提示
        ILoadingLayout endLabels = mPullToRefreshListView
                .getLoadingLayoutProxy(false, true);
        endLabels.setLastUpdatedLabel("上拉刷新...");
        endLabels.setPullLabel("");// 刚下拉时显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时的提示
        endLabels.setReleaseLabel("放开刷新...");// 下来到达一定距离时，显示的提示

        // 设置每项点击监听
        // *************************************************************************************************************
        mPullToRefreshListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        System.out.println(commentsDataList.size());
                        System.out.println(position);
                        // HashMap<String,Object> map =
                        // commentsDataList.get(position-1);

//						Toast.makeText(CommentDetailsActivity.this,
//								id + "被点击了", Toast.LENGTH_SHORT).show();
                    }
                });
        // 设置刷新事件
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {
            // 向下拉
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                if (commentsDataListForAdapter.size() > 0) {
                    startPosition = 0;
                    new getCommentDataTask().execute(newsid, startPosition,true);
                } else {
                    // mPullToRefreshListView的bug ，需要延迟一秒后再调用onRefresComplete方法，
                    // 不然会卡在正在加载中，onRefresComplete方法变得无效果；
                    mPullToRefreshListView.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                }
            }

            // 向上拉
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                Log.d("Homg_Activity", "总记录数" + tatalCount);
                Log.d("Homg_Activity", "当前位置" + commentsDataListForAdapter.size());
                if (commentsDataListForAdapter.size() < tatalCount) {
                    startPosition = commentsDataListForAdapter.size();
                    new getCommentDataTask().execute(newsid, startPosition,
                            false);
                } else {
                    // mPullToRefreshListView的bug ，需要延迟一秒后再调用onRefresComplete方法，
                    // 不然会卡在正在加载中，onRefresComplete方法变得无效果；
                    mPullToRefreshListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });

        // 从数据库中加载数据
        if (commentsDataListForAdapter == null) {
            commentsDataList = new LinkedList<HashMap<String, Object>>();
            new getCommentDataTask().execute(newsid, startPosition, true);
        }

        sAdapter = new myBaseAdapter();
        // 获取真正的listview
        ListView actualLIstView = mPullToRefreshListView.getRefreshableView();
        // 设置adapter
        actualLIstView.setAdapter(sAdapter);

        activity_comment_details_View_RelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activity_comment_details_View_RelativeLayout.getRootView().getHeight() - activity_comment_details_View_RelativeLayout.getHeight();
                if (heightDiff > 200) {
                    // 如果高度差超过100像素，就很有可能是有软键盘...scrollToBottom();
                } else {
                    if (havaPopuWindow == 2) {
                        //关闭PopuWindow
                        editWindow.dismiss();
                        // 关闭PopuWindow  隐藏软键盘
                        imm.hideSoftInputFromWindow(realEditText.getWindowToken(), 0);
                    }
                }
            }
        });


    }

    // 监听事件
    @SuppressWarnings("static-access")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回图标
            case R.id.Comment_image_itlebar_btn_back:
                CommentDetailsActivity.this.finish();
                break;
            //返回原文
            case R.id.Comment_image_backto_content:
                CommentDetailsActivity.this.finish();
                break;
            case R.id.Comment_fale_editComment_button:

                editWindow = new EditCommentPopupWindow(context, itemsOnClick);
                editWindow.showAtLocation(
                        activity_comment_details_View_RelativeLayout,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                //实例 真的回复编辑框
                realEditText = editWindow.getEdit_comment_popupwindow_edittext();
                //实例InputMethodManager
                imm = (InputMethodManager) realEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                realEditText.requestFocus();
                imm.showSoftInputFromInputMethod(realEditText.getWindowToken(), imm.SHOW_FORCED);
                imm.showSoftInput(realEditText, 0);
                havaPopuWindow = 2;
                break;
            default:
                break;
        }
    }


    // 为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            editWindow.dismiss();
            switch (v.getId()) {
                // 提交评论按钮
                case R.id.edit_comment_popupwindow_button:

                    //如果有登录的用户对象，则储存评论
                    if (userdemo.getBoolean("userDate", false)) {
                        String comment = realEditText.getText().toString();
                        //将评论数据存入数据库
                        new commmentsAsync().execute(newsidString, Count, userNickName, Gender, Head, comment);
                    } else {
                        //没登录去登录界面
                        Intent intent = new Intent(context, LoginngActivity.class);
                        startActivity(intent);
                    }
                    break;
//             case R.id.selector_support_thumb_icon_RadioButton:
//            	 Toast.makeText(CommentDetailsActivity.this, "点赞111111", Toast.LENGTH_LONG).show(); 
//            	
//            	 break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comment_details, menu);
        return true;
    }

    // 构造baseAdap
    private class myBaseAdapter extends BaseAdapter {
        LinkedList<HashMap<String, Object>> adapterCommentsDataList = new LinkedList<>();

        public void setCommentsDataList(LinkedList<HashMap<String, Object>> commentsDataList) {
            this.adapterCommentsDataList = commentsDataList;
        }

        public myBaseAdapter() {
        }

        public myBaseAdapter(
                LinkedList<HashMap<String, Object>> commentsDataList) {
            this.adapterCommentsDataList = commentsDataList;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return adapterCommentsDataList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return adapterCommentsDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            long nid = (Integer) adapterCommentsDataList.get(position).get(
                    "commentsId");
            return nid;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("Homg_Activity", "到布局ui来了 ");
            final HashMap<String, Object> map = adapterCommentsDataList.get(position);
            ViewHolder holder = ViewHolder.get(CommentDetailsActivity.this,
                    convertView, parent, R.layout.comments_item_view, position);

            // 用户名
            TextView tv_title = (TextView) holder
                    .getView(R.id.comments_userNickName_textView);
            tv_title.setText((String) map.get("Username"));
            // 时间
            TextView tv_digest = (TextView) holder
                    .getView(R.id.comments_Write_time_textView);
            tv_digest.setText((String) map.get("commentsTime"));
            tv_digest.setTextColor(0xffadb2ad);
            // 评论内容
            TextView comments_commentsContent_textViewtext = (TextView) holder
                    .getView(R.id.comments_commentsContent_textViewtext);
            comments_commentsContent_textViewtext.setText((String) map
                    .get("Comments"));
            // tv_digest.setTextColor(0xffadb2ad);

            /*
             * 下面的加载网络图片中，用到了Android-Universal-Image-Loader框架
			 */
            // 显示图片的配置
            // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.default_ptr_flip) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // 图片会缩放到目标大小完全。非常重要，也就是说，这个view有多大，图片就会缩放到多大
                    // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                    // .displayer(new
                    // FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                    .build();

            // 用户头像
            ImageView comments_head_icon_imageView = (ImageView) holder
                    .getView(R.id.comments_head_icon_imageView);
            ImageLoader.getInstance().displayImage(
                    Uri.fromFile(new File(map.get("Userhead").toString())).toString(),
                    comments_head_icon_imageView, options);
            // 性别图标
            ImageView comments_user_gender_imageView = (ImageView) holder
                    .getView(R.id.comments_user_gender_imageView);
            if (map.get("Usergender").equals("男")) {
                comments_user_gender_imageView
                        .setImageResource(R.drawable.male_128);
            } else if (map.get("Usergender").equals("女")) {
                comments_user_gender_imageView
                        .setImageResource(R.drawable.female_128);
            }


            //点赞数字的显示
            //点赞图标 and 数字的显示
            selector_support_thumb_icon_RadioButton = (CheckBox) holder
                    .getView(R.id.selector_support_thumb_icon_CheckBox);
            selector_support_thumb_icon_RadioButton.setTextColor(0xffadb2ad);
            selector_support_thumb_icon_RadioButton.setText(map.get("commentsPraise").toString());
            SharedPreferences editPraise = context.getSharedPreferences("editPraise", Context.MODE_PRIVATE);
            final boolean jugeIsChecked = editPraise.getBoolean("isChecked" + map.get("commentsId"), false);
            if (jugeIsChecked) {
                selector_support_thumb_icon_RadioButton.setChecked(true);
            }

            selector_support_thumb_icon_RadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    //点赞数字
                    int praiseNumber = (Integer) map.get("commentsPraise");
                    if (isChecked && jugeIsChecked) {
                        praiseNumber = praiseNumber + 0;
                    } else if (isChecked) {
                        praiseNumber += 1;
                        SharedPreferences editPraise = context.getSharedPreferences("editPraise", Context.MODE_PRIVATE);
                        Editor editor = editPraise.edit();
                        editor.putBoolean("isChecked" + map.get("commentsId"), true);
                        editor.commit();
                    } else if (jugeIsChecked) {
                        praiseNumber -= 1;
                    }
                    new saveCommentPraiseAsyncTask().execute(map.get("commentsId").toString(), String.valueOf(praiseNumber));
                    buttonView.setText(String.valueOf(praiseNumber));
                }
            });
            // Toast.makeText(context, String.valueOf(changePraiseNumber)+"是是是", Toast.LENGTH_SHORT).show();
            return holder.getConvertView();

        }
    }


    //异步任务 存入 评论
    private class commmentsAsync extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case 1:
                    Toast.makeText(CommentDetailsActivity.this, "发表成功", Toast.LENGTH_LONG).show();
                    // 已经修改数据通知更新ui
                    sAdapter.notifyDataSetChanged();
                    // list 跟新完成调用onRrfreshConp;
                    mPullToRefreshListView.onRefreshComplete();
                    break;
                default:
                    break;
            }
            super.onPostExecute(result);
        }
        @Override
        protected Integer doInBackground(String... params) {
            Log.d("Homg_Activity", params[0].toString() + params[1] + params[2] + params[3] + params[4] + params[5]);
            // params[0] newsidString, params[1] Count, params[2] userNickName, params[3] Gender,params[4] Head,params[5] comment
            if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                UploadDataToServert dafDataToServert = UploadDataToServert.createInstance();
                Integer result = dafDataToServert.saveComments(params[0], params[1], params[2], params[3], params[4], params[5]);
                return result;
            } else {
                return CommentDataService.insertComments(params[0], params[1], params[2], params[3], params[4], params[5]);
            }
        }
    }


    /**
     * 异步任务 拿出评论
     *
     * @author create by Administrator
     */
    private class getCommentDataTask extends
            AsyncTask<Object, Integer, HashMap> {
        @Override
        protected HashMap doInBackground(Object... params) {
            Log.d("Homg_Activity", "到异步任务来了 ");
            //检查是否连接
            if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                // 参数0表示新闻Id，参数1表示起始位置，参数2表示是否第一次加载
                HashMap<String, String> result = getSpeCateComments((Integer) params[0], commentsDataList, (Integer) params[1], (Boolean) params[2]);
                return result;
            } else {
                HashMap<String, Object> result = CommentDataService.getSpeCateComments((Integer) params[0], commentsDataList, (Integer) params[1], (Boolean) params[2]);
                tatalCount = Integer.valueOf(result.get("tatalCount").toString());
                return result;
            }
        }

        @Override
        protected void onPostExecute(HashMap result) {
            // 已经修改数据通知更新ui
            //System.out.println(result);
            System.out.println("6999999999999999999999999999996");
            if ("SUCCESS".equals(result.get("returnState").toString())) {
                commentsDataListForAdapter = DeepCopyList.deepCopyList(commentsDataList);
                sAdapter.setCommentsDataList(commentsDataListForAdapter);
                sAdapter.notifyDataSetChanged();
                // list 跟新完成调用onRrfreshConp;ete
                mPullToRefreshListView.onRefreshComplete();
            }
            super.onPostExecute(result);
        }

    }

    /**
     * 保存点赞的异步任务
     */
    private class saveCommentPraiseAsyncTask extends
            AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Log.d("Homg_Activity", "到异步任务来了 ");
            if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                UploadDataToServert dafDataToServert = UploadDataToServert.createInstance();
                //params[0] commentsId, params[1] praiseNumber
                int result = dafDataToServert.savePraiseNumber(params[0], params[1]);
                return result;
            } else {
                return CommentDataService.updateCommentsPraise(params[0], params[1]);

            }
        }
        @Override
        protected void onPostExecute(Integer result) {
            // 已经修改数据通知更新ui
            //System.out.println(result);
            switch (result) {
                case 1:
                    //点赞数字变化
                    Toast.makeText(CommentDetailsActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
//				// 已经修改数据通知更新ui
//	              sAdapter.notifyDataSetChanged();
//			     // list 跟新完成调用onRrfreshConp;
//				  mPullToRefreshListView.onRefreshComplete();
                    break;
                default:
                    break;
            }
            super.onPostExecute(result);
        }

    }

    // 从数据库获取特定新闻的评论
    public HashMap<String, String> getSpeCateComments(int nid,
                                                      LinkedList<HashMap<String, Object>> commentsDataList,
                                                      int startnid,
                                                      boolean isFirstLoad) {
        Log.d("Homg_Activity", "到读取数据来了来了 ");
        HashMap<String, String> returnStateMap = new HashMap<>();
        String url = Constants.BASEURL + "/ServertForComments";
        String params = "startnid=" + startnid + "&count=" + 8 + "&nid=" + nid
                + "&difference=" + "1";
        System.out.println("传到方法里的参数" + startnid + nid);
        try {
            // 实例Http链接
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            Log.d("Homg_Activity", "到实例化链接来了来了 " + startnid + nid);

            // 发送请求
            String jsonstr = httpUtil.httpPost(url, params);
            // 把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);

            String result = myjsonObject.get("result").toString();
            tatalCount = Integer.valueOf(myjsonObject.get("totalCount")
                    .toString());
            if (isFirstLoad) {
                commentsDataList.clear();
                sAdapter.notifyDataSetChanged();
                Log.d("Homg_Activity", "到清除这里来了吗");
            }
            if (result.equals("success")) {

                JSONArray commentList = myjsonObject
                        .getJSONArray("commentList");
                System.out.println(myjsonObject.getJSONArray("commentList"));
                for (int i = 0; i < commentList.size(); i++) {
                    JSONObject newsObject = commentList.getJSONObject(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();

                    hashMap.put(
                            "Userhead",
                            Constants.IMAGEBASEURL
                                    + newsObject.getString("Userhead"));
                    hashMap.put("commentsId",
                            newsObject.getInteger("commentsId"));
                    hashMap.put("commentsPraise",
                            newsObject.getInteger("commentsPraise"));
                    hashMap.put("Username", newsObject.getString("Username"));
                    hashMap.put("Usergender",
                            newsObject.getString("Usergender"));
                    hashMap.put("commentsTime", newsObject.getString("Time"));
                    hashMap.put("Comments", newsObject.getString("Comments"));

                    if (isFirstLoad) {
                        // 第一次加载
                        commentsDataList.add(hashMap);
                    } else {
                        commentsDataList.addLast(hashMap);
                    }
                }
                returnStateMap.put("returnState", "SUCCESS");
            } else {
                returnStateMap.put("returnState", "FALSE");
            }
            Log.d("Homg_Activity", myjsonObject.get("result").toString() + "判断");
            Log.d("Homg_Activity", tatalCount + "返回总数");

        } catch (Exception e) {
            e.printStackTrace();
            returnStateMap.put("returnState", "FALSE");
        }
        return returnStateMap;

    }


}