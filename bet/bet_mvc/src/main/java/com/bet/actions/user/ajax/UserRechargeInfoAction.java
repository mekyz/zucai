package com.bet.actions.user.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.user.BaseUserController;
import com.bet.daos.IUserRechargeInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.enums.MsgType;
import com.bet.enums.SmsCodeType;
import com.bet.orms.MsgInfo;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserRechargeInfo;
import com.bet.services.IMsgInfoService;
import com.bet.services.ISmsCodeConfigInfoService;
import com.bet.services.IUserRechargeInfoService;
import com.bet.utils.BetConstValues;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userUserRechargeInfoAction")
@RequestMapping(value = "/user")
public class UserRechargeInfoAction extends BaseUserController
{
	@Autowired
	private IUserRechargeInfoService userRechargeInfoService;
	@Autowired
	private ISmsCodeConfigInfoService smsCodeConfigInfoService;
	@Autowired
	private IMsgInfoService msgInfoService;

	@RequestMapping(value = "/ajaxAddUserRechargeInfo")
	@ResponseBody
	public ReturnInfo ajaxAddUserRechargeInfo(HttpServletRequest request, @ModelAttribute("userRechargeInfo") UserRechargeInfo userRechargeInfo)
	{
		try
		{
			int minMoney = configInfoService.getIntValue(BetConstValues.CONFIG_MIN_RECHARGE);
			if (minMoney > userRechargeInfo.getMoney())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, String.format("最低充值金额为%.2f元，请重新输入！", ((double) minMoney) / 100));
			}
			UserInfo sessionUserInfo = getUserSession(request);
			long current = System.currentTimeMillis();
			userRechargeInfo.setRechargeId(userRechargeInfoService.genId());
			userRechargeInfo.setUserId(sessionUserInfo.getUserId());
			userRechargeInfo.setMoney(userRechargeInfo.getMoney());
			userRechargeInfo.setUserMoney(userRechargeInfo.getMoney());
			userRechargeInfo.setFee(0);
			userRechargeInfo.setStatus(ApplyStatus.APPLY.getStatus());
			userRechargeInfo.setPayDateLong(current);
			userRechargeInfo = userRechargeInfoService.addUserRechargeInfo(userRechargeInfo);
			if (userRechargeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加用户充值信息失败！");
			}
			try
			{
				String adminNumber = configInfoService.getValue(BetConstValues.CONFIG_ADMIN_NUMBER);
				if (!StringTools.isNull(adminNumber))
				{
					String codeType = SmsCodeType.USER_AUTH.getType() + "";
					SmsCodeConfigInfo smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByType(codeType);
					if (smsCodeConfigInfo != null && smsCodeConfigInfo.getStatus() == StatusType.ENABLED.getStatus())
					{
						String content = String.format("%s平台用户“%s(%s)”申请充值%.2f元，请登录后台查看！", BetConstValues.PROJECT_NAME, sessionUserInfo.getUserId(), sessionUserInfo.getName(),
							((double) userRechargeInfo.getMoney()) / 100);
						sendSmsMsg(smsCodeConfigInfo, adminNumber, content);
					}
				}
			}
			catch (Exception e)
			{
				toExceptionReturnInfo(e);
			}
			// 添加到消息
			try
			{
				String userId = sessionUserInfo.getUserId();
				if (userId.length() > 5)
				{
					int endIndex = (userId.length() - 2) / 2 + (userId.length() % 2 == 0 ? 0 : 1);
					userId = String.format("%s**%s", userId.substring(0, endIndex), userId.substring(endIndex + 2));
				}
				String title = String.format("用户%s刚刚充值了%.2f彩金。", userId, ((double) userRechargeInfo.getMoney()) / 100);
				log.info("ajaxAddUserRechargeInfo", title);
				msgInfoService.addMsgInfo(new MsgInfo(msgInfoService.genId(), MsgType.RECHARGE.getType(), title, sessionUserInfo.getUserId(), title, StatusType.ENABLED.getStatus(), current, current));
			}
			catch (Exception e)
			{
				toExceptionReturnInfo(e);
			}
			return toObjectReturnInfo(ParseModel.parseUserRechargeInfo(userRechargeInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateUserRechargeInfo")
	@ResponseBody
	public ReturnInfo ajaxUpdateUserRechargeInfo(HttpServletRequest request, @ModelAttribute("userRechargeInfo") UserRechargeInfo userRechargeInfo)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserRechargeInfo userRechargeInfo1 = userRechargeInfoService.getUserRechargeInfoByRechargeId(userRechargeInfo.getRechargeId());
			if (userRechargeInfo1 == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户充值信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userRechargeInfo1.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserRechargeInfoDao.MONEY, userRechargeInfo.getMoney());
			valueMap.put(IUserRechargeInfoDao.USER_MONEY, userRechargeInfo.getMoney());
			valueMap.put(IUserRechargeInfoDao.FEE, 0);
			valueMap.put(IUserRechargeInfoDao.PAY_NAME, userRechargeInfo.getPayName());
			valueMap.put(IUserRechargeInfoDao.PAY_BANK_NAME, userRechargeInfo.getPayBankName());
			valueMap.put(IUserRechargeInfoDao.PAY_BANK_CARD_ID, userRechargeInfo.getPayBankCardId());
			valueMap.put(IUserRechargeInfoDao.PAYEE_NAME, userRechargeInfo.getPayeeName());
			valueMap.put(IUserRechargeInfoDao.PAYEE_BANK_NAME, userRechargeInfo.getPayeeBankName());
			valueMap.put(IUserRechargeInfoDao.PAYEE_BANK_CARD_ID, userRechargeInfo.getPayeeBankCardId());
			valueMap.put(IUserRechargeInfoDao.PAY_PIC_URL, userRechargeInfo.getPayPicUrl());
			valueMap.put(IUserRechargeInfoDao.PAY_REMARK, userRechargeInfo.getPayRemark());
			valueMap.put(IUserRechargeInfoDao.UPDATE_DATE_LONG, current);
			if (!userRechargeInfoService.updateValueByRechargeId(userRechargeInfo.getRechargeId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户充值信息失败！");
			}
			return toObjectReturnInfo(ParseModel.parseUserRechargeInfo(userRechargeInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteUserRechargeInfo")
	@ResponseBody
	public ReturnInfo ajaxDeleteUserRechargeInfo(HttpServletRequest request, @RequestParam(value = "rechargeId", required = true) String rechargeId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(rechargeId);
			if (userRechargeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户充值信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userRechargeInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			if (userRechargeInfo.getStatus() != ApplyStatus.APPLY.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "充值信息正在处理不能删除！");
			}
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
			UserInfo sessionUserInfo = getUserSession(request);
			UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(rechargeId);
			if (userRechargeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户充值信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userRechargeInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseUserRechargeInfo(userRechargeInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserRechargeInfoList")
	@ResponseBody
	public TableData ajaxGetUserRechargeInfoList(HttpServletRequest request, @RequestParam(value = "status", required = false) Byte status,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserRechargeInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			if (status != null)
			{
				searchInfos.add(new TableSearchInfo(IUserRechargeInfoDao.STATUS, status + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserRechargeInfoDao.USER_ID, IUserRechargeInfoDao.STATUS });
			List<UserRechargeInfo> list = userRechargeInfoService.getUserRechargeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos,
				startDateLong, endDateLong);
			long count = userRechargeInfoService.getUserRechargeInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserRechargeInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
