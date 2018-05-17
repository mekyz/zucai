package com.bet.models;

import com.google.gson.annotations.SerializedName;

public class ParentUser
{
	@SerializedName("depth")
	private int depth;
	@SerializedName("userId")
	private String userId;

	public ParentUser()
	{
		super();
	}

	public ParentUser(int depth, String userId)
	{
		super();
		this.depth = depth;
		this.userId = userId;
	}

	public int getDepth()
	{
		return depth;
	}

	public void setDepth(int depth)
	{
		this.depth = depth;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
