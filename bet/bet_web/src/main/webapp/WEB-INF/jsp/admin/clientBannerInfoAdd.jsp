<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>添加客户端Banner</title>
<meta name="pageId" content="clientBannerInfoAdd" />
<meta name="groupId" content="manageClients" />
<meta name="bodyStyle" content="" />
<script src="<%=commonJsPath%>/ajaxfileupload.js?v=20171018"></script>
<script>
	function formSubmit(){
		submitForm("clientBannerInfo","ajaxAddClientBannerInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","添加成功！",function(){
				goUrl("manageClientBannerInfos");
			});
		},function(errmsg){
			showMsg(errmsg);
		});
		return false;
	}
	function getPartnerList(){
		ajaxDataJson("ajaxGetUserInfoList","userType="+LR_WEB.USER_TYPE.PARTNER,function(data){
			var partnerList=data.data;
			var content="<option value =''>请选择</option>";
			if(partnerList.length>0){
				for( var index in partnerList){
					var partnerInfo=partnerList[index];
					content+="<option value ='"+partnerInfo.userId+"'>"+partnerInfo.nickname+"("+partnerInfo.userId+")"+"</option>";
				}
			}
			$("#partnerId").html(content);
		});
	}
	function getAgentList(){
		var partnerId=$("#partnerId").val();
		ajaxDataJson("ajaxGetUserInfoList","partnerId="+partnerId+"&userType="+LR_WEB.USER_TYPE.AGENT,function(data){
			var agentList=data.data;
			var content="<option value =''>请选择</option>";
			if(agentList.length>0){
				for( var index in agentList){
					var agentInfo=agentList[index];
					content+="<option value ='"+agentInfo.userId+"'>"+agentInfo.nickname+"("+agentInfo.userId+")"+"</option>";
				}
			}
			$("#agentId").html(content);
		});
	}
	$(function(){
		getPartnerList();
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>添加客户端Banner</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="clientBannerInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="picUrl" />
						<div class="form-group">
							<label for="bannerPosition" class="control-label col-md-3 col-sm-3 col-xs-12">合伙人：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="partnerId" theme="simple" class="form-control" onchange="getAgentList();"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="bannerPosition" class="control-label col-md-3 col-sm-3 col-xs-12">代理商：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="agentId" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="bannerPosition" class="control-label col-md-3 col-sm-3 col-xs-12">客户端位置：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="bannerPosition" items="${clientPositionMap}" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="bannerType" class="control-label col-md-3 col-sm-3 col-xs-12">客户端类型：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="bannerType" items="${clientTypeMap}" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">功能名：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="name" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="content" class="control-label col-md-3 col-sm-3 col-xs-12">事件内容：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="text" path="content" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">图片：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="col-xs-9" style="padding-left: 0; padding-right: 0;">
									<input type="file" id="upload" name="pic" accept="image/*" />
									<p class="help-block">支持JPG,PNG,BMP格式图片，启动页宽高比为9:16，一般制作720*1280的分辨率，展示图宽高比为5:3，一般制作720*432的分辨率。</p>
								</div>
								<div class="col-xs-3" style="padding-left: 0; padding-right: 0;">
									<button type="button" class="btn btn-primary" onclick="fileUpload('upload','banner','uploadImg','picUrl','<%=basePath%>');">上传</button>
								</div>
								<div class="col-xs-12" style="padding-left: 0; padding-right: 0;">
									<img id="uploadImg" width="50%" />
								</div>
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