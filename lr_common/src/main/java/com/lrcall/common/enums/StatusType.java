package com.lrcall.common.enums;

/**
 * 状态类型
 * 
 * @author libit
 */
public enum StatusType
{
	ENABLED((byte) 0), DISABLED((byte) 1), INVALIDE((byte) 2), UNKNOWN((byte) 3);
	private byte status;

	StatusType(byte status)
	{
		this.setStatus(status);
	}

	public byte getStatus()
	{
		return status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	public static String getDesc(byte status)
	{
		StatusType[] values = StatusType.values();
		for (StatusType statusType : values)
		{
			if (statusType.status == status)
			{
				return "";
			}
		}
		return "未知";
	}
}
