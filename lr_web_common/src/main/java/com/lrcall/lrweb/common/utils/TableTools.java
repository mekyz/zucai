package com.lrcall.lrweb.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lrcall.common.models.TableData;
import com.lrcall.common.utils.LogTools;

public class TableTools
{
	public static LogTools log = LogTools.getInstance();
	public static final String DRAW = "draw";
	public static final String START = "start";
	public static final String LENGTH = "length";
	public static final String ORDER_COLUMN = "orderColumn";
	public static final String ORDER_COLUMN_NAME = "orderColumnName";
	public static final String ORDER_DIR = "orderDir";
	public static final String SEARCH_VALUE = "searchValue";

	public static Map<String, Object> getTableParams(HttpServletRequest request)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		int draw = 0;
		int start = 0;
		int length = 100;
		int orderColumn = 0;
		String orderColumnName = "";
		String orderDir = "";
		String searchValue = "";
		try
		{
			draw = Integer.parseInt(request.getParameter("draw"));
		}
		catch (Exception e)
		{
			draw = 0;
			// logTools.info("getTableParams", "draw参数无效！");
		}
		try
		{
			start = Integer.parseInt(request.getParameter("start"));
		}
		catch (Exception e)
		{
			start = 0;
			// logTools.info("getTableParams", "start参数无效！");
		}
		try
		{
			length = Integer.parseInt(request.getParameter("length"));
		}
		catch (Exception e)
		{
			length = 100;
			// logTools.info("getTableParams", "length参数无效！");
		}
		try
		{
			// searchValue = new
			// String(request.getParameter("search[value]").getBytes("ISO-8859-1"), "UTF-8");
			searchValue = request.getParameter("search[value]");
		}
		catch (Exception e)
		{
			searchValue = "";
			// logTools.info("getTableParams", "searchValue参数无效！");
		}
		try
		{
			orderColumn = Integer.parseInt(request.getParameter("order[0][column]"));
		}
		catch (Exception e)
		{
			orderColumn = 0;
			// logTools.info("getTableParams", "orderColumn参数无效！");
		}
		try
		{
			orderColumnName = request.getParameter("columns[" + (orderColumn) + "][data]");
		}
		catch (Exception e)
		{
			orderColumnName = "";
			// logTools.info("getTableParams", "orderColumnName参数无效！");
		}
		try
		{
			orderDir = request.getParameter("order[0][dir]");
		}
		catch (Exception e)
		{
			orderDir = "";
			// logTools.info("getTableParams", "orderDir参数无效！");
		}
		map.put(DRAW, draw);
		map.put(START, start);
		map.put(LENGTH, length);
		map.put(ORDER_COLUMN, orderColumn);
		map.put(ORDER_COLUMN_NAME, orderColumnName);
		map.put(ORDER_DIR, orderDir);
		map.put(SEARCH_VALUE, searchValue);
		return map;
	}

	/**
	 * 转换成表格数据
	 * 
	 * @param draw
	 * @param start
	 * @param totalCount
	 * @param list
	 * @return
	 */
	public static TableData getTableDataInfo(int draw, int start, long totalCount, List<?> list)
	{
		long count = 0;
		if (list != null)
		{
			count = list.size();
		}
		return new TableData(draw, start, totalCount, count, list);
	}
}
