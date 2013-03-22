package com.jsu.img;

import com.jsu.activity.R;
import com.jsu.img.AsyncImageLoader.ImageCallback;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class SimpleImageLoader
{
	private static AsyncImageLoader asyncImageLoader;

	public static void showImg(final ImageView v, String value)
	{

		asyncImageLoader = new AsyncImageLoader();

		Bitmap bm = asyncImageLoader.loadImage(value, new ImageCallback()
		{
			public void imageLoaded(Bitmap bitmap)
			{
				v.setImageBitmap(bitmap);
			}
		});
		if (bm == null)
		{
			v.setImageResource(R.drawable.usericon);
		}
		else
		{
			v.setImageBitmap(bm);
		}

		

	}


}
