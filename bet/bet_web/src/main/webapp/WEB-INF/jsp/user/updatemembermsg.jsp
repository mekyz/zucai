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
		window.location.href="updatemembermsg_m";
	}
</script>
</head>
<body>
	<link rel="stylesheet" type="text/css" href="<%=betDir%>/Public/Home/h+/css/pay.css" />
	<div class="row">
		<style>
html {
	height: 100%;
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
}

.withdrawals-from input {
	width: 250px;
}
</style>
		<div class="container-fluid content">
			<div class="row">
				<div id="content" class="col-sm-12 full">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="panel panel-default bk-bg-white">
								<div class="panel-heading bk-bg-white">
									<h6>
										<i class="fa fa-indent red"></i>资料完善
									</h6>
									<div class="panel-actions"></div>
								</div>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="background: #FFFFFF;">
									<form:form modelAttribute="userInfo" method="post" class="form-horizontal">
										<div class="pay_list">
											<div class="prompt">
												<h2>温馨提示：</h2>
												<p>请如实填写您的收款人信息、手机号、当您发起提现时收款账号必须与您所登记的收款人一致，否则无法提现。手机号码用于提现等重要操作时候的验证码，如果您填写的手机号码不正确，可能会影响到您的使用！</p>
												<p>本平台仅用于娱乐休闲，严禁赌博、洗钱等非法行为，否则您的账号将有可能被冻结。</p>
												<p>如利用平台进行任何洗钱诈骗行为，本平台将保留权利终止代理服务及冻结其账户。</p>
											</div>
											<div class="withdrawals">
												<img src="<%=betDir%>/Public/Home/h+/images/zil.png" />
											</div>
											<div class="withdrawals-from">
												<p>
													<span>会员编号：</span>
													<form:input path="userId" disabled="disabled" />
													&nbsp;&nbsp;<em style="color: #d34842;">*</em>
												</p>
												<p>
													<span>会员姓名：</span>
													<form:input path="name" />
													&nbsp;&nbsp;<em style="color: #d34842;">*</em>
												</p>
												<p>
													<span>会员昵称：</span>
													<form:input path="nickname" />
													&nbsp;&nbsp;<em style="color: #d34842;">*</em>
												</p>
												<p>
													<span>手机号：</span>
													<form:input path="number" disabled="disabled" />
													&nbsp;&nbsp;<em style="color: #d34842;">*</em>
												</p>
												<p>
													<span>收款人：</span>
													<form:input path="payeeName" />
												</p>
												<p>
													<span>开户银行：</span>
													<form:select path="bankName">
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
													<form:input path="bankCardId" maxlength="19" />
													&nbsp;&nbsp;<em style="color: #d34842;">*</em>
												</p>
												<p>
													<span>图形验证码：</span> <input type="text" id="code1" name="code1" placeholder="输入图形验证码" /> <img id="verify" src="getAuthCode"> &nbsp;&nbsp;<em style="color: #d34842;">*</em>
												</p>
												<p>
													<span>短信验证：</span> <input type="text" name="code" id="code" value="" placeholder="请填写短信验证码" style="width: 130px;" />
													<button type="button" class="btn btn-sm btn-danger get" style="height: 35px; padding: 3px 10px; position: relative; top: -2px;">获取</button>
												</p>
											</div>
										</div>
										<p class="pay-txt1" style="margin-left: 167px;">
											<button type="button" id="btnSubmit" onclick="formSubmit()" class="btn btn-success">提交</button>
											<button type="button" class="btn btn-danger" onclick="javascript:history.back();">返回</button>
										</p>
									</form:form>
									<script>
										$('.pay_list ul li').click(function(){
											$(this).addClass('hover').siblings().removeClass('hover');
										});
									</script>
								</div>
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
		/*银行卡号*/
		$("#bankCardId").blur(function(){
			if(!check_empty('bankCardId','银行卡号')){
				return false;
			}
			if(!check_number('bankCardId','银行卡号')){
				return false;
			}
			if(!check_Size('bankCardId','银行卡号','16','19')){
				return false;
			}
		});
		function formSubmit(){
			if(!checkNull("name","姓名不能为空！")) return false;
			if(!checkNull("nickname","昵称不能为空！")) return false;
			if(!checkNull("number","手机号码不能为空！")) return false;
			if(!checkNull("payeeName","收款人不能为空！")) return false;
			if(!checkNull("bankCardId","银行卡号不能为空！")) return false;
			if(!checkNull("code","请输入验证码！")) return false;
			if(!$("#number").val().match(/^1\d{10}$/)){
				showMsg('请输入有效的手机号码！');
				return false;
			}
			$("#btnSubmit").attr('disabled','disabled');
			$.ajax({
				url:"ajaxUpdateUserInfo",
				type:"post",
				data:{'name':$("#name").val(),'nickname':$("#nickname").val(),'payeeName':$("#payeeName").val(),'bankName':$("#bankName").val(),'bankCardId':$("#bankCardId").val(),
					'code':$("#code").val()},success:function(res){
					if(res.code==0){
						showSuccessMsg("提示","修改资料成功！","确定",function(){
							goUrl('index');
						});
					}else{
						layer.alert(res.msg,{title:'错误提示',icon:0});
					}
					$("#btnSubmit").removeAttr('disabled');
				}});
			/* submitForm("userInfo","ajaxUpdateUserInfo",true,"正在更新资料...",function(data){
				showSuccessMsg("提示","修改资料成功！","确定",function(){
					goUrl('index');
				});
			},null); */
			return false;
		}
		function timesecond(o,intDiff){
			var number=$("#number").val();
			var code=$("#code1").val();
			var type='1';
			if(number==''){
				alert('手机号不能为空!1');
			}
			/* if(!(/^1[34578]\d{9}$/.test(mobile))){
				alert('手机号码有误!');
			} */
			if(!code){
				layer.msg("请输入图形验证码！");
				return;
			}
			$.post("../ajaxGetSmsCode",{'number':number,'code':code,'type':LR_WEB.SMS_CODE_TYPE.USER_AUTH},function(data){
				if(data.code==0){
					layer.alert(data.msg,{title:'提示',icon:0});
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
				}else{
					layer.msg(data.msg);
				}
			});
		}
		$('body').on('click','.get',function(){
			timesecond(this,'59');
			$('.get').addClass('disabled');
		});
		$(function(){
			$("#number").attr('readonly','readonly');
		});
	</script>
</body>
</html>