package com.bet.enums;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

/**
 * 验证码类型
 * 
 * @author libit
 */
public enum SmsCodeType
{
	REGISTER(1, "注册验证码"), RESET_PWD(2, "重置密码验证码"), USER_AUTH(3, "实名认证验证码"), NUMBER_AUTH(4, "手机号码验证码");
	private int type;
	private String desc;

	private SmsCodeType(int type, String desc)
	{
		this.type = type;
		this.desc = desc;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
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

	public static String getCodeType(int type)
	{
		SmsCodeType[] list = SmsCodeType.values();
		for (SmsCodeType item : list)
		{
			if (item.getType() == type)
			{
				return item.getDesc();
			}
		}
		return "";
	}

	public static Map<Integer, String> getMap()
	{
		SmsCodeType[] list = SmsCodeType.values();
		Map<Integer, String> map = new HashedMap<>();
		for (SmsCodeType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
