<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ include file="path_var.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%-- 设置标题 --%>
<title><sitemesh:write property='title'>DM SUPER CLUB</sitemesh:write></title>
<%-- 设置作者 --%>
<meta name="author" content="DM SUPER CLUB" />
<%-- 设置图标 --%>
<link rel="shortcut icon" href="<%=commonImgPath%>/logo.png">
<%-- 设置描述 --%>
<meta name="description" content="DM SUPER CLUB">
<%-- 设置兼容模式 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%-- 设置字符集 --%>
<meta charset="utf-8">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<%-- 设置移动设备屏幕参数 --%>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<%-- 浏览器不会自动调整文件的大小,也就是说是固定大小,不会随着浏览器拉伸缩放 --%>
<meta name="MobileOptimized" content="240" />
<%-- 添加到主屏幕“后，全屏显示 --%>
<meta name="apple-touch-fullscreen" content="YES" />
<%-- 如果内容设置为YES，Web应用程序运行在全屏模式;否则，它不会。默认行为是使用Safari浏览器显示网页内容 --%>
<meta name="apple-mobile-web-app-capable" content="yes" />
<%-- 引入css文件 --%>
<link rel="stylesheet" href="<%=commonJsPath%>/bootstrap-3.3.7/css/bootstrap.min.css?v=20170418">
<link rel="stylesheet" href="<%=commonCssPath%>/customer.css?v=20170418">
<link rel="stylesheet" href="<%=mobileCssPath%>/customer.css?v=20170507">
<%-- 引入js文件 --%>
<%-- <script src="<%=commonJsPath%>/ie-emulation-modes-warning.js"></script> --%>
<script src="<%=commonJsPath%>/jquery.min.js?v=20170418"></script>
<script src="<%=commonJsPath%>/layer/mobile/layer.js?v=20170418"></script>
<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
<script src="<%=mobileJsPath%>/common.js?v=20180505"></script>
<script>
	var getUserPreUrl = function() {
	    var preUserUrl = "<%=preUserUrl%>";
		return preUserUrl;
	};
</script>
<sitemesh:write property='head' />
</head>
<body>
	<%-- 顶部标题及菜单按钮 --%>
	<div id="div_head" class="head_root">
		<div id="div_back" class="div_back" style="visibility: hidden;">
			<a id="a_back"> <img src="<%=mobileImgPath%>/return.png" alt="返回" />
			</a>
		</div>
		<div class="div_title">
			<sitemesh:write property='title'>DM SUPER CLUB</sitemesh:write>
		</div>
		<div class="div_menu">
			<div id="div_sub_menu" class="btn-group" style="visibility: hidden;">
				<img src="<%=mobileImgPath%>/menu.png" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" />
				<ul id="menu_id" class="dropdown-menu">
				</ul>
			</div>
		</div>
	</div>
	<%-- 内容区 --%>
	<sitemesh:write property='body' />
	<script>
		var parentId=getMetaContent("parentId");
		var metaShowHead=getMetaContent("showHead");
		var metaShowMenu=getMetaContent("showMenu");
		var bodyStyle=getMetaContent("bodyStyle");
		if(isNull(metaShowHead)){
			metaShowHead="true";
		}
		if(isWechart()){
			metaShowHead="false";
		}
		if(isNull(metaShowMenu)){
			metaShowMenu="false";
		}
		if(metaShowHead=="true"){
			$("#div_head").css("display","block");
		}else{
			$("#div_head").css("display","none");
			$("body").eq(0).css("padding-top","0");
		}
		if(metaShowMenu=="true"){
			$("#div_sub_menu").css("visibility","visible");
		}else{
			$("#div_sub_menu").css("visibility","hidden");
		}
		if(!isNull(parentId)){
			$("#div_back").css("visibility","visible");
			$("#a_back").attr("href",parentId);
		}
		if(!isNull(bodyStyle)){
			$("body").eq(0).attr("style",bodyStyle);
		}
	</script>
	<script src="<%=commonJsPath%>/bootstrap-3.3.7/js/bootstrap.min.js?v=20170418"></script>
	<%-- <script src="<%=commonJsPath%>/ie10-viewport-bug-workaround.js"></script> --%>
</body>
</html>
