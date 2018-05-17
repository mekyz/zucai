package com.lrcall.common.models;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

import com.lrcall.common.utils.StringTools;

/**
 * xml对象抽象类<br>
 * 提供对象到xml的相互转换
 * 
 * @author libit
 */
public abstract class AbstractXmlObject
{
	/**
	 * 对象转换成xml
	 * 
	 * @return
	 */
	public Document toDocument()
	{
		Document doc = new Document();
		Element root = new Element("xml");
		doc.setRootElement(root);
		Map<String, String> map = getMap();
		for (String key : map.keySet())
		{
			String value = map.get(key);
			if (!StringTools.isNull(value))
			{
				createElement(root, key, value);
			}
		}
		return doc;
	}

	protected Element createElement(Element parent, String name, String value)
	{
		Element elem = new Element(name);
		elem.setText(value);
		parent.getChildren().add(elem);
		return elem;
	}

	public Map<String, String> getMap()
	{
		Map<String, String> map = new HashMap<>();
		Field[] fields = this.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				Method[] methods = this.getClass().getDeclaredMethods();
				if (methods != null && methods.length > 0)
				{
					for (Method method : methods)
					{
						if (method.getName().startsWith("get") && method.getName().toLowerCase().equals("get" + field.getName()))
						{
							try
							{
								String value = (String) method.invoke(this);
								if (!StringTools.isNull(value))
								{
									map.put(field.getName(), value);
									// System.out.println("name:" + field.getName() + ",value:" + value);
								}
								break;
							}
							catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return map;
	}
	// public Map<String, Object> toMap()
	// {
	// Map<String, Object> map = new HashMap<String, Object>();
	// Field[] fields = this.getClass().getDeclaredFields();
	// for (Field field : fields)
	// {
	// Object obj;
	// try
	// {
	// obj = field.get(this);
	// if (obj != null)
	// {
	// map.put(field.getName(), obj);
	// }
	// }
	// catch (IllegalArgumentException e)
	// {
	// e.printStackTrace();
	// }
	// catch (IllegalAccessException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// return map;
	// }
}
