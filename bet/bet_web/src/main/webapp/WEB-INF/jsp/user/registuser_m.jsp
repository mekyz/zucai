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
	if(!isMobileWeb()){
		window.location.href="registuser";
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
	<style>
input::-webkit-input-placeholder {
	color: #ce3861 !important;
}

input::-moz-placeholder { /* Mozilla Firefox 19+ */
	color: #ce3861 !important;
}

input:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
	color: #ce3861 !important;
}

input:-ms-input-placeholder { /* Internet Explorer 10-11 */
	color: #ce3861 !important;
}
</style>
	<div class="row">
		<form method="post" class="form-horizontal">
			<input type="hidden" id="v" name="v" value="${apiVersion}" />
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="panel panel-default bk-bg-white">
					<div class="panel-heading bk-bg-white">
						<h6>
							<i class="fa fa-indent red"></i>基本信息
						</h6>
						<div class="panel-actions"></div>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-md-3 control-label" for="usernumber-input">会员编号:</label>
							<div class="col-md-6">
								<input id="userId" name="userId" class="form-control" size="16" type="text" placeholder="小写字母开头,小写字母和数字组合" maxlength="11" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="tuijiannumber-input">推荐人编号:</label>
							<div class="col-md-6">
								<input type="text" id="referrerId" name="referrerId" class="form-control" value="${userInfo.userId}" placeholder="推荐人编号...">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="tuijiannumber-input">图形验证码:</label>
							<div class="col-md-6">
								<input type="text" id="code1" name="code1" class="form-control" value="" placeholder="输入图形验证码">
							</div>
							<div class="col-sm-3">
								<img id="verify" src="getAuthCode">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="tuijiannumber-input">手机号码:</label>
							<div class="col-md-6">
								<input type="text" id="number" name="number" class="form-control" value="" placeholder="手机号码...">
							</div>
							<div class="col-sm-3">
								<button type="button" id="btn_get_code" class="btn btn-success btn-sm" onclick="getCode();">获取验证码</button>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="tuijiannumber-input">验证码:</label>
							<div class="col-md-6">
								<input type="text" id="code" name="code" class="form-control" value="" placeholder="验证码...">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="panel panel-default bk-bg-white">
					<div class="panel-heading bk-bg-white">
						<h6>
							<i class="fa fa-indent red"></i>安全信息
						</h6>
						<div class="panel-actions"></div>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-md-3 control-label">(默认密码:123456)</label>
							<div class="col-md-9 ">
								<button type="button" class="btn btn-success btn-sm" id='set'>重置密码</button>
							</div>
						</div>
						<hr />
						<div class="form-group">
							<label class="col-md-3 control-label" for="password1-input">登陆密码:</label>
							<div class="col-md-6">
								<input type="password" id="password" required name="password" class="form-control" value="" placeholder="登陆密码...">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="repassword1-input">确认登陆密码:</label>
							<div class="col-md-6">
								<input type="password" id="rePassword" required name="rePassword" class="form-control" value="" placeholder="确认登陆密码...">
							</div>
						</div>
						<hr />
						<div class="form-group">
							<label class="col-md-3 control-label" for="password2-input">二级密码:</label>
							<div class="col-md-6">
								<input type="password" id="password2" required name="password2" class="form-control" value="" placeholder="二级密码...">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="repassword2-input">确认二级密码:</label>
							<div class="col-md-6">
								<input type="password" id="rePassword2" required name="rePassword2" class="form-control" value="" placeholder="确认二级密码...">
							</div>
						</div>
					</div>
				</div>
				<div class=" bk-bg-white">
					<div class="panel-body">
						<div class="text-center bk-margin-top-10 bk-margin-bottom-30">
							<button class="bk-margin-5 btn btn-success" type="button" onclick="formSubmit();" target-form="commentForm">提交</button>
							<button type="reset" class="bk-margin-5 btn btn-warning">重置</button>
							<button type="button" class="bk-margin-5 btn btn-danger" onclick="javascript:history.back();">返回</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		layui.use('layer',function(){
			var layer=layui.layer; 
		});
		/* 重置密码 */
		i=0;
		$('#set').click(function(){
			if(i%2){
				$("input[type='password']").val(123456);
			}else{
				$("input[type='password']").val('');
			}
			i=i+1;
		});
	</script>
	<script type="text/javascript">
		/*验证用户编号*/
		$("#userId").blur(function(){
			if(!check_empty('userId','会员编号1')){
				return false;
			}
		});
		/*手机号*/
		$("#number").blur(function(){
			if(!check_empty('number','手机号')){
				return false;
			}
			if(!check_phone('number','手机号')){
				return false;
			}
		});
		/*一级密码*/
		$("#password").blur(function(){
			if(!check_empty('password','登陆级密码')){
				return false;
			}
			if(!check_Size('password','登陆密码','6','30')){
				return false;
			}
		});
		/*重复一级密码*/
		$("#rePassword").blur(function(){
			if(!check_empty('rePassword','登陆密码')){
				return false;
			}
			if(!check_Same('password','登陆密码','rePassword','确认登陆密码')){
				return false;
			}
		});
		/*二级密码*/
		$("#password2").blur(function(){
			if(!check_empty('password2','安全密码')){
				return false;
			}
			if(!check_Size('password2','安全密码','6','30')){
				return false;
			}
		});
		/*重复二级密码*/
		$("#rePassword2").blur(function(){
			if(!check_empty('rePassword2','安全密码')){
				return false;
			}
			if(!check_Same('password2','安全密码','rePassword2','确认安全密码')){
				return false;
			}
		});
	</script>
	<script type="text/javascript">
		/*验证推荐人编号*/
		$("#tuijiannumber").blur(function(){
			if(!check_empty('tuijiannumber','推荐人编号')){
				return false;
			}
		});
	</script>
	<script type="text/javascript">
		/*验证节点人编号*/
		$("#parentnumber").blur(function(){
			if(!check_empty('parentnumber','接点人编号')){
				return false;
			}
		});
	</script>
	<script src="<%=betDir%>/Public/Home/h+/js/jquery.cookie.js"></script>
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
		/*获取短信验证码*/
		function getCode(){
			var count="180"; //间隔函数，1秒执行
			if(!count){
				count=180;
			}
			if(!check_empty('number','手机号')){
				return false;
			}
			if(!check_phone('number','手机号')){
				return false;
			}
			number=$("#number").val();
			var code=$("#code1").val();
			if(!code){
				layer.msg("请输入图形验证码！");
				return;
			}
			$.ajax({url:"../ajaxGetSmsCode",type:"post",data:{"number":number,'code':code,"type":LR_WEB.SMS_CODE_TYPE.REGISTER},success:function(res){
				if(res.code==0){
					curCount=count;
					$("#btn_get_code").html("请在"+curCount+"秒内输入验证码");
					$("#btn_get_code").attr("disabled","true");
					$("#btn_get_code").attr("style","color:#fff");
					$("#number").attr("readonly",'true');
					$.cookie('mobile_code',curCount,{expires:1});
					$.cookie('number',number,{expires:1});
					InterValObj=window.setInterval(SetRemainTime,1000);
					layer.msg(res.msg);
				}else{
					layer.msg(res.msg);
				}
			}});
		}
		function SetRemainTime(){
			if(curCount==0){
				window.clearInterval(InterValObj);//停止计时器
				$("#btn_get_code").removeAttr("disabled");//启用按钮
				$("#btn_get_code").removeAttr("style");//启用按钮
				$("#btn_get_code").html("重新发送验证码");
			}else{
				curCount--;
				$.cookie('mobile_code',curCount,{expires:1});
				$("#btn_get_code").html("请在"+curCount+"秒内输入验证码");
			}
		}
		function formSubmit(){
			var userId=$("#userId").val();
			var password=$("#password").val();
			var rePassword=$("#rePassword").val();
			var password2=$("#password2").val();
			var rePassword2=$("#rePassword2").val();
			var referrerId=$("#referrerId").val();
			var v=$("#v").val();
			var number=$("#number").val();
			var code=$("#code").val();
			if($("#password").val()!==$("#rePassword").val()){
				showMsg('两次输入的登录密码不一致！');
				return false;
			}
			if($("#password2").val()!==$("#rePassword2").val()){
				showMsg('两次输入的二级密码不一致！');
				return false;
			}
			$.ajax({url:"ajaxRegister",type:"post",data:{'userId':userId,'number':number,'password':password,'password2':password2,'referrerId':referrerId,'v':v,'code':code},success:function(res){
				if(res.code==0){
					layer.msg('注册账号成功！');
					//window.location.href='login';
				}else{
					layer.msg(res.msg);
				}
			}});
		}
	</script>
</body>
</html>