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
	if(isMobileWeb()){
		window.location.href="game_log_m";
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
.center {
	text-align: center;
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

.aaaa {
	padding: 10px 15px;; display: inline-block; color: #ffffff; border-radius: 5px; margin: 0px 10px 10px 0px;
}

.text-xxx {
	width: 50%; display: inline-block; float: left;
}
</style>
	<div id="app1" class="row">
		<!-- <span class="aaaa btn-success" target-form="commentForm">投注总计：{{betData.totalMoney}}</span> <span class="aaaa btn-success" target-form="commentForm">盈亏：{{betData.profitMoney}}</span> -->
		<div id="div_data" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>投注结果
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<table class="table table-no-more table-bordered table-striped mb-none">
						<thead>
							<tr>
								<th class="text-center ball-name"></th>
								<th class="text-center" onclick="setOrderColumn('addDateLong')">交易时间</th>
								<th class="text-center">开赛时间</th>
								<th class="text-center">赛事</th>
								<th class="text-center" onclick="setOrderColumn('matchName')">球队</th>
								<th class="text-center" onclick="setOrderColumn('matchType')">波胆项目</th>
								<th class="text-center" onclick="setOrderColumn('score1')">比分/进球数</th>
								<th class="text-center" onclick="setOrderColumn('money')">投注金额</th>
								<th class="text-center" onclick="setOrderColumn('moneyUnit')">投注币种</th>
								<th class="text-center">收益率%</th>
								<th class="text-center" onclick="setOrderColumn('winMoney')">波胆结果</th>
								<th class="text-center">盈/亏</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in betData.userBetInfoList">
								<td class="ball-name"><p>{{item.matchInfo.homeTeam}}</p>
									<p style="color: red">vs</p>
									<p>&nbsp;{{item.matchInfo.awayTeam}}</p></td>
								<td data-title="交易时间">&nbsp;{{item.addDateLongStr}}</td>
								<td data-title="开赛时间">&nbsp;{{item.matchInfo.matchDateStr}}</td>
								<td data-title="赛事">&nbsp;{{item.matchInfo.matchName}}</td>
								<td data-title="球队" class="moblie-ball"><span class="text-xxx" style="text-align: right;">{{item.matchInfo.homeTeam}} <!-- <img style="height: 30px;"
										:src="item.matchInfo.homeTeamPicUrl"
									/> --> <em>-</em>
								</span> <span class="text-xxx" style="text-align: left;">&nbsp;&nbsp;<!-- <img style="height: 30px;" :src="item.matchInfo.awayTeamPicUrl" /> --> {{item.matchInfo.awayTeam}}
								</span></td>
								<td data-title="波胆项目">&nbsp;{{item.matchProfitInfo.matchTypeStr}}</td>
								<td data-title="比分/进球数">&nbsp;{{item.matchProfitInfo.score1}}:{{item.matchProfitInfo.score2}}</td>
								<td data-title="投注金额">&nbsp;{{item.moneyStr}}</td>
								<td data-title="投注币种">&nbsp;{{item.moneyUnitStr}}</td>
								<td data-title="收益率%">&nbsp;{{item.matchProfitInfo.profitPercent/100}}</td>
								<td data-title="波胆结果">&nbsp;{{item.result}}</td>
								<td data-title="盈/亏">&nbsp; {{item.userMoneyStr}}</td>
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
			var start=$("#start").val();
			var end=$("#end").val();
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(userBetInfo){
			userBetInfo.addDateLongStr=getFormatDateTime(userBetInfo.addDateLong);
			userBetInfo.moneyStr=convertRmb(userBetInfo.money);
			if(!isNull(userBetInfo.userMoney)){
				if(userBetInfo.userMoney-userBetInfo.money>=0){
					userBetInfo.userMoneyStr="+"+convertRmb(userBetInfo.userMoney);
				}else{
					userBetInfo.userMoneyStr=convertRmb(userBetInfo.userMoney-userBetInfo.money);
				}
			}else{
				if(userBetInfo.userMoney==0){
					userBetInfo.userMoneyStr='0';
				}else{
					userBetInfo.userMoneyStr='正在计算';
				}
			}
			userBetInfo.moneyUnitStr=getMoneyUnitString(userBetInfo.moneyUnit);
			userBetInfo.statusStr='';
			if(userBetInfo.status==LR_WEB.APPLY_STATUS.APPLY){
				userBetInfo.statusStr='已申请';
			}else if(userBetInfo.status==LR_WEB.APPLY_STATUS.VERIFY_SUCCESS){
				userBetInfo.statusStr='审核成功';
			}else if(userBetInfo.status==LR_WEB.APPLY_STATUS.VERIFY_FAIL){
				userBetInfo.statusStr='审核失败';
			}else if(userBetInfo.status==LR_WEB.APPLY_STATUS.PAYED){
				userBetInfo.statusStr='已付款';
			}else if(userBetInfo.status==LR_WEB.APPLY_STATUS.PROCESSED){
				userBetInfo.statusStr='已完成';
			}
			userBetInfo.result='';
			userBetInfo.matchInfo={};
			userBetInfo.matchProfitInfo={};
			getMatchInfo(userBetInfo);
			app1.betData.userBetInfoList.push(userBetInfo);
		};
		function getMatchInfo(userBetInfo){
			ajaxJson("ajaxGetMatchProfitInfo","profitId="+userBetInfo.profitId,function(msg){
				var matchProfitInfo=toJson(msg);
				matchProfitInfo.matchTypeStr=getBodanTypeString(matchProfitInfo.matchType);
				userBetInfo.matchProfitInfo=matchProfitInfo;
				ajaxJson("ajaxGetMatchInfo","phaseId="+matchProfitInfo.phaseId,function(msg){
					var matchInfo=toJson(msg);
					matchInfo.homeTeamPicUrl='<%=basePath%>'+matchInfo.homeTeamPicUrl;
					matchInfo.awayTeamPicUrl='<%=basePath%>'+matchInfo.awayTeamPicUrl;
					if(matchProfitInfo.matchType==LR_WEB.BODAN_TYPE.FULL){
						if(!isNull(matchInfo.finalScore1)||matchInfo.finalScore1==0){
							userBetInfo.result=matchInfo.finalScore1+':'+matchInfo.finalScore2;
						}else{
							userBetInfo.result='未开奖';
							userBetInfo.userMoneyStr='';
						}
					}else if(matchProfitInfo.matchType==LR_WEB.BODAN_TYPE.HALF){
						if(!isNull(matchInfo.halfScore1)||matchInfo.halfScore1==0){
							userBetInfo.result=matchInfo.halfScore1+':'+matchInfo.halfScore2;
						}else{
							userBetInfo.result='未开奖';
							userBetInfo.userMoneyStr='';
						}
					}else if(matchProfitInfo.matchType==LR_WEB.BODAN_TYPE.SCORE){
						if(!isNull(matchInfo.handicap)||matchInfo.handicap==0){
							userBetInfo.result=matchInfo.handicap;
						}else{
							userBetInfo.result='未开奖';
							userBetInfo.userMoneyStr='';
						}
					}else{
						userBetInfo.result='未开奖';
						userBetInfo.userMoneyStr='';
					}
					matchInfo.matchDateStr=getFormatDateTime(matchInfo.matchDate);
					userBetInfo.matchInfo=matchInfo;
				},function(errmsg){});
			},function(errmsg){});
		}
		function getUserBetTotalMoney(){
			ajaxJson("ajaxGetUserBetTotalMoney","",function(res){
				var totalMoney=res;
				app1.betData.totalMoney=convertRmb(totalMoney);
			},function(errmsg){});
		}
		function getUserBetWinMoney(){
			ajaxJson("ajaxGetUserBetWinMoney","",function(res){
				var profitMoney=res;
				app1.betData.profitMoney=convertRmb(profitMoney);
			},function(errmsg){});
		}
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.betData.userBetInfoList=[];
			if(g_type!=type){
				app1.betData.totalRecords=0;
				app1.betData.totalPage=0;
				app1.betData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetUserBetInfoList?startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.betData,parseObjData);
			}
		}
		$(function(){
			app1=new Vue({
				el:'#app1',
				data:{betData:{userBetInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data',totalMoney:0,profitMoney:0},
					isLoading:false}});
			initData(app1);
			//getUserBetTotalMoney();
			//getUserBetWinMoney();
			getMoreData();
		});
	</script>
</body>
</html>