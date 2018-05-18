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
		window.location.href="rechargereply_m";
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
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="panel panel-default bk-bg-white">
						<div class="panel-heading bk-bg-white">
							<h6>
								<i class="fa fa-indent red"></i>充值申请
							</h6>
							<div class="panel-actions"></div>
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="background: #FFFFFF;">
							<div class="pay_list" style="margin: 25px 0;">
								<h3>请选择充值方式：</h3>
								<ul>
									<li class="hover" data-val="1">
										<h2 style="color: #757575">转账入款</h2> <img src="<%=betDir%>/Public/Home/h+/images/pay_img2.jpg" />
										<p style="color: #757575">请务必根据页面提示的线下汇款银行账户信息，进行汇款并在第一时间将支付凭证编辑上传。</p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
									<%-- <li data-val="2">
										<h2 style="color: #757575">在线支付</h2> <img src="<%=betDir%>/Public/Home/h+/images/pay_img1.jpg" />
										<p style="color: #757575">银联在线支付是中国银联与商业银行共同打造的银行卡网上交易转接清算平台，支持各类型银联卡支持开通网银的银联储蓄卡在线支付充值。</p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li> --%>
									<li data-val="3">
										<h2 style="color: #757575">支付宝入款</h2> <img src="<%=betDir%>/Public/Home/h+/images/pay_img3.jpg" />
										<p style="color: #757575">支付宝（中国）网络技术有限公司是国内领先的第三方支付平台，致力于提供“简单、安全、快捷”的支付解决方案。</p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
									<!--<li data-val="4">
										<h2 style="color: #757575">比特币入款</h2> <img src="<%=betDir%>/Public/Home/h+/images/pay_img4.jpg" />
										<p style="color: #757575">比特币是一种P2P形式的数字货币。点对点的传出意味着一个去中心化的支付系统。</p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>-->
									<li data-val="5">
										<h2 style="color: #757575">微信支付</h2> <img src="<%=betDir%>/Public/Home/h+/images/pay_img5.png" />
										<p style="color: #757575">微信支付是国内领先的第三方支付平台，致力于提供“简单、安全、快捷”的支付解决方案。</p>
										<div class="pay-right">
											<img src="<%=betDir%>/Public/Home/h+/images/right.png" /> <span></span>
										</div>
									</li>
								</ul>
							</div>
							<p class="pay-txt1">
								<a class="btn btn-success" onclick="next()">提交</a>
								<button type="button" class="btn btn-danger" onclick="javascript:history.back();">返回</button>
							</p>
							<script>
								$('.pay_list ul li').click(function(){
									$(this).addClass('hover').siblings().removeClass('hover');
								})
								function next(){
									var val=$('.hover').attr('data-val');
									if(val=='2'){
										layer.msg('暂未开放，敬请期待！',{icon:7});
										//window.location.href="reply_put1";
									}else if(val=='1'){
										window.location.href="reply_put2";
									}else if(val=='3'){
										//layer.msg('暂未开放，敬请期待！',{icon:7});
										window.location.href="reply_put3";
									}else if(val=='4'){
										layer.msg('暂未开放，敬请期待！',{icon:7});
									}else if(val=='5'){
										window.location.href="reply_put5";
									}
								}
							</script>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>