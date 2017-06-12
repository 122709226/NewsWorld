package com.example.newsapp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SyncHttpUtil {

    //单例设计模式
    private SyncHttpUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static SyncHttpUtil createInstance() {
        SyncHttpUtil httpUtil = new SyncHttpUtil();
        return httpUtil;
    }


    public String httpPost(String url, String params) throws Exception {
        String response = null;
        //构造httpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramlist = converStringParamToList(params);
        httpPost.setEntity(new UrlEncodedFormEntity(paramlist, "UTF-8"));
        System.out.println("到Http链接来了2222222222" + httpPost);


        //使用execute方法发送httpPost 请求，并返回httpResponse对象
        HttpResponse httpResponse = httpClient.execute(httpPost);
        System.out.println("提交响应了");
        //响应状态
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode + "响应结果");
        if (statusCode == HttpStatus.SC_OK)
            response = EntityUtils.toString(httpResponse.getEntity());
        else {
            response = "返回码" + statusCode;
        }
        System.out.println(response + "响应结果");
        return response;

    }

    //将字符串转换成数据组
    public List<NameValuePair> converStringParamToList(String params) {
        //创建针对post的参数集合
        List<NameValuePair> paramlist = new ArrayList<NameValuePair>();
        System.out.println("传过去的数据" + params);
        String[] keyValues = params.split("&");
        for (String keyValue : keyValues) {
            String[] results = keyValue.split("=");
            paramlist.add(new BasicNameValuePair(results[0], results[1]));

        }
        System.out.println("1351351513" + paramlist);
        return paramlist;
    }


}
