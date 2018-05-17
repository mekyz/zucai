<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户升级条件管理</title>
<meta name="pageId" content="manageUserTypeConditionInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserType=$("#sUserType").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetUserTypeConditionInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"userType",render:LR_WEB.DATA_TABLES.RENDER.USER_TYPE},{data:"directCount"},{data:"teamCount"},{data:"money",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"updateDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="userTypeConditionInfoEdit?userType='+row.userType+'">修改</a></li>';
				if(row.status==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeStatus('+row.userType+','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					data+='<li><a onclick="changeStatus('+row.userType+','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[1,"asc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
	});
	// 修改状态
	function changeStatus(userType,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateUserTypeConditionInfoStatus","userType="+userType+"&status="+status,function(data){
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
							<h2>用户升级条件列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_without_add.jsp"%>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sUserType">用户级别:</label> <input type="text" class="form-control" id="sUserType" />
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
								<th class="column_len2">用户级别</th>
								<th class="column_len2">直推人数</th>
								<th class="column_len2">团队人数</th>
								<th class="column_len2">充值金额</th>
								<th class="column_len1">状态</th>
								<th class="column_len3">更新时间</th>
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