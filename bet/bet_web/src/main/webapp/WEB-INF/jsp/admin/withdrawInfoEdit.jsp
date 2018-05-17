<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户提现信息</title>
<meta name="pageId" content="withdrawInfoEdit" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<script src="<%=commonJsPath%>/ajaxfileupload.js?v=20171018"></script>
<script>
	function formSubmit(status){
		if(status==0){
			status=LR_WEB.APPLY_STATUS.VERIFY_SUCCESS;
			confirmDialog("确定提交吗？","ajaxUpdateWithdrawInfoVerify",{'withdrawId':'${withdrawInfo.withdrawId}','status':status},function(data){
				showSuccessMsg("提示","审核提交成功！",function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
		}else if(status==1){
			status=LR_WEB.APPLY_STATUS.VERIFY_FAIL;
			var content='审核留言：<input type="text" id="remark0" value=""/>';
			showChooseMsg("请输入审核留言",content,'确定','取消',function(){
				var remark0=$("#remark0").val();
				if(isNull(remark0)){
					showMsg("请输入审核留言！");
					return;
				}
				ajaxTipJson("ajaxUpdateWithdrawInfoVerify",{"withdrawId":'${withdrawInfo.withdrawId}','status':status,"remark":remark0},true,"请稍后...",function(data){
					showSuccessMsg("提示","审核提交成功！",function(){
						refreshPage();
					});
				},function(msg){
					showMsg(msg);
				});
			},null);
		}
		return false;
	}
	function updatePay(){
		confirmDialog("确定提交吗？","ajaxUpdateWithdrawInfoPay",{'withdrawId':'${withdrawInfo.withdrawId}','payRemark':$("#payRemark").val(),'payPicUrl':$("#picUrl").val()},function(data){
			showSuccessMsg("提示","提交支付信息成功！",function(){
				goUrl("manageWithdrawInfos");
			});
		},function(msg){
			showMsg(msg);
		});
		return false;
	}
	// 上传图片
	function fileUpload(fileElementId,pic,uploadPicDir){
		var loadi=loading('正在上传图片...');
		// 开始上传
		$.ajaxFileUpload({url:"ajaxUploadPic?sortId=withdraw", // 需要链接到服务器地址
		secureuri:false,fileElementId:fileElementId, // 文件选择框的id属性
		dataType:'text', // 服务器返回的格式，可以是json
		success:function(data,status){
			// 去掉系统多余的字符串
			data=trimPicResponseText(data);
			data=toJson(data);
			layer.close(loadi);
			if(data.code===0){
				var picInfo=toJson(data.msg);
				var path=uploadPicDir+picInfo.picUrl;
				$("#"+pic).attr("src",path);
				$("#picUrl").val(picInfo.picUrl);
				showMsg("图片上传成功！");
			}else{
				showMsg(data.msg);
			}
		},error:function(data,status,e){
			// 去掉系统多余的字符串
			data=trimPicResponseText(data);
			data=toJson(data);
			showMsg(data.msg);
		}});
	};
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>用户提现信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">提现ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.withdrawId}</div>
						</div>
						<div class="form-group">
							<label for="userId" class="control-label col-md-3 col-sm-3 col-xs-12">账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.userId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">提现金额：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.money/100}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">实得金额：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.userMoney/100}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">收款人：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.payeeName}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">开户行：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.bankName}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">银行卡号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.bankCardId}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">留言信息：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.remark}</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">申请日期：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									writeDate('${withdrawInfo.addDateLong}');
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">提现状态：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
								<script>
									document.write(getApplyStatusString('${withdrawInfo.status}'));
								</script>
							</div>
						</div>
						<c:choose>
							<c:when test="${withdrawInfo.status == applyStatusMap.get('APPLY')||withdrawInfo.status == applyStatusMap.get('VERIFY_SUCCESS')}">
								<div class="form-group">
									<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
										<button type="button" class="btn btn-success" onclick="formSubmit(0);">审核通过</button>
										<button type="button" class="btn btn-danger" onclick="formSubmit(1);">审核不通过</button>
									</div>
								</div>
							</c:when>
							<c:when test="${withdrawInfo.status == applyStatusMap.get('VERIFY_SUCCESS')}">
								<input type="hidden" id="picUrl" name="picUrl" />
								<div class="form-group">
									<label for="name" class="control-label col-md-3 col-sm-3 col-xs-12">支付备注信息：</label>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<input type="text" id="payRemark" name="payRemark" class="form-control" placeholder="请输入支付备注信息" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12">支付凭证：</label>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<div class="div_photo no-border" style="width: 150px; height: 100%; margin: 0;">
											<div class="photo no-border">
												<img id="img_pic" style="border-radius: 0; width: 100%; height: 100%;" src="<%=mobileImgPath%>/ic_add_pressed.png" />
											</div>
											<input type="file" id="pic" name="pic" accept="image/*" onchange="fileUpload('pic','img_pic','<%=basePath%>');" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
										<button type="button" class="btn btn-success" onclick="updatePay()">支付</button>
									</div>
								</div>
							</c:when>
							<c:when test="${withdrawInfo.status == applyStatusMap.get('VERIFY_FAIL')}"></c:when>
							<c:when test="${withdrawInfo.status == applyStatusMap.get('PAYED')}">
								<div class="form-group">
									<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">支付备注信息：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">${withdrawInfo.payRemark}</div>
								</div>
								<div class="form-group">
									<label for="money" class="control-label col-md-3 col-sm-3 col-xs-12">支付凭证：</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-control-static" style="color: red;">
										<img src="<%=basePath%>${withdrawInfo.payPicUrl}" style="width: 600px;" />
									</div>
								</div>
							</c:when>
							<c:when test="${withdrawInfo.status == applyStatusMap.get('PROCESSED')}"></c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>