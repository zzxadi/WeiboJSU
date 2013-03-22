package com.jsu.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;


public class BaseActivity extends Activity
{
	
	 /**
     * 通过uri获取文件的绝对路径
     * @param uri
     * @return
     */
	public String getAbsoluteImagePath(Uri uri) 
    {
		String imagePath = "";
        String [] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,
                        proj, 		// Which columns to return
                        null,       // WHERE clause; which rows to return (all rows)
                        null,       // WHERE clause selection arguments (none)
                        null); 		// Order-by clause (ascending by name)
        
        if(cursor!=null)
        {
        	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        	if(cursor.getCount() > 0 && cursor.moveToFirst())
            {
            	imagePath = cursor.getString(column_index);
            }
        }
        
        return imagePath;
    }
	
	
	
	/**
	 * 获取SD卡中最新图片路径
	 * http://www.eoeandroid.com/thread-37464-1-1.html
	 * @return
	 */
	@SuppressWarnings("unused")
	protected String getLatestImage()
	{
		String latestImage = null;
		String[] items = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA }; 
		Cursor cursor = managedQuery(
		                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
		                                items, 
		                                null,
		                                null, 
		                                null);
		
		if( cursor != null && cursor.getCount()>0 )
		{
			cursor.moveToFirst();
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			{
				latestImage = cursor.getString(1);
				break;
			}
		}
		
	    return latestImage;
	}
	
	
	
	/**
	 * 获取图片缩略图
	 * 只有Android2.1以上版本支持
	 * http://blog.csdn.net/kesenhoo/article/details/6569885
	 * @param imgName
	 * @param kind   MediaStore.Images.Thumbnails.MICRO_KIND
	 * @return
	 */
	protected Bitmap loadImgThumbnail(String imgName, int kind) 
	{
		Bitmap bitmap = null;
		
        String[] proj = { MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME };
        //从图库里查找指定名字的图片
        Cursor cursor = managedQuery(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
                        MediaStore.Images.Media.DISPLAY_NAME + "='" + imgName +"'", null, null);
       
        if (cursor!=null && cursor.getCount()>0 && cursor.moveToFirst()) 
        {
        	ContentResolver crThumb = getContentResolver();
        	
        	//将bitmap使用options进行缩略
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bitmap = MediaStore.Images.Thumbnails.getThumbnail(crThumb,
            		cursor.getInt(0),
                	kind, options);
        } 
        return bitmap;
	}
}