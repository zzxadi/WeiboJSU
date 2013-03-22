package com.jsu.bean;

import java.util.List;

public class WeiBoData
{
	private String text;
	private String origtext;
	private int count;
	private int mcount;
	private String from;
	private long id;
	// private image
	private String name;
	private String nick;
	private String uid;
	private int self;
	private long timestamp;
	private int type;
	private String head;
	private String location;
	private String country_code;
	private String province_code;
	private String city_code;
	private int isVip;
	private int status;
	private WeiBoData source;
	private List<String> image;

	public List<String> getImage()
	{
		return image;
	}

	public void setImage(List<String> image)
	{
		this.image = image;
	}

	public WeiBoData getSource()
	{
		return source;
	}

	public void setSource(WeiBoData source)
	{
		this.source = source;
	}

	@Override
	public String toString()
	{
		return "WeiBoData [city_code=" + city_code + ", count=" + count
				+ ", country_code=" + country_code + ", from=" + from
				+ ", img_head=" + head + ", id=" + id + ", image=" + image
				+ ", isVip=" + isVip + ", location=" + location + ", mcount="
				+ mcount + ", name=" + name + ", nick=" + nick + ", origtext="
				+ origtext + ", province_code=" + province_code + ", self="
				+ self + ", source=" + source + ", status=" + status
				+ ", text=" + text + ", timestamp=" + timestamp + ", type="
				+ type + ", uid=" + uid + "]";
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getOrigtext()
	{
		return origtext;
	}

	public void setOrigtext(String origtext)
	{
		this.origtext = origtext;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getMcount()
	{
		return mcount;
	}

	public void setMcount(int mcount)
	{
		this.mcount = mcount;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNick()
	{
		return nick;
	}

	public void setNick(String nick)
	{
		this.nick = nick;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public int getSelf()
	{
		return self;
	}

	public void setSelf(int self)
	{
		this.self = self;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getHead()
	{
		return head;
	}

	public void setHead(String head)
	{
		this.head = head;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getCountry_code()
	{
		return country_code;
	}

	public void setCountry_code(String countryCode)
	{
		country_code = countryCode;
	}

	public String getProvince_code()
	{
		return province_code;
	}

	public void setProvince_code(String provinceCode)
	{
		province_code = provinceCode;
	}

	public String getCity_code()
	{
		return city_code;
	}

	public void setCity_code(String cityCode)
	{
		city_code = cityCode;
	}

	public int getIsVip()
	{
		return isVip;
	}

	public void setIsVip(int isVip)
	{
		this.isVip = isVip;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
}
