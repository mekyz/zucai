package com.bet.models;

import com.google.gson.annotations.SerializedName;

public class District
{
	@SerializedName("name")
	private String name;
	@SerializedName("code")
	private String code;

	public District(String name, String code)
	{
		super();
		this.name = name;
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
}
