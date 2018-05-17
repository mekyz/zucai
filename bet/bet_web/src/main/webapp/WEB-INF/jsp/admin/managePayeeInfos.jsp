<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>收款信息管理</title>
<meta name="pageId" content="managePayeeInfos" />
<meta name="groupId" content="more" />
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
			getDataFromServer("ajaxGetPayeeInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"payeeId"},{data:"payeeName"},{data:"bankName"},{data:"bankCardId"},{data:"type",render:LR_WEB.DATA_TABLES.RENDER.PAYEE_TYPE},{data:"sortIndex"},
			{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				var btnChange='<li><a href="payeeInfoEdit?payeeId='+row.payeeId+'">修改</a></li>';
				data+=btnChange;
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[8,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("payeeInfoAdd");
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
						<h2>收款信息列表</h2>
					</div>
					<div class="col-sm-6">
						<%@ include file="query_with_add.jsp"%>
					</div>
				</div>
				<div class="row" style="display: none;" id="div-advanced-search">
					<form class="form-inline well">
						<div class="form-group">
							<label for="sName">姓名:</label> <input type="text" class="form-control" id="sName" />
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
								<th class="column_len2">ID</th>
								<th class="column_len2">收款人</th>
								<th class="column_len2">开户行</th>
								<th class="column_len2">卡号</th>
								<th class="column_len2">收款类型</th>
								<th class="column_len1">优先级</th>
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