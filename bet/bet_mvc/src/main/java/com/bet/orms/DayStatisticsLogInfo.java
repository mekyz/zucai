package com.bet.orms;
// Generated 2018-5-4 10:33:46 by Hibernate Tools 4.3.5.Final

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
 * DayStatisticsLogInfo generated by hbm2java
 */
@Entity
@Table(name = "day_statistics_log_info", uniqueConstraints = @UniqueConstraint(columnNames = "day_id"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class DayStatisticsLogInfo implements java.io.Serializable
{
	private static final long serialVersionUID = 8434584076457258197L;
	private Integer id;
	private String dayId;
	private long userBetMoney;
	private long userRechargeMoney;
	private long userWithdrawMoney;
	private long finalBetMoney;
	private long finalBonusMoney;
	private long finalWinMoney;
	private long halfBetMoney;
	private long halfBonusMoney;
	private long halfWinMoney;
	private long countBetMoney;
	private long countBonusMoney;
	private long countWinMoney;
	private long shareBonusMoney;
	private long agentBonusMoney;
	private long sameLevel1BonusMoney;
	private long sameLevel2BonusMoney;
	private long benefitBonusMoney;
	private long teamMoney;
	private long companyMoney;
	private byte status;
	private long addDateLong;
	private long updateDateLong;

	public DayStatisticsLogInfo()
	{
	}

	public DayStatisticsLogInfo(String dayId, long userBetMoney, long userRechargeMoney, long userWithdrawMoney, long finalBetMoney, long finalBonusMoney, long finalWinMoney, long halfBetMoney,
		long halfBonusMoney, long halfWinMoney, long countBetMoney, long countBonusMoney, long countWinMoney, long shareBonusMoney, long agentBonusMoney, long sameLevel1BonusMoney,
		long sameLevel2BonusMoney, long benefitBonusMoney, long teamMoney, long companyMoney, byte status, long addDateLong, long updateDateLong)
	{
		this.dayId = dayId;
		this.userBetMoney = userBetMoney;
		this.userRechargeMoney = userRechargeMoney;
		this.userWithdrawMoney = userWithdrawMoney;
		this.finalBetMoney = finalBetMoney;
		this.finalBonusMoney = finalBonusMoney;
		this.finalWinMoney = finalWinMoney;
		this.halfBetMoney = halfBetMoney;
		this.halfBonusMoney = halfBonusMoney;
		this.halfWinMoney = halfWinMoney;
		this.countBetMoney = countBetMoney;
		this.countBonusMoney = countBonusMoney;
		this.countWinMoney = countWinMoney;
		this.shareBonusMoney = shareBonusMoney;
		this.agentBonusMoney = agentBonusMoney;
		this.sameLevel1BonusMoney = sameLevel1BonusMoney;
		this.sameLevel2BonusMoney = sameLevel2BonusMoney;
		this.benefitBonusMoney = benefitBonusMoney;
		this.teamMoney = teamMoney;
		this.companyMoney = companyMoney;
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

	@Column(name = "day_id", unique = true, nullable = false, length = 16)
	public String getDayId()
	{
		return this.dayId;
	}

	public void setDayId(String dayId)
	{
		this.dayId = dayId;
	}

	@Column(name = "user_bet_money", nullable = false)
	public long getUserBetMoney()
	{
		return this.userBetMoney;
	}

	public void setUserBetMoney(long userBetMoney)
	{
		this.userBetMoney = userBetMoney;
	}

	@Column(name = "user_recharge_money", nullable = false)
	public long getUserRechargeMoney()
	{
		return this.userRechargeMoney;
	}

	public void setUserRechargeMoney(long userRechargeMoney)
	{
		this.userRechargeMoney = userRechargeMoney;
	}

	@Column(name = "user_withdraw_money", nullable = false)
	public long getUserWithdrawMoney()
	{
		return this.userWithdrawMoney;
	}

	public void setUserWithdrawMoney(long userWithdrawMoney)
	{
		this.userWithdrawMoney = userWithdrawMoney;
	}

	@Column(name = "final_bet_money", nullable = false)
	public long getFinalBetMoney()
	{
		return this.finalBetMoney;
	}

	public void setFinalBetMoney(long finalBetMoney)
	{
		this.finalBetMoney = finalBetMoney;
	}

	@Column(name = "final_bonus_money", nullable = false)
	public long getFinalBonusMoney()
	{
		return this.finalBonusMoney;
	}

	public void setFinalBonusMoney(long finalBonusMoney)
	{
		this.finalBonusMoney = finalBonusMoney;
	}

	@Column(name = "final_win_money", nullable = false)
	public long getFinalWinMoney()
	{
		return this.finalWinMoney;
	}

	public void setFinalWinMoney(long finalWinMoney)
	{
		this.finalWinMoney = finalWinMoney;
	}

	@Column(name = "half_bet_money", nullable = false)
	public long getHalfBetMoney()
	{
		return this.halfBetMoney;
	}

	public void setHalfBetMoney(long halfBetMoney)
	{
		this.halfBetMoney = halfBetMoney;
	}

	@Column(name = "half_bonus_money", nullable = false)
	public long getHalfBonusMoney()
	{
		return this.halfBonusMoney;
	}

	public void setHalfBonusMoney(long halfBonusMoney)
	{
		this.halfBonusMoney = halfBonusMoney;
	}

	@Column(name = "half_win_money", nullable = false)
	public long getHalfWinMoney()
	{
		return this.halfWinMoney;
	}

	public void setHalfWinMoney(long halfWinMoney)
	{
		this.halfWinMoney = halfWinMoney;
	}

	@Column(name = "count_bet_money", nullable = false)
	public long getCountBetMoney()
	{
		return this.countBetMoney;
	}

	public void setCountBetMoney(long countBetMoney)
	{
		this.countBetMoney = countBetMoney;
	}

	@Column(name = "count_bonus_money", nullable = false)
	public long getCountBonusMoney()
	{
		return this.countBonusMoney;
	}

	public void setCountBonusMoney(long countBonusMoney)
	{
		this.countBonusMoney = countBonusMoney;
	}

	@Column(name = "count_win_money", nullable = false)
	public long getCountWinMoney()
	{
		return this.countWinMoney;
	}

	public void setCountWinMoney(long countWinMoney)
	{
		this.countWinMoney = countWinMoney;
	}

	@Column(name = "share_bonus_money", nullable = false)
	public long getShareBonusMoney()
	{
		return this.shareBonusMoney;
	}

	public void setShareBonusMoney(long shareBonusMoney)
	{
		this.shareBonusMoney = shareBonusMoney;
	}

	@Column(name = "agent_bonus_money", nullable = false)
	public long getAgentBonusMoney()
	{
		return this.agentBonusMoney;
	}

	public void setAgentBonusMoney(long agentBonusMoney)
	{
		this.agentBonusMoney = agentBonusMoney;
	}

	@Column(name = "same_level1_bonus_money", nullable = false)
	public long getSameLevel1BonusMoney()
	{
		return this.sameLevel1BonusMoney;
	}

	public void setSameLevel1BonusMoney(long sameLevel1BonusMoney)
	{
		this.sameLevel1BonusMoney = sameLevel1BonusMoney;
	}

	@Column(name = "same_level2_bonus_money", nullable = false)
	public long getSameLevel2BonusMoney()
	{
		return this.sameLevel2BonusMoney;
	}

	public void setSameLevel2BonusMoney(long sameLevel2BonusMoney)
	{
		this.sameLevel2BonusMoney = sameLevel2BonusMoney;
	}

	@Column(name = "benefit_bonus_money", nullable = false)
	public long getBenefitBonusMoney()
	{
		return this.benefitBonusMoney;
	}

	public void setBenefitBonusMoney(long benefitBonusMoney)
	{
		this.benefitBonusMoney = benefitBonusMoney;
	}

	@Column(name = "team_money", nullable = false)
	public long getTeamMoney()
	{
		return this.teamMoney;
	}

	public void setTeamMoney(long teamMoney)
	{
		this.teamMoney = teamMoney;
	}

	@Column(name = "company_money", nullable = false)
	public long getCompanyMoney()
	{
		return this.companyMoney;
	}

	public void setCompanyMoney(long companyMoney)
	{
		this.companyMoney = companyMoney;
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
