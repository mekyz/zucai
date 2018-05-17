package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 申请状态
 * 
 * @author libit
 */
public enum ApplyStatus
{
	APPLY((byte) 0, "已申请"), PAYED((byte) 5, "已支付"), VERIFY_SUCCESS((byte) 10, "审核成功"), VERIFY_FAIL((byte) 20, "审核失败"), PROCESSED((byte) 30, "已处理");
	private byte status;
	private String desc;

	private ApplyStatus(byte status, String desc)
	{
		this.status = status;
		this.desc = desc;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static String getStatusDesc(byte status)
	{
		ApplyStatus[] list = ApplyStatus.values();
		for (ApplyStatus item : list)
		{
			if (item.getStatus() == status)
			{
				return item.getDesc();
			}
		}
		return "";
	}

	public static Map<String, Byte> getMap()
	{
		ApplyStatus[] list = ApplyStatus.values();
		Map<String, Byte> map = new HashMap<>();
		for (ApplyStatus item : list)
		{
			map.put(item.name(), item.getStatus());
		}
		// map.put("APPLY", ApplyStatus.APPLY.getStatus());
		// map.put("SUBMIT_WX", ApplyStatus.SUBMIT_WX.getStatus());
		// map.put("VERIFY_SUCCESS", ApplyStatus.VERIFY_SUCCESS.getStatus());
		// map.put("VERIFY_FAIL", ApplyStatus.VERIFY_FAIL.getStatus());
		// map.put("PROCESSED", ApplyStatus.PROCESSED.getStatus());
		return map;
	}
}
