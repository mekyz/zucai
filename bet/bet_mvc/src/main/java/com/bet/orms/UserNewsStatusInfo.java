package com.bet.orms;
// Generated 2018-4-2 11:21:17 by Hibernate Tools 4.3.5.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * UserNewsStatusInfo generated by hbm2java
 */
@Entity
@Table(name = "user_news_status_info")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserNewsStatusInfo implements java.io.Serializable
{
	private static final long serialVersionUID = 3456843830178682934L;
	private Integer id;
	private String userId;
	private String newsId;
	private byte status;
	private long addDateLong;

	public UserNewsStatusInfo()
	{
	}

	public UserNewsStatusInfo(String userId, String newsId, byte status, long addDateLong)
	{
		this.userId = userId;
		this.newsId = newsId;
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

	@Column(name = "user_id", nullable = false, length = 32)
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	@Column(name = "news_id", nullable = false, length = 16)
	public String getNewsId()
	{
		return this.newsId;
	}

	public void setNewsId(String newsId)
	{
		this.newsId = newsId;
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
