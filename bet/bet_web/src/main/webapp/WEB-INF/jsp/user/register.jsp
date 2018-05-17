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
<meta name="showHead" content="false" />
<meta name="showMenu" content="false" />
<meta name="bodyStyle" content="background: url(<%=betDir%>/Uploads/Picture/2018-03-01/5a976824df63d.jpg) no-repeat fixed top; background-size: 100% 100%;" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/cropper.css" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/initCrop.css" />
<script src="<%=commonJsPath%>/cookie.js"></script>
<script src="<%=commonJsPath%>/alerts.js"></script>
<script>
	function formSubmit(){
		if(!checkNull("userId","账号不能为空！")) return false;
		if(!checkNull("number","手机号码不能为空！")) return false;
		if(!checkNull("password","登录密码不能为空！")) return false;
		//if(!checkNull("rePassword","确认登录密码不能为空！")) return false;
		if(!checkNull("password2","二级密码不能为空！")) return false;
		//if(!checkNull("rePassword2","确认二级密码不能为空！")) return false;
		if(!checkNull("code","请输入验证码！")) return false;
		/* if($("#password").val()!==$("#rePassword").val()){
			showMsg('两次输入的登录密码不一致！');
			return false;
		}
		if($("#password2").val()!==$("#rePassword2").val()){
			showMsg('两次输入的二级密码不一致！');
			return false;
		} */
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
<style>
.demo{
  background-color: rgba(255,255,255,0.9);/* IE9、标准浏览器、IE6和部分IE7内核的浏览器(如QQ浏览器)会读懂 */
}
.demo p{
  color: #FFFFFF;
}
@media \0screen\,screen\9 {/* 只支持IE6、7、8 */
  .demo{
    background-color:#ffffff;
    filter:Alpha(opacity=90);
    position:static; /* IE6、7、8只能设置position:static(默认属性) ，否则会导致子元素继承Alpha值 */
    *zoom:1; /* 激活IE6、7的haslayout属性，让它读懂Alpha */
  }
  .demo p{
    position: relative;/* 设置子元素为相对定位，可让子元素不继承Alpha值 */
  }  
}
</style>
</head>
<body>
	<form:form modelAttribute="userInfo" method="post">
		<input type="hidden" name="v" value="${apiVersion}" />
		<form:hidden path="picUrl" />
        <div style="font-size:26px; color:#ffffff; text-align:center; padding:25px 0;">欢迎注册DM</div>
		<div class="page" >
			<div class="page__bd">
				<div class="weui-cells_form demo" style="border:1px solid; border-radius:5px;">
					<div class="weui-cell" style="display:none;">
						<div class="div_photo">
							<div class="photo">
								<img id="img_pic" src="<%=mobileImgPath%>/ic_add_pressed.png" />
							</div>
							<input type="file" id="upload" name="pic" class="upbtn" accept="image/*"> <a class="up-photo"></a>
						</div>
						<div class="tips grey" style="text-align: center;">建议使用真实头像和信息，有助于获取更多惊喜。</div>
					</div>
					<div class="weui-cell" style="width:90%;">
						<div class="weui-cell__hd">
							<label class="weui-label">登录账号：</label>
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
					<!--<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">确认密码：</label>
						</div>
						<div class="weui-cell__hd">
							<input type="password" id="rePassword" class="weui-input" placeholder="请再次输入登录密码" />
						</div>
					</div>-->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">二级密码：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input type="password" path="password2" class="weui-input" placeholder="请输入您的二级密码" />
						</div>
					</div>
					<!--<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">确认二级密码：</label>
						</div>
						<div class="weui-cell__hd">
							<input type="password" id="rePassword2" class="weui-input" placeholder="请再次输入二级密码" />
						</div>
					</div>-->
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
							<label class="weui-label">手机验证码：</label>
						</div>
						<div class="weui-cell__hd" style="width:30%;">
							<input id="code" name="code" class="weui-input" placeholder="请输入验证码" />
						</div>
						<div class="weui_cell_ft">
							<button id="btn_getcode" style="font-size:16px; float:right; color:#FFF;border:1px solid; border-radius:3px; padding:5px;" type="button" class="weui-btn_primary" onclick="getSmsCode();">获取验证码</button>
						</div>
					</div>
					<div class="weui-cell" style="display:none;">
						<div class="weui-cell__hd">
							<label class="weui-label">姓名：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input path="name" class="weui-input" placeholder="请输入姓名" />
						</div>
					</div>
					<div class="weui-cell" style="display:none;">
						<div class="weui-cell__hd">
							<label class="weui-label">昵称：</label>
						</div>
						<div class="weui-cell__hd">
							<form:input path="nickname" class="weui-input" placeholder="请输入昵称" />
						</div>
					</div>
					<div class="weui-cell weui-cell_select weui-cell_select-after" style="display:none;">
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