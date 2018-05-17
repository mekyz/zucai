<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title><spring:message code="page_title" /></title>
<script src="<%=commonJsPath%>/vue/vue-2.5.9.min.js"></script>
<script src="<%=commonJsPath%>/simpleTables.js?v=20171205"></script>
<script>
	if(!isMobileWeb()){
		window.location.href="financialflow";
	}
</script>
</head>
<body>
	<div class="page-header" style="background: none;"></div>
	<style>
.phone-tq {
	display: none;
}

@media ( max-width : 414px) {
	.phone-tq {
		display: block;
	}
	.phone-tq .ticker-news-box {
		padding: 14px !important;
	}
}
</style>
	<style>
.table-responsive {
	overflow: auto;
}

@media only screen and (max-width: 991px) {
	table {
		width: 100% !important;
	}
}

.laydate_body .laydate_table {
	width: 230px !important;
}
</style>
	<div id="app1" class="row">
		<div id="div_data" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>财务流水
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="panel panel-default bk-bg-white">
								<div class="panel-body ">
									<form class="form-inline">
										<div class="form-group">
											<div class="col-md-4">
												<input type="text" id='start' class="form-control laydate-icon" readonly>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-4">
												<input type="text" id='end' class="form-control laydate-icon" readonly>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-4">
												<select id="changetype" class='form-control col-sm-2'>
													<option value="" selected>流水类型</option>
													<c:forEach items="${userBalanceLogTypeMap}" var="item">
														<option value="${item.key}">${item.value}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-4">
												<select id="moneytype" class='form-control col-sm-2'>
													<option value="" selected>余额类型</option>
													<!-- <option value="11">彩金钱包</option>
													<option value="13">体验彩金</option> -->
													<c:forEach items="${moneyUnitMap}" var="item">
														<option value="${item.key}">${item.value}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<button type="button" class="btn btn-success" style="margin-left: 15px !important;" onclick="query()">
											<span class="bigger-110">查询</span>
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<script>
						layui.use('laydate',function(){
							var laydate=layui.laydate;
							var start={format:'YYYY-MM-DD',min:'2010-01-01 00:00:00', //设定最小日期为当前日期
							max:'2099-06-16 23:59:59', //最大日期
							istime:false,istoday:true,choose:function(datas){
								end.min=datas; //开始日选好后，重置结束日的最小日期
								end.start=datas; //将结束日的初始值设定为开始日
							}};
							var end={format:'YYYY-MM-DD',
							// min: laydate.now(),
							min:'2010-01-01 00:00:00',max:'2099-06-16 23:59:59',istime:false,istoday:true,choose:function(datas){
								start.max=datas; //结束日选好后，重置开始日的最大日期
							}};
							document.getElementById('start').onclick=function(){
								start.elem=this;
								laydate(start);
							};
							document.getElementById('end').onclick=function(){
								end.elem=this;
								laydate(end);
							};
						});
					</script>
					<table class="table table-no-more table-bordered table-striped mb-none">
						<thead>
							<tr>
								<th class="text-center" onclick="setOrderColumn('addDateLong')">日期</th>
								<th class="text-center" onclick="setOrderColumn('money')">金额</th>
								<th class="text-center" onclick="setOrderColumn('moneyUnit')">币种</th>
								<th class="text-center" onclick="setOrderColumn('logType')">类型</th>
								<th class="text-center" onclick="setOrderColumn('remark')">备注</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in userBalanceLogData.userBalanceLogInfoList">
								<td data-title="日期" class="text-center">{{item.addDateLongStr}}</td>
								<td data-title="金额">{{item.money}}</td>
								<td data-title="币种">{{item.moneyUnitStr}}</td>
								<td data-title="类型">{{item.logTypeStr}}</td>
								<td data-title="备注" class="text-center">{{item.remark}}</td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.userBalanceLogData)">首页</a><a class="num" onclick="goPrePage(app1.userBalanceLogData)">上一页</a><span class="current">{{userBalanceLogData.currentPage}}</span>
								<a class="num" onclick="goNextPage(app1.userBalanceLogData)">下一页</a> <a class="num" onclick="goLastPage(app1.userBalanceLogData)">末页</a> <span class="rows">共
									{{userBalanceLogData.totalRecords}} 条记录 第{{userBalanceLogData.currentPage}} / {{userBalanceLogData.totalPage}} 页</span> <span class="jump">转到第&nbsp; <input type="text" id="inp"
									:value="userBalanceLogData.currentPage" onclick="goPage(app1.userBalanceLogData,this)"
								>&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.userBalanceLogData)">GO</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="div_no_data" class="container-fluid content">
			<div class="row">
				<div id="content" class="col-sm-12 full">
					<div class="row box-error">
						<div class="col-lg-12 col-md-12 col-xs-12">
							<div class="text-center">
								<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有余额记录！</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">暂时没有余额记录！</h2>
								<br /> <a href="javascript: history.go(-1)">
									<button type="button" class="btn btn-danger">
										<i class="fa fa-chevron-left"></i> 返回
									</button>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		orderColumn='addDateLong';
		orderDir='desc';
		var currentDate=getCurrentTimeLong();
		$("#start").val(getMyFormatDateTime(currentDate-30*24*60*60*1000,'YYYY-MM-DD'));
		$("#end").val(getMyFormatDateTime(currentDate,'YYYY-MM-DD'));
		var startDateLong='';
		var endDateLong='';
		var moneytype='';
		var logType='';
		function query(){
			var start=$("#start").val();
			var end=$("#end").val();
			moneytype=$("#moneytype").val();
			logType=$("#changetype").val();
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(userBalanceLogInfo){
			userBalanceLogInfo.money=convertRmb(userBalanceLogInfo.money);
			userBalanceLogInfo.moneyUnitStr=getMoneyUnitString(userBalanceLogInfo.moneyUnit);
			userBalanceLogInfo.logTypeStr=getUserBalanceLogTypeString(userBalanceLogInfo.logType);
			userBalanceLogInfo.addDateLongStr=getFormatDateTime(userBalanceLogInfo.addDateLong);
			app1.userBalanceLogData.userBalanceLogInfoList.push(userBalanceLogInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.userBalanceLogData.userBalanceLogInfoList=[];
			if(g_type!=type){
				app1.userBalanceLogData.totalRecords=0;
				app1.userBalanceLogData.totalPage=0;
				app1.userBalanceLogData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetUserBalanceLogInfoList?moneyUnit="+moneytype+"&logType="+logType+"&startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn
					+"&order[0][dir]="+orderDir,app1.userBalanceLogData,parseObjData);
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',
				data:{userBalanceLogData:{userBalanceLogInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>