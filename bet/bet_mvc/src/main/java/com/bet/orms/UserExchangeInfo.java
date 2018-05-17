package com.bet.orms;
// Generated 2018-4-22 11:58:09 by Hibernate Tools 4.3.5.Final

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
 * UserExchangeInfo generated by hbm2java
 */
@Entity
@Table(name = "user_exchange_info", uniqueConstraints = @UniqueConstraint(columnNames = "exchange_id"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserExchangeInfo implements java.io.Serializable
{
	private static final long serialVersionUID = -1894096329172845220L;
	private Integer id;
	private String exchangeId;
	private String userId;
	private String receiveUserId;
	private int money;
	private int userMoney;
	private int fee;
	private String payRemark;
	private byte status;
	private Long payDateLong;
	private long addDateLong;
	private long updateDateLong;

	public UserExchangeInfo()
	{
	}

	public UserExchangeInfo(String exchangeId, String userId, String receiveUserId, int money, int userMoney, int fee, byte status, long addDateLong, long updateDateLong)
	{
		this.exchangeId = exchangeId;
		this.userId = userId;
		this.receiveUserId = receiveUserId;
		this.money = money;
		this.userMoney = userMoney;
		this.fee = fee;
		this.status = status;
		this.addDateLong = addDateLong;
		this.updateDateLong = updateDateLong;
	}

	public UserExchangeInfo(String exchangeId, String userId, String receiveUserId, int money, int userMoney, int fee, String payRemark, byte status, Long payDateLong, long addDateLong,
		long updateDateLong)
	{
		this.exchangeId = exchangeId;
		this.userId = userId;
		this.receiveUserId = receiveUserId;
		this.money = money;
		this.userMoney = userMoney;
		this.fee = fee;
		this.payRemark = payRemark;
		this.status = status;
		this.payDateLong = payDateLong;
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

	@Column(name = "exchange_id", unique = true, nullable = false, length = 16)
	public String getExchangeId()
	{
		return this.exchangeId;
	}

	public void setExchangeId(String exchangeId)
	{
		this.exchangeId = exchangeId;
	}

	@Column(name = "user_id", nullable = false, length = 16)
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	@Column(name = "receive_user_id", nullable = false, length = 16)
	public String getReceiveUserId()
	{
		return this.receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId)
	{
		this.receiveUserId = receiveUserId;
	}

	@Column(name = "money", nullable = false)
	public int getMoney()
	{
		return this.money;
	}

	public void setMoney(int money)
	{
		this.money = money;
	}

	@Column(name = "user_money", nullable = false)
	public int getUserMoney()
	{
		return this.userMoney;
	}

	public void setUserMoney(int userMoney)
	{
		this.userMoney = userMoney;
	}

	@Column(name = "fee", nullable = false)
	public int getFee()
	{
		return this.fee;
	}

	public void setFee(int fee)
	{
		this.fee = fee;
	}

	@Column(name = "pay_remark", length = 256)
	public String getPayRemark()
	{
		return this.payRemark;
	}

	public void setPayRemark(String payRemark)
	{
		this.payRemark = payRemark;
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

	@Column(name = "pay_date_long")
	public Long getPayDateLong()
	{
		return this.payDateLong;
	}

	public void setPayDateLong(Long payDateLong)
	{
		this.payDateLong = payDateLong;
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
