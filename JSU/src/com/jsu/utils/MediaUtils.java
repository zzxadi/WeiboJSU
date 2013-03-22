package com.jsu.utils;

import java.util.HashMap;
import java.util.Map;


public class MediaUtils
{
	private static Map<String, String> FORMAT_TO_CONTENTTYPE = new HashMap<String, String>();
	
	static
	{			
		FORMAT_TO_CONTENTTYPE.put( "null", "null" );
		
		//图片
		FORMAT_TO_CONTENTTYPE.put( "jpg", "photo" );
		FORMAT_TO_CONTENTTYPE.put( "jpeg", "photo" );
		FORMAT_TO_CONTENTTYPE.put( "png", "photo" );
		FORMAT_TO_CONTENTTYPE.put( "bmp", "photo" );
		FORMAT_TO_CONTENTTYPE.put( "gif", "photo" );
	}
	
	/**
	 * 根据根据扩展名获取类型
	 * @param attFormat
	 * @return
	 */
	public static String getContentType(String attFormat )
	{
		String contentType = FORMAT_TO_CONTENTTYPE.get("null");
		
		if (attFormat != null ) 
		{
			contentType = (String)FORMAT_TO_CONTENTTYPE.get(attFormat.toLowerCase());
		}
		return contentType;
	}
}