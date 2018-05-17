<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="com.bet.utils.*"%>
<%@ page import="com.lrcall.lrweb.common.utils.*"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String basePath = request.getContextPath() + "/";
	String commonDir = "skin/common";
	String mobileDir = "skin/mobile";
	String pcDir = "skin/pc";
	String commonImgPath = basePath + commonDir + "/images";
	String commonJsPath = basePath + commonDir + "/js";
	String commonCssPath = basePath + commonDir + "/css";
	String mobileImgPath = basePath + mobileDir + "/images";
	String mobileJsPath = basePath + mobileDir + "/js";
	String mobileCssPath = basePath + mobileDir + "/css";
	String pcImgPath = basePath + pcDir + "/images";
	String pcJsPath = basePath + pcDir + "/js";
	String pcCssPath = basePath + pcDir + "/css";
	String thirdDir = "../skin/third";
	String currentPath = basePath + "admin/";
	String preUrl = (String) session.getAttribute(SessionManage.SessionAdmin.ADMIN_PRE_URL.getName());
	if (StringTools.isNull(preUrl)) {
		preUrl = "";
	}
%>
<html>
<head>
<base href="<%=currentPath%>" />
<%-- 设置兼容模式 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%-- 设置字符集 --%>
<meta charset="utf-8">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<%-- 设置移动设备屏幕参数 --%>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<%-- 设置图标 --%>
<link rel="shortcut icon" href="<%=commonImgPath%>/logo.png">
<%-- 设置标题 --%>
<title>管理员登录</title>
<%-- 设置作者 --%>
<meta name="author" content="DM SUPER CLUB" />
<%-- 设置描述 --%>
<meta name="description" content="DM SUPER CLUB">
<%-- 浏览器不会自动调整文件的大小,也就是说是固定大小,不会随着浏览器拉伸缩放 --%>
<meta name="MobileOptimized" content="240" />
<%-- 添加到主屏幕“后，全屏显示 --%>
<meta name="apple-touch-fullscreen" content="YES" />
<%-- 如果内容设置为YES，Web应用程序运行在全屏模式;否则，它不会。默认行为是使用Safari浏览器显示网页内容 --%>
<meta name="apple-mobile-web-app-capable" content="yes" />
<%-- 引入css文件 --%>
<link rel="stylesheet" href="<%=commonJsPath%>/bootstrap-3.3.7/css/bootstrap.min.css?v=20170418">
<link rel="stylesheet" href="<%=commonCssPath%>/customer.css?v=20170418">
<link rel="stylesheet" href="<%=pcCssPath%>/style.css" type="text/css" media="all">
<%-- 引入js文件 --%>
<script src="<%=commonJsPath%>/ie-emulation-modes-warning.js?v=20170418"></script>
<script src="<%=commonJsPath%>/jquery.min.js?v=20170418"></script>
<script src="<%=commonJsPath%>/layer/layer.js?v=20170418"></script>
<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
<script src="<%=pcJsPath%>/common.js?v=20180505"></script>
<script>
	addEventListener("load",function(){
		setTimeout(hideURLbar,0);
	},false);
	function hideURLbar(){
		window.scrollTo(0,1);
	}
	document.onkeydown=function keyEnter(){
		if(event.keyCode==13){
			formSubmit();
		}
	};
	var getAdminPreUrl=function(){
		var pre_url="<%=preUrl%>";
		return pre_url;
	};
	function formSubmit(){
		if(!checkNull("userId","账号不能为空！")) return false;
		if(!checkNull("password","登录密码不能为空！")) return false;
		if(!checkNull("code","验证码不能为空！")) return false;
		submitForm("adminInfo","ajaxLogin",true,"正在登录...",function(data){
			var prePage=getAdminPreUrl();
			if(isNull(prePage)){
				prePage="index";
			}
			goUrl(prePage);
		},function(msg){
			showMsg(msg);
			getAuthCode();
		});
		return false;
	}
	function getAuthCode(){
		$("#code").val("");
		$("#imgCode").attr("src","getAuthCode?v="+Math.random());
	}
</script>
</head>
<body>
	<h1></h1>
	<div class="container w3layouts agileits">
		<h2 style="text-align: center;">管理员登录</h2>
		<form:form modelAttribute="adminInfo" method="post">
			<form:input type="text" path="userId" placeholder="请输入账号" />
			<form:input type="password" path="password" placeholder="请输入密码" />
			<input type="text" id="code" name="code" placeholder="请输入验证码" />
			<img id="imgCode" src="getAuthCode" onclick="getAuthCode()" style="margin-bottom: 20px;" />
			<!-- <a onclick="getAuthCode()" style="margin-left: 20px;">看不清？换一张</a> -->
		</form:form>
		<div class="send-button w3layouts agileits">
			<input type="button" class="btn" style="width: 50%;" onclick="formSubmit();" value="登 录">
		</div>
		<div class="clear"></div>
	</div>
	<div class="footer w3layouts agileits">
		<p>© 2016-2020 DM SUPER CLUB</p>
	</div>
	<script src="<%=commonJsPath%>/bootstrap-3.3.7/js/bootstrap.min.js?v=20170418"></script>
</body>
</html>