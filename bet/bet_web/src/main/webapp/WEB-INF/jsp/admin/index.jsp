<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>首页</title>
<meta name="pageId" content="index" />
<meta name="groupId" content="index" />
<meta name="bodyStyle" content="" />
<script>
	$(function(){
		var currentDate=new Date();
		var startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate());
		var endDateLong=startDateLong+24*60*60*1000-1;
		bindContent("p_user_today_count","ajaxGetTodayUserCount","");
		bindContent("p_user_total_count","ajaxGetUserInfoListCount","");
		bindContentMoney("p_bet_today_count","ajaxGetUserBetTotalMoney","startDateLong="+startDateLong+"&endDateLong="+endDateLong);
		bindContentMoney("p_bet_total_count","ajaxGetUserBetTotalMoney","");
		bindContentMoney("p_recharge_today_count","ajaxGetUserRechargeTotalMoney","startDateLong="+startDateLong+"&endDateLong="+endDateLong);
		bindContentMoney("p_recharge_total_count","ajaxGetUserRechargeTotalMoney","");
		bindContentMoney("p_withdraw_today_count","ajaxGetUserWithdrawTotalMoney","startDateLong="+startDateLong+"&endDateLong="+endDateLong);
		bindContentMoney("p_withdraw_total_count","ajaxGetUserWithdrawTotalMoney","");
	});
</script>
</head>
<body>
	<div class="">
		<div class="row top_tiles">
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageUsers')">
				<div class="tile-stats" style="background: #32cc7f; padding: 12px; color: #FFFFFF;">
					<div class="icon">
						<i class="fa fa-user"></i>
					</div>
					<div class="count" style="font-size: 22px; font-weight: bolder;">
						<span id="p_user_today_count">0</span>位
					</div>
					<h5 style="margin-left: 10px;">今日会员</h5>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageUsers')">
				<div class="tile-stats" style="background: #e4eaec; padding: 12px;">
					<div class="icon">
						<i class="fa fa-users"></i>
					</div>
					<div class="count" style="color: #0C0; font-size: 22px; font-weight: bolder;">
						<span id="p_user_total_count">0</span>位
					</div>
					<h5 style="margin-left: 10px;">总会员数</h5>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageUserBetInfos')">
				<div class="tile-stats" style="background: #e4eaec; padding: 12px;">
					<div class="icon">
						<i class="fa fa-users"></i>
					</div>
					<div class="count" style="color: #0C0; font-size: 22px; font-weight: bolder;">
						<span id="p_bet_today_count">0</span>元
					</div>
					<h5 style="margin-left: 10px;">今日下注金额</h5>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageUserBetInfos')">
				<div class="tile-stats" style="background: #e4eaec; padding: 12px;">
					<div class="icon">
						<i class="fa fa-users"></i>
					</div>
					<div class="count" style="color: #0C0; font-size: 22px; font-weight: bolder;">
						<span id="p_bet_total_count">0</span>元
					</div>
					<h5 style="margin-left: 10px;">总下注金额</h5>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageUserRechargeInfos')">
				<div class="tile-stats" style="background: #e4eaec; padding: 12px;">
					<div class="icon">
						<i class="fa fa-users"></i>
					</div>
					<div class="count" style="color: #0C0; font-size: 22px; font-weight: bolder;">
						<span id="p_recharge_today_count">0</span>元
					</div>
					<h5 style="margin-left: 10px;">今日充值金额</h5>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageUserRechargeInfos')">
				<div class="tile-stats" style="background: #e4eaec; padding: 12px;">
					<div class="icon">
						<i class="fa fa-users"></i>
					</div>
					<div class="count" style="color: #0C0; font-size: 22px; font-weight: bolder;">
						<span id="p_recharge_total_count">0</span>元
					</div>
					<h5 style="margin-left: 10px;">总充值金额</h5>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageWithdrawInfos')">
				<div class="tile-stats" style="background: #e4eaec; padding: 12px;">
					<div class="icon">
						<i class="fa fa-users"></i>
					</div>
					<div class="count" style="color: #0C0; font-size: 22px; font-weight: bolder;">
						<span id="p_withdraw_today_count">0</span>元
					</div>
					<h5 style="margin-left: 10px;">今日提现金额</h5>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12" onClick="goUrl('manageWithdrawInfos')">
				<div class="tile-stats" style="background: #e4eaec; padding: 12px;">
					<div class="icon">
						<i class="fa fa-users"></i>
					</div>
					<div class="count" style="color: #0C0; font-size: 22px; font-weight: bolder;">
						<span id="p_withdraw_total_count">0</span>元
					</div>
					<h5 style="margin-left: 10px;">总提现金额</h5>
				</div>
			</div>
		</div>
	</div>
</body>
</html>