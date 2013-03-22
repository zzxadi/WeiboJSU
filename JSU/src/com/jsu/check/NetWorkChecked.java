package com.jsu.check;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetWorkChecked
{
	 /**
     * 检测网络是否可用
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context )
    {
    	boolean isNetWork = false;  
        ConnectivityManager connectManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	if(connectManager.getActiveNetworkInfo() != null )
    	{
    		isNetWork = true;
    	}
        return isNetWork;
    }
}
