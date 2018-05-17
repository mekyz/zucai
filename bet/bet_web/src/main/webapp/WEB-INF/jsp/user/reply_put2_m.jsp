<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title><spring:message code="page_title" /></title>
<script src="<%=commonJsPath%>/ajaxfileupload.js?v=20171018"></script>
<script>
	if(!isMobileWeb()){
		window.location.href="reply_put2";
	}
</script>
<script>
	//上传图片
	function fileUpload(fileElementId,pic,uploadPicDir){
		var loadi=loading('正在上传图片...');
		// 开始上传
		$.ajaxFileUpload({url:"ajaxUploadPic?sortId=recharge", // 需要链接到服务器地址
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
				$("#payPicUrl").val(picInfo.picUrl);
				$("#"+fileElementId).parent().find('.upload-img-box').html('<div class="upload-pre-item"><img src="'+path+'" onclick="priview(\''+path+'\');" /></div>');
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
	<div class="page-header" style="background: none;"></div>
	<style>
.phone-tq {
	display: none;
}

@media ( max-width : 414px) {
	.phone-tq {
		display: block;
	}
	.phone-tq .ticker-news-box {
		padding: 14px !important;
	}
}
</style>
	<script src="<%=betDir%>/Public/Home/h+/js/clipboard.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=betDir%>/Public/Home/h+/css/pay.css" />
	<link href="<%=betDir%>/Public/Home/h+/js/layui/css/layui.css" rel="stylesheet">
	<link href="<%=betDir%>/Public/Home/h+/js/layui/css/layui.css" rel="stylesheet">
	<%-- <script type="text/javascript" src="<%=betDir%>/Public/static/uploadify/jquery.uploadify.min.js"></script> --%>
	<div class="row">
		<style>
html {
	height: 100%;
}

.main {
	height: auto !important;
}

.phone-tq {
	display: none;
}

@media ( max-width : 414px) {
	.phone-tq {
		display: block;
	}
	.phone-tq .ticker-news-box {
		padding: 14px !important;
	}
	.bank {
		width: 100%;
	}
}

.upload-pre-item img {
	height: 150px;
}

.uploadify {
	margin-top: 10px; background-color: #313131; text-align: center; border: 1px solid #6D6D6D; border-radius: 3px; color: #eaeaea;
}

.upload-pre-item {
	display: inline-block; margin-right: 2px;
}

.uploadify {
	background-color: #528aaa;
}

.bank-center p span {
	width: auto; text-align: left;
}
</style>
		<script>
			$('.main').height($('html').height()-135);
		</script>
		<div class="container-fluid content">
			<div class="row">
				<div id="content" class="col-sm-12 full">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="panel panel-default bk-bg-white">
								<div class="panel-heading bk-bg-white">
									<h6>
										<i class="fa fa-indent red"></i>转账入款
									</h6>
									<div class="panel-actions"></div>
								</div>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="background: #FFFFFF;">
									<div class="pay_list" style="margin-bottom: 0;">
										<form:form modelAttribute="userRechargeInfo" method="post" class="form-horizontal ">
											<form:hidden path="moneyUnit" value="11" />
											<form:hidden path="payeeName" value="${payeeInfo.payeeName}" />
											<form:hidden path="payeeBankName" value="${payeeInfo.bankName}" />
											<form:hidden path="payeeBankCardId" value="${payeeInfo.bankCardId}" />
											<form:hidden path="payPicUrl" />
											<h3>请选转账到以下银行账户中：</h3>
											<div class="bank">
												<div class="bank-change">
													<p style="font-size: 16px; line-height: 44px; margin-left: 55px;">
														<img src="<%=basePath%>${payeeInfo.picUrl}" />&nbsp;&nbsp;&nbsp;${payeeInfo.bankName}
													</p>
													<ul>
														<li><img src="<%=basePath%>${payeeInfo.picUrl}" />&nbsp;&nbsp;&nbsp;${payeeInfo.bankName}</li>
													</ul>
												</div>
												<div class="bank-center">
													<p>
														<em class="fr" id="copy1" onclick="copy(this)" data-clipboard-action="copy" data-clipboard-target="#name">复制</em> <span>姓名：</span><i id="name">${payeeInfo.payeeName}</i>
													</p>
													<p>
														<em class="fr" id="copy2" onclick="copy(this)" data-clipboard-action="copy" data-clipboard-target="#bankcad">复制</em> <span>账号：</span><i id="bankcad">${payeeInfo.bankCardId}</i>
													</p>
												</div>
											</div>
											<div class="bank" style="overflow: hidden;">
												<div class="bank-change">
													<span>您的打款信息</span>
												</div>
												<div class="bank-center">
													<p>
														<span>存款金额：</span> <b>￥</b>
														<form:input path="money" value="" />
													</p>
													<p>
														<span>账号信息：</span>
														<form:input path="payName" value="" placeholder="请填写您的账户姓名" />
													</p>
													<p>
														<span>开户银行：</span>
														<form:select path="payBankName">
															<form:option value="中国工商银行">中国工商银行</form:option>
															<form:option value="中国建设银行">中国建设银行</form:option>
															<form:option value="中国农业银行">中国农业银行</form:option>
															<form:option value="中国银行">中国银行</form:option>
															<form:option value="兴业银行">兴业银行</form:option>
															<form:option value="交通银行">交通银行</form:option>
															<form:option value="中国邮政储蓄银行">中国邮政储蓄银行</form:option>
															<form:option value="招商银行">招商银行</form:option>
															<form:option value="光大银行">光大银行</form:option>
															<form:option value="浦发银行">浦发银行</form:option>
															<form:option value="华夏银行">华夏银行</form:option>
															<form:option value="平安银行">平安银行</form:option>
															<form:option value="民生银行">民生银行</form:option>
															<form:option value="中信银行">中信银行</form:option>
															<form:option value="渣打银行">渣打银行</form:option>
															<form:option value="花旗银行">花旗银行</form:option>
															<form:option value="广发银行">广发银行</form:option>
														</form:select>
													</p>
													<p>
														<span>银行卡号：</span>
														<form:input path="payBankCardId" value="" placeholder="请填写银行卡号后五位" maxlength="5" />
													</p>
													<p style="float: left;">
														<span>转账截图：</span>
													<div style="float: left;">
														<input type="file" id="pic" name="pic" onchange="fileUpload('pic','img_pic','<%=basePath%>');">
														<div class="upload-img-box"></div>
													</div>
													</p>
												</div>
											</div>
											<p class="pay-txt1">
												<button type="button" id="btnSubmit" onclick="formSubmit();" class="btn btn btn-success ajax-post" target-form='form-horizontal'>提交</button>
												<button type="button" class="btn btn-danger" onclick="javascript:history.back();">返回</button>
											</p>
										</form:form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		/*充值金额*/
		$("#money").blur(function(){
			if(!check_empty('money','充值金额')){
				return false;
			}
			if(!check_number('money','充值金额')){
				return false;
			}
		});
		/*银行卡号*/
		$("#payBankCardId").blur(function(){
			if(!check_empty('payBankCardId','银行卡号')){
				return false;
			}
			if(!check_number('payBankCardId','银行卡号')){
				return false;
			}
			/* if(!check_Size('payBankCardId','银行卡号','16','19')){
				return false;
			} */
		});
		function copy(id){
			var id=$(id).attr('id');
			var clipboard2=new Clipboard('#'+id);
			clipboard2.on('success',function(e){
				layer.msg("复制成功！",{icon:3});
			});
			clipboard2.on('error',function(e){
				layer.msg("复制失败！请手动复制！",{icon:3});
			});
		}
		function formSubmit(){
			if(!checkNull("money","充值金额不能为空！")) return false;
			if(!checkNull("payName","付款人姓名不能为空！")) return false;
			if(!checkNull("payBankCardId","银行卡号不能为空！")) return false;
			if(!checkNull("payPicUrl","请上传转账截图！")) return false;
			//if(!checkNull("code","请输入验证码！")) return false;
			$("#btnSubmit").attr('disabled','disabled');
			$.ajax({
				url:"ajaxAddUserRechargeInfo",
				type:"post",
				data:{'moneyUnit':$("#moneyUnit").val(),'payeeName':$("#payeeName").val(),'payeeBankName':$("#payeeBankName").val(),'payeeBankCardId':$("#payeeBankCardId").val(),
					'payPicUrl':$("#payPicUrl").val(),'money':parseInt($("#money").val()*100),'payName':$("#payName").val(),'payBankName':$("#payBankName").val(),
					'payBankCardId':$("#payBankCardId").val(),'code':$("#code").val(),'code':$("#code").val(),'remark':$("#remark").val()},success:function(res){
					if(res.code==0){
						showSuccessMsg("提示","申请充值成功，请等待管理员审核！","确定",function(){
							goUrl('rechargerecord');
						});
					}else{
						layer.alert(res.msg,{title:'错误提示',icon:0});
					}
					$("#btnSubmit").attr('enabled','enabled');
				}});
			/* $("#money").val($("#money").val()*100+"");
			submitForm("userRechargeInfo","ajaxAddUserRechargeInfo",true,"正在申请充值...",function(data){
				showSuccessMsg("提示","申请充值成功，请等待管理员审核！","确定",function(){
					goUrl('index');
				});
			},function(errmsg){
				$("#money").val(($("#money").val()/100).toFixed(2));
				showMsg(errmsg);
			}); */
			return false;
		}
	</script>
</body>
</html>