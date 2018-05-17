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
		window.location.href="userBetReturnList";
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

@media only screen and (max-width: 767px) {
	.moblie-ball {
		display: none !important;
	}
	.ball-name {
		display: block !important; padding-left: 0 !important; text-align: center !important;
	}
}

@media only screen and (min-width: 767px) {
	.ball-name {
		display: none !important;
	}
}

.text-xxx {
	width: 50%; display: inline-block; float: left;
}
</style>
	<style>
html {
	height: 100%;
}
</style>
	<div id="app1" class="row">
		<div id="div_data" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>投注撤回记录
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
								end.start=datas;//将结束日的初始值设定为开始日
							}};
							var end={format:'YYYY-MM-DD',
							// min: laydate.now(),
							min:'2010-01-01 00:00:00',max:'2099-06-16 23:59:59',istime:false,istoday:true,choose:function(datas){
								start.max=datas; //结束日选好后，重置开始日的最大日期
							}};
							document.getElementById('start').onclick=function(){
								start.elem=this;
								laydate(start);
							}
							document.getElementById('end').onclick=function(){
								end.elem=this
								laydate(end);
							}
						});
					</script>
					<table class="table table-no-more table-bordered table-striped mb-none">
						<thead>
							<tr>
								<th class="text-center" onclick="setOrderColumn('addDateLong')">下注时间</th>
								<th class="text-center">赛事</th>
								<th class="text-center" onclick="setOrderColumn('matchName')">球队</th>
								<th class="text-center" onclick="setOrderColumn('matchType')">波胆项目</th>
								<th class="text-center" onclick="setOrderColumn('score1')">比分</th>
								<th class="text-center" onclick="setOrderColumn('money')">下注金额（￥）</th>
								<th class="text-center">预期盈利（￥）</th>
								<th class="text-center">备注</th>
								<th class="text-center" onclick="setOrderColumn('status')">状态</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in betData.userBetReturnInfoList">
								<td data-title="下注时间" class="text-center">{{item.addDateLongStr}}</td>
								<td data-title="赛事" class="text-center">{{item.matchInfo.matchName}}</td>
								<td data-title="球队" class="moblie-ball"><span class="text-xxx" style="text-align: right;">{{item.matchInfo.homeTeam}} <!-- <img style="height: 30px;"
										:src="item.matchInfo.homeTeamPicUrl"
									/> --> <em>-</em>
								</span> <span class="text-xxx" style="text-align: left;">&nbsp;&nbsp;<!-- <img style="height: 30px;" :src="item.matchInfo.awayTeamPicUrl" /> --> {{item.matchInfo.awayTeam}}
								</span></td>
								<td data-title="波胆项目" class="text-center">{{item.matchProfitInfo.matchTypeStr}}</td>
								<td data-title="比分" class="text-center">{{item.matchProfitInfo.score1}}:{{item.matchProfitInfo.score2}}</td>
								<td data-title="下注金额（￥）" class="text-center">{{item.moneyStr}}</td>
								<td data-title="预期盈利（￥）" class="text-center">{{item.matchProfitInfo.profitPercentStr}}</td>
								<td data-title="预期盈利（￥）" class="text-center">{{item.remark}}</td>
								<td data-title="状态" class="text-center">已撤回</td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.betData)">首页</a><a class="num" onclick="goPrePage(app1.betData)">上一页</a><span class="current">{{betData.currentPage}}</span> <a class="num"
									onclick="goNextPage(app1.betData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.betData)">末页</a> <span class="rows">共 {{betData.totalRecords}} 条记录 第{{betData.currentPage}} / {{betData.totalPage}} 页</span> <span class="jump">转到第&nbsp;
									<input type="text" id="inp" :value="betData.currentPage" onclick="goPage(app1.betData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.matchData)">GO</button>
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
								<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有投注记录！</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">暂时没有投注记录！</h2>
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
		function query(){
			var start=$("#start").val();//+" 00:00:00";
			var end=$("#end").val();//+" 00:00:00";
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(userBetReturnInfo){
			var current=getCurrentTimeLong();
			userBetReturnInfo.addDateLongStr=getFormatDateTime(userBetReturnInfo.addDateLong);
			userBetReturnInfo.moneyStr=convertRmb(userBetReturnInfo.money);
			userBetReturnInfo.matchInfo={};
			userBetReturnInfo.matchProfitInfo={};
			userBetReturnInfo.teamBetMoney=0;
			getMatchInfo(userBetReturnInfo);
			getTeamBetMoney(userBetReturnInfo);
			app1.betData.userBetReturnInfoList.push(userBetReturnInfo);
		};
		function getMatchInfo(userBetReturnInfo){
			ajaxJson("ajaxGetMatchProfitInfo","profitId="+userBetReturnInfo.profitId,function(msg){
				var matchProfitInfo=toJson(msg);
				matchProfitInfo.matchTypeStr=getBodanTypeString(matchProfitInfo.matchType);
				matchProfitInfo.profitPercentStr=(userBetReturnInfo.money*matchProfitInfo.profitPercent/1000000).toFixed(2);
				userBetReturnInfo.matchProfitInfo=matchProfitInfo;
				ajaxJson("ajaxGetMatchInfo","phaseId="+matchProfitInfo.phaseId,function(msg){
					var matchInfo=toJson(msg);
					matchInfo.homeTeamPicUrl='<%=basePath%>'+matchInfo.homeTeamPicUrl;
					matchInfo.awayTeamPicUrl='<%=basePath%>'+matchInfo.awayTeamPicUrl;
					userBetReturnInfo.matchInfo=matchInfo;
				},function(errmsg){});
			},function(errmsg){});
		}
		function getTeamBetMoney(userBetReturnInfo){
			ajaxJson("ajaxGetUserTeamBetMoney","phaseId="+userBetReturnInfo.phaseId,function(msg){
				var money=toJson(msg);
				userBetReturnInfo.teamBetMoney=convertRmb(money);
			},function(errmsg){});
		}
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.betData.userBetReturnInfoList=[];
			if(g_type!=type){
				app1.betData.totalRecords=0;
				app1.betData.totalPage=0;
				app1.betData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetUserBetReturnInfoList?startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.betData,
					parseObjData);
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',
				data:{betData:{userBetReturnInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>