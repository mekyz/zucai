package com.bet.orms;
// Generated 2018-3-29 21:47:22 by Hibernate Tools 4.3.5.Final

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
 * AdviceInfo generated by hbm2java
 */
@Entity
@Table(name = "advice_info", uniqueConstraints = @UniqueConstraint(columnNames = "advice_id"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AdviceInfo implements java.io.Serializable
{
	private static final long serialVersionUID = -8104999756651806297L;
	private Integer id;
	private String adviceId;
	private String platform;
	private String adviceType;
	private String userId;
	private String name;
	private String number;
	private String email;
	private String content;
	private String readUserId;
	private byte readStatus;
	private String replyUserId;
	private byte replyStatus;
	private byte status;
	private long addDateLong;

	public AdviceInfo()
	{
	}

	public AdviceInfo(String adviceId, String platform, String content, byte readStatus, byte replyStatus, byte status, long addDateLong)
	{
		this.adviceId = adviceId;
		this.platform = platform;
		this.content = content;
		this.readStatus = readStatus;
		this.replyStatus = replyStatus;
		this.status = status;
		this.addDateLong = addDateLong;
	}

	public AdviceInfo(String adviceId, String platform, String adviceType, String userId, String name, String number, String email, String content, String readUserId, byte readStatus,
		String replyUserId, byte replyStatus, byte status, long addDateLong)
	{
		this.adviceId = adviceId;
		this.platform = platform;
		this.adviceType = adviceType;
		this.userId = userId;
		this.name = name;
		this.number = number;
		this.email = email;
		this.content = content;
		this.readUserId = readUserId;
		this.readStatus = readStatus;
		this.replyUserId = replyUserId;
		this.replyStatus = replyStatus;
		this.status = status;
		this.addDateLong = addDateLong;
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

	@Column(name = "advice_id", unique = true, nullable = false, length = 16)
	public String getAdviceId()
	{
		return this.adviceId;
	}

	public void setAdviceId(String adviceId)
	{
		this.adviceId = adviceId;
	}

	@Column(name = "platform", nullable = false, length = 16)
	public String getPlatform()
	{
		return this.platform;
	}

	public void setPlatform(String platform)
	{
		this.platform = platform;
	}

	@Column(name = "advice_type", length = 32)
	public String getAdviceType()
	{
		return this.adviceType;
	}

	public void setAdviceType(String adviceType)
	{
		this.adviceType = adviceType;
	}

	@Column(name = "user_id", length = 16)
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	@Column(name = "name", length = 16)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "number", length = 16)
	public String getNumber()
	{
		return this.number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	@Column(name = "email", length = 32)
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Column(name = "content", nullable = false, length = 2048)
	public String getContent()
	{
		return this.content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@Column(name = "read_user_id", length = 16)
	public String getReadUserId()
	{
		return this.readUserId;
	}

	public void setReadUserId(String readUserId)
	{
		this.readUserId = readUserId;
	}

	@Column(name = "read_status", nullable = false)
	public byte getReadStatus()
	{
		return this.readStatus;
	}

	public void setReadStatus(byte readStatus)
	{
		this.readStatus = readStatus;
	}

	@Column(name = "reply_user_id", length = 16)
	public String getReplyUserId()
	{
		return this.replyUserId;
	}

	public void setReplyUserId(String replyUserId)
	{
		this.replyUserId = replyUserId;
	}

	@Column(name = "reply_status", nullable = false)
	public byte getReplyStatus()
	{
		return this.replyStatus;
	}

	public void setReplyStatus(byte replyStatus)
	{
		this.replyStatus = replyStatus;
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
}
