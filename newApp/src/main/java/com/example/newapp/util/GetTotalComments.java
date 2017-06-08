package com.example.newapp.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2016/4/9.
 */
public class GetTotalComments {
    private  static GetTotalComments Instancen;

    protected GetTotalComments() {
        super();
    }
    //单例设计模式l
    public static GetTotalComments createInstance(){
        if(Instancen==null){
            synchronized (GetTotalComments.class){
                if(Instancen==null){
                    Instancen = new GetTotalComments();
                }
            }
        }
        return Instancen;
    }

    //得到某新闻的总评论数
    public Integer getCommentsCount(String newsId){
        Log.d("Homg_Activity", "到存入赞的个数处理来了 ");
        int result =0;
        String url= Constants.BASEURL+"/ServertForComments" ;
        String params = "nid="+newsId+"&difference="+"6";

        try {
            //实例Http链接
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            Log.d("Homg_Activity", "到实例化链接来了来了 "+newsId);
            //发送请求
            String jsonstr = httpUtil.httpPost(url,params);
            //把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);
            result =Integer.valueOf( myjsonObject.get("totalCount").toString()) ;

        } catch (Exception e) {
            result=2;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
