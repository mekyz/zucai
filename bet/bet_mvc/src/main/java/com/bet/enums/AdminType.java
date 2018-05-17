package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员类型
 * 
 * @author libit
 */
public enum AdminType
{
	ADMIN((byte) 0, "管理员"), CUSTOMER((byte) 20, "客服");
	private byte type;
	private String desc;

	AdminType(byte type, String desc)
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
		AdminType[] list = AdminType.values();
		for (AdminType item : list)
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
		AdminType[] list = AdminType.values();
		Map<Byte, String> map = new HashMap<>();
		for (AdminType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
