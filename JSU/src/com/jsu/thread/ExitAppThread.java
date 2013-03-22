package com.jsu.thread;

import android.content.Context;

import com.jsu.activity.FriendsTimeLineActivity;
import com.jsu.activity.JSUMainActivity;
import com.jsu.db.AccessInfoHelper;

/**
 * 本类为用户注销线程
 *
 */
public class ExitAppThread implements Runnable
{

	private AccessInfoHelper accessDBHelper = null;
	private Context context = null;
	
	public ExitAppThread(Context context)
	{
		this.context = context;
	}
			
	public void run() 
	{
		accessDBHelper = new AccessInfoHelper(context);
		accessDBHelper.open();
		accessDBHelper.delete();
		accessDBHelper.close();   
		
		
		FriendsTimeLineActivity.webInstance.finish();
		
		if(JSUMainActivity.webInstance != null)
		{
			JSUMainActivity.webInstance.finish();
		}
			
		android.os.Process.killProcess(android.os.Process.myPid()); 
        System.exit(0); 
	}
}
