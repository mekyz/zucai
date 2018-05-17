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
import com.bet.daos.IAdviceTypeInfoDao;
import com.bet.orms.AdviceTypeInfo;
import com.bet.services.IAdviceTypeInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userAdviceTypeInfoAction")
@RequestMapping(value = "/user")
public class AdviceTypeInfoAction extends BaseUserController
{
	@Autowired
	private IAdviceTypeInfoService adviceTypeInfoService;

	@RequestMapping(value = "/ajaxGetAdviceTypeInfo")
	@ResponseBody
	public ReturnInfo ajaxGetAdviceTypeInfo(HttpServletRequest request, @RequestParam(value = "typeId", required = true) String typeId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			AdviceTypeInfo adviceTypeInfo = adviceTypeInfoService.getAdviceTypeInfoByTypeId(typeId);
			if (adviceTypeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "意见反馈类型信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseAdviceTypeInfo(adviceTypeInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetAdviceTypeInfoList")
	@ResponseBody
	public TableData ajaxGetAdviceTypeInfoList(HttpServletRequest request)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IAdviceTypeInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IAdviceTypeInfoDao.NAME, IAdviceTypeInfoDao.STATUS });
			List<AdviceTypeInfo> list = adviceTypeInfoService.getAdviceTypeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = adviceTypeInfoService.getAdviceTypeInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseAdviceTypeInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
