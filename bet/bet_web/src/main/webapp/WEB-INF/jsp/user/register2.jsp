<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户注册</title>
<meta name="parentId" content="login" />
<meta name="showHead" content="true" />
<meta name="showMenu" content="false" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/cropper.css" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/initCrop.css" />
<script src="<%=commonJsPath%>/cookie.js"></script>
<script src="<%=commonJsPath%>/alerts.js"></script>
<script>
	function formSubmit(){
		if(!checkNull("userId","账号不能为空！")) return false;
		if(!checkNull("number","手机号码不能为空！")) return false;
		if(!checkNull("password","登录密码不能为空！")) return false;
		if(!checkNull("rePassword","确认登录密码不能为空！")) return false;
		if(!checkNull("password2","二级密码不能为空！")) return false;
		if(!checkNull("rePassword2","确认二级密码不能为空！")) return false;
		if(!checkNull("code","请输入验证码！")) return false;
		if($("#password").val()!==$("#rePassword").val()){
			showMsg('两次输入的登录密码不一致！');
			return false;
		}
		if($("#password2").val()!==$("#rePassword2").val()){
			showMsg('两次输入的二级密码不一致！');
			return false;
		}
		if(!$("#number").val().match(/^1\d{10}$/)){
			showMsg('请输入有效的手机号码！');
			return false;
		}
		submitForm("userInfo","ajaxRegister",true,"正在注册...",function(data){
			setNumberCookie($("#userId").val());
			showSuccessMsg("提示","恭喜，注册成功！","确定",function(){
				var prePage=getUserPreUrl();
				if(isNull(prePage)){
					prePage="index";
				}
				goUrl(prePage);
			});
		},null);
		return false;
	}
	/* 验证码切换 */
	$(function(){
		$("#verify").click(function(){
			var verifyimg=$("#verify").attr("src");
			if(verifyimg.indexOf('?')>0){
				$("#verify").attr("src",verifyimg+'&random='+Math.random());
			}else{
				$("#verify").attr("src",verifyimg.replace(/\?.*$/,'')+'?'+Math.random());
			}
		})
	});
	function getSmsCode(){
		if(!checkNull("number","手机号码不能为空！")) return false;
		var number=$("#number").val();
		var code=$("#code1").val();
		if(!number.match(/^1\d{10}$/)){
			showMsg('请输入有效的手机号码！');
			return false;
		}
		if(!code){
			showMsg("请输入图形验证码！");
			return;
		}
		ajaxJson("../ajaxGetSmsCode",{"number":number,'code':code,"type":LR_WEB.SMS_CODE_TYPE.REGISTER},function(msg){
			codeTime(60);
			Show(msg,2000);
		},null);
	}
	function codeTime(tm){
		var timer=setInterval(function(){
			tm--;
			if(tm>0){
				$("#btn_getcode").html(tm+'秒内有效');
				$('#btn_getcode').attr('onclick','');
			}else{
				$("#btn_getcode").html('获取验证码');
				$('#btn_getcode').attr('onclick','getSmsCode();');
				clearInterval(timer);
				timer=null;
			}
		},1000);
	}
</script>
</head>
<body>
	<form:form modelAttribute="userInfo" method="post">
		<input type="hidden" name="v" value="${apiVersion}" />
		<form:hidden path="picUrl" />
		<div class="page">
			<div class="page__bd">
				<div class="weui-cells weui-cells_form">
					<div class="weui-cell">
						<div class="div_photo">
							<div class="photo">
								<img id="img_pic" src="<%=mobileImgPath%>/ic_add_pressed.png" />
							</div>
							<input type="file" id="upload" name="pic" class="upbtn" accept="image/*"> <a class="up-photo"></a>
						</div>
						<div class="tips grey" style="text-align: center;">建议使用真实头像和信息，有助于获取更多惊喜。</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">账号：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input path="userId" class="weui-input" placeholder="请输入账号" />
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">登录密码：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input type="password" path="password" class="weui-input" placeholder="请输入您的登录密码" />
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">确认登录密码：</label>
						</div>
						<div class="weui-cell__hd">
							<input type="password" id="rePassword" class="weui-input" placeholder="请再次输入登录密码" />
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">二级密码：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input type="password" path="password2" class="weui-input" placeholder="请输入您的二级密码" />
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">确认二级密码：</label>
						</div>
						<div class="weui-cell__hd">
							<input type="password" id="rePassword2" class="weui-input" placeholder="请再次输入二级密码" />
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">手机号码：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input type="number" path="number" class="weui-input" placeholder="请输入手机号码" />
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">图形验证码：</label>
						</div>
						<div class="weui-cell__hd">
							<input id="code1" name="code1" class="weui-input" placeholder="请输入图形验证码" />
						</div>
						<div class="weui_cell_ft">
							<img id="verify" src="getAuthCode">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">验证码：</label>
						</div>
						<div class="weui-cell__hd">
							<input id="code" name="code" class="weui-input" placeholder="请输入验证码" />
						</div>
						<div class="weui_cell_ft">
							<button id="btn_getcode" type="button" class="weui-btn weui-btn_primary" onclick="getSmsCode();">获取验证码</button>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">姓名：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input path="name" class="weui-input" placeholder="请输入姓名" />
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">昵称：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input path="nickname" class="weui-input" placeholder="请输入昵称" />
						</div>
					</div>
					<div class="weui-cell weui-cell_select weui-cell_select-after">
						<div class="weui-cell__hd">
							<label class="weui-label">性别：</label>
						</div>
						<div class="weui-cell__hd">
							<form:select path="sex" items="${sexTypeMap}" theme="simple" class="weui-select"></form:select>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">推荐人：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input path="referrerId" value="${referrerId}" class="weui-input" placeholder="请输入推荐人账号" />
						</div>
					</div>
					<div class="weui-btn-area">
						<a class="weui-btn weui-btn_primary" onclick="formSubmit();">注册</a>
					</div>
					<div id="containerDiv" class="div_container">
						<img id="img-container" src="">
						<div id="actions" class="div_btn_row">
							<button type="button" class="btn" data-method="destroy">
								<span class="docs-tooltip"> 取消</span>
							</button>
							<button type="button" id="imgCutConfirm" class="btn btn-right" data-method="getCroppedCanvas">
								<span class="docs-tooltip">确认</span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
	<script src="<%=commonJsPath%>/exif.js"></script>
	<script src="<%=commonJsPath%>/cropper/cropper.js"></script>
	<script src="<%=commonJsPath%>/cropper/initCrop.js"></script>
	<script>
		var initCropParams={imgContainerId:'img-container',divContainerId:'containerDiv',actionsId:'actions',showImgId:'img_pic',inputImageId:'upload',aspectRatio:1/1,imgSize:{width:320,height:320}};
		var fileImg="";
		$(function(){
			$("#imgCutConfirm").bind("click",function(){
				$("#"+initCropParams.divContainerId).hide();
			});
		});
		//提交表达
		function uploadPicForm(){
			$("#registerForm").attr("enctype","multipart/form-data");
			var formData=new FormData($("#registerForm")[0]);
			formData.append("upload",fileImg);
			var loadi=loading('正在上传图片...');
			$.ajax({url:"ajaxUploadBase64Pic?sortId=head",type:'POST',data:formData,dataType:'json',async:true,cache:false,contentType:false,processData:false,success:function(result){
				layer.close(loadi);
				if(result.code==LR_WEB.RETURN_CODE.SUCCESS){
					var picInfo=toJson(result.msg);
					$("#picUrl").val(picInfo.picUrl);
				}else{
					$("#"+initCropParams.showImgId).attr("src","");
					showMsg(result.msg);
				}
			},error:function(returndata){
				showLog(JSON.stringify(returndata));
				showMsg("上传图片错误，请重新上传！");
				$("#"+initCropParams.showImgId).attr("src","");
			}});
		}
	</script>
</body>
</html>