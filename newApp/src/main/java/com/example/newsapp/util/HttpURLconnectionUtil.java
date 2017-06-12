package com.example.newsapp.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.example.newsapp.util.Constants.BASEURL;

/**
 * Created by Administrator on 2017/5/6.
 */

public class HttpURLconnectionUtil {
    private static HttpURLconnectionUtil httpConnectUtilInstance = null;

    private HttpURLconnectionUtil() {
    }

    private static synchronized void creatInstance() {
        if (httpConnectUtilInstance == null) {
            httpConnectUtilInstance = new HttpURLconnectionUtil();
        }
    }

    public static HttpURLconnectionUtil getHttpClientUtilInstance() {
        if (httpConnectUtilInstance == null) {
            creatInstance();
        }
        return httpConnectUtilInstance;
    }
    //采用内部内实现单例模式
    /*public static synchronized HttpURLconnectionUtil getInstance() {
        return SingletonHolder.instance;
    }
    private static class SingletonHolder{
        private static HttpURLconnectionUtil instance = new HttpURLconnectionUtil();
    }*/


    public HashMap httpPost(String urlData, HashMap paramDatas) throws IOException {
        String param2 = "pw=" + URLEncoder.encode("丁丁", "UTF-8");
        HttpURLConnection httpURLConnection = null;
        HashMap responseResult = new HashMap();
        InputStream is = null;
        BufferedOutputStream outputStream;
        PrintWriter printWriter = null;
        BufferedWriter bufferedWriter = null;
        int responseCode;
        StringBuffer params = converStringParamToASPUnifrom(paramDatas);
        //构造httpClient对象
        try {
            URL url = new URL(urlData);
            httpURLConnection = (HttpURLConnection) url.openConnection();//使用URL打开一个链接
            httpURLConnection.setConnectTimeout(5 * 1000);
            httpURLConnection.setDoOutput(true); //允许输出流，即允许上传
            httpURLConnection.setDoInput(true); //允许输入流，即允许下载
            httpURLConnection.setUseCaches(false); //不使用缓冲
            httpURLConnection.setRequestMethod("POST"); //使用POST请求
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置该HttpURLConnection实例是否自动执行重定向
            httpURLConnection.setInstanceFollowRedirects(true);
            // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            // httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript");
            //httpURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");//文本信息
            //httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");//流信息 可以传输图片音频等信息
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
            httpURLConnection.setChunkedStreamingMode(0);
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect

            httpURLConnection.connect();
            /*String kk="userName="+"ss"+"&userPhone="+"phonenum"+"&userPassword="+"222222";
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(params.toString());
            printWriter.flush();
            printWriter.close();*/
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
            bufferedWriter.write(params.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                responseResult.put("returnState", responseCode);
                throw new IOException("HTTP error code: " + responseCode);
            }
            //is = httpURLConnection.getInputStream();   //获取输入流，此时才真正建立链接
            if (is != null) {
                // Converts Stream to String with max length of 2050.
                //responseResult.put("returnState",responseCode);
                responseResult.put("MyreturnState", "SUCCESS");
                responseResult.put("returnData", readStream(is, 2050));
            } else {
                responseResult.put("returnState", responseCode);
                responseResult.put("MyreturnState", "FALSE");
                responseResult.put("returnData", readStream(is, 2050));
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            return responseResult;
        }

    }

    /**
     * 从网络获取json数据,(String byte[})
     *
     * @param path
     * @return
     */
    public HashMap httpGet(String path) throws IOException {
        HttpURLConnection httpURLConnection = null;
        HashMap responseResult = new HashMap();
        InputStream is = null;
        int responseCode;
        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();//使用URL打开一个链接
            httpURLConnection.setConnectTimeout(5 * 1000);
            httpURLConnection.setDoOutput(true); //允许输出流，即允许上传
            httpURLConnection.setDoInput(true); //允许输入流，即允许下载
            httpURLConnection.setUseCaches(false); //不使用缓冲
            httpURLConnection.setRequestMethod("GET"); //使用GET请求
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("007", "nijiushiyigeshadiao");
            //httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));httpURLConnection.setChunkedStreamingMode(0);
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                responseResult.put("returnState", responseCode);
                throw new IOException("HTTP error code: " + responseCode);
            }
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                responseResult.put("returnState", responseCode);
                throw new IOException("HTTP error code: " + responseCode);
            }
            is = httpURLConnection.getInputStream();   //获取输入流，此时才真正建立链接
            if (is != null) {
                // Converts Stream to String with max length of 2050.
                //responseResult.put("returnState",responseCode);
                responseResult.put("MyreturnState", "SUCCESS");
                responseResult.put("returnData", readStream(is, 2050));
            } else {
                responseResult.put("returnState", responseCode);
                responseResult.put("MyreturnState", "FALSE");
                responseResult.put("returnData", readStream(is, 2050));

            }
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (is != null)
                is.close();
            return responseResult;
        }
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    private String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        // Create temporary buffer to hold Stream data with specified max length.
        char[] buffer = new char[maxLength];
        // Populate temporary buffer with Stream data.
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            //int pct = (100 * numChars) / maxLength;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1) {
            // The stream was not empty.
            // Create String that is actual length of response body if actual length was less than
            // max length.
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
            new String(buffer, 2, 2);
        }
        return result;
    }

    // 组织请求参数   就是把数据提交给网站格式必须为（key=value&key1=value1）
    public StringBuffer converStringParamToASPUnifrom(HashMap paramsDate) {
        StringBuffer params = new StringBuffer();
        Iterator it = paramsDate.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        return params;
    }


    public boolean isConnectionOk(String textConnection) {
        boolean connectionState = false;
        try {
            LocalDefaultSharePreference.setBooleanLocalSharePreference("ConnectionState", false);
            URL texturl = new URL(BASEURL + textConnection);
            HttpURLConnection httpURLConnection = (HttpURLConnection) texturl.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.getContent();
            OutputStream ops = httpURLConnection.getOutputStream();
            ops.close();
            int openState = httpURLConnection.getResponseCode();
            if (openState == HttpURLConnection.HTTP_OK) {
                connectionState = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connectionState;
    }

}
