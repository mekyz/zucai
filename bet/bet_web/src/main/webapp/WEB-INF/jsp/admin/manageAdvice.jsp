<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>意见反馈管理</title>
<meta name="pageId" content="manageAdvice" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sNumber=$("#sNumber").val();
		param.sEmail=$("#sEmail").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetAdviceList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"platform"},{data:"adviceType"},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"name"},{data:"number"},{data:"content"},
			{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{data:"readStatus",render:function(data,type,row,meta){
				return data===LR_WEB.STATUS.ENABLED?"已阅读":"未阅读";
			}},{data:"replyStatus",render:function(data,type,row,meta){
				return data===LR_WEB.STATUS.ENABLED?"已回复":"未回复";
			}},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="advice?adviceId='+row.adviceId+'">查看</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[7,"desc"]];
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
							<h2>意见反馈列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_without_add.jsp"%>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sUserId">用户账号:</label> <input type="text" class="form-control" id="sUserId" />
							</div>
							<div class="form-group">
								<label for="sNumber">手机号码:</label> <input type="text" class="form-control" id="sNumber" />
							</div>
							<div class="form-group">
								<label for="sEmail">邮箱:</label> <input type="text" class="form-control" id="sEmail" />
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
								<th class="column_len2">反馈平台</th>
								<th class="column_len2">反馈类型</th>
								<th class="column_len2">账号</th>
								<th class="column_len2">联系人</th>
								<th class="column_len2">手机号码</th>
								<th class="column_len6">内容</th>
								<th class="column_len3">反馈时间</th>
								<th class="column_len1">阅读状态</th>
								<th class="column_len1">回复状态</th>
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