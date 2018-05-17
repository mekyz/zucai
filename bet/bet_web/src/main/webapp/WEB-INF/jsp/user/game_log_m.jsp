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
		window.location.href="game_log";
	}
</script>
</head>
<body>
	 
	<div class="page-header" style="background: none;">
	 
	</div>
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
	<!-- Page Header -->
	<style>
.foot-mobile ul li a>img {
	width: 16px; height: 16px; position: absolute; top: 50%; right: 10px; margin-top: -8px;
}

.foot-mobile {
	height: auto; overflow: hidden; min-height: 100%;
	/* padding-top: 88px; */ margin: 0 13px; display: block;
}

.foot-mobile ul li {
	width: 100%; background: rgba(255, 255, 255, 1); padding: 10px 12px; margin-bottom: 7px; border-radius: 5px; box-shadow: 0px 2px 3px #000; position: relative;
}

.foot-mobile ul li a {
	width: 100%; height: auto; overflow: hidden; display: block; position: relative;
}

.foot-table {
	width: 100%; background: #FFFFFF; display: none;
}

.foot-mobile ul li a .foot-table {
	display: block;
}

.foot-mobile ul li a img {
	transform: rotate(90deg);
}

.foot-mobile ul li a img.down {
	transform: rotate(-90deg);
}

.foot-table p {
	line-height: 26px; height: auto; overflow: hidden;
}

.foot-table p em {
	width: 100px; display: inline-block; text-align: right; font-style: normal;
}

.xxx {
	background: rgba(255, 255, 255, 1); margin-bottom: 10px; border-radius: 5px; padding: 10px 12px;
}

.xxx p b {
	color: #fff !important;
}
/* .foot-table p span:last-child {
			text-align: left;
		} */
.foot-table p span {
	width: 50%; display: inline-block; float: left; text-align: center; position: relative;
}

.foot-table p span i {
	height: 14px; line-height: 14px; position: absolute; right: 0; top: 50%; display: block; margin-top: -7px; text-align: left;
}
</style>
	<div id="app1" class="foot-mobile">
		<!-- <div class="xxx" style="background: #0EA080;">
			<p class="foot-time display">
				<b>投注总计：{{betData.totalMoney}}</b>
			</p>
		</div>
		<div class="xxx" style="background: #0EA080;">
			<p class="foot-time display">
				<b>盈亏：{{betData.profitMoney}}</b>
			</p>
		</div> -->
		<div class="xxx" style="background: #2C456E">
			<p class="foot-time display">
				<b>投注结果</b>
			</p>
		</div>
		<ul>
			<li v-for="(item,index) in betData.userBetInfoList"><a v-on:click="bindClick(index)">
					<div class="foot-table">
						<p>
							<em>交易时间：</em>{{item.addDateLongStr}}
						</p>
						<p>
							<em>开赛时间：</em>{{item.matchInfo.matchDateStr}}
						</p>
					</div> <img src="<%=betDir%>/Public/Home/h+/images/enter.png">
			</a>
				<div :id="index" class="foot-table">
					<p>
						<em>赛区：</em>{{item.matchInfo.matchName}}
					</p>
					<p>
						<em>赛事：</em> <br> <span style="width: 50%;"> {{item.matchInfo.homeTeam}}<i>vs</i>
						</span> <span style="width: 50%;"> {{item.matchInfo.awayTeam}} </span> <br> <span style="width: 50%;"> <img style="height: 30px;" :src="item.matchInfo.homeTeamPicUrl"><i>-</i>
						</span> <span style="width: 50%;"> <img style="height: 30px;" :src="item.matchInfo.awayTeamPicUrl">
						</span>
					</p>
					<p>
						<em>波胆项目：</em>{{item.matchProfitInfo.matchTypeStr}}
					</p>
					<p>
						<em>比分/进球数：</em>{{item.matchProfitInfo.score1}}:{{item.matchProfitInfo.score2}}
					</p>
					<p>
						<em>投注金额：</em>{{item.moneyStr}}
					</p>
					<p>
						<em>投注币种：</em>{{item.moneyUnitStr}}
					</p>
					<p>
						<em>收益率%：</em>{{item.matchProfitInfo.profitPercent/100}}
					</p>
					<p>
						<em>波胆结果：</em>{{item.result}}
					</p>
					<p>
						<em>盈/亏：</em><font color="green"> {{item.userMoneyStr}}</font>
					</p>
				</div></li>
		</ul>
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
					isLoading:false},methods:{bindClick:function(index){
					if($("#"+index).hasClass('on')){
						$("#"+index).removeClass('on');
						$("#"+index).css('display','none');
					}else{
						$("#"+index).addClass('on');
						$("#"+index).css('display','inherit');
					}
				}}});
			initData(app1);
			//getUserBetTotalMoney();
			//getUserBetWinMoney();
			getMoreData();
		});
		function a(){
			$('.foot-mobile ul li a').click(function(){
				if($(this).hasClass('on')){
					$(this).next().slideUp();
					$(this).find('img').removeClass('down');
					$(this).removeClass('on');
				}else{
					$('.foot-mobile ul li a').next().slideUp();
					$('.foot-mobile ul li a').find('img').removeClass('down');
					$('.foot-mobile ul li a').removeClass('on');
					$(this).addClass('on');
					$(this).next().slideDown();
					$(this).find('img').addClass('down');
				}
			});
		}
	</script>
</body>
</html>