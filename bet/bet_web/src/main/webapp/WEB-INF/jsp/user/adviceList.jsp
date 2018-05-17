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
<!-- <script>
	if(isMobileWeb()){
		window.location.href="adviceList_m";
	}
</script> -->
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
						<i class="fa fa-indent red"></i>意见反馈列表
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="panel panel-default bk-bg-white">
								<div class="panel-body ">
									<form class="form-inline">
										<button type="button" class="btn  btn-success" style="margin-left: 15px !important;" onclick="goUrl('adviceAdd')">
											<span class="bigger-110">我要反馈</span>
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<table class="table table-no-more table-bordered table-striped mb-none">
						<thead>
							<tr>
								<th class="text-center" onclick="setOrderColumn('addDateLong')">日期</th>
								<th class="text-center">内容</th>
								<th class="text-center">回复</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in adviceData.adviceInfoList">
								<td data-title="日期" class="text-center">{{item.addDateLongStr}}</td>
								<td data-title="内容">{{item.content}}</td>
								<td data-title="回复">{{item.adviceReplyInfo.content}}</td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.adviceData)">首页</a><a class="num" onclick="goPrePage(app1.adviceData)">上一页</a><span class="current">{{adviceData.currentPage}}</span> <a class="num"
									onclick="goNextPage(app1.adviceData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.adviceData)">末页</a> <span class="rows">共 {{adviceData.totalRecords}} 条记录 第{{adviceData.currentPage}} / {{adviceData.totalPage}} 页</span> <span
									class="jump"
								>转到第&nbsp; <input type="text" id="inp" :value="adviceData.currentPage" onclick="goPage(app1.adviceData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.adviceData)">GO</button>
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
								<h1 class="hidden-xs" style="border: 3px solid red;">
									<a href="adviceAdd">我要反馈！</a>
								</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">
									<a href="adviceAdd">我要反馈！</a>
								</h2>
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
		var changetype='';
		function query(){
			var start=$("#start").val();
			var end=$("#end").val();
			betUserId=$("#betUserId").val();
			changetype=$("#changetype").val();
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(adviceInfo){
			adviceInfo.addDateLongStr=getFormatDateTime(adviceInfo.addDateLong);
			if(adviceInfo.status==LR_WEB.STATUS.ENABLED){
				adviceInfo.statusStr='已回复';
			}else{
				adviceInfo.statusStr='未回复';
			}
			adviceInfo.adviceReplyInfo={};
			getAdviceReplyInfo(adviceInfo);
			app1.adviceData.adviceInfoList.push(adviceInfo);
		};
		function getAdviceReplyInfo(adviceInfo){
			ajaxDataJson("ajaxGetAdviceReplyInfoList","adviceId="+adviceInfo.adviceId,function(data){
				var adviceReplyInfoList=data.data;
				if(adviceReplyInfoList&&adviceReplyInfoList.length>0){
					adviceInfo.adviceReplyInfo=adviceReplyInfoList[0];
				}
			},function(errmsg){});
		}
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.adviceData.adviceInfoList=[];
			if(g_type!=type){
				app1.adviceData.totalRecords=0;
				app1.adviceData.totalPage=0;
				app1.adviceData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetAdviceInfoList?startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,app1.adviceData,parseObjData);
			}
		}
		$(function(){
			app1=new Vue(
				{el:'#app1',data:{adviceData:{adviceInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>