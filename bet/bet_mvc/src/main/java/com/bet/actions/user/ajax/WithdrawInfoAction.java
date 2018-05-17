package com.bet.actions.user.ajax;

import java.util.ArrayList;
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
import com.bet.daos.IWithdrawInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.enums.MsgType;
import com.bet.enums.SmsCodeType;
import com.bet.orms.MsgInfo;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.orms.SmsCodeInfo;
import com.bet.orms.SmsCodeVerifyInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.WithdrawInfo;
import com.bet.services.CWithdrawService;
import com.bet.services.IMsgInfoService;
import com.bet.services.ISmsCodeConfigInfoService;
import com.bet.services.ISmsCodeInfoService;
import com.bet.services.ISmsCodeVerifyInfoService;
import com.bet.services.IWithdrawInfoService;
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

@Controller("userWithdrawInfoAction")
@RequestMapping(value = "/user")
public class WithdrawInfoAction extends BaseUserController
{
	@Autowired
	private IWithdrawInfoService withdrawInfoService;
	@Autowired
	private CWithdrawService cWithdrawService;
	@Autowired
	private ISmsCodeInfoService smsCodeInfoService;
	@Autowired
	private ISmsCodeVerifyInfoService smsCodeVerifyInfoService;
	@Autowired
	private ISmsCodeConfigInfoService smsCodeConfigInfoService;
	@Autowired
	private IMsgInfoService msgInfoService;

	@RequestMapping(value = "/ajaxAddWithdrawInfo")
	@ResponseBody
	public ReturnInfo ajaxAddWithdrawInfo(HttpServletRequest request, @ModelAttribute("withdrawInfo") WithdrawInfo withdrawInfo, @RequestParam(value = "code", required = false) String code)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			String number = sessionUserInfo.getNumber();
			// 验证码验证
			long current = System.currentTimeMillis();
			String codeType = SmsCodeType.USER_AUTH.getType() + "";
			SmsCodeConfigInfo smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByType(codeType);
			if (smsCodeConfigInfo == null || smsCodeConfigInfo.getStatus() != StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "短信验证码服务暂时不可用！");
			}
			else
			{
				if (StringTools.isNull(code))
				{
					throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "验证码不能为空！");
				}
				SmsCodeInfo smsCodeInfo = smsCodeInfoService.getSmsCodeInfoByNumber(number, codeType);
				if (smsCodeInfo == null)
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "请先获取验证码！");
				}
				String ip = getClientIp(request);
				SmsCodeVerifyInfo smsCodeVerifyInfo = new SmsCodeVerifyInfo(number, codeType, ip, code, current, StatusType.ENABLED.getStatus());
				if (current - smsCodeInfo.getStartDateLong() > smsCodeConfigInfo.getValidateSeconds() * 1000) // 如果是已经大于60秒，那么就应该重新获取验证码
				{
					smsCodeVerifyInfo.setStatus(StatusType.DISABLED.getStatus());
					try
					{
						smsCodeVerifyInfoService.addSmsCodeVerifyInfo(smsCodeVerifyInfo);
					}
					catch (Exception e)
					{
						log.error("ajaxAddWithdrawInfo", "添加验证码验证过期信息失败");
					}
					return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "验证码已过期，请重新获取！");
				}
				if (!code.equals(smsCodeInfo.getCode())) // 验证码错误
				{
					smsCodeVerifyInfo.setStatus(StatusType.DISABLED.getStatus());
					try
					{
						smsCodeVerifyInfoService.addSmsCodeVerifyInfo(smsCodeVerifyInfo);
					}
					catch (Exception e)
					{
						log.error("ajaxAddWithdrawInfo", "添加验证码验证错误信息失败");
					}
					return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "验证码错误！");
				}
				try
				{
					smsCodeVerifyInfoService.addSmsCodeVerifyInfo(smsCodeVerifyInfo);
				}
				catch (Exception e)
				{
					log.error("ajaxAddWithdrawInfo", "添加验证码验证成功信息失败");
				}
			}
			if (withdrawInfo.getMoney() > sessionUserInfo.getPoint())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "用户可用彩金不足！");
			}
			withdrawInfo.setUserId(sessionUserInfo.getUserId());
			ReturnInfo returnInfo = cWithdrawService.addWidthdrawInfo(withdrawInfo);
			if (ReturnInfo.isSuccess(returnInfo))
			{
				try
				{
					String adminNumber = configInfoService.getValue(BetConstValues.CONFIG_ADMIN_NUMBER);
					if (!StringTools.isNull(adminNumber))
					{
						String content = String.format("%s平台用户“%s(%s)”申请提现%.2f元，请登录后台查看！", BetConstValues.PROJECT_NAME, sessionUserInfo.getUserId(), sessionUserInfo.getName(),
							((double) withdrawInfo.getMoney()) / 100);
						sendSmsMsg(smsCodeConfigInfo, adminNumber, content);
					}
				}
				catch (Exception e)
				{
					toExceptionReturnInfo(e);
				}
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
				String title = String.format("用户%s刚刚提现了%.2f元。", userId, ((double) withdrawInfo.getMoney()) / 100);
				msgInfoService.addMsgInfo(new MsgInfo(msgInfoService.genId(), MsgType.WITHDRAW.getType(), title, sessionUserInfo.getUserId(), title, StatusType.ENABLED.getStatus(), current, current));
			}
			catch (Exception e)
			{
				toExceptionReturnInfo(e);
			}
			return returnInfo;
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteWithdrawInfo")
	@ResponseBody
	public ReturnInfo ajaxDeleteWithdrawInfo(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
			if (withdrawInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户提现信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(withdrawInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			if (withdrawInfo.getStatus() != ApplyStatus.APPLY.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "提现状态不是新申请的不能删除！");
			}
			withdrawInfoService.deleteWithdrawInfoByWithdrawId(withdrawId);
			return toStringReturnInfo("删除用户提现信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetWithdrawInfo")
	@ResponseBody
	public ReturnInfo ajaxGetWithdrawInfo(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
			if (withdrawInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户提现信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(withdrawInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseWithdrawInfo(withdrawInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetWithdrawInfoList")
	@ResponseBody
	public TableData ajaxGetWithdrawInfoList(HttpServletRequest request, @RequestParam(value = "startDateLong", required = false) Long startDateLong,
		@RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IWithdrawInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IWithdrawInfoDao.USER_ID, IWithdrawInfoDao.PAYEE_NAME, IWithdrawInfoDao.BANK_NAME, IWithdrawInfoDao.BANK_CARD_ID, IWithdrawInfoDao.STATUS });
			List<WithdrawInfo> list = withdrawInfoService.getWithdrawInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos, startDateLong,
				endDateLong);
			long count = withdrawInfoService.getWithdrawInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseWithdrawInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
