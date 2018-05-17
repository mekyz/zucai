<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>意见反馈详情</title>
<meta name="parentId" content="adviceList" />
<meta name="showHead" content="true" />
<meta name="showMenu" content="false" />
</head>
<body>
	<div class="page__bd">
		<div class="weui-cells">
			<div class="weui-cells__title">意见内容：</div>
			<div class="weui-cells weui-cells_form">
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea" readonly="readonly">${adviceInfo.content}</textarea>
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${adviceReplyInfoList.size() > 0}">
					<c:forEach var="adviceReplyInfo" items="${adviceReplyInfoList}">
						<div class="weui-cells__title">回复内容：</div>
						<div class="weui-cells weui-cells_form">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<textarea class="weui-textarea" readonly="readonly">${adviceReplyInfo.content}</textarea>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="weui-cells__title text-center">暂无回复</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>