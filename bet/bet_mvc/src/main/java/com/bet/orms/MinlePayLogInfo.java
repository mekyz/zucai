package com.bet.orms;
// Generated 2018-4-12 22:01:22 by Hibernate Tools 4.3.5.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * MinlePayLogInfo generated by hbm2java
 */
@Entity
@Table(name = "minle_pay_log_info", uniqueConstraints = @UniqueConstraint(columnNames = "log_id"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MinlePayLogInfo implements java.io.Serializable
{
	private static final long serialVersionUID = 8983102601006141773L;
	private Integer id;
	private String logId;
	private String mchId;
	private String reqSign;
	private String type;
	private String notifyUrl;
	private String backUrl;
	private String cardType;
	private String outTradeNo;
	private String totalFee;
	private String body;
	private String errorCode;
	private String errorMsg;
	private String respSign;
	private String outTradeNo1;
	private String orderId;
	private String totalFee1;
	private String payUrl;
	private String qrCode;
	private byte status;
	private long addDateLong;
	private long updateDateLong;

	public MinlePayLogInfo()
	{
	}

	public MinlePayLogInfo(String logId, String mchId, String reqSign, String type, String notifyUrl, String outTradeNo, String totalFee, byte status, long addDateLong, long updateDateLong)
	{
		this.logId = logId;
		this.mchId = mchId;
		this.reqSign = reqSign;
		this.type = type;
		this.notifyUrl = notifyUrl;
		this.outTradeNo = outTradeNo;
		this.totalFee = totalFee;
		this.status = status;
		this.addDateLong = addDateLong;
		this.updateDateLong = updateDateLong;
	}

	public MinlePayLogInfo(String logId, String mchId, String reqSign, String type, String notifyUrl, String backUrl, String cardType, String outTradeNo, String totalFee, String body,
		String errorCode, String errorMsg, String respSign, String outTradeNo1, String orderId, String totalFee1, String payUrl, String qrCode, byte status, long addDateLong, long updateDateLong)
	{
		this.logId = logId;
		this.mchId = mchId;
		this.reqSign = reqSign;
		this.type = type;
		this.notifyUrl = notifyUrl;
		this.backUrl = backUrl;
		this.cardType = cardType;
		this.outTradeNo = outTradeNo;
		this.totalFee = totalFee;
		this.body = body;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.respSign = respSign;
		this.outTradeNo1 = outTradeNo1;
		this.orderId = orderId;
		this.totalFee1 = totalFee1;
		this.payUrl = payUrl;
		this.qrCode = qrCode;
		this.status = status;
		this.addDateLong = addDateLong;
		this.updateDateLong = updateDateLong;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Column(name = "log_id", unique = true, nullable = false, length = 16)
	public String getLogId()
	{
		return this.logId;
	}

	public void setLogId(String logId)
	{
		this.logId = logId;
	}

	@Column(name = "mch_id", nullable = false, length = 16)
	public String getMchId()
	{
		return this.mchId;
	}

	public void setMchId(String mchId)
	{
		this.mchId = mchId;
	}

	@Column(name = "req_sign", nullable = false, length = 32)
	public String getReqSign()
	{
		return this.reqSign;
	}

	public void setReqSign(String reqSign)
	{
		this.reqSign = reqSign;
	}

	@Column(name = "type", nullable = false, length = 16)
	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@Column(name = "notify_url", nullable = false, length = 256)
	public String getNotifyUrl()
	{
		return this.notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}

	@Column(name = "back_url", length = 256)
	public String getBackUrl()
	{
		return this.backUrl;
	}

	public void setBackUrl(String backUrl)
	{
		this.backUrl = backUrl;
	}

	@Column(name = "card_type", length = 1)
	public String getCardType()
	{
		return this.cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}

	@Column(name = "out_trade_no", nullable = false, length = 32)
	public String getOutTradeNo()
	{
		return this.outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo)
	{
		this.outTradeNo = outTradeNo;
	}

	@Column(name = "total_fee", nullable = false, length = 8)
	public String getTotalFee()
	{
		return this.totalFee;
	}

	public void setTotalFee(String totalFee)
	{
		this.totalFee = totalFee;
	}

	@Column(name = "body", length = 128)
	public String getBody()
	{
		return this.body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	@Column(name = "error_code", length = 8)
	public String getErrorCode()
	{
		return this.errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	@Column(name = "error_msg", length = 512)
	public String getErrorMsg()
	{
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	@Column(name = "resp_sign", length = 32)
	public String getRespSign()
	{
		return this.respSign;
	}

	public void setRespSign(String respSign)
	{
		this.respSign = respSign;
	}

	@Column(name = "out_trade_no1", length = 32)
	public String getOutTradeNo1()
	{
		return this.outTradeNo1;
	}

	public void setOutTradeNo1(String outTradeNo1)
	{
		this.outTradeNo1 = outTradeNo1;
	}

	@Column(name = "order_id", length = 64)
	public String getOrderId()
	{
		return this.orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	@Column(name = "total_fee1", length = 8)
	public String getTotalFee1()
	{
		return this.totalFee1;
	}

	public void setTotalFee1(String totalFee1)
	{
		this.totalFee1 = totalFee1;
	}

	@Column(name = "pay_url", length = 256)
	public String getPayUrl()
	{
		return this.payUrl;
	}

	public void setPayUrl(String payUrl)
	{
		this.payUrl = payUrl;
	}

	@Column(name = "qr_code", length = 256)
	public String getQrCode()
	{
		return this.qrCode;
	}

	public void setQrCode(String qrCode)
	{
		this.qrCode = qrCode;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus()
	{
		return this.status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	@Column(name = "add_date_long", nullable = false)
	public long getAddDateLong()
	{
		return this.addDateLong;
	}

	public void setAddDateLong(long addDateLong)
	{
		this.addDateLong = addDateLong;
	}

	@Column(name = "update_date_long", nullable = false)
	public long getUpdateDateLong()
	{
		return this.updateDateLong;
	}

	public void setUpdateDateLong(long updateDateLong)
	{
		this.updateDateLong = updateDateLong;
	}
}
