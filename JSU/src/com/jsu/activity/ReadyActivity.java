package com.jsu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class ReadyActivity extends Activity
{
	private ImageView welcomeImg = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		  
		setContentView(R.layout.welcome);
		
		
		welcomeImg = (ImageView)findViewById(R.id.img_welcome);
	    

		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(2000);
        welcomeImg.setAnimation(animation);
        
        animation.setAnimationListener(new AnimationListener()
		{
			
			public void onAnimationStart(Animation animation)
			{
				
			}
			
			public void onAnimationRepeat(Animation animation)
			{
				
			}
			
		    //当动画结束后，启动欢迎界面，并结束本Activity
			public void onAnimationEnd(Animation animation)
			{
				Intent intent = new Intent();
				intent.setClass(ReadyActivity.this, WelcomeActivity.class);
				startActivity(intent);				
				finish();
			}
		});
		
	}
	
	
}
