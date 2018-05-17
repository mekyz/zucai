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
	if(isMobileWeb()){
		window.location.href="bonus_share_m";
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
		<div id="div_data" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>分享奖金明细列表
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
												<input type="text" id='betUserId' class="form-control" value="" placeholder="请输入会员编号">
											</div>
										</div>
										<button type="button" class="btn  btn-success" style="margin-left: 15px !important;" onclick="query()">
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
							}
							document.getElementById('end').onclick=function(){
								end.elem=this;
								laydate(end);
							}
						});
					</script>
					<table class="table table-no-more table-bordered table-striped mb-none">
						<thead>
							<tr>
								<th class="text-center" onclick="setOrderColumn('addDateLong')">日期</th>
								<th class="text-center" onclick="setOrderColumn('phaseId')">赛事</th>
								<th class="text-center">球队</th>
								<th class="text-center">比分 / 进球数</th>
								<th class="text-center" onclick="setOrderColumn('betUserId')">相关会员</th>
								<th class="text-center" onclick="setOrderColumn('profitUserMoney')">结算金额</th>
								<th class="text-center" onclick="setOrderColumn('profitType')">奖金类型</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in teamProfitData.teamProfitInfoList">
								<td data-title="日期" class="text-center">{{item.addDateLongStr}}</td>
								<td data-title="赛事">{{item.matchInfo.matchName}}&nbsp;</td>
								<td data-title="球队" class="moblie-ball"><span class="text-xxx" style="text-align: right;">{{item.matchInfo.homeTeam}} <em>-</em>
								</span> <span class="text-xxx" style="text-align: left;">&nbsp;&nbsp;{{item.matchInfo.awayTeam}} </span></td>
								<td data-title="比分 / 进球数">{{item.matchInfo.finalScore1}}:{{item.matchInfo.finalScore2}}&nbsp;</td>
								<td data-title="相关会员" class="text-center">{{item.betUserId}}</td>
								<td data-title="变更金额" class="text-center"><span style="color: red; display: inline;">{{item.profitUserMoney}}</span></td>
								<td data-title="奖金类型" class="text-center"><span style='color: red;'>{{item.profitTypeStr}}</span></td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.teamProfitData)">首页</a><a class="num" onclick="goPrePage(app1.teamProfitData)">上一页</a><span class="current">{{teamProfitData.currentPage}}</span> <a
									class="num" onclick="goNextPage(app1.teamProfitData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.teamProfitData)">末页</a> <span class="rows">共 {{teamProfitData.totalRecords}} 条记录 第{{teamProfitData.currentPage}} /
									{{teamProfitData.totalPage}} 页</span> <span class="jump">转到第&nbsp; <input type="text" id="inp" :value="teamProfitData.currentPage" onclick="goPage(app1.teamProfitData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.teamProfitData)">GO</button>
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
		function query(){
			var start=$("#start").val();
			var end=$("#end").val();
			betUserId=$("#betUserId").val();
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(teamProfitInfo){
			teamProfitInfo.addDateLongStr=getFormatDateTime(teamProfitInfo.addDateLong);
			teamProfitInfo.winMoney=(teamProfitInfo.winMoney/10000).toFixed(4);
			teamProfitInfo.profitMoney=(teamProfitInfo.profitMoney/10000).toFixed(4);
			teamProfitInfo.profitUserMoney=(teamProfitInfo.profitUserMoney/10000).toFixed(4);
			teamProfitInfo.fee=(teamProfitInfo.fee/10000).toFixed(4);
			teamProfitInfo.profitTypeStr=getTeamProfitTypeString(teamProfitInfo.profitType);
			teamProfitInfo.profitStatusStr='';
			if(teamProfitInfo.profitStatus==LR_WEB.STATUS.ENABLED){
				teamProfitInfo.statusStr='已发放';
			}else{
				teamProfitInfo.statusStr='未发放';
			}
			teamProfitInfo.matchInfo={};
			teamProfitInfo.userBetInfo={};
			teamProfitInfo.matchProfitInfo={};
			getMatchInfo(teamProfitInfo);
			getMatchProfitInfo(teamProfitInfo);
			app1.teamProfitData.teamProfitInfoList.push(teamProfitInfo);
		};
		function getMatchInfo(teamProfitInfo){
			ajaxJson("ajaxGetMatchInfo","phaseId="+teamProfitInfo.phaseId,function(msg){
				var matchInfo=toJson(msg);
				matchInfo.homeTeamPicUrl='<%=basePath%>'+matchInfo.homeTeamPicUrl;
				matchInfo.awayTeamPicUrl='<%=basePath%>'+matchInfo.awayTeamPicUrl;
				matchInfo.matchDateStr=getFormatDateTime(matchInfo.matchDate);
				teamProfitInfo.matchInfo=matchInfo;
			},function(errmsg){});
		}
		function getMatchProfitInfo(teamProfitInfo){
			ajaxJson("ajaxGetUserBetInfo","betId="+teamProfitInfo.betId,function(msg){
				var userBetInfo=toJson(msg);
				userBetInfo.money=(userBetInfo.money/100).toFixed(2);
				teamProfitInfo.userBetInfo=userBetInfo;
				ajaxJson("ajaxGetMatchProfitInfo","profitId="+userBetInfo.profitId,function(msg){
					var matchProfitInfo=toJson(msg);
					matchProfitInfo.matchTypeStr=getBodanTypeString(matchProfitInfo.matchType);
					teamProfitInfo.matchProfitInfo=matchProfitInfo;
				},function(errmsg){});
			},function(errmsg){});
		}
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.teamProfitData.teamProfitInfoList=[];
			if(g_type!=type){
				app1.teamProfitData.totalRecords=0;
				app1.teamProfitData.totalPage=0;
				app1.teamProfitData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetTeamProfitInfoList?phaseId=${phaseId}&betUserId="+betUserId+"&profitType=1&startDateLong="+startDateLong+"&endDateLong="+endDateLong
					+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.teamProfitData,parseObjData);
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',
				data:{teamProfitData:{teamProfitInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>