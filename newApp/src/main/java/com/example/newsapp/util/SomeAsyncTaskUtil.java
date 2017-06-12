package com.example.newsapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.newsapp.sqlite_service.NewsDataService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/5/26.
 */

public class SomeAsyncTaskUtil {
    private Context context;
    private ImageView imageview;

    private static final int ERRORS = 0;//加载失败
    private static final int SUCCESS = 1;//加载成功

    public SomeAsyncTaskUtil(Context context, ImageView view) {
        this.context = context;
        this.imageview = view;
    }

    public void getUserHeadIcon(String url) {
        new getHesdAsyc().execute(url);
    }

    // 异步任务
    public class getHesdAsyc extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap result) {
            imageview.setImageBitmap(result);
            super.onPostExecute(result);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                Bitmap bmp = getHeadFromServer(params[0]);
                return bmp;
            }
            Bitmap bmp = ExternalStorageUtil.getExternalImage(context, params[0]);
            return bmp;
        }
    }

    /**
     * 从服务器拿头像图片
     *
     * @param url
     * @return Bitmap
     * @author create by Administrator
     */
    public Bitmap getHeadFromServer(String url) {
        Bitmap bm = null;
        URL headUrl;
        try {
            headUrl = new URL(Constants.IMAGEBASEURL + url);
            InputStream ip = headUrl.openStream();
            bm = BitmapFactory.decodeStream(ip);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bm;
    }


    /**
     * Returns 返回的状态
     *
     * @param cid         类别id
     * @param newsList    将获取数据未组装的list或可能已经有数据了的list，如果是第一次加载就没有数据
     * @param startnid    开始位置
     * @param isFirstLoad 是否第一次加载标志
     * @param totalNum    当前返回的总记录数
     * @param tatalCount  该类新闻的总记录数
     * @param firstLoad   是第一次加载
     * @return int 数据获取成功与否标志
     * @throws
     * @author create by Administrator
     */
    //从数据库获取特定类别的新闻
    public static int getSpeCateNews(int cid, LinkedList<HashMap<String, Object>> newsList, int startnid, boolean isFirstLoad, int totalNum, int tatalCount, int firstLoad) {
        Log.d("Homg_Activity", "到读取数据来了来了 ");
        String url = Constants.BASEURL + "/NewsDataServert";
        String params = "startnid=" + startnid + "&count=" + 8 + "&cid=" + cid;
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
            tatalCount = Integer.valueOf(myjsonObject.get("tatalCount").toString());
            if (isFirstLoad) {
                newsList.clear();
                firstLoad = 9527;
                Log.d("Homg_Activity", "到清除这里来了吗");
            }
            if (result.equals("成功")) {
                data = (JSONObject) myjsonObject.get("data");
                totalNum = data.getInteger("getBackTotalnum");
                Log.d("Homg_Activity", data + "里面返回的data1111111");
            } else {
                totalNum = 0;
            }
            Log.d("Homg_Activity", myjsonObject.get("ret").toString() + "判断");

            Log.d("Homg_Activity", totalNum + "返回总数");
            if (totalNum > 0) {

                JSONArray newslist = data.getJSONArray("newslist");

                for (int i = 0; i < newslist.size(); i++) {
                    JSONObject newsObject = newslist.getJSONObject(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("nid", newsObject.getInteger("nid"));
                    hashMap.put("newslist_item_title", newsObject.getString("title"));
                    hashMap.put("newslist_item_patr", newsObject.getString("part"));
                    hashMap.put("newslist_item_content", newsObject.getString("content"));
                    hashMap.put("newslist_item_ptime", newsObject.getString("time"));
                    hashMap.put("newslist_item_Author", newsObject.getString("Author"));
                    hashMap.put("newslist_item_comments", newsObject.getString("commentCount"));
                    hashMap.put("newslist_item_imgsrc", Constants.IMAGEBASEURL + newsObject.getString("imgsrc"));

                    if (isFirstLoad) {
                        //第一次加载
                        newsList.add(hashMap);
                    } else {
                        newsList.addLast(hashMap);
                        //newsList.addFirst();
                    }
                }
                return SUCCESS;
            } else {
                return ERRORS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORS;
        }

    }


    /**
     * Returns 返回的状态
     *
     * @param cid            类别id
     * @param startnid       开始位置
     * @param haoManyRows    每次获取的数量
     * @param reFreshStation 刷新加载数据的情况
     * @return int 数据获取成功与否标志
     * @author create by Administrator
     */
    //从数据库获取特定类别的新闻
    public static HashMap<String, Object> getSpeCateNewsBySQLite(int cid, int startnid, int haoManyRows, int reFreshStation, LinkedList<HashMap<String, Object>> newsDataList) {
        Log.d("Homg_Activity", "到读取SQLite数据来了来了 ");

        HashMap<String, Object> hashMapREtureData = new HashMap<String, Object>();
        //LinkedList<HashMap<String, Object>> newsDataList = new LinkedList<HashMap<String, Object>>();
        int totalNum = NewsDataService.getNewDataTotalCountByCategory(cid);

        LinkedList<HashMap<String, Object>> newsDataListBack = NewsDataService.getSomoeNewsDatabyCondition(cid, startnid, haoManyRows,
                reFreshStation, newsDataList);
        hashMapREtureData.put("newsDataList", newsDataList);
        hashMapREtureData.put("categoryTotalNum", totalNum);
        if (newsDataListBack.size() > 0) {
            Log.d("Homg_Activity", totalNum + "返回该类的总数");
            Log.d("Homg_Activity", newsDataList.size() + "返回本次拿回的总数");
            hashMapREtureData.put("isSuccess", "YES");
        } else {
            hashMapREtureData.put("isSuccess", "NO");
        }
        return hashMapREtureData;
    }


}
