package com.jsu.bean;


/**
 * 本类是一个普通的Java Bean。
 * 包涵三个属性userID，accessToken和accessSecret
 * @author zhang
 *
 */
public class AccessInfo
{
	// 用户Id
	private String userID;

	// accessToken
	private String accessToken;

	// accessSecret
	private String accessSecret;

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getAccessSecret()
	{
		return accessSecret;
	}

	public void setAccessSecret(String accessSecret)
	{
		this.accessSecret = accessSecret;
	}
}