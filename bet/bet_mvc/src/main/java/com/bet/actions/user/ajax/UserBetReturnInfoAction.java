package com.bet.actions.user.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.user.BaseUserController;
import com.bet.daos.IUserBetReturnInfoDao;
import com.bet.orms.UserBetInfo;
import com.bet.orms.UserBetReturnInfo;
import com.bet.orms.UserInfo;
import com.bet.services.CBetService;
import com.bet.services.IUserBetInfoService;
import com.bet.services.IUserBetReturnInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userUserBetReturnInfoAction")
@RequestMapping(value = "/user")
public class UserBetReturnInfoAction extends BaseUserController
{
	@Autowired
	private IUserBetReturnInfoService userBetReturnInfoService;
	@Autowired
	private IUserBetInfoService userBetInfoService;
	@Autowired
	private CBetService cBetService;

	/**
	 * 下注撤回接口
	 * 
	 * @param request
	 * @param userBetReturnInfo
	 * @return
	 */
	@RequestMapping(value = "/ajaxUserBetReturn")
	@ResponseBody
	public ReturnInfo ajaxUserBetReturn(HttpServletRequest request, @RequestParam(value = "betId", required = true) String betId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
			if (userBetInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户下注信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userBetInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return cBetService.deleteUserBetInfo(betId, "用户撤回。");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBetReturnInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBetReturnInfo(HttpServletRequest request, @RequestParam(value = "logId", required = true) String logId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserBetReturnInfo userBetReturnInfo = userBetReturnInfoService.getUserBetReturnInfoByLogId(logId);
			if (userBetReturnInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户撤回下注信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userBetReturnInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseUserBetReturnInfo(userBetReturnInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBetReturnInfoList")
	@ResponseBody
	public TableData ajaxGetUserBetReturnInfoList(HttpServletRequest request, @RequestParam(value = "startDateLong", required = false) Long startDateLong,
		@RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserBetReturnInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IUserBetReturnInfoDao.BET_ID, IUserBetReturnInfoDao.USER_ID, IUserBetReturnInfoDao.PROFIT_ID, IUserBetReturnInfoDao.PHASE_ID, IUserBetReturnInfoDao.STATUS });
			List<UserBetReturnInfo> list = userBetReturnInfoService.getUserBetReturnInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos,
				startDateLong, endDateLong);
			long count = userBetReturnInfoService.getUserBetReturnInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserBetReturnInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
