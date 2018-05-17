package com.bet.utils;

public class SessionManage
{
	private static SessionManage instance = null;

	public static SessionManage getInstance()
	{
		if (instance == null)
		{
			instance = new SessionManage();
		}
		return instance;
	}

	private SessionManage()
	{
	}

	// 用户Session
	public enum SessionUser
	{
		USER_INFO("session_user_info"), RANDOM_CODE("session_user_random_code"), USER_PRE_URL("session_pre_user_url"), REFERRER_ID("session_referrer_id"), PASSWORD2_AUTH("session_password2_auth");
		private String name;

		SessionUser(String name)
		{
			this.setName(name);
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	};

	// 管理员Session
	public enum SessionAdmin
	{
		ADMIN_INFO("session_admin_info"), ADMIN_RANDOM_CODE("session_admin_random_code"), ADMIN_PRE_URL("session_pre_admin_url");
		private String name;

		SessionAdmin(String name)
		{
			this.setName(name);
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	};
}
