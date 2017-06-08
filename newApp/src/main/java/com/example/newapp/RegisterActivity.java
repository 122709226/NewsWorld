package com.example.newapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    private TextView textViewtip;
    private EditText editTextCount;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private String gender = "empty";
    private Integer result = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);


        //顶部提示
        textViewtip = (TextView) findViewById(R.id.regist_top_tip);
        //账号
        editTextCount = (EditText) findViewById(R.id.rigist_count);
        editTextCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && editTextCount.getText().toString().isEmpty()) {
                    textViewtip.setText("请输入正确的手机号码格式");
                } else {
                    if (!hasFocus && verifyount(editTextCount.getText().toString()) ) {
                        new getAsyc().execute("验证count", editTextCount.getText().toString());
                        textViewtip.setText("");
                        result = 1;
                    } else if (!hasFocus) {
                        textViewtip.setText("请输入正确的手机号码格式");
                        result = 2;
                    }

                }
            }
        });
        //呢陈
        editTextUsername = (EditText) findViewById(R.id.regist_username);
        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && editTextUsername.getText().toString().isEmpty() ) {
                    textViewtip.setText("昵称不能为空");
                } else if (!hasFocus) {
                    textViewtip.setText("");
                }
            }
        });
        //密码
        editTextPassword = (EditText) findViewById(R.id.regist_password);
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && editTextPassword.getText().toString().isEmpty()) {
                    textViewtip.setText("密码不能为空");
                } else if (!hasFocus && verifPassword(editTextPassword.getText().toString())) {
                    textViewtip.setText("");
                }
            }
        });
        //选择性别按按钮
        RadioGroup radio1 = (RadioGroup) findViewById(R.id.radioGroup1);
        final RadioButton rabuButton0 = (RadioButton) findViewById(R.id.radio0);
        final RadioButton rabuButton1 = (RadioButton) findViewById(R.id.radio1);
        radio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rabuButton0.getId()) {
                    gender = "男";
                } else if (checkedId == rabuButton1.getId()) {
                    gender = "女";
                }
            }
        });

        //提交按钮
        final Button button = (Button) findViewById(R.id.button_regist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = editTextCount.getText().toString();
                String username = editTextUsername.getText().toString();
                String passwore = editTextPassword.getText().toString();
                System.out.println(gender);
                if (gender.equals("empty")) {
                    Toast.makeText(RegisterActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                } else {
                    if (!count.isEmpty() && !username.isEmpty() && !passwore.isEmpty()) {
                        System.out.println(result + "5858585858585858s");
                        if (verifyount(count) && verifPassword(passwore) && result == 1) {
                            Log.d("RegisterActivity", "输入的数据" + count + username + passwore + gender);
                            new getAsyc().execute("非验证count", count, username, passwore, gender);
                        } else {
                            textViewtip.setText("请按正确格式输入账号密码");
                            Toast.makeText(RegisterActivity.this, "请按正确格式输入账号密码", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    //验证账号手机格式
    public boolean verifyount(String email) {
        String regex = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            textViewtip.setText("");
            return true;
        } else {
            textViewtip.setText("请输入正确的手机号码格式");
            return false;
        }
    }

    //验证密码个格式
    public boolean verifPassword(String pass) {
        String regex = "[a-zA-Z][a-zA-Z0-9]{5,15}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        if (matcher.matches()) {
            textViewtip.setText("");
            return true;
        } else {
            textViewtip.setText("请输入正确的密码格式");
            return false;
        }
    }

    /**
     * 异步任务
     */
    public class getAsyc extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onPostExecute(Integer result) {
            // TODO Auto-generated method stub
            System.out.println(result + "结果");
            switch (result) {
                case 1:
                    textViewtip.setText("该账号可以注册");
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "该账号已经被注册", Toast.LENGTH_LONG).show();
                    textViewtip.setText("该账号已经被注册");
                    break;
                case 3:
                    Intent intent = new Intent(RegisterActivity.this, LoginngActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    textViewtip.setText("注册成功");
                    //注册成功将对象存入sharepreference；
                    SharedPreferences user = getSharedPreferences("userDate", MODE_PRIVATE);
                    Editor editor = user.edit();
                    editor.putString("userNickName", editTextUsername.getText().toString());
                    editor.putString("Gender", gender.toString());
                    editor.putString("Count", editTextCount.getText().toString());
                    editor.putString("Head", "header/user_icon_pi.png");
                    editor.putBoolean("userDate", true);
                    editor.commit();
                    break;
                case 4:
                    textViewtip.setText("网络错误请重新尝试");
                    break;
                default:
                    break;
            }
            super.onPostExecute(result);
        }

        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (params[0].equals("验证count")) {
                // 检查网络连接
                if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                    return verifyUserCountOperation(params[0], params[1]);
                } else {
                    return UserDataSrvice.checkUserCountExist(params[1]);
                }
            } else {
                // 检查网络连接
                if (LocalDefaultSharePreference.getBooleanSharePreference("ConnectionState")) {
                    return saveUserdataOperation(params[0], params[1], params[2], params[3], params[4]);
                } else {
                    //new getAsyc().execute("非验证count", count, username, passwore, gender);
                    return UserDataSrvice.insertUserData(params[1], params[4], params[2], params[3], "");
                }
            }
        }


    }

    //到数据库去儲存data的操作
    public Integer saveUserdataOperation(String flag, String count, String username, String password, String gender) {
        Integer result = null;
        Log.d("RegisterActivity", "到数据库验证来了 ");
        String url = Constants.BASEURL + "/ServertForRegister";
        String params = "flag=" + flag + "&count=" + count + "&username=" + username + "&password=" + password + "&gender=" + gender;
        //得到服务端传过来的数据
        try {
            Log.d("LoginngActivity", "到实例化链接来了来了 " + username + password);
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            String jsonstr = httpUtil.httpPost(url, params);
            //把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);
            result = Integer.valueOf(myjsonObject.get("result").toString());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }

    //到数据库去验证账号的操作
    public Integer verifyUserCountOperation(String flag, String count) {
        Integer result = null;
        Log.d("RegisterActivity", "到数据库验证账号来了 ");
        String url = Constants.BASEURL + "/ServertForRegister";
        String params = "flag=" + flag + "&count=" + count;
        Log.d("RegisterActivity", flag + count + "传到这里的数据");
        //得到服务端传过来的数据
        try {
            SyncHttpUtil httpUtil = SyncHttpUtil.createInstance();
            String jsonstr = httpUtil.httpPost(url, params);
            //把数据转换为fastjson类型
            JSONObject myjsonObject = (JSONObject) JSON.parse(jsonstr);
            result = Integer.valueOf(myjsonObject.get("result").toString());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }


}
