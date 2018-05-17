<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户实名认证管理</title>
<meta name="pageId" content="manageUserAuths" />
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
			getDataFromServer("ajaxGetUserAuthInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"name"},{data:"number"},{data:"identifityNumber"},{data:"sex",render:LR_WEB.DATA_TABLES.RENDER.SEX_TYPE},
			{data:"birthday",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{data:"province",render:function(data,type,row,meta){
				return row.province+' '+row.city;
			}},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.APPLY_STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				var btnView='';
				if(row.status==LR_WEB.APPLY_STATUS.APPLY){
					btnView='<li><a href="userAuthEdit?userId='+row.userId+'">审核</a></li>';
				}else{
					btnView='<li><a href="userAuthEdit?userId='+row.userId+'">查看</a></li>';
				}
				data+=btnView;
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[9,"desc"]];
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
							<h2>用户实名认证列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_without_add.jsp"%>
						</div>
					</div>
					<div class="row" style="display: none;" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sUserId">用户账号:</label> <input type="text" class="form-control" id="sUserId" />
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
								<th class="column_len2">账号</th>
								<th class="column_len2">姓名</th>
								<th class="column_len2">手机号码</th>
								<th class="column_len3">身份证</th>
								<th class="column_len1">性别</th>
								<th class="column_len3">出生日期</th>
								<th class="column_len3">城市</th>
								<th class="column_len1">状态</th>
								<th class="column_len3">申请日期</th>
								<th class="column_len2">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div><%@ include file="table_footer.jsp"%>
</body>
</html>