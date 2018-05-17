package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 需付款的类型
 */
public enum PayType
{
	PAY_BALANCE("pay_balance", "余额充值"), PAY_UPGRADE("pay_upgrade", "用户升级");
	private String type;
	private String desc;

	PayType(String type, String desc)
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
		PayType[] list = PayType.values();
		for (PayType item : list)
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
		PayType[] list = PayType.values();
		Map<String, String> map = new HashMap<>();
		for (PayType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
