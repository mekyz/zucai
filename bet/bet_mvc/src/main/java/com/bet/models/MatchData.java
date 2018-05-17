package com.bet.models;

import com.google.gson.annotations.SerializedName;

/**
 * 采集的比赛信息
 * 
 * @author libit
 */
public class MatchData
{
	@SerializedName("id")
	private String id;
	@SerializedName("date")
	private String date;
	@SerializedName("name")
	private String name;
	@SerializedName("num")
	private String num;
	@SerializedName("time")
	private String time;
	@SerializedName("status")
	private String status;
	@SerializedName("homeTeam")
	private String homeTeam;
	@SerializedName("finalScore")
	private String finalScore;
	@SerializedName("awayTeam")
	private String awayTeam;
	@SerializedName("halfScore")
	private String halfScore;
	@SerializedName("url")
	private String url;
	@SerializedName("count")
	private String count;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNum()
	{
		return num;
	}

	public void setNum(String num)
	{
		this.num = num;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getHomeTeam()
	{
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam)
	{
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam()
	{
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam)
	{
		this.awayTeam = awayTeam;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getFinalScore()
	{
		return finalScore;
	}

	public void setFinalScore(String finalScore)
	{
		this.finalScore = finalScore;
	}

	public String getHalfScore()
	{
		return halfScore;
	}

	public void setHalfScore(String halfScore)
	{
		this.halfScore = halfScore;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}
}
