package com.bet.orms;
// Generated 2018-5-4 21:35:58 by Hibernate Tools 4.3.5.Final

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
 * UserTypeConditionInfo generated by hbm2java
 */
@Entity
@Table(name = "user_type_condition_info", uniqueConstraints = @UniqueConstraint(columnNames = "user_type"))
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserTypeConditionInfo implements java.io.Serializable
{
	private static final long serialVersionUID = 3456576348944936969L;
	private Integer id;
	private byte userType;
	private int directCount;
	private int teamCount;
	private long money;
	private byte status;
	private long addDateLong;
	private long updateDateLong;

	public UserTypeConditionInfo()
	{
	}

	public UserTypeConditionInfo(byte userType, int directCount, int teamCount, long money, byte status, long addDateLong, long updateDateLong)
	{
		this.userType = userType;
		this.directCount = directCount;
		this.teamCount = teamCount;
		this.money = money;
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

	@Column(name = "user_type", unique = true, nullable = false)
	public byte getUserType()
	{
		return this.userType;
	}

	public void setUserType(byte userType)
	{
		this.userType = userType;
	}

	@Column(name = "direct_count", nullable = false)
	public int getDirectCount()
	{
		return this.directCount;
	}

	public void setDirectCount(int directCount)
	{
		this.directCount = directCount;
	}

	@Column(name = "team_count", nullable = false)
	public int getTeamCount()
	{
		return this.teamCount;
	}

	public void setTeamCount(int teamCount)
	{
		this.teamCount = teamCount;
	}

	@Column(name = "money", nullable = false)
	public long getMoney()
	{
		return this.money;
	}

	public void setMoney(long money)
	{
		this.money = money;
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