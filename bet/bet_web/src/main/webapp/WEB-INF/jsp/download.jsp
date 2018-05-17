<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>软件下载</title>
<%-- 配置信息结束  --%>
<meta name="pageId" content="download" />
<meta name="groupId" content="download" />
<meta name="bodyStyle" content="" />
<%-- 配置信息结束  --%>
<script>
	function getAndroidComImg(){
		ajaxJson("user/ajaxGetClientDownloadQr","id=${androidClientInfo.id}&imgPath=${serverUrl}..<%=commonImgPath%>/logo.png",function(data){
			//showLog('data:'+data);
			$("#img_android_comp").attr('src','${serverUrl}'+data+'');
		},function(msg){
			showLog(msg);
		});
	}
	function getIosComImg(){
		ajaxJson("user/ajaxGetClientDownloadQr","id=${iosClientInfo.id}&imgPath=${serverUrl}..<%=commonImgPath%>/logo.png",function(data){
			//showLog('data:'+data);
			$("#img_ios_comp").attr('src','${serverUrl}'+data+'');
		},function(msg){
			showLog(msg);
		});
	}
	$(function(){
		$("#android_down").on('click',function(){
			var url='${androidClientInfo.url}';
			if(isWechart()){//如果是微信浏览器打开
				//showMsg('请点击右上角选择“在浏览器中打开”。');
				$("#div_pic").show();
				$("#div_pic").css({"position":"fixed","left":"0","top":"0","height":winHeight,"width":"100%","z-index":"20000","background-color":"rgba(0,0,0,0.8)","filter":"alpha(opacity=80)",});
				$("#div_pic p").css({"text-align":"center","margin-top":"10%","padding-left":"5%","padding-right":"5%"});
			}else{
				goUrl(url);
			}
		});
		$("#ios_down").on('click',function(){
			var url='${iosClientInfo.url}';
			if(isWechart()){//如果是微信浏览器打开
				//showMsg('请点击右上角选择“在浏览器中打开”。');
				$("#div_pic").show();
				$("#div_pic").css({"position":"fixed","left":"0","top":"0","height":winHeight,"width":"100%","z-index":"20000","background-color":"rgba(0,0,0,0.8)","filter":"alpha(opacity=80)",});
				$("#div_pic p").css({"text-align":"center","margin-top":"10%","padding-left":"5%","padding-right":"5%"});
				goUrl(url);
			}else{
				goUrl(url);
			}
		});
		getAndroidComImg();
		getIosComImg();
	});
</script>
</head>
<body>
	<div class="container-fluid marketing" style="margin-top: 0px;">
		<hr class="featurette-divider">
		<div class="row">
			<div class="col-md-7">
				<h4>安卓最新版：V${androidClientInfo.versionName}.${androidClientInfo.versionCode}</h4>
				<p>${androidClientInfo.content}</p>
				<p class="lead">
					<a id="android_down">点击下载</a>
				</p>
			</div>
			<div class="col-md-5">
				<img id="img_android_comp" class="featurette-image img-responsive center-block">
			</div>
		</div>
		<hr class="featurette-divider">
		<div class="row">
			<div class="col-md-7">
				<h4>苹果最新版：V${iosClientInfo.versionName}.${iosClientInfo.versionCode}</h4>
				<p></p>
				<p class="lead">
					<a id="ios_down">点击下载</a>
				</p>
			</div>
			<div class="col-md-5">
				<img id="img_ios_comp" class="featurette-image img-responsive center-block">
			</div>
		</div>
		<hr class="featurette-divider">
	</div>
	<div id="div_pic" style="display: none;">
		<img src="<%=commonImgPath%>/live_weixin.png" style="width: 100%;" />
	</div>
</body>
</html>
