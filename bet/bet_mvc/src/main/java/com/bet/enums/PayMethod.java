package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 付款方式
 */
public enum PayMethod
{
	PAY_POINT("point", "积分支付"), PAY_WECHART("wechart", "微信公众号支付"), PAY_WECHART_H5("wechart_h5", "微信H5支付"), PAY_WECHART_SHAOMA("wechart_shaoma", "微信扫码支付"), PAY_WECHAT_MP("wechat_mp", "微信小程序支付");
	private String type;
	private String desc;

	PayMethod(String type, String desc)
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
		PayMethod[] list = PayMethod.values();
		for (PayMethod item : list)
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
		PayMethod[] list = PayMethod.values();
		Map<String, String> map = new HashMap<>();
		for (PayMethod item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
