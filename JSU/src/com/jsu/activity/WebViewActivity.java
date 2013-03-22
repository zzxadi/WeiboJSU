package com.jsu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class WebViewActivity extends BaseActivity 
{
	private WebView webView;
	private Intent intent = null;
	private String url = "url";
	private String titleMsg = "请稍等，JSU正在加载中...";
	public static WebViewActivity webInstance = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//设置进度条风格，长条在标题栏下显示
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.web);
		
		webInstance = this;
		getApplicationContext();
		webView  = (WebView)findViewById(R.id.web);
		
		//设置web页面
		webSet();
        
		//监听web页面
        webView.setOnTouchListener(new OnTouchListener()
        {
			public boolean onTouch(View v, MotionEvent event) {
				webView.requestFocus();
				return false;
			}
        });
        
        
		intent = this.getIntent();
		if(!intent.equals(null))
		{
			//得到从Welcome页面传过来的OAuthUrl
			Bundle b = intent.getExtras();
		    if(b != null && b.containsKey(url))
		    {  
		    	//打开url
		    	webView.loadUrl(b.getString(url));
		    	webView.setWebChromeClient(new WebChromeClient() {            
		    		  public void onProgressChanged(WebView view, int progress)               
		    		  {                   
		    			  setTitle(titleMsg + progress + "%");
		    			  setProgress(progress * 100);

		    			  if (progress == 100)	
		    			  setTitle(R.string.app_name);
		    		  }
		    	});
		    }
		}
	}


    /**
     * 设置web页面，使web页面可以执行Javascript脚本，支持保存密码等
     */
	private void webSet()
	{
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode( WebSettings.LOAD_NO_CACHE );
	}
	

	
    /**
     * 监听BACK键,按下时就结束本Activity
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {	
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 )
		{
			WelcomeActivity.webInstance.finish();
			finish();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}