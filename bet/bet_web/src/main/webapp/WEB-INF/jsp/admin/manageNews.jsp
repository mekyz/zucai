<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>消息管理</title>
<meta name="pageId" content="manageNews" />
<meta name="groupId" content="news" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sTitle=$("#sTitle").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetNewsList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"title"},{data:"sortId"},{data:"author"},{data:"topStatus",render:function(data,type,row,meta){
			if(data==LR_WEB.STATUS.ENABLED){
				return '是';
			}else{
				return '否';
			}
		}},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"updateDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{data:"valideDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="newsEdit?newsId='+row.newsId+'">修改</a></li>';
				if(row.topStatus==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeTopStatus('+row.newsId+','+LR_WEB.STATUS.DISABLED+');">取消顶部显示</a></li>';
				}else{
					data+='<li><a onclick="changeTopStatus('+row.newsId+','+LR_WEB.STATUS.ENABLED+');">顶部显示</a></li>';
				}
				if(row.status==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeStatus('+row.newsId+','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					data+='<li><a onclick="changeStatus('+row.newsId+','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				data+='<li><a onclick="setValidateDateLong('+row.newsId+');">设置有效期</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[6,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			ajaxJson("ajaxGetNewsSortInfo","sortId="+data.sortId,function(msg){
				var newsSortInfo=toJson(msg);
				$('td:eq(2)',row).html(newsSortInfo.name);
			},function(errmsg){});
			return row;
		};
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("newsAdd");
		});
	});
	// 修改状态
	function changeTopStatus(newsId,status){
		confirmDialog("确定要更改顶部显示状态吗？","ajaxUpdateNewsInfoTopStatus","newsId="+newsId+"&status="+status,function(data){
			showSuccessMsg("提示","更新顶部显示状态成功！",function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 修改状态
	function changeStatus(newsId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateNewsInfoStatus","newsId="+newsId+"&status="+status,function(data){
			showSuccessMsg("提示","更新状态成功！",function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	function setValidateDateLong(newsId){
		showChooseMsg("请输入有效期(格式：2016-01-01 12:12:12)",'有效期：<input type="text" id="validate_date_long" value="" placeholder="" style="width:100%;"/><br/>','设置','取消',function(){
			var date=$("#validate_date_long").val();
			if(isNull(date)){
				showMsg("输入为空！");
				return;
			}
			date=getDateTimeLong(date);
			showLog("date:"+date);
			ajaxTipJson("ajaxUpdateNewsInfoValidateDateLong","newsId="+newsId+"&valideDateLong="+date,true,"请稍后...",function(data){
				showSuccessMsg("提示",data,function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
		},null);
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
							<h2>消息列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_with_add.jsp"%>
						</div>
					</div>
					<div class="row" style="display: none;" id="div-advanced-search">
						<form class="form-inline well">
							<span>标题:</span> <input type="text" class="input-medium" id="sTitle" />
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
								<th class="column_len6">标题</th>
								<th class="column_len2">分类名</th>
								<th class="column_len2">发布者</th>
								<th class="column_len1">顶部显示</th>
								<th class="column_len1">状态</th>
								<th class="column_len3">发布日期</th>
								<th class="column_len3">有效日期</th>
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