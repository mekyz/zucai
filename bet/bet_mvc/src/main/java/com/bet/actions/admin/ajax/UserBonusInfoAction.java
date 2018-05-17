package com.bet.actions.admin.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IUserBonusInfoDao;
import com.bet.orms.UserBonusInfo;
import com.bet.services.IUserBonusInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserBonusInfoAction")
@RequestMapping(value = "/admin")
public class UserBonusInfoAction extends BaseAdminController
{
	@Autowired
	private IUserBonusInfoService userBonusInfoService;

	@RequestMapping(value = "/ajaxUpdateUserBonusInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserBonusInfo(HttpServletRequest request, @ModelAttribute("userBonusInfo") UserBonusInfo userBonusInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserBonusInfoDao.USER_ID, userBonusInfo.getUserId());
			valueMap.put(IUserBonusInfoDao.PHASE_ID, userBonusInfo.getPhaseId());
			valueMap.put(IUserBonusInfoDao.FINAL_BONUS_MONEY, userBonusInfo.getFinalBonusMoney());
			valueMap.put(IUserBonusInfoDao.HALF_BONUS_MONEY, userBonusInfo.getHalfBonusMoney());
			valueMap.put(IUserBonusInfoDao.COUNT_BONUS_MONEY, userBonusInfo.getCountBonusMoney());
			valueMap.put(IUserBonusInfoDao.SHARE_BONUS_MONEY, userBonusInfo.getShareBonusMoney());
			valueMap.put(IUserBonusInfoDao.AGENT_BONUS_MONEY, userBonusInfo.getAgentBonusMoney());
			valueMap.put(IUserBonusInfoDao.SAME_LEVEL1_BONUS_MONEY, userBonusInfo.getSameLevel1BonusMoney());
			valueMap.put(IUserBonusInfoDao.SAME_LEVEL2_BONUS_MONEY, userBonusInfo.getSameLevel2BonusMoney());
			valueMap.put(IUserBonusInfoDao.BENEFIT_BONUS_MONEY, userBonusInfo.getBenefitBonusMoney());
			valueMap.put(IUserBonusInfoDao.FINANCIAL_MONEY, userBonusInfo.getFinancialMoney());
			valueMap.put(IUserBonusInfoDao.FEE, userBonusInfo.getFee());
			valueMap.put(IUserBonusInfoDao.USER_MONEY, userBonusInfo.getUserMoney());
			valueMap.put(IUserBonusInfoDao.STATUS, userBonusInfo.getStatus());
			valueMap.put(IUserBonusInfoDao.UPDATE_DATE_LONG, current);
			if (!userBonusInfoService.updateValueByBonusId(userBonusInfo.getBonusId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户奖金信息失败！");
			}
			return toObjectReturnInfo(userBonusInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	// @RequestMapping(value = "/ajaxDeleteUserBonusInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxDeleteUserBonusInfo(HttpServletRequest request, @RequestParam(value = "bonusId", required = true) String bonusId)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// userBonusInfoService.deleteUserBonusInfoByBonusId(bonusId);
	// return toStringReturnInfo("删除用户奖金信息成功！");
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxGetUserBonusInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBonusInfo(HttpServletRequest request, @RequestParam(value = "bonusId", required = true) String bonusId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserBonusInfo userBonusInfo = userBonusInfoService.getUserBonusInfoByBonusId(bonusId);
			if (userBonusInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户奖金信息不存在！");
			}
			return toObjectReturnInfo(userBonusInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBonusInfoList")
	@ResponseBody
	public TableData ajaxGetUserBonusInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserBonusInfoDao.USER_ID, IUserBonusInfoDao.PHASE_ID, IUserBonusInfoDao.STATUS });
			List<UserBonusInfo> list = userBonusInfoService.getUserBonusInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userBonusInfoService.getUserBonusInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, list);
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
