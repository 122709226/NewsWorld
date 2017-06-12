package com.example.newsapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.newsapp.R;
import com.example.newsapp.appconfigs.MyLog;
import com.example.newsapp.custom_adapter.myBaseAdapter;
import com.example.newsapp.util.Constants;
import com.example.newsapp.util.DeepCopyList;
import com.example.newsapp.util.LocalDefaultSharePreference;
import com.example.newsapp.util.SomeAsyncTaskUtil;
import com.example.newsapp.util.SyncHttpUtil;
import com.example.newsapp.po.Category;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import static com.example.newsapp.util.Constants.LOG_ERROR;

public class Homg_Activity extends Fragment {
    private int loadCountNumber = 8; //每次加载新闻的数量
    private int startPosition = 0; //加载新闻的起始位置
    private int currentTotalNum;//当前返回的总记录数
    private int categoryTotalNum;//该类新闻的总记录数
    private int[] reFreshStation = {1, 2, 3}; //1第一次加载 2 正常加载 3 向上刷新新闻
    private Context mContext;
    private LinkedList<HashMap<String, Object>> newsDataList; //应用传递不断在改变值的新闻数据集合
    private LinkedList<HashMap<String, Object>> newsDataListForAdapter; //给 UI Adapter的新闻数据集合
    private PullToRefreshListView mPullToRefreshListView; //可下拉的listview
    private myBaseAdapter sAdapter;

    LinkedList<myBaseAdapter> myBaseAdapters;
    ListView actualLIstView;
    int cid = 1;//类别id
    ImageView imageView;
    String catName = "焦点";//类别名


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_activaty, container, false);
        initializationData(view);
        MyLog.d("执行到 Homg_Activity  onCreateView  ");
        //获取水平滚动条
        final HorizontalScrollView categoryScrollView = (HorizontalScrollView) view.findViewById(R.id.categorybar_scrollview);
        categoryScrollView.fling(1000);

        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        mPullToRefreshListView.setMode(Mode.BOTH);//向下拉刷新
        //下拉提示
        ILoadingLayout startlabels = mPullToRefreshListView.getLoadingLayoutProxy();
        startlabels.setLastUpdatedLabel("下拉刷新...");
        startlabels.setPullLabel("");//刚下拉时显示的提示
        startlabels.setRefreshingLabel("正在刷新...");//刷新时的提示
        startlabels.setReleaseLabel("放开刷新...");//下来到达一定距离时，显示的提示
        //上拉提示
        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setLastUpdatedLabel("上拉刷新...");
        endLabels.setPullLabel("");// 刚下拉时显示的提示
        endLabels.setRefreshingLabel("正在载入...");//刷新时的提示
        endLabels.setReleaseLabel("放开刷新...");//下来到达一定距离时，显示的提示
        //设置每项点击监听
        //*************************************************************************************************************
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(Homg_Activity.this.getActivity(), NewsContentctivity.class);
                System.out.println("该业新闻总条数" + myBaseAdapters.get(cid - 1).getNewsDataListForAdapter().size());
                System.out.println("该新闻位置" + position);
                HashMap<String, Object> map = myBaseAdapters.get(cid - 1).getNewsDataListForAdapter().get(position - 1);
                String newId = id + "";
                String title = (String) map.get("title");
                String author = (String) map.get("author");
                String editTime = (String) map.get("time");
                String comments = (String) map.get("news_comments_cuont");
                String content = (String) map.get("content");
                intent.putExtra("catagaryname", catName);
                intent.putExtra("newsid", newId);
                intent.putExtra("title", title);
                intent.putExtra("author", author);
                intent.putExtra("editTime", editTime);
                intent.putExtra("comments", comments);
                intent.putExtra("content", content);
                startActivity(intent);
                //	Toast.makeText(Homg_Activity.this.getActivity(), id+"被点击了", Toast.LENGTH_LONG).show();
            }
        });

        //设置刷新事件
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {
            //向下拉
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // 暂且简单做成下拉重新加载，以后改进成 下来添加新内容
                if (true) {
                    startPosition = 0;
                    new getDataTask().execute(cid, startPosition, loadCountNumber, reFreshStation[0]);
                } else {
                    //mPullToRefreshListView的bug ，需要延迟一秒后再调用onRefresComplete方法，
                    //不然会卡在正在加载中，onRefresComplete方法变得无效果；
                    mPullToRefreshListView.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                }
            }

            //向上拉
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                Log.d("Homg_Activity", "总记录数" + categoryTotalNum);
                Log.d("Homg_Activity", "当前位置" + myBaseAdapters.get(cid - 1).getNewsDataListForAdapter().size());
                //没到底
                if (myBaseAdapters.get(cid - 1).getNewsDataListForAdapter().size() < categoryTotalNum) {
                    startPosition = newsDataList.size();
                    new getDataTask().execute(cid, startPosition, loadCountNumber, reFreshStation[1]);
                    //到底了
                } else if (myBaseAdapters.get(cid - 1).getNewsDataListForAdapter().size() == categoryTotalNum) {
                    ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
                    endLabels.setPullLabel("已经到底了");// 刚下拉时显示的提示
                    mPullToRefreshListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                } else {
                    //mPullToRefreshListView的bug ，需要延迟一秒后再调用onRefresComplete方法，
                    //不然会卡在正在加载中，onRefresComplete方法变得无效果；
                    mPullToRefreshListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });

        initializationewsDataListForAdapterN();
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    public void initializationData(View v) {
        mContext = Homg_Activity.this.getActivity();

        myBaseAdapters = new LinkedList<myBaseAdapter>();

        for (int i = 1; i <= 7; i++) {
            myBaseAdapters.add(new myBaseAdapter(mContext));
        }
        final SharedPreferences userdemo = Homg_Activity.this.getActivity().getSharedPreferences("userDate", 0);
        imageView = (ImageView) v.findViewById(R.id.imageView_top_head);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userdemo.getBoolean("userDate", true)) {
                    //如果存在登录对象 去用户界面
                    Intent intent = new Intent(Homg_Activity.this.getActivity(), MainActivity.class);
                    intent.putExtra("goToUser", "goToUser");
                    startActivity(intent);
                } else {
                    //如果不存在用户 则去登陆界面
                    Intent intent = new Intent(Homg_Activity.this.getActivity(), LoginngActivity.class);
                    startActivity(intent);
                }

            }
        });
        //如果纯在登录对象 改变左上角头像
        if (userdemo.getBoolean("userDate", false)) {
            String url = userdemo.getString("Head", " ");
            if (!("header/user_icon_pi.png".equals(url)) && !(url.equals(""))) {
                new SomeAsyncTaskUtil(mContext, imageView).getUserHeadIcon(url);
            }
        }
        //获取新闻分类
        String[] categoryArray = this.getResources().getStringArray(R.array.categories);
        //将新闻分类文本加入到List中
        final List<HashMap<String, Category>> categories = new ArrayList<HashMap<String, Category>>();
        for (int i = 0; i < categoryArray.length; i++) {
            String[] temp = categoryArray[i].split("[|]");
            if (temp.length == 2) {
                //获取类别id
                int cid = Integer.valueOf(temp[0]);
                //获取类别名
                String title = temp[1].toString();
                //	MyLog.d("Homg_Activity", cid+title+"888888888888");
                Category category = new Category(cid, title);
                HashMap<String, Category> hashMap = new HashMap<String, Category>();
                hashMap.put("category", category);
                categories.add(hashMap);
            }
        }
        //创建类别标题栏的适配器
        CatigorySimpleAdapter categoryAdapter = new CatigorySimpleAdapter(mContext, categories, R.layout.category_title, new String[]{"category"}, new int[]{R.id.category_title});
        //设置gridview属性
        GridView category = new GridView(this.getActivity());
        //设置适配器
        category.setAdapter(categoryAdapter);
        //总的列数的宽度
        int totalWidth = 0;
        //单列的宽度
        int columnWidth = 0;
        for (int i = 0; i < categoryAdapter.getCount(); i++) {
            View gridItem = categoryAdapter.getView(i, null, category);
            //从textview的左上角0，0开始计算
            gridItem.measure(0, 0);
            //getMeasuredWidth获取实际的宽度
            totalWidth += gridItem.getMeasuredWidth();
        }
        //计算单列平均宽度
        columnWidth = totalWidth / categoryAdapter.getCount();
        Log.d("totalWidth", totalWidth + "");
        Log.d("ColumnWidth", categoryAdapter.getCount() + "");
        //每列宽100(写死)
        // category.setColumnWidth(200);
        category.setColumnWidth(columnWidth);
        //自动调整显示列数,也可以设置为categories.size()
        category.setNumColumns(GridView.AUTO_FIT);
        //取消当点击GridView 的时候出现的那个黄色背景
        category.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //category.setSelector(R.color.category_bar_title);
        //int width = 100 * categories.size();
        LayoutParams params = new LayoutParams(totalWidth, LayoutParams.WRAP_CONTENT);
        //设置整个gridview的布局
        category.setLayoutParams(params);
        category.setGravity(Gravity.CENTER);
        LinearLayout category_ll = (LinearLayout) v.findViewById(R.id.category_layout);
        //将gridview放入linearlayout中
        category_ll.addView(category);
        category.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        category.setItemChecked(1,true);
        //对GridView添加单击事件
        category.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView categoryTitle;
                //去除每一个textview的样式,也就是去除默认背景和样式
                for (int i = 0; i < parent.getChildCount(); i++) {
                    categoryTitle = (TextView) parent.getChildAt(i);
                    //categoryTitle.setTextColor(0xffadb2ad);
                    categoryTitle.setTextColor(getResources().getColor(R.color.color_black));
                    categoryTitle.setBackgroundResource(R.drawable.tablehost_background);
                }
                //为选中那一项的text设置样式
                categoryTitle = (TextView) parent.getChildAt(position);
                categoryTitle.setBackgroundResource(R.drawable.categary_background_shape);
                categoryTitle.setTextColor(getResources().getColor(R.color.color_blue));
                cid = categories.get(position).get("category").getT_id();
                catName = categories.get(position).get("category").getKind();
                // Toast.makeText(Homg_Activity.this.getActivity(), catName+"类别id"+cid, Toast.LENGTH_SHORT).show();
                startPosition = 0;
                //newsDataListForAdapter.clear();//清除当前的所有新闻
                Log.d("Homg_Activity", cid+"888888888888");
                actualLIstView.setAdapter(myBaseAdapters.get(cid - 1));
                if (myBaseAdapters.get(cid - 1).getNewsDataListForAdapter().isEmpty())
                    new getDataTask().execute(cid, startPosition, loadCountNumber, reFreshStation[0]);

            }
        });

    }

    public void initializationewsDataListForAdapterN() {
        // 从数据库中初次加载数据
        if (newsDataListForAdapter == null) {
            newsDataList = new LinkedList<HashMap<String, Object>>();
            new getDataTask().execute(cid, startPosition, loadCountNumber, reFreshStation[0]);
        }else{
            actualLIstView.setAdapter(myBaseAdapters.get(cid - 1));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //获取真正的listview
        actualLIstView = mPullToRefreshListView.getRefreshableView();
        super.onActivityCreated(savedInstanceState);
    }


    //异步任务
    private class getDataTask extends AsyncTask<Object, Integer, HashMap> {
        @Override
        protected HashMap doInBackground(Object... params) {
            Log.d("Homg_Activity", "到异步任务来了 ");

            if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                // 参数0表示类别，参数1表示起始位置，参数2表示是否第一次加载
                //int result1 = getSpeCateNews((Integer) params[0], newsDataList, (Integer) params[1], (Boolean) params[2]);
                HashMap result = getSpeCateNews((Integer) params[0], (Integer) params[1], (Integer) params[2], (Integer) params[3], newsDataList);
                return result;
            }

            HashMap result = SomeAsyncTaskUtil.getSpeCateNewsBySQLite((Integer) params[0], (Integer) params[1], (Integer) params[2], (Integer) params[3], newsDataList);
            return result;
        }

        @Override
        protected void onPostExecute(HashMap result) {
            // 已经修改数据通知更新ui
            if (result.get("isSuccess").equals("YES")) {
                currentTotalNum = newsDataList.size();
                categoryTotalNum = Integer.valueOf(result.get("categoryTotalNum").toString());
                //如果获取数据成功，就在这里赋值，并通知sAdapter notifyDataSetChanged()；
                newsDataListForAdapter = DeepCopyList.deepCopyList(newsDataList);
                Log.e(LOG_ERROR, "22225555    " + newsDataListForAdapter.toString());
                Log.e(LOG_ERROR, "111221    " + newsDataList.toString());
                //设置adapter
                myBaseAdapters.get(cid - 1).setNewsDataListForAdapter(newsDataListForAdapter);
                actualLIstView.setAdapter(myBaseAdapters.get(cid - 1));
                myBaseAdapters.get(cid - 1).notifyDataSetChanged();
                //list 跟新完成调用onRrfreshConp;ete
                mPullToRefreshListView.onRefreshComplete();
            }
            super.onPostExecute(result);
        }

    }

    /**
     * @param cid            类别id
     * @param startnid       开始位置
     * @param reFreshStation 刷新数据的情况
     * @param haoManyRows    一次拿多少条
     * @param newsDataList   引用传递装置数据
     * @return LinkedList<HashMap<String, Object>>
     */
    //从数据库获取特定类别的新闻
    public HashMap<String, Object> getSpeCateNews(int cid, int startnid, int haoManyRows, int reFreshStation, LinkedList<HashMap<String, Object>> newsDataList) {
        Log.d("Homg_Activity", "到读取数据来了来了 ");
        HashMap<String, Object> hashMapREtureData = new HashMap<String, Object>();
        int currentTotalNum = 0;
        int categoryTotalNum = 0;
        String url = Constants.BASEURL + "/NewsDataServert";
        String params = "startnid=" + startnid + "&count=" + haoManyRows + "&cid=" + cid;
        JSONObject data = null;
        try {
            //实例Http链接
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            Log.d("Homg_Activity", "到实例化链接来了来了 " + startnid + cid);

            //发送请求
            String jsonstr = httpUtil.httpPost(url, params);
            //把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);
            String result = myjsonObject.get("ret").toString();
            // 返回的当前的新闻条数？ 好像没用
            currentTotalNum = Integer.valueOf(myjsonObject.get("tatalCount").toString());
            if (result.equals("成功")) {
                data = (JSONObject) myjsonObject.get("data");
                categoryTotalNum = data.getInteger("getBackTotalnum");
                if (categoryTotalNum > 0) {
                    JSONArray newslist = data.getJSONArray("newslist");
                    for (int i = 0; i < newslist.size(); i++) {
                        JSONObject newsObject = newslist.getJSONObject(i);
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("nid", newsObject.getInteger("nid"));
                        hashMap.put("title", newsObject.getString("title"));
                        hashMap.put("part", newsObject.getString("part"));
                        hashMap.put("content", newsObject.getString("content"));
                        hashMap.put("time", newsObject.getString("time"));
                        hashMap.put("author", newsObject.getString("Author"));
                        hashMap.put("news_comments_cuont", newsObject.getString("commentCount"));
                        hashMap.put("picture", Constants.IMAGEBASEURL + newsObject.getString("imgsrc"));

                        switch (reFreshStation) {
                            case 1: //第一次加载
                                newsDataList.clear();
                                newsDataList.add(hashMap);
                                break;
                            case 2: //不是第一次加载
                                newsDataList.addLast(hashMap);

                                break;
                            case 3: //向上刷新新闻
                                break;
                            default:
                                break;
                        }
                        hashMapREtureData.put("isSuccess", "YES");
                    }
                }
                Log.d("Homg_Activity", data + "里面返回的data1111111");
            } else {
                categoryTotalNum = 0;
                hashMapREtureData.put("isSuccess", "NO");
            }
            Log.d("Homg_Activity", myjsonObject.get("ret").toString() + "判断");
            Log.d("Homg_Activity", categoryTotalNum + "返回总数");
        } catch (Exception e) {
            e.printStackTrace();
        }
        hashMapREtureData.put("categoryTotalNum", categoryTotalNum);
        hashMapREtureData.put("newsDataList", newsDataList);
        return hashMapREtureData;
    }

    public class CatigorySimpleAdapter extends SimpleAdapter {

        public CatigorySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //一定要先调用super.getView(position, convertView, parent)方法，此方法用于创建每一项的View视图
            // MyLog.d("Homg_Activity","到这不来了了没有");
            View v = super.getView(position, convertView, parent);
            TextView categoryTitle = (TextView) v;
            //如果是第一个textView添加样式
            if (position == 0) {
                categoryTitle.setBackgroundResource(R.drawable.categbar_item_bar);
                //categoryTitle.setTextColor(getResources().getColor(R.color.category_bar_title));

            }
            //设置文本框内容居中
            categoryTitle.setGravity(Gravity.CENTER);
            //设置文本框的padding
            categoryTitle.setPadding(30, 10, 30, 10);
            return v;
        }
    }

}
