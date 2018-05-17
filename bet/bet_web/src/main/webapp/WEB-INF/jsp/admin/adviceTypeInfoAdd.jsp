<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>添加意见反馈类型</title>
<meta name="pageId" content="adviceTypeInfoAdd" />
<meta name="groupId" content="news" />
<meta name="bodyStyle" content="" />
<script>
	document.onkeydown=function keyEnter(){
		if(event.keyCode==13){
			formSubmit();
		}
	};
	function formSubmit(){
		if(!checkNull("name","类型名称不能为空！")) return false;
		submitForm("adviceTypeInfo","ajaxAddAdviceTypeInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","添加成功！",function(){
				goUrl("manageAdviceTypeInfos");
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
					<h2>添加意见反馈类型</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="adviceTypeInfo" method="post" class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">类型名称：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="name" class="form-control" placeholder="请输入类型名称" />
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