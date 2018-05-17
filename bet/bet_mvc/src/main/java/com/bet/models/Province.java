package com.bet.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Province
{
	@SerializedName("name")
	private String name;
	@SerializedName("code")
	private String code;
	@SerializedName("cityList")
	private List<City> cityList;

	public Province(String name, String code)
	{
		super();
		this.name = name;
		this.code = code;
		cityList = new ArrayList<>();
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

	public List<City> getCityList()
	{
		return cityList;
	}

	public void setCityList(List<City> cityList)
	{
		this.cityList = cityList;
	}
}
