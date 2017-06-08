package com.example.newapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.newapp.util.Constants;
import com.example.newapp.util.GetTotalComments;
import com.example.newapp.util.SomeAsyncTaskUtil;
import com.example.newapp.util.SyncHttpUtil;
import com.example.newapp.util.UploadDataToServert;
import com.example.newapp.util.ViewHolder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.LinkedList;

public class MyComments extends Activity implements OnClickListener {

    private int tatalCount;// 该新闻的总评论数
    // private int loadCount = 5; //每次加载新闻的数量
    private final int SUCCESS = 1;// 加载成功
    private int firstLoad = 0;// 加载成功
    private int startPosition = 0; // 加载新闻的起始位置
    private int totalNum;// 当前返回的总记录数
    private int havaPopuWindow = 0;//是否加载了PopuWindow

    private String userCount;
    private ImageLoader imageLoader; //图片容器
    private Context mycontext;
    private myBaseAdapter sAdapter;
    private String userNickName, Count, Gender, Head;
    private SharedPreferences userdemo; //获取用户对象
    private PullToRefreshListView mPullToRefreshListView; // 可下拉的listview
    private LinkedList<HashMap<String, Object>> commentsDataList; // 新闻数据集合
    private RelativeLayout activity_MyComments_View_RelativeLayout;
    private DisplayImageOptions options;
    private ImageView headIcon;//头像
    private CheckBox selector_support_thumb_icon_RadioButton;//点赞的CheckBox

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Comment_image_itlebar_btn_myComments_back:
                //返回用户界面
                Intent intent = new Intent(mycontext, MainActivity.class);
                intent.putExtra("goToUser", "goToUser");
                startActivity(intent);
                break;
            default:
                break;
        }


    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_comments);


        //实例化上下文
        mycontext = MyComments.this;
        //获取跟布局文件
        activity_MyComments_View_RelativeLayout = (RelativeLayout) findViewById(R.id.activity_MyComments_View_RelativeLayout);

        headIcon = (ImageView) findViewById(R.id.imageView_topenter_head); //头像
        //获得当前用户对象
        userdemo = getSharedPreferences("userDate", 0);
        //如果有用户记录 拿出用户对象
        if (userdemo.getBoolean("userDate", false)) {
            userNickName = userdemo.getString("userNickName", "用户名");
            Count = userdemo.getString("Count", "用户id");
            userCount = Count;
            Gender = userdemo.getString("Gender", "性别");
            //Head = userdemo.getString("Head", "头像");

            String url = userdemo.getString("Head", " ");
            if (!("header/user_icon_pi.png".equals(url)) && !(url.equals(""))) {
                new SomeAsyncTaskUtil(mycontext,headIcon).getUserHeadIcon(url);
            }
        }
        ImageView btn_myComments_back = (ImageView) findViewById(R.id.Comment_image_itlebar_btn_myComments_back);
        btn_myComments_back.setEnabled(true);
        btn_myComments_back.setOnClickListener(this);

        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.my_comments_pull_refresh_list);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);// 向下拉刷新
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
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
        // 设置刷新事件
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            // 向下拉
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                if (commentsDataList.size() == 0) {
                    startPosition = 0;
                    new getCommentDataTask().execute(userCount, startPosition,
                            true);
                } else if (commentsDataList.size() > 8) {
                    startPosition = startPosition - 8;
                    new getCommentDataTask().execute(userCount, startPosition,
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

            // 向上拉
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                Log.d("Homg_Activity", "总记录数" + tatalCount);
                Log.d("Homg_Activity", "当前位置" + commentsDataList.size());
                if (commentsDataList.size() < tatalCount) {
                    startPosition = commentsDataList.size();
                    new getCommentDataTask().execute(userCount, startPosition,
                            false);
                    // new getDataTask().execute(cid, startPosition,false);
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
        if (commentsDataList == null) {
            commentsDataList = new LinkedList<HashMap<String, Object>>();
            new getCommentDataTask().execute(userCount, startPosition, true);
        }
        sAdapter = new myBaseAdapter(commentsDataList);
        // 获取真正的listview
        ListView actualLIstView = mPullToRefreshListView.getRefreshableView();
        // 设置adapter
        actualLIstView.setAdapter(sAdapter);

    }

    // 构造baseAdap
    private class myBaseAdapter extends BaseAdapter {
        LinkedList<HashMap<String, Object>> commentsDataList;

        public myBaseAdapter(
                LinkedList<HashMap<String, Object>> commentsDataList) {
            this.commentsDataList = commentsDataList;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return commentsDataList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return commentsDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            long nid = Integer.getInteger(commentsDataList.get(position).get("newsId") + "");
            return nid;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("Homg_Activity", "到我的评论布局ui来了 ");
            // final HashMap<String, Object> wrapData = commentsDataList.get(0);
            final HashMap<String, Object> map = commentsDataList.get(position);
            ViewHolder holder = ViewHolder.get(mycontext,
                    convertView, parent, R.layout.mycomments_itemlist_view, position);

            // 时间
            TextView tv_digest = (TextView) holder
                    .getView(R.id.myComments_Write_time_textView);
            tv_digest.setText((String) map.get("commentsTime"));
            tv_digest.setTextColor(0xffadb2ad);

            // 评论内容
            TextView comments_commentsContent_textViewtext = (TextView) holder
                    .getView(R.id.myComments_commentsContent_textViewtext);
            comments_commentsContent_textViewtext.setText(" " + map
                    .get("Comments"));
            // tv_digest.setTextColor(0xffadb2ad);

            //原文标题
            TextView NewsTitle = holder.getView(R.id.textView_myCommentsNewsTitle);
            NewsTitle.setText("【原新闻】" + map.get("title"));
            NewsTitle.setEnabled(true);
            NewsTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mycontext, NewsContentctivity.class);
                    Toast.makeText(mycontext, "是dfsasdfsadfasdfasdfasdfads是是", Toast.LENGTH_SHORT).show();
                    intent.putExtra("newsId", "" + map.get("newsId"));

                    GetTotalComments getTotalComments = GetTotalComments.createInstance();

                    intent.putExtra("catagaryname", "" + map.get("kinds"));
                    intent.putExtra("newsid", "" + map.get("newsId"));
                    intent.putExtra("title", "" + map.get("title"));
                    intent.putExtra("author", "" + map.get("author"));
                    intent.putExtra("editTime", "" + map.get("editTime"));
                    intent.putExtra("comments", "" + getTotalComments.getCommentsCount("" + map.get("newsId")));
                    intent.putExtra("tontent", "" + map.get("content"));

                    startActivity(intent);
                }
            });

            //点赞数字的显示
            //点赞图标 and 数字的显示
            selector_support_thumb_icon_RadioButton = (CheckBox) holder
                    .getView(R.id.mySelector_support_thumb_icon_CheckBox);
            selector_support_thumb_icon_RadioButton.setTextColor(0xffadb2ad);
            selector_support_thumb_icon_RadioButton.setText(map.get("commentsPraise").toString());
            SharedPreferences editPraise = mycontext.getSharedPreferences("editPraise", Context.MODE_PRIVATE);
            final boolean jugeIsChecked = editPraise.getBoolean("isChecked" + map.get("commentsId"), false);
            if (jugeIsChecked) {
                selector_support_thumb_icon_RadioButton.setChecked(true);
            }

            selector_support_thumb_icon_RadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    //点赞数字
                    int praiseNumber = (Integer) map.get("commentsPraise");
                    if (isChecked && jugeIsChecked) {
                        praiseNumber = praiseNumber + 0;
                    } else if (isChecked) {
                        praiseNumber += 1;
                        SharedPreferences editPraise = mycontext.getSharedPreferences("editPraise", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = editPraise.edit();
                        editor.putBoolean("isChecked" + map.get("commentsId"), true);
                        editor.commit();
                    } else if (jugeIsChecked) {
                        praiseNumber -= 1;
                    }
                    //Toast.makeText(mycontext,map.get("commentsId").toString()+"sadfasdfasdfsadfsadfasd"+String.valueOf(praiseNumber),Toast.LENGTH_LONG).show();
                    new saveCommentPraiseAsyncTask().execute(map.get("commentsId").toString(), String.valueOf(praiseNumber));
                    buttonView.setText(String.valueOf(praiseNumber));
                }
            });
            // Toast.makeText(context, String.valueOf(changePraiseNumber)+"是是是", Toast.LENGTH_SHORT).show();
            return holder.getConvertView();

        }
    }

    // 异步任务 拿出评论
    private class getCommentDataTask extends
            AsyncTask<Object, Integer, Integer> {
        @Override
        protected Integer doInBackground(Object... params) {
            Log.d("Homg_Activity", "到异步任务来了 ");
            // 参数0表示类别，参数1表示起始位置，参数2表示是否第一次加载
            int result = getMyComments((String) params[0],
                    commentsDataList, (Integer) params[1], (Boolean) params[2]);
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // 已经修改数据通知更新ui
            //System.out.println(result);
            if (result == SUCCESS | firstLoad == 9527) {
                sAdapter.notifyDataSetChanged();
                // list 跟新完成调用onRrfreshConp;ete
                mPullToRefreshListView.onRefreshComplete();
            }
            super.onPostExecute(result);
        }

    }

    // 异步任务 点赞
    private class saveCommentPraiseAsyncTask extends
            AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Log.d("Homg_Activity", "到异步任务来了 ");
            // 参数0表示类别，
            UploadDataToServert dafDataToServert = UploadDataToServert.createInstance();
            int result = dafDataToServert.savePraiseNumber(params[0], params[1]);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // 已经修改数据通知更新ui
            //System.out.println(result);
            switch (result) {
                case 1:
                    //点赞数字变化
                    Toast.makeText(mycontext, "点赞成功", Toast.LENGTH_LONG).show();
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

    // 从数据库获取我对所有新闻的评论
    public int getMyComments(String userCount,
                             LinkedList<HashMap<String, Object>> newsList, int startnid,
                             boolean isFirstLoad) {
        Log.d("Homg_Activity", "到读取数据来了来了 ");
        String url = Constants.BASEURL + "/ServertForComments";
        String params = "startnid=" + startnid + "&count=" + 8 + "&userCount=" + userCount
                + "&difference=" + "5";
        System.out.println("传到方法里的参数" + startnid + userCount);
        int ERRORS = 0;
        try {
            // 实例Http链接
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            Log.d("Homg_Activity", "到实例化链接来了来了 " + startnid + userCount);

            // 发送请求
            String jsonstr = httpUtil.httpPost(url, params);
            // 把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);

            String result = myjsonObject.get("result").toString();
            System.out.println(result + "689999999999999999999996");
            tatalCount = Integer.valueOf(myjsonObject.get("totalCount")
                    .toString());
            if (isFirstLoad) {
                commentsDataList.clear();
                firstLoad = 9527;
                Log.d("Homg_Activity", "到清除这里来了吗");
            }
            if (result.equals("success")) {

                JSONArray commentList = myjsonObject
                        .getJSONArray("commentList");
                System.out.println(myjsonObject.getJSONArray("commentList") + "66666666666");
                for (int i = 0; i < commentList.size(); i++) {
                    JSONObject newsObject = commentList.getJSONObject(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();

                    hashMap.put("commentsPraise", newsObject.getInteger("prasie"));
                    hashMap.put("title", newsObject.getString("title"));
                    hashMap.put("commentsTime", newsObject.getString("Time"));
                    hashMap.put("Comments", newsObject.getString("comments"));
                    hashMap.put("commentsId", newsObject.getString("commentsId"));
                    hashMap.put("newsId", newsObject.getString("newsId"));
                    hashMap.put("author", newsObject.getString("author"));
                    hashMap.put("editTime", newsObject.getString("editTime"));
                    hashMap.put("part", newsObject.getString("part"));
                    hashMap.put("content", newsObject.getString("content"));
                    hashMap.put("kinds", newsObject.getString("kinds"));
                    if (isFirstLoad) {
                        // 第一次加载
                        commentsDataList.add(hashMap);
                    } else {
                        commentsDataList.addLast(hashMap);
                    }
                }

            } else {
                return ERRORS;
            }

            Log.d("Homg_Activity", myjsonObject.get("result").toString() + "判断");
            Log.d("Homg_Activity", totalNum + "返回总数");

        } catch (Exception e) {
            e.printStackTrace();
            return ERRORS;
        }
        return SUCCESS;

    }
}
