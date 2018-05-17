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
		window.location.href="tfreply_m";
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
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>会员转币
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<form:form modelAttribute="userExchangeInfo" method="post" class="form-horizontal ">
						<div class="form-group">
							<label class="col-sm-3 control-label">会员编号:</label>
							<div class="col-md-4">
								<label class="control-label text-info">${userInfo.userId}[${userInfo.name}]</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">可用彩金:</label>
							<div class="col-md-4">
								<label class="control-label text-success">${userInfo.point/100}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">转币规则:</label>
							<div class="col-md-4">
								<p class="form-control-static inline">
									<span class="label label-danger">最低转币金额为50并且必须是10.00的整数倍</span>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">转入编号:</label>
							<div class="col-md-4">
								<form:input class="form-control" path="receiveUserId" value="" />
							</div>
							<div class="col-lg-2 col-md-3 col-sm-3">
								<p id='backinfo' class="form-control-static inline"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">转币数量:</label>
							<div class="col-md-4">
								<form:input class="form-control" path="money" value="" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onpaste="this.value=this.value.replace(/[^\d.]/g,'')" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">转币类型:</label>
							<div class="col-sm-8">
								<div class="radio-custom">
									<input type="radio" id="moneytype1" checked name="tftype" value="1" /> <label for="tftype1">彩金钱包互转</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">输入二级密码:</label>
							<div class="col-md-4">
								<input class="form-control" type="password" id="password2" name="password2" required value="" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">图形验证码:</label>
							<div class="col-md-4">
								<input class="form-control" type="text" id="code1" name="code1" required value="" /><img id="verify" src="getAuthCode">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">短信验证:</label>
							<div class="col-md-4">
								<input class="form-control" type="text" id="code" name="code" required value="" />
								<button type="button" class="btn btn-sm btn-danger get" style="height: 35px; padding: 3px 10px; position: relative; top: -2px;">获取</button>
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-9 col-xs-9 col-lg-offset-3 col-md-offset-3 col-sm-offset-3 col-xs-offset-3">
								<button type="button" onclick="formSubmit();" class="btn btn-sm btn-info ajax-post" target-form='form-horizontal'>提交</button>
								<button type="button" class="btn btn-sm btn-success" onclick="javascript:history.back();">返回</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		/*转币数量*/
		$("#money").blur(function(){
			if(!check_empty('money','金额')){
				return false;
			}
			if(!check_number('money','金额')){
				return false;
			}
		});
		function formSubmit(){
			if(!checkNull("receiveUserId","接收账号不能为空！")) return false;
			if(!checkNull("money","金额不能为空！")) return false;
			$("#money").val($("#money").val()*100+"");
			submitForm("userExchangeInfo","ajaxAddUserExchangeInfo",true,"正在申请转账...",function(data){
				showSuccessMsg("提示","申请转账成功！","确定",function(){
					goUrl('index');
				});
			},function(errmsg){
				$("#money").val(($("#money").val()/100).toFixed(2));
				showMsg(errmsg);
			});
			return false;
		}
		function timesecond(o,intDiff){
			var number='${userInfo.number}';
			var code=$("#code1").val();
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
			timesecond(this,'59');
			$('.get').addClass('disabled');
		});
	</script>
</body>
</html>