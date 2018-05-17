package com.bet.utils;

import java.util.HashMap;

import com.lrcall.common.utils.ConstValues;

public class BetConstValues extends ConstValues
{
	public static final String DEFAULT_ADMIN_ID = "admin";
	public static final String API_LOW = "您的版本过低，请升级到最新版！";
	public static final String PROJECT_NAME = "DM SUPER CLUB";
	public static final String CONFIG_DEFAULT_AGENT = "";
	public static final String CONFIG_TEAM_AGENT_RATE = "1";
	public static final String CONFIG_TEAM_BENEFIT_RATE = "2";
	// 配置项
	public static final String CONFIG_SERVER_URL = "server_url";// 服务器地址
	public static final String CONFIG_KEFU_NUMBER = "kefu_number";// 客服电话
	public static final String CONFIG_OFFICAL_URL = "offical_url";// 官方网址
	public static final String CONFIG_RESOURCE_URL = "resource_url";// 资源服务器地址。
	public static final String CONFIG_MAIN_URL = "main_url";// 主服务器地址。
	public static final String CONFIG_SHARE_TEXT = "share_text";// 分享文本
	public static final String CONFIG_API_VERSION = "api_version";// 客户端API版本
	public static final String CONFIG_REGISTER_GIVE_POINT = "register_give_point";// 注册赠送积分
	public static final String CONFIG_REGISTER_NEED_REFERRER = "register_need_referrer";// 注册是否需要推荐人
	public static final String CONFIG_MIN_WITHDRAW = "min_withdraw";// 最低提现金额
	public static final String CONFIG_WITHDRAW_FEE = "withdraw_fee";// 提现费率，范围为0-100
	public static final String CONFIG_MIN_RECHARGE = "min_recharge";// 最低充值金额，单位为分
	public static final String CONFIG_MIN_EXCHANGE = "min_exchange";// 最低转账金额，单位为分
	public static final String CONFIG_MIN_BET = "min_bet";// 最低下注金额，单位为分
	public static final String CONFIG_REGISTER_GIVE_POINT_VALIDATE = "register_give_point_validate";// 注册赠送积分有效天数
	public static final String CONFIG_BET_FEE = "bet_fee";// 中奖费率，范围为0-100。
	public static final String CONFIG_SHARE_PROFT_RATE = "share_profit_rate";// 分享佣金比率，直推会员返利，范围为0-100。
	public static final String CONFIG_CHECK_HOUR = "check_hour";// 检查团队奖间隔
	public static final String CONFIG_SMS_CODE_NEED_CODE = "sms_need_code";// 短信验证码是否需要图形验证码
	public static final String CONFIG_STATISTICS_CHECK_DAY = "statistics_check_day";// 检查最近多少天的数据，因为手工开奖，所以不能保证每天实时统计数据
	public static final String CONFIG_ADMIN_NUMBER = "admin_number";// 管理员手机号码，用于接收用户充值和提现的通知
	public static final String CONFIG_ADMIN_WHITE_LIST = "admin_white_list";// 管理员白名单IP列表，多个IP之间用英文逗号(,)分隔。
	public static final String CONFIG_ADMIN_MAINTAIN = "admin_maintain";// 是否进行系统维护。
	public static final String CONFIG_CLOSE_EXCHANGE = "close_exchange";// 是否关闭用户转账。
	public static final String CONFIG_LIMIT_GIVE_POINT = "limit_give_point";// 是否限制体验金盈利。
	// 默认参数
	public static final HashMap<String, String> CONFIG_STRING_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = 1646234267547321L;
		{
			put(CONFIG_SERVER_URL, "http://127.0.0.1/bet/");
			put(CONFIG_KEFU_NUMBER, "10086");
			put(CONFIG_OFFICAL_URL, "http://127.0.0.1/bet/");
			put(CONFIG_RESOURCE_URL, "http://127.0.0.1/bet/");
			put(CONFIG_MAIN_URL, "http://127.0.0.1/bet/");
			put(CONFIG_SHARE_TEXT, "");
			put(CONFIG_ADMIN_NUMBER, "");
			put(CONFIG_ADMIN_WHITE_LIST, "");
		}
	};
	public static final HashMap<String, Double> CONFIG_PERCENT_DOUBLE_MAP = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1646234267547321L;
		{
		}
	};
	public static final HashMap<String, Double> CONFIG_DOUBLE_MAP = new HashMap<String, Double>() {
		private static final long serialVersionUID = 3351158241409552378L;
		{
		}
	};
	public static final HashMap<String, Boolean> CONFIG_BOOLEAN_MAP = new HashMap<String, Boolean>() {
		private static final long serialVersionUID = 3284750932340181327L;
		{
			put(CONFIG_REGISTER_NEED_REFERRER, false);
			put(CONFIG_SMS_CODE_NEED_CODE, true);
			put(CONFIG_ADMIN_MAINTAIN, false);
			put(CONFIG_CLOSE_EXCHANGE, false);
			put(CONFIG_LIMIT_GIVE_POINT, false);
		}
	};
	public static final HashMap<String, Integer> CONFIG_INTEGER_MAP = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 4204385089397171324L;
		{
			put(CONFIG_API_VERSION, 1);
			put(CONFIG_REGISTER_GIVE_POINT, 0);
			put(CONFIG_MIN_WITHDRAW, 5000);
			put(CONFIG_WITHDRAW_FEE, 1);
			put(CONFIG_MIN_RECHARGE, 5000);
			put(CONFIG_MIN_EXCHANGE, 5000);
			put(CONFIG_REGISTER_GIVE_POINT_VALIDATE, 30);
			put(CONFIG_BET_FEE, 5);
			put(CONFIG_SHARE_PROFT_RATE, 25);
			put(CONFIG_CHECK_HOUR, 240);
			put(CONFIG_STATISTICS_CHECK_DAY, 7);
			put(CONFIG_MIN_BET, 5000);
		}
	};
}
