<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>意见反馈详情</title>
<meta name="pageId" content="advice" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<script>
	function reply(){
		var content='<div class="container-fluid"><form id="adviceReplyInfo" method="post"><input type="hidden" name="adviceId" value="${adviceInfo.adviceId}" />'
		content+='<div class="form-group"><label for="content">回复内容：</label>';
		content+='<textarea name="content" rows="10" cols="10" style="width:100%;height: 100px;" placeholder="请输入回复内容"></textarea></div></form></div>';
		showChooseMsg("请输入回复内容",content,'确定','取消',function(){
			submitForm("adviceReplyInfo","ajaxAddReplyAdviceInfo",true,"请稍后...",function(data){
				showSuccessMsg("提示","回复成功！",function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
		},null);
	}
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>意见反馈详情</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">反馈平台：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">${adviceInfo.platform}</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">会员ID：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">${adviceInfo.userId}</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">手机号码：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">${adviceInfo.number}</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">内容：</label>
							<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">${adviceInfo.content}</div>
						</div>
						<c:forEach var="adviceReplyInfo" items="${adviceReplyInfoList}">
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">回复内容：</label>
								<div class="col-md-6 col-sm-6 col-xs-12 form-control-static">${adviceReplyInfo.content}</div>
							</div>
						</c:forEach>
						<c:choose>
							<c:when test="${adviceReplyInfoList.size() < 1}">
								<div class="form-group">
									<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
										<button type="button" class="btn btn-success" onclick="reply();">回复</button>
									</div>
								</div>
							</c:when>
						</c:choose>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>