package com.jsu.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jsu.bean.AccessInfo;
import com.jsu.helper.DBHelper;
import com.jsu.utils.ConstantsUtils;

/** 
 * 本类是对数据库Sqlite进行操作
 */
public class AccessInfoHelper 
{
	private DBHelper dbHelper;
	private SQLiteDatabase newsDB;
	private Context context;
	
	public AccessInfoHelper(Context context )
	{
		this.context = context;
	}
	
	/**
	 * 初始化数据库连接
	 */
	public AccessInfoHelper open()
	{
		dbHelper = new DBHelper(this.context);
		//得到一个sqliteDatabase类的实例，使用这个对象可以查找或者操作数据库
		newsDB = dbHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * 关闭连接
	 */
	public void close()
	{
		if(dbHelper != null)
		{
			dbHelper.close();
		}
	}
	
	/**
	 * 创建一条记录
	 * @param accessInfo
	 * @return
	 */
	public long create(AccessInfo accessInfo)
	{
		ContentValues values = new ContentValues();
		
		//从accessInfo中得到要创建的数据，以值的方式存到values中
		values.put(AccessInfoColumn.USERID, accessInfo.getUserID() );
		values.put(AccessInfoColumn.ACCESS_TOKEN, accessInfo.getAccessToken() );
		values.put(AccessInfoColumn.ACCESS_SECRET, accessInfo.getAccessSecret() );
		
		//插入一条记录
		return newsDB.insert(ConstantsUtils.ACCESSLIB_TABLE, null, values);
	}
	
	/**
	 * 更新记录
	 * @param image
	 * @return
	 */
	public boolean update(AccessInfo accessInfo)
	{
		ContentValues values = new ContentValues();
		
		//从accessInfo中得到要更新的数据，以值的方式存到values中
		values.put(AccessInfoColumn.USERID, accessInfo.getUserID() );
		values.put(AccessInfoColumn.ACCESS_TOKEN, accessInfo.getAccessToken() );
		values.put(AccessInfoColumn.ACCESS_SECRET, accessInfo.getAccessSecret() );
		
		//更新条件
		String whereClause = AccessInfoColumn.USERID + "=" + accessInfo.getUserID();
		
		//更新记录
		return newsDB.update(ConstantsUtils.ACCESSLIB_TABLE, values, whereClause, null) > 0;
	}
	
	/**
	 * 获取全部AccessInfo信息
	 * @return
	 */
	public ArrayList<AccessInfo> getAccessInfos()
	{
		//创建一个List用户存储AccessInfo
		ArrayList<AccessInfo> list = new ArrayList<AccessInfo>();
		
		//声明一个accessInfo对象
		AccessInfo accessInfo = null;
		
		//从表中得到数据
		Cursor cursor = newsDB.query(ConstantsUtils.ACCESSLIB_TABLE, AccessInfoColumn.PROJECTION, 
				null, null, null, null, null);
		
		if(cursor != null && cursor.getCount() > 0)
		{
			//遍历查询获得的数据，将结果添加到list中
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext() )
			{
				accessInfo = new AccessInfo();
				
				accessInfo.setUserID(cursor.getString(AccessInfoColumn.USERID_COLUMN));
				accessInfo.setAccessToken(cursor.getString(AccessInfoColumn.ACCESS_TOKEN_COLUMN));
				accessInfo.setAccessSecret(cursor.getString(AccessInfoColumn.ACCESS_SECRET_COLUMN));
				list.add(accessInfo);
			}
		}
		
		cursor.close();
		
		return list;
	}
	
	/**
	 * 获取一条图片记录
	 * @param userID
	 * @return
	 */
	public AccessInfo getAccessInfo(String userID)
	{
		AccessInfo accessInfo = null;
		String selection = AccessInfoColumn.USERID + "=" + userID;
		
		//从表中得到数据
		Cursor cursor = newsDB.query(ConstantsUtils.ACCESSLIB_TABLE, AccessInfoColumn.PROJECTION, 
				selection, null, null, null, null);
		//如果查找记录不为空，且返回数据不为零
		if(cursor != null && cursor.getCount()>0)
		{
			cursor.moveToFirst();
			accessInfo = new AccessInfo();
			accessInfo.setUserID(cursor.getString(AccessInfoColumn.USERID_COLUMN));
			accessInfo.setAccessToken(cursor.getString(AccessInfoColumn.ACCESS_TOKEN_COLUMN));
			accessInfo.setAccessSecret(cursor.getString(AccessInfoColumn.ACCESS_SECRET_COLUMN));
		}
		return accessInfo;
	}
	
	/**
	 * 删除
	 */
	public boolean delete()
	{
		//删除表
		int ret = newsDB.delete(ConstantsUtils.ACCESSLIB_TABLE, null, null);
		return ret > 0 ? true : false;
	}
}