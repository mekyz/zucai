package com.bet.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class City
{
	@SerializedName("name")
	private String name;
	@SerializedName("code")
	private String code;
	@SerializedName("districtList")
	private List<District> districtList;

	public City(String name, String code)
	{
		super();
		this.name = name;
		this.code = code;
		districtList = new ArrayList<>();
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

	public List<District> getDistrictList()
	{
		return districtList;
	}

	public void setDistrictList(List<District> districtList)
	{
		this.districtList = districtList;
	}
}
