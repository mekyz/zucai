package com.bet.actions.user.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.user.BaseUserController;
import com.bet.daos.IUserBetInfoDao;
import com.bet.enums.MoneyUnit;
import com.bet.enums.UserType;
import com.bet.orms.UserBetInfo;
import com.bet.orms.UserInfo;
import com.bet.services.CBetService;
import com.bet.services.CBonusService;
import com.bet.services.IUserBetInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userUserBetInfoAction")
@RequestMapping(value = "/user")
public class UserBetInfoAction extends BaseUserController
{
	@Autowired
	private IUserBetInfoService userBetInfoService;
	@Autowired
	private CBetService cBetService;
	@Autowired
	private CBonusService cBonusService;
	@Autowired
	private IUserInfoService userInfoService;

	@RequestMapping(value = "/ajaxAddUserBetInfo")
	@ResponseBody
	public ReturnInfo ajaxAddUserBetInfo(HttpServletRequest request, @ModelAttribute("userBetInfo") UserBetInfo userBetInfo)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			userBetInfo.setUserId(sessionUserInfo.getUserId());
			userBetInfo.setTeamProfitStatus(StatusType.DISABLED.getStatus());
			userBetInfo.setSysStatus(sessionUserInfo.getSysStatus());
			userBetInfo.setStatus(StatusType.ENABLED.getStatus());
			return cBetService.addUserBetInfo(userBetInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBetInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBetInfo(HttpServletRequest request, @RequestParam(value = "betId", required = true) String betId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
			if (userBetInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "投注信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userBetInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseUserBetInfo(userBetInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBetInfoList")
	@ResponseBody
	public TableData ajaxGetUserBetInfoList(HttpServletRequest request, @RequestParam(value = "startDateLong", required = false) Long startDateLong,
		@RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserBetInfoDao.BET_ID, IUserBetInfoDao.PROFIT_ID, IUserBetInfoDao.STATUS });
			List<UserBetInfo> list = userBetInfoService.getUserBetInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos, startDateLong,
				endDateLong);
			long count = userBetInfoService.getUserBetInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserBetInfoList(list));
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
	 * @param phaseId
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserBetTotalMoney")
	@ResponseBody
	public ReturnInfo ajaxGetUserBetTotalMoney(HttpServletRequest request, @RequestParam(value = "userId", required = false) String userId,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (StringTools.isNull(userId))
			{
				userId = sessionUserInfo.getUserId();
			}
			else
			{
				boolean canGet = false;
				UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
				while (userInfo != null)
				{
					if (sessionUserInfo.getUserId().equals(userInfo.getUserId()) || sessionUserInfo.getUserId().equals(userInfo.getReferrerId()))
					{
						canGet = true;
						break;
					}
					if (StringTools.isNull(userInfo.getReferrerId()))
					{
						break;
					}
					userInfo = userInfoService.getUserInfoByUserId(userInfo.getReferrerId());
				}
				if (!canGet)
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
				}
			}
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userId, true, false));
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MONEY_UNIT, MoneyUnit.POINT.getType() + "", true, false));
			long money = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startDateLong, endDateLong);
			return toObjectReturnInfo(money);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取投注盈利总金额接口<br>
	 * 
	 * @param request
	 * @param phaseId
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserBetWinMoney")
	@ResponseBody
	public ReturnInfo ajaxGetUserBetWinMoney(HttpServletRequest request)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			long money = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, null, null);
			return toObjectReturnInfo(money);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取团队投注金额接口<br>
	 * 
	 * @param request
	 * @param phaseId
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserTeamBetMoney")
	@ResponseBody
	public ReturnInfo ajaxGetUserTeamBetMoney(HttpServletRequest request, @RequestParam(value = "phaseId", required = false) String phaseId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			long money = 0;
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (!StringTools.isNull(phaseId))
			{
				if (sessionUserInfo.getUserType() < UserType.AGENT5.getType())
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您的级别不够！");
				}
				// searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
				money = cBonusService.getSubUserBetMoney(phaseId, sessionUserInfo.getUserId());
			}
			else
			{
				money = userBetInfoService.getTeamUserTotalMoney(sessionUserInfo.getUserId(), searchInfos);
			}
			return toObjectReturnInfo(money);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
