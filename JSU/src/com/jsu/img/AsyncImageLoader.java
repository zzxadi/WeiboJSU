package com.jsu.img;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/*
 * 功能：根据url判断图片是否存在于Cache，如果存在那么通知回调函数
 * 如果不存在则开启新线程下载图片，完成之后通知回调函数
 * 异步显示原理：
 * 1.在图片没有下载下来之前通知listView显示本地图片
 * 2.图片下载完成之后通知回调函数更新图片 
 */
public class AsyncImageLoader
{

	private HashMap<String, SoftReference<Bitmap>> imageCache;

	public AsyncImageLoader()
	{
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	public Bitmap loadImage(final String strUrl,
			final ImageCallback imageCallback)
	{
		Bitmap bitmap = null;

		// 查找图片是否在缓存，如果在缓存中直接返回该图片
		if (imageCache.containsKey(strUrl))
		{
			SoftReference<Bitmap> sr = imageCache.get(strUrl);
			bitmap = sr.get();
			if (bitmap != null)
				return bitmap;
		}

		final Handler myHandler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				imageCallback.imageLoaded((Bitmap) msg.obj);
				super.handleMessage(msg);
			}
		};

		// 启动新线程下载图片，下载完成之后Handler通知ListView显示
		new Thread(new Runnable()
		{
			public void run()
			{
				Bitmap bitmap = downImageFromUrl(strUrl);
				imageCache.put(strUrl, new SoftReference<Bitmap>(bitmap));
				Message msg = myHandler.obtainMessage(0, bitmap);
				myHandler.sendMessage(msg);
			}
		}).start();
		return null;
	}

	public Bitmap downImageFromUrl(String strUrl)
	{
		URL imageUrl;
		InputStream is = null;
		try
		{
			imageUrl = new URL(strUrl);
			URLConnection conn = imageUrl.openConnection();
			is = conn.getInputStream();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		Bitmap bm = BitmapFactory.decodeStream(bis);
		return bm;
	}

	// 回调函数，供下载完成后调用者做一些操作，方法在调用函数中实现
	public interface ImageCallback
	{
		public void imageLoaded(Bitmap bitmap);
	}
}