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
		window.location.href="participation_game/${matchInfo.phaseId}";
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
body {
	height: auto !important; background-size: cover 1000px; background: url(<%=betDir%>/Public/Home/h+/images/bj.jpg) no-repeat #001e00; background-attachment: fixed;
}

.main {
	height: 100% !important; background-size: cover 1000px; background: url(<%=betDir%>/Public/Home/h+/images/bj.jpg) no-repeat #001e00; background-attachment: fixed;
}

@media ( max-width : 640px) {
	.foot-mobile {
		height: auto; margin: 0 13px; display: block; border-radius: 5px;
	}
	.foot-mobile ul li {
		width: 100%; background: rgba(255, 255, 255, 1); padding: 10px 12px; margin-bottom: 5px; border-radius: 5px; box-shadow: 0px 2px 3px #000; position: relative;
	}
	.foot-mobile ul li:nth-child(2n) {
		border: 1px solid darkred;
	}
	.foot-mobile ul li:nth-child(2n+1) {
		border: 1px solid darkgreen;
	}
	.foot-mobile ul li p {
		margin-bottom: 7px;
	}
	.foot-mobile ul li img {
		width: 16px; height: 16px; position: absolute; top: 50%; right: 10px; margin-top: -8px;
	}
	.foot-mobile-top {
		margin-bottom: 10px; background: #FFFFFF; border-radius: 8px; padding: 15px 0;
	}
	.foot-mobile-top-img {
		display: -moz-box; display: -webkit-box; display: box; text-align: center; -moz-box-align: center; -webkit-box-align: center; -o-box-align: center; box-align: center;
	}
	.foot-mobile-top-img>div {
		-moz-box-flex: 1; -webkit-box-flex: 1; box-flex: 1; color: #212121;
	}
	.foot-mobile-top-img img {
		display: block; margin: 0 auto; height: 50px; margin-bottom: 5px;
	}
	.foot-mobile-top-img div span {
		display: block; margin-top: 5px;
	}
	.foot-txt2 {
		height: 50px; line-height: 50px; border-radius: 5px 5px 0 0; background: #FFFFFF; padding: 0 12px; font-size: 18px;
	}
	.foot-txt2 span {
		float: right; font-size: 14px;
	}
	.foot-mobile table {
		width: 100%; background: #FFFFFF;
	}
	.foot-mobile table {
		border-top: 1px solid #DCDCDC; border-right: 1px solid #DCDCDC; box-shadow: 0px 2px 3px #000; border-radius: 0 0 5px 5px;
	}
	.foot-mobile table tr td, .foot-mobile table tr th {
		padding: 9px 0 9px 10px; border-left: 1px solid #DCDCDC; border-bottom: 1px solid #DCDCDC; color: #212121; text-align: center;
	}
	.foot-s {
		margin: 8px 12px 0 12px; height: auto; overflow: hidden;
	}
	.foot-s p {
		width: 50%; display: block; float: left; border: 1px solid #FFFFFF;
	}
	.foot-s p img {
		width: 24px; border: 5px solid #FFFFFF; box-sizing: initial; position: relative; z-index: 1;
	}
	.foot-s p span {
		height: 2px; display: block; position: relative; top: -18px;
	}
	.foot-tab {
		width: 100%; height: auto; overflow: hidden;
	}
	.foot-tab a {
		width: 33.333%; display: block; float: left; text-align: center; background: rgba(0, 0, 0, 0.4); padding: 10px 0; color: #cc8442; font-size: 16px;
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

.aaabbb>div table {
	display: block;
}

.xxx {
	margin-bottom: 30px
}

.xxx>div {
	display: none;
}

.xxx>a {
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

.scroll-wrapper {
	-webkit-overflow-scrolling: touch; overflow-y: scroll;
}

iframe {
	overflow: auto !important;
}

.container-fluid.content {
	overflow: auto !important;
}
</style>
	<div id="app1" class="foot-mobile">
		<div class="foot-mobile-top">
			<div class="foot-mobile-top-img">
				<div>
					<c:choose>
						<c:when test="${matchInfo.homeTeamPicUrl!=null && matchInfo.homeTeamPicUrl.length() > 0}">
							<img src="<%=commonImgPath%>/${matchInfo.homeTeamPicUrl}">
						</c:when>
						<c:otherwise>
							<img src="<%=commonImgPath%>/ic_header.png">
						</c:otherwise>
					</c:choose>
					${matchInfo.homeTeam}
				</div>
				<div>
					<script>
						document.write(getMyFormatDateTime('${matchInfo.matchDate}','MM-dd hh:mm:ss'));
					</script>
					<span>${matchInfo.matchName}</span>
				</div>
				<div>
					<c:choose>
						<c:when test="${matchInfo.awayTeamPicUrl!=null && matchInfo.awayTeamPicUrl.length() > 0}">
							<img src="<%=commonImgPath%>/${matchInfo.awayTeamPicUrl}">
						</c:when>
						<c:otherwise>
							<img src="<%=commonImgPath%>/ic_header.png">
						</c:otherwise>
					</c:choose>
					${matchInfo.awayTeam}
				</div>
			</div>
			<div class="foot-s">
				<p>
					<img src="<%=betDir%>/Public/Home/h+/images/img-l.jpg"><span style="background: #f00300;"></span>
				</p>
				<p style="text-align: right;">
					<img src="<%=betDir%>/Public/Home/h+/images/img-r.jpg"><span style="background: #519bf3;"></span>
				</p>
			</div>
		</div>
		<div class="foot-tab aaabbb sxet nav">
			<a class="hover" data-type="1">全场</a> <a data-type="2">半场</a> <a data-type="3">总进球</a>
		</div>
		<div class="xxx">
			<div class="display">
				<p class="foot-txt2">全场波胆</p>
				<table>
					<tbody>
						<tr>
							<th width="33.333%">选项</th>
							<th width="33.333%">收益(%)</th>
							<th width="33.333%">可交易量</th>
						</tr>
					</tbody>
					<tbody>
						<tr v-for="(item,index) in fullData.matchProfitInfoList" v-on:click="invest1(index)">
							<td>{{item.score1}}:{{item.score2}}</td>
							<td>{{item.profitPercent}}</td>
							<td><span style="color: red; display: inline;">{{item.avAmount}}</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div>
				<p class="foot-txt2">半场波胆</p>
				<table>
					<tbody>
						<tr>
							<th width="33.333%">选项</th>
							<th width="33.333%">收益(%)</th>
							<th width="33.333%">可交易量</th>
						</tr>
					</tbody>
					<tbody>
						<tr v-for="(item,index) in halfData.matchProfitInfoList" v-on:click="invest2(index)">
							<td>{{item.score1}}:{{item.score2}}</td>
							<td>{{item.profitPercent}}</td>
							<td><span style="color: red; display: inline;">{{item.avAmount}}</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div>
				<p class="foot-txt2">总进球数/角球数</p>
				<table>
					<tbody>
						<tr>
							<th width="33.333%">选项</th>
							<th width="33.333%">收益(%)</th>
							<th width="33.333%">可交易量</th>
						</tr>
					</tbody>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<style>
.layui-layer-content {
	height: 100%; width: 100%; overflow: auto; -webkit-overflow-scrolling: touch;
}
</style>
	<script>
		orderColumn='addDateLong';
		orderDir='asc';
		var app1;
		var g_type=1;
		var parseObjData=function(matchProfitInfo){
			matchProfitInfo.profitPercent=(matchProfitInfo.profitPercent/100).toFixed(2);
			matchProfitInfo.avAmount=((matchProfitInfo.amount-matchProfitInfo.saledAmount)/100).toFixed(2);
			app1.fullData.matchProfitInfoList.push(matchProfitInfo);
		};
		var parseObjData2=function(matchProfitInfo){
			matchProfitInfo.profitPercent=(matchProfitInfo.profitPercent/100).toFixed(2);
			matchProfitInfo.avAmount=((matchProfitInfo.amount-matchProfitInfo.saledAmount)/100).toFixed(2);
			app1.halfData.matchProfitInfoList.push(matchProfitInfo);
		};
		var parseObjData3=function(matchProfitInfo){
			matchProfitInfo.profitPercent=(matchProfitInfo.profitPercent/100).toFixed(2);
			matchProfitInfo.avAmount=((matchProfitInfo.amount-matchProfitInfo.saledAmount)/100).toFixed(2);
			app1.scoreData.matchProfitInfoList.push(matchProfitInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.fullData.matchProfitInfoList=[];
			app1.halfData.matchProfitInfoList=[];
			app1.scoreData.matchProfitInfoList=[];
			if(g_type!=type){
				app1.fullData.totalRecords=0;
				app1.fullData.totalPage=0;
				app1.fullData.currentPage=1;
				app1.halfData.totalRecords=0;
				app1.halfData.totalPage=0;
				app1.halfData.currentPage=1;
				app1.scoreData.totalRecords=0;
				app1.scoreData.totalPage=0;
				app1.scoreData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				if(type==1){
					getDataList("ajaxGetMatchProfitInfoList?phaseId=${matchInfo.phaseId}&type="+LR_WEB.BODAN_TYPE.FULL+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.fullData,
						parseObjData);
				}else if(type==2){
					getDataList("ajaxGetMatchProfitInfoList?phaseId=${matchInfo.phaseId}&type="+LR_WEB.BODAN_TYPE.HALF+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.halfData,
						parseObjData2);
				}else if(type==3){
					getDataList("ajaxGetMatchProfitInfoList?phaseId=${matchInfo.phaseId}&type="+LR_WEB.BODAN_TYPE.SCORE+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.scoreData,
						parseObjData3);
				}
			}
		}
		function invest(matchProfitInfo){
			var width=document.documentElement.clientWidth;
			var sw,sh;
			if(matchProfitInfo.amount-matchProfitInfo.saledAmount<1){
				layer.alert('无可交易量！',{icon:7},function(){
					window.location.reload();
				});
			}else{
				var url="xiazhu/"+matchProfitInfo.profitId;
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
		}
		$(function(){
			app1=new Vue({
				el:'#app1',
				data:{fullData:{matchProfitInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data1',divNoData:'div_no_data1'},
					halfData:{matchProfitInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data2',divNoData:'div_no_data2'},
					scoreData:{matchProfitInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data3',divNoData:'div_no_data3'},isLoading:false},
				methods:{invest1:function(index){
					var matchProfitInfo=this.fullData.matchProfitInfoList[index];
					invest(matchProfitInfo);
				},invest2:function(index){
					var matchProfitInfo=this.halfData.matchProfitInfoList[index];
					invest(matchProfitInfo);
				},invest3:function(index){
					var matchProfitInfo=this.scoreData.matchProfitInfoList[index];
					invest(matchProfitInfo);
				}}});
			initData(app1);
			$(".nav a").on("click",function(i){
				$(this).addClass("hover").siblings().removeClass("hover");
				var index=$(this).index();
				$('.xxx >div').eq(index).addClass('display').siblings().removeClass('display');
				var type=$(this).attr("data-type");
				if(g_type!=type){
					fnDataDoChoice(type);
				}
			});
			getMoreData();
		});
	</script>
</body>
</html>