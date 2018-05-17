package com.bet.actions.admin.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IUserBetInfoDao;
import com.bet.orms.UserBetInfo;
import com.bet.services.CBonusService;
import com.bet.services.CStatService;
import com.bet.services.IUserBetInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserBetInfoAction")
@RequestMapping(value = "/admin")
public class UserBetInfoAction extends BaseAdminController
{
	@Autowired
	private IUserBetInfoService userBetInfoService;
	// @Autowired
	// private IUserInfoService userInfoService;
	@Autowired
	private CStatService cStatService;
	@Autowired
	private CBonusService cBonusService;

	@RequestMapping(value = "/ajaxGetUserBetInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBetInfo(HttpServletRequest request, @RequestParam(value = "betId", required = true) String betId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
			if (userBetInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "投注信息不存在！");
			}
			return toObjectReturnInfo(userBetInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBetInfoList")
	@ResponseBody
	public TableData ajaxGetUserBetInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IUserBetInfoDao.BET_ID, IUserBetInfoDao.USER_ID, IUserBetInfoDao.PROFIT_ID, IUserBetInfoDao.PHASE_ID, IUserBetInfoDao.STATUS });
			List<UserBetInfo> list = userBetInfoService.getUserBetInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userBetInfoService.getUserBetInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, list);
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取投注总金额接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserBetTotalMoney")
	@ResponseBody
	public long ajaxGetUserBetTotalMoney(HttpServletRequest request, @RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "phaseId", required = false) String phaseId,
		@RequestParam(value = "moneyUnit", required = false) Byte moneyUnit, @RequestParam(value = "startDateLong", required = false) Long startDateLong,
		@RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (!StringTools.isNull(userId))
			{
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userId, true, false));
			}
			if (!StringTools.isNull(phaseId))
			{
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
			}
			if (moneyUnit != null)
			{
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MONEY_UNIT, moneyUnit + "", true, false));
			}
			long money = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startDateLong, endDateLong);
			return money;
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
		}
		return 0;
	}

	/**
	 * 计算比赛的中奖情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateCheckUserBetBonusJob")
	@ResponseBody
	public ReturnInfo ajaxUpdateCheckUserBetBonusJob(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateCheckUserBetBonusJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 计算比赛的中奖情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateCheckUserBetBonusByPhaseId")
	@ResponseBody
	public ReturnInfo ajaxUpdateCheckUserBetBonusByPhaseId(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			LogTools.getInstance().info("ajaxUpdateCheckUserBetBonusByPhaseId", "开始计算用户下注结果");
			int start = 0;
			int size = 100;
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
			// searchInfos.add(new TableSearchInfo(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
			List<UserBetInfo> userBetInfoList = null;
			int successCount = 0, failCount = 0;
			while (true)
			{
				userBetInfoList = userBetInfoService.getUserBetInfoList(start, size, orderInfos, searchInfos);
				if (userBetInfoList == null || userBetInfoList.size() < 1)
				{
					break;
				}
				for (UserBetInfo userBetInfo : userBetInfoList)
				{
					try
					{
						ReturnInfo returnInfo = cBonusService.checkAndUpdateUserBetBonusJob(userBetInfo.getBetId());
						LogTools.getInstance().info("ajaxUpdateCheckUserBetBonusByPhaseId", "计算用户下注:" + GsonTools.toJson(userBetInfo) + "的奖金结果：" + GsonTools.toJson(returnInfo));
						if (ReturnInfo.isSuccess(returnInfo))
						{
							successCount++;
						}
						else
						{
							failCount++;
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						failCount++;
					}
				}
				if (userBetInfoList.size() < size)// 取完了结束循环
				{
					break;
				}
				else
				{
					start += size;
				}
			}
			return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
