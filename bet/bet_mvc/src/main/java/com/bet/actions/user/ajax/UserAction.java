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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.bet.actions.user.BaseUserController;
import com.bet.daos.IUserInfoDao;
import com.bet.enums.MoneyUnit;
import com.bet.enums.SmsCodeType;
import com.bet.enums.UserBalanceLogType;
import com.bet.enums.UserType;
import com.bet.models.ParamInfo;
import com.bet.models.User;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.orms.SmsCodeInfo;
import com.bet.orms.SmsCodeVerifyInfo;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserLoginLogInfo;
import com.bet.services.ISmsCodeConfigInfoService;
import com.bet.services.ISmsCodeInfoService;
import com.bet.services.ISmsCodeVerifyInfoService;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserInfoService;
import com.bet.services.IUserLoginLogInfoService;
import com.bet.utils.BetConstValues;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.ConstValues;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.crypto.UserCryptoTools;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userUserAction")
@RequestMapping("/user")
public class UserAction extends BaseUserController
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private ISmsCodeInfoService smsCodeInfoService;
	@Autowired
	private ISmsCodeVerifyInfoService smsCodeVerifyInfoService;
	@Autowired
	private ISmsCodeConfigInfoService smsCodeConfigInfoService;
	@Autowired
	private IUserLoginLogInfoService userLoginLogInfoService;
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;

	/**
	 * 用户注册接口<br/>
	 * 
	 * @param request
	 * @param userInfo
	 *            用户信息
	 * @param code
	 *            验证码
	 * @return
	 */
	@RequestMapping(value = "/ajaxRegister", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxRegister(HttpServletRequest request, @ModelAttribute("userInfo") UserInfo userInfo, @RequestParam(value = "appAgentId", required = false) String agentId,
		@RequestParam(value = "code", required = false) String code)
	{
		try
		{
			String ver = request.getParameter("v");
			Integer v = null;
			try
			{
				v = Integer.parseInt(ver);
			}
			catch (Exception e)
			{
			}
			if (v == null || v < configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, BetConstValues.API_LOW);
			}
			if (StringTools.isNull(userInfo.getNumber()))// 如果没有设置手机号，那么账号就是手机号
			{
				userInfo.setNumber(userInfo.getUserId());
			}
			String number = userInfo.getNumber();
			if (StringTools.isNull(number))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "手机号码不能为空！");
			}
			if (!StringTools.isNull(userInfo.getParentId()))
			{
				UserInfo agentInfo = userInfoService.getUserInfoByUserId(userInfo.getParentId());
				if (agentInfo == null || agentInfo.getUserType() < UserType.AGENT1.getType())
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "代理账号错误！");
				}
			}
			// 验证码验证
			boolean bVerifyed = true;
			long current = System.currentTimeMillis();
			String codeType = SmsCodeType.REGISTER.getType() + "";
			SmsCodeConfigInfo smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByType(codeType);
			if (smsCodeConfigInfo == null || smsCodeConfigInfo.getStatus() != StatusType.ENABLED.getStatus())
			{
				// bVerifyed = false;
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
						log.error("ajaxRegister", "添加验证码验证过期信息失败");
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
						log.error("ajaxRegister", "添加验证码验证错误信息失败");
					}
					return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "验证码错误！");
				}
				try
				{
					smsCodeVerifyInfoService.addSmsCodeVerifyInfo(smsCodeVerifyInfo);
				}
				catch (Exception e)
				{
					log.error("ajaxResetPwd", "添加验证码验证成功信息失败");
				}
			}
			if (userInfoService.getUserInfoByNumber(number) != null)
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "手机号码已注册，请更换手机号码！");
			}
			userInfo.setUserType(UserType.COMMON.getType());
			userInfo.setPoint(0);
			userInfo.setFreezePoint(0);
			userInfo.setRewardPoint(0);
			int givePoint = configInfoService.getIntValue(BetConstValues.CONFIG_REGISTER_GIVE_POINT);
			userInfo.setGivePoint(givePoint);// 注册体验金
			userInfo.setGiveValidateDateLong(current + configInfoService.getIntValue(BetConstValues.CONFIG_REGISTER_GIVE_POINT_VALIDATE) * ConstValues.ONE_DAY_TIME_LONG);
			userInfo.setNumberVerifyStatus(bVerifyed ? StatusType.ENABLED.getStatus() : StatusType.DISABLED.getStatus());
			userInfo.setAuthStatus(StatusType.DISABLED.getStatus());
			userInfo.setActiveStatus(StatusType.DISABLED.getStatus());
			userInfo.setSysStatus(StatusType.DISABLED.getStatus());
			userInfo.setStatus(StatusType.ENABLED.getStatus());
			userInfo.setRegDateLong(current);
			if (configInfoService.getBooleanValue(BetConstValues.CONFIG_REGISTER_NEED_REFERRER) && StringTools.isNull(userInfo.getReferrerId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "必须有推荐人才能注册！");
			}
			if (!StringTools.isNull(userInfo.getReferrerId()))
			{
				UserInfo referrerUserInfo = userInfoService.getUserInfoByUserId(userInfo.getReferrerId());
				if (referrerUserInfo == null)
				{
					referrerUserInfo = userInfoService.getUserInfoByNumber(userInfo.getReferrerId());
					if (referrerUserInfo == null)
					{
						throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "推荐人不存在！");
					}
				}
				userInfo.setReferrerId(referrerUserInfo.getUserId());
				userInfo.setReferrerDateLong(current);
			}
			UserInfo registerUserInfo = userInfoService.register(userInfo, code);
			if (givePoint > 0)
			{
				// 添加赠送记录
				userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(registerUserInfo.getUserId(), givePoint, MoneyUnit.GIVE_POINT.getType(), UserBalanceLogType.GIVE_POINT.getType(),
					registerUserInfo.getUserId(), null, null, null, String.format("新用户注册赠送%.2f体验彩金。", (double) givePoint / 100), current));
			}
			// setUserSession(request, registerUserInfo);
			try
			{
				userInfoService.checkAndUpdateUserParentInfoJob(userInfo.getUserId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return toObjectReturnInfo(ParseModel.parseUserInfo(registerUserInfo));
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 用户登录接口<br/>
	 * 
	 * @param request
	 * @param userInfo
	 *            用户信息，只需要账号和密码信息
	 * @param code
	 *            验证码
	 * @return
	 */
	@RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxLogin(HttpServletRequest request, @ModelAttribute("userInfo") UserInfo userInfo, @RequestParam(value = "appAgentId", required = false) String agentId,
		@RequestParam(value = "code", required = false) String code)
	{
		try
		{
			String ver = request.getParameter("v");
			Integer v = null;
			try
			{
				v = Integer.parseInt(ver);
			}
			catch (Exception e)
			{
			}
			if (v == null || v < configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, BetConstValues.API_LOW);
			}
			String sessionCode = getCodeSession(request);
			if (StringTools.isNull(sessionCode) || !sessionCode.equals(code))
			{
				setCodeSession(request, "");
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "验证码错误，请刷新后再输入！");
			}
			UserInfo loginedUserInfo = userInfoService.login(userInfo.getUserId(), userInfo.getPassword());
			if (loginedUserInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "账号或密码错误！");
			}
			if (loginedUserInfo.getStatus() != StatusType.ENABLED.getStatus())
			{
				String kefuNumber = configInfoService.getValue(BetConstValues.CONFIG_KEFU_NUMBER);
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "账号已被禁用，如有疑问请联系客服，电话：" + kefuNumber + "！");
			}
			try
			{
				String userId = request.getParameter("userId");
				String appAgentId = request.getParameter("appAgentId");
				String platform = request.getParameter("platform");
				String deviceName = request.getParameter("deviceName");
				String sysVersion = request.getParameter("sysVersion");
				String versionCode = request.getParameter("versionCode");
				String sign = request.getParameter("sign");
				String sessionId = request.getParameter("sessionId");
				List<ParamInfo> paramInfoList = new ArrayList<>();
				Map<String, String[]> paramMap = request.getParameterMap();
				if (paramMap != null)
				{
					for (String param : paramMap.keySet())
					{
						String[] strs = paramMap.get(param);
						String value = "";
						int index = 0;
						for (String str : strs)
						{
							if (index == 0)
							{
								value += str;
							}
							else
							{
								value += ";;;;" + str;
							}
							index++;
						}
						paramInfoList.add(new ParamInfo(param, value));
					}
				}
				long current = System.currentTimeMillis();
				userLoginLogInfoService.addUserLoginLogInfo(new UserLoginLogInfo(userId, getClientIp(request), appAgentId, platform, deviceName, sysVersion, versionCode, sign, ver, sessionId,
					GsonTools.toJson(paramInfoList), StatusType.ENABLED.getStatus(), "登录成功。", current));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			setUserSession(request, loginedUserInfo);
			return toObjectReturnInfo(ParseModel.parseUserInfo(loginedUserInfo));
		}
		catch (Exception e)
		{
			setCodeSession(request, "");
			try
			{
				String userId = request.getParameter("userId");
				String appAgentId = request.getParameter("appAgentId");
				String platform = request.getParameter("platform");
				String deviceName = request.getParameter("deviceName");
				String sysVersion = request.getParameter("sysVersion");
				String versionCode = request.getParameter("versionCode");
				String sign = request.getParameter("sign");
				String ver = request.getParameter("v");
				String sessionId = request.getParameter("sessionId");
				List<ParamInfo> paramInfoList = new ArrayList<>();
				Map<String, String[]> paramMap = request.getParameterMap();
				if (paramMap != null)
				{
					for (String param : paramMap.keySet())
					{
						String[] strs = paramMap.get(param);
						String value = "";
						int index = 0;
						for (String str : strs)
						{
							if (index == 0)
							{
								value += str;
							}
							else
							{
								value += ";;;;" + str;
							}
							index++;
						}
						paramInfoList.add(new ParamInfo(param, value));
					}
				}
				long current = System.currentTimeMillis();
				userLoginLogInfoService.addUserLoginLogInfo(new UserLoginLogInfo(userId, getClientIp(request), appAgentId, platform, deviceName, sysVersion, versionCode, sign, ver, sessionId,
					GsonTools.toJson(paramInfoList), StatusType.DISABLED.getStatus(), e.getMessage(), current));
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 用户二级密码验证接口<br/>
	 * 
	 * @param request
	 * @param password
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/ajaxAuthPwd2", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAuthPwd2(HttpServletRequest request, @RequestParam(value = "password", required = true) String password)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
			if (!UserCryptoTools.getCryptoPwd2(sessionUserInfo.getUserId(), password).equals(sessionUserInfo.getPassword2()))
			{
				setAuth2Session(request, false);
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "密码错误！");
			}
			setAuth2Session(request, true);
			return toObjectReturnInfo(ParseModel.parseUserInfo(sessionUserInfo));
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户资料接口<br/>
	 * 
	 * @param request
	 * @param userInfo
	 *            用户信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserInfo(HttpServletRequest request, @ModelAttribute(value = "userInfo") UserInfo userInfo, @RequestParam(value = "code", required = true) String code)
	{
		try
		{
			userInfo.setRemark(HtmlUtils.htmlUnescape(userInfo.getRemark()));
			UserInfo sessionUserInfo = getUserSession(request);
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
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
						log.error("ajaxRegister", "添加验证码验证过期信息失败");
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
						log.error("ajaxRegister", "添加验证码验证错误信息失败");
					}
					return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "验证码错误！");
				}
				try
				{
					smsCodeVerifyInfoService.addSmsCodeVerifyInfo(smsCodeVerifyInfo);
				}
				catch (Exception e)
				{
					log.error("ajaxResetPwd", "添加验证码验证成功信息失败");
				}
			}
			Map<String, Object> valueMap = new HashMap<>();
			if (sessionUserInfo.getNumberVerifyStatus() != StatusType.ENABLED.getStatus())
			{
				if (!StringTools.isNull(userInfo.getNumber()) && StringTools.isChinaPhoneNumber(userInfo.getNumber()))
				{
					valueMap.put(IUserInfoDao.NUMBER, userInfo.getNumber());
				}
			}
			valueMap.put(IUserInfoDao.NAME, userInfo.getName());
			valueMap.put(IUserInfoDao.NICKNAME, userInfo.getNickname());
			valueMap.put(IUserInfoDao.SEX, userInfo.getSex());
			if (!StringTools.isNull(userInfo.getPicUrl()))
			{
				valueMap.put(IUserInfoDao.PIC_URL, userInfo.getPicUrl());
			}
			if (!StringTools.isNull(userInfo.getBirthday()))
			{
				valueMap.put(IUserInfoDao.BIRTHDAY, userInfo.getBirthday());
			}
			if (!StringTools.isNull(userInfo.getCountry()))
			{
				valueMap.put(IUserInfoDao.COUNTRY, userInfo.getCountry());
			}
			if (!StringTools.isNull(userInfo.getProvince()))
			{
				valueMap.put(IUserInfoDao.PROVINCE, userInfo.getProvince());
			}
			if (!StringTools.isNull(userInfo.getCity()))
			{
				valueMap.put(IUserInfoDao.CITY, userInfo.getCity());
			}
			if (!StringTools.isNull(userInfo.getAddress()))
			{
				valueMap.put(IUserInfoDao.ADDRESS, userInfo.getAddress());
			}
			if (!StringTools.isNull(userInfo.getIdentityCard()))
			{
				valueMap.put(IUserInfoDao.IDENTITY_CARD, userInfo.getIdentityCard());
			}
			if (!StringTools.isNull(userInfo.getPayeeName()))
			{
				valueMap.put(IUserInfoDao.PAYEE_NAME, userInfo.getPayeeName());
			}
			if (!StringTools.isNull(userInfo.getBankName()))
			{
				valueMap.put(IUserInfoDao.BANK_NAME, userInfo.getBankName());
			}
			if (!StringTools.isNull(userInfo.getBankCardId()))
			{
				valueMap.put(IUserInfoDao.BANK_CARD_ID, userInfo.getBankCardId());
			}
			if (!StringTools.isNull(userInfo.getRemark()))
			{
				valueMap.put(IUserInfoDao.REMARK, userInfo.getRemark());
			}
			valueMap.put(IUserInfoDao.ACTIVE_STATUS, StatusType.ENABLED.getStatus());
			if (userInfo.getActiveDateLong() == null)
			{
				valueMap.put(IUserInfoDao.ACTIVE_DATE_LONG, current);
			}
			if (!userInfoService.updateValueByUserId(sessionUserInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户资料失败！");
			}
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
			setUserSession(request, sessionUserInfo);
			return toObjectReturnInfo(ParseModel.parseUserInfo(sessionUserInfo));
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户头像接口<br/>
	 * 
	 * @param request
	 * @param headUrl
	 *            头像地址
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserHeader", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserHeader(HttpServletRequest request, @RequestParam(value = "headUrl", required = true) String picUrl)
	{
		try
		{
			if (StringTools.isNull(picUrl))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "头像不能为空！");
			}
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.PIC_URL, picUrl);
			if (!userInfoService.updateValueByUserId(sessionUserInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户头像失败！");
			}
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
			setUserSession(request, sessionUserInfo);
			return toObjectReturnInfo(ParseModel.parseUserInfo(sessionUserInfo));
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 重置密码接口<br/>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param newPassword
	 *            新密码
	 * @param code
	 *            验证码
	 * @return
	 */
	@RequestMapping(value = "/ajaxResetPwd", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxResetPwd(HttpServletRequest request, @RequestParam(value = "number", required = true) String number, @RequestParam(value = "newPassword", required = true) String newPassword,
		@RequestParam(value = "code", required = true) String code)
	{
		try
		{
			String ver = request.getParameter("v");
			Integer v = null;
			try
			{
				v = Integer.parseInt(ver);
			}
			catch (Exception e)
			{
			}
			if (v == null || v < configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您的版本过低，请升级到最新版后享受在线功能！");
			}
			UserInfo userInfo = userInfoService.getUserInfoByNumber(number);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "手机号码不存在！");
			}
			if (userInfo.getNumberVerifyStatus() != StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您的手机号码未验证，请通过人工客服找回密码！");
			}
			// 验证码验证
			long current = System.currentTimeMillis();
			String codeType = SmsCodeType.RESET_PWD.getType() + "";
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
				// String number = userInfo.getNumber();
				// if (StringTools.isNull(number))
				// {
				// throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "您未设置手机号码，不能通过手机号码重置密码！");
				// }
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
						log.error("ajaxResetPwd", "添加验证码验证过期信息失败");
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
						log.error("ajaxResetPwd", "添加验证码验证错误信息失败");
					}
					return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "验证码错误！");
				}
				try
				{
					smsCodeVerifyInfoService.addSmsCodeVerifyInfo(smsCodeVerifyInfo);
				}
				catch (Exception e)
				{
					log.error("ajaxResetPwd", "添加验证码验证成功信息失败");
				}
			}
			// 修改密码
			if (!userInfoService.updateResetPwd(userInfo.getUserId(), newPassword, code))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "重置用户密码失败！");
			}
			userInfo = userInfoService.getUserInfoByUserId(userInfo.getUserId());
			setUserSession(request, userInfo);
			return toObjectReturnInfo(ParseModel.parseUserInfo(userInfo));
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户密码接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserPwd(HttpServletRequest request, @RequestParam(value = "password", required = true) String password, @RequestParam(value = "newPwd", required = true) String newPwd)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
			if (!UserCryptoTools.getCryptoPwd(sessionUserInfo.getUserId(), password).equals(sessionUserInfo.getPassword()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "原密码错误！");
			}
			if (StringTools.isNull(newPwd))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "新密码不能为空！");
			}
			if (newPwd.length() < 6 || newPwd.length() > 16)
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "新密码应该在6~16位之间！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.PASSWORD, UserCryptoTools.getCryptoPwd(sessionUserInfo.getUserId(), newPwd));
			if (!userInfoService.updateValueByUserId(sessionUserInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "修改用户登录密码失败！");
			}
			return toStringReturnInfo("修改用户登录密码成功！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户二级密码接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserPwd2", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserPwd2(HttpServletRequest request, @RequestParam(value = "password2", required = true) String password,
		@RequestParam(value = "newPwd2", required = true) String newPwd)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
			if (!UserCryptoTools.getCryptoPwd2(sessionUserInfo.getUserId(), password).equals(sessionUserInfo.getPassword2()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "原密码错误！");
			}
			if (StringTools.isNull(newPwd))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "新密码不能为空！");
			}
			if (newPwd.length() < 6 || newPwd.length() > 16)
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "新密码应该在6~16位之间！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.PASSWORD2, UserCryptoTools.getCryptoPwd2(sessionUserInfo.getUserId(), newPwd));
			if (!userInfoService.updateValueByUserId(sessionUserInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "修改用户二级密码失败！");
			}
			return toStringReturnInfo("修改用户二级密码成功！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取用户信息接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserInfo(HttpServletRequest request, @RequestParam(value = "userId1", required = true) String userId)
	{
		try
		{
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			UserInfo userInfo1 = new UserInfo();
			userInfo1.setUserId(userInfo.getUserId());
			userInfo1.setNickname(userInfo.getNickname());
			if (!StringTools.isNull(userInfo.getPicUrl()))
			{
				userInfo1.setPicUrl(userInfo.getPicUrl());
			}
			return toObjectReturnInfo(userInfo1);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取用户自己的信息接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetMyUserInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMyUserInfo(HttpServletRequest request)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
			if (sessionUserInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseUserInfo(sessionUserInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取用户推荐人列表接口<br>
	 * 
	 * @param request
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserSubInfoList")
	@ResponseBody
	public TableData ajaxGetUserSubInfoList(HttpServletRequest request, @RequestParam(value = "userType", required = false) String userId,
		@RequestParam(value = "userType", required = false) Byte userType, @RequestParam(value = "active", required = false) Boolean active,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (StringTools.isNull(userId))
			{
				userId = sessionUserInfo.getUserId();
			}
			else
			{
				boolean canGet = false;
				UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
				while (userInfo != null)
				{
					if (sessionUserInfo.getUserId().equals(userInfo.getReferrerId()))
					{
						canGet = true;
						break;
					}
					if (StringTools.isNull(userInfo.getReferrerId()))
					{
						break;
					}
					userInfo = userInfoService.getUserInfoByUserId(userInfo.getReferrerId());
				}
				if (!canGet)
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
				}
			}
			searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, userId, true, false));
			if (userType != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, userType + "", true, false));
			}
			if (active != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.ACTIVE_STATUS, (active ? StatusType.ENABLED.getStatus() : StatusType.DISABLED.getStatus()) + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserInfoDao.USER_ID, IUserInfoDao.PARENT_ID, IUserInfoDao.PROVINCE, IUserInfoDao.CITY, IUserInfoDao.ADDRESS,
				IUserInfoDao.REFERRER_ID, IUserInfoDao.NUMBER, IUserInfoDao.USER_TYPE, IUserInfoDao.STATUS, IUserInfoDao.REMARK });
			List<UserInfo> list = userInfoService.getUserInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos, startDateLong, endDateLong);
			long count = userInfoService.getUserInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取用户推荐人列表接口<br>
	 * 
	 * @param request
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserSubInfoList1")
	@ResponseBody
	public List<UserInfo> ajaxGetUserSubInfoList1(HttpServletRequest request, @RequestParam(value = "userType", required = false) String userId,
		@RequestParam(value = "userType", required = false) Byte userType, @RequestParam(value = "active", required = false) Boolean active,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (StringTools.isNull(userId))
			{
				userId = sessionUserInfo.getUserId();
			}
			else
			{
				boolean canGet = false;
				UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
				while (userInfo != null)
				{
					if (sessionUserInfo.getUserId().equals(userInfo.getUserId()) || sessionUserInfo.getUserId().equals(userInfo.getReferrerId()))
					{
						canGet = true;
						break;
					}
					if (StringTools.isNull(userInfo.getReferrerId()))
					{
						break;
					}
					userInfo = userInfoService.getUserInfoByUserId(userInfo.getReferrerId());
				}
				if (!canGet)
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
				}
			}
			searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, userId, true, false));
			if (userType != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, userType + "", true, false));
			}
			if (active != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.ACTIVE_STATUS, (active ? StatusType.ENABLED.getStatus() : StatusType.DISABLED.getStatus()) + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserInfoDao.USER_ID, IUserInfoDao.PARENT_ID, IUserInfoDao.PROVINCE, IUserInfoDao.CITY, IUserInfoDao.ADDRESS,
				IUserInfoDao.REFERRER_ID, IUserInfoDao.NUMBER, IUserInfoDao.USER_TYPE, IUserInfoDao.STATUS, IUserInfoDao.REMARK });
			List<UserInfo> list = userInfoService.getUserInfoList(0, -1, orderInfos, searchInfos, startDateLong, endDateLong);
			if (list != null && list.size() > 0)
			{
				for (UserInfo userInfo : list)
				{
					userInfo.setPassword(null);
					userInfo.setPassword2(null);
				}
			}
			// return ParseModel.parseUserInfoList(list);
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取用户推荐的人数接口<br>
	 * 
	 * @param request
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserSubInfoListCount")
	@ResponseBody
	public long ajaxGetUserSubInfoListCount(HttpServletRequest request, @RequestParam(value = "userType", required = false) Byte userType,
		@RequestParam(value = "active", required = false) Boolean active)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, sessionUserInfo.getUserId(), true, false));
			if (userType != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, userType + "", true, false));
			}
			if (active != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.ACTIVE_STATUS, (active ? StatusType.ENABLED.getStatus() : StatusType.DISABLED.getStatus()) + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserInfoDao.USER_ID, IUserInfoDao.PARENT_ID, IUserInfoDao.PROVINCE, IUserInfoDao.CITY, IUserInfoDao.ADDRESS,
				IUserInfoDao.REFERRER_ID, IUserInfoDao.NUMBER, IUserInfoDao.USER_TYPE, IUserInfoDao.STATUS, IUserInfoDao.REMARK });
			long count = userInfoService.getUserInfoListCount(searchInfos);
			return count;
		}
		catch (Exception e)
		{
			toExceptionReturnInfo(e);
		}
		return 0;
	}

	/**
	 * 获取用户推荐人列表接口<br>
	 * 
	 * @param request
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAllUserSubInfoList")
	@ResponseBody
	public List<User> ajaxGetAllUserSubInfoList(HttpServletRequest request, @RequestParam(value = "userId", required = false) String userId,
		@RequestParam(value = "userType", required = false) Byte userType, @RequestParam(value = "active", required = false) Boolean active)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			// List<TableOrderInfo> orderInfos = new ArrayList<>();
			// List<TableSearchInfo> searchInfos = new ArrayList<>();
			// if (StringTools.isNull(userId))
			// {
			// userId = sessionUserInfo.getUserId();
			// }
			// searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, userId, true, false));
			// if (userType != null)
			// {
			// searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, userType + "", true, false));
			// }
			// if (active != null)
			// {
			// searchInfos.add(new TableSearchInfo(IUserInfoDao.ACTIVE_STATUS, (active ? StatusType.ENABLED.getStatus() : StatusType.DISABLED.getStatus()) + "", true, false));
			// }
			// List<UserInfo> list = userInfoService.getUserInfoList(0, -1, orderInfos, searchInfos);
			// List<User> userList = new ArrayList<>();
			// if (list != null && list.size() > 0)
			// {
			// for (UserInfo userInfo : list)
			// {
			// User user = new User(userId, userType, referrerId, number, name, activeStatus, regDateLong, activeDateLong, subUserList);
			// userList.add(user);
			// }
			// }
			List<User> userList = new ArrayList<>();
			userList.add(getUser(sessionUserInfo, 0));
			log.info("ajaxGetAllUserSubInfoList", GsonTools.toJson(userList));
			return userList;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取用户推荐人列表接口<br>
	 * 
	 * @param request
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAllSubUserInfoList")
	@ResponseBody
	public TableData ajaxGetAllSubUserInfoList(HttpServletRequest request, @RequestParam(value = "userType", required = false) Byte userType,
		@RequestParam(value = "active", required = false) Boolean active)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (userType != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, userType + "", true, false));
			}
			if (active != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.ACTIVE_STATUS, (active ? StatusType.ENABLED.getStatus() : StatusType.DISABLED.getStatus()) + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserInfoDao.USER_ID, IUserInfoDao.PARENT_ID, IUserInfoDao.PROVINCE, IUserInfoDao.CITY, IUserInfoDao.ADDRESS,
				IUserInfoDao.REFERRER_ID, IUserInfoDao.NUMBER, IUserInfoDao.USER_TYPE, IUserInfoDao.STATUS, IUserInfoDao.REMARK });
			List<UserInfo> list = userInfoService.getAllSubUserInfoList(sessionUserInfo.getUserId(), (int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos, null, null);
			long count = userInfoService.getAllSubUserCount(sessionUserInfo.getUserId(), searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseUserInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取用户推荐人列表数量接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAllUserSubInfoListCount")
	@ResponseBody
	public long ajaxGetAllUserSubInfoListCount(HttpServletRequest request)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			return userInfoService.getAllSubUserCount(sessionUserInfo.getUserId(), null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	private User getUser(UserInfo userInfo, int depth)
	{
		List<User> subUserList = new ArrayList<>();// userInfo.getActiveStatus() == StatusType.ENABLED.getStatus() ? "已激活" : "未激活",
		User user = new User(userInfo.getUserId(), String.format("%s-%s[%s][第%d代]", userInfo.getUserId(), userInfo.getName(), UserType.getDesc(userInfo.getUserType()), depth), userInfo.getUserType(),
			userInfo.getReferrerId(), userInfo.getNumber(), userInfo.getName(), userInfo.getActiveStatus(), userInfo.getRegDateLong(), userInfo.getActiveDateLong(), depth, subUserList);
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, userInfo.getUserId(), true, false));
		List<UserInfo> list = userInfoService.getUserInfoList(0, -1, orderInfos, searchInfos);
		if (list != null && list.size() > 0)
		{
			depth++;
			for (UserInfo userInfo1 : list)
			{
				User user1 = getUser(userInfo1, depth);
				subUserList.add(user1);
			}
		}
		return user;
	}
}
