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
		window.location.href="link_list_m";
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
	<link rel="stylesheet" type="text/css" href="<%=betDir%>/Public/Home/h+/css/pay.css" />
	<link href="<%=betDir%>/Public/Home/h+/js/layui/css/layui.css" rel="stylesheet">
	<style>
.pay_list>ul li {
	height: 250px; text-align: center; line-height: 170px; max-width: 90%;
}
</style>
	<div class="row">
		<div id="content" class="col-sm-12 full">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="panel panel-default bk-bg-white">
						<div class="panel-heading bk-bg-white">
							<h6>
								<i class="fa fa-indent red"></i>友情链接
							</h6>
							<div class="panel-actions"></div>
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="background: #FFFFFF;">
							<div class="pay_list">
								<ul>
									<li data-val="1">
										<h2></h2> <a href="http://live.leisu.com" target="_blank"><img src="<%=betDir%>/Uploads/Picture/2018-03-01/5a9776c12247a.jpg" /></a>
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
									<li data-val="1">
										<h2></h2> <a href="http://live.titan007.com" target="_blank"><img src="<%=betDir%>/Uploads/Picture/2018-03-01/5a97771ed73a6.jpg" /></a>
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
									<li data-val="1">
										<h2></h2> <a href="http://bf.spbo1.com" target="_blank"><img src="<%=betDir%>/Uploads/Picture/2018-03-01/5a9776f3092f6.jpg" /></a>
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
									<li data-val="1">
										<h2></h2> <a href="http://live.gooooal.com" target="_blank"><img src="<%=betDir%>/Uploads/Picture/2018-03-01/5a97772e07604.jpg" /></a>
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>