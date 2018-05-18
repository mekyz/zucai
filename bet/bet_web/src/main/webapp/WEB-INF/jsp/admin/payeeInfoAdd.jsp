<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>添加收款信息</title>
<meta name="pageId" content="payeeInfoAdd" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<script src="<%=commonJsPath%>/ajaxfileupload.js?v=20171018"></script>
<script>
	function formSubmit(){
		if(!checkNull("payeeName","收款人姓名不能为空！")) return false;
		if(!checkNull("bankCardId","银行卡号不能为空！")) return false;
		if(!checkNull("sortIndex","优先级不能为空！")) return false;
		if(!checkNull("picUrl","银行LOGO不能为空！")) return false;
		submitForm("payeeInfo","ajaxAddPayeeInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","添加成功！",function(){
				goUrl("managePayeeInfos");
			});
		},null);
		return false;
	}
	// 上传图片
	function fileUpload(fileElementId,pic,uploadPicDir){
		var loadi=loading('正在上传图片...');
		// 开始上传
		$.ajaxFileUpload({url:"ajaxUploadPic?sortId=payee", // 需要链接到服务器地址
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
					<h2>添加收款信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="payeeInfo" method="post" class="form-horizontal form-label-left input_mask">
						<form:hidden path="picUrl" />
						<div class="form-group">
							<label for="payeeName" class="control-label col-md-3 col-sm-3 col-xs-12">收款人姓名：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="payeeName" class="form-control" placeholder="请输入收款人姓名" />
							</div>
						</div>
						<div class="form-group">
							<label for="bankName" class="control-label col-md-3 col-sm-3 col-xs-12">开户行/第三方支付：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="bankName" class="form-control">
									<form:option value="工商银行">工商银行</form:option>
									<form:option value="建设银行">建设银行</form:option>
									<form:option value="农业银行">农业银行</form:option>
									<form:option value="中国银行">中国银行</form:option>
									<form:option value="兴业银行">兴业银行</form:option>
									<form:option value="交通银行">交通银行</form:option>
									<form:option value="中国邮政">中国邮政</form:option>
									<form:option value="招商银行">招商银行</form:option>
									<form:option value="光大银行">光大银行</form:option>
									<form:option value="浦发银行">浦发银行</form:option>
									<form:option value="华夏银行">华夏银行</form:option>
									<form:option value="农商银行">农商银行</form:option>
									<form:option value="平安银行">平安银行</form:option>
									<form:option value="民生银行">民生银行</form:option>
									<form:option value="中信银行">中信银行</form:option>
									<form:option value="渣打银行">渣打银行</form:option>
									<form:option value="花旗银行">花旗银行</form:option>
									<form:option value="广发银行">广发银行</form:option>
									<form:option value="支付宝">支付宝</form:option>
									<form:option value="微信">微信</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="bankCardId" class="control-label col-md-3 col-sm-3 col-xs-12">银行卡号/第三方账号：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="bankCardId" class="form-control" placeholder="请输入银行卡号/第三方账号" />
							</div>
						</div>
						<div class="form-group">
							<label for="sortIndex" class="control-label col-md-3 col-sm-3 col-xs-12">优先级：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="sortIndex" value="100" class="form-control" placeholder="请输入优先级" />
							</div>
						</div>
						<div class="form-group">
							<label for="type" class="control-label col-md-3 col-sm-3 col-xs-12">收款类型：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="type" items="${payeeTypeMap}" class="form-control">
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="picUrl" class="control-label col-md-3 col-sm-3 col-xs-12">LOGO：</label>
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