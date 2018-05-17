<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改领导奖返利比率</title>
<meta name="pageId" content="teamLeaderProfitConfigInfoEdit" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("name","名称不能为空！")) return false;
		if(!checkNull("teamBetMoney","团队下注金额不能为空！")) return false;
		if(!checkNull("userBetMoney","用户下注金额不能为空！")) return false;
		if(!checkNull("profitPercent","返利不能为空！")) return false;
		if(!checkNull("profitMaxMoney","封顶金额不能为空！")) return false;
		submitForm("teamLeaderProfitConfigInfo","ajaxUpdateTeamLeaderProfitConfigInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageTeamLeaderProfitConfigInfos");
			});
		},function(msg){
			showMsg(msg);
		});
		return false;
	}
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>修改领导奖返利比率</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="teamLeaderProfitConfigInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="configId" />
						<div class="form-group">
							<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">名称：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="name" class="form-control" placeholder="请输入名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="userType" class="control-label col-md-3 col-sm-3 col-xs-12">返利级别：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="userType" items="${userTypeMap}" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="teamBetMoney" class="control-label col-md-3 col-sm-3 col-xs-12">团队下注金额（单位分）：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="teamBetMoney" class="form-control" placeholder="请输入团队下注金额" />
							</div>
						</div>
						<div class="form-group">
							<label for="userBetMoney" class="control-label col-md-3 col-sm-3 col-xs-12">用户下注金额（单位分）：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="userBetMoney" class="form-control" placeholder="请输入用户下注金额" />
							</div>
						</div>
						<div class="form-group">
							<label for="profitPercent" class="control-label col-md-3 col-sm-3 col-xs-12">返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="profitPercent" class="form-control" placeholder="请输入返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="profitMaxMoney" class="control-label col-md-3 col-sm-3 col-xs-12">封顶金额（单位分）：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="profitMaxMoney" class="form-control" placeholder="请输入封顶金额" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
								<form:button type="button" class="btn btn-success" onclick="formSubmit();">更新</form:button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>