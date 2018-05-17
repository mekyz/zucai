<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户转账信息</title>
<meta name="pageId" content="userExchangeInfoEdit" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>用户转账信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">转账ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userExchangeInfo.exchangeId}</div>
						</div>
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userExchangeInfo.userId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">接收账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userExchangeInfo.receiveUserId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">转账金额(￥)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userExchangeInfo.money/100}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">留言：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userExchangeInfo.payRemark}</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>