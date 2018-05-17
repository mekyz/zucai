<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户奖金管理</title>
<meta name="pageId" content="manageUserBonusInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sPhaseId=$("#sPhaseId").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetUserBonusInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"bonusId"},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"phaseId"},{data:"shareBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"agentBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"sameLevel1BonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"benefitBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"sameLevel2BonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"fee",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"userMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"status",render:function(data,type,row,meta){
				return data===LR_WEB.STATUS.ENABLED?"已发放":"未发放";
			}},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="userBonusInfoEdit?bonusId='+row.bonusId+'">查看</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[12,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			if(!isNull(data.phaseId)){
				ajaxTipJson("ajaxGetMatchInfo","phaseId="+data.phaseId,false,null,function(data){
					var matchInfo=toJson(data);
					$('td:eq(3)',row).html(matchInfo.matchName+'<br>('+matchInfo.homeTeam+'-'+matchInfo.awayTeam+')');
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
							<h2>用户奖金列表</h2>
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
								<th class="column_len2">账号</th>
								<th class="column_len4">赛事</th>
								<th class="column_len2">分享佣金</th>
								<th class="column_len2">代理佣金</th>
								<th class="column_len2">代理平级奖</th>
								<th class="column_len2">福利奖</th>
								<th class="column_len2">福利平级奖</th>
								<th class="column_len2">手续费</th>
								<th class="column_len2">实得金额</th>
								<th class="column_len1">状态</th>
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