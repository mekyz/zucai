<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>团队返利比率管理</title>
<meta name="pageId" content="manageTeamProfitRateInfos" />
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
			getDataFromServer("ajaxGetTeamProfitRateInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"rateId"},{data:"name"},{data:"type1",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"type2",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"type3",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"type4",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"type5",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"type6",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"type7",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"sameLevel",render:function(data,type,row,meta){
			return data/100+"%";
		}},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="teamProfitRateInfoEdit?rateId='+row.rateId+'">查看</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[12,"desc"]];
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
							<h2>团队返利比率列表</h2>
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
								<th class="column_len2">一级返利</th>
								<th class="column_len2">二级返利</th>
								<th class="column_len2">三级返利</th>
								<th class="column_len2">四级返利</th>
								<th class="column_len2">五级返利</th>
								<th class="column_len2">六级返利</th>
								<th class="column_len2">七级返利</th>
								<th class="column_len2">平级奖</th>
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