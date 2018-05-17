package com.bet.actions.admin.ajax;

import java.util.ArrayList;
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
import com.bet.daos.IUserBalanceLogInfoDao;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserBalanceLogInfoAction")
@RequestMapping(value = "/admin")
public class UserBalanceLogInfoAction extends BaseAdminController
{
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;
	@Autowired
	private IUserInfoService userInfoService;

	@RequestMapping(value = "/ajaxAddUserBalanceLogInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddUserBalanceLogInfo(HttpServletRequest request, @ModelAttribute("userBalanceLogInfo") UserBalanceLogInfo userBalanceLogInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			userBalanceLogInfo = userBalanceLogInfoService.addUserBalanceLogInfo(userBalanceLogInfo);
			if (userBalanceLogInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加用户余额记录信息失败！");
			}
			return toObjectReturnInfo(userBalanceLogInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteUserBalanceLogInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteUserBalanceLogInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			userBalanceLogInfoService.deleteUserBalanceLogInfoById(id);
			return toStringReturnInfo("删除用户余额记录信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBalanceLogInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBalanceLogInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserBalanceLogInfo userBalanceLogInfo = userBalanceLogInfoService.getUserBalanceLogInfoById(id);
			if (userBalanceLogInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户余额记录信息不存在！");
			}
			return toObjectReturnInfo(userBalanceLogInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBalanceLogInfoList")
	@ResponseBody
	public TableData ajaxGetUserBalanceLogInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserBalanceLogInfoDao.USER_ID, IUserBalanceLogInfoDao.LOG_TYPE, IUserBalanceLogInfoDao.SERVER_ID });
			List<UserBalanceLogInfo> list = userBalanceLogInfoService.getUserBalanceLogInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userBalanceLogInfoService.getUserBalanceLogInfoListCount(searchInfos);
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
	 * 用户收益金额
	 * 
	 * @param request
	 * @param userId
	 * @param moneyUnit
	 * @param logType
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserBalanceLogMoney")
	@ResponseBody
	public long ajaxGetUserBalanceLogMoney(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId,
		@RequestParam(value = "moneyUnit", required = true) byte moneyUnit, @RequestParam(value = "logType", required = false) String logType,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			searchInfos.add(new TableSearchInfo(IUserBalanceLogInfoDao.USER_ID, userId, true, false));
			if (!StringTools.isNull(logType))
			{
				searchInfos.add(new TableSearchInfo(IUserBalanceLogInfoDao.LOG_TYPE, logType, true, false));
			}
			return userBalanceLogInfoService.getUserLogTotalMoney(moneyUnit, searchInfos, startDateLong, endDateLong);
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
			return 0;
		}
	}
}
