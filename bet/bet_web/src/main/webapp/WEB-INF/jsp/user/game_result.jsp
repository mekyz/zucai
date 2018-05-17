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
	window.location.href="game_result_m";
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
.text-xxx {
	width: 50%; display: inline-block; float: left;
}

@media only screen and (max-width: 991px) {
	.moblie-ball {
		overflow: hidden;
	}
}
</style>
	<div id="app1" class="row">
		<div id="div_data" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>赛事结果
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<table class="table table-no-more table-bordered table-striped mb-none">
						<%-- id="datatable-default" --%>
						<thead>
							<tr>
								<th class="text-center" onclick="setOrderColumn('matchDate')">开赛时间</th>
								<th class="text-center" onclick="setOrderColumn('matchName')">赛事</th>
								<th class="text-center" onclick="setOrderColumn('homeTeam')">球队</th>
								<th class="text-center" onclick="setOrderColumn('finalScore1')">全场比分</th>
								<th class="text-center" onclick="setOrderColumn('halfScore1')">半场比分</th>
								<th class="text-center" onclick="setOrderColumn('handicap')">总进球数</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in matchData.matchInfoList">
								<td data-title="开赛时间">{{item.matchDateStr}}</td>
								<td data-title="赛事">{{item.matchName}}</td>
								<td data-title="球队" class="moblie-ball"> {{item.homeTeam}} <em>-</em>
								</span> &nbsp;&nbsp;{{item.awayTeam}} </span></td>
								<td data-title="全场波胆">{{item.finalScore1}}:{{item.finalScore2}}&nbsp;</td>
								<td data-title="半场波胆">{{item.halfScore1}}:{{item.halfScore2}}&nbsp;</td>
								<td data-title="总进球数">{{item.finalScore1+item.finalScore2}}&nbsp;</td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.matchData)">首页</a><a class="num" onclick="goPrePage(app1.matchData)">上一页</a><span class="current">{{matchData.currentPage}}</span> <a class="num"
									onclick="goNextPage(app1.matchData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.matchData)">末页</a> <span class="rows">共 {{matchData.totalRecords}} 条记录 第{{matchData.currentPage}} / {{matchData.totalPage}} 页</span> <span
									class="jump"
								>转到第&nbsp; <input type="text" id="inp" :value="matchData.currentPage" onclick="goPage(app1.matchData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.matchData)">GO</button></span>
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
								<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有赛事结果记录！</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">暂时没有赛事结果记录！</h2>
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
			matchInfo.matchDateStr=getMyFormatDateTime(matchInfo.matchDate,'MM月dd日 hh:mm');
			app1.matchData.matchInfoList.push(matchInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.matchData.matchInfoList=[];
			if(g_type!=type){
				app1.matchData.totalRecords=0;
				app1.matchData.totalPage=0;
				app1.matchData.currentPage=0;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				var current=getCurrentTimeLong();
				if(type==1){
					getDataList("ajaxGetMatchInfoList?isGameOver=true&endDateLong="+current+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.matchData,parseObjData);
				}
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',data:{matchData:{matchInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>