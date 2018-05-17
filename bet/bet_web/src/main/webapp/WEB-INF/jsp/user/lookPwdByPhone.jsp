<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>找回密码</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="shortcut icon" href="<%=betDir%>/Public/Home/h+/ico/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon.png" />
<link rel="apple-touch-icon" sizes="57x57" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-57x57.png" />
<link rel="apple-touch-icon" sizes="72x72" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-72x72.png" />
<link rel="apple-touch-icon" sizes="76x76" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-76x76.png" />
<link rel="apple-touch-icon" sizes="114x114" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-114x114.png" />
<link rel="apple-touch-icon" sizes="120x120" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-120x120.png" />
<link rel="apple-touch-icon" sizes="144x144" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-144x144.png" />
<link rel="apple-touch-icon" sizes="152x152" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-152x152.png" />
<link href="<%=betDir%>/Public/Home/h+/js/layui/css/layui.css" rel="stylesheet">
<link href="<%=betDir%>/Public/Home/h+/css/checkbox.css" rel="stylesheet">
<link href="<%=betDir%>/Public/Home/h+/css/icheck.css" rel="stylesheet">
<link href="<%=betDir%>/Public/Home/h+/css/animate.min.css" rel="stylesheet">
<link href="<%=betDir%>/Public/Home/h+/vendor/skycons/css/skycons.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/css/style.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<script>
	if(isMobileWeb()){
		window.location.href="lookPwdByPhone_m";
	}
</script>
</head>
<body style="background: url(<%=betDir%>/Uploads/Picture/2018-03-01/5a976824df63d.jpg) no-repeat fixed top">
	<div class="container-fluid content">
		<div class="row">
			<div class="body-sign body-locked">
				<div class="center-sign">
					<div class="panel bk-bg-white panel-sign">
						<div class="panel-body text-center bk-padding-off bk-wrapper">
							<img src="<%=betDir%>/Public/Home/h+/login/img/flat-landscape.jpg" class="img-responsive bk-radius-top" />
							<div class="bk-avatar bk-avatar120-halfdown">
								<div class="bk-vcenter"></div>
								<div class="bk-fg-info bk-fg-darken">
									<img src="<%=betDir%>/Public/Home/h+/login/img/avatar3.jpg" alt="" class="img-circle bk-img-120 bk-border-white bk-border-darken bk-border-3x" />
								</div>
							</div>
						</div>
						<div class="panel-body bk-bg-white bk-padding-left-30 bk-padding-right-30 bk-avatar120-halfdown-after text-center">
							<!-- <h3 class="bk-margin-off">
								<strong>lcx111</strong>
							</h3>
							<div class="bk-padding-bottom-30 bk-padding-top-10">
								<p>李福华</p>
							</div> -->
							<form class="form-horizontal locked" method="post">
								<input type="hidden" id="v" name="v" value="${apiVersion}" />
								<div class="form-group">
									<div class="input-group input-group-icon">
										<input class="form-control bk-radius mobile" required aria-required="true" maxlength="11" type="text" name="number" id="number" placeholder="输入注册时手机号" /> <span class="input-group-addon">
											<span class="icon icon-lg"> <i class="fa fa-mobile"></i>
										</span>
										</span>
									</div>
								</div>
								<div class="form-group">
									<div class="input-group input-group-icon">
										<input class="form-control bk-radius mobile" required aria-required="true" maxlength="11" type="text" name="newPassword" id="newPassword" placeholder="输入新的密码" /> <span
											class="input-group-addon"
										> <span class="icon icon-lg"> <i class="fa fa-mobile"></i>
										</span>
										</span>
									</div>
								</div>
								<div class="form-group">
									<div class="input-group input-group-icon">
										<input class="form-control bk-radius code" style="width: 48%" required aria-required="true" type="text" id="code1" name="code1" placeholder="输入图形验证码" /> <img id="verify" src="getAuthCode"
											style="cursor: pointer; width: 40%; float: left; height: 34px; top: 6px; left: 13px;"
										>
									</div>
								</div>
								<div class="form-group">
									<div class="input-group input-group-icon">
										<input class="form-control bk-radius code" style="width: 48%" required aria-required="true" type="text" id="code" name="code" placeholder="输入短信验证码" />
										<button type="button" id='get_code' onclick="getCode(this);" class=" btn btn-primary hidden-xs get_code">获取短信验证码</button>
										<button type="button" id='get_code' onClick="getCode(this)" class="btn btn-primary  visible-xs get_code">获取短信验证码</button>
									</div>
								</div>
								<div class="pull-left">
									<button type="button" onclick="formSubmit();" class="btn btn-primary hidden-xs">重置密码</button>
									<button type="button" onclick="formSubmit();" class="btn btn-primary visible-xs ">重置密码</button>
								</div>
							</form>
							<div class="pull-right">
								<a href="javascript:();">
									<button type="button" onclick="javascript:history.back();" class="btn btn-danger">返回登录</button>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=betDir%>/Public/Home/h+/js/jquery.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/layui/layui.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/jquery.cookie.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/validate/validate.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/validate/messages.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/common.js"></script>
	<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
	<script src="<%=mobileJsPath%>/common.js?v=20180505"></script>
	<script>
		layui.use('layer',function(){
			var layer=layui.layer;
			layer.config({});
		});
	</script>
	<script type="text/javascript">
		/* 验证码切换 */
		$("#verify").click(function(){
			var verifyimg=$("#verify").attr("src");
			if(verifyimg.indexOf('?')>0){
				$("#verify").attr("src",verifyimg+'&random='+Math.random());
			}else{
				$("#verify").attr("src",verifyimg.replace(/\?.*$/,'')+'?'+Math.random());
			}
		});
		var InterValObj; //timer变量，控制时间
		var curCount;//当前剩余秒数
		function getCode(obj){
			layui.use('layer',function(){
				var layer=layui.layer;
				var count="60"; //间隔函数，1秒执行
				if(!count){
					count=60;
				}
				var number=$("#number").val();
				var code=$("#code1").val();
				if(!number){
					layer.msg("请输入手机号码！");
					return;
				}
				if(!code){
					layer.msg("请输入图形验证码！");
					return;
				}
				$.ajax({url:"../ajaxGetSmsCode",type:"post",data:{'number':number,'code':code,'type':LR_WEB.SMS_CODE_TYPE.RESET_PWD},success:function(res){
					if(res.code==0){
						curCount=count;
						$(obj).text(curCount+"秒后重新获取");
						$(obj).attr("disabled","true");
						$(obj).addClass("btn-danger width-45").removeClass('btn-success');
						$("#number").attr("readonly",'true');
						InterValObj=window.setInterval(SetRemainTime,1000);
						layer.alert(res.msg,{title:'提示',icon:0});
					}else{
						layer.alert(res.msg,{title:'错误提示',icon:0});
					}
				}});
			});
		}
		function formSubmit(){
			if(!checkNull("number","手机号码不能为空！")) return false;
			if(!checkNull("newPassword","登录密码不能为空！")) return false;
			if(!checkNull("code","请输入验证码！")) return false;
			if(!$("#number").val().match(/^1\d{10}$/)){
				showMsg('请输入有效的手机号码！');
				return;
			}
			$.ajax({url:"ajaxResetPwd",type:"post",data:{'number':$("#number").val(),'newPassword':$("#newPassword").val(),'code':$("#code").val(),'v':$("#v").val()},success:function(res){
				if(res.code==0){
					showSuccessMsg("提示","重置密码成功！","确定",function(){
						goUrl('login');
					});
				}else{
					layer.alert(res.msg,{title:'错误提示',icon:0});
				}
			}});
		}
		function SetRemainTime(){
			if(curCount==0){
				window.clearInterval(InterValObj);//停止计时器
				$(".get_code").removeAttr("disabled");//启用按钮
				$(".get_code").addClass("btn-success").removeClass('btn-danger');
				$(".get_code").text("重新获取验证码");
			}else{
				curCount--;
				$(".get_code").text(curCount+"秒后重新获取");
			}
		}
	</script>
</body>
</html>