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
		window.location.href="news";
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
			<div class="panel panel-default bk-widget bk-border-off">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>公司公告
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<div class="bk-padding-left-20 bk-padding-right-20" v-for="(item,index) in newsData.newsInfoList">
						<a :href="item.href" class="bk-bg-very-light-gray bk-bg-lighten bk-padding-off-top bk-padding-off-bottom">
							<div class="bk-fg-inverse bk-margin-top-10">
								<strong class="bk-fg-primary"><div class="point point-success point-lg"></div>{{item.title}}</strong> <span class="pull-right"><i class="fa fa-clock-o"></i><small>
										{{item.updateDateLongStr}}</small></span>
							</div> <br />
						</a>
						<hr class="bk-margin-off" />
					</div>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.newsData)">首页</a><a class="num" onclick="goPrePage(app1.newsData)">上一页</a><span class="current">{{newsData.currentPage}}</span> <a class="num"
									onclick="goNextPage(app1.newsData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.newsData)">末页</a> <span class="rows">共 {{newsData.totalRecords}} 条记录 第{{newsData.currentPage}} / {{newsData.totalPage}} 页</span> <span
									class="jump"
								>转到第&nbsp; <input type="text" id="inp" :value="newsData.currentPage" onclick="goPage(app1.newsData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.newsData)">GO</button>
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
								<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有公司公告！</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">暂时没有公司公告！</h2>
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
		var app1;
		var g_type=1;
		var parseObjData=function(newsInfo){
			newsInfo.updateDateLongStr=getFormatDateTime(newsInfo.updateDateLong);
			newsInfo.href='shownews/'+newsInfo.newsId;
			app1.newsData.newsInfoList.push(newsInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.newsData.newsInfoList=[];
			if(g_type!=type){
				app1.newsData.totalRecords=0;
				app1.newsData.totalPage=0;
				app1.newsData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetNewsList?sortId=1",app1.newsData,parseObjData);
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',data:{newsData:{newsInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>