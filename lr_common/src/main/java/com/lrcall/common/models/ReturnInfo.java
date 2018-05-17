package com.lrcall.common.models;

import com.lrcall.common.utils.GsonTools;

/**
 * Gson返回数据格式
 * 
 * @author libit
 */
public class ReturnInfo
{
	protected int code;// 代码
	protected String msg;// 信息

	public ReturnInfo()
	{
		super();
	}

	public ReturnInfo(int code, String msg)
	{
		super();
		this.code = code;
		this.msg = msg;
	}

	public static boolean isSuccess(ReturnInfo returnInfo)
	{
		if (returnInfo != null && returnInfo.getCode() == ErrorInfo.SUCCESS)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	@Override
	public String toString()
	{
		return GsonTools.toJson(this);
	}
}
