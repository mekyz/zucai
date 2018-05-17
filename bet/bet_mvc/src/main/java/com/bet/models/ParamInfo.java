package com.bet.models;

public class ParamInfo
{
	private String name;
	private String value;

	public ParamInfo()
	{
		super();
	}

	public ParamInfo(String name, String value)
	{
		super();
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
