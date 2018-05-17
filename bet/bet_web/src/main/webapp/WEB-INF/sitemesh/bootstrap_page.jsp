<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
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
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<%-- 设置字符集 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<%-- 设置移动设备屏幕参数 --%>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<%-- 设置图标 --%>
<link rel="shortcut icon" href="<%=commonImgPath%>/logo.png">
<title><sitemesh:write property='title' /></title>
<%-- 设置作者 --%>
<meta name="author" content="DM SUPER CLUB" />
<%-- 设置描述 --%>
<meta name="description" content="DM SUPER CLUB">
<%-- 浏览器不会自动调整文件的大小,也就是说是固定大小,不会随着浏览器拉伸缩放 --%>
<meta name="MobileOptimized" content="240" />
<%-- 添加到主屏幕“后，全屏显示 --%>
<meta name="apple-touch-fullscreen" content="yes" />
<%-- 如果内容设置为YES，Web应用程序运行在全屏模式;否则，它不会。默认行为是使用Safari浏览器显示网页内容 --%>
<meta name="apple-mobile-web-app-capable" content="yes" />
<link rel="stylesheet" href="<%=commonJsPath%>/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=pcCssPath%>/carousel.css">
<script src="<%=commonJsPath%>/ie-emulation-modes-warning.js"></script>
<script src="<%=commonJsPath%>/jquery.min.js"></script>
<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
<sitemesh:write property='head' />
</head>
<body>
	<nav id="div_head" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index"><img alt="logo" src="<%=commonImgPath%>/logo.png" style="float: left; margin-top: -6px;" width="32px"><span style="float: left; margin-left: 5px;">DM
						SUPER CLUB</span></a>
			</div>
			<div id="navbar" class="collapse navbar-collapse" style="max-height: 600px;">
				<ul class="nav navbar-nav">
					<li id="li_index"><a href="index">首页</a></li>
					<li id="li_download"><a href="download">下载</a></li>
					<li id="li_log"><a href="log">日志</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="user/login">用户登录</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div id="div_body" class="container-fluid marketing">
		<sitemesh:write property='body' />
	</div>
	<nav id="div_footer" class="navbar navbar-default" style="margin-bottom: 0;">
		<%-- navbar-fixed-bottom --%>
		<div class="container-fluid text-center" style="max-width: 320px; padding: 20px 0;">
			<div class="row" style="margin-left: 0; margin-right: 0; padding-top: 10px;">
				<div class="col-md-12">© 2017-2025</div>
			</div>
		</div>
	</nav>
	<script>
		//setBodyHeight();
		var winHeight=document.documentElement.clientHeight;// 可见区域高度
		var headHeight=$("#div_head").css("height").replace("px","");
		var footerHeight=$("#div_footer").css("height").replace("px","");
		var bodyHeight=winHeight-headHeight-footerHeight-20;
		//showLog("winHeight:"+winHeight+",headHeight:"+headHeight+",footerHeight:"+footerHeight+",bodyHeight:"+bodyHeight);
		$("#div_body").css("min-height",bodyHeight+"px");
		var pageId=getMetaContent("pageId");
		var groupId=getMetaContent("groupId");
		var bodyStyle=getMetaContent("bodyStyle");
		if(!isNull(groupId)){
			$("#li_"+groupId).attr("class","active");
		}
		if(!isNull(bodyStyle)){
			$("body").eq(0).attr("style",bodyStyle);
		}
	</script>
	<script src="<%=commonJsPath%>/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
