package com.jsu.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jsu.utils.ConstantsUtils;

/**
 * 本类是数据库帮助类
 * http://www.ibm.com/developerworks/cn/opensource/os-cn-sqlite/
 */
public class DBHelper extends SQLiteOpenHelper
{
	/**
	 * 构造函数
	 * @param context
	 */
	public DBHelper(Context context)
	{
		super(context, ConstantsUtils.DATABASE_NAME, null,
				ConstantsUtils.DATABASE_VERSION);
	}

	/**
	 * 在数据库中创建一个名为jsu的表，表中有四个字段
	 * 第一个字段为_ID,整型 主键 自增
	 * 第二个字段为USERID, 字符串文本
	 * 第三个字段为ACCESS_TOKEN, 字符串文本
	 * 第四个字段为ACESS_SECRET, 字符串文本
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(ConstantsUtils.CREATE_ACCESSINFO_LIB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
}