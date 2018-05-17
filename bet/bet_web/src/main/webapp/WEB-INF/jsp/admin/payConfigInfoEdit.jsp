<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改支付配置信息</title>
<meta name="pageId" content="payConfigInfoEdit" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("mchId","商户号不能为空！")) return false;
		if(!checkNull("payKey","密钥不能为空！")) return false;
		if(!checkNull("sortIndex","优先级不能为空！")) return false;
		submitForm("payConfigInfo","ajaxUpdatePayConfigInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("managePayConfigInfos");
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
					<h2>修改支付配置信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="payConfigInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="configId" />
						<div class="form-group">
							<label for="platform" class="control-label col-md-3 col-sm-3 col-xs-12">支付平台：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="platform" items="${payPlatformMap}" class="form-control">
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="mchId" class="control-label col-md-3 col-sm-3 col-xs-12">商户号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="mchId" class="form-control" placeholder="请输入商户号" />
							</div>
						</div>
						<div class="form-group">
							<label for="payKey" class="control-label col-md-3 col-sm-3 col-xs-12">密钥：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="payKey" class="form-control" placeholder="请输入密钥" />
							</div>
						</div>
						<div class="form-group">
							<label for="sortIndex" class="control-label col-md-3 col-sm-3 col-xs-12">优先级：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="sortIndex" value="100" class="form-control" placeholder="请输入优先级" />
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