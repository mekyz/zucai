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
<link rel="stylesheet" href="<%=mobileCssPath%>/weui.min.css?v=20171127">
<link rel="stylesheet" href="<%=mobileCssPath%>/wx_customer.css?v=20171127">
<%-- 引入js文件 --%>
<script src="<%=commonJsPath%>/jquery.min.js?v=20170418"></script>
<script src="<%=commonJsPath%>/layer/mobile/layer.js?v=20170418"></script>
<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
<script src="<%=mobileJsPath%>/common.js?v=20180505"></script>
<script>
	var getUserPreUrl = function() {
	    var preUserUrl = "<%=preUserUrl%>";
	    return preUserUrl;
    }
</script>
<sitemesh:write property='head' />
</head>
<body>
	<%-- 顶部标题及菜单按钮 --%>
	<div id="div_head" class="head_root">
		<div id="div_back" class="div_back" style="visibility: hidden;">
			<a id="a_back"><img src="<%=mobileImgPath%>/return.png" alt="返回" /></a>
		</div>
		<div class="div_title">
			<sitemesh:write property='title'>DM SUPER CLUB</sitemesh:write>
		</div>
		<div id="div_menu" class="div_menu" style="visibility: hidden;">
			<a href="index"><img src="<%=mobileImgPath%>/ic_action_user.png" alt="主页" /></a>
		</div>
	</div>
	<%-- 内容区 --%>
	<div id="div_content" class="div_content">
		<sitemesh:write property='body' />
	</div>
	<script>
		var parentId = getMetaContent("parentId");
        var metaShowHead = getMetaContent("showHead");
        var metaShowMenu = getMetaContent("showMenu");
        var bodyStyle = getMetaContent("bodyStyle");
        if(isNull(metaShowHead)){
	        metaShowHead = "true";
        }
        if(isWechart()){
        	metaShowHead = "false";
        }
        if(isNull(metaShowMenu)){
	        metaShowMenu = "false";
        }
        if(metaShowHead == "true"){
	        $("#div_head").css("display","block");
	        $("#div_content").css("margin-top","50px");
        }else{
	        $("#div_head").css("display","none");
	        $("body").eq(0).css("padding-top","0");
	        $("#div_content").css("margin-top","0");
        }
        if(metaShowMenu == "true"){
	        $("#div_menu").css("visibility","visible");
        }else{
	        $("#div_menu").css("visibility","hidden");
        }
        if(!isNull(parentId)){
	        $("#div_back").css("visibility","visible");
	        $("#a_back").attr("href",parentId);
        }
        if(!isNull(bodyStyle)){
	        $("body").eq(0).attr("style",bodyStyle);
        }
	</script>
</body>
</html>
