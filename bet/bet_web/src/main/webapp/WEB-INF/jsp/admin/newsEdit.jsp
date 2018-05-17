<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改消息</title>
<meta name="pageId" content="newsEdit" />
<meta name="groupId" content="news" />
<meta name="bodyStyle" content="" />
<script src="<%=commonJsPath%>/ckeditor/ckeditor.js"></script>
<script>
	function formSubmit(){
		$("#content").val(editor.getData());//获取编辑的值
		if(!checkNull("title","标题不能为空！")) return false;
		if(!checkNull("shortContent","简述不能为空！")) return false;
		if(!checkNull("content","内容不能为空！")) return false;
		submitForm("newsInfo","ajaxUpdateNewsInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageNews");
			});
		},function(msg){
			showMsg(msg);
		});
		return false;
	}
	$(function(){
		editor=CKEDITOR.replace('content'); //参数"content"是textarea元素的name属性值，而非id属性值
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>修改消息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="newsInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:input type="hidden" path="id" />
						<form:input type="hidden" path="newsId" />
						<div class="form-group">
							<label for="title" class="control-label col-md-3 col-sm-3 col-xs-12">标题：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="title" class="form-control" placeholder="请输入标题" />
							</div>
						</div>
						<div class="form-group">
							<label for="sortId" class="control-label col-md-3 col-sm-3 col-xs-12">消息类型：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="sortId" items="${sortIds}" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="sortId" class="control-label col-md-3 col-sm-3 col-xs-12">顶部滚动显示：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="topStatus" theme="simple" class="form-control">
									<form:option value="0">是</form:option>
									<form:option value="1">否</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="shortContent" class="control-label col-md-3 col-sm-3 col-xs-12">简述：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="shortContent" class="form-control" placeholder="请输入简述" />
							</div>
						</div>
						<div class="form-group">
							<label for="content" class="control-label col-md-3 col-sm-3 col-xs-12">内容：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:textarea path="content" style="height: 200px;" placeholder="请输入内容"></form:textarea>
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