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
 * ProvinceInfo generated by hbm2java
 */
@Entity
@Table(name = "province_info", uniqueConstraints = { @UniqueConstraint(columnNames = "name"), @UniqueConstraint(columnNames = "province_id") })
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ProvinceInfo implements java.io.Serializable
{
	private static final long serialVersionUID = 4793918968860883159L;
	private Integer id;
	private String provinceId;
	private String name;
	private String code;

	public ProvinceInfo()
	{
	}

	public ProvinceInfo(String provinceId, String name)
	{
		this.provinceId = provinceId;
		this.name = name;
	}

	public ProvinceInfo(String provinceId, String name, String code)
	{
		this.provinceId = provinceId;
		this.name = name;
		this.code = code;
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

	@Column(name = "province_id", unique = true, nullable = false, length = 16)
	public String getProvinceId()
	{
		return this.provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

	@Column(name = "name", unique = true, nullable = false, length = 32)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "code", length = 12)
	public String getCode()
	{
		return this.code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
}