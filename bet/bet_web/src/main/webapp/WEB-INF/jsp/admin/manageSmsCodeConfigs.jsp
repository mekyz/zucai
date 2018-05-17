<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>验证码配置管理</title>
<meta name="pageId" content="manageSmsCodeConfigs" />
<meta name="groupId" content="manageClients" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sConfigId=$("#sConfigId").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetSmsCodeConfigInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"name"},{data:"platform"},{data:"smsType",render:LR_WEB.DATA_TABLES.RENDER.SMS_CODE_TYPE},{data:"topic"},{data:"signName"},{data:"smsTemplateCode"},{data:"mnsEndpoint"},
			{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="smsCodeConfigEdit?configId='+row.configId+'">修改</a></li>';
				if(row.status==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeStatus('+row.configId+','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					data+='<li><a onclick="changeStatus('+row.configId+','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[1,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("smsCodeConfigAdd");
		});
	});
	// 修改状态
	function changeStatus(configId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateSmsCodeConfigInfoStatus","configId="+configId+"&status="+status,function(data){
			showSuccessMsg("提示","更新状态成功！",function(){
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
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>验证码配置列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_with_add.jsp"%>
						</div>
					</div>
					<div class="row" style="display: none;" id="div-advanced-search">
						<form class="form-inline well">
							<span>配置ID:</span> <input type="text" class="input-medium" id="sConfigId" />
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
								<th class="column_len2">名称</th>
								<th class="column_len2">平台</th>
								<th class="column_len2">短信类型</th>
								<th class="column_len3">主题</th>
								<th class="column_len2">短信签名</th>
								<th class="column_len2">签名模板</th>
								<th class="column_len3">Endpoint</th>
								<th class="column_len1">状态</th>
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