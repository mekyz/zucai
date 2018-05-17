<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>我的二维码</title>
<meta name="parentId" content="index" />
<meta name="showMenu" content="false" />
</head>
<body>
	<div class="page">
		<div class="weui_cells">
			<div class="weui_cell">
				<div class="div_photo no-border" style="width: 40%;">
					<div class="photo no-border">
						<img src="<%=basePath%>${userInfo.qrUrl}" style="border-radius: 0;" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>