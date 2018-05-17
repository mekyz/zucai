<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改验证码配置</title>
<meta name="pageId" content="smsCodeConfigEdit" />
<meta name="groupId" content="manageClients" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(){
		if(!checkNull("name","名称不能为空！")) return false;
		if(!checkNull("platform","验证码平台不能为空！")) return false;
		if(!checkNull("topic","主题不能为空！")) return false;
		if(!checkNull("appKey","AppKey不能为空！")) return false;
		if(!checkNull("appSecret","AppSecret不能为空！")) return false;
		if(!checkNull("smsType","短信类型不能为空！")) return false;
		if(!checkNull("smsParams","短信参数不能为空！")) return false;
		if(!checkNull("smsTemplateCode","短信模板不能为空！")) return false;
		if(!checkNull("mnsEndpoint","MNSEndpoint不能为空！")) return false;
		submitForm("smsCodeConfigInfo","ajaxUpdateSmsCodeConfigInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageSmsCodeConfigs");
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
					<h2>修改验证码配置</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="smsCodeConfigInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="configId" />
						<div class="form-group">
							<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">名称：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="name" class="form-control" placeholder="请输入名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="platform" class="control-label col-md-3 col-sm-3 col-xs-12">验证码平台：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="platform" class="form-control" placeholder="请输入验证码平台" />
							</div>
						</div>
						<div class="form-group">
							<label for="url" class="control-label col-md-3 col-sm-3 col-xs-12">服务器地址：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="url" class="form-control" placeholder="请输入服务器地址" />
							</div>
						</div>
						<div class="form-group">
							<label for="topic" class="control-label col-md-3 col-sm-3 col-xs-12">主题：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="topic" class="form-control" placeholder="请输入主题" />
							</div>
						</div>
						<div class="form-group">
							<label for="appKey" class="control-label col-md-3 col-sm-3 col-xs-12">AppKey：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="appKey" class="form-control" placeholder="请输入AppKey" />
							</div>
						</div>
						<div class="form-group">
							<label for="appSecret" class="control-label col-md-3 col-sm-3 col-xs-12">AppSecret：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="appSecret" class="form-control" placeholder="请输入AppSecret" />
							</div>
						</div>
						<div class="form-group">
							<label for="smsType" class="control-label col-md-3 col-sm-3 col-xs-12">短信类型：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="smsType" items="${smsCodeTypeMap}" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="signName" class="control-label col-md-3 col-sm-3 col-xs-12">短信签名：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:textarea path="signName" style="width:100%;height: 100px;" placeholder="请输入短信签名"></form:textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="smsParams" class="control-label col-md-3 col-sm-3 col-xs-12">短信参数：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:textarea path="smsParams" style="width:100%;height: 100px;" placeholder="请输入短信参数"></form:textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="smsTemplateCode" class="control-label col-md-3 col-sm-3 col-xs-12">短信模板：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="smsTemplateCode" class="form-control" placeholder="请输入短信模板" />
							</div>
						</div>
						<div class="form-group">
							<label for="mnsEndpoint" class="control-label col-md-3 col-sm-3 col-xs-12">MNSEndpoint：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:textarea path="mnsEndpoint" style="width:100%;height: 100px;" placeholder="请输入mnsEndpoint"></form:textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="sortIndex" class="control-label col-md-3 col-sm-3 col-xs-12">排序序号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="sortIndex" class="form-control" placeholder="请输入排序序号" />
							</div>
						</div>
						<div class="form-group">
							<label for="validateSeconds" class="control-label col-md-3 col-sm-3 col-xs-12">有效时间(秒)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="validateSeconds" class="form-control" placeholder="请输入有效时间" />
							</div>
						</div>
						<div class="form-group">
							<label for="remark" class="control-label col-md-3 col-sm-3 col-xs-12">备注：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:textarea path="remark" style="width:100%;height: 100px;" placeholder="请输入备注"></form:textarea>
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