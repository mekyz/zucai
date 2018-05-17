package com.bet.models;

import com.google.gson.annotations.SerializedName;

public class MinleNotifyInfo
{
	@SerializedName("error_code")
	private String error_code;
	@SerializedName("error_msg")
	private String error_msg;
	@SerializedName("sign")
	private String sign;
	@SerializedName("out_trade_no")
	private String out_trade_no;
	@SerializedName("order_id")
	private String order_id;
	@SerializedName("pay_status")
	private String pay_status;
	@SerializedName("total_fee")
	private String total_fee;
	@SerializedName("body")
	private String body;

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

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
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

	public String getPay_status()
	{
		return pay_status;
	}

	public void setPay_status(String pay_status)
	{
		this.pay_status = pay_status;
	}

	public String getTotal_fee()
	{
		return total_fee;
	}

	public void setTotal_fee(String total_fee)
	{
		this.total_fee = total_fee;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}
}
