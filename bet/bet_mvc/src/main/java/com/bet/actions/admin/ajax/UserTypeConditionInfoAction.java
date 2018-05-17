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
import com.bet.daos.IUserTypeConditionInfoDao;
import com.bet.orms.UserTypeConditionInfo;
import com.bet.services.IUserTypeConditionInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserTypeConditionInfoAction")
@RequestMapping(value = "/admin")
public class UserTypeConditionInfoAction extends BaseAdminController
{
	@Autowired
	private IUserTypeConditionInfoService userTypeConditionInfoService;

	// @RequestMapping(value = "/ajaxAddUserTypeConditionInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxAddUserTypeConditionInfo(HttpServletRequest request, @ModelAttribute("userTypeConditionInfo") UserTypeConditionInfo userTypeConditionInfo)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// userTypeConditionInfo.setStatus(StatusType.ENABLED.getStatus());
	// userTypeConditionInfo = userTypeConditionInfoService.addUserTypeConditionInfo(userTypeConditionInfo);
	// if (userTypeConditionInfo == null)
	// {
	// throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加用户类型满足的条件信息失败！");
	// }
	// return toObjectReturnInfo(userTypeConditionInfo);
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxUpdateUserTypeConditionInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserTypeConditionInfo(HttpServletRequest request, @ModelAttribute("userTypeConditionInfo") UserTypeConditionInfo userTypeConditionInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserTypeConditionInfoDao.DIRECT_COUNT, userTypeConditionInfo.getDirectCount());
			valueMap.put(IUserTypeConditionInfoDao.TEAM_COUNT, userTypeConditionInfo.getTeamCount());
			valueMap.put(IUserTypeConditionInfoDao.MONEY, userTypeConditionInfo.getMoney());
			valueMap.put(IUserTypeConditionInfoDao.UPDATE_DATE_LONG, current);
			if (!userTypeConditionInfoService.updateValueByUserType(userTypeConditionInfo.getUserType(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户类型满足的条件信息失败！");
			}
			return toObjectReturnInfo(userTypeConditionInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateUserTypeConditionInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserTypeConditionInfoStatus(HttpServletRequest request, @RequestParam(value = "userType", required = true) byte userType,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserTypeConditionInfoDao.STATUS, status);
			valueMap.put(IUserTypeConditionInfoDao.UPDATE_DATE_LONG, current);
			if (!userTypeConditionInfoService.updateValueByUserType(userType, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户类型满足的条件信息状态失败！");
			}
			return toStringReturnInfo("更新用户类型满足的条件信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	// @RequestMapping(value = "/ajaxDeleteUserTypeConditionInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxDeleteUserTypeConditionInfo(HttpServletRequest request, @RequestParam(value = "userType", required = true) byte userType)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// userTypeConditionInfoService.deleteUserTypeConditionInfoByUserType(userType);
	// return toStringReturnInfo("删除用户类型满足的条件信息成功！");
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxGetUserTypeConditionInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserTypeConditionInfo(HttpServletRequest request, @RequestParam(value = "userType", required = true) byte userType)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserTypeConditionInfo userTypeConditionInfo = userTypeConditionInfoService.getUserTypeConditionInfoByUserType(userType);
			if (userTypeConditionInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户类型满足的条件信息不存在！");
			}
			return toObjectReturnInfo(userTypeConditionInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserTypeConditionInfoList")
	@ResponseBody
	public TableData ajaxGetUserTypeConditionInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserTypeConditionInfoDao.USER_TYPE, IUserTypeConditionInfoDao.STATUS });
			List<UserTypeConditionInfo> list = userTypeConditionInfoService.getUserTypeConditionInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos);
			long count = userTypeConditionInfoService.getUserTypeConditionInfoListCount(searchInfos);
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
