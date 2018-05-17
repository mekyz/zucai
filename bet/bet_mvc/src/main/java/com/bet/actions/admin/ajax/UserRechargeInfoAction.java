package com.bet.actions.admin.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IUserRechargeInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.orms.UserRechargeInfo;
import com.bet.services.CRechargeService;
import com.bet.services.IUserRechargeInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserRechargeInfoAction")
@RequestMapping(value = "/admin")
public class UserRechargeInfoAction extends BaseAdminController
{
	@Autowired
	private IUserRechargeInfoService userRechargeInfoService;
	@Autowired
	private CRechargeService cRechargeService;

	@RequestMapping(value = "/ajaxUpdateUserRechargeInfoVerify", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserRechargeInfoVerify(HttpServletRequest request, @RequestParam(value = "rechargeId", required = true) String rechargeId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cRechargeService.updateUserRechargeInfoVerify(rechargeId, status);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteUserRechargeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteUserRechargeInfo(HttpServletRequest request, @RequestParam(value = "rechargeId", required = true) String rechargeId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			userRechargeInfoService.deleteUserRechargeInfoByRechargeId(rechargeId);
			return toStringReturnInfo("删除用户充值信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserRechargeInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserRechargeInfo(HttpServletRequest request, @RequestParam(value = "rechargeId", required = true) String rechargeId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(rechargeId);
			if (userRechargeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户充值信息不存在！");
			}
			return toObjectReturnInfo(userRechargeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserRechargeInfoList")
	@ResponseBody
	public TableData ajaxGetUserRechargeInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserRechargeInfoDao.USER_ID, IUserRechargeInfoDao.STATUS });
			List<UserRechargeInfo> list = userRechargeInfoService.getUserRechargeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userRechargeInfoService.getUserRechargeInfoListCount(searchInfos);
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
	 * 获取充值总金额接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserRechargeTotalMoney")
	@ResponseBody
	public long ajaxGetUserRechargeTotalMoney(HttpServletRequest request, @RequestParam(value = "userId", required = false) String userId,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserRechargeInfoDao.STATUS, ApplyStatus.VERIFY_SUCCESS.getStatus() + "", true, false));
			if (!StringTools.isNull(userId))
			{
				searchInfos.add(new TableSearchInfo(IUserRechargeInfoDao.USER_ID, userId, true, false));
			}
			long money = userRechargeInfoService.getSum(IUserRechargeInfoDao.MONEY, searchInfos, startDateLong, endDateLong);
			return money;
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
		}
		return 0;
	}
}
