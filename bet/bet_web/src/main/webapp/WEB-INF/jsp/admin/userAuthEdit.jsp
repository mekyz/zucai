<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户实名认证信息</title>
<meta name="pageId" content="userAuthEdit" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(status){
		if(status==0){
			status=LR_WEB.APPLY_STATUS.VERIFY_SUCCESS;
		}else if(status==1){
			status=LR_WEB.APPLY_STATUS.VERIFY_FAIL;
		}
		var remark=$("#remark").val();
		confirmDialog("确定提交吗？","ajaxUpdateUserAuthInfo","userId=${userAuthInfo.userId}&status="+status+"&remark="+remark,function(data){
			showSuccessMsg("提示","审核提交成功！",function(){
				goUrl("manageUserAuths");
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
					<h2>用户实名认证信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userAuthInfo.userId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">姓名：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userAuthInfo.name}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">手机号码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userAuthInfo.number}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">身份证号码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userAuthInfo.identifityNumber}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">性别：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									document.write(getSexTypeString('${userAuthInfo.sex}'));
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">出生日期：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userAuthInfo.birthday}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">审核状态：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									document.write(getApplyStatusString('${userAuthInfo.status}'));
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="remark" class="control-label col-md-3 col-sm-3 col-xs-12">备注：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<textarea id="remark" style="width: 100%; height: 100px;" placeholder="请输入备注">${userAuthInfo.remark}</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
								<button type="button" class="btn btn-success" onclick="formSubmit(0);">审核通过</button>
								<button type="button" class="btn btn-danger" onclick="formSubmit(1);">审核不通过</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>