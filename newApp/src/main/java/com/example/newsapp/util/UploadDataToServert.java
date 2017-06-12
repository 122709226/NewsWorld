package com.example.newsapp.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class UploadDataToServert {

    //单例设计模式

    private UploadDataToServert() {
        super();
    }

    public static UploadDataToServert createInstance() {
        UploadDataToServert uploadDataToServert = new UploadDataToServert();
        return uploadDataToServert;
    }

    //将用户评论内容存入数据库
    public Integer saveComments(String nid, String userCount, String nickName, String usergender, String userHead, String comments) {
        Log.d("Homg_Activity", "到存入评论处理来了 ");
        int result = 0;
        String url = Constants.BASEURL + "/ServertForComments";
        String params = "nid=" + nid + "&userCount=" + userCount + "&nickName=" + nickName + "&usergender=" + usergender + "&userHead=" + userHead + "&comments=" + comments + "&difference=" + "2";
        try {
            //实例Http链接
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            Log.d("Homg_Activity", "到实例化链接来了来了 " + comments + nid);
            //发送请求
            String jsonstr = httpUtil.httpPost(url, params);
            //把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);
            result = Integer.valueOf(myjsonObject.get("result").toString());
        } catch (Exception e) {
            result = 2;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    //将用户评论内容存入数据库
    public Integer savePraiseNumber(String commentsId, String praiseNumber) {
        Log.d("Homg_Activity", "到存入赞的个数处理来了 ");
        int result = 0;
        String url = Constants.BASEURL + "/ServertForComments";
        String params = "commentsId=" + commentsId + "&praiseNumber=" + praiseNumber + "&difference=" + "3";

        try {
            //实例Http链接
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            Log.d("Homg_Activity", "到实例化链接来了来了 " + commentsId + praiseNumber);
            //发送请求
            String jsonstr = httpUtil.httpPost(url, params);
            //把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);
            result = Integer.valueOf(myjsonObject.get("result").toString());

        } catch (Exception e) {
            result = 2;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


}
