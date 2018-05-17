<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>赛事统计信息</title>
<meta name="pageId" content="manageMatchStatisticsLogInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sPhaseId=$("#sPhaseId").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetMatchStatisticsLogInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"phaseId"},{data:"",orderable:false,searchable:false},{data:"userBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"finalBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"finalBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"finalWinMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"halfBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"halfBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"halfWinMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			/* {data:"countBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"countBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"countWinMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"shareBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"agentBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"sameLevel1BonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"sameLevel2BonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"benefitBonusMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"teamMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB}, */{data:"companyMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"updateDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG}];
		d.aaSorting=[[1,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			if(!isNull(data.phaseId)){
				ajaxTipJson("ajaxGetMatchInfo","phaseId="+data.phaseId,false,null,function(data){
					var matchInfo=toJson(data);
					$('td:eq(1)',row).html(matchInfo.matchName+'<br>('+matchInfo.homeTeam+'-'+matchInfo.awayTeam+')');
					$('td:eq(2)',row).html(getMyFormatDateTime(matchInfo.matchDate,'MM-dd hh:mm'));
				},function(msg){});
			}
			return row;
		};
		var _table=$table.DataTable(d);
		initTableBtns(_table);
	});
	// 统计赛事金额
	function updateMatchStatisticsJob(){
		confirmDialog("确定要统计赛事金额吗？这将花费比较多的时间。","ajaxUpdateMatchStatisticsJob",{},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>赛事统计信息列表</h2>
						</div>
						<div class="col-sm-6">
							<div class="row">
								<div class="btn-toolbar">
									<div class="pull-right" id="div-fuzzy-search">
										<input type="text" placeholder="模糊查询" id="fuzzy-search">
										<div class="btn-group" style="float: none;">
											<button type="button" class="btn" id="btn-simple-search">
												<i class="fa fa-search"></i>
											</button>
											<button type="button" class="btn" title="高级查询" id="toggle-advanced-search">
												<i class="fa fa-angle-double-down"></i>
											</button>
										</div>
									</div>
									<button type="button" class="btn btn-primary pull-right" onclick="updateMatchStatisticsJob()">统计赛事金额</button>
								</div>
							</div>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
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
								<th class="column_len4">赛事</th>
								<th class="column_len3">比赛时间</th>
								<th class="column_len2">下注金额</th>
								<th class="column_len2">全场下注</th>
								<th class="column_len2">全场奖</th>
								<th class="column_len2">全场拨出</th>
								<th class="column_len2">半场下注</th>
								<th class="column_len2">半场奖</th>
								<th class="column_len2">半场拨出</th>
								<!-- <th class="column_len2">球数下注</th>
								<th class="column_len2">球数奖</th>
								<th class="column_len2">球数赢</th>
								<th class="column_len2">分享佣金</th>
								<th class="column_len2">代理佣金</th>
								<th class="column_len2">代理平级奖</th>
								<th class="column_len2">福利平级奖</th>
								<th class="column_len2">福利奖</th>
								<th class="column_len2">团队业绩奖</th> -->
								<th class="column_len2">公司利润</th>
								<th class="column_len3">统计时间</th>
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