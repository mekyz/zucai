<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>消息分类管理</title>
<meta name="pageId" content="manageNewsSorts" />
<meta name="groupId" content="news" />
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
			getDataFromServer("ajaxGetNewsSortList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"name"},{data:"parentId"},{data:"levelId"},{orderable:false,searchable:false,render:function(data,type,row,meta){
			data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
			data+='<li><a href="newsSortEdit?sortId='+row.sortId+'">修改</a></li>';
			data+='</ul></div>';
			return data;
		}}];
		d.aaSorting=[[3,"asc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			if(!isNull(data.parentId)){
				ajaxTipJson("ajaxGetNewsSortInfo","sortId="+data.parentId,false,null,function(data){
					var newsSortInfo=toJson(data);
					$('td:eq(2)',row).html(newsSortInfo.name);
				},function(msg){});
			}
			return row;
		};
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("newsSortAdd");
		});
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
							<h2>消息分类列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_with_add.jsp"%>
						</div>
					</div>
					<div class="row" style="display: none;" id="div-advanced-search">
						<form class="form-inline well">
							<span>分类名:</span> <input type="text" class="input-medium" id="sName" />
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
								<th class="column_len2">分类名</th>
								<th class="column_len2">上级分类</th>
								<th class="column_len2">级别</th>
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