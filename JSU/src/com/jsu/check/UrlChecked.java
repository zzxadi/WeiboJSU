package com.jsu.check;

import java.io.File;

import com.jsu.utils.ConstantsUtils;

import android.net.Uri;
import android.os.Environment;

public class UrlChecked
{
	/**
	 * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
	 * @param uri
	 * @return
	 */
	public static String getAbsolutePathFromNoStandardUri(Uri mUri)
	{	
		String filePath = null;
		
		String mUriString = mUri.toString();
		mUriString = Uri.decode(mUriString);
		
		//判断路径名，因为同一个文件有可能有两个表达地址
		String pre1 = "file://" + ConstantsUtils.SDCARD + File.separator;
		String pre2 = "file://" + ConstantsUtils.SDCARD_MNT + File.separator;
		
		if( mUriString.startsWith(pre1) )
		{    
			filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring(pre1.length() );
		}
		else if(mUriString.startsWith(pre2) )
		{
			filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring( pre2.length() );
		}
		return filePath;
	}
	
	
}
