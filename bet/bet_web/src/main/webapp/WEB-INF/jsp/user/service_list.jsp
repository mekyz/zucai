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
		window.location.href="service_list_m";
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
	<div class="row">
		<div id="content" class="col-sm-12 full">
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" style="width: 100%;">
					<div class="panel panel-default bk-bg-white">
						<div class="panel-heading bk-bg-white">
							<h6>
								<i class="fa fa-indent red"></i>在线客服
							</h6>
							<div class="panel-actions"></div>
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="background: #FFFFFF;">
							<div class="pay_list" style="margin:25px 0;">
								<ul>
									<%-- <li data-val="1">
										<h2>零点QQ客服</h2> <img src="<%=betDir%>/Uploads/Picture/2018-03-01/5a97b727cea5e.png" style="height: 220px; width: 235px;" />
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li> --%>
									<%-- <li data-val="1">
										<h2>白天在线微信客服</h2> <img src="<%=betDir%>/Uploads/Picture/2018-03-10/5aa3a017bcf0b.jpg" style="height: 220px; width: 235px;" />
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li> --%>
									<li data-val="1">
										<h2>白天在线微信客服</h2> <img src="<%=commonImgPath%>/wechart_qrcode.jpg" style="height: 220px; width: 235px;" />
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
									<%-- <li data-val="1">
										<h2>白天在线QQ客服</h2> <img src="<%=betDir%>/Uploads/Picture/2018-03-13/5aa7e1a682aa1.png" style="height: 220px; width: 235px;" />
										<p></p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li> --%>
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