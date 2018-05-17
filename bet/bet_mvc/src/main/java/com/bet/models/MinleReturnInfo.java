package com.bet.models;

import com.google.gson.annotations.SerializedName;

public class MinleReturnInfo
{
	@SerializedName("error_code")
	private String error_code;
	@SerializedName("error_msg")
	private String error_msg;
	@SerializedName("out_trade_no")
	private String out_trade_no;
	@SerializedName("order_id")
	private String order_id;
	@SerializedName("total_fee")
	private String total_fee;
	@SerializedName("pay_url")
	private String pay_url;
	@SerializedName("qr_code")
	private String qr_code;
	@SerializedName("sign")
	private String sign;

	public String getError_code()
	{
		return error_code;
	}

	public void setError_code(String error_code)
	{
		this.error_code = error_code;
	}

	public String getError_msg()
	{
		return error_msg;
	}

	public void setError_msg(String error_msg)
	{
		this.error_msg = error_msg;
	}

	public String getOut_trade_no()
	{
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no)
	{
		this.out_trade_no = out_trade_no;
	}

	public String getOrder_id()
	{
		return order_id;
	}

	public void setOrder_id(String order_id)
	{
		this.order_id = order_id;
	}

	public String getTotal_fee()
	{
		return total_fee;
	}

	public void setTotal_fee(String total_fee)
	{
		this.total_fee = total_fee;
	}

	public String getPay_url()
	{
		return pay_url;
	}

	public void setPay_url(String pay_url)
	{
		this.pay_url = pay_url;
	}

	public String getQr_code()
	{
		return qr_code;
	}

	public void setQr_code(String qr_code)
	{
		this.qr_code = qr_code;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}
}
