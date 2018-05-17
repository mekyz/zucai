package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 波胆类型
 * 
 * @author libit
 */
public enum BodanType
{
	FULL("FULL", "全场"), HALF("HALF", "半场"), SCORE("SCORE", "进球数");
	private String type;
	private String desc;

	BodanType(String type, String desc)
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
		BodanType[] list = BodanType.values();
		for (BodanType item : list)
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
		BodanType[] list = BodanType.values();
		Map<String, String> map = new HashMap<>();
		for (BodanType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
