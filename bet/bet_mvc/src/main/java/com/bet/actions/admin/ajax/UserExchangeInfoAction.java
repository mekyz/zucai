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
import com.bet.daos.IUserExchangeInfoDao;
import com.bet.orms.UserExchangeInfo;
import com.bet.services.IUserExchangeInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserExchangeInfoAction")
@RequestMapping(value = "/admin")
public class UserExchangeInfoAction extends BaseAdminController
{
	@Autowired
	private IUserExchangeInfoService userExchangeInfoService;

	// @RequestMapping(value = "/ajaxDeleteUserExchangeInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxDeleteUserExchangeInfo(HttpServletRequest request, @RequestParam(value = "exchangeId", required = true) String exchangeId)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// userExchangeInfoService.deleteUserExchangeInfoByExchangeId(exchangeId);
	// return toStringReturnInfo("删除用户转账信息成功！");
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxGetUserExchangeInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserExchangeInfo(HttpServletRequest request, @RequestParam(value = "exchangeId", required = true) String exchangeId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserExchangeInfo userExchangeInfo = userExchangeInfoService.getUserExchangeInfoByExchangeId(exchangeId);
			if (userExchangeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户转账信息不存在！");
			}
			return toObjectReturnInfo(userExchangeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserExchangeInfoList")
	@ResponseBody
	public TableData ajaxGetUserExchangeInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IUserExchangeInfoDao.EXCHANGE_ID, IUserExchangeInfoDao.USER_ID, IUserExchangeInfoDao.RECEIVE_USER_ID, IUserExchangeInfoDao.STATUS });
			List<UserExchangeInfo> list = userExchangeInfoService.getUserExchangeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userExchangeInfoService.getUserExchangeInfoListCount(searchInfos);
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
