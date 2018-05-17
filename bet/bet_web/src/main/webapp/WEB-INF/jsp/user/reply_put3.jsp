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
	<script type="text/javascript" src="<%=betDir%>/Public/static/uploadify/jquery.uploadify.min.js"></script>
	<div class="row">
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

#SWFUpload_0 {
	margin-left: -62px;
}

.main {
	height: auto !important;
}
</style>
		<style>
html {
	height: 100%;
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
										<i class="fa fa-indent red"></i>支付宝入款
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
													<input type="hidden" name="bankid" id="bankid" value="28" />
													<ul>
														<li data-val="28"><img src="<%=basePath%>${payeeInfo.picUrl}" />&nbsp;&nbsp;&nbsp;${payeeInfo.bankName}</li>
													</ul>
												</div>
												<script>
													/* $('.bank-change p').click(function(e){
														$(this).toggleClass('hover');
														$('.bank-change ul').toggle();
													}) */
													$('.bank-change ul li').click(function(){
														var imgsrc=$(this).find('img').attr('src');
														$('.bank-change p').html('<img src="'+ imgsrc +'"/>')
														$('.bank-change ul').hide();
														$('.bank-change p').removeClass('hover');
														var val_id=$(this).attr('data-val');
														$('#bankid').val(val_id);
														$('#name').html('${payeeInfo.payeeName}');
														$('#bankcad').html('${payeeInfo.bankCardId}');
													});
												</script>
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
														<form:input path="money" value="" style="margin-left: -18px; padding-left: 25px;" />
													</p>
													<p>
														<span>认证姓名：</span>
														<form:input path="payName" value="" placeholder="请填写您的支付宝实名姓名" />
													</p>
													<p>
														<span>开户银行：</span>
														<form:select path="payBankName">
															<form:option value="支付宝">支付宝</form:option>
														</form:select>
													</p>
													<p>
														<span>账号信息：</span>
														<form:input path="payBankCardId" value="" placeholder="请填写支付宝账号信息" />
													</p>
													<p>
														<span>手机号码：</span> <input type="text" name="" id="" value="15999649298" disabled="disabled" />
													</p>
													<p style="float: left;">
														<span>转账截图：</span>
													<div style="float: left;">
														<input type="file" id="pic" name="pic" onchange="fileUpload('pic','img_pic','<%=basePath%>');">
														<div class="upload-img-box"></div>
														<%-- <script type="text/javascript">
															//上传图片
															/* 初始化上传插件 */
															$("#upload_picture_goods_ico").uploadify(
																{"height":30,"swf":"<%=betDir%>
															/Public/static/uploadify/uploadify.swf","fileObjName":"download",
																	"buttonText":"添加<b style='font-size:16px;color:#fff !important;'>+</b>",
																	"uploader":"/index.php?s=/home/file/uploadpicture/session_id/qmqplc88srcc2fq2m55957u011.html","width":70,'removeTimeout':1,
																	'fileTypeExts':'*.jpg; *.png; *.gif;',"onUploadSuccess":uploadPicturegoods_ico,'onFallback':function(){
																		alert('未检测到兼容版本的Flash.');
																	}});
															function uploadPicturegoods_ico(file,data){
																var data=$.parseJSON(data);
																var src='';
																if(data.status){
																	$("#cover_id_goods_ico").val(data.id);
																	src=data.url||''+data.path
																	$("#cover_id_goods_ico").parent().find('.upload-img-box').html(
																		'<div class="upload-pre-item"><img src="'+src+'" onclick="priview(\''+src+'\');" /></div>');
																}else{
																	layer.msg(data.info,{icon:2});
																}
															}
														</script> --%>
													</div>
													</p>
												</div>
											</div>
											<p class="pay-txt1">
												<button type="button" onclick="formSubmit();" class="btn btn btn-success ajax-post" target-form='form-horizontal'>提交</button>
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
		/*手机号*/
		$("#mobile").blur(function(){
			if(!check_empty('mobile','手机号')){
				return false;
			}
			if(!check_phone('mobile','手机号')){
				return false;
			}
		});
		/*提现金额*/
		$("#money").blur(function(){
			if(!check_empty('money','充值金额')){
				return false;
			}
			if(!check_number('money','充值金额')){
				return false;
			}
		});
		/*银行卡号*/
		$("#banknumber").blur(function(){
			if(!check_empty('banknumber','银行卡号')){
				return false;
			}
			if(!check_number('banknumber','银行卡号')){
				return false;
			}
			if(!check_Size('banknumber','银行卡号','16','19')){
				return false;
			}
			//if(!luhmCheck('banknumber','银行卡号','16','19')){return false;}
		});
		$("#wechat").blur(function(){
			if(!check_empty('wechat','微信号')){
				return false;
			}
			if(!check_number('banknumber','微信号')){
				return false;
			}
		});
		$("#alipay").blur(function(){
			if(!check_empty('alipay','支付宝号')){
				return false;
			}
			if(!check_number('alipay','支付宝号')){
				return false;
			}
		});
		/*开户人*/
		$("#bankholder").blur(function(){
			if(!check_empty('bankholder','开户人')){
				return false;
			}
		});
		/*开户行*/
		$("#bankname").blur(function(){
			if(!check_empty('bankname','开户行')){
				return false;
			}
		});
		/*开户行地址*/
		$("#bonkaddress").blur(function(){
			if(!check_empty('bonkaddress','开户行地址')){
				return false;
			}
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
		function timesecond(o,intDiff){
			var mobile='${userInfo.number}';
			var type='0';
			if(mobile==''){
				alert('手机号不能为空!');
			}
			if(!(/^1[34578]\d{9}$/.test(mobile))){
				alert('手机号码有误!');
			}
			$.post("/index.php?s=/home/trading_hall/get_note.html",{mobile:mobile,type:type},function(data){
				if(data.status=='1'){
					var ss=setInterval(function(){
						var second=0; //默认值        
						if(intDiff>0){
							second=Math.floor(intDiff);
						}
						$(o).html("重置发送时间"+second+"秒");
						if(intDiff==0){
							$(o).html("获取");
							clearInterval(ss);
							$('.get').removeClass('disabled');
						}
						intDiff--;
					},1000);
				}else{
					layer.msg(data.msg);
				}
			});
		}
		$('body').on('click','.get',function(){
			timesecond(this,'59');
			$('.get').addClass('disabled');
		});
		function formSubmit(){
			//if(!checkNull("number","手机号码不能为空！")) return false;
			if(!checkNull("money","充值金额不能为空！")) return false;
			if(!checkNull("payName","付款人姓名不能为空！")) return false;
			if(!checkNull("payBankCardId","银行卡号不能为空！")) return false;
			//if(!checkNull("code","请输入验证码！")) return false;
			/* if(!$("#number").val().match(/^1\d{10}$/)){
				showMsg('请输入有效的手机号码！');
				return;
			} */
			$("#money").val($("#money").val()*100+"");
			submitForm("userRechargeInfo","ajaxAddUserRechargeInfo",true,"正在申请充值...",function(data){
				showSuccessMsg("提示","申请充值成功，请等待管理员审核！","确定",function(){
					goUrl('index');
				});
			},function(errmsg){
				$("#money").val(($("#money").val()/100).toFixed(2));
				showMsg(errmsg);
			});
			return false;
		}
	</script>
</body>
</html>