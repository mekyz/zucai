<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>添加客户端</title>
<meta name="pageId" content="clientInfoAdd" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<script src="<%=commonJsPath%>/ajaxfileupload.js"></script>
<script>
	function formSubmit(){
		if(!checkNull("versionName","版本号不能为空！")) return false;
		if(!checkNull("versionCode","版本数字不能为空！")) return false;
		if(!checkNull("url","升级地址不能为空！")) return false;
		if(!checkNull("description","升级记录不能为空！")) return false;
		submitForm("clientInfo","ajaxAddClientInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","添加成功！",function(){
				goUrl("manageClients");
			});
		},null);
		return false;
	}
	// 上传文件
	function uploadFile(){
		var loadi=loading('正在上传文件...');
		// 开始上传
		$.ajaxFileUpload({url:"ajaxUploadFile?sortId=android", // 需要链接到服务器地址
		secureuri:false,fileElementId:"appFile", // 文件选择框的id属性
		dataType:'text', // 服务器返回的格式，可以是json
		success:function(data,status){
			layer.close(loadi);
			data=trimPicResponseText(data);
			data=toJson(data);
			if(data.code===0){
				var picUrl=data.msg;
				$("#url").val("${serverUrl}"+picUrl);
				showMsg("文件上传成功！");
			}else{
				showMsg(data.msg);
			}
		},error:function(data,status,e){
			data=trimPicResponseText(data);
			data=toJson(data);
			showMsg("上传失败："+data.msg);
		}});
	};
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>添加客户端</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="clientInfo" method="post" class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="platform" class="control-label col-md-3 col-sm-3 col-xs-12">平台 ：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="platform" class="form-control">
									<form:option value="android">安卓</form:option>
									<form:option value="ios">苹果</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="clientVersion" class="control-label col-md-3 col-sm-3 col-xs-12">合伙人ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="partnerId" class="form-control" placeholder="请输入合伙人ID" />
							</div>
						</div>
						<div class="form-group">
							<label for="clientVersion" class="control-label col-md-3 col-sm-3 col-xs-12">代理商ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="agentId" class="form-control" placeholder="请输入代理商ID" />
							</div>
						</div>
						<div class="form-group">
							<label for="clientVersion" class="control-label col-md-3 col-sm-3 col-xs-12">版本号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="versionName" class="form-control" placeholder="请输入客户端版本号" />
							</div>
						</div>
						<div class="form-group">
							<label for="clientCode" class="control-label col-md-3 col-sm-3 col-xs-12">版本数字：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="versionCode" class="form-control" placeholder="请输入客户端版本数字" />
							</div>
						</div>
						<div class="form-group">
							<label for="url" class="control-label col-md-3 col-sm-3 col-xs-12">升级地址：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="url" class="form-control" placeholder="请输入升级地址" />
							</div>
						</div>
						<div class="form-group">
							<label for="appFile" class="control-label col-md-3 col-sm-3 col-xs-12">上传安装包：</label>
							<div class="col-md-4 col-sm-4 col-xs-12">
								<input type="file" id="appFile" name="upload" accept=".apk" />
							</div>
							<div class="col-md-2 col-sm-2 col-xs-12">
								<button type="button" class="btn btn-success" onclick="uploadFile();">上传</button>
							</div>
						</div>
						<div class="form-group">
							<label for="updateDesc" class="control-label col-md-3 col-sm-3 col-xs-12">升级记录：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:textarea path="description" class="form-control" style="height:200px;"></form:textarea>
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