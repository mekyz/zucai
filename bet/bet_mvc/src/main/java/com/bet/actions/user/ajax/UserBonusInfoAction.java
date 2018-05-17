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
import com.bet.daos.IUserBonusInfoDao;
import com.bet.orms.UserBonusInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IUserBonusInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userUserBonusInfoAction")
@RequestMapping(value = "/user")
public class UserBonusInfoAction extends BaseUserController
{
	@Autowired
	private IUserBonusInfoService userBonusInfoService;

	@RequestMapping(value = "/ajaxGetUserBonusInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserBonusInfo(HttpServletRequest request, @RequestParam(value = "bonusId", required = true) String bonusId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserBonusInfo userBonusInfo = userBonusInfoService.getUserBonusInfoByBonusId(bonusId);
			if (userBonusInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户奖金信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userBonusInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseUserBonusInfo(userBonusInfo));
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
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserBonusInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserBonusInfoDao.USER_ID, IUserBonusInfoDao.PHASE_ID, IUserBonusInfoDao.STATUS });
			List<UserBonusInfo> list = userBonusInfoService.getUserBonusInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userBonusInfoService.getUserBonusInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserBonusInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
