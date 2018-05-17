package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付平台
 * 
 * @author libit
 */
public enum PayPlatform
{
	MINLE_PAY("minlepay", "民乐支付");
	private String type;
	private String desc;

	PayPlatform(String type, String desc)
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
		PayPlatform[] list = PayPlatform.values();
		for (PayPlatform item : list)
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
		PayPlatform[] list = PayPlatform.values();
		Map<String, String> map = new HashMap<>();
		for (PayPlatform item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
