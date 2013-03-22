package com.jsu.activity;

import java.util.List;

import weibo4android.Status;
import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jsu.adapter.WeiboAdapter;
import com.jsu.bean.AccessInfo;
import com.jsu.helper.InfoHelper;
import com.jsu.thread.ExitAppThread;
import com.jsu.utils.ConstantsUtils;
import com.jsu.utils.DialogUtils;
import com.jsu.utils.DialogUtils.DialogCallBack;

public class FriendsTimeLineActivity extends Activity
{
	private Weibo weibo = null;
	private User user = null;
	private AccessInfo accessInfo = null;
	private String accessToken, accessSecret, userId;
	private List<Status> stas = null;
	public static Context mContext;
	private ListView listView = null;
	private ProgressDialog dialog = null;

	private Button reflesh = null;
	private Button writeBtn = null;
	private TextView userName = null;

	public static FriendsTimeLineActivity webInstance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);

		listView = (ListView) findViewById(R.id.lv_weibos);
		writeBtn = (Button) findViewById(R.id.btn_writer);
		userName = (TextView) findViewById(R.id.txt_wb_title);
		reflesh = (Button) findViewById(R.id.btn_refresh);

		webInstance = this;
		mContext = getApplicationContext();
		accessInfo = InfoHelper.getAccessInfo(mContext);

		accessToken = accessInfo.getAccessToken();
		accessSecret = accessInfo.getAccessSecret();
		userId = accessInfo.getUserID();

		dialog = new ProgressDialog(webInstance);
		dialog.setIndeterminate(false);
		dialog.setCancelable(true);

		System.setProperty("weibo4j.oauth.consumerKey",
				ConstantsUtils.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				ConstantsUtils.CONSUMER_SECRET);

		// 得到一个weibo对象
		weibo = weibo4android.androidexamples.OAuthConstant.getInstance()
				.getWeibo();
		// 设置微博的accessToken和accessSecret
		weibo.setToken(accessToken, accessSecret);

		try
		{
			user = weibo.showUser(userId);
			userName.setText(user.getName());
		}
		catch (WeiboException e1)
		{
			e1.printStackTrace();
		}

		try
		{
			//得到weibo数据，使用WeiboAdapater进行显示
			stas = weibo.getFriendsTimeline();
			WeiboAdapter adapter = new WeiboAdapter(mContext, stas);
			listView.setAdapter(adapter);
		}
		catch (WeiboException e)
		{
			e.printStackTrace();
		}

		reflesh.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{

				Intent intent = new Intent();
				intent.setClass(FriendsTimeLineActivity.this,
						FriendsTimeLineActivity.class);
				startActivity(intent);
			}
		});

		writeBtn.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				Intent intent = new Intent();

				Bundle bundle = getIntent().getExtras();
				if (bundle != null)
				{
					bundle.putString("thisLarge", bundle.getString("thisLarge"));
					bundle.putString("accessToken",
							accessToken);
					bundle.putString("accessSecret",
							accessSecret);
				    bundle.putString("userId", userId);
					intent.putExtras(bundle);
				}
				intent.setClass(FriendsTimeLineActivity.this,
						JSUMainActivity.class);
				startActivity(intent);
			}
		});

	}
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
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

			if (JSUMainActivity.webInstance != null)
			{
				JSUMainActivity.webInstance.finish();
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
							
							ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);   
					        am.restartPackage(getPackageName()); 
						}
					});
		}
		return super.onOptionsItemSelected(item);
	}

}
