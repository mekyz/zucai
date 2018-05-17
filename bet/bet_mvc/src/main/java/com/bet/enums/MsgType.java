package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类型
 * 
 * @author libit
 */
public enum MsgType
{
	WITHDRAW("WITHDRAW", "用户提现"), RECHARGE("RECHARGE", "用户充值"), BET_WIN("BET_WIN", "用户中奖");
	private String type;
	private String desc;

	MsgType(String type, String desc)
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
		MsgType[] list = MsgType.values();
		for (MsgType item : list)
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
		MsgType[] list = MsgType.values();
		Map<String, String> map = new HashMap<>();
		for (MsgType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
