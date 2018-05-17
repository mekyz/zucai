<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户充值信息</title>
<meta name="pageId" content="userRechargeInfoEdit" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<script>
	function formSubmit(status){
		if(status==0){
			status=LR_WEB.APPLY_STATUS.VERIFY_SUCCESS;
		}else if(status==1){
			status=LR_WEB.APPLY_STATUS.VERIFY_FAIL;
		}
		confirmDialog("确定提交吗？","ajaxUpdateUserRechargeInfoVerify",{'rechargeId':'${userRechargeInfo.rechargeId}','status':status},function(data){
			showSuccessMsg("提示","审核提交成功！",function(){
				goUrl("manageUserRechargeInfos");
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
					<h2>用户充值信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">充值ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.rechargeId}</div>
						</div>
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.userId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">充值金额(￥)：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.money/100}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">付款人：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.payName}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">付款开户行：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.payBankName}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">付款银行卡号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.payBankCardId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">收款人：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.payeeName}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">收款开户行：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.payeeBankName}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">收款银行卡号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.payeeBankCardId}</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">支付凭证：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="div_photo no-border" style="width: 150px; height: 100%; margin: 0;">
									<div class="photo no-border">
										<img id="img_pic" style="border-radius: 0; width: 100%; height: 100%;" src="<%=basePath%>${userRechargeInfo.payPicUrl}" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">申请日期：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									writeDate('${userRechargeInfo.addDateLong}');
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">充值状态：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									document.write(getApplyStatusString('${userRechargeInfo.status}'));
								</script>
							</div>
						</div>
						<c:choose>
							<c:when test="${userRechargeInfo.status == applyStatusMap.get('APPLY')}">
								<div class="form-group">
									<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
										<button type="button" class="btn btn-success" onclick="formSubmit(0);">审核通过</button>
										<button type="button" class="btn btn-danger" onclick="formSubmit(1);">审核不通过</button>
									</div>
								</div>
							</c:when>
							<c:when test="${userRechargeInfo.status == applyStatusMap.get('VERIFY_SUCCESS')}">
								<div class="form-group">
									<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">留言信息：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${userRechargeInfo.payRemark}</div>
								</div>
							</c:when>
							<c:when test="${userRechargeInfo.status == applyStatusMap.get('VERIFY_FAIL')}"></c:when>
						</c:choose>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>