package com.bet.services;

import com.bet.orms.UserExchangeInfo;
import com.lrcall.common.models.ReturnInfo;

public interface CExchangeService
{
	/**
	 * 会员转账
	 * 
	 * @param userExchangeInfo
	 * @return
	 */
	public ReturnInfo addUserExchangeInfo(UserExchangeInfo userExchangeInfo);
}
