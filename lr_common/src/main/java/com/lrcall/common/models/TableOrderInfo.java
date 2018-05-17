package com.lrcall.common.models;

public class TableOrderInfo
{
	private int index;
	private String orderColumnName;
	private String orderDir;

	public TableOrderInfo()
	{
		super();
	}

	public TableOrderInfo(String orderColumnName, String orderDir)
	{
		super();
		this.index = 0;
		this.orderColumnName = orderColumnName;
		this.orderDir = orderDir;
	}

	public TableOrderInfo(int index, String orderColumnName, String orderDir)
	{
		super();
		this.index = index;
		this.orderColumnName = orderColumnName;
		this.orderDir = orderDir;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public String getOrderColumnName()
	{
		return orderColumnName;
	}

	public void setOrderColumnName(String orderColumnName)
	{
		this.orderColumnName = orderColumnName;
	}

	public String getOrderDir()
	{
		return orderDir;
	}

	public void setOrderDir(String orderDir)
	{
		this.orderDir = orderDir;
	}
}
