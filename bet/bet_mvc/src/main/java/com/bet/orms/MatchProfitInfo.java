package com.bet.orms;
// Generated 2018-4-22 12:07:53 by Hibernate Tools 4.3.5.Final

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
 * MatchProfitInfo generated by hbm2java
 */
@Entity
@Table(name = "match_profit_info", uniqueConstraints = @UniqueConstraint(columnNames = "profit_id"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MatchProfitInfo implements java.io.Serializable
{
	private static final long serialVersionUID = -1299518311254457687L;
	private Integer id;
	private String profitId;
	private String phaseId;
	private String matchType;
	private byte score1;
	private byte score2;
	private int profitPercent;
	private long amount;
	private long saledAmount;
	private byte status;
	private long addDateLong;
	private long updateDateLong;

	public MatchProfitInfo()
	{
	}

	public MatchProfitInfo(String profitId, String phaseId, String matchType, byte score1, byte score2, int profitPercent, long amount, long saledAmount, byte status, long addDateLong,
		long updateDateLong)
	{
		this.profitId = profitId;
		this.phaseId = phaseId;
		this.matchType = matchType;
		this.score1 = score1;
		this.score2 = score2;
		this.profitPercent = profitPercent;
		this.amount = amount;
		this.saledAmount = saledAmount;
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

	@Column(name = "profit_id", unique = true, nullable = false, length = 16)
	public String getProfitId()
	{
		return this.profitId;
	}

	public void setProfitId(String profitId)
	{
		this.profitId = profitId;
	}

	@Column(name = "phase_id", nullable = false, length = 16)
	public String getPhaseId()
	{
		return this.phaseId;
	}

	public void setPhaseId(String phaseId)
	{
		this.phaseId = phaseId;
	}

	@Column(name = "match_type", nullable = false, length = 16)
	public String getMatchType()
	{
		return this.matchType;
	}

	public void setMatchType(String matchType)
	{
		this.matchType = matchType;
	}

	@Column(name = "score1", nullable = false)
	public byte getScore1()
	{
		return this.score1;
	}

	public void setScore1(byte score1)
	{
		this.score1 = score1;
	}

	@Column(name = "score2", nullable = false)
	public byte getScore2()
	{
		return this.score2;
	}

	public void setScore2(byte score2)
	{
		this.score2 = score2;
	}

	@Column(name = "profit_percent", nullable = false)
	public int getProfitPercent()
	{
		return this.profitPercent;
	}

	public void setProfitPercent(int profitPercent)
	{
		this.profitPercent = profitPercent;
	}

	@Column(name = "amount", nullable = false)
	public long getAmount()
	{
		return this.amount;
	}

	public void setAmount(long amount)
	{
		this.amount = amount;
	}

	@Column(name = "saled_amount", nullable = false)
	public long getSaledAmount()
	{
		return this.saledAmount;
	}

	public void setSaledAmount(long saledAmount)
	{
		this.saledAmount = saledAmount;
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
