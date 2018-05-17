<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户信息</title>
<meta name="parentId" content="index" />
<meta name="showMenu" content="false" />
<meta name="bodyStyle" content="" />
</head>
<body>
	<div class="page">
		<div class="weui_cells">
			<div class="weui_cell">
				<div class="div_photo">
					<div class="photo">
						<img id="img_pic" src="<%=basePath%>${userInfo.picUrl}" />
					</div>
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>账号：</p>
				</div>
				<div class="weui_cell_ft">${userInfo.userId}</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>昵称：</p>
				</div>
				<div class="weui_cell_ft">${userInfo.nickname}</div>
			</div>
			<%-- <div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>电话：</p>
				</div>
				<div class="weui_cell_ft">${userInfo.number}</div>
			</div> --%>
			<%-- <div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>邮箱：</p>
				</div>
				<div class="weui_cell_ft">${userInfo.email}</div>
			</div> --%>
			<div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>性别：</p>
				</div>
				<div class="weui_cell_ft">
					<script>
						document.write(getSexTypeString('${userInfo.sex}'));
					</script>
				</div>
			</div>
			<!-- <div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>生日：</p>
				</div>
				<div class="weui_cell_ft">
					<script>
						document.write(getMyFormatDateTime('${userInfo.birthday}','yyyy-MM-dd'));
					</script>
				</div>
			</div> -->
			<div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<p>地区：</p>
				</div>
				<div class="weui_cell_ft">${userInfo.country}-${userInfo.province}-${userInfo.city}-${userInfo.address}</div>
			</div>
		</div>
	</div>
</body>
</html>