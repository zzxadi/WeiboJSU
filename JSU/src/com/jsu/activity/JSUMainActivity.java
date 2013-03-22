package com.jsu.activity;

import java.io.File;
import java.net.URLEncoder;

import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.androidexamples.OAuthConstant;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsu.check.NetWorkChecked;
import com.jsu.check.StringChecked;
import com.jsu.check.UrlChecked;
import com.jsu.helper.InfoHelper;
import com.jsu.thread.ExitAppThread;
import com.jsu.utils.ConstantsUtils;
import com.jsu.utils.DialogUtils;
import com.jsu.utils.DialogUtils.DialogCallBack;
import com.jsu.utils.FileUtils;
import com.jsu.utils.MediaUtils;

public class JSUMainActivity extends BaseActivity
{
	private TextView userName = null;
	private Button button = null;
	private Button friBtn = null;
	private ImageButton imgChooseBtn = null;
	private ImageView imgView = null;
	private TextView wordCounterTextView = null;
	private EditText contentEditText = null;
	private ProgressDialog dialog = null;

	// 获取bundle传来的值
	private String accessToken, accessSecret, userId, name;
	private String thisLarge = null;
	

	public static JSUMainActivity webInstance = null;
	public static Context mContext;

	private Weibo weibo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);

		// 设置
		System.setProperty("weibo4j.oauth.consumerKey",
				ConstantsUtils.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				ConstantsUtils.CONSUMER_SECRET);

		webInstance = this;
		mContext = getApplicationContext();

		getBundle();

		button = (Button) findViewById(R.id.btn_send);
		friBtn = (Button) findViewById(R.id.btn_friend);
		userName = (TextView) findViewById(R.id.txt_user_name);
		imgChooseBtn = (ImageButton) findViewById(R.id.btn_choose_img);
		imgView = (ImageView) findViewById(R.id.img_choose);
		wordCounterTextView = (TextView) findViewById(R.id.txt_word_count);
		contentEditText = (EditText) findViewById(R.id.txt_content);

		dialog = new ProgressDialog(webInstance);
		dialog.setMessage("发送中...");
		dialog.setIndeterminate(false);
		dialog.setCancelable(true);

		button.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{

				if (!NetWorkChecked.checkNetWork(mContext))
				{
					Toast.makeText(mContext, "网络连接失败，请检查网络设置！",
							Toast.LENGTH_LONG).show();
				}
				else
				{
					if (isChecked())
					{
						dialog.show();

						Thread thread = new Thread(new UpdateStatusThread());
						thread.start();
					}
				}
			}
		});

		friBtn.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(JSUMainActivity.this,
						FriendsTimeLineActivity.class);
				startActivity(intent);
			}
		});

		imgChooseBtn.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				CharSequence[] items = { "手机相册", "手机拍照", "清除照片" };
				imageChooseItem(items);
			}
		});

		// 监听EditText字数改变
		TextWatcher watcher = new TextWatcher()
		{
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				textCountSet();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				textCountSet();
			}

			public void afterTextChanged(Editable s)
			{
				textCountSet();
			}
		};

		contentEditText.addTextChangedListener(watcher);

		// 如果文件地址不为空
		if (!StringChecked.isBlank(thisLarge))
		{
			String imgName = FileUtils.getFileName(thisLarge);
			// ImgThumbnailActivity thumbnail = new ImgThumbnailActivity();
			Bitmap bitmap = loadImgThumbnail(imgName,
					MediaStore.Images.Thumbnails.MICRO_KIND);
			if (bitmap != null)
			{
				imgView.setBackgroundDrawable(new BitmapDrawable(bitmap));
				imgView.setOnClickListener(new OnClickListener()
				{
					public void onClick(View v)
					{
						// 隐式启动Activity，到Android手机图库中查找图片
						Intent intent = new Intent();
						intent.setAction(android.content.Intent.ACTION_VIEW);
						intent.setDataAndType(
								Uri.fromFile(new File(thisLarge)), "image/*");
						startActivity(intent);
					}
				});
			}
		}

		// 得到一个weibo对象
		weibo = OAuthConstant.getInstance().getWeibo();
		// 设置微博的accessToken和accessSecret
		weibo.setToken(accessToken, accessSecret);

		try
		{
			name = weibo.showUser(userId).getName();
			userName.setText(name);
		}
		catch (WeiboException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

	private void getBundle()
	{
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			thisLarge = bundle.containsKey("thisLarge") ? bundle
					.getString("thisLarge") : "";
			accessToken = bundle.containsKey("accessToken") ? bundle
					.getString("accessToken") : "";
			accessSecret = bundle.containsKey("accessSecret") ? bundle
					.getString("accessSecret") : "";
			userId = bundle.containsKey("userId") ? bundle.getString("userId")
					: "";
		}
	}

	/**
	 * 设置稿件字数
	 */
	private void textCountSet()
	{
		String textContent = contentEditText.getText().toString();
		int currentLength = textContent.length();
		if (currentLength <= 140)
		{
			wordCounterTextView.setTextColor(Color.BLACK);
			wordCounterTextView.setText(String.valueOf(textContent.length()));
		}
		else
		{
			wordCounterTextView.setTextColor(Color.RED);
			wordCounterTextView.setText(String.valueOf(140 - currentLength));
		}
	}

	/**
	 * 选择发送的图片来自手机图库还是照相机
	 * 
	 * @param items
	 */
	public void imageChooseItem(CharSequence[] items)
	{
		AlertDialog imageDialog = new AlertDialog.Builder(webInstance)
				.setTitle("增加图片")
				.setItems(items, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int item)
					{
						// 从手机内获取图
						if (item == 0)
						{
							// 启动Android手机图库
							startImgIntent();
						}
						// 通过手机照相机拍照获取图片
						else if (item == 1)
						{
							startCameraIntent();
						}
						// 清除已选的图片
						else if (item == 2)
						{
							thisLarge = null;
							imgView.setBackgroundDrawable(null);
						}
					}
				}).create();

		imageDialog.show();
	}

	/**
	 * 回调方法，当从手机图库或者照相机选择图片后，会执行此方法， 且带回返回值
	 * 
	 * @param requestCode
	 *            用以便确认返回的数据是从哪个Activity返回的。
	 * @param resultCode
	 *            由子Activity通过其setResult()方法返回.
	 * @param data
	 *            个Intent对象，带有返回的数据。
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// 如果是从手机图库中选择的图片
		if (requestCode == ConstantsUtils.REQUEST_CODE_GETIMAGE_BYSDCARD)
		{
			if (resultCode != RESULT_OK)
			{
				return;
			}

			if (data == null)
			{
				return;
			}

			// 从返回的Intent中得到图片的uri
			Uri thisUri = data.getData();
			String thePath = UrlChecked
					.getAbsolutePathFromNoStandardUri(thisUri);

			// 如果是标准Uri
			if (StringChecked.isBlank(thePath))
			{
				thisLarge = getAbsoluteImagePath(thisUri);
			}
			else
			{
				thisLarge = thePath;
			}

			// 根据文件地址获取文件的扩展名
			String attFormat = FileUtils.getFileFormat(thisLarge);
			if (!"photo".equals(MediaUtils.getContentType(attFormat)))
			{
				Toast.makeText(mContext, "请选择图片文件！", Toast.LENGTH_SHORT).show();
				return;
			}

			// 根据文件地址获取文件名
			String imgName = FileUtils.getFileName(thisLarge);
		
			// 获取文件的缩略图，并显示在主界面上
			Bitmap bitmap = loadImgThumbnail(imgName,
					MediaStore.Images.Thumbnails.MICRO_KIND);
			if (bitmap != null)
			{
				imgView.setBackgroundDrawable(new BitmapDrawable(bitmap));
			}
		}
		 //如果是拍摄的图片
        else if(requestCode == ConstantsUtils.REQUEST_CODE_GETIMAGE_BYCAMERA )
        {	
        	super.onActivityResult(requestCode, resultCode, data);
        	if (resultCode != RESULT_OK) 
    		{   
    	        return;   
    	    }
        	
        	//得到适应屏幕的Bitmap
        	Bitmap bitmap = InfoHelper.getScaleBitmap(mContext, thisLarge);
        	
    		if(bitmap!=null)
    		{
    			imgView.setBackgroundDrawable(new BitmapDrawable(bitmap));
    		}
        }
        
	
		imgView.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(thisLarge)),
						"image/*");
				startActivity(intent);
			}
		});
	}

	/**
	 * 判断数据的合法性，即不能发送空微博和超过 140字的微博
	 * 
	 * @return
	 */
	private boolean isChecked()
	{
		boolean result = true;
		if (StringChecked.isBlank(contentEditText.getText().toString()))
		{
			Toast.makeText(mContext, "有什么新鲜事告诉大家?", Toast.LENGTH_SHORT).show();
			result = false;
		}
		else if (contentEditText.getText().toString().length() > 140)
		{
			int currentLength = contentEditText.getText().toString().length();

			Toast.makeText(mContext, "已超出" + (currentLength - 140) + "字",
					Toast.LENGTH_SHORT).show();
			result = false;
		}
		return result;
	}

	/**
	 * 异步判断微博是否发送成功
	 */
	Handler handle = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (dialog != null)
			{
				dialog.dismiss();
			}

			thisLarge = null;
			contentEditText.setText("");
			imgView.setBackgroundDrawable(null);

			if (msg.what > 0)
			{
				Toast.makeText(mContext, "微博发送成功", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(mContext, "微博发送失败", Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * 发送微博线程
	 */
	class UpdateStatusThread implements Runnable
	{
		public void run()
		{
			//声明一个标记位，当微博发送成功时置为1
			int what = -1;

			try
			{
				//得到要发送的微博内容
				String msg = contentEditText.getText().toString();

				Status status = null;
				//如果发送图片地址为空，则只发送文字微博
				if (StringChecked.isBlank(thisLarge))
				{
					status = weibo.updateStatus(msg);
				}
				//否则得到图片文件，和文字一起发送
				else
				{
					File file = new File(thisLarge);
					status = weibo.uploadStatus(msg, file);
				}
				//如果发送成功，则置what为1
				if (status != null)
				{
					what = 1;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				Log.e("sendWeiBoError", e.getMessage());
			}
			//使用handle发送通知
			handle.sendEmptyMessage(what);
		}
	}

	/**
	 * 创建一个Menu，提供两个功能 退出程序和注销用户
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0, ConstantsUtils.ITEM1, 1, "退出程序").setIcon(
				android.R.drawable.ic_menu_revert);
		menu.add(0, ConstantsUtils.ITEM2, 2, "注销用户").setIcon(
				android.R.drawable.ic_menu_delete);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == 0)
		{
			
			if(FriendsTimeLineActivity.webInstance != null){
				FriendsTimeLineActivity.webInstance.finish();
			}
			finish();
		}
		else if (item.getItemId() == 1)
		{
			DialogUtils.dialogBuilder(webInstance, "提示", "确定要注销用户并退出？",
					new DialogCallBack()
					{
						public void callBack()
						{
							dialog.show();
							dialog.setMessage("注销用户中...");
							ExitAppThread exit = new ExitAppThread(mContext);
							Thread thread = new Thread(exit);
							thread.start();
						}
					});
		}
		return super.onOptionsItemSelected(item);
	}

	private void startImgIntent()
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent,
				ConstantsUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}
	
	private void startCameraIntent()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	
		
		String camerName = InfoHelper.getFileName();
		String fileName = "jsu" + camerName + ".tmp";	
		String directory = Environment.getExternalStorageDirectory() + File.separator +  "JSU" + File.separator;
		
		File camerFile = new File(directory, fileName);
				
		thisLarge = getLatestImage();
		
		Uri originalUri = Uri.fromFile(camerFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri); 	
		startActivityForResult(intent, ConstantsUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	
}