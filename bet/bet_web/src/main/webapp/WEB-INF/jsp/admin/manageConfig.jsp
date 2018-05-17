<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>参数设置</title>
<%-- 配置信息开始 --%>
<meta name="pageId" content="manageConfig" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<%-- 配置信息结束 --%>
<script>
	var update=function(configId){
		var value=$("#"+configId).val();
		ajaxTipJson("ajaxUpdateConfig","configId="+configId+"&content="+value,true,"正在更新，请稍后...",function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},null);
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12" style="padding: 0 0 0 0;">
				<div class="panel panel-default">
					<div class="panel-heading">配置管理</div>
					<div class="panel-body">
						<c:forEach items="${configInfoList}" var="configInfo">
							<c:choose>
								<c:when test="${pageScope.configInfo.configId == 'register_need_referrer'||pageScope.configInfo.configId == 'sms_need_code'||pageScope.configInfo.configId == 'admin_maintain'||pageScope.configInfo.configId == 'close_exchange'}">
									<form:form modelAttribute="configInfo" class="form-horizontal" method="post" target="_self">
										<div class="form-group" style="margin-top: 5px;">
											<label for="content" class="col-sm-2 control-label text-right">${pageScope.configInfo.configName}：</label>
											<div class="col-sm-8">
												<form:select path="content" id="${pageScope.configInfo.configId}" class="form-control">
													<c:choose>
														<c:when test="${pageScope.configInfo.content == 'true'}">
															<form:option value="true" selected="selected">是</form:option>
															<form:option value="false">否</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="true">是</form:option>
															<form:option value="false" selected="selected">否</form:option>
														</c:otherwise>
													</c:choose>
												</form:select>
											</div>
											<div class="col-sm-2">
												<a onclick="update('${pageScope.configInfo.configId}');" class="btn btn-warning btn-warng1"><span class="tag_02"></span>提交</a>
											</div>
										</div>
									</form:form>
								</c:when>
								<c:otherwise>
									<div class="form-group" style="margin-top: 5px;">
										<label for="${pageScope.configInfo.configId}" class="col-sm-2 control-label text-right">${pageScope.configInfo.configName}：</label>
										<div class="col-sm-8">
											<input type="text" id="${pageScope.configInfo.configId}" value="<c:out value='${pageScope.configInfo.content}'/>" class="form-control" />
										</div>
										<div class="col-sm-2">
											<a onclick="update('${pageScope.configInfo.configId}');" class="btn btn-warning btn-warng1"><span class="tag_02"></span>提交</a>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							<label class="col-sm-12 text-center" style="margin-top: 5px; margin-bottom: 30px;">${pageScope.configInfo.remark}</label>
							<br />
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>