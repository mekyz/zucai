package com.lrcall.common.utils;

import java.util.Iterator;
import java.util.Map;

public class UrlFilter
{
	public static final String SERVER_URL = "{serverUrl}";
	public static final String USER_ID = "{userId}";

	public static String filter(String url, Map<String, String> replaceStrs)
	{
		if (StringTools.isNull(url))
		{
			return url;
		}
		Iterator<String> iterator = replaceStrs.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			if (url.indexOf(key) > -1)
			{
				url = url.replace(key, replaceStrs.get(key));
			}
		}
		return url;
	}
}
