package com.bet.models;

import java.awt.Color;
import java.awt.Font;

public class DrawStrInfo
{
	private String content;
	private Font font;
	private Color color;
	private int x;
	private int y;

	public DrawStrInfo()
	{
		super();
	}

	public DrawStrInfo(String content, Font font, Color color, int x, int y)
	{
		super();
		this.content = content;
		this.font = font;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
