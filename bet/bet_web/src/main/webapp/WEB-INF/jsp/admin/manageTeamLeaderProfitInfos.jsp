<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>团队领导奖管理</title>
<meta name="pageId" content="manageTeamLeaderProfitInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetTeamLeaderProfitInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"profitId"},{data:"phaseId"},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"profitType"},{data:"userBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"teamBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"profitMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"profitUserMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"profitStatus",render:function(data,type,row,meta){
				if(data==LR_WEB.STATUS.ENABLED){
					return '已发放';
				}else{
					return '未发放';
				}
			}},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="teamLeaderProfitInfoEdit?profitId='+row.profitId+'">查看</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[10,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			if(!isNull(data.phaseId)){
				ajaxTipJson("ajaxGetMatchInfo","phaseId="+data.phaseId,false,null,function(data){
					var matchInfo=toJson(data);
					$('td:eq(2)',row).html(matchInfo.matchName+'<br>('+matchInfo.homeTeam+'-'+matchInfo.awayTeam+')');
				},function(msg){});
			}
			if(!isNull(data.profitType)){
				ajaxTipJson("ajaxGetTeamLeaderProfitConfigInfo","configId="+data.profitType,false,null,function(data){
					var configInfo=toJson(data);
					$('td:eq(4)',row).html(configInfo.name);
				},function(msg){});
			}
			return row;
		};
		var _table=$table.DataTable(d);
		initTableBtns(_table);
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>团队领导奖列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_without_add.jsp"%>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sUserId">用户ID:</label> <input type="text" class="form-control" id="sUserId" />
							</div>
							<div class="form-group">
								<button type="button" class="btn" style="margin-bottom: 0px !important;" id="btn-advanced-search">
									<i class="fa fa-search"></i> 查询
								</button>
							</div>
						</form>
					</div>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1">序号</th>
								<th class="column_len2">ID</th>
								<th class="column_len4">赛事</th>
								<th class="column_len2">用户</th>
								<th class="column_len2">奖金类型</th>
								<th class="column_len2">用户下注</th>
								<th class="column_len2">团队下注</th>
								<th class="column_len2">返利金额</th>
								<th class="column_len2">实得金额</th>
								<th class="column_len2">发放状态</th>
								<th class="column_len3">添加时间</th>
								<th class="column_len2">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="table_footer.jsp"%>
</body>
</html>