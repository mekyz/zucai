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
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>二级密码
					</h6>
					<div class="panel-actions">
						<a href="#" class="btn-minimize"><i class="fa fa-caret-up"></i></a> <a href="#" class="btn-close"><i class="fa fa-times"></i></a>
					</div>
				</div>
				<div class="panel-body">
					<form method="post" class="form-horizontal ">
						<div class="form-group">
							<label class="col-lg-2 col-md-2 col-sm-12 control-label">二级密码<span class="required">*</span></label>
							<div class="col-md-10">
								<div class="input-group col-lg-6 col-md-6">
									<input type="password" id="password2" name="password2" value="" required class="form-control" placeholder="输入二级密码..." />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-9 col-sm-offset-4">
								<button class="bk-margin-5 btn btn-success" type="button" onclick="formSubmit();" target-form="commentForm">验证</button>
								<button type="reset" class="bk-margin-5 btn btn-danger">返回</button>
							</div>
						</div>
						<hr />
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		function formSubmit(){
			var password2=$("#password2").val();
			$.ajax({url:"ajaxAuthPwd2",type:"post",data:{'password':password2},success:function(res){
				if(res.code==0){
					layer.msg('验证成功！');
					var prePage='${sessionScope.get('session_pre_user_url')}';
					if(isNull(prePage)){
						prePage="index";
					}
					window.location.href=prePage;
				}else{
					layer.msg(res.msg);
				}
			}});
		}
	</script>
</body>
</html>