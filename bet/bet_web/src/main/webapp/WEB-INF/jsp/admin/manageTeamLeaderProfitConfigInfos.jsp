<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>领导奖返利比率管理</title>
<meta name="pageId" content="manageTeamLeaderProfitConfigInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sName=$("#sName").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetTeamLeaderProfitConfigInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"configId"},{data:"name"},{data:"userType",render:LR_WEB.DATA_TABLES.RENDER.USER_TYPE},{data:"teamBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"userBetMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"profitPercent",render:function(data,type,row,meta){
				return data/100+"%";
			}},{data:"profitMaxMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},
			{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="teamLeaderProfitConfigInfoEdit?configId='+row.configId+'">修改</a></li>';
				if(row.status==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeStatus('+row.configId+','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					data+='<li><a onclick="changeStatus('+row.configId+','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[9,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
	});
	// 修改状态
	function changeStatus(configId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateTeamLeaderProfitConfigInfoStatus","configId="+configId+"&status="+status,function(data){
			showSuccessMsg("提示","更新状态成功！",function(){
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
							<h2>领导奖返利比率列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_without_add.jsp"%>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sName">名称:</label> <input type="text" class="form-control" id="sName" />
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
								<th class="column_len2">名称</th>
								<th class="column_len2">用户类型</th>
								<th class="column_len2">团队下注金额</th>
								<th class="column_len2">用户下注金额</th>
								<th class="column_len2">返利</th>
								<th class="column_len2">封顶金额</th>
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