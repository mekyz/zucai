package com.bet.models;

import java.io.Serializable;

public class PayTypeInfo implements Serializable
{
	private static final long serialVersionUID = -7404611634463340895L;
	private String id;// ID
	private String userId;// 用户ID
	private String payType;// 需付款的类型
	private int price;// 付款金额,单位:分
	private String subject;// 标题
	private String comment;// 备注信息,可以是订单号

	public PayTypeInfo()
	{
	}

	public PayTypeInfo(String id, String userId, String payType, int price, String subject, String comment)
	{
		super();
		this.id = id;
		this.userId = userId;
		this.payType = payType;
		this.price = price;
		this.subject = subject;
		this.comment = comment;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getPayType()
	{
		return payType;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
