<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title><spring:message code="page_title" /></title>
<script>
if(isMobileWeb()){
	window.location.href="user_info_m";
}
</script>
<script>
	$(function(){
		bindContent("referrerCount","ajaxGetUserSubInfoListCount","");
		bindContent("referrerTotalCount","ajaxGetAllUserSubInfoListCount","");
	});
</script>
</head>
<body>
	<div class="page-header" style="background: none;"></div>
	<style>
.phone-tq {
	display: none;
}

@media ( max-width : 414px) {
	.phone-tq {
		display: block;
	}
	.phone-tq .ticker-news-box {
		padding: 14px !important;
	}
}
</style>
	<style>
.link--kukuri {
	text-transform: uppercase; font-weight: 900; overflow: hidden; line-height: 0.75; color: #c5c2b8;
}

.link {
	outline: none; text-decoration: none; position: relative; font-size: 8em; line-height: 1; color: #9e9ba4; display: inline-block;
}

.shouyi {
	background: #a2c357;
}

.shouyi span {
	display: block; padding: 6px 12px; position: absolute; top: 0; left: 15px; border-radius: 0 6px 6px 0; background: #f33d2c; color: #fff;
}

.shouyi a {
	display: block; text-align: center; font-size: 35px; padding: 24px; color: #8a6d3b;
}
</style>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading bk-bg-white">
					<h6>
						<span class="label label-danger bk-margin-5"></span>账户信息
					</h6>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
							<tbody>
								<tr>
									<td>会员编号</td>
									<td colspan="3">${userInfo.userId}</td>
								</tr>
								<tr>
									<td>会员姓名</td>
									<td colspan="3">${userInfo.name}</td>
								</tr>
								<tr>
									<td>会员电话</td>
									<td colspan="3">${userInfo.number}</td>
								</tr>
								<tr>
									<td>会员注册时间</td>
									<td colspan="3"><script>
										writeDate('${userInfo.regDateLong}');
									</script></td>
								</tr>
								<tr>
									<td>推荐人</td>
									<td colspan="3">${userInfo.referrerId}</td>
								</tr>
								<tr>
									<td>直推人数</td>
									<td colspan="3" id="referrerCount">0</td>
								</tr>
								<tr>
									<td>下级总人数</td>
									<td colspan="3" id="referrerTotalCount">0</td>
								</tr>
								<tr>
									<td>会员级别</td>
									<td colspan="3"><span style='color: #FFAC00;'><script>
										document.write(getUserTypeString("${userInfo.userType}"));
									</script></span></td>
								</tr>
								<tr>
									<td>彩金钱包</td>
									<td colspan="3">${userInfo.point/100}</td>
								</tr>
								<%-- <tr>
									<td>代理佣金</td>
									<td colspan="3">${userInfo.rewardPoint/100}</td>
								</tr> --%>
								<tr>
									<td>冻结金额</td>
									<td colspan="3">${userInfo.freezePoint/100}</td>
								</tr>
								<tr>
									<td>体验彩金</td>
									<td colspan="3">${userInfo.givePoint/100}</td>
								</tr>
								<tr>
									<td>推广链接</td>
									<td colspan="3"><span style="white-space: normal;">${serverUrl}user/register?userId=${userInfo.userId}</span></td>
								</tr>
								<tr>
									<td>推广二维码</td>
									<td colspan="3" style="text-align: left;"><img src="<%=basePath%>${userInfo.qrUrl}" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>