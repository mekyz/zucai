package com.bet.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MobileSelectDataInfo
{
	@SerializedName("id")
	private String id;
	@SerializedName("name")
	private String name;
	@SerializedName("child")
	private List<MobileSelectDataInfo> child;

	public MobileSelectDataInfo()
	{
		super();
	}

	public MobileSelectDataInfo(String id, String name, List<MobileSelectDataInfo> child)
	{
		super();
		this.id = id;
		this.name = name;
		this.child = child;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
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

	public List<MobileSelectDataInfo> getChild()
	{
		return child;
	}

	public void setChild(List<MobileSelectDataInfo> child)
	{
		this.child = child;
	}
}
