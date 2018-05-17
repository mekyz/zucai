package com.bet.models;

import com.google.gson.annotations.SerializedName;

public class UserTeamProfit
{
	@SerializedName("userId")
	private String userId;
	@SerializedName("finalBonusMoney")
	private long finalBonusMoney;
	@SerializedName("halfBonusMoney")
	private long halfBonusMoney;
	@SerializedName("countBonusMoney")
	private long countBonusMoney;
	@SerializedName("shareBonusMoney")
	private long shareBonusMoney;
	@SerializedName("agentBonusMoney")
	private long agentBonusMoney;
	@SerializedName("sameLevel1BonusMoney")
	private long sameLevel1BonusMoney;
	@SerializedName("sameLevel2BonusMoney")
	private long sameLevel2BonusMoney;
	@SerializedName("benefitBonusMoney")
	private long benefitBonusMoney;

	public UserTeamProfit()
	{
		super();
	}

	public UserTeamProfit(String userId, long finalBonusMoney, long halfBonusMoney, long countBonusMoney, long shareBonusMoney, long agentBonusMoney, long sameLevel1BonusMoney,
		long sameLevel2BonusMoney, long benefitBonusMoney)
	{
		super();
		this.userId = userId;
		this.finalBonusMoney = finalBonusMoney;
		this.halfBonusMoney = halfBonusMoney;
		this.countBonusMoney = countBonusMoney;
		this.shareBonusMoney = shareBonusMoney;
		this.agentBonusMoney = agentBonusMoney;
		this.sameLevel1BonusMoney = sameLevel1BonusMoney;
		this.sameLevel2BonusMoney = sameLevel2BonusMoney;
		this.benefitBonusMoney = benefitBonusMoney;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public long getFinalBonusMoney()
	{
		return finalBonusMoney;
	}

	public void setFinalBonusMoney(long finalBonusMoney)
	{
		this.finalBonusMoney = finalBonusMoney;
	}

	public long getHalfBonusMoney()
	{
		return halfBonusMoney;
	}

	public void setHalfBonusMoney(long halfBonusMoney)
	{
		this.halfBonusMoney = halfBonusMoney;
	}

	public long getCountBonusMoney()
	{
		return countBonusMoney;
	}

	public void setCountBonusMoney(long countBonusMoney)
	{
		this.countBonusMoney = countBonusMoney;
	}

	public long getShareBonusMoney()
	{
		return shareBonusMoney;
	}

	public void setShareBonusMoney(long shareBonusMoney)
	{
		this.shareBonusMoney = shareBonusMoney;
	}

	public long getAgentBonusMoney()
	{
		return agentBonusMoney;
	}

	public void setAgentBonusMoney(long agentBonusMoney)
	{
		this.agentBonusMoney = agentBonusMoney;
	}

	public long getSameLevel1BonusMoney()
	{
		return sameLevel1BonusMoney;
	}

	public void setSameLevel1BonusMoney(long sameLevel1BonusMoney)
	{
		this.sameLevel1BonusMoney = sameLevel1BonusMoney;
	}

	public long getSameLevel2BonusMoney()
	{
		return sameLevel2BonusMoney;
	}

	public void setSameLevel2BonusMoney(long sameLevel2BonusMoney)
	{
		this.sameLevel2BonusMoney = sameLevel2BonusMoney;
	}

	public long getBenefitBonusMoney()
	{
		return benefitBonusMoney;
	}

	public void setBenefitBonusMoney(long benefitBonusMoney)
	{
		this.benefitBonusMoney = benefitBonusMoney;
	}
}
