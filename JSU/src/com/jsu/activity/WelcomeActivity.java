package com.jsu.activity;




import java.util.SortedSet;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

import com.jsu.bean.AccessInfo;
import com.jsu.check.StringChecked;
import com.jsu.check.UrlChecked;
import com.jsu.db.AccessInfoHelper;
import com.jsu.helper.InfoHelper;
import com.jsu.utils.ConstantsUtils;
import com.jsu.utils.UrlUtils;




public class WelcomeActivity extends BaseActivity
{
	private Context mContext = null;
	private AccessInfo accessInfo = null;
	
	//代表本应用
	private OAuthConsumer consumer;
	//代表新浪微博
	private OAuthProvider provider;
	
	public static WelcomeActivity webInstance = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{   
		super.onCreate(savedInstanceState);
		//设置屏幕没有标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.welcome);
		
		webInstance = this;
        mContext = getApplicationContext();
        accessInfo = InfoHelper.getAccessInfo(mContext);
        
        //生成consumer，consumer代表我们自己开发的应用JSU
    	consumer = new CommonsHttpOAuthConsumer(ConstantsUtils.CONSUMER_KEY, 
    			ConstantsUtils.CONSUMER_SECRET);
    	
    	//生成provider，provider代表新浪微博，传入认证需要的三个地址
		provider = new DefaultOAuthProvider(
				UrlUtils.REQUEST_TOKEN,
				UrlUtils.ACCESS_TOKEN,
				UrlUtils.AUTHORIZE);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
        //之前登陆过
        if(accessInfo!=null)
        {   
			startMainView();
        }
        //之前没登陆过
        else
        {
			startOAuthView();
        }
	}

	/**
	 * 启动程序主界面FriendsTimeLineActivity,并传入数据
	 */
	private void startMainView()
	{
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		//得到相应数据，并传给FriendsTimeLineActivity
		bundle.putString("thisLarge", getImgPathByCaptureSendFilter());
		bundle.putString("accessToken", accessInfo.getAccessToken());
		bundle.putString("accessSecret", accessInfo.getAccessSecret());
		bundle.putString("userId", accessInfo.getUserID());
		intent.putExtras(bundle);
		intent.setClass(WelcomeActivity.this, FriendsTimeLineActivity.class);
		startActivity(intent);
		finish();
	}
	
	/**
	 * 捕捉android.intent.action.SEND，并得到捕捉到的图片路径
	 * http://developer.android.com/reference/android/content/Intent.html#ACTION_SEND
	 * @return
	 */
	private String getImgPathByCaptureSendFilter()
	{
		String imgUrl = "";
		Uri mUri = null;
		final Intent intent = getIntent();
		final String action = intent.getAction();
		
		if(!StringChecked.isBlank(action) && "android.intent.action.SEND".equals(action) ) 
		{
			boolean hasExtra = intent.hasExtra("android.intent.extra.STREAM");
			if(hasExtra)
			{
				mUri = (Uri)intent.getParcelableExtra("android.intent.extra.STREAM");
			}
			
			if(mUri != null)
			{   
				
				if(UrlChecked.getAbsolutePathFromNoStandardUri(mUri) != null)
				{
					imgUrl = UrlChecked.getAbsolutePathFromNoStandardUri(mUri);
				}
				else
				{
					//根据给定的mUri获得图片的绝对地址
					imgUrl = getAbsoluteImagePath(mUri);
				}
			}	
		}
		return imgUrl;
	}
	
	/**
	 * 启动认证授权界面，让用户使用新浪微博账号登录
	 */
	private void startOAuthView()
	{
        try
        {
        	/*
        	 * retrieveRequestToken有三个作用
        	 * 第一步：获取未授权的Request Token
        	 * 第二步：将未授权的Request Token和key存入consumer中
        	 * 第三步：传入回调地址，当认证授权完后，返回到本Activity
        	 */
    		String authUrl =  provider.retrieveRequestToken(consumer, UrlUtils.CALLBACK_URL);
    		
    		//启动WebViewActivity，进行用户授权登录
    		Intent intent = new Intent();
    		Bundle bundle = new Bundle();
    		bundle.putString("url", authUrl);
    		intent.putExtras(bundle);
    		intent.setClass(mContext , WebViewActivity.class);
    		startActivity(intent);
    	}
        catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	//onNewIntent是方法当再次调用此Activity时，会首先触发。
	//本方法的目的是存储获取的AccessToken和 AccessSecret，并跳转到程序的主界面
	@Override
    protected void onNewIntent(Intent intent) 
	{
    	super.onNewIntent(intent);
    	
    	Uri uri = intent.getData();
    	//如果跳转不正确，则结束程序
    	if(uri==null)
    	{
    		return;
    	}
    	
    	//得到返回的六位验证码，作为请求AccessToken之用
    	String verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
    	
    	try 
    	{
    	 //请求Access Token Url用于获取授权的Access Token和Access Secret
          provider.setOAuth10a(true);           
          provider.retrieveAccessToken(consumer,verifier);
        } 
    	catch (OAuthMessageSignerException ex) {
            ex.printStackTrace();
        } 
    	catch (OAuthNotAuthorizedException ex) {
            ex.printStackTrace();
        } 
    	catch (OAuthExpectationFailedException ex) {
            ex.printStackTrace();
        } 
    	catch (OAuthCommunicationException ex) {
            ex.printStackTrace();
        }
        
    	//从provider中提取用户信息
        SortedSet<String> userInfoSet = provider.getResponseParameters().get("user_id");
        if(userInfoSet!=null&&!userInfoSet.isEmpty())
        {
        	//将得到的用户id，accessToken，accessSecret存到数据库中
            String userID = userInfoSet.first();
            String accessToken = consumer.getToken();
            String accessSecret = consumer.getTokenSecret();
            
            AccessInfo accessInfo = new AccessInfo();
            accessInfo.setUserID(userID);
            accessInfo.setAccessToken(accessToken);
            accessInfo.setAccessSecret(accessSecret);
            
            //打开数据库表，创建一条记录
            AccessInfoHelper accessDBHelper = new AccessInfoHelper(mContext);
            accessDBHelper.open();
            accessDBHelper.create(accessInfo);
            accessDBHelper.close();
            
            //跳转到微博主界面
            Intent intent2 = new Intent();
    		Bundle bundle = new Bundle();
    		bundle.putString("thisLarge", getImgPathByCaptureSendFilter());
    		bundle.putString("accessToken", accessInfo.getAccessToken());
    		bundle.putString("accessSecret", accessInfo.getAccessSecret());
    		intent2.putExtras(bundle);
    		intent2.setClass(WelcomeActivity.this, FriendsTimeLineActivity.class);
    		startActivity(intent2);
    		
    		//结束webviewActivity和本Activity
    		if(WebViewActivity.webInstance != null)
    		{    			
    			WebViewActivity.webInstance.finish();
    		}
    		finish();
        }
    }
}