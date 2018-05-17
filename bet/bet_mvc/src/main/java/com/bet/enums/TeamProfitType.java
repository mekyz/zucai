package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 团队奖类型
 * 
 * @author libit
 */
public enum TeamProfitType
{
	SHARE((byte) 1, "分享佣金"), AGENT((byte) 2, "代理佣金"), SAME_LEVEL1((byte) 3, "代理佣金平级奖"), BENEFIT((byte) 4, "福利奖"), SAME_LEVEL2((byte) 5, "福利平级奖");
	private byte type;
	private String desc;

	private TeamProfitType(byte type, String desc)
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
		TeamProfitType[] list = TeamProfitType.values();
		for (TeamProfitType item : list)
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
		TeamProfitType[] list = TeamProfitType.values();
		Map<Byte, String> map = new HashMap<>();
		for (TeamProfitType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
