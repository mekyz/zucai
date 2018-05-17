package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 货币单位
 * 
 * @author libit
 */
public enum MoneyUnit
{
	POINT((byte) 11, "彩金钱包"), /* REWARD_POINT((byte) 12, "奖励余额"), */ GIVE_POINT((byte) 13, "体验彩金");
	private byte type;
	private String desc;

	MoneyUnit(byte type, String desc)
	{
		this.type = type;
		this.desc = desc;
	}

	public byte getType()
	{
		return type;
	}

	public void setType(byte type)
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

	public static String getTypeDesc(byte type)
	{
		MoneyUnit[] list = MoneyUnit.values();
		for (MoneyUnit item : list)
		{
			if (item.getType() == type)
			{
				return item.getDesc();
			}
		}
		return "";
	}

	public static Map<Byte, String> getMap()
	{
		MoneyUnit[] list = MoneyUnit.values();
		Map<Byte, String> modelMap = new HashMap<>();
		for (MoneyUnit item : list)
		{
			modelMap.put(item.getType(), item.getDesc());
		}
		return modelMap;
	}
}
