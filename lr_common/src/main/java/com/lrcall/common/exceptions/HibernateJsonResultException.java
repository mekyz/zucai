package com.lrcall.common.exceptions;

import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.LogTools;

/**
 * 输出JSON结果的错误类
 * 
 * @author libit
 */
public class HibernateJsonResultException extends RuntimeException
{
	private static final long serialVersionUID = -5864385394978019074L;
	public static final LogTools log = LogTools.getInstance();

	// 传入ReturnInfo对象，这样getMessage()方法返回的是ReturnInfo的JSON字符串
	public HibernateJsonResultException(ReturnInfo returnInfo)
	{
		super(returnInfo.toString());
	}

	// 传入构造ReturnInfo的参数
	public HibernateJsonResultException(int code, String msg)
	{
		super(new ReturnInfo(code, msg).toString());
		log.debug("HibernateJsonResultException", new ReturnInfo(code, msg).toString());
	}
}
