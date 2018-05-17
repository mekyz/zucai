<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>出错了</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<%-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ --%>
<meta name="description" content="DM SUPER CLUB">
<meta name="author" content="DM SUPER CLUB">
<link rel="icon" href="<%=commonImgPath%>/logo.png">
<%-- Bootstrap core CSS --%>
<link rel="stylesheet" href="<%=commonJsPath%>/bootstrap-3.3.7/css/bootstrap.min.css">
<%-- 自定义Bootstrap --%>
<link rel="stylesheet" href="<%=pcCssPath%>/carousel.css">
<script src="<%=commonJsPath%>/ie-emulation-modes-warning.js"></script>
<script src="<%=commonJsPath%>/jquery.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index"><img alt="logo" src="<%=commonImgPath%>/logo.png" style="float: left; margin-top: -6px;" width="32px"><span style="float: left; margin-left: 5px;">DM SUPER CLUB</span></a>
			</div>
			<div id="navbar" class="collapse navbar-collapse" style="max-height: 600px;">
				<ul class="nav navbar-nav">
					<li class=active><a href="index">首页</a></li>
					<li><a href="download">下载</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div class="container-fluid marketing" style="margin-bottom: 50px;">
		<div class="row first_row">
			<div class="col-md-12" style="margin-top: 40px;">
				<img class="featurette-image img-responsive center-block" src="<%=pcImgPath%>/sad.gif" alt="sad">
			</div>
			<div class="col-md-12 text-center" style="margin-top: 20px;">
				<p>
					<span style="font-size: 1.5em;">抱歉，系统出错了！</span> <br /> <br />
				</p>
				<p>
					<c:choose>
						<c:when test="${exception.getCause() != null}">${exception.getCause().message}</c:when>
					</c:choose>
				</p>
			</div>
		</div>
	</div>
	<nav class="navbar navbar-default navbar-fixed-bottom">
		<div class="container-fluid text-center" style="min-width: 120px; max-width: 320px; padding: 20px 0;">
			<div class="row" style="margin-left: 0; margin-right: 0; padding-top: 20px;">
				<div class="col-md-12">© 2016-2020 DM SUPER CLUB</div>
			</div>
		</div>
	</nav>
	<%-- Bootstrap core JavaScript
    ================================================== --%>
	<%-- Placed at the end of the document so the pages load faster --%>
	<script src="<%=commonJsPath%>/bootstrap-3.3.7/js/bootstrap.min.js"></script>
	<%-- <script src="<%=pcJsPath%>/jquery-1.11.3.min.js"></script>
	<script src="<%=pcJsPath%>/bootstrap.min.js"></script> --%>
	<%-- IE10 viewport hack for Surface/desktop Windows 8 bug --%>
	<%-- <script src="<%=pcJsPath%>/ie10-viewport-bug-workaround.js"></script> --%>
</body>
</html>
