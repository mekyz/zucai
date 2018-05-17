<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改用户消息</title>
<meta name="pageId" content="msgInfoEdit" />
<meta name="groupId" content="news" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("userId","用户ID不能为空！")) return false;
		if(!checkNull("title","标题不能为空！")) return false;
		if(!checkNull("shortContent","简述不能为空！")) return false;
		submitForm("msgInfo","ajaxUpdateMsgInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageMsgInfos");
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
					<h2>修改用户消息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="msgInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:input type="hidden" path="msgId" />
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">用户ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="userId" class="form-control" placeholder="请输入用户ID" />
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">标题：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="title" class="form-control" placeholder="请输入标题" />
							</div>
						</div>
						<div class="form-group">
							<label for="sortId" class="control-label col-md-3 col-sm-3 col-xs-12">消息类型：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="sortId" items="${msgTypeMap}" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="shortContent" class="control-label col-md-3 col-sm-3 col-xs-12">简述：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="shortContent" class="form-control" placeholder="请输入简述" />
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