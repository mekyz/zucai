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
	window.location.href="index_m";
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

.aaabbb h3 h4 {
	color: #ffffff; text-align: center; cursor: pointer; margin-bottom: 5px;
}

.aaabbb>div {
	width: 100%; margin: 0 auto;
}

.xxx>div {
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

.nnnn1 {
	width: 250px;
}

.nnnn2 {
	width: 300px;
}

.nnnn3 {
	width: 250px;
}

table {
	width: 100% !important; display: table;
}

.nnnn1 {
	width: 20% !important; box-sizing: border-box;
}

.nnnn2 {
	width: 20% !important; box-sizing: border-box;
}

.nnnn3 {
	width: 20% !important; box-sizing: border-box;
}

@media only screen and (max-width: 991px) {
	.nnnn1, .nnnn2, .nnnn3 {
		width: 100% !important; overflow: hidden;
	}
	.moblie-ball {
		overflow: hidden;
	}
}

.main {
	padding: 90px 37px 98px 278px;
}

marquee a {
	line-height: 30px;
}
</style>
	<div id="app1">
		<div class="row" style="margin-bottom: 5px;">
			<template v-if="msgList.length>0"> <marquee direction=left behavior=scroll loop=-1 scrollamount=5 scrolldelay=10 align=top bgcolor=#ffffff height=30 width=100% onmouseover=this.stop()
				onmouseout=this.start()
			>
				<a v-for="(item,index) in msgList"><font color="#368ee0">{{item.title}}</font></a>
			</marquee></template>
		</div>
		<div class="row" style="margin-bottom: 30px;">
			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6">
				<a href="index"> <img alt="" src="<%=betDir%>/Public/Home/h+/images/foot1.png" style="height: 40px;">
				</a>
			</div>
			<%-- <div class="col-lg-4 col-md-4 col-sm-4 col-xs-6">
			<a href="index"> <img alt="" src="<%=betDir%>/Public/Home/h+/images/bask.png" style="height: 40px;">
			</a>
		</div> --%>
		</div>
		<div class="tab_aaabbb nav">
			<div class="aaabbb col-lg-6 col-md-6 col-sm-6 col-xs-6 hover btn" style="background: rgba(0, 0, 0, 0.4) !important;" data-type="1">
				<div>
					<span style="font-size: 24px; margin-left:;"> <font color="#efaa3b">今日赛事</font>
					</span> <span style="font-size: 16px; color: #bdbcbc; margin-left: 10px; position: relative; top: -2px;"> <font>共{{todayData.totalRecords}} 场</font>
					</span>
					<h3></h3>
					<h4></h4>
				</div>
			</div>
			<div class="aaabbb col-lg-6 col-md-6 col-sm-6 col-xs-6 btn" style="background: rgba(0, 0, 0, 0.4) !important;" data-type="2">
				<div>
					<span style="font-size: 24px; margin-left:;"> <font color="#efaa3b">明日赛事</font>
					</span> <span style="font-size: 16px; color: #bdbcbc; margin-left: 10px; position: relative; top: -2px;"> <font>共 {{tomorrowData.totalRecords}} 场</font>
					</span>
					<h3></h3>
					<h4></h4>
				</div>
			</div>
		</div>
		<div class="xxx">
			<div class="row display">
				<div id="div_data1" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="panel panel-default bk-bg-white">
						<div class="panel-body">
							<table class="table table-no-more table-bordered table-striped mb-none">
								<thead>
									<tr>
										<th class="text-center ball-name"></th>
										<th class="text-center" onclick="setOrderColumn('matchDate')">开赛时间</th>
										<th class="text-center" onclick="setOrderColumn('matchName')">赛事</th>
										<th class="text-center">球队</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(item,index) in todayData.matchInfoList">
										<td class="ball-name" style="color: red"><p>{{item.homeTeam}}</p>
											<p style="color: red">vs</p>
											<p>{{item.awayTeam}}</p></td>
										<td data-title="开赛时间" class="nnnn1"><template v-if="item.href"> <img src="<%=betDir%>/Public/Home/h+/images/img-l.jpg"></template>{{item.matchDateStr}}</td>
										<td data-title="赛事" class="nnnn2">{{item.matchName}}&nbsp;</td>
										<td data-title="球队" class="moblie-ball"><span class="text-xxx" style="text-align: right;">{{item.homeTeam}} <!-- <img style="height: 30px;" :src="item.homeTeamPicUrl" /> --> <em>-</em>
										</span> <span class="text-xxx" style="text-align: left;">&nbsp;&nbsp;<!-- <img style="height: 30px;" :src="item.awayTeamPicUrl" /> --> {{item.awayTeam}}
										</span></td>
										<td data-title="操作" class="nnnn3"><template v-if="item.href"> <a class="bk-margin-5 btn-sm btn-success" style="background: #39a5bf;" :href="item.href">投注<b
												style="font-size: 16px; color: #fff !important;"
											>+</b></a> </template> <template v-else> <a class="bk-margin-5 btn-sm btn-success" style="background: #b5b5b5; cursor: pointer;">投注<b style="font-size: 16px; color: #fff !important;">+</b></a></template></td>
									</tr>
								</tbody>
							</table>
							<div class="row">
								<div class="col-sm-12 page">
									<div>
										<a class="num" onclick="goFirstPage(app1.todayData)">首页</a><a class="num" onclick="goPrePage(app1.todayData)">上一页</a><span class="current">{{todayData.currentPage}}</span> <a class="num"
											onclick="goNextPage(app1.todayData)"
										>下一页</a> <a class="num" onclick="goLastPage(app1.todayData)">末页</a> <span class="rows">共 {{todayData.totalRecords}} 条记录 第{{todayData.currentPage}} / {{todayData.totalPage}} 页</span> <span
											class="jump"
										>转到第&nbsp; <input type="text" id="inp" :value="todayData.currentPage" onclick="goPage(app1.todayData,this)">&nbsp;页
											<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.todayData)">GO</button>
										</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
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
			<div class="row">
				<div id="div_data2" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="panel panel-default bk-bg-white">
						<div class="panel-body">
							<table class="table table-no-more table-bordered table-striped mb-none">
								<thead>
									<tr>
										<th class="text-center ball-name"></th>
										<th class="text-center" onclick="setOrderColumn('matchDate')">开赛时间</th>
										<th class="text-center" onclick="setOrderColumn('matchName')">赛事</th>
										<th class="text-center">球队</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(item,index) in tomorrowData.matchInfoList">
										<td class="ball-name"><p>{{item.homeTeam}}</p>
											<p style="color: red">vs</p>
											<p>{{item.awayTeam}}</p></td>
										<td data-title="开赛时间" class="nnnn1">{{item.matchDateStr}}</td>
										<td data-title="赛区" class="nnnn2">{{item.matchName}}</td>
										<td data-title="球队" class="moblie-ball"><span class="text-xxx" style="text-align: right;">{{item.homeTeam}} <!-- <img style="height: 30px;" :src="item.homeTeamPicUrl" /> --> <em>-</em>
										</span> <span class="text-xxx" style="text-align: left;">&nbsp;&nbsp;<!-- <img style="height: 30px;" :src="item.awayTeamPicUrl" /> --> {{item.awayTeam}}
										</span></td>
										<td data-title="操作" class="nnnn3"><template v-if="item.href"> <a class="bk-margin-5 btn-sm btn-success" style="background: #39a5bf;" :href="item.href">投注<b
												style="font-size: 16px; color: #fff !important;"
											>+</b></a> </template> <template v-else> <a class="bk-margin-5 btn-sm btn-success" style="background: #b5b5b5; cursor: pointer;">投注<b style="font-size: 16px; color: #fff !important;">+</b></a></template></td>
									</tr>
								</tbody>
							</table>
							<div class="row">
								<div class="col-sm-12 page">
									<div>
										<a class="num" onclick="goFirstPage(app1.tomorrowData)">首页</a><a class="num" onclick="goPrePage(app1.tomorrowData)">上一页</a><span class="current">{{tomorrowData.currentPage}}</span> <a
											class="num" onclick="goNextPage(app1.tomorrowData)"
										>下一页</a> <a class="num" onclick="goLastPage(app1.tomorrowData)">末页</a> <span class="rows">共 {{tomorrowData.totalRecords}} 条记录 第{{tomorrowData.currentPage}} / {{tomorrowData.totalPage}}
											页</span> <span class="jump">转到第&nbsp; <input type="text" id="inp" :value="tomorrowData.currentPage" onclick="goPage(app1.tomorrowData,this)">&nbsp;页
											<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.tomorrowData)">GO</button>
										</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
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
			matchInfo.matchDateStr=getFormatDateTime(matchInfo.matchDate);
			if(matchInfo.timeEndsale>current&&matchInfo.halfResultStatus!=LR_WEB.STATUS.ENABLED&&matchInfo.finalResultStatus!=LR_WEB.STATUS.ENABLED){
				matchInfo.href='participation_game/'+matchInfo.phaseId;
			}
			matchInfo.recommend=(matchInfo.sortIndex>100);
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
			matchInfo.matchDateStr=getFormatDateTime(matchInfo.matchDate);
			if(matchInfo.timeEndsale>current&&matchInfo.halfResultStatus!=LR_WEB.STATUS.ENABLED&&matchInfo.finalResultStatus!=LR_WEB.STATUS.ENABLED){
				matchInfo.href='participation_game/'+matchInfo.phaseId;
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
				//app1.todayData.totalRecords=0;
				//app1.todayData.totalPage=0;
				app1.todayData.currentPage=1;
				//app1.tomorrowData.totalRecords=0;
				//app1.tomorrowData.totalPage=0;
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
			$(".nav div").on("click",function(i){
				$(this).addClass("hover").siblings().removeClass("hover");
				var index=$(this).index();
				$('.xxx >div').eq(index).addClass('display').siblings().removeClass('display');
				var type=$(this).attr("data-type");
				if(g_type!=type){
					fnDataDoChoice(type);
				}
			});
			fnDataDoChoice(2);
			fnDataDoChoice(1);
			//getMoreData();
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