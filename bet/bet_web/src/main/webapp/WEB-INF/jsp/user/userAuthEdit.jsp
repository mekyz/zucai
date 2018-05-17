<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>实名认证信息</title>
<meta name="parentId" content="index" />
<meta name="showMenu" content="false" />
<meta name="bodyStyle" content="margin:0 auto;" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/cropper.css" />
<link rel="stylesheet" href="<%=commonJsPath%>/cropper/initCrop.css" />
<script src="<%=commonJsPath%>/jedate/jedate.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=commonJsPath%>/mobile-select-area/dist/mobile-select-area.min.css">
<link rel="stylesheet" type="text/css" href="<%=commonJsPath%>/mobile-select-area/dist/dialog.min.css">
<script src="<%=commonJsPath%>/mobile-select-area/dist/dialog.min.js"></script>
<script src="<%=commonJsPath%>/mobile-select-area/dist/mobile-select-area.min.js"></script>
<script>
	function formSubmit(){
		if(!checkNull("name","姓名不能为空！")) return false;
		if(!checkNull("number","手机号码不能为空！")) return false;
		if(!checkNull("identifityNumber","身份证号码不能为空！")) return false;
		submitForm("userAuthInfo","ajaxUpdateUserAuthInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","提交申请成功！","确定",function(){
				goUrl("index");
			});
		},function(msg){
			showMsg(msg);
		});
		return false;
	}
	$(function(){
		$("#birthday").attr("readonly","readonly");
		$("#area").val('${userAuthInfo.province}${userAuthInfo.city}');
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
		ajaxJson("../ajaxGetSmsCode",{"number":number,'code':code,"type":LR_WEB.SMS_CODE_TYPE.USER_AUTH},function(msg){
			codeTime(60);
			showMsg(msg);
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
	<form:form modelAttribute="userAuthInfo" method="post">
		<form:input type="hidden" path="picUrl" />
		<form:input type="hidden" path="province" />
		<form:input type="hidden" path="city" />
		<div class="page">
			<div class="weui_cells weui_cells_form">
				<div class="weui_cell">
					<div class="div_photo">
						<div class="photo">
							<c:choose>
								<c:when test="${userInfo.picUrl.length() > 0}">
									<img id="img_pic" src="<%=basePath%>${userInfo.picUrl}" />
								</c:when>
								<c:otherwise>
									<img id="img_pic" src="${userInfo.headImgUrl}" />
								</c:otherwise>
							</c:choose>
						</div>
						<input type="file" id="upload" name="pic" class="upbtn" accept="image/*"> <a class="up-photo"></a>
					</div>
					<div class="tips grey" style="text-align: center;">建议使用真实头像和信息，有助于获取更多惊喜。</div>
				</div>
				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">姓名：</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<form:input path="name" class="weui_input" placeholder="请输入您的姓名" />
					</div>
				</div>
				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">手机号码：</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<form:input type="number" path="number" class="weui_input" placeholder="请输入您的手机号码" />
					</div>
				</div>
				<c:choose>
					<c:when test="${userAuthInfo.status!=applyStatusMap.get('VERIFY_SUCCESS')}">
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
						<div class="weui_cell">
							<div class="weui_cell_hd">
								<label class="weui_label">验证码：</label>
							</div>
							<div class="weui_cell_bd weui_cell_primary">
								<input id="code" name="code" class="weui_input" placeholder="请输入验证码" />
							</div>
							<div class="weui_cell_ft">
								<button id="btn_getcode" type="button" class="weui_btn weui_btn_primary" onclick="getSmsCode();">获取验证码</button>
							</div>
						</div>
					</c:when>
				</c:choose>
				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">身份证号码：</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<form:input path="identifityNumber" class="weui_input" placeholder="请输入身份证号码" />
					</div>
				</div>
				<div class="weui_cell weui_cell_select weui_select_after">
					<div class="weui_cell_hd">
						<label class="weui_label">性别：</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<form:select path="sex" theme="simple" class="weui_select">
							<c:forEach items="${sexTypeList}" var="sexType" varStatus="status">
								<c:choose>
									<c:when test="${sexType.sex != 3}">
										<option value="${sexType.sex}">${sexType.desc}</option>
									</c:when>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">生日：</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<form:input path="birthday" class="weui_input" placeholder="" />
					</div>
					<div class="weui_cell_ft">
						<img src="<%=commonImgPath%>/ic_choose_date.png" onClick="jeDate({dateCell:'#birthday',isTime:false,format:'YYYY-MM-DD'});adjustCalendar();" style="width: 30px;" />
					</div>
				</div>
				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">所在地区：</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<input id="area" class="weui_input" placeholder="请选择所在地区" />
					</div>
				</div>
				<c:choose>
					<c:when test="${userAuthInfo.status==applyStatusMap.get('VERIFY_FAIL')}">
						<div class="weui_cells_tips" style="color: red; font-size: 1.2em;">审核失败：${userAuthInfo.remark}</div>
					</c:when>
					<c:when test="${userAuthInfo.status==applyStatusMap.get('VERIFY_SUCCESS')}">
						<div class="weui_cells_tips" style="color: red; font-size: 1.2em;">您已通过实名认证</div>
					</c:when>
					<c:otherwise>
						<div class="weui_btn_area">
							<a class="weui_btn weui_btn_primary" onclick="formSubmit();">立即提交</a>
						</div>
					</c:otherwise>
				</c:choose>
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
	</form:form>
	<script>
		new MobileSelectArea().init({trigger:"#area",tit:"选择地区",level:2,position:"bottom",data:"<%=commonJsPath%>/json-data/area.json",callback:function(){
			var areas=$("#area").val();
			var sp=areas.split(' ');
			var province=sp[0];
			var city=sp[1];
			$("#province").val(province);
			$("#city").val(city);
		}});
	</script>
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
					//showLog("picUrl:" + picInfo.picUrl + ".");
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