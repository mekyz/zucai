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
		window.location.href="index";
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
		width: 100%; height: auto; overflow: hidden;margin-top:30px;
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

.xxx1>div {
	display: none;
}

.xxx1>p {
	display: none;
}

.xxx1 .display {
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
		<template v-if="msgList.length>0"> <marquee direction=left behavior=scroll loop=3 scrollamount=5 scrolldelay=10 align=top bgcolor=#ffffff height=30 onmouseover=this.stop()
			onmouseout=this.start() style="top:110px;"
		>
			<a v-for="(item,index) in msgList"><font color="#368ee0">{{item.title}}</font></a>
		</marquee> </template>
		<%-- <div style="margin-bottom: 15px; overflow: hidden;">
			<a href="index" style="display: block; float: left; width: 50%;"> <img alt="" src="<%=betDir%>/Public/Home/h+/images/foot1.png" style="max-height: 40px; max-width: 90%">
			</a> <a href="index" style="display: block; float: right; width: 50%; text-align: right;"> <img alt="" src="<%=betDir%>/Public/Home/h+/images/bask.png" style="max-height: 40px; max-width: 90%">
			</a>
		</div> --%>
		<div class="foot-tab aaabbb nav">
			<a class="hover" data-type="1">今日赛事<em><font color="#bdbcbc">共 {{todayData.totalRecords}} 场</font></em></a> <a data-type="2">明日赛事<em><font color="#bdbcbc">共
						{{tomorrowData.totalRecords}} 场</font></em></a>
		</div>
		<div class="xxx">
			<p id="p_today" class="foot-time display"></p>
			<p id="p_tomorrow" class="foot-time"></p>
		</div>
		<div class="xxx xxx1">
			<div class="display">
				<ul id="div_data1">
					<template v-for="(item,index) in todayData.matchInfoList"> <template v-if="item.href">
					<li><a :href="item.href"> <span class="time">{{item.matchDateStr}}</span>
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
					</a> <template v-if="item.href"> <img src="<%=betDir%>/Public/Home/h+/images/img-l.jpg"></template></li>
					</template> <template v-else>
					<li style="background: #8C8C8C;"><a :href="item.href"> <span class="time">{{item.matchDateStr}}</span>
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
					</a> <template v-if="item.href"> <img src="<%=betDir%>/Public/Home/h+/images/img-l.jpg"></template></li>
					</template></template>
					<div class="row">
						<div class="col-sm-12 page">
							<div style="background: #ffffff;">
								<a class="num" onclick="goFirstPage(app1.todayData)">首页</a><a class="num" onclick="goPrePage(app1.todayData)">上一页</a><span class="current">{{todayData.currentPage}}/{{todayData.totalPage}}</span>
								<a class="num" onclick="goNextPage(app1.todayData)">下一页</a> <a class="num" onclick="goLastPage(app1.todayData)">末页</a>
							</div>
						</div>
					</div>
				</ul>
				<div id="div_no_data1" class="container-fluid content">
					<div class="row">
						<div id="content" class="col-sm-12 full">
							<div class="row box-error">
								<div class="col-lg-12 col-md-12 col-xs-12">
									<div class="text-center">
										<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有赛事！</h1>
										<br />
										<h2 class="visible-xs" style="border: 3px solid red;">暂时没有赛事！</h2>
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
			<div>
				<ul id="div_data2">
					<li v-for="(item,index) in tomorrowData.matchInfoList"><a class="J_alinkaa" :href="item.href"> <span class="time">{{item.matchDateStr}}</span>
							<div class="foot-team">
								<div>
									<img :src="item.homeTeamPicUrl" />
								</div>
								<p>{{item.matchName}}</p>
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
								<a class="num" onclick="goFirstPage(app1.tomorrowData)">首页</a><a class="num" onclick="goPrePage(app1.tomorrowData)">上一页</a><span class="current">{{tomorrowData.currentPage}}/{{tomorrowData.totalPage}}</span>
								<a class="num" onclick="goNextPage(app1.tomorrowData)">下一页</a> <a class="num" onclick="goLastPage(app1.tomorrowData)">末页</a>
							</div>
						</div>
					</div>
				</ul>
				<div id="div_no_data2" class="container-fluid content">
					<div class="row">
						<div id="content" class="col-sm-12 full">
							<div class="row box-error">
								<div class="col-lg-12 col-md-12 col-xs-12">
									<div class="text-center">
										<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有赛事！</h1>
										<br />
										<h2 class="visible-xs" style="border: 3px solid red;">暂时没有赛事！</h2>
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
		</div>
	</div>
	<script>
		orderColumn='matchDate';
		orderDir='asc';
		var app1;
		var g_type=1;
		var parseObjData=function(matchInfo){
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
			if(matchInfo.timeEndsale>current&&matchInfo.halfResultStatus!=LR_WEB.STATUS.ENABLED&&matchInfo.finalResultStatus!=LR_WEB.STATUS.ENABLED){
				matchInfo.href='participation_game_m/'+matchInfo.phaseId;
			}
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
			if(matchInfo.timeEndsale>current&&matchInfo.halfResultStatus!=LR_WEB.STATUS.ENABLED&&matchInfo.finalResultStatus!=LR_WEB.STATUS.ENABLED){
				matchInfo.href='participation_game_m/'+matchInfo.phaseId;
			}
			app1.tomorrowData.matchInfoList.push(matchInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.todayData.matchInfoList=[];
			app1.tomorrowData.matchInfoList=[];
			if(g_type!=type){
				app1.todayData.currentPage=1;
				app1.tomorrowData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				var current=getCurrentTimeLong();
				var currentDate=new Date();
				if(type==1){
					var startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate());
					var endDateLong=startDateLong+24*60*60*1000-1;
					getDataList("ajaxGetMatchInfoList?startDateLong="+current+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.todayData,parseObjData);
				}else if(type==2){
					var startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate())+24*60*60*1000;
					var endDateLong=startDateLong+24*60*60*1000-1;
					getDataList("ajaxGetMatchInfoList?startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.tomorrowData,
						parseObjData2);
				}
			}
		}
		$(function(){
			app1=new Vue({
				el:'#app1',
				data:{todayData:{matchInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data1',divNoData:'div_no_data1'},
					tomorrowData:{matchInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data2',divNoData:'div_no_data2'},isLoading:false,msgList:[]}});
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
					$("#p_tomorrow").removeClass('display');
				}else if(type==2){
					$("#p_today").removeClass('display');
					$("#p_tomorrow").addClass('display');
				}
			});
			fnDataDoChoice(2);
			fnDataDoChoice(1);
			var currentDate=new Date();
			var tomorrowDate=new Date();
			tomorrowDate.setTime(getCurrentTimeLong()+24*60*60*1000);
			$("#p_today").html((currentDate.getMonth()+1)+"月"+currentDate.getDate()+"日  "+getWeekDay(currentDate.getTime()));
			$("#p_tomorrow").html((tomorrowDate.getMonth()+1)+"月"+tomorrowDate.getDate()+"日  "+getWeekDay(tomorrowDate.getTime()));
			getMsgs();
		});
		function getMsgs(){
			ajaxDataJson("ajaxGetMsgInfoList",{},function(data){
				var list=data.data;
				for(var i=0;i<list.length;i++){
					var msgInfo=list[i];
					app1.msgList.push(msgInfo);
				}
			},function(errmsg){});
		}
	</script>
</body>
</html>