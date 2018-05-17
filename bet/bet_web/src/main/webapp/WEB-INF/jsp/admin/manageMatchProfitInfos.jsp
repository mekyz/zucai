<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>赛事波胆管理</title>
<meta name="pageId" content="manageMatchProfitInfos" />
<meta name="groupId" content="more" />
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
			var url="ajaxGetMatchProfitInfoList";
			var phaseId='${phaseId}';
			if(!isNull(phaseId)){
				url="ajaxGetMatchProfitInfoList?phaseId="+phaseId;
			}
			getDataFromServer(url,data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"phaseId"},{data:"phaseId"},{data:"matchType",render:LR_WEB.DATA_TABLES.RENDER.BODAN_TYPE},{data:"score1",render:function(data,type,row,meta){
			return row.score1+':'+row.score2;
		}},{data:"profitPercent",render:function(data,type,row,meta){
			return data/100+'%';
		}},{data:"amount",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"saledAmount",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},
			{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="matchProfitInfoEdit?profitId='+row.profitId+'">修改</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[2,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			ajaxJson("ajaxGetMatchInfo","phaseId="+data.phaseId,function(msg){
				var matchInfo=toJson(msg);
				$('td:eq(1)',row).html(matchInfo.matchName+'<br>('+matchInfo.homeTeam+'-'+matchInfo.awayTeam+')');
				$('td:eq(2)',row).html(getFormatDateTime(matchInfo.matchDate));
			},function(errmsg){});
			return row;
		};
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("matchProfitInfoAdd");
		});
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="row">
					<div class="col-sm-6">
						<h2>赛事波胆列表</h2>
					</div>
					<div class="col-sm-6">
						<%@ include file="query_with_add.jsp"%>
					</div>
				</div>
				<div class="row" id="div-advanced-search">
					<form class="form-inline well">
						<div class="form-group">
							<label for="sPhaseId">赛事ID:</label> <input type="text" class="form-control" id="sPhaseId" value="${phaseId}" />
						</div>
						<div class="form-group">
							<button type="button" class="btn" style="margin-bottom: 0px !important;" id="btn-advanced-search">
								<i class="fa fa-search"></i> 查询
							</button>
						</div>
					</form>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1">序号</th>
								<th class="column_len6">赛事</th>
								<th class="column_len3">比赛时间</th>
								<th class="column_len2">波胆类型</th>
								<th class="column_len2">比分</th>
								<th class="column_len2">收益</th>
								<th class="column_len2">份数</th>
								<th class="column_len2">已售份数</th>
								<th class="column_len1">状态</th>
								<th class="column_len3">添加日期</th>
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