<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改团队返利比率</title>
<meta name="pageId" content="teamProfitRateInfoEdit" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("type1","一级返利不能为空！")) return false;
		if(!checkNull("type2","二级返利不能为空！")) return false;
		if(!checkNull("type3","三级返利不能为空！")) return false;
		if(!checkNull("type4","四级返利不能为空！")) return false;
		if(!checkNull("type5","五级返利不能为空！")) return false;
		if(!checkNull("type6","六级返利不能为空！")) return false;
		if(!checkNull("type7","七级返利不能为空！")) return false;
		if(!checkNull("sameLevel","平级奖不能为空！")) return false;
		submitForm("teamProfitRateInfo","ajaxUpdateTeamProfitRateInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageTeamProfitRateInfos");
			});
		},function(msg){
			showMsg(msg);
		});
		return false;
	}
	$(function(){
		$("#name").attr('readonly','readonly');
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>修改团队返利比率</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="teamProfitRateInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="rateId" />
						<div class="form-group">
							<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">名称：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="name" class="form-control" placeholder="请输入名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="type1" class="control-label col-md-3 col-sm-3 col-xs-12">一级返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="type1" class="form-control" placeholder="请输入一级返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="type2" class="control-label col-md-3 col-sm-3 col-xs-12">二级返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="type2" class="form-control" placeholder="请输入二级返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="type3" class="control-label col-md-3 col-sm-3 col-xs-12">三级返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="type3" class="form-control" placeholder="请输入三级返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="type4" class="control-label col-md-3 col-sm-3 col-xs-12">四级返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="type4" class="form-control" placeholder="请输入四级返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="type5" class="control-label col-md-3 col-sm-3 col-xs-12">五级返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="type5" class="form-control" placeholder="请输入五级返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="type6" class="control-label col-md-3 col-sm-3 col-xs-12">六级返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="type6" class="form-control" placeholder="请输入六级返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="type7" class="control-label col-md-3 col-sm-3 col-xs-12">七级返利(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="type7" class="form-control" placeholder="请输入七级返利" />
							</div>
						</div>
						<div class="form-group">
							<label for="sameLevel" class="control-label col-md-3 col-sm-3 col-xs-12">平级奖(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="sameLevel" class="form-control" placeholder="请输入平级奖" />
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