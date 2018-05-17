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
	<%-- <script type="text/javascript" src="<%=betDir%>/Public/static/uploadify/jquery.uploadify.min.js"></script> --%>
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
</style>
		<style>
html {
	height: 100%;
}

.main {
	height: auto !important;
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
										<i class="fa fa-indent red"></i>在线支付
									</h6>
									<div class="panel-actions"></div>
								</div>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="background: #FFFFFF;">
									<div class="pay_list" style="margin-bottom: 0;">
										<form:form modelAttribute="userRechargeInfo" method="post" class="form-horizontal ">
											<form:hidden path="moneyUnit" value="11" />
											<form:hidden path="payeeName" value="NA" />
											<form:hidden path="payeeBankName" value="NA" />
											<form:hidden path="payeeBankCardId" value="NA" />
											<form:hidden path="payPicUrl" value="NA" />
											<form:hidden path="payBankCardId" value="NA" />
											<div class="bank" style="overflow: hidden;">
												<div class="bank-change">
													<span>您的打款存款信息</span>
												</div>
												<div class="bank-center">
													<p>
														<span>存款金额：</span> <b>￥</b>
														<form:input path="money" value="" style="margin-left: -18px; padding-left: 25px;" />
													</p>
													<p>
														<span>账号信息：</span>
														<form:input path="payName" value="${userInfo.name}" placeholder="请填写您的账户姓名" />
													</p>
													<p>
														<span>开户银行：</span>
														<form:select path="payBankName">
															<form:option value="民乐支付">民乐支付</form:option>
														</form:select>
													</p>
													<p>
														<span>支付方式：</span> <select id="payType">
															<option value="WXCODE">微信扫码支付</option>
															<!-- <option value="QQCODE">QQ扫码支付</option> -->
															<option value="ALICODE">支付宝扫码支付</option>
															<!-- <option value="JDCODE">京东扫码</option> -->
															<option value="YLCODE">银联扫码</option>
															<!-- <option value="QQWAP">QQWAP</option> -->
															<!-- <option value="WXH5">微信H5支付</option> -->
															<option value="ALIWAP">支付宝WAP</option>
															<!-- <option value="QUICK">快捷支付</option> -->
														</select>
													</p>
												</div>
											</div>
											<p class="pay-txt1">
												<button type="button" onclick="formSubmit();" class="btn btn btn-success ajax-post" target-form='form-horizontal' id="stime">提交</button>
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
		/*充值金额*/
		/* $("#money").blur(function(){
			if(!check_empty('money','充值金额')){
				return false;
			}
			if(!check_number('money','充值金额')){
				return false;
			}
		}); */
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
			var mobile=15999649298;
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
			if(!checkNull("money","充值金额不能为空！")) return false;
			if(!checkNull("payName","付款人姓名不能为空！")) return false;
			if(!checkNull("payBankCardId","银行卡号不能为空！")) return false;
			$("#money").val($("#money").val()*100+"");
			var payType=$("#payType").val();
			var money=$("#money").val();
			money=(money/100).toFixed(2);
			if(payType=='WXCODE'||payType=='WXH5'){
				money=parseInt(money);
				if(money<1){
					layer.msg('微信支付需要输入整数金额！');
					return;
				}
			}
			submitForm("userRechargeInfo","ajaxAddUserRechargeInfo",true,"正在申请充值...",function(data){
				/* showSuccessMsg("提示","申请充值成功，请等待管理员审核！","确定",function(){
					goUrl('index');
				}); */
				var userRechargeInfo=toJson(data);
				if(payType=='WXCODE'||payType=='QQCODE'||payType=='ALICODE'||payType=='JDCODE'||payType=='YLCODE'){
					
					$.post("ajaxMinleQrPay",{'out_trade_no':userRechargeInfo.rechargeId,'type':payType,'totalFee':money},function(data){
						showLog('data:'+JSON.stringify(data));
						$("#money").val(money+"");
						if(data.code=='0'){
							var res=toJson(data.msg);
							//layer.msg('qr_code:'+res.qr_code);
							//goUrl(res.qr_code);
							var width=document.documentElement.clientWidth;
							var sw,sh;
							if(width<500){
								sw='85%';
								sh='85%';
							}else{
								sw='450px';
								sh='500px';
							}
							var title='';
							if(payType=='WXCODE'){
								title='请使用微信扫码支付';
							}else if(payType=='QQCODE'){
								title='请使用QQ扫码支付';
							}else if(payType=='ALICODE'){
								title='请使用支付宝扫码支付';
							}else if(payType=='JDCODE'){
								title='请使用京东扫码支付';
							}else if(payType=='YLCODE'){
								title='请使用银联扫码支付';
							}
							layui.use('layer',function(){
								layer.open({type:2,scrollbar:false,area:[sw,sh],fixed:true, //不固定
								maxmin:true,title:title,content:res.qr_code});
							});
						}else{
							layer.msg(data.error_msg);
						}
					});
				}else{
					$.post("ajaxMinleH5Pay",{'out_trade_no':userRechargeInfo.rechargeId,'type':payType,'totalFee':money},function(data){
						showLog('data:'+JSON.stringify(data));
						$("#money").val(money+"");
						if(data.code=='0'){
							var res=toJson(data.msg);
							//layer.msg('qr_code:'+res.qr_code);
							goUrl(res.pay_url);
						}else{
							layer.msg(data.error_msg);
						}
					});
				}
			},function(errmsg){
				$("#money").val(($("#money").val()/100));
				showMsg(errmsg);
			});
			return false;
		}
	</script>
</body>
</html>