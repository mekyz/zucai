package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

public enum ClientType
{
	BANNER("BANNER", "展示图"), BUTTON("BUTTON", "功能按钮图");
	private String type;
	private String desc;

	ClientType(String type, String desc)
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
		ClientType[] list = ClientType.values();
		for (ClientType item : list)
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
		ClientType[] list = ClientType.values();
		Map<String, String> map = new HashMap<>();
		for (ClientType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
