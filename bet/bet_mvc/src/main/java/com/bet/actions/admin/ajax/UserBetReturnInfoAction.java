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
import com.bet.daos.IUserBetReturnInfoDao;
import com.bet.orms.UserBetReturnInfo;
import com.bet.services.IUserBetReturnInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserBetReturnInfoAction")
@RequestMapping(value = "/admin")
public class UserBetReturnInfoAction extends BaseAdminController
{
	@Autowired
	private IUserBetReturnInfoService userBetReturnInfoService;

	@RequestMapping(value = "/ajaxGetUserBetReturnInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBetReturnInfo(HttpServletRequest request, @RequestParam(value = "logId", required = true) String logId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserBetReturnInfo userBetReturnInfo = userBetReturnInfoService.getUserBetReturnInfoByLogId(logId);
			if (userBetReturnInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户撤回下注信息不存在！");
			}
			return toObjectReturnInfo(userBetReturnInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserBetReturnInfoList")
	@ResponseBody
	public TableData ajaxGetUserBetReturnInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IUserBetReturnInfoDao.BET_ID, IUserBetReturnInfoDao.USER_ID, IUserBetReturnInfoDao.PROFIT_ID, IUserBetReturnInfoDao.PHASE_ID, IUserBetReturnInfoDao.STATUS });
			List<UserBetReturnInfo> list = userBetReturnInfoService.getUserBetReturnInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userBetReturnInfoService.getUserBetReturnInfoListCount(searchInfos);
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
