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
	window.location.href="userindex_m";
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
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>直推会员
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="panel panel-default bk-bg-white">
								<div class="panel-body ">
									<form class="form-inline">
										<div class="form-group">
											<div class="col-md-4">
												<input type="text" id='start' class="form-control laydate-icon" readonly>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-4">
												<input type="text" id='end' class="form-control laydate-icon" readonly>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-4">
												<input type="text" id='userId' class="form-control" value="" placeholder="请输入会员编号">
											</div>
										</div>
										<button type="button" class="btn btn-success" style="margin-left: 15px !important;" onclick="query()">
											<span class="bigger-110">查询</span>
										</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<script>
						layui.use('laydate',function(){
							var laydate=layui.laydate;
							var start={format:'YYYY-MM-DD',min:'2010-01-01 00:00:00', //设定最小日期为当前日期
							max:'2099-06-16 23:59:59', //最大日期
							istime:false,istoday:true,choose:function(datas){
								end.min=datas; //开始日选好后，重置结束日的最小日期
								end.start=datas; //将结束日的初始值设定为开始日
							}};
							var end={format:'YYYY-MM-DD',
							// min: laydate.now(),
							min:'2010-01-01 00:00:00',max:'2099-06-16 23:59:59',istime:false,istoday:true,choose:function(datas){
								start.max=datas; //结束日选好后，重置开始日的最大日期
							}};
							document.getElementById('start').onclick=function(){
								start.elem=this;
								laydate(start);
							}
							document.getElementById('end').onclick=function(){
								end.elem=this;
								laydate(end);
							}
						});
					</script>
					<table class="table table-no-more table-bordered table-striped mb-none">
						<thead>
							<tr>
								<th class="text-center" onclick="setOrderColumn('userId')">会员编号</th>
								<th class="text-center" onclick="setOrderColumn('referrerId')">推荐人</th>
								<th class="text-center" onclick="setOrderColumn('regDateLong')">注册时间</th>
								<!-- <th class="text-center" onclick="setOrderColumn('activeDateLong')">激活时间</th> -->
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in userData.userInfoList">
								<td data-title="会员编号" class="text-center">{{item.userId}}</td>
								<td data-title="推荐人" class="text-center"><span style='color: #F086E8'>{{item.referrerId}}</span> <template v-if="item.isReferrerIdSelf">[自己]</template></td>
								<td data-title="注册时间" class="text-center">{{item.regDateLongStr}}</td>
								<!-- <td data-title="激活时间" class="text-center">{{item.activeDateLongStr}}</td> -->
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.userData)">首页</a><a class="num" onclick="goPrePage(app1.userData)">上一页</a><span class="current">{{userData.currentPage}}</span> <a class="num"
									onclick="goNextPage(app1.userData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.userData)">末页</a> <span class="rows">共 {{userData.totalRecords}} 条记录 第{{userData.currentPage}} / {{userData.totalPage}} 页</span> <span
									class="jump"
								>转到第&nbsp; <input type="text" id="inp" :value="userData.currentPage" onclick="goPage(app1.userData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.userData)">GO</button>
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
								<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有直推会员！</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">暂时没有直推会员！</h2>
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
		orderColumn='regDateLong';
		orderDir='desc';
		var startDateLong='';
		var endDateLong='';
		var userId='';
		function query(){
			var start=$("#start").val();
			var end=$("#end").val();
			userId=$("#userId").val();
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(userInfo){
			userInfo.regDateLongStr=getFormatDateTime(userInfo.regDateLong);
			userInfo.activeDateLongStr=getFormatDateTime(userInfo.activeDateLong);
			userInfo.isReferrerIdSelf=false;
			if(userInfo.referrerId=='${userInfo.userId}'){
				userInfo.isReferrerIdSelf=true;
			}
			app1.userData.userInfoList.push(userInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.userData.userInfoList=[];
			if(g_type!=type){
				app1.userData.totalRecords=0;
				app1.userData.totalPage=0;
				app1.userData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetUserSubInfoList?subUserId="+userId+"&startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,
					app1.userData,parseObjData);
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',data:{userData:{userInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data'},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>