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
import com.bet.daos.IPayeeInfoDao;
import com.bet.orms.PayeeInfo;
import com.bet.services.IPayeeInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userPayeeInfoAction")
@RequestMapping(value = "/user")
public class PayeeInfoAction extends BaseUserController
{
	@Autowired
	private IPayeeInfoService payeeInfoService;

	@RequestMapping(value = "/ajaxGetPayeeInfo")
	@ResponseBody
	public ReturnInfo ajaxGetPayeeInfo(HttpServletRequest request, @RequestParam(value = "payeeId", required = true) String payeeId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeId(payeeId);
			if (payeeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "收款信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parsePayeeInfo(payeeInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetPayeeInfoByType")
	@ResponseBody
	public ReturnInfo ajaxGetPayeeInfoByType(HttpServletRequest request, @RequestParam(value = "type", required = true) String type)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(type);
			if (payeeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "收款信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parsePayeeInfo(payeeInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetPayeeInfoList")
	@ResponseBody
	public TableData ajaxGetPayeeInfoList(HttpServletRequest request)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IPayeeInfoDao.PAYEE_ID, IPayeeInfoDao.BANK_CARD_ID, IPayeeInfoDao.TYPE });
			List<PayeeInfo> list = payeeInfoService.getPayeeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = payeeInfoService.getPayeeInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parsePayeeInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
