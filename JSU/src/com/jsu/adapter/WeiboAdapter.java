package com.jsu.adapter;

import java.util.List;

import weibo4android.Status;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsu.activity.R;
import com.jsu.check.StringChecked;
import com.jsu.img.SimpleImageLoader;

public class WeiboAdapter extends BaseAdapter
{

	private List<Status> status;
	private LayoutInflater inflater = null;

	public WeiboAdapter(Context context, List<Status> status)
	{

		this.status = status;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * 返回item个数
	 */
	public int getCount()
	{
		return status == null ? 0 : status.size();
	}

	/**
	 * 返回指定位置的item
	 */
	public Object getItem(int position)
	{
		return status == null ? null : status.get(position);
	}

	/**
	 * 返回指定位置item的Id
	 */
	public long getItemId(int position)
	{
		return status.get(position).getId();
	}

	/**
	 * 返回指定位置要显示的view
	 * 也就是listView中相应位置显示的view
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{

		View view;
		if (convertView == null)
		{
			view = inflater.inflate(R.layout.wb_item, null);
		}
		else
		{
			view = convertView;
		}

		bindView(position, view);
		return view;
	}

	private void bindView(int position, View view)
	{

		Status s = this.status.get(position);

		WeiboHolder holder = findView(view, s);
		
		holder.txt_name.setText(s.getUser().getName());
		holder.txt_content.setText(s.getText());
		//根据得到的用户头像url来异步加载用户头像
		SimpleImageLoader.showImg(holder.img_head, s.getUser()
				.getProfileImageURL().toString());
		//显示微博来源
		holder.txt_from.setText("来自:" + Html.fromHtml(s.getSource()));
		// 判断是否通过认证
		if (s.getUser().isVerified())
		{
			holder.img_v.setVisibility(View.VISIBLE);
		}
		else
		{			
			holder.img_v.setVisibility(View.GONE);
		}

		// 判断微博中是否含有图片
		if (!StringChecked.isBlank(s.getThumbnail_pic()))
		{
			holder.img_content.setVisibility(View.VISIBLE);
			//异步加载微博图片
			SimpleImageLoader.showImg(holder.img_content,
					s.getThumbnail_pic());
		}
		else
		{
			holder.img_content.setVisibility(View.GONE);
		}
		// 判断使用转发
		if (s.getRetweeted_status() != null)
		{
			holder.lyt_sublayout.setVisibility(View.VISIBLE);
			holder.txt_subcontent.setText(s.getRetweeted_status()
					.getText());

			// 判断微博中是否含有图片
			if (!StringChecked.isBlank(s.getRetweeted_status()
					.getThumbnail_pic()))
			{
				holder.img_subcontent.setVisibility(View.VISIBLE);
				SimpleImageLoader.showImg(holder.img_subcontent, s
						.getRetweeted_status().getThumbnail_pic());
			}
			else
			{
				holder.img_subcontent.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			holder.lyt_sublayout.setVisibility(View.GONE);
		}

	}

	/**
	 * 给WeiboHolder中声明的变量赋值
	 * @param view
	 * @param s
	 * @return
	 */
	private WeiboHolder findView(View view, Status s)
	{
		WeiboHolder holder = new WeiboHolder();

		holder.txt_name = (TextView) view
				.findViewById(R.id.txt_uname);
		holder.txt_content = (TextView) view
				.findViewById(R.id.txt_content);
		holder.txt_from = (TextView) view
				.findViewById(R.id.txt_from);
		holder.img_head = (ImageView) view
				.findViewById(R.id.img_head);
		holder.img_v = (ImageView) view
				.findViewById(R.id.img_V);
		holder.img_content = (ImageView) view
				.findViewById(R.id.img_content);
		holder.lyt_sublayout = (LinearLayout) view
				.findViewById(R.id.lyt_sublayout);
		holder.txt_subcontent = (TextView) view
				.findViewById(R.id.txt_subcontent);
		holder.img_subcontent = (ImageView) view
				.findViewById(R.id.img_subcontent);
		
		return holder;
	}

	/**
	 * 内部类，声明微博需要显示的控件	 *
	 */
	private static class WeiboHolder
	{
		//微博用户头像
		ImageView img_head;
		//微博用户姓名
		TextView txt_name;
		//加V图像
		ImageView img_v;
		//微博内容
		TextView txt_content;
		//微博图像
		ImageView img_content;
		//转发布局
		LinearLayout lyt_sublayout;
		//转发者内容
		TextView txt_subcontent;
		//转发者内容图像
		ImageView img_subcontent;
		//来源
		TextView txt_from;
	}

}
