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
import com.bet.daos.IUserExchangeInfoDao;
import com.bet.enums.SmsCodeType;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.orms.SmsCodeInfo;
import com.bet.orms.SmsCodeVerifyInfo;
import com.bet.orms.UserExchangeInfo;
import com.bet.orms.UserInfo;
import com.bet.services.CExchangeService;
import com.bet.services.ISmsCodeConfigInfoService;
import com.bet.services.ISmsCodeInfoService;
import com.bet.services.ISmsCodeVerifyInfoService;
import com.bet.services.IUserExchangeInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.crypto.UserCryptoTools;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userUserExchangeInfoAction")
@RequestMapping(value = "/user")
public class UserExchangeInfoAction extends BaseUserController
{
	@Autowired
	private IUserExchangeInfoService userExchangeInfoService;
	@Autowired
	private CExchangeService cExchangeService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private ISmsCodeInfoService smsCodeInfoService;
	@Autowired
	private ISmsCodeVerifyInfoService smsCodeVerifyInfoService;
	@Autowired
	private ISmsCodeConfigInfoService smsCodeConfigInfoService;

	@RequestMapping(value = "/ajaxAddUserExchangeInfo")
	@ResponseBody
	public ReturnInfo ajaxAddUserExchangeInfo(HttpServletRequest request, @ModelAttribute("userExchangeInfo") UserExchangeInfo userExchangeInfo,
		@RequestParam(value = "password2", required = true) String password2, @RequestParam(value = "code", required = false) String code)
	{
		try
		{
			if (StringTools.isNull(code))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请输入短信验证码！");
			}
			UserInfo sessionUserInfo = getUserSession(request);
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
			setUserSession(request, sessionUserInfo);
			if (!UserCryptoTools.getCryptoPwd2(sessionUserInfo.getUserId(), password2).equals(sessionUserInfo.getPassword2()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "二级密码错误，请重新输入！");
			}
			if (userExchangeInfo.getMoney() % 1000 != 0)
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "转账必须是10的整数倍！");
			}
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
						log.error("ajaxAddUserExchangeInfo", "添加验证码验证过期信息失败");
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
						log.error("ajaxAddUserExchangeInfo", "添加验证码验证错误信息失败");
					}
					return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "验证码错误！");
				}
				try
				{
					smsCodeVerifyInfoService.addSmsCodeVerifyInfo(smsCodeVerifyInfo);
				}
				catch (Exception e)
				{
					log.error("ajaxAddUserExchangeInfo", "添加验证码验证成功信息失败");
				}
			}
			userExchangeInfo.setUserId(sessionUserInfo.getUserId());
			return cExchangeService.addUserExchangeInfo(userExchangeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserExchangeInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserExchangeInfo(HttpServletRequest request, @RequestParam(value = "exchangeId", required = true) String exchangeId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			UserExchangeInfo userExchangeInfo = userExchangeInfoService.getUserExchangeInfoByExchangeId(exchangeId);
			if (userExchangeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户转账信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(userExchangeInfo.getUserId()) && !sessionUserInfo.getUserId().equals(userExchangeInfo.getReceiveUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseUserExchangeInfo(userExchangeInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetUserExchangeInfoList")
	@ResponseBody
	public TableData ajaxGetUserExchangeInfoList(HttpServletRequest request, @RequestParam(value = "status", required = false) Byte status,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IUserExchangeInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			if (status != null)
			{
				searchInfos.add(new TableSearchInfo(IUserExchangeInfoDao.STATUS, status + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IUserExchangeInfoDao.EXCHANGE_ID, IUserExchangeInfoDao.USER_ID, IUserExchangeInfoDao.RECEIVE_USER_ID, IUserExchangeInfoDao.STATUS });
			List<UserExchangeInfo> list = userExchangeInfoService.getUserExchangeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos,
				startDateLong, endDateLong);
			long count = userExchangeInfoService.getUserExchangeInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserExchangeInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
