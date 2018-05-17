package com.bet.daos;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserRechargeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserRechargeInfoDao extends IAbstractDao<UserRechargeInfo>
{
	public static final String ID = "id";
	public static final String RECHARGE_ID = "rechargeId";
	public static final String USER_ID = "userId";
	public static final String MONEY = "money";
	public static final String MONEY_UNIT = "moneyUnit";
	public static final String USER_MONEY = "userMoney";
	public static final String FEE = "fee";
	public static final String PAY_NAME = "payName";
	public static final String PAY_BANK_NAME = "payBankName";
	public static final String PAY_BANK_CARD_ID = "payBankCardId";
	public static final String PAYEE_NAME = "payeeName";
	public static final String PAYEE_BANK_NAME = "payeeBankName";
	public static final String PAYEE_BANK_CARD_ID = "payeeBankCardId";
	public static final String PAY_PIC_URL = "payPicUrl";
	public static final String PAY_REMARK = "payRemark";
	public static final String STATUS = "status";
	public static final String VERIFY_DATE_LONG = "verifyDateLong";
	public static final String PAY_DATE_LONG = "payDateLong";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException;

	public UserRechargeInfo getUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException;

	public long updateValueByRechargeId(String rechargeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */

	public long getSum(String colName, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
