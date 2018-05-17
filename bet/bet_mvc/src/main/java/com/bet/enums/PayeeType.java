package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 收款类型
 * 
 * @author libit
 */
public enum PayeeType
{
	PAY_ONLINE("PAY_ONLINE", "在线支付"), UNIONPAY("UNIONPAY", "银联转账"), ALIPAY("ALIPAY", "支付宝");
	private String type;
	private String desc;

	PayeeType(String type, String desc)
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
		PayeeType[] list = PayeeType.values();
		for (PayeeType item : list)
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
		PayeeType[] list = PayeeType.values();
		Map<String, String> map = new HashMap<>();
		for (PayeeType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
