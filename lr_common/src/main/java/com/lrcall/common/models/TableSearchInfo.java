package com.lrcall.common.models;

public class TableSearchInfo
{
	private String searchColumnName;
	private String searchValue;
	private boolean bFullMatch;// 是否是全值匹配
	private boolean bOr;// 是否与其他条件OR操作，false则与其他条件AND操作

	public TableSearchInfo()
	{
		super();
	}

	public TableSearchInfo(String searchColumnName, String searchValue, boolean bFullMatch, boolean bOr)
	{
		super();
		this.searchColumnName = searchColumnName;
		this.searchValue = searchValue;
		this.bFullMatch = bFullMatch;
		this.bOr = bOr;
	}

	public String getSearchColumnName()
	{
		return searchColumnName;
	}

	public void setSearchColumnName(String searchColumnName)
	{
		this.searchColumnName = searchColumnName;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public boolean isbFullMatch()
	{
		return bFullMatch;
	}

	public void setbFullMatch(boolean bFullMatch)
	{
		this.bFullMatch = bFullMatch;
	}

	public boolean isbOr()
	{
		return bOr;
	}

	public void setbOr(boolean bOr)
	{
		this.bOr = bOr;
	}
}
