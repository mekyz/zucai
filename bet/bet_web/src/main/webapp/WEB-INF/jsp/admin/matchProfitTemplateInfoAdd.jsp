<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>添加波胆模板信息</title>
<meta name="pageId" content="matchProfitTemplateInfoAdd" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("score1","主场得分不能为空！")) return false;
		if(!checkNull("score2","客场得分不能为空！")) return false;
		if(!checkNull("profitPercent","收益不能为空！")) return false;
		if(!checkNull("amount","份数不能为空！")) return false;
		submitForm("matchProfitTemplateInfo","ajaxAddMatchProfitTemplateInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","添加成功！",function(){
				goUrl("manageMatchProfitTemplateInfos");
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
					<h2>添加波胆模板信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="matchProfitTemplateInfo" method="post" class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="matchType" class="control-label col-md-3 col-sm-3 col-xs-12">波胆类型：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="matchType" items="${bodanTypeMap}" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="score1" class="control-label col-md-3 col-sm-3 col-xs-12">主场得分：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="score1" class="form-control" placeholder="请输入主场得分" />
							</div>
						</div>
						<div class="form-group">
							<label for="score2" class="control-label col-md-3 col-sm-3 col-xs-12">客场得分：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="score2" class="form-control" placeholder="请输入客场得分" />
							</div>
						</div>
						<div class="form-group">
							<label for="profitPercent" class="control-label col-md-3 col-sm-3 col-xs-12">收益(万分比)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="profitPercent" class="form-control" placeholder="请输入收益" />
							</div>
						</div>
						<div class="form-group">
							<label for="amount" class="control-label col-md-3 col-sm-3 col-xs-12">份数(单位:分)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="amount" class="form-control" placeholder="请输入份数" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
								<form:button type="button" class="btn btn-success" onclick="formSubmit();">添加</form:button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>