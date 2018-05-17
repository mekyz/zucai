package com.lrcall.common.models;

import java.util.List;

@SuppressWarnings("rawtypes")
public class TableData implements java.io.Serializable
{
	private static final long serialVersionUID = 3681198409906620754L;
	private long draw;
	private long start;
	private long recordsTotal;
	private long recordsFiltered;
	private List data;

	public TableData()
	{
		super();
	}

	public TableData(long draw, long start, long recordsTotal, long recordsFiltered, List data)
	{
		super();
		this.draw = draw;
		this.start = start;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsTotal;
		this.data = data;
	}

	public long getDraw()
	{
		return draw;
	}

	public void setDraw(long draw)
	{
		this.draw = draw;
	}

	public long getStart()
	{
		return start;
	}

	public void setStart(long start)
	{
		this.start = start;
	}

	public long getRecordsTotal()
	{
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal)
	{
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered()
	{
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered)
	{
		this.recordsFiltered = recordsFiltered;
	}

	public List getData()
	{
		return data;
	}

	public void setData(List data)
	{
		this.data = data;
	}
}
