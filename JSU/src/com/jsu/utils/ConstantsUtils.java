package com.jsu.utils;

import com.jsu.db.AccessInfoColumn;

public class ConstantsUtils
{
   //consumer key
   public final static String CONSUMER_KEY = "";
   
   //consumer secret
   public final static String CONSUMER_SECRET = "";
   
   //字符编码
   public final static String ENCODING = "utf-8";
   
   /*
    * sdcard_mnt和sdcard就是同一个文件夹（是手机上的内存），
    * 相当于在电脑桌面建立的快捷方式和实际位置,其内容一样的。
    */
   public final static String SDCARD_MNT = "/mnt/sdcard";
   public final static String SDCARD = "/sdcard";
   
   public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
   public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
   
   //菜单按钮
   public static final int ITEM1 = 0;
   public static final int ITEM2 = 1;
   
   //数据库信息
   public static final String DATABASE_NAME = "jsu.db";
   public static final int DATABASE_VERSION = 1;
   public static final String ACCESSLIB_TABLE = "accessinfo";
	
   public static final String CREATE_ACCESSINFO_LIB = "CREATE TABLE " + ACCESSLIB_TABLE +" ("
		+ AccessInfoColumn._ID + " integer primary key autoincrement,"
		+ AccessInfoColumn.USERID + " text,"
		+ AccessInfoColumn.ACCESS_TOKEN + " text,"
		+ AccessInfoColumn.ACCESS_SECRET + " text)";
}
