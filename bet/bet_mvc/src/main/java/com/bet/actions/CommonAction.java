package com.bet.actions;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.bet.enums.SmsCodeType;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.orms.SmsCodeInfo;
import com.bet.services.IConfigInfoService;
import com.bet.services.ISmsCodeConfigInfoService;
import com.bet.services.ISmsCodeInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.BetConstValues;
import com.bet.utils.SessionManage.SessionUser;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.RandomTools;
import com.lrcall.common.utils.RandomTools.SecurityCodeLevel;
import com.lrcall.common.utils.StringTools;

@Controller("CommonAction")
@RequestMapping("/")
public class CommonAction extends BaseAction
{
	@Autowired
	private ISmsCodeInfoService smsCodeInfoService;
	@Autowired
	private ISmsCodeConfigInfoService smsCodeConfigInfoService;
	@Autowired
	private IConfigInfoService configInfoService;
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 获取短信验证码
	 * 
	 * @param request
	 * @param number
	 *            手机号码
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetSmsCode", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxGetSmsCode(HttpServletRequest request, @RequestParam(value = "number", required = true) String number, @RequestParam(value = "type", required = true) int type,
		@RequestParam(value = "code", required = false) String code1)
	{
		try
		{
			boolean needCode = configInfoService.getBooleanValue(BetConstValues.CONFIG_SMS_CODE_NEED_CODE);
			if (needCode)
			{
				String sessionCode = (String) (request.getSession()).getAttribute(SessionUser.RANDOM_CODE.getName());
				if (StringTools.isNull(sessionCode) || !sessionCode.equals(code1))
				{
					(request.getSession()).setAttribute(SessionUser.RANDOM_CODE.getName(), "");
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "验证码错误，请刷新后再输入！");
				}
			}
			if (StringTools.isNull(number) || !StringTools.isChinaMobilePhoneNumber(number))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "手机号码错误！");
			}
			SmsCodeConfigInfo smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByType(type + "");
			if (smsCodeConfigInfo != null && smsCodeConfigInfo.getStatus() != StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "短信验证码服务暂时不可用！");
			}
			if (type == SmsCodeType.REGISTER.getType() && userInfoService.getUserInfoByNumber(number) != null)
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "手机号码已注册，请更换手机号码！");
			}
			String codeType = type + "";
			SmsCodeInfo smsCodeInfo = smsCodeInfoService.getSmsCodeInfoByNumber(number, codeType);
			long current = System.currentTimeMillis();
			String code = RandomTools.getSecurityCode(6, SecurityCodeLevel.Simple, true);
			int validateSeconds = 180;
			if (smsCodeConfigInfo != null)
			{
				validateSeconds = smsCodeConfigInfo.getValidateSeconds();
			}
			if (smsCodeInfo != null)
			{
				if (current - smsCodeInfo.getStartDateLong() < smsCodeInfo.getValidateTimeLong() * 1000) // 如果60秒内已经申请过了，直接返回让用户等待验证码
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "验证码已发送，请耐心等待！");
				}
				// 更新数据库验证码信息
				smsCodeInfo.setCode(code);
				smsCodeInfo.setStatus(StatusType.ENABLED.getStatus());
				smsCodeInfo.setStartDateLong(current);
				smsCodeInfo = smsCodeInfoService.updateSmsCodeInfo(smsCodeInfo);
			}
			else
			{
				smsCodeInfo = new SmsCodeInfo(number, codeType, code, current, validateSeconds, StatusType.ENABLED.getStatus(), current);
				smsCodeInfo = smsCodeInfoService.addSmsCodeInfo(smsCodeInfo);
			}
			if (smsCodeConfigInfo == null)
			{
				return toStringReturnInfo(String.format("您的验证码为%s，有效期：%d秒！", code, validateSeconds));
			}
			// smsCodeConfigInfo.setSmsParams(String.format(smsCodeConfigInfo.getSmsParams(), code));
			// 发短信
			try
			{
				String content = String.format("您在%s平台的验证码是%s，请查收！", BetConstValues.PROJECT_NAME, code);
				sendSmsMsg(smsCodeConfigInfo, number, content);
			}
			catch (Exception e)
			{
				toExceptionReturnInfo(e);
				return toStringReturnInfo("短信服务器异常！" + e.getMessage());
			}
			return toStringReturnInfo("验证码已发送到您的手机上面，请注意查收！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	private void sendAliMsg(SmsCodeConfigInfo smsCodeConfigInfo, String number, String code)
	{
		/**
		 * Step 1. 获取主题引用
		 */
		CloudAccount account = new CloudAccount(smsCodeConfigInfo.getAppKey(), smsCodeConfigInfo.getAppSecret(), smsCodeConfigInfo.getMnsEndpoint());
		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef(smsCodeConfigInfo.getTopic());
		/**
		 * Step 2. 设置SMS消息体（必须） 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
		 */
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("sms-message");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName(smsCodeConfigInfo.getSignName());
		// 3.2 设置发送短信使用的模板（SMSTempateCode）
		batchSmsAttributes.setTemplateCode(smsCodeConfigInfo.getSmsTemplateCode());
		// 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
		smsReceiverParams.setParam(smsCodeConfigInfo.getSmsParams(), code);
		// smsReceiverParams.setParam("$YourSMSTemplateParamKey2", "$value2");
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver(number, smsReceiverParams);
		// batchSmsAttributes.addSmsReceiver("$YourReceiverPhoneNumber2", smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
		try
		{
			/**
			 * Step 4. 发布SMS消息
			 */
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);
			log.info("ajaxGetSmsCode", "MessageId: " + ret.getMessageId());
			log.info("ajaxGetSmsCode", "MessageMD5: " + ret.getMessageBodyMD5());
		}
		catch (ServiceException se)
		{
			log.info("ajaxGetSmsCode", se.getMessage());
			se.printStackTrace();
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "验证码发送失败：" + se.getMessage() + "！");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "验证码发送失败：" + e.getMessage() + "！");
		}
		client.close();
	}
}
