package com.bet.orms;
// Generated 2018-5-2 10:38:26 by Hibernate Tools 4.3.5.Final

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
 * UserBetReturnInfo generated by hbm2java
 */
@Entity
@Table(name = "user_bet_return_info", uniqueConstraints = { @UniqueConstraint(columnNames = "bet_id"), @UniqueConstraint(columnNames = "log_id") })
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserBetReturnInfo implements java.io.Serializable
{
	private static final long serialVersionUID = -1845063184741883560L;
	private Integer id;
	private String logId;
	private String betId;
	private String userId;
	private String profitId;
	private String phaseId;
	private String matchName;
	private String matchType;
	private Byte score1;
	private Byte score2;
	private long money;
	private byte moneyUnit;
	private String remark;
	private byte status;
	private long addDateLong;
	private long updateDateLong;

	public UserBetReturnInfo()
	{
	}

	public UserBetReturnInfo(String logId, String betId, String userId, String profitId, long money, byte moneyUnit, byte status, long addDateLong, long updateDateLong)
	{
		this.logId = logId;
		this.betId = betId;
		this.userId = userId;
		this.profitId = profitId;
		this.money = money;
		this.moneyUnit = moneyUnit;
		this.status = status;
		this.addDateLong = addDateLong;
		this.updateDateLong = updateDateLong;
	}

	public UserBetReturnInfo(String logId, String betId, String userId, String profitId, String phaseId, String matchName, String matchType, Byte score1, Byte score2, long money, byte moneyUnit,
		String remark, byte status, long addDateLong, long updateDateLong)
	{
		this.logId = logId;
		this.betId = betId;
		this.userId = userId;
		this.profitId = profitId;
		this.phaseId = phaseId;
		this.matchName = matchName;
		this.matchType = matchType;
		this.score1 = score1;
		this.score2 = score2;
		this.money = money;
		this.moneyUnit = moneyUnit;
		this.remark = remark;
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

	@Column(name = "bet_id", unique = true, nullable = false, length = 16)
	public String getBetId()
	{
		return this.betId;
	}

	public void setBetId(String betId)
	{
		this.betId = betId;
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

	@Column(name = "profit_id", nullable = false, length = 16)
	public String getProfitId()
	{
		return this.profitId;
	}

	public void setProfitId(String profitId)
	{
		this.profitId = profitId;
	}

	@Column(name = "phase_id", length = 16)
	public String getPhaseId()
	{
		return this.phaseId;
	}

	public void setPhaseId(String phaseId)
	{
		this.phaseId = phaseId;
	}

	@Column(name = "match_name", length = 64)
	public String getMatchName()
	{
		return this.matchName;
	}

	public void setMatchName(String matchName)
	{
		this.matchName = matchName;
	}

	@Column(name = "match_type", length = 16)
	public String getMatchType()
	{
		return this.matchType;
	}

	public void setMatchType(String matchType)
	{
		this.matchType = matchType;
	}

	@Column(name = "score1")
	public Byte getScore1()
	{
		return this.score1;
	}

	public void setScore1(Byte score1)
	{
		this.score1 = score1;
	}

	@Column(name = "score2")
	public Byte getScore2()
	{
		return this.score2;
	}

	public void setScore2(Byte score2)
	{
		this.score2 = score2;
	}

	@Column(name = "money", nullable = false)
	public long getMoney()
	{
		return this.money;
	}

	public void setMoney(long money)
	{
		this.money = money;
	}

	@Column(name = "money_unit", nullable = false)
	public byte getMoneyUnit()
	{
		return this.moneyUnit;
	}

	public void setMoneyUnit(byte moneyUnit)
	{
		this.moneyUnit = moneyUnit;
	}

	@Column(name = "remark", length = 512)
	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
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
