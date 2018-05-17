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
		window.location.href="shownews_m/${newsInfo.newsId}";
	}
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
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>详细信息
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<h1>
						<p class="text-center">${newsInfo.title}</p>
					</h1>
					<p class="text-center">
						发布日期：
						<script>
							writeDate('${newsInfo.updateDateLong}');
						</script>
						来源：${newsInfo.author}
					</p>
					<div class="well">${newsInfo.content}</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>