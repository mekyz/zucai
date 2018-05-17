package com.bet.models;

import java.awt.image.BufferedImage;

public class DrawImgInfo
{
	private BufferedImage bufferedImage;
	private int x;
	private int y;
	private int width;
	private int height;

	public DrawImgInfo()
	{
		super();
	}

	public DrawImgInfo(BufferedImage bufferedImage, int x, int y, int width, int height)
	{
		super();
		this.bufferedImage = bufferedImage;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public BufferedImage getBufferedImage()
	{
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage)
	{
		this.bufferedImage = bufferedImage;
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

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}
