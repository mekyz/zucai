<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>添加赛事信息</title>
<meta name="pageId" content="matchInfoAdd" />
<meta name="groupId" content="news" />
<meta name="bodyStyle" content="" />
<script src="<%=commonJsPath%>/jedate/jedate.min.js"></script>
<script>
	function formSubmit(){
		var m1=getDateTimeLong($("#matchDate").val());
		var m2=getDateTimeLong($("#timeEndsale").val());
		$("#matchDate").val(m1);
		$("#timeEndsale").val(m2);
		if(!checkNull("matchName","赛事名称不能为空！")) return false;
		if(!checkNull("matchDate","比赛时间不能为空！")) return false;
		if(!checkNull("timeEndsale","停止售票时间不能为空！")) return false;
		submitForm("matchInfo","ajaxAddMatchInfo",true,"请稍后...",function(data){
			showSuccessMsg("提示","添加成功！",function(){
				goUrl("manageMatchInfos");
			});
		},function(msg){
			$("#matchDate").val(getFormatDateTime($("#matchDate").val()));
			$("#timeEndsale").val(getFormatDateTime($("#timeEndsale").val()));
			showMsg(msg);
		});
		return false;
	}
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>添加赛事信息</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form modelAttribute="matchInfo" method="post" class="form-horizontal form-label-left input_mask">
						<div class="form-group">
							<label for="matchName" class="control-label col-md-3 col-sm-3 col-xs-12">赛事名称*：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="matchName" class="form-control" placeholder="请输入赛事名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="matchNum" class="control-label col-md-3 col-sm-3 col-xs-12">轮次：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="matchNum" class="form-control" placeholder="请输入轮次" />
							</div>
						</div>
						<div class="form-group">
							<label for="matchDate" class="control-label col-md-3 col-sm-3 col-xs-12">比赛时间*：</label>
							<div class="col-md-4 col-sm-4 col-xs-10">
								<form:input path="matchDate" class="form-control" placeholder="请输入比赛时间" />
							</div>
							<div class="col-xs-2">
								<img src="<%=commonImgPath%>/ic_choose_date.png" onClick="jeDate({dateCell:'#matchDate',isTime:true,format:'YYYY-MM-DD hh:mm:ss'});adjustCalendar();" style="width: 30px;" />
							</div>
						</div>
						<div class="form-group">
							<label for="timeEndsale" class="control-label col-md-3 col-sm-3 col-xs-12">停止售票时间*：</label>
							<div class="col-md-4 col-sm-4 col-xs-10">
								<form:input path="timeEndsale" class="form-control" placeholder="请输入停止售票时间" />
							</div>
							<div class="col-xs-2">
								<img src="<%=commonImgPath%>/ic_choose_date.png" onClick="jeDate({dateCell:'#timeEndsale',isTime:true,format:'YYYY-MM-DD hh:mm:ss'});adjustCalendar();" style="width: 30px;" />
							</div>
						</div>
						<div class="form-group">
							<label for="homeTeamId" class="control-label col-md-3 col-sm-3 col-xs-12">主队*：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="homeTeamId" items="${teamInfoList}" itemLabel="name" itemValue="teamId" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="awayTeamId" class="control-label col-md-3 col-sm-3 col-xs-12">客队*：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="awayTeamId" items="${teamInfoList}" itemLabel="name" itemValue="teamId" theme="simple" class="form-control"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="sortIndex" class="control-label col-md-3 col-sm-3 col-xs-12">优先级*：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input type="number" path="sortIndex" class="form-control" placeholder="请输入优先级,越大优先级越高" />
							</div>
						</div>
						<div class="form-group">
							<label for="videoUrl" class="control-label col-md-3 col-sm-3 col-xs-12">直播地址：</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:input path="videoUrl" class="form-control" placeholder="请输入直播地址" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 text-center">
								<form:button type="button" class="btn btn-success" onclick="formSubmit();">添加</form:button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>