<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
		window.location.href="teamLeaderBonus";
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
	<div id="app1" class="row">
		<style>
@media only screen and (max-width: 991px) {
	.table.table-no-more td {
		padding-left: 45% !important;
	}
}
</style>
		<div id="div_data" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>领袖奖金
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
												<input type="text" id='start' name="begintime" class="form-control  laydate-icon" readonly value="" placeholder="">
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-4">
												<input type="text" id='end' name="endtime" class="form-control  laydate-icon" readonly value="" placeholder="">
											</div>
										</div>
										<button type="button" class="btn  btn-success " style="margin-left: 15px !important;" onclick="query()">
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
								<th class="text-center" onclick="setOrderColumn('phaseId')">赛事</th>
								<th class="text-center" onclick="setOrderColumn('userBetMoney')">下注金额</th>
								<th class="text-center" onclick="setOrderColumn('teamBetMoney')">团队下注</th>
								<th class="text-center" onclick="setOrderColumn('profitMoney')">奖金</th>
								<th class="text-center" onclick="setOrderColumn('profitUserMoney')">实发</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in bonusData.teamLeaderProfitInfoList">
								<td data-title="日期" class="text-center">{{item.addDateLongStr}}</td>
								<td data-title="赛事" class="text-center">{{item.matchInfo.matchName}}</td>
								<td data-title="下注金额" class="text-center">{{item.userBetMoney}}</td>
								<td data-title="团队下注" class="text-center">{{item.teamBetMoney}}</td>
								<td data-title="奖金" class="text-center">{{item.profitMoney}}</td>
								<td data-title="实发奖金" class="text-center">{{item.profitUserMoney}}</td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.bonusData)">首页</a><a class="num" onclick="goPrePage(app1.bonusData)">上一页</a><span class="current">{{bonusData.currentPage}}</span> <a class="num"
									onclick="goNextPage(app1.bonusData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.bonusData)">末页</a> <span class="rows">共 {{bonusData.totalRecords}} 条记录 第{{bonusData.currentPage}} / {{bonusData.totalPage}} 页</span> <span
									class="jump"
								>转到第&nbsp; <input type="text" id="inp" :value="bonusData.currentPage" onclick="goPage(app1.bonusData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.bonusData)">GO</button>
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
								<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有奖金记录！</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">暂时没有奖金记录！</h2>
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
		var startDateLong='';
		var endDateLong='';
		var betUserId='';
		var changetype='';
		function query(){
			var start=$("#start").val();
			var end=$("#end").val();
			betUserId=$("#betUserId").val();
			changetype=$("#changetype").val();
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(teamLeaderProfitInfo){
			teamLeaderProfitInfo.userBetMoney=convertRmb(teamLeaderProfitInfo.userBetMoney);
			teamLeaderProfitInfo.teamBetMoney=convertRmb(teamLeaderProfitInfo.teamBetMoney);
			teamLeaderProfitInfo.profitMoney=convertRmb(teamLeaderProfitInfo.profitMoney);
			teamLeaderProfitInfo.profitUserMoney=convertRmb(teamLeaderProfitInfo.profitUserMoney);
			teamLeaderProfitInfo.addDateLongStr=getFormatDateTime(teamLeaderProfitInfo.addDateLong);
			teamLeaderProfitInfo.matchInfo={};
			teamLeaderProfitInfo.teamLeaderProfitConfigInfo={};
			getMatchInfo(teamLeaderProfitInfo);
			app1.bonusData.teamLeaderProfitInfoList.push(teamLeaderProfitInfo);
		};
		function getMatchInfo(teamLeaderProfitInfo){
			ajaxJson("ajaxGetMatchInfo","phaseId="+teamLeaderProfitInfo.phaseId,function(msg){
				var matchInfo=toJson(msg);
				matchInfo.homeTeamPicUrl='<%=basePath%>'+matchInfo.homeTeamPicUrl;
				matchInfo.awayTeamPicUrl='<%=basePath%>'+matchInfo.awayTeamPicUrl;
				matchInfo.matchDateStr=getFormatDateTime(matchInfo.matchDate);
				teamLeaderProfitInfo.matchInfo=matchInfo;
			},function(errmsg){});
		}
		function getProfitType(teamLeaderProfitInfo){
			ajaxJson("ajaxGetTeamLeaderProfitConfigInfo","configId="+teamLeaderProfitInfo.profitType,function(msg){
				var teamLeaderProfitConfigInfo=toJson(msg);
				teamLeaderProfitInfo.teamLeaderProfitConfigInfo=teamLeaderProfitConfigInfo;
			},function(errmsg){});
		}
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.bonusData.teamLeaderProfitInfoList=[];
			if(g_type!=type){
				app1.bonusData.totalRecords=0;
				app1.bonusData.totalPage=0;
				app1.bonusData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetTeamLeaderProfitInfoList?startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.bonusData,
					parseObjData);
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',
				data:{bonusData:{teamLeaderProfitInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>