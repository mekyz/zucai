<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改消息分类</title>
<meta name="pageId" content="newsSortEdit" />
<meta name="groupId" content="news" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("name","分类名称不能为空！")) return false;
		submitForm("newsSortInfo","ajaxUpdateNewsSortInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageNewsSorts");
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
					<h2>修改消息分类</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="newsSortInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:input type="hidden" path="id" />
						<form:input type="hidden" path="sortId" />
						<div class="form-group">
							<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">分类名称：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="name" class="form-control" placeholder="请输入分类名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="parentId" class="control-label col-md-3 col-sm-3 col-xs-12">父类别：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="parentId" items="${sortIds}" theme="simple" class="form-control"></form:select>
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