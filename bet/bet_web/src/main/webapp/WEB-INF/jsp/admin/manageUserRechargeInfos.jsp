<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户充值管理</title>
<meta name="pageId" content="manageUserRechargeInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sStatus=$("#sStatus").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetUserRechargeInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"rechargeId"},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"money",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"payName"},{data:"payBankCardId"},
			{data:"payeeBankCardId"},{data:"status",render:function(data,type,row,meta){
				if(data===LR_WEB.APPLY_STATUS.APPLY){
					return '已申请，等待审核';
				}else if(data===LR_WEB.APPLY_STATUS.VERIFY_SUCCESS){
					return '审核成功';
				}else if(data===LR_WEB.APPLY_STATUS.VERIFY_FAIL){
					return '审核失败';
				}
			}},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="userRechargeInfoEdit?rechargeId='+row.rechargeId+'">查看</a></li>';
				if(row.status===LR_WEB.APPLY_STATUS.APPLY){
					data+='<li><a onclick="changeFinishStatus(\''+row.rechargeId+'\','+LR_WEB.APPLY_STATUS.VERIFY_SUCCESS+');">已收款</a></li>';
				}
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[8,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
	});
	function changeFinishStatus(rechargeId,status){
		confirmDialog("确定已收款了吗？","ajaxUpdateUserRechargeInfoVerify",{'rechargeId':rechargeId,'status':status},function(data){
			showSuccessMsg("提示","已收款成功，用户充值成功！",function(){
				refreshPage();
			});
		},function(errmsg){
			showMsg(errmsg);
		});
	}
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>用户充值列表</h2>
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
								<label for="sStatus">充值状态:</label> <select class="form-control" id="sStatus">
									<option value="">全部状态</option>
									<c:forEach items="${applyStatusList}" var="applyStatus" varStatus="status">
										<option value="${applyStatus.status}">${applyStatus.desc}</option>
									</c:forEach>
								</select>
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
								<th class="column_len2">充值ID</th>
								<th class="column_len2">用户ID</th>
								<th class="column_len2">充值金额(￥)</th>
								<th class="column_len2">付款人</th>
								<th class="column_len3">付款卡号</th>
								<th class="column_len3">收款卡号</th>
								<th class="column_len2">状态</th>
								<th class="column_len3">申请时间</th>
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