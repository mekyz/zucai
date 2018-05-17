<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>客户端管理</title>
<meta name="pageId" content="manageClientBannerInfos" />
<meta name="groupId" content="more" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sPlatform=$("#sPlatform").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetClientInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"platform"},{data:"agentId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"versionName"},{data:"versionCode"},{data:"content"},
			{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="clientInfoEdit?id='+row.id+'">修改</a></li>';
				data+='<li><a onclick="del(\''+row.id+'\')">删除</a></li>';
				if(row.status==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeStatus(\''+row.id+'\','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					data+='<li><a onclick="changeStatus(\''+row.id+'\','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[7,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("clientInfoAdd");
		});
	});
	function del(id){
		confirmDialog("确定要删除此客户端吗？","ajaxDeleteClientInfo","id="+id,function(data){
			showSuccessMsg("提示","删除成功！",function(){
				refreshPage();
			});
		},function(errmsg){
			showMsg(errmsg);
		});
	}
	// 修改状态
	function changeStatus(id,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateClientInfoStatus","id="+id+"&status="+status,function(data){
			showSuccessMsg("提示","更新成功！",function(){
				refreshPage();
			});
		},function(errmsg){
			showMsg(errmsg);
		});
	};
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="row">
					<div class="col-sm-6">
						<h2>客户端列表</h2>
					</div>
					<div class="col-sm-6">
						<%@ include file="query_with_add.jsp"%>
					</div>
				</div>
				<div class="row" style="display: none;" id="div-advanced-search">
					<form class="form-inline well">
						<span>平台:</span> <input type="text" class="input-medium" id="sPlatform" />
						<button type="button" class="btn" id="btn-advanced-search">
							<i class="fa fa-search"></i> 查询
						</button>
					</form>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1">序号</th>
								<th class="column_len2">平台</th>
								<th class="column_len2">代理</th>
								<th class="column_len2">版本号</th>
								<th class="column_len2">版本数字</th>
								<th class="column_len6">描述</th>
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