package com.bet.orms;
// Generated 2018-4-22 12:06:22 by Hibernate Tools 4.3.5.Final

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
 * MatchProfitTemplateInfo generated by hbm2java
 */
@Entity
@Table(name = "match_profit_template_info", uniqueConstraints = @UniqueConstraint(columnNames = "template_id"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MatchProfitTemplateInfo implements java.io.Serializable
{
	private static final long serialVersionUID = -6343818404370242178L;
	private Integer id;
	private String templateId;
	private String matchType;
	private byte score1;
	private byte score2;
	private int profitPercent;
	private long amount;
	private byte status;
	private long addDateLong;
	private long updateDateLong;

	public MatchProfitTemplateInfo()
	{
	}

	public MatchProfitTemplateInfo(String templateId, String matchType, byte score1, byte score2, int profitPercent, long amount, byte status, long addDateLong, long updateDateLong)
	{
		this.templateId = templateId;
		this.matchType = matchType;
		this.score1 = score1;
		this.score2 = score2;
		this.profitPercent = profitPercent;
		this.amount = amount;
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

	@Column(name = "template_id", unique = true, nullable = false, length = 16)
	public String getTemplateId()
	{
		return this.templateId;
	}

	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
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
