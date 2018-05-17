package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台类型
 * 
 * @author libit
 */
public enum PlatformType
{
	ANDROID("android", ""), IOS("ios", ""), OTHER("other", "");
	private String type;
	private String desc;

	private PlatformType(String type, String desc)
	{
		this.type = type;
		this.desc = desc;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static String getTypeDesc(String type)
	{
		PlatformType[] list = PlatformType.values();
		for (PlatformType item : list)
		{
			if (item.getType().equals(type))
			{
				return item.getDesc();
			}
		}
		return "";
	}

	public static Map<String, String> getMap()
	{
		PlatformType[] list = PlatformType.values();
		Map<String, String> map = new HashMap<>();
		for (PlatformType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
