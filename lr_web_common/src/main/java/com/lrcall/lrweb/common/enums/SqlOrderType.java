package com.lrcall.lrweb.common.enums;

/**
 * 平台类型
 * 
 * @author libit
 */
public enum SqlOrderType
{
	ASC("asc"), DESC("desc");
	private String type;

	SqlOrderType(String type)
	{
		this.setType(type);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
