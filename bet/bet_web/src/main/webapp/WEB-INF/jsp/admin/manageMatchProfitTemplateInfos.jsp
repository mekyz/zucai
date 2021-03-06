<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>波胆模板管理</title>
<meta name="pageId" content="manageMatchProfitTemplateInfos" />
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
			getDataFromServer("ajaxGetMatchProfitTemplateInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"templateId"},{data:"matchType",render:LR_WEB.DATA_TABLES.RENDER.BODAN_TYPE},{data:"score1",render:function(data,type,row,meta){
			return row.score1+':'+row.score2;
		}},{data:"profitPercent",render:function(data,type,row,meta){
			return data/100+'%';
		}},{data:"amount",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="matchProfitTemplateInfoEdit?templateId='+row.templateId+'">修改</a></li>';
				if(row.status==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeStatus('+row.templateId+','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					data+='<li><a onclick="changeStatus('+row.templateId+','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				data+='<li><a onclick="updateToMatch('+row.templateId+');">更新到未开赛的比赛</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[7,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("matchProfitTemplateInfoAdd");
		});
	});
	// 修改状态
	function changeStatus(templateId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateMatchProfitTemplateInfoStatus",{"templateId":templateId,"status":status},function(data){
			showSuccessMsg("提示","更新状态成功！",function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 更新到未开赛的比赛
	function updateToMatch(templateId){
		confirmDialog("确定要更新到未开赛的比赛吗？","ajaxUpdateMatchProfitTemplateInfoToUnBeginMatch",{"templateId":templateId},function(data){
			showSuccessMsg("提示",data,function(){
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
				<div class="row">
					<div class="col-sm-6">
						<h2>波胆模板列表</h2>
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
								<th class="column_len2">类型</th>
								<th class="column_len2">波胆</th>
								<th class="column_len2">收益</th>
								<th class="column_len2">份数</th>
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