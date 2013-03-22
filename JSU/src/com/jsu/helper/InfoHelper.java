package com.jsu.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.jsu.bean.AccessInfo;
import com.jsu.db.AccessInfoHelper;

/** 
 *本类是一个帮助类
 */
public class InfoHelper 
{
      
	/**
	 * 使用当前时间戳拼接一个唯一的文件名
	 * @param format
	 * @return
	 */
    public static String getFileName() 
    {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    	String fileName = format.format(new Timestamp(System.currentTimeMillis()));
    	return fileName;
    }
    
   
    
	
	
	/**
	 * 根据路径获取适应屏幕大小的Bitmap
	 * @param context
	 * @param filePath
	 * @param maxWidth
	 * @return
	 */
	public static Bitmap getScaleBitmap(Context context, String filePath)
	{
		BitmapFactory.Options opts = new BitmapFactory.Options();
		//高和宽都缩小4倍
		opts.inSampleSize = 4;
		
		//从filePath获取图片，并返回
		return BitmapFactory.decodeFile(filePath, opts);
	}
	
	/**
	 * 只要本地数据库中有数据，就表示登录过
	 * @param mContext
	 * @return
	 */
	public static AccessInfo getAccessInfo(Context mContext)
	{
		ArrayList<AccessInfo> list = null;
		AccessInfoHelper accessDBHelper = new AccessInfoHelper(mContext);
		accessDBHelper.open();
		
		try
		{
			list = accessDBHelper.getAccessInfos();
		}
		finally
		{
			accessDBHelper.close();
		}
		return (list!=null&&list.size()!=0)?list.get(0):null;
	}
}