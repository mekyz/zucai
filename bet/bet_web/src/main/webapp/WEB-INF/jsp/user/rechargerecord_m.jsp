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
		window.location.href="rechargerecord";
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
						<i class="fa fa-indent red"></i>充值记录
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
												<input type="text" id='start' name="begintime" class="form-control  laydate-icon" readonly value="" placeholder="">
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-4">
												<input type="text" id='end' name="endtime" class="form-control  laydate-icon" readonly value="" placeholder="">
											</div>
										</div>
										<!-- <div class="form-group">
											<div class="col-md-4">
												<select name="type" class='form-control col-sm-2'>
													<option value="0" selected>全部</option>
													<option value="1">彩金钱包</option>
												</select>
											</div>
										</div> -->
										<div class="form-group">
											<div class="col-md-4">
												<select id="status" class='form-control col-sm-2'>
													<option value="" selected>全部</option>
													<option value="5">正在处理</option>
													<option value="10">充值成功</option>
													<option value="20">充值拒绝</option>
												</select>
											</div>
										</div>
										<button type="button" class="btn  btn-success " style="margin-left: 15px !important;" onclick="query()">
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
								<th class="text-center">支付凭证</th>
								<th class="text-center" onclick="setOrderColumn('addDateLong')">申请日期</th>
								<th class="text-center" onclick="setOrderColumn('money')">充值金额（￥）</th>
								<th class="text-center" onclick="setOrderColumn('moneyUnit')">充值币种</th>
								<th class="text-center" onclick="setOrderColumn('payBankName')">充值方式</th>
								<th class="text-center" onclick="setOrderColumn('payDateLong')">充值日期</th>
								<th class="text-center" onclick="setOrderColumn('status')">申请状态</th>
								<th class="text-center">管理员回复</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in rechargeData.rechargeInfoList">
								<td><img :src="item.payPicUrl" width="50" height="75" /><a title="编辑" :href="item.payPicUrl" target='_blank'>查看大图</a></td>
								<td data-title="申请日期" class="text-center">{{item.addDateLongStr}}</td>
								<td data-title="充值金额（￥）" class="text-center"><span style="color: red; display: inline;">{{item.moneyStr}}</span></td>
								<td data-title="充值币种" class="text-center">{{item.moneyUnitStr}}</td>
								<td data-title="充值方式" class="text-center">{{item.payBankName}}</td>
								<td data-title="充值日期" class="text-center">{{item.payDateLongStr}}</td>
								<td data-title="申请状态" class="text-center">{{item.statusStr}}</td>
								<td data-title="管理员回复" class="text-center" style="height:58px;">{{item.payRemark}}</td>
							</tr>
						</tbody>
						<tr>
							<td colspan="3">总计</td>
							<td colspan="6"><span style="color: red; display: inline;">{{rechargeData.totalMoney/100}}</span></td>
						</tr>
					</table>
					<div class="row">
						<div class="col-sm-12 page">
							<div>
								<a class="num" onclick="goFirstPage(app1.rechargeData)">首页</a><a class="num" onclick="goPrePage(app1.rechargeData)">上一页</a><span class="current">{{rechargeData.currentPage}}</span> <a
									class="num" onclick="goNextPage(app1.rechargeData)"
								>下一页</a> <a class="num" onclick="goLastPage(app1.rechargeData)">末页</a> <span class="rows">共 {{rechargeData.totalRecords}} 条记录 第{{rechargeData.currentPage}} / {{rechargeData.totalPage}} 页</span> <span
									class="jump"
								>转到第&nbsp; <input type="text" id="inp" :value="rechargeData.currentPage" onclick="goPage(app1.rechargeData,this)">&nbsp;页
									<button style="cursor: pointer; margin-left: 5px; margin-bottom: 5px;" class="btn btn-success btn-lg" id="go" type="button" onclick="goFirstPage(app1.rechargeData)">GO</button>
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
								<h1 class="hidden-xs" style="border: 3px solid red;">暂时没有充值记录！</h1>
								<br />
								<h2 class="visible-xs" style="border: 3px solid red;">暂时没有充值记录！</h2>
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
		var status='';
		function query(){
			var start=$("#start").val();
			var end=$("#end").val();
			status=$("#status").val();
			startDateLong=getDateLong(start);
			endDateLong=getDateLong(end);
			getMoreData();
		}
		var app1;
		var g_type=1;
		var parseObjData=function(rechargeInfo){
			rechargeInfo.moneyUnitStr=getMoneyUnitString(rechargeInfo.moneyUnit);
			rechargeInfo.payPicUrl='<%=basePath%>'+rechargeInfo.payPicUrl;
			rechargeInfo.moneyStr=convertRmb(rechargeInfo.money);
			rechargeInfo.addDateLongStr=getFormatDateTime(rechargeInfo.addDateLong);
			rechargeInfo.verifyDateLongStr=getFormatDateTime(rechargeInfo.verifyDateLong);
			rechargeInfo.payDateLongStr=getFormatDateTime(rechargeInfo.payDateLong);
			rechargeInfo.statusStr='';
			if(rechargeInfo.status==LR_WEB.APPLY_STATUS.APPLY){
				rechargeInfo.statusStr='已申请';
			}else if(rechargeInfo.status==LR_WEB.APPLY_STATUS.VERIFY_SUCCESS){
				rechargeInfo.statusStr='审核成功';
				app1.rechargeData.totalMoney+=rechargeInfo.money;
			}else if(rechargeInfo.status==LR_WEB.APPLY_STATUS.VERIFY_FAIL){
				rechargeInfo.statusStr='审核失败';
			}
			app1.rechargeData.rechargeInfoList.push(rechargeInfo);
		};
		function getMoreData(){
			fnDataDoChoice(g_type);
		}
		function fnDataDoChoice(type){
			app1.rechargeData.rechargeInfoList=[];
			app1.rechargeData.totalMoney=0;
			if(g_type!=type){
				app1.rechargeData.totalRecords=0;
				app1.rechargeData.totalPage=0;
				app1.rechargeData.currentPage=1;
				app1.isLoading=false;
			}
			g_type=type;
			if(!app1.isLoading){
				getDataList("ajaxGetUserRechargeInfoList?status="+status+"&startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&columns[0][data]="+orderColumn+"&order[0][dir]="+orderDir,
					app1.rechargeData,parseObjData);
			}
		}
		$(function(){
			app1=new Vue({el:'#app1',
				data:{rechargeData:{rechargeInfoList:[],LIST_LEN:LR_WEB.LIST_LEN,totalRecords:0,totalPage:0,currentPage:1,divData:'div_data',divNoData:'div_no_data',totalMoney:0},isLoading:false}});
			initData(app1);
			getMoreData();
		});
	</script>
</body>
</html>