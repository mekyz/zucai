package com.lrcall.common.utils;

import java.util.Comparator;

import com.lrcall.common.models.TableOrderInfo;

public class TableOrderSortComparator implements Comparator<TableOrderInfo>
{
	@Override
	public int compare(TableOrderInfo o1, TableOrderInfo o2)
	{
		if (o1.getIndex() < o2.getIndex())
		{
			return -1;
		}
		else if (o1.getIndex() == o2.getIndex())
		{
			return 0;
		}
		return 1;
	}
}
