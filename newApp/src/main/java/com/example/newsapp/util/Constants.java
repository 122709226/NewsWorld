package com.example.newsapp.util;

import android.os.Environment;

import java.io.File;

public class Constants {

	public static final String LOG_ERROR = "LOG_ERROR";
	public static final String LOG_DEBUG = "LOG_DEBUG";

	public static final String BASEURL ="http://192.168.1.115:8080/ServertForAppNews/servlet";
	//public static final String BASEURL ="http://169.254.109.163:8080/ServertForAppNews/servlet";
	public static final String IMAGEBASEURL ="http://192.168.1.115:8080/ServertForAppNews/";
	//public static final String IMAGEBASEURL ="http://169.254.109.163:8080/ServertForAppNews/";



	public static final String APP_NAME = "JIYOsNews.newApp";
	public static final String LOG_DIR = MyApplicationContext.getMyApplicationContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS)+ File.separator+"Log"+File.separator;
}
