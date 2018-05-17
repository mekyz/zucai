<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta charset="UTF-8">
<title><spring:message code="page_login_title" /></title>
<link rel="stylesheet" type="text/css" href="<%=betDir%>/Public/Home/football/css/css.css" />
</head>
<link href="<%=betDir%>/Public/Home/football/js/layui/css/layui.css" rel="stylesheet">
<body style="background: url(<%=betDir%>/Uploads/Picture/2018-03-01/5a976824df63d.jpg) no-repeat fixed top; background-size: 100% 100%;">
	<style>
html, body {
	height: 100%;
}

body {
	background: center center no-repeat; overflow: hidden;
}

@media ( max-width : 320px) {
	.login {
		width: 100%; margin-top: 289px;
	}
	.inp {
		width: 250px;
	}
	.login ul li {
		margin-bottom: 30px;
	}
	.foot {
		display: none;
	}
}

@media ( max-width : 480px) {
	html {
		height: 100%;
	}
	body {
		height: auto; overflow: auto; min-height: 100%; background: #000;
	}
	.logo {
		width: 100px; margin-left: -50px;
	}
	.login {
		width: 100% !important; margin-top: 160px;
	}
	.login ul {
		margin-top: 45px;
	}
	.login ul li img {
		width: 45px;
	}
	.inp {
		width: 300px;
	}
	.foot {
		display: none;
	}
}

@media ( min-width : 481px) {
	.login {
		margin-top: 250px;
	}
}

.select {
	position: absolute; top: 25px; right: 65px; background: none; color: #FFFFFF; border: none;
}

select option {
	color: #323232;
}

.foot {
	width: 100%; position: fixed; bottom: 0; text-align: center; color: #FFFFFF; padding-bottom: 15px; font-size: 14px;
}

.foot p {
	margin-top: 10px;
}

.foot a {
	color: #FFFFFF; text-decoration: none;
}
</style>
	<span class="select" style="float: left; margin-right: 15px;"> <a style="cursor: pointer; color: #FFFFFF;" href="login?lang=en_US"><img src="<%=betDir%>/Public/Home/h+/images/en.jpg"
			style="margin-right: 7px;"
		/>English</a> <a style="cursor: pointer; color: #FFFFFF;" href="login?lang=zh_CN"><img src="<%=betDir%>/Public/Home/h+/images/zh.jpg" style="margin: 0 7px;" />中文</a>
	</span>
	<img src="<%=betDir%>/Uploads/Picture/2018-03-01/5a9768310187f.png" class="logo" alt="" />
	<form:form modelAttribute="userInfo" class="form-horizontal" style="overflow: hidden;" method="post">
		<input type="hidden" id="v" name="v" value="${apiVersion}" />
		<spring:message code='page_login_user_id' var="userIdPlaceHodler" />
		<spring:message code="page_login_user_password" var="userPasswordPlaceHodler" />
		<div class="login">
			<div class="inp">
				<img src="<%=betDir%>/Public/Home/football/Img/login-ico1.jpg" />
				<form:input path="userId" value="" placeholder="${userIdPlaceHodler}" />
			</div>
			<div class="inp">
				<img src="<%=betDir%>/Public/Home/football/Img/login-ico2.jpg" />
				<form:input type="password" path="password" value="" placeholder='${userPasswordPlaceHodler}' />
			</div>
			<div class="inp">
				<input type="text" name="code" id="code" value="" placeholder="<spring:message code='page_login_auth_code' />" /> <img id="verify" alt="<spring:message code='page_login_change_code' />"
					title="<spring:message code="page_login_change_code" />" src="getAuthCode" style="cursor: pointer; width: 40%; float: left; height: 34px; top: 6px; right: 13px; left: auto;"
				>
			</div>
			<p style="overflow: hidden;">
				<label style="float: left;"><input type="checkbox" name="" id="" value="" /> <spring:message code="page_login_remember_me" /></label> <a style="cursor: pointer; float: right;"
					style="color: #FFFFFF;" onClick="lookpsd();"
				><font color="#ffffff"><spring:message code="page_login_forgot_pwd" /></font></a>
			</p>
			<button type="button" value="<spring:message code='page_login_login' />" onclick="login(this);">
				<spring:message code="page_login_login" />
			</button>
		</div>
	</form:form>
	<div class="foot">
		<a id="about_us" href="javascript:;"><spring:message code="page_login_company_introduce" /></a>&nbsp;|&nbsp;<a id="rulls" href="javascript:;"><spring:message code="page_login_rule" /></a>&nbsp;|&nbsp;<a
			href="javascript:;" id="contact"
		><spring:message code="page_login_contact_us" /></a>&nbsp;|&nbsp; <a href="https://live.leisu.com"><spring:message code="page_login_link" /></a>&nbsp;
		<p>
			<b><spring:message code="page_login_tips" /></b>
		</p>
	</div>
	<script src="<%=betDir%>/Public/Home/football/js/jquery.js"></script>
	<script src="<%=betDir%>/Public/Home/football/js/layui/layui.js"></script>
	<script src="<%=betDir%>/Public/Home/football/js/jquery.cookie.js"></script>
	<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
	<script src="<%=mobileJsPath%>/common.js?v=20180505"></script>
	<script>
		function lookpsd(){
			layui.use('layer',function(){
				var layer=layui.layer;
				var userId=$("#userId").val();
				if(!userId){
					layer.msg("请输入账号！");
				}else{
					window.location.href="lookPwdByPhone";
				}
			});
		}
		/*  登录  */
		$(function(){
			$(document).keydown(function(event){
				if(event.keyCode==13){
					login();
				}
			});
			if($.cookie("rememberMe")=='true'){
				$(".js_rememberMe").attr('checked',true);
				$(".js_userId").val($.cookie("userId"));
				$(".js_password").val($.cookie("password"));
			}
			// console.log($.cookie('adminname'));
		});
		function login(obj){
			layui.use('layer',function(){
				var layer=layui.layer;
				var userId=$('#userId').val();
				if(!userId){
					layer.msg('请输入账号');
					$('#userId').focus();
					return false;
				};
				var password=$('#password').val();
				if(!password){
					layer.msg('请输入密码');
					$('#password').focus();
					return false;
				};
				var verify_open="1";
				if(verify_open==1||verify_open==3){
					var verify=$('#code').val();
					if(!verify){
						layer.msg('请输入验证码');
						$('#code').focus();
						return false;
					};
					var data={userId:userId,password:password,code:verify,v:$('#v').val()};
				}else{
					var data={userId:userId,password:password};
				}
				//	console.log(data);
				var url="ajaxLogin";
				$(obj).text("登陆中..");
				$(obj).attr("disabled","disabled");
				var index=layer.msg('正在登陆，请耐心等待...',{icon:16,shade:0.01});
				$.ajax({url:url,type:"post",dataType:"json",data:data,success:function(res){
					if(res.code==0){
						layer.close(index);
						rememberMe();
						layer.msg("登录成功....",{icon:1},function(){
							location.href='index';
						});
					}else{
						layer.close(index);
						layer.alert(res.msg,{title:'错误提示',icon:0});
						if(verify_open==1||verify_open==3){
							$("#verify_code").val('');
							$("#verify").click();
						}
						$(obj).text("登陆");
						$(obj).attr("disabled",false);
					}
				}});
			});
		}
	</script>
	<script type="text/javascript">
		if($(document).height()<621){
			$('.foot').css({'position':'inherit','marginTop':'60px'});
		}else{
			$('.foot').css({'position':'fixed'});
		}
		$(window).resize(function(){
			if($(document).height()<621){
				$('.foot').css({'position':'inherit','marginTop':'60px'});
			}else{
				$('.foot').css({'position':'fixed'});
			}
		});
		/* 登陆 验证码切换 */
		$("#verify").click(function(){
			verifytrun('verify');
		});
		function verifytrun(verify){
			var verifyimg=$("#"+verify).attr("src");
			if(verifyimg.indexOf('?')>0){
				$("#"+verify).attr("src",verifyimg+'&random='+Math.random());
			}else{
				$("#"+verify).attr("src",verifyimg.replace(/\?.*$/,'')+'?'+Math.random());
			}
		}
		/*记住密码*/
		function rememberMe(){
			if($("input[type='checkbox']").is(':checked')){
				var userId=$(".js_userId").val();
				var password=$(".js_password").val();
				$.cookie('rememberMe','true',{expires:7}); //设置cookie有效期为7天
				$.cookie('userId',userId,{expires:7}); //设置cookie有效期为7天
				$.cookie('password',password,{expires:7}); //设置cookie有效期为7天
			}else{
				$.cookie('rememberMe','false',{expires:-1}); //使cookie失效
				$.cookie('userId','',{expires:-1});//使cookie失效
				$.cookie('password','',{expires:-1});//使cookie失效
				$(".js_userId,.js_password").attr("autocomplete","off"); //禁止浏览器自动记录
			}
		}
	</script>
	<script type="text/javascript">
		layui.use('layer',function(){
			$('#contact').click(function(){
				layer.open({type:2,title:'联系我们',shadeClose:true,shade:0.8,area:['1200px','700px'],content:"pageContact" //iframe的url
				});
			});
			$('#about_us').click(function(){
				layer.open({type:2,title:'公司简介',shadeClose:true,shade:0.8,area:['1200px','700px'],content:"pageAboutUs" //iframe的url
				});
			});
			$('#rulls').click(function(){
				layer.open({type:2,title:'规则说明',shadeClose:true,shade:0.8,area:['1200px','700px'],content:"pageRules" //iframe的url
				});
			});
		});
	</script>
</body>
</html>