<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<!-- Basic -->
<meta charset="UTF-8" />
<title><spring:message code="page_title" /></title>
<script src="<%=commonJsPath%>/vue/vue-2.5.9.min.js"></script>
<script src="<%=commonJsPath%>/simpleTables.js?v=20171205"></script>
<script>
	if(!isMobileWeb()){
		window.location.href="game_result";
	}
</script>
</head>
<body>
	<!-- Page Header -->
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
	<!-- Page Header -->
	<style>
@media ( max-width : 640px) {
	html, body {
		height: 100%; overflow: auto;
	}
	.foot-mobile {
		height: auto; overflow: hidden; min-height: 100%;
		/* padding-top: 88px; */ margin: 0 13px; display: block;
	}
	.foot-mobile ul li {
		width: 100%; background: rgba(255, 255, 255, 1); padding: 10px 12px; margin-bottom: 7px; border-radius: 5px; box-shadow: 0px 2px 3px #000; position: relative;
	}
	.foot-mobile ul li .time {
		color: #212121; position: absolute; top: 50%; left: 7px; margin-top: -10px;
	}
	.foot-mobile ul li a>img {
		width: 16px; height: 16px; position: absolute; top: 50%; right: 10px; margin-top: -8px;
	}
	.foot-team {
		padding: 0 40px; display: -moz-box; display: -webkit-box; display: box; text-align: center; -moz-box-align: center; -webkit-box-align: center; -o-box-align: center; box-align: center;
	}
	.foot-team>div {
		width: 50px;
	}
	.foot-team img {
		height: 30px;
	}
	.foot-team>p {
		-moz-box-flex: 1; -webkit-box-flex: 1; box-flex: 1; color: #959595;
	}
	.team-txt {
		height: auto; overflow: hidden; padding: 0 40px; margin-top: 3px;
	}
	.team-txt p {
		width: 50%; display: block; float: left;
	}
	.foot-time {
		color: #212121; text-align: center; padding: 7px 0; border-radius: 5px; background: #e8e8e8; margin-bottom: 10px; font-size: 16px;
	}
	.foot-tab {
		width: 100%; height: auto; overflow: hidden;
	}
	.foot-tab a {
		width: 50%; display: block; float: left; text-align: center; background: rgba(0, 0, 0, 0.4); padding: 10px 0; color: #cc8442; font-size: 16px;
	}
	.foot-tab a em {
		font-size: 14px; margin-left: 7px; font-style: normal; color: #9d5f26;
	}
	.foot-tab a.hover {
		box-shadow: 1px 1px 3px #e0dede inset, -1px 1px 3px #e0dede inset;
	}
}

.aaabbb>div {
	width: 100%; margin: 0 auto;
}

.xxx>ul {
	display: none;
}

.xxx>p {
	display: none;
}

.xxx .display {
	display: block;
}

.aaabbb.hover {
	background: rgba(0, 0, 0, 0.4) !important; box-shadow: 1px 1px 3px #e0dede inset, -1px 1px 3px #e0dede inset;
}

.text-xxx {
	width: 50%; display: inline-block; float: left;
}
</style>
	<div id="app1" class="foot-mobile">
		<div class="foot-tab aaabbb nav">
			<a class="hover" data-type="1">今日赛事<em><font color="#bdbcbc"></font></em></a> <a data-type="2">昨日赛事<em><font color="#bdbcbc"></font></em></a>
		</div>
		<div class="xxx">
			<p id="p_today" class="foot-time display"></p>
			<p id="p_yesterday" class="foot-time"></p>
		</div>
		<div class="xxx xxx1">
			<div id="div_data1" class="display">
				<ul>
					<li v-for="(item,index) in todayData.matchInfoList"><a v-on:click="invest1(index)"> <span class="time">{{item.matchDateStr}}</span>
							<div class="foot-team">
								<div>
									<img :src="item.homeTeamPicUrl" />
								</div>
								<p>
									<font color="#202020">{{item.matchName}}</font>
								</p>
								<div>
									<img :src="item.awayTeamPicUrl" />
								</div>
							</div> <img src="<%=betDir%>/Public/Home/h+/images/enter.png" />
							<div class="team-txt">
								<p style="text-align: left;">{{item.homeTeam}}</p>
								<p style="text-align: right;">{{item.awayTeam}}</p>
							</div>
					</a></li>
					<div class="row">
						<div class="col-sm-12 page">
							<div style="background: #ffffff;">
								<a class="num" onclick="goFirstPage(app1.todayData)">首页</a><a class="num" onclick="goPrePage(app1.todayData)">上一页</a><span class="current">{{todayData.currentPage}}/{{todayData.totalPage}}</span>
								<a class="num" onclick="goNextPage(app1.todayData)">下一页</a> <a class="num" onclick="goLastPage(app1.todayData)">末页</a>
							</div>
						</div>
					</div>
				</ul>
				<!-- <div id="div_no_data1" class="container-fluid content">
					<div class="row">
						<div id="content" class="col-sm-12 full">
							<div class="row box-error">
								<div class="col-lg-12 col-md-12 col-xs-12">
									<div class="text-center">
										<h1 class="hidden-xs">暂时没有赛事！</h1>
										<br />
										<h2 class="visible-xs">暂时没有赛事！</h2>
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
				</div> -->
			</div>
			<div id="div_data2">
				<ul>
					<li v-for="(item,index) in yesterdayData.matchInfoList"><a v-on:click="invest2(index)"> <span class="time">{{item.matchDateStr}}</span>
							<div class="foot-team">
								<div>
									<img :src="item.homeTeamPicUrl" />
								</div>
								<p>
									<font color="#202020">{{item.matchName}}</font>
								</p>
								<div>
									<img :src="item.awayTeamPicUrl" />
								</div>
							</div> <img src="<%=betDir%>/Public/Home/h+/images/enter.png" />
							<div class="team-txt">
								<p style="text-align: left;">{{item.homeTeam}}</p>
								<p style="text-align: right;">{{item.awayTeam}}</p>
							</div>
					</a></li>
					<div class="row">
						<div class="col-sm-12 page">
							<div style="background: #ffffff;">
								<a class="num" onclick="goFirstPage(app1.yesterdayData)">首页</a><a class="num" onclick="goPrePage(app1.yesterdayData)">上一页</a><span class="current">{{yesterdayData.currentPage}}/{{yesterdayData.totalPage}}</span>
								<a class="num" onclick="goNextPage(app1.yesterdayData)">下一页</a> <a class="num" onclick="goLastPage(app1.yesterdayData)">末页</a>
							</div>
						</div>
					</div>
				</ul>
				<!-- <div id="div_no_data2" class="container-fluid content">
					<div class="row">
						<div id="content" class="col-sm-12 full">
							<div class="row box-error">
								<div class="col-lg-12 col-md-12 col-xs-12">
									<div class="text-center">
										<h1 class="hidden-xs">暂时没有赛事！</h1>
										<br />
										<h2 class="visible-xs">暂时没有赛事！</h2>
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
				</div> -->
			</div>
		</div>
	</div>
	<script>
		function invest(matchInfo){
			var width=document.documentElement.clientWidth;
			var sw,sh;
			var url="matchInfo/"+matchInfo.phaseId;
			if(width<500){
				sw='85%';
				sh='85%';
			}else{
				sw='450px';
				sh='500px';
			}
			layui.use('layer',function(){
				layer.open({type:2,scrollbar:false,area:[sw,sh],fixed:true, //不固定
				maxmin:true,content:url});
			});
		}
	</script>
	<script>
		orderColumn='matchDate';
		orderDir='desc';
		var app1;
		var g_type=1;
		var parseObjData=function(matchInfo){
			if(isNull(matchInfo.homeTeamPicUrl)){
				matchInfo.homeTeamPicUrl='<%=commonImgPath%>/ic_header.png';
			}
			else{
				matchInfo.homeTeamPicUrl='<%=basePath%>'+matchInfo.homeTeamPicUrl;
			}
			if(isNull(matchInfo.awayTeamPicUrl)){
				matchInfo.awayTeamPicUrl='<%=commonImgPath%>/ic_header.png';
			}
			else{
				matchInfo.awayTeamPicUrl='<%=basePath%>'+matchInfo.awayTeamPicUrl;
			}
			matchInfo.matchDateStr=getMyFormatDateTime(matchInfo.matchDate,'hh:mm');
			app1.todayData.matchInfoList.push(matchInfo);
		};
		var parseObjData2=function(matchInfo){
			var current=getCurrentTimeLong();
			if(isNull(matchInfo.homeTeamPicUrl)){
				matchInfo.homeTeamPicUrl='<%=commonImgPath%>/ic_header.png';
			}
			else{
				matchInfo.homeTeamPicUrl='<%=basePath%>'+matchInfo.homeTeamPicUrl;
			}
			if(isNull(matchInfo.awayTeamPicUrl)){
				matchInfo.awayTeamPicUrl='<%=commonImgPath%>/ic_header.png';
			}
			else{
				matchInfo.awayTeamPicUrl='<%=basePath%>'+matchInfo.awayTeamPicUrl;
			}
			matchInfo.matchDateStr=getMyFormatDateTime(matchInfo.matchDate,'hh:mm');
			app1.yesterdayData.matchInfoList.push(matchInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.todayData.matchInfoList=[];
			app1.yesterdayData.matchInfoList=[];
			if(g_type!=type){
				app1.todayData.totalRecords=0;
				app1.todayData.totalPage=1;
				app1.todayData.currentPage=1;
				app1.yesterdayData.totalRecords=0;
				app1.yesterdayData.totalPage=1;
				app1.yesterdayData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				var current=getCurrentTimeLong();
				var currentDate=new Date();
				if(type==1){
					var startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate());
					var endDateLong=startDateLong+24*60*60*1000-1;
					getDataList("ajaxGetMatchInfoList?isGameOver=true&startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.todayData,parseObjData);
				}else if(type==2){
					var startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate())-24*60*60*1000;
					var endDateLong=startDateLong+24*60*60*1000-1;
					getDataList("ajaxGetMatchInfoList?isGameOver=true&startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,
						app1.yesterdayData,parseObjData2);
				}
			}
		}
		$(function(){
			app1=new Vue({
				el:'#app1',
				data:{todayData:{matchInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data1',divNoData:'div_no_data1'},
					yesterdayData:{matchInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data2',divNoData:'div_no_data2'},isLoading:false},
				methods:{invest1:function(index){
					var matchInfo=this.todayData.matchInfoList[index];
					invest(matchInfo);
				},invest2:function(index){
					var matchInfo=this.yesterdayData.matchInfoList[index];
					invest(matchInfo);
				}}});
			initData(app1);
			$(".nav a").on("click",function(i){
				$(this).addClass("hover").siblings().removeClass("hover");
				var index=$(this).index();
				$('.xxx1 >div').eq(index).addClass('display').siblings().removeClass('display');
				var type=$(this).attr("data-type");
				if(g_type!=type){
					fnDataDoChoice(type);
				}
				if(type==1){
					$("#p_today").addClass('display');
					$("#p_yesterday").removeClass('display');
				}else if(type==2){
					$("#p_today").removeClass('display');
					$("#p_yesterday").addClass('display');
				}
			});
			getMoreData();
			var currentDate=new Date();
			var yesterdayDate=new Date();
			yesterdayDate.setTime(getCurrentTimeLong()-24*60*60*1000);
			$("#p_today").html((currentDate.getMonth()+1)+"月"+currentDate.getDate()+"日  "+getWeekDay(currentDate.getTime()));
			$("#p_yesterday").html((yesterdayDate.getMonth()+1)+"月"+yesterdayDate.getDate()+"日  "+getWeekDay(yesterdayDate.getTime()));
		});
	</script>
</body>
</html>