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
import com.bet.daos.IClientBugInfoDao;
import com.bet.orms.ClientBugInfo;
import com.bet.services.IClientBugInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminClientBugInfoAction")
@RequestMapping(value = "/admin")
public class ClientBugInfoAction extends BaseAdminController
{
	@Autowired
	private IClientBugInfoService clientBugInfoService;

	@RequestMapping(value = "/ajaxDeleteClientBugInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteClientBugInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			clientBugInfoService.deleteClientBugInfoById(id);
			return toStringReturnInfo("删除客户端BUG信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetClientBugInfo")
	@ResponseBody
	public ReturnInfo ajaxGetClientBugInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			ClientBugInfo clientBugInfo = clientBugInfoService.getClientBugInfoById(id);
			if (clientBugInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "客户端BUG信息不存在！");
			}
			return toObjectReturnInfo(clientBugInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetClientBugInfoList")
	@ResponseBody
	public TableData ajaxGetClientBugInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IClientBugInfoDao.DEVICE_NAME });
			List<ClientBugInfo> list = clientBugInfoService.getClientBugInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = clientBugInfoService.getClientBugInfoListCount(searchInfos);
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
