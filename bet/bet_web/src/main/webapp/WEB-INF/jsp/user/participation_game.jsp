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
		window.location.href="participation_game_m/${matchInfo.phaseId}";
	}
</script>
</head>
<body>
	<style>
.main {
	padding: 78px 37px 98px 278px;
}

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
.aaabbb h3 {
	color: #efaa3b; text-align: center; cursor: pointer; margin-bottom: 5px;
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

@media ( max-width :768px) {
	.aaabbb h3 {
		font-size: 18px;
	}
	.layui-layer-iframe {
		position: fixed !important
	}
}

.table-bordered>tbody>tr>td.feng {
	border: none !important;
}
</style>
	<div id="app1">
		<div class='row'>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<a class=" btn" style="color: #fff" target-form="commentForm" onclick="history.go(-1);">返回</a>
			</div>
		</div>
		<div class="nav">
			<div class="aaabbb col-lg-4 col-md-4 col-sm-4 col-xs-4 hover btn" style="background: rgba(0, 0, 0, 0.4) !important;" data-type="1">
				<div>
					<h3>全场波胆</h3>
				</div>
			</div>
			<div class="aaabbb col-lg-4 col-md-4 col-sm-6 col-xs-4 btn" style="background: rgba(0, 0, 0, 0.4) !important;" data-type="2">
				<div>
					<h3>半场波胆</h3>
				</div>
			</div>
			<div class="aaabbb col-lg-4 col-md-4 col-sm-6 col-xs-4 btn" style="background: rgba(0, 0, 0, 0.4) !important;" data-type="3">
				<div>
					<h3>总进球数</h3>
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
										<th class="text-center" onclick="setOrderColumn('score1')">比分</th>
										<th class="text-center" onclick="setOrderColumn('profitPercent')">收益（%）</th>
										<th class="text-center" onclick="setOrderColumn('amount')">可交易量</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(item,index) in fullData.matchProfitInfoList">
										<td data-title="比分" class="text-center">{{item.score1}}:{{item.score2}}</td>
										<td data-title="收益（%）" class="text-center">{{item.profitPercent}}</td>
										<td data-title="可交易量" class="text-center"><span style="color: red; display: inline;">{{item.avAmount}}</span></td>
										<td data-title="详情"><a class=" btn-sm btn-success" style="background: #39a5bf; cursor: pointer;" v-on:click="invest1(index)">下注<b style="font-size: 16px; color: #fff !important;">+</b></a></td>
									</tr>
								</tbody>
							</table>
							<div class="row">
								<div class="col-sm-12 page">
									<div>
										<a class="num" onclick="goFirstPage(app1.fullData)">首页</a><a class="num" onclick="goPrePage(app1.fullData)">上一页</a><span class="current">{{fullData.currentPage}}</span> <a class="num"
											onclick="goNextPage(app1.fullData)"
										>下一页</a> <a class="num" onclick="goLastPage(app1.fullData)">末页</a> <span class="rows">共 {{fullData.totalRecords}} 条记录 第{{fullData.currentPage}} / {{fullData.totalPage}} 页</span> <span
											class="jump"
										>转到第&nbsp; <input type="text" id="inp" :value="fullData.currentPage" onclick="goPage(app1.fullData,this)">&nbsp;页
											<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.fullData)">GO</button>
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
										<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有全场波胆！</h1>
										<br />
										<h2 class="visible-xs" style="border: 3px solid red;">暂时没有全场波胆！</h2>
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
										<th class="text-center" onclick="setOrderColumn('score1')">比分</th>
										<th class="text-center" onclick="setOrderColumn('profitPercent')">收益（%）</th>
										<th class="text-center" onclick="setOrderColumn('amount')">可交易量</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(item,index) in halfData.matchProfitInfoList">
										<td data-title="比分" class="text-center">{{item.score1}}:{{item.score2}}</td>
										<td data-title="收益（%）" class="text-center">{{item.profitPercent}}</td>
										<td data-title="可交易量" class="text-center"><span style="color: red; display: inline;">{{item.avAmount}}</span></td>
										<td data-title="详情"><a class=" btn-sm btn-success " style="background: #39a5bf; cursor: pointer;" v-on:click="invest2(index)">下注<b style="font-size: 16px; color: #fff !important;">+</b></a></td>
									</tr>
								</tbody>
							</table>
							<div class="row">
								<div class="col-sm-12 page">
									<div>
										<a class="num" onclick="goFirstPage(app1.halfData)">首页</a><a class="num" onclick="goPrePage(app1.halfData)">上一页</a><span class="current">{{halfData.currentPage}}</span> <a class="num"
											onclick="goNextPage(app1.halfData)"
										>下一页</a> <a class="num" onclick="goLastPage(app1.halfData)">末页</a> <span class="rows">共 {{halfData.totalRecords}} 条记录 第{{halfData.currentPage}} / {{halfData.totalPage}} 页</span> <span
											class="jump"
										>转到第&nbsp; <input type="text" id="inp" :value="halfData.currentPage" onclick="goPage(app1.halfData,this)">&nbsp;页
											<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.halfData)">GO</button>
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
										<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有半场波胆！</h1>
										<br />
										<h2 class="visible-xs" style="border: 3px solid red;">暂时没有半场波胆！</h2>
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
				<div id="div_data3" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="panel panel-default bk-bg-white">
						<div class="panel-body">
							<table class="table table-no-more table-bordered table-striped mb-none">
								<thead>
									<tr>
										<th class="text-center" onclick="setOrderColumn('score1')">比分</th>
										<th class="text-center" onclick="setOrderColumn('profitPercent')">收益（%）</th>
										<th class="text-center" onclick="setOrderColumn('amount')">可交易量</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(item,index) in scoreData.matchProfitInfoList">
										<td data-title="比分" class="text-center">{{item.score1}}:{{item.score2}}</td>
										<td data-title="收益（%）" class="text-center">{{item.profitPercent}}</td>
										<td data-title="可交易量" class="text-center"><span style="color: red; display: inline;">{{item.avAmount}}</span></td>
										<td data-title="详情"><a class=" btn-sm btn-success " style="background: #39a5bf; cursor: pointer;" v-on:click="invest3(index)">下注<b style="font-size: 16px; color: #fff !important;">+</b></a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div id="div_no_data3" class="container-fluid content">
					<div class="row">
						<div id="content" class="col-sm-12 full">
							<div class="row box-error">
								<div class="col-lg-12 col-md-12 col-xs-12">
									<div class="text-center">
										<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有数据！</h1>
										<br />
										<h2 class="visible-xs" style="border: 3px solid red;">暂时没有数据！</h2>
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
			$(".nav div").on("click",function(i){
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