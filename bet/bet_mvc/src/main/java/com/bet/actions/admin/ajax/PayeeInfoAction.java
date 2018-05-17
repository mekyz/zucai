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
import com.bet.daos.IPayeeInfoDao;
import com.bet.orms.PayeeInfo;
import com.bet.services.IPayeeInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminPayeeInfoAction")
@RequestMapping(value = "/admin")
public class PayeeInfoAction extends BaseAdminController
{
	@Autowired
	private IPayeeInfoService payeeInfoService;

	@RequestMapping(value = "/ajaxAddPayeeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddPayeeInfo(HttpServletRequest request, @ModelAttribute("payeeInfo") PayeeInfo payeeInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			payeeInfo.setPayeeId(payeeInfoService.genId());
			payeeInfo.setStatus(StatusType.ENABLED.getStatus());
			payeeInfo = payeeInfoService.addPayeeInfo(payeeInfo);
			if (payeeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加收款信息失败！");
			}
			return toObjectReturnInfo(payeeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdatePayeeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdatePayeeInfo(HttpServletRequest request, @ModelAttribute("payeeInfo") PayeeInfo payeeInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IPayeeInfoDao.PAYEE_NAME, payeeInfo.getPayeeName());
			valueMap.put(IPayeeInfoDao.BANK_NAME, payeeInfo.getBankName());
			valueMap.put(IPayeeInfoDao.BANK_CARD_ID, payeeInfo.getBankCardId());
			valueMap.put(IPayeeInfoDao.PIC_URL, payeeInfo.getPicUrl());
			valueMap.put(IPayeeInfoDao.TYPE, payeeInfo.getType());
			valueMap.put(IPayeeInfoDao.SORT_INDEX, payeeInfo.getSortIndex());
			valueMap.put(IPayeeInfoDao.UPDATE_DATE_LONG, current);
			if (!payeeInfoService.updateValueByPayeeId(payeeInfo.getPayeeId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新收款信息失败！");
			}
			return toObjectReturnInfo(payeeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdatePayeeInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdatePayeeInfoStatus(HttpServletRequest request, @RequestParam(value = "payeeId", required = true) String payeeId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IPayeeInfoDao.STATUS, status);
			valueMap.put(IPayeeInfoDao.UPDATE_DATE_LONG, current);
			if (!payeeInfoService.updateValueByPayeeId(payeeId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新收款信息状态失败！");
			}
			return toStringReturnInfo("更新收款信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeletePayeeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeletePayeeInfo(HttpServletRequest request, @RequestParam(value = "payeeId", required = true) String payeeId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			payeeInfoService.deletePayeeInfoByPayeeId(payeeId);
			return toStringReturnInfo("删除收款信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetPayeeInfo")
	@ResponseBody
	public ReturnInfo ajaxGetPayeeInfo(HttpServletRequest request, @RequestParam(value = "payeeId", required = true) String payeeId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeId(payeeId);
			if (payeeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "收款信息不存在！");
			}
			return toObjectReturnInfo(payeeInfo);
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
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IPayeeInfoDao.PAYEE_ID, IPayeeInfoDao.BANK_CARD_ID, IPayeeInfoDao.TYPE });
			List<PayeeInfo> list = payeeInfoService.getPayeeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = payeeInfoService.getPayeeInfoListCount(searchInfos);
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
