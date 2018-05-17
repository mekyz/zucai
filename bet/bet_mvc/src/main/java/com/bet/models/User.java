package com.bet.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class User
{
	@SerializedName("userId")
	private String userId;
	@SerializedName("title")
	private String title;
	@SerializedName("userType")
	private byte userType;
	@SerializedName("referrerId")
	private String referrerId;
	@SerializedName("number")
	private String number;
	@SerializedName("name")
	private String name;
	@SerializedName("activeStatus")
	private byte activeStatus;
	@SerializedName("regDateLong")
	private long regDateLong;
	@SerializedName("activeDateLong")
	private Long activeDateLong;
	@SerializedName("depth")
	private int depth;
	@SerializedName("children")
	private List<User> subUserList;

	public User(String userId, String title, byte userType, String referrerId, String number, String name, byte activeStatus, long regDateLong, Long activeDateLong, int depth, List<User> subUserList)
	{
		super();
		this.userId = userId;
		this.title = title;
		this.userType = userType;
		this.referrerId = referrerId;
		this.number = number;
		this.name = name;
		this.activeStatus = activeStatus;
		this.regDateLong = regDateLong;
		this.activeDateLong = activeDateLong;
		this.depth = depth;
		this.subUserList = subUserList;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public byte getUserType()
	{
		return userType;
	}

	public void setUserType(byte userType)
	{
		this.userType = userType;
	}

	public String getReferrerId()
	{
		return referrerId;
	}

	public void setReferrerId(String referrerId)
	{
		this.referrerId = referrerId;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public byte getActiveStatus()
	{
		return activeStatus;
	}

	public void setActiveStatus(byte activeStatus)
	{
		this.activeStatus = activeStatus;
	}

	public long getRegDateLong()
	{
		return regDateLong;
	}

	public void setRegDateLong(long regDateLong)
	{
		this.regDateLong = regDateLong;
	}

	public Long getActiveDateLong()
	{
		return activeDateLong;
	}

	public void setActiveDateLong(Long activeDateLong)
	{
		this.activeDateLong = activeDateLong;
	}

	public int getDepth()
	{
		return depth;
	}

	public void setDepth(int depth)
	{
		this.depth = depth;
	}

	public List<User> getSubUserList()
	{
		return subUserList;
	}

	public void setSubUserList(List<User> subUserList)
	{
		this.subUserList = subUserList;
	}
}
