package com.bet.models;

import com.google.gson.annotations.SerializedName;

public class CoordinateInfo
{
	@SerializedName("latitude")
	private String latitude;
	@SerializedName("longitude")
	private String longitude;

	public CoordinateInfo()
	{
		super();
	}

	public CoordinateInfo(String latitude, String longitude)
	{
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
}
