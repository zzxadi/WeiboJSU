package com.jsu.db;

import android.provider.BaseColumns;

/**
 * 本类是 Sqlite数据库列常量
 * http://stackoverflow.com/questions/7899720/what-is-the-use-of-basecolumns-in-android
 */
public class AccessInfoColumn implements BaseColumns
{
	public AccessInfoColumn()
	{

	}

	// 列名
	public static final String USERID = "USERID";
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	public static final String ACCESS_SECRET = "ACCESS_SECRET";

	// 索引值
	public static final int _ID_ACCESS = 0;
	public static final int USERID_COLUMN = 1;
	public static final int ACCESS_TOKEN_COLUMN = 2;
	public static final int ACCESS_SECRET_COLUMN = 3;

	// 查询结果集
	public static final String[] PROJECTION = { 
		    _ID, // 0
			USERID, // 1
			ACCESS_TOKEN, // 2
			ACCESS_SECRET, // 3
	};
}