package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型
 * 
 * @author libit
 */
public enum UserType
{
	COMMON((byte) 0, "普通用户"), MEMBER((byte) 5, "会员"), AGENT1((byte) 10, "一星代理"), AGENT2((byte) 20, "二星代理"), AGENT3((byte) 30, "三星代理"), AGENT4((byte) 40, "四星代理"), AGENT5((byte) 50,
		"五星代理"), AGENT6((byte) 60, "白金代理"), AGENT7((byte) 70, "红钻代理");
	private byte type;
	private String desc;

	UserType(byte type, String desc)
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

	public static String getDesc(byte type)
	{
		UserType[] list = UserType.values();
		for (UserType item : list)
		{
			if (item.getType() == type)
			{
				return item.getDesc();
			}
		}
		return "未知类型";
	}

	public static Map<Byte, String> getMap()
	{
		UserType[] list = UserType.values();
		Map<Byte, String> map = new HashMap<>();
		for (UserType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
