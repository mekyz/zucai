<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>球队管理</title>
<meta name="pageId" content="manageTeamInfos" />
<meta name="groupId" content="manageClients" />
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
			getDataFromServer("ajaxGetTeamInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"teamId"},{data:"name"},{data:"picUrl",render:function(data,type,row,meta){
			return '<img src="<%=basePath%>'+data+'" style="width:60px;"/>';
		}},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{orderable:false,searchable:false,render:function(data,type,row,meta){
			data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
			data+='<li><a href="teamInfoEdit?teamId='+row.teamId+'">修改</a></li>';
			if(row.status==LR_WEB.STATUS.ENABLED){
				data+='<li><a onclick="changeStatus('+row.teamId+','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
			}else{
				data+='<li><a onclick="changeStatus('+row.teamId+','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
			}
			data+='</ul></div>';
			return data;
		}}];
		d.aaSorting=[[1,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("teamInfoAdd");
		});
	});
	// 修改状态
	function changeStatus(teamId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateTeamInfoStatus",{"teamId":teamId,"status":status},function(data){
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
							<h2>球队列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_with_add.jsp"%>
						</div>
					</div>
					<div class="row" style="display: none;" id="div-advanced-search">
						<form class="form-inline well">
							<span>球队名称:</span> <input type="text" class="input-medium" id="sName" />
							<button type="button" class="btn" id="btn-advanced-search">
								<i class="fa fa-search"></i> 查询
							</button>
						</form>
					</div>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1">序号</th>
								<th class="column_len2">球队ID</th>
								<th class="column_len6">球队名称</th>
								<th class="column_len2">LOGO</th>
								<th class="column_len1">状态</th>
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