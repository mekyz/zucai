package com.bet.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户余额变动记录类型
 * 
 * @author libit
 */
public enum UserBalanceLogType
{
	WITHDRAW("withdraw", "彩金钱包提现"), GIVE_POINT("give_point", "注册赠送"), GIVE_POINT_INVALIDE("gp_invalide", "体验彩金已到有效期"), RECHARGE("recharge", "用户充值"), EXCHANGE_OUT("exchange_out", "用户转出"), EXCHANGE_IN(
		"exchange_in", "用户转入"), BET("bet", "用户下注"), BET_RETURN("bet_return", "用户撤回下注"), WIN_BET("win_bet", "用户下注中奖"), WIN_BET_RETURN("win_bet_rt", "用户下注中奖返还本金"), TEAM_SHARE("team_share",
			"团队分享佣金"), TEAM_AGENT("team_agent", "团队代理佣金"), TEAM_AGENT_SAME_LEVEL("team_agent_sl", "团队代理佣金平级奖"), TEAM_BENFIT("team_benfit", "团队福利奖"), TEAM_BENFIT_SAME_LEVEL("team_benfit_sl",
				"团队福利平级奖"), TEAM_LEADER5_BENFIT("team_l5_benfit", "VIP领袖奖"), TEAM_LEADER6_BENFIT("team_l6_benfit", "钻石领袖奖"), TEAM_LEADER7_BENFIT("team_l7_benfit", "超级领袖奖");
	private String type;
	private String desc;

	private UserBalanceLogType(String type, String desc)
	{
		this.type = type;
		this.desc = desc;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static String getTypeDesc(String type)
	{
		UserBalanceLogType[] list = UserBalanceLogType.values();
		for (UserBalanceLogType item : list)
		{
			if (item.getType().equals(type))
			{
				return item.getDesc();
			}
		}
		return "";
	}

	public static String getLogString(String logType)
	{
		UserBalanceLogType[] list = UserBalanceLogType.values();
		for (UserBalanceLogType item : list)
		{
			if (logType.equals(item.type))
			{
				return item.desc;
			}
		}
		return "";
	}

	public static Map<String, String> getMap()
	{
		UserBalanceLogType[] list = UserBalanceLogType.values();
		Map<String, String> map = new HashMap<>();
		for (UserBalanceLogType item : list)
		{
			map.put(item.getType(), item.getDesc());
		}
		return map;
	}
}
