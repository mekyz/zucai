<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>修改客服账号</title>
<meta name="pageId" content="adminEdit" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/cropper.css" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/initCrop.css" />
<link rel="stylesheet" href="<%=thirdDir%>/css/customer.css" />
<script>
	function formSubmit(){
		if(!checkNull("userId","账号不能为空！")) return false;
		if(!checkNull("name","姓名不能为空！")) return false;
		submitForm("adminInfo","ajaxUpdateAdminInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","更新成功！",function(){
				goUrl("manageAdmins");
			});
		},function(msg){
			showMsg(msg);
		});
		return false;
	}
	$(function(){
		$("#userId").attr("readonly","readonly");
		$("#password").val("");
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>修改客服账号</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="adminInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="picUrl" />
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="userId" class="form-control" placeholder="请输入账号" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="control-label col-md-3 col-sm-3 col-xs-12">密码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="password" path="password" value="" class="form-control" placeholder="请输入密码" />
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">姓名：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="name" class="form-control" placeholder="请输入姓名" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">LOGO图片：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="div_photo no-border" style="width: 150px; height: 100%; margin: 0;">
									<div class="photo no-border">
										<img id="img_pic" style="border-radius: 0; width: 100%; height: 100%;" src="<%=basePath%>${adminInfo.picUrl}" />
									</div>
									<input type="file" id="inputImage" name="pic" accept="image/*" />
								</div>
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
		<div id="containerDiv" class="div_container">
			<img id="img-container" src="">
			<div id="actions" class="div_btn_row">
				<button type="button" class="btn" data-method="destroy">
					<span class="docs-tooltip"> 取消</span>
				</button>
				<button type="button" id="imgCutConfirm" class="btn btn-right" data-method="getCroppedCanvas">
					<span class="docs-tooltip">确认</span>
				</button>
			</div>
		</div>
	</div>
	<script src="<%=commonJsPath%>/exif.js"></script>
	<script src="<%=commonJsPath%>/cropper/cropper.js"></script>
	<script src="<%=commonJsPath%>/cropper/initCrop.js"></script>
	<script>
		var initCropParams={imgContainerId:'img-container',divContainerId:'containerDiv',actionsId:'actions',showImgId:'img_pic',inputImageId:'inputImage',aspectRatio:320/320,
			imgSize:{width:320,height:320}};
		var fileImg="";
		$(function(){
			$("#imgCutConfirm").bind("click",function(){
				$("#"+initCropParams.divContainerId).hide();
			});
		});
		//提交表达
		function uploadPicForm(){
			$("#registerForm").attr("enctype","multipart/form-data");
			var formData=new FormData($("#registerForm")[0]);
			formData.append("upload",fileImg);
			var loadi=loading('正在上传图片...');
			$.ajax({url:"ajaxUploadBase64Pic?sortId=admin_head",type:'POST',data:formData,dataType:'json',async:true,cache:false,contentType:false,processData:false,success:function(result){
				layer.close(loadi);
				if(result.code==LR_WEB.RETURN_CODE.SUCCESS){
					var picInfo=toJson(result.msg);
					//showLog("picUrl:" + picInfo.picUrl + ".");
					$("#picUrl").val(picInfo.picUrl);
				}else{
					$("#"+initCropParams.showImgId).attr("src","");
					showMsg(result.msg);
				}
			},error:function(returndata){
				showLog(JSON.stringify(returndata));
				showMsg("上传图片错误，请重新上传！");
				$("#"+initCropParams.showImgId).attr("src","");
			}});
		}
	</script>
</body>
</html>