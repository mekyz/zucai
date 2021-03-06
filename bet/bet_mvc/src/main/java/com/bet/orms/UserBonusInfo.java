package com.bet.orms;
// Generated 2018-4-22 11:55:49 by Hibernate Tools 4.3.5.Final

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
 * UserBonusInfo generated by hbm2java
 */
@Entity
@Table(name = "user_bonus_info", uniqueConstraints = @UniqueConstraint(columnNames = "bonus_id"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserBonusInfo implements java.io.Serializable
{
	private static final long serialVersionUID = -4014956523981539973L;
	private Integer id;
	private String bonusId;
	private String userId;
	private String phaseId;
	private int finalBonusMoney;
	private int halfBonusMoney;
	private int countBonusMoney;
	private int shareBonusMoney;
	private int agentBonusMoney;
	private int sameLevel1BonusMoney;
	private int sameLevel2BonusMoney;
	private int benefitBonusMoney;
	private int financialMoney;
	private int fee;
	private int userMoney;
	private byte status;
	private long addDateLong;
	private long updateDateLong;

	public UserBonusInfo()
	{
	}

	public UserBonusInfo(String bonusId, String userId, String phaseId, int finalBonusMoney, int halfBonusMoney, int countBonusMoney, int shareBonusMoney, int agentBonusMoney,
		int sameLevel1BonusMoney, int sameLevel2BonusMoney, int benefitBonusMoney, int financialMoney, int fee, int userMoney, byte status, long addDateLong, long updateDateLong)
	{
		this.bonusId = bonusId;
		this.userId = userId;
		this.phaseId = phaseId;
		this.finalBonusMoney = finalBonusMoney;
		this.halfBonusMoney = halfBonusMoney;
		this.countBonusMoney = countBonusMoney;
		this.shareBonusMoney = shareBonusMoney;
		this.agentBonusMoney = agentBonusMoney;
		this.sameLevel1BonusMoney = sameLevel1BonusMoney;
		this.sameLevel2BonusMoney = sameLevel2BonusMoney;
		this.benefitBonusMoney = benefitBonusMoney;
		this.financialMoney = financialMoney;
		this.fee = fee;
		this.userMoney = userMoney;
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

	@Column(name = "bonus_id", unique = true, nullable = false, length = 16)
	public String getBonusId()
	{
		return this.bonusId;
	}

	public void setBonusId(String bonusId)
	{
		this.bonusId = bonusId;
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

	@Column(name = "phase_id", nullable = false, length = 16)
	public String getPhaseId()
	{
		return this.phaseId;
	}

	public void setPhaseId(String phaseId)
	{
		this.phaseId = phaseId;
	}

	@Column(name = "final_bonus_money", nullable = false)
	public int getFinalBonusMoney()
	{
		return this.finalBonusMoney;
	}

	public void setFinalBonusMoney(int finalBonusMoney)
	{
		this.finalBonusMoney = finalBonusMoney;
	}

	@Column(name = "half_bonus_money", nullable = false)
	public int getHalfBonusMoney()
	{
		return this.halfBonusMoney;
	}

	public void setHalfBonusMoney(int halfBonusMoney)
	{
		this.halfBonusMoney = halfBonusMoney;
	}

	@Column(name = "count_bonus_money", nullable = false)
	public int getCountBonusMoney()
	{
		return this.countBonusMoney;
	}

	public void setCountBonusMoney(int countBonusMoney)
	{
		this.countBonusMoney = countBonusMoney;
	}

	@Column(name = "share_bonus_money", nullable = false)
	public int getShareBonusMoney()
	{
		return this.shareBonusMoney;
	}

	public void setShareBonusMoney(int shareBonusMoney)
	{
		this.shareBonusMoney = shareBonusMoney;
	}

	@Column(name = "agent_bonus_money", nullable = false)
	public int getAgentBonusMoney()
	{
		return this.agentBonusMoney;
	}

	public void setAgentBonusMoney(int agentBonusMoney)
	{
		this.agentBonusMoney = agentBonusMoney;
	}

	@Column(name = "same_level1_bonus_money", nullable = false)
	public int getSameLevel1BonusMoney()
	{
		return this.sameLevel1BonusMoney;
	}

	public void setSameLevel1BonusMoney(int sameLevel1BonusMoney)
	{
		this.sameLevel1BonusMoney = sameLevel1BonusMoney;
	}

	@Column(name = "same_level2_bonus_money", nullable = false)
	public int getSameLevel2BonusMoney()
	{
		return this.sameLevel2BonusMoney;
	}

	public void setSameLevel2BonusMoney(int sameLevel2BonusMoney)
	{
		this.sameLevel2BonusMoney = sameLevel2BonusMoney;
	}

	@Column(name = "benefit_bonus_money", nullable = false)
	public int getBenefitBonusMoney()
	{
		return this.benefitBonusMoney;
	}

	public void setBenefitBonusMoney(int benefitBonusMoney)
	{
		this.benefitBonusMoney = benefitBonusMoney;
	}

	@Column(name = "financial_money", nullable = false)
	public int getFinancialMoney()
	{
		return this.financialMoney;
	}

	public void setFinancialMoney(int financialMoney)
	{
		this.financialMoney = financialMoney;
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

	@Column(name = "user_money", nullable = false)
	public int getUserMoney()
	{
		return this.userMoney;
	}

	public void setUserMoney(int userMoney)
	{
		this.userMoney = userMoney;
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
