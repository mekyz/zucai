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
import com.bet.daos.IUserBalanceLogInfoDao;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userUserBalanceLogInfoAction")
@RequestMapping(value = "/user")
public class UserBalanceLogInfoAction extends BaseUserController
{
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;

	@RequestMapping(value = "/ajaxGetUserBalanceLogInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBalanceLogInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserBalanceLogInfo userBalanceLogInfo = userBalanceLogInfoService.getUserBalanceLogInfoById(id);
			if (userBalanceLogInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户余额记录信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userBalanceLogInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseUserBalanceLogInfo(userBalanceLogInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBalanceLogInfoList")
	@ResponseBody
	public TableData ajaxGetUserBalanceLogInfoList(HttpServletRequest request, @RequestParam(value = "moneyUnit", required = false) Byte moneyUnit,
		@RequestParam(value = "logType", required = false) String logType, @RequestParam(value = "startDateLong", required = false) Long startDateLong,
		@RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserBalanceLogInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			if (moneyUnit != null)
			{
				searchInfos.add(new TableSearchInfo(IUserBalanceLogInfoDao.MONEY_UNIT, moneyUnit + "", true, false));
			}
			if (!StringTools.isNull(logType))
			{
				searchInfos.add(new TableSearchInfo(IUserBalanceLogInfoDao.LOG_TYPE, logType, true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserBalanceLogInfoDao.USER_ID, IUserBalanceLogInfoDao.LOG_TYPE, IUserBalanceLogInfoDao.SERVER_ID });
			List<UserBalanceLogInfo> list = userBalanceLogInfoService.getUserBalanceLogInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos,
				startDateLong, endDateLong);
			long count = userBalanceLogInfoService.getUserBalanceLogInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserBalanceLogInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
