// 存储name的值value
function setCookieItem(name, value)
{
	window.localStorage.setItem(name, value);
}
// 获取name的值
function getCookieItem(name)
{
	return window.localStorage.getItem(name);
}
// 删除name的值
function delCookieItem(name)
{
	return window.localStorage.removeItem(name);
}
// 清空cookie的值
function clearCookie()
{
	// window.localStorage.clear()；
}
// 显示所有的Cookie
function showStorage()
{
	var storage = window.localStorage;
	for (var i = 0; i < storage.length; i++)
	{
		// key(i)获得相应的键，再用getItem()方法获得对应的值
		document.write(storage.key(i) + " : " + storage.getItem(storage.key(i)) + "<br>");
	}
}
/* 存储cookie */
function setCookie(c_name, value, expiredays)
{
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}

/* 取出cookie */
function getCookie(c_name)
{
	if (document.cookie.length > 0)
	{
		c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1)
		{
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1)
				c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

var NUMBER = "number";
var PASSWORD = "password";

function setNumberCookie(value)
{
	setCookie(NUMBER, value);
}
/* <%-- 为了安全起见，不能存储password的cookie --%> */
function setPasswordCookie(value)
{
	setCookie(PASSWORD, "");
}
function getNumberCookie()
{
	return getCookie(NUMBER);
}
function getPasswordCookie()
{
	return getCookie(PASSWORD);
}
function deleteCookie(key)
{
	setCookie(key, "");
}
function clearCookie()
{
	var keys = new Array(NUMBER, PASSWORD);
	var len = keys.length();
	for ( var key in keys)
	{
		deleteCookie(key);
	}
}
