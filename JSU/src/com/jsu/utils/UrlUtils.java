package com.jsu.utils;


public class UrlUtils
{
  	
   //用于获取未授权的Request Token
   public final static String REQUEST_TOKEN = "http://api.t.sina.com.cn/oauth/request_token";
   
   //用于获取Access Token
   public final static String ACCESS_TOKEN = "http://api.t.sina.com.cn/oauth/access_token";
   
   //用于对未授权的Request Token进行验证授权
   public final static String AUTHORIZE = "http://api.t.sina.com.cn/oauth/authorize";
   
   //回调的协议
   public final static String OAUTH_CALLBACK_SCHAME = "JSU";
   
   //回调的地址
   public final static String OAUTH_CALLBACK_HOST = "WelcomeActivity"; 
   
   //回调URL
   public final static String CALLBACK_URL = OAUTH_CALLBACK_SCHAME + "://" + OAUTH_CALLBACK_HOST;
	
   
	
}
