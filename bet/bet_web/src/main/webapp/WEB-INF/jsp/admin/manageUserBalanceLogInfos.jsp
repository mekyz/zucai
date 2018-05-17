<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户余额记录</title>
<meta name="pageId" content="manageUserBalanceLogInfos" />
<meta name="groupId" content="statistics" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sLogType=$("#sLogType").val();
		param.sMoneyUnit=$("#sMoneyUnit").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetUserBalanceLogInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"money",render:function(data,type,row,meta){
			data="";
			//if(row.moneyUnit==LR_WEB.MONEY_UNIT.US||row.moneyUnit==LR_WEB.MONEY_UNIT.RMB){
				data=convertRmb(row.money);
			//}else{
			//	data=row.money;
			//}
			return data;
		}},{data:"moneyUnit",render:LR_WEB.DATA_TABLES.RENDER.MONEY_UNIT},{data:"logType",render:LR_WEB.DATA_TABLES.RENDER.USER_BALANCE_LOG_TYPE},{data:"remark"},
			{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG}];
		d.aaSorting=[[6,"desc"]];
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
							<h2>用户余额记录列表</h2>
						</div>
						<div class="col-sm-6">
							<%@ include file="query_without_add.jsp"%>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sUserId">用户ID:</label> <input type="text" class="form-control" id="sUserId" />
							</div>
							<div class="form-group">
								<label for="sLogType">来源:</label> <select class="form-control" id="sLogType">
									<option value="">全部</option>
									<c:forEach items="${userBalanceLogTypeList}" var="userBalanceLogType" varStatus="status">
										<option value="${userBalanceLogType.type}">${userBalanceLogType.desc}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="sMoneyUnit">类型:</label> <select class="form-control" id="sMoneyUnit">
									<option value="">全部</option>
									<c:forEach items="${moneyUnitList}" var="moneyUnit" varStatus="status">
										<option value="${moneyUnit.type}">${moneyUnit.desc}</option>
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
								<th class="column_len2">用户ID</th>
								<th class="column_len2">金额</th>
								<th class="column_len2">类型</th>
								<th class="column_len2">来源</th>
								<th class="column_len6">备注</th>
								<th class="column_len3">时间</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div><%@ include file="table_footer.jsp"%>
</body>
</html>