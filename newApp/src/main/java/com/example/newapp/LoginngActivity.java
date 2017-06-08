package com.example.newapp;


import java.util.HashMap;
import java.util.LinkedList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.newapp.sqliteService.UserDataSrvice;
import com.example.newapp.util.Constants;
import com.example.newapp.util.LocalDefaultSharePreference;
import com.example.newapp.util.SyncHttpUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginngActivity extends Activity {
    private String result;
    TextView tipeTextView;
    private String username = null;
    private String password = null;
    EditText usernameEditText;
    EditText paswordEditText;
    String aa;
    private LinkedList<HashMap<String, Object>> userData; //新闻数据集合private LinkedList<HashMap<String,Object>> newsDataList; //新闻数据集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loginng);
        userData = new LinkedList<HashMap<String, Object>>();

        //获取提示文本框
        tipeTextView = (TextView) findViewById(R.id.tipe_textView_Loging);
        //拿到 登录信息
        usernameEditText = (EditText) findViewById(R.id.username_editText1);
        paswordEditText = (EditText) findViewById(R.id.password_editText2);


        Button buttonRegist = (Button) findViewById(R.id.button_loging_regist);
        //跳转到注册的按钮
        buttonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginngActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //监听登录的按钮
        Button buttonLoging = (Button) findViewById(R.id.button_for_loging);
        buttonLoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = paswordEditText.getText().toString();
                //MyLog.d("LoginngActivity", usernameEditText.getText().toString()+paswordEditText.getText().toString()+"账号和密码");
                //Toast.makeText(LoginngActivity.this, usernameEditText.getText().toString(), 1000).show();
                // tipeTextView.setText(usernameEditText.getText().toString());
                if (!username.isEmpty() & !password.isEmpty()) {
                    new verificationLoging().execute(username, password);
                }

            }
        });


    }

    //异步任务
    private class verificationLoging extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String result) {
            //密码正确处理
            if (result.equals("正确")) {
                Intent intent = new Intent(LoginngActivity.this, MainActivity.class);
                startActivity(intent);
                //经登录对象存入share preference
                HashMap<String, Object> userdemo = null;
                for (int i = 0; i < userData.size(); i++) {
                    userdemo = userData.get(i);
                }
                SharedPreferences user = getSharedPreferences("userDate", MODE_PRIVATE);
                Editor editor = user.edit();
                editor.putString("userNickName", userdemo.get("usrname").toString());
                editor.putString("Gender", userdemo.get("Gender").toString());
                editor.putString("Count", userdemo.get("Count").toString());
                editor.putString("Head", userdemo.get("Head").toString());
                editor.putBoolean("userDate", true);
                editor.commit();
                //错误处理
            } else {
                tipeTextView.setText("账号或密码错误");
                Toast.makeText(LoginngActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }

        @Override
        protected String doInBackground(String... params) {
            // 检查网络连接
            if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                String result = verificationOperation(params[0], params[1]);
                return result;
            } else {
                String getPSD = UserDataSrvice.getUserPSD(params[0]);
                if (params[1].equals(getPSD)) {
                    HashMap<String, Object> hashMap = UserDataSrvice.getUserData(params[0]);
                    userData.add(hashMap);
                    return "正确";
                }else{
                    return "错误";
                }
            }
        }

    }

    //到数据库去验证的操作
    public String verificationOperation(String username, String password) {
        Log.d("LoginngActivity", "到数据库验证来了 ");
        String url = Constants.BASEURL + "/ServertforLoging";
        String params = "username=" + username + "&password=" + password;
        //得到服务端传过来的数据
        try {
            Log.d("LoginngActivity", "到实例化链接来了来了 " + username + password);
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            String jsonstr = httpUtil.httpPost(url, params);
            //把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);
            result = myjsonObject.get("result").toString();
            if (result.equals("正确")) {

                System.out.println("202020" + myjsonObject.get("userDataList"));
                //拿出user对象数据
                JSONArray userDataList = myjsonObject.getJSONArray("userDataList");
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                for (int i = 0; i < userDataList.size(); i++) {
                    JSONObject jsonObject = userDataList.getJSONObject(i);

                    hashMap.put("usrname", jsonObject.getString("nickName"));
                    hashMap.put("Gender", jsonObject.getString("Gender"));
                    hashMap.put("Count", jsonObject.getString("Count"));
                    hashMap.put("Head", jsonObject.getString("Head"));
                    userData.add(hashMap);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loginng, menu);
        return true;
    }


}
