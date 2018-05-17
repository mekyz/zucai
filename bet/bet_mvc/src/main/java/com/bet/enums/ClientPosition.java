package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

public enum ClientPosition
{
	PAGE_START("START", "启动页"), PAGE_INDEX("INDEX", "首页"), PAGE_AGENT_INDEX("AGENT_INDEX", "代理商首页");
	private String position;
	private String desc;

	ClientPosition(String position, String desc)
	{
		this.position = position;
		this.desc = desc;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static String getTypeDesc(String position)
	{
		ClientPosition[] list = ClientPosition.values();
		for (ClientPosition item : list)
		{
			if (item.getPosition().equals(position))
			{
				return item.getDesc();
			}
		}
		return "";
	}

	public static Map<String, String> getMap()
	{
		ClientPosition[] list = ClientPosition.values();
		Map<String, String> map = new HashMap<>();
		for (ClientPosition item : list)
		{
			map.put(item.getPosition(), item.getDesc());
		}
		return map;
	}
}
