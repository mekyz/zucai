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
<script>
	if(isMobileWeb()){
		window.location.href="wdreply_m";
	}
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
	<style>
.withdrawals-from span {
	width: 270px;
}
/* .layui-layer-content{text-align:center !important} */
</style>
	<link rel="stylesheet" type="text/css" href="<%=betDir%>/Public/Home/h+/css/pay.css" />
	<div class="container-fluid content">
		<div class="row">
			<div id="content" class="col-sm-12 full">
				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="panel panel-default bk-bg-white">
							<div class="panel-heading bk-bg-white">
								<h6>
									<i class="fa fa-indent red"></i>提现申请
								</h6>
								<div class="panel-actions"></div>
							</div>
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="background: #FFFFFF;">
								<form:form modelAttribute="withdrawInfo" method="post" class="form-horizontal">
									<form:hidden path="moneyUnit" value="11" />
									<div class="pay_list">
										<div class="prompt">
											<h2>温馨提示：</h2>
											<p>每次提现最少${minWithdraw/100}￥以上，请务必确认填写正确账号，若提供的账号错误，恕本平台无法及时处理。</p>
											<p>请妥善保管账户资料，如出现泄露，本平台不予负责。</p>
											<p>如利用平台进行任何洗钱诈骗行为，本平台将保留权利终止代理服务及冻结其账户。</p>
											<p>日提现时间：9点开始，23点结束</p>
										</div>
										<div class="withdrawals">
											<img src="<%=betDir%>/Public/Home/h+/images/withdrawals.jpg" />
										</div>
										<p class="withdrawals-txt1">
											<span>会员编号：<em>${userInfo.userId}[${userInfo.name}]</em></span> <span>可用彩金：<em>${userInfo.point/100}</em></span>
										</p>
										<div class="withdrawals-from">
											<p>
												<span>提现金额：</span> <b>￥</b>
												<form:input type="text" path="money" value="" style="margin-left: -17px; padding-left: 25px; width: 250px;" />
												&nbsp;&nbsp;<em style="color: #d34842;">*</em>
											</p>
											<p>
												<span>图形验证码：</span> <input type="text" id="code1" value="" style="width: 130px;" /> &nbsp;&nbsp;<img id="verify" src="getAuthCode">
											</p>
											<p>
												<span>短信验证：</span> <input type="text" name="code" id="code" value="" placeholder="请填写短息验证码" style="width: 130px;" />&nbsp;&nbsp;
												<button type="button" class="btn btn-sm btn-danger get" style="height: 35px; padding: 3px 10px; position: relative; top: -2px;">获取</button>
											</p>
											<p style="display: none">
												<span>手机号：</span> <input type="text" style="width: 250px;" name="mobile" id="mobile" value="${userInfo.number}" readonly /> &nbsp;&nbsp;<em style="color: #d34842;">*</em>
											</p>
											<p>
												<span>银行卡号：</span>
												<form:input path="bankCardId" style="width: 250px;" value="${userInfo.bankCardId}" />
												&nbsp;&nbsp;<em style="color: #d34842;">*</em>
											</p>
											<p>
												<span>开户银行：</span>
												<form:input path="bankName" style="width: 250px;" value="${userInfo.bankName}" />
												&nbsp;&nbsp;<em style="color: #d34842;">*</em>
											</p>
											<p>
												<span>收款人：</span>
												<form:input path="payeeName" style="width: 250px;" value="${userInfo.name}" />
												&nbsp;&nbsp;<em style="color: #d34842;">*</em>
											</p>
											<p>
												<span>申请留言：</span>
												<form:input path="remark" style="width: 250px;" />
											</p>
										</div>
									</div>
									<p class="pay-txt1" style="margin-left: 140px;">
										<button type="button" onclick="formSubmit();" class="btn btn-success ajax-post" target-form='form-horizontal'>提交</button>
										<button type="button" class="btn btn-sm btn-danger" onclick="javascript:history.back();">返回</button>
									</p>
									<script>
										$('.pay_list ul li').click(function(){
											$(this).addClass('hover').siblings().removeClass('hover');
										});
									</script>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		/* 验证码切换 */
		$("#verify").click(function(){
			var verifyimg=$("#verify").attr("src");
			if(verifyimg.indexOf('?')>0){
				$("#verify").attr("src",verifyimg+'&random='+Math.random());
			}else{
				$("#verify").attr("src",verifyimg.replace(/\?.*$/,'')+'?'+Math.random());
			}
		});
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
			if(!check_empty('money','提现金额')){
				return false;
			}
			if(!check_number('money','提现金额')){
				return false;
			}
		});
		function timesecond(o,intDiff){
			var number=$("#mobile").val();
			var code=$("#code1").val();
			var type='1';
			if(number==''){
				alert('手机号不能为空!');
			}
			if(!(/^1[34578]\d{9}$/.test(number))){
				alert('手机号码有误!');
			}
			if(!code){
				layer.msg("请输入图形验证码！");
				return;
			}
			$.post("../ajaxGetSmsCode",{'number':number,'code':code,'type':LR_WEB.SMS_CODE_TYPE.USER_AUTH},function(res){
				if(res.code=='0'){
					var ss=setInterval(function(){
						var second=0; //默认值        
						if(intDiff>0){
							second=Math.floor(intDiff);
						}
						$(o).html(second+"秒后重新获取");
						if(intDiff==0){
							$(o).html("获取");
							clearInterval(ss);
							$('.get').removeClass('disabled');
						}
						intDiff--;
					},1000);
					layer.alert(res.msg,{title:'提示',icon:0});
				}else{
					layer.alert(res.msg);
				}
			});
		}
		$('body').on('click','.get',function(){
			var activeStatus='${userInfo.activeStatus}';
			if(activeStatus!=0){
				showSuccessMsg("提示","您还未激活，请先激活后再提现！","确定",function(){
					goUrl('updatemembermsg');
				});
			}
			timesecond(this,'59');
			$('.get').addClass('disabled');
		});
		function formSubmit(){
			if(!checkNull("money","提现金额不能为空！")) return false;
			if(!checkNull("bankCardId","银行卡号不能为空！")) return false;
			if(!checkNull("bankName","开户银行不能为空！")) return false;
			if(!checkNull("payeeName","收款人不能为空！")) return false;
			if(!checkNull("code","请输入验证码！")) return false;
			//$("#money").val(parseInt($("#money").val()*100)+"");
			$.ajax({url:"ajaxAddWithdrawInfo",type:"post",data:{'moneyUnit':$("#moneyUnit").val(),'money':parseInt($("#money").val()*100),'code':$("#code").val(),'remark':$("#remark").val()},
				success:function(res){
					if(res.code==0){
						showSuccessMsg("提示","申请提现成功，请等待管理员审核！","确定",function(){
							goUrl('wdrecord');
						});
					}else{
						layer.alert(res.msg,{title:'错误提示',icon:0});
					}
				}});
			/* submitForm("withdrawInfo","ajaxAddWithdrawInfo",true,"正在申请提现...",function(data){
				showSuccessMsg("提示","申请提现成功，请等待管理员审核！","确定",function(){
					goUrl('index');
				});
			},function(errmsg){
				$("#money").val(($("#money").val()/100).toFixed(2));
				showMsg(errmsg);
			}); */
			return false;
		}
		$(function(){
			$("#bankCardId").attr('readonly','readonly');
			$("#bankName").attr('readonly','readonly');
			$("#payeeName").attr('readonly','readonly');
		});
	</script>
</body>
</html>