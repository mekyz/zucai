package com.bet.actions.user.ajax;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.user.BaseUserController;
import com.bet.enums.ApplyStatus;
import com.bet.models.MinleNotifyInfo;
import com.bet.models.MinleReturnInfo;
import com.bet.orms.MinlePayLogInfo;
import com.bet.orms.MinlePayNotifyLogInfo;
import com.bet.orms.PayConfigInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserRechargeInfo;
import com.bet.services.CQrService;
import com.bet.services.CRechargeService;
import com.bet.services.IMinlePayLogInfoService;
import com.bet.services.IMinlePayNotifyLogInfoService;
import com.bet.services.IPayConfigInfoService;
import com.bet.services.IUserRechargeInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.StringTools;
import com.lrcall.common.utils.crypto.CryptoTools;
import com.lrcall.lrweb.common.utils.HttpTools;
import com.lrcall.lrweb.common.utils.ServerTools;

/**
 * 民乐支付接口
 * 
 * @author libit
 */
@Controller("userMinlePayAction")
@RequestMapping(value = "/user")
public class MinlePayAction extends BaseUserController
{
	@Autowired
	private IPayConfigInfoService payConfigInfoService;
	@Autowired
	private IMinlePayLogInfoService minlePayLogInfoService;
	@Autowired
	private IMinlePayNotifyLogInfoService minlePayNotifyLogInfoService;
	@Autowired
	private IUserRechargeInfoService userRechargeInfoService;
	@Autowired
	private CRechargeService cRechargeService;
	@Autowired
	private CQrService cQrService;
	private static final String PAY_URL = "http://minlepay.com/api/pay";

	public static void main(String[] args)
	{
		String body = "libit1用户充值";
		String mch_id = "13570849261";
		String serverUrl = "http://10.90013900.com/";
		String notify_url = serverUrl + "user/ajaxMinleQrPayNotify";
		Map<String, String> params = new HashMap<>();
		params.put("body", body);
		params.put("mch_id", mch_id);
		params.put("notify_url", notify_url);
		params.put("back_url", "http://10.90013900.com/user/minlePayBack");
		params.put("out_trade_no", "693261800201110");
		params.put("total_fee", "1");
		params.put("card_type", "0");
		params.put("type", "QUICK");
		String sign = CryptoTools.md5(CryptoTools.getSignData(params).substring(1) + "CLXa(NzgiR(6tZO[@9!>(c03").toLowerCase();
		params.put("sign", sign);
		HttpTools.doJsonPost(PAY_URL, params, HttpTools.UTF8);
	}

	/**
	 * 扫码支付接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxMinleQrPay")
	@ResponseBody
	public ReturnInfo ajaxMinleQrPay(HttpServletRequest request, @RequestParam(value = "out_trade_no", required = true) String out_trade_no, @RequestParam(value = "type", required = true) String type,
		@RequestParam(value = "totalFee", required = true) String totalFee)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			PayConfigInfo payConfigInfo = payConfigInfoService.getMinlePayConfigInfo();
			if (payConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "支付配置信息不存在！");
			}
			String body = sessionUserInfo.getUserId() + "用户充值";
			String mch_id = payConfigInfo.getMchId();
			String serverUrl = configInfoService.getServerUrl();
			String notify_url = serverUrl + "user/ajaxMinleQrPayNotify";
			Map<String, String> params = new HashMap<>();
			params.put("body", body);
			params.put("mch_id", mch_id);
			params.put("notify_url", notify_url);
			params.put("out_trade_no", out_trade_no);
			params.put("total_fee", totalFee);
			params.put("type", type);
			String sign = CryptoTools.md5(CryptoTools.getSignData(params).substring(1) + payConfigInfo.getPayKey()).toLowerCase();
			params.put("sign", sign);
			String result = HttpTools.doJsonPost(PAY_URL, params, HttpTools.UTF8);
			MinleReturnInfo minleReturnInfo = GsonTools.getObject(result, MinleReturnInfo.class);
			if (minleReturnInfo != null && !StringTools.isNull(minleReturnInfo.getQr_code()))
			{
				String fileName = "file/upload/images/qr_pay/" + out_trade_no + ".jpg";
				ReturnInfo returnInfo = cQrService.genQr(minleReturnInfo.getQr_code(), ServerTools.getServerRealRootPath(request) + fileName);
				if (ReturnInfo.isSuccess(returnInfo))
				{
					minleReturnInfo.setQr_code(serverUrl + fileName);
				}
			}
			else
			{
				minleReturnInfo = new MinleReturnInfo();
			}
			long current = System.currentTimeMillis();
			try
			{
				minlePayLogInfoService.addMinlePayLogInfo(new MinlePayLogInfo(minlePayLogInfoService.genId(), mch_id, sign, type, notify_url, null, null, out_trade_no, totalFee, body,
					minleReturnInfo.getError_code(), minleReturnInfo.getError_msg(), minleReturnInfo.getSign(), minleReturnInfo.getOut_trade_no(), minleReturnInfo.getOrder_id(),
					minleReturnInfo.getTotal_fee(), minleReturnInfo.getPay_url(), minleReturnInfo.getQr_code(), StatusType.ENABLED.getStatus(), current, current));
			}
			catch (Exception e)
			{
			}
			return toObjectReturnInfo(minleReturnInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * H5和WAP、快捷支付接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxMinleH5Pay")
	@ResponseBody
	public ReturnInfo ajaxMinleH5Pay(HttpServletRequest request, @RequestParam(value = "out_trade_no", required = true) String out_trade_no, @RequestParam(value = "type", required = true) String type,
		@RequestParam(value = "totalFee", required = true) String totalFee)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			PayConfigInfo payConfigInfo = payConfigInfoService.getMinlePayConfigInfo();
			if (payConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "支付配置信息不存在！");
			}
			String body = sessionUserInfo.getUserId() + "用户充值";
			String mch_id = payConfigInfo.getMchId();
			String serverUrl = configInfoService.getServerUrl();
			String notify_url = serverUrl + "user/ajaxMinleH5PayNotify";
			String back_url = configInfoService.getServerUrl() + "user/minlePayBack";
			String card_type = "0";
			Map<String, String> params = new HashMap<>();
			params.put("back_url", back_url);
			params.put("body", body);
			params.put("card_type", card_type);
			params.put("mch_id", mch_id);
			params.put("notify_url", notify_url);
			params.put("out_trade_no", out_trade_no);
			params.put("total_fee", totalFee);
			params.put("type", type);
			String sign = CryptoTools.md5(CryptoTools.getSignData(params).substring(1) + payConfigInfo.getPayKey()).toLowerCase();
			params.put("sign", sign);
			String result = HttpTools.doJsonPost(PAY_URL, params, HttpTools.UTF8);
			MinleReturnInfo minleReturnInfo = GsonTools.getObject(result, MinleReturnInfo.class);
			if (minleReturnInfo == null)
			{
				minleReturnInfo = new MinleReturnInfo();
			}
			long current = System.currentTimeMillis();
			try
			{
				minlePayLogInfoService.addMinlePayLogInfo(new MinlePayLogInfo(minlePayLogInfoService.genId(), mch_id, sign, type, notify_url, back_url, card_type, out_trade_no, totalFee, body,
					minleReturnInfo.getError_code(), minleReturnInfo.getError_msg(), minleReturnInfo.getSign(), minleReturnInfo.getOut_trade_no(), minleReturnInfo.getOrder_id(),
					minleReturnInfo.getTotal_fee(), minleReturnInfo.getPay_url(), minleReturnInfo.getQr_code(), StatusType.ENABLED.getStatus(), current, current));
			}
			catch (Exception e)
			{
			}
			return toStringReturnInfo(result);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 扫码支付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxMinleQrPayNotify", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String ajaxMinleQrPayNotify(HttpServletRequest request, @RequestBody MinleNotifyInfo minleNotifyInfo)
	{
		try
		{
			// String params1 = String.format("error_code=%s&error_msg=%s&sign=%s&out_trade_no=%s&order_id=%s&pay_status=%s&total_fee=%s&body=%s", minleNotifyInfo.get, error_msg, sign, out_trade_no,
			// order_id,
			// pay_status, total_fee, body);
			LogTools.getInstance().info("ajaxMinlePayNotify", GsonTools.toJson(minleNotifyInfo));
			long current = System.currentTimeMillis();
			try
			{
				minlePayNotifyLogInfoService.addMinlePayNotifyLogInfo(new MinlePayNotifyLogInfo(minlePayNotifyLogInfoService.genId(), minleNotifyInfo.getError_code(), minleNotifyInfo.getError_msg(),
					minleNotifyInfo.getSign(), minleNotifyInfo.getPay_status(), minleNotifyInfo.getOut_trade_no(), minleNotifyInfo.getOrder_id(), minleNotifyInfo.getTotal_fee(),
					minleNotifyInfo.getBody(), StatusType.ENABLED.getStatus(), current, current));
			}
			catch (Exception e)
			{
			}
			PayConfigInfo payConfigInfo = payConfigInfoService.getMinlePayConfigInfo();
			if (payConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "支付配置信息不存在！");
			}
			// 验证签名
			Map<String, String> params = new HashMap<>();
			params.put("out_trade_no", minleNotifyInfo.getOut_trade_no());
			params.put("order_id", minleNotifyInfo.getOrder_id());
			params.put("pay_status", minleNotifyInfo.getPay_status());
			params.put("total_fee", minleNotifyInfo.getTotal_fee());
			params.put("body", minleNotifyInfo.getBody());
			String sign1 = CryptoTools.md5(CryptoTools.getSignData(params).substring(1) + payConfigInfo.getPayKey()).toLowerCase();
			if (!sign1.equalsIgnoreCase(minleNotifyInfo.getSign()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "回调签名错误！");
			}
			//
			if (minleNotifyInfo.getPay_status().equals("1"))// 支付成功
			{
				UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(minleNotifyInfo.getOut_trade_no());
				if (userRechargeInfo == null)
				{
					throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "充值订单不存在！");
				}
				if (userRechargeInfo.getStatus() == ApplyStatus.APPLY.getStatus())
				{
					ReturnInfo returnInfo = cRechargeService.updateUserRechargeInfoVerify(minleNotifyInfo.getOut_trade_no(), ApplyStatus.VERIFY_SUCCESS.getStatus());
					if (!ReturnInfo.isSuccess(returnInfo))
					{
						throw new HibernateJsonResultException(returnInfo);
					}
				}
				else
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "充值订单已处理！");
				}
			}
			else
			{
			}
			return "SUCCESS";
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
			return "ERROR";
		}
	}

	/**
	 * H5支付回调接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxMinleH5PayNotify", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ajaxMinleH5PayNotify(HttpServletRequest request, @RequestBody MinleNotifyInfo minleNotifyInfo)
	{
		try
		{
			// return ajaxMinleQrPayNotify(request, error_code, error_msg, sign, out_trade_no, order_id, pay_status, total_fee, body);
			return ajaxMinleQrPayNotify(request, minleNotifyInfo);
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
			return "ERROR";
		}
	}
}
