<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>客户端Banner管理</title>
<meta name="pageId" content="manageClientBanners" />
<meta name="groupId" content="manageClients" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sContent=$("#sContent").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetClientBannerInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"partnerId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"agentId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},
			{data:"bannerPosition",render:LR_WEB.DATA_TABLES.RENDER.CLIENT_POSITION},{data:"bannerType",render:LR_WEB.DATA_TABLES.RENDER.CLIENT_TYPE},
			{data:"picUrl",render:function(data,type,row,meta){
				if(isNull(data)){
					return data;
				}else{
					return "<img src='<%=basePath%>"+data+"' width='80px;'/>";
				}
			}},{data:"name"},{data:"content"},{data:"updateDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				var btnView='<li><a href="clientBannerInfoEdit?bannerId='+row.bannerId+'">修改</a></li>';
				var btnDelete='<li><a onclick="del('+row.bannerId+')">删除</a></li>';
				var btnStatus='';
				if(row.status==LR_WEB.STATUS.ENABLED){
					btnStatus='<li><a onclick="changeStatus(\''+row.bannerId+'\','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					btnStatus='<li><a onclick="changeStatus(\''+row.bannerId+'\','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				data+=btnView+btnStatus+btnDelete;
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[3,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("clientBannerInfoAdd");
		});
	});
	function del(bannerId){
		confirmDialog("确定要删除此图片吗？","ajaxDeleteClientBannerInfo","bannerId="+bannerId,function(data){
			showSuccessMsg("提示","删除成功！",function(){
				refreshPage();
			});
		},function(errmsg){
			showMsg(errmsg);
		});
	}
	// 修改状态
	function changeStatus(bannerId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateClientBannerInfoStatus","bannerId="+bannerId+"&status="+status,function(data){
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
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>客户端Banner列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_with_add.jsp"%>
						</div>
					</div>
					<div class="row" style="display: none;" id="div-advanced-search">
						<form class="form-inline well">
							<span>事件内容:</span> <input type="text" class="input-medium" id="sContent" />
							<button type="button" class="btn" id="btn-advanced-search">
								<i class="fa fa-search"></i>查询
							</button>
						</form>
					</div>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1">序号</th>
								<th class="column_len2">合伙人</th>
								<th class="column_len2">代理商</th>
								<th class="column_len2">客户端位置</th>
								<th class="column_len2">Banner类型</th>
								<th class="column_len2">图片</th>
								<th class="column_len2">名称</th>
								<th class="column_len2">事件内容</th>
								<th class="column_len3">更新时间</th>
								<th class="column_len1">状态</th>
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