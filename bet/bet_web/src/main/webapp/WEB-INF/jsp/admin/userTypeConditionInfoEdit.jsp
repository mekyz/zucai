<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改用户升级条件</title>
<meta name="pageId" content="userTypeConditionInfoEdit" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("directCount","直推人数不能为空！")) return false;
		if(!checkNull("teamCount","团队人数不能为空！")) return false;
		submitForm("userTypeConditionInfo","ajaxUpdateUserTypeConditionInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageUserTypeConditionInfos");
			});
		},null);
		return false;
	}
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>修改用户升级条件信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="userTypeConditionInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="userType" />
						<div class="form-group">
							<label for="directCount" class="control-label col-md-3 col-sm-3 col-xs-12">直推人数：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="directCount" class="form-control" placeholder="请输入直推人数" />
							</div>
						</div>
						<div class="form-group">
							<label for="teamCount" class="control-label col-md-3 col-sm-3 col-xs-12">团队人数：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="teamCount" class="form-control" placeholder="请输入团队人数" />
							</div>
						</div>
						<div class="form-group">
							<label for="teamCount" class="control-label col-md-3 col-sm-3 col-xs-12">充值金额：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="money" class="form-control" placeholder="请输入充值金额" />
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