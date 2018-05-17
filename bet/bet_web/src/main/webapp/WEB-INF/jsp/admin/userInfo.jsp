<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户信息</title>
<meta name="pageId" content="userInfo" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>用户信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form class="form-horizontal form-label-left input_mask">
						<c:choose>
							<c:when test="${userInfo.picUrl.length() > 0}">
								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12">LOGO：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">
										<img src="<%=basePath%>${userInfo.picUrl}" width="50%" />
									</div>
								</div>
							</c:when>
						</c:choose>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">用户账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<a href="goUserInfo?userId=${userInfo.userId}" target="_blank">${userInfo.userId}</a>
							</div>
						</div>
						<div class="form-group">
							<label for="sortId" class="control-label col-md-3 col-sm-3 col-xs-12">用户类型：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									document.write(getUserTypeString("${userInfo.userType}"));
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">姓名：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.name}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">昵称：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.nickname}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">上级代理：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.parentId}</div>
						</div>
						<c:choose>
							<c:when test="${userInfo.referrerId.length() > 0}">
								<div class="form-group">
									<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">推荐人：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.referrerId}</div>
								</div>
								<div class="form-group">
									<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">推荐时间：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
										<script>
											writeDate('${userInfo.referrerDateLong}');
										</script>
									</div>
								</div>
							</c:when>
						</c:choose>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">性别：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									document.write(getSexTypeString('${userInfo.sex}'));
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">出生日期：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.birthday}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">地址：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.country}-${userInfo.province}-${userInfo.city}-${userInfo.address}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">账户余额：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.point/100}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">冻结余额：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.freezePoint/100}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">奖励余额：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.rewardPoint/100}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">赠送余额：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.givePoint/100}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">赠送有效时间：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									writeDate('${userInfo.giveValidateDateLong}');
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">手机号码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.number}</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">手机号码认证状态：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									var status='${userInfo.numberVerifyStatus}';
									if(status==LR_WEB.STATUS.ENABLED){
										document.write('已认证');
									}else{
										document.write('未认证');
									}
								</script>
							</div>
						</div>
						<c:choose>
							<c:when test="${userInfo.authStatus == 0}">
								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12">实名认证：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">已认证</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12">身份证号码：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">${userInfo.identityCard}</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12">实名认证：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">未认证</div>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">账号状态：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									var status='${userInfo.status}';
									if(status==LR_WEB.STATUS.ENABLED){
										document.write('正常');
									}else{
										document.write('已禁用');
									}
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">注册时间：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									writeDate('${userInfo.regDateLong}');
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">备注：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userInfo.remark}</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>