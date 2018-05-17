<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改密码</title>
<meta name="pageId" content="changePwd" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<script>
	document.onkeydown=function keyEnter(){
		if(event.keyCode==13){
			formSubmit();
		}
	};
	function formSubmit(){
		if(!checkNull("userId","账号不能为空！")) return false;
		if(!checkNull("password","原密码不能为空！")) return false;
		if(!checkNull("newPassword","新密码不能为空！")) return false;
		if(!checkNull("rePassword","确认密码不能为空！")) return false;
		if($("#newPassword").val()!==$("#rePassword").val()){
			showMsg('两次输入的密码不一致！');
			return false;
		}
		submitForm("adminInfo","ajaxChangePwd",true,"请稍后...",function(data){
			showSuccessMsg("提示","修改密码成功！",function(){
				goUrl("index");
			});
		},function(msg){
			showMsg(msg);
		});
		return false;
	}
	$(document).ready(function(){
		$("#userId").attr("readonly","readonly");
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>修改密码</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="adminInfo" method="post" class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">登录账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="userId" readonly="readonly" class="form-control" placeholder="请输入登录账号！" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="control-label col-md-3 col-sm-3 col-xs-12">原密码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="password" path="password" class="form-control" placeholder="请输入原登录密码！" />
							</div>
						</div>
						<div class="form-group">
							<label for="newPassword" class="control-label col-md-3 col-sm-3 col-xs-12">新密码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="请输入新登录密码！" />
							</div>
						</div>
						<div class="form-group">
							<label for="rePassword" class="control-label col-md-3 col-sm-3 col-xs-12">确认密码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="password" id="rePassword" name="rePassword" class="form-control" placeholder="请再次输入登录密码！" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
								<form:button type="button" class="btn btn-success" onclick="formSubmit();">提交</form:button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>