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
	window.location.href="updatepwd_m";
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
.aaabbb h3 h4 {
	color: #ffffff; text-align: center; cursor: pointer; margin-bottom: 5px;
}

.aaabbb>div {
	width: 100%; margin: 0 auto;
}

.xxx>div {
	display: none;
}

.xxx .display {
	display: block;
}

.aaabbb.hover {
	background: rgba(0, 0, 0, 0.4) !important; box-shadow: 1px 1px 3px #e0dede inset, -1px 1px 3px #e0dede inset;
}
</style>
	<div class="ab">
		<div class="aaabbb col-lg-6 col-md-6 col-sm-6 col-xs-6 hover btn" style="background: rgba(0, 0, 0, 0.4) !important;">
			<div>
				<span style="font-size: 24px; margin-left:;"> <font color="#efaa3b">修改登陆密码</font>
				</span>
			</div>
		</div>
		<div class="aaabbb col-lg-6 col-md-6 col-sm-6 col-xs-6 btn" style="background: rgba(0, 0, 0, 0.4) !important;">
			<div>
				<span style="font-size: 24px; margin-left:;"> <font color="#efaa3b">修改二级密码</font>
				</span>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-body">
					<div class="tabs tabs-danger">
						<div class="tab-content">
							<div id="loginpassword" class="tab-pane active">
								<form action="ajaxUpdateUserPwd" method="post" class="form-horizontal " id="form1">
									<div class="form-group">
										<label class="col-sm-3 control-label" for="w2-username">原登陆密码</label>
										<div class="col-md-4">
											<input class="form-control" type="password" name="password" value="">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" for="w2-password">新登陆密码</label>
										<div class="col-md-4">
											<input class="form-control" type="password" name="newPwd" value="">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" for="w2-password">确认新密码</label>
										<div class="col-md-4">
											<input class="form-control input-sm" type="password" name="reNewPwd" value="">
										</div>
									</div>
									<div class="row">
										<div class="col-lg-9 col-md-6 col-sm-9 col-lg-offset-3 col-md-offset-3 col-sm-offset-3 col-xs-offset-4">
											<button type="button" class="btn btn-sm btn-info " onClick="updatePwd1(this)">更新</button>
											<button type="button" class="btn btn-sm btn-success" onclick="javascript:history.back();">返回</button>
										</div>
									</div>
								</form>
							</div>
							<div id="safepassword" class="tab-pane">
								<form action="ajaxUpdateUserPwd2" method="post" class="form-horizontal" id="form2">
									<div class="form-group">
										<label class="col-lg-3 col-md-3 col-sm-3 control-label" for="w2-username">原二级密码</label>
										<div class="col-md-4">
											<input class="form-control" type="password" name="password2" value="">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 col-md-3 col-sm-3 control-label" for="w2-password">新二级密码</label>
										<div class="col-md-4">
											<input class="form-control" type="password" name="newPwd2" value="">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-3 col-md-3 col-sm-3 control-label" for="w2-password">确认新密码</label>
										<div class="col-md-4">
											<input class="form-control" type="password" name="reNewPwd2" value="">
										</div>
									</div>
									<div class="row">
										<div class="col-lg-9 col-md-6 col-sm-9 col-xs-9 col-lg-offset-3 col-md-offset-3 col-sm-offset-3 col-xs-offset-4">
											<button type="button" class="btn btn-sm btn-info " onClick="updatePwd2(this)">更新</button>
											<button type="button" class="btn btn-sm btn-success" onclick="javascript:history.back();">返回</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(".ab .aaabbb").click(function(){
			var index=$(this).index();
			$(this).addClass('hover').siblings().removeClass('hover');
			$('.tab-content > div').eq(index).addClass('active').siblings().removeClass('active');
		});
		function updatePwd1(obj){
			if($("#newPwd").val()!==$("#reNewPwd").val()){
				showMsg('两次输入的登录密码不一致！');
				return false;
			}
			$.ajax({cache:true,type:"POST",url:$("#form1").attr('action'),//提交的URL
			data:$('#form1').serialize(), // 要提交的表单,必须使用name属性
			async:false,success:function(res){
				//console.log(data)
				if(res.code==0){
					layer.msg(""+res.msg+"....",{icon:1},function(){
						location.href='index';
					});
				}else{
					layer.alert(res.msg,{title:'错误提示',icon:0});
					$(obj).attr("disabled",false);
				}
			},error:function(request){
				alert("Connection error");
			}});
		}
		function updatePwd2(obj){
			if($("#newPwd2").val()!==$("#reNewPwd2").val()){
				showMsg('两次输入的二级密码不一致！');
				return false;
			}
			$.ajax({cache:true,type:"POST",url:$("#form2").attr('action'),//提交的URL
			data:$('#form2').serialize(), // 要提交的表单,必须使用name属性
			async:false,success:function(res){
				//console.log(data)
				if(res.code==0){
					layer.msg(""+res.msg+"....",{icon:1},function(){
						location.href='index';
					});
				}else{
					layer.alert(res.msg,{title:'错误提示',icon:0});
					$(obj).attr("disabled",false);
				}
			},error:function(request){
				alert("Connection error");
			}});
		}
	</script>
</body>
</html>