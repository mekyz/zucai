<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户下注信息</title>
<meta name="pageId" content="userBetInfoEdit" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>用户下注信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">下注ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userBetInfo.betId}</div>
						</div>
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userBetInfo.userId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">赛事：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${matchInfo.matchName}(${matchInfo.homeTeam}-${matchInfo.awayTeam})</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">比分：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${matchProfitInfo.score1}:${matchProfitInfo.score2}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">下注金额(￥)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userBetInfo.money/100}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">预期收益(￥)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userBetInfo.money*matchProfitInfo.profitPercent/1000000}</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>