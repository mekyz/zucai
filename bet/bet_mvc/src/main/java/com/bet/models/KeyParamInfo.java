package com.bet.models;

import com.google.gson.annotations.SerializedName;

public class KeyParamInfo
{
	@SerializedName("word")
	private String word;
	@SerializedName("flag")
	private Integer flag;

	public KeyParamInfo()
	{
		super();
	}

	public KeyParamInfo(String word, Integer flag)
	{
		super();
		this.word = word;
		this.flag = flag;
	}

	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
	}

	public Integer getFlag()
	{
		return flag;
	}

	public void setFlag(Integer flag)
	{
		this.flag = flag;
	}
}
