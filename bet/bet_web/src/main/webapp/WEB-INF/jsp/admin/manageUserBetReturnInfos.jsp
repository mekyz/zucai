<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户下注撤回管理</title>
<meta name="pageId" content="manageUserBetReturnInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sPhaseId=$("#sPhaseId").val();
		param.sProfitId=$("#sProfitId").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetUserBetReturnInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"betId"},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"profitId"},{data:"money",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"moneyUnit",render:LR_WEB.DATA_TABLES.RENDER.MONEY_UNIT},{data:"remark"},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				//data+='<li><a href="userBetReturnInfoEdit?logId='+row.logId+'">查看</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[7,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			ajaxJson("ajaxGetMatchProfitInfo","profitId="+data.profitId,function(msg){
				var matchProfitInfo=toJson(msg);
				ajaxJson("ajaxGetMatchInfo","phaseId="+matchProfitInfo.phaseId,function(msg){
					var matchInfo=toJson(msg);
					$('td:eq(3)',row).html(matchInfo.matchName+'<br>('+matchInfo.homeTeam+'-'+matchInfo.awayTeam+' '+matchProfitInfo.score1+':'+matchProfitInfo.score2+')');
				},function(errmsg){});
				$('td:eq(3)',row).html(matchProfitInfo.score1+':'+matchProfitInfo.score2);
			},function(errmsg){});
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
							<h2>用户下注撤回列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_without_add.jsp"%>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sUserId">用户账号:</label> <input type="text" class="form-control" id="sUserId" />
							</div>
							<div class="form-group">
								<label for="sPhaseId">赛事ID:</label> <input type="text" class="form-control" id="sPhaseId" />
							</div>
							<div class="form-group">
								<label for="sProfitId">波胆ID:</label> <input type="text" class="form-control" id="sProfitId" />
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
								<th class="column_len2">下注ID</th>
								<th class="column_len2">账号</th>
								<th class="column_len6">赛事</th>
								<th class="column_len2">金额</th>
								<th class="column_len2">金额类型</th>
								<th class="column_len3">备注</th>
								<th class="column_len3">撤回时间</th>
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