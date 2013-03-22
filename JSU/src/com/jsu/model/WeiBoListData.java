package com.jsu.model;

import java.util.ArrayList;
import java.util.List;

import com.jsu.bean.WeiBoData;

public class WeiBoListData
{
	private long timestamp;
	private int hasnext;
	private int totalNum;
	private List<WeiBoData> info = new ArrayList<WeiBoData>();

	public List<WeiBoData> getInfo()
	{
		return info;
	}

	public void setInfo(List<WeiBoData> info)
	{
		this.info = info;
	}

	public WeiBoListData(long timestamp, int hasnext, int totalNum)
	{
		super();
		this.timestamp = timestamp;
		this.hasnext = hasnext;
		this.totalNum = totalNum;
	}

	public WeiBoListData()
	{
		super();
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public int getHasnext()
	{
		return hasnext;
	}

	public void setHasnext(int hasnext)
	{
		this.hasnext = hasnext;
	}

	public int getTotalNum()
	{
		return totalNum;
	}

	public void setTotalNum(int totalNum)
	{
		this.totalNum = totalNum;
	}

	@Override
	public String toString()
	{
		return "WeiBoListData [hasnext=" + hasnext + ", info=" + info
				+ ", timestamp=" + timestamp + ", totalNum=" + totalNum + "]";
	}

}
