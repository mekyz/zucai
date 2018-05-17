<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>支付配置管理</title>
<meta name="pageId" content=managePayConfigInfos />
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
			getDataFromServer("ajaxGetPayConfigInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"platform"},{data:"mchId"},{data:"sortIndex"},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				var btnChange='<li><a href="payConfigInfoEdit?configId='+row.configId+'">修改</a></li>';
				data+=btnChange;
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[5,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("payConfigInfoAdd");
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
						<h2>支付配置列表</h2>
					</div>
					<div class="col-sm-6">
						<%@ include file="query_with_add.jsp"%>
					</div>
				</div>
				<div class="row" style="display: none;" id="div-advanced-search">
					<form class="form-inline well">
						<div class="form-group">
							<label for="sPlatform">支付平台:</label> <input type="text" class="form-control" id="sPlatform" />
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
								<th class="column_len2">支付平台</th>
								<th class="column_len2">商户号</th>
								<th class="column_len2">优先级</th>
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