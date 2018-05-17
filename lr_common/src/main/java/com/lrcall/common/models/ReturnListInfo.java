package com.lrcall.common.models;

/**
 * 列表返回的信息
 * 
 * @author libit
 */
public class ReturnListInfo extends ReturnInfo
{
	protected long count;
	protected long totalCount;

	public ReturnListInfo()
	{
		super();
	}

	public ReturnListInfo(Integer code, String msg)
	{
		super(code, msg);
	}

	public ReturnListInfo(ReturnInfo returnInfo)
	{
		super(returnInfo.getCode(), returnInfo.getMsg());
		this.count = 0;
		this.totalCount = 0;
	}

	public ReturnListInfo(ReturnInfo returnInfo, long count, long totalCount)
	{
		super(returnInfo.getCode(), returnInfo.getMsg());
		this.count = count;
		this.totalCount = totalCount;
	}

	public ReturnListInfo(Integer code, String msg, long count, long totalCount)
	{
		super(code, msg);
		this.count = count;
		this.totalCount = totalCount;
	}

	public long getCount()
	{
		return count;
	}

	public void setCount(long count)
	{
		this.count = count;
	}

	public long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(long totalCount)
	{
		this.totalCount = totalCount;
	}
}
