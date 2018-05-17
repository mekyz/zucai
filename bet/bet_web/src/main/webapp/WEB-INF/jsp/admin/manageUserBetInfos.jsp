<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户下注管理</title>
<meta name="pageId" content="manageUserBetInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	;
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sPhaseId=$("#sPhaseId").val();
		param.sProfitId=$("#sProfitId").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetUserBetInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"betId"},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"profitId"},{data:"money",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"moneyUnit",render:LR_WEB.DATA_TABLES.RENDER.MONEY_UNIT},{data:"winStatus",render:function(data,type,row,meta){
				if(data==LR_WEB.STATUS.ENABLED){
					return '中奖'+'<br>发放'+convertRmb(row.userMoney)+'元';
				}
				if(data==LR_WEB.STATUS.DISABLED){
					return '未中奖';
				}
				return '未开奖';
			}},{data:"profitStatus",render:function(data,type,row,meta){
				if(data==LR_WEB.STATUS.ENABLED){
					return '已发放';
				}
				if(row.winStatus==LR_WEB.STATUS.ENABLED){
					return '未发放';
				}
				if(row.winStatus==LR_WEB.STATUS.DISABLED){
					return '未中奖';
				}
				return '未开奖';
			}},{data:"teamProfitStatus",render:function(data,type,row,meta){
				if(data==LR_WEB.STATUS.ENABLED){
					return '已计算';
				}
				if(row.winStatus==LR_WEB.STATUS.ENABLED){
					return '未计算';
				}
				if(row.winStatus==LR_WEB.STATUS.DISABLED){
					return '未计算';
				}
				return '未开奖';
			}},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.APPLY_STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="userBetInfoEdit?betId='+row.betId+'">查看</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[10,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			ajaxJson("ajaxGetMatchProfitInfo","profitId="+data.profitId,function(msg){
				var matchProfitInfo=toJson(msg);
				ajaxJson("ajaxGetMatchInfo","phaseId="+matchProfitInfo.phaseId,function(msg){
					var matchInfo=toJson(msg);
					$('td:eq(3)',row).html(
						matchInfo.matchName+'<br>('+matchInfo.homeTeam+'-'+matchInfo.awayTeam+' '+matchProfitInfo.score1+':'+matchProfitInfo.score2+')'+'<br>'
							+getMyFormatDateTime(matchInfo.mactchDate,'MM-dd hh:mm:ss'));
				},function(errmsg){});
				$('td:eq(3)',row).html(matchProfitInfo.score1+':'+matchProfitInfo.score2);
			},function(errmsg){});
			return row;
		};
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		var currentDate=new Date();
		var startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate());
		var endDateLong=startDateLong+24*60*60*1000-1;
		bindContentMoney("sp_today_total_money","ajaxGetUserBetTotalMoney","startDateLong="+startDateLong+"&endDateLong="+endDateLong);
		bindContentMoney("sp_total_money","ajaxGetUserBetTotalMoney","");
		bindContentMoney("sp_give_point_today_total_money","ajaxGetUserBetTotalMoney","startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&moneyUnit="+LR_WEB.MONEY_UNIT.GIVE_POINT);
		bindContentMoney("sp_give_point_total_money","ajaxGetUserBetTotalMoney","moneyUnit="+LR_WEB.MONEY_UNIT.GIVE_POINT);
		bindContentMoney("sp_point_today_total_money","ajaxGetUserBetTotalMoney","startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&moneyUnit="+LR_WEB.MONEY_UNIT.POINT);
		bindContentMoney("sp_point_total_money","ajaxGetUserBetTotalMoney","moneyUnit="+LR_WEB.MONEY_UNIT.POINT);
	});
	// 计算比赛的中奖情况
	function ajaxUpdateCheckUserBetBonusByPhaseId(){
		var content='赛事ID：<input type="text" id="phaseId0" value=""/>';
		showChooseMsg("请输入赛事ID",content,'确定','取消',function(){
			var phaseId0=$("#phaseId0").val();
			if(isNull(phaseId0)){
				showMsg("请输入赛事ID！");
				return;
			}
			var phaseIds=phaseId0.split(',');
			for(var i in phaseIds){
				var phaseId=phaseIds[i];
				showLog('phaseId:'+phaseId);
			ajaxTipJson("ajaxUpdateCheckUserBetBonusByPhaseId",{"phaseId":phaseId},true,"请稍后...",function(data){
				showSuccessMsg("提示",data,function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
			}
		},null);
	};
	// 计算比赛的中奖情况
	function updateMatchBonus(){
		confirmDialog("确定要检查用户中奖吗？这将花费比较多的时间。","ajaxUpdateCheckUserBetBonusJob",{},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 计算团队奖
	function updateUserBetTeamBonus(){
		confirmDialog("确定要检查团队奖吗？这将花费比较多的时间。","ajaxUpdateUserBetTeamBonus",{},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 汇总团队奖
	function updateCheckTeamBonusJob(){
		confirmDialog("确定要汇总团队奖吗？这将花费比较多的时间。","ajaUpdateCheckTeamBonusJob",{},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 发放团队奖
	function updateTeamBonus(){
		confirmDialog("确定要发放团队奖吗？这将花费比较多的时间。","ajaxUpdateTeamBonus",{},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>用户下注列表</h2>
						</div>
						<div class="col-sm-6">
							<div class="row">
								<div class="btn-toolbar">
									<div class="pull-right" id="div-fuzzy-search">
										<input type="text" placeholder="模糊查询" id="fuzzy-search">
										<div class="btn-group" style="float: none;">
											<button type="button" class="btn" id="btn-simple-search">
												<i class="fa fa-search"></i>
											</button>
											<button type="button" class="btn" title="高级查询" id="toggle-advanced-search">
												<i class="fa fa-angle-double-down"></i>
											</button>
										</div>
									</div>
									<button type="button" class="btn btn-primary pull-right" onclick="ajaxUpdateCheckUserBetBonusByPhaseId()">检查用户中奖</button>
									<button type="button" class="btn btn-primary pull-right" onclick="updateUserBetTeamBonus()">检查团队奖</button>
									<button type="button" class="btn btn-primary pull-right" onclick="updateCheckTeamBonusJob()">汇总团队奖</button>
									<button type="button" class="btn btn-primary pull-right" onclick="updateTeamBonus()">发放团队奖</button>
								</div>
							</div>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sUserId">用户账号:</label> <input type="text" class="form-control" id="sUserId" />
							</div>
							<div class="form-group">
								<label for="sPhaseId">赛事ID:</label> <input type="text" class="form-control" id="sPhaseId" />
							</div>
							<div class="form-group">
								<label for="sProfitId">波胆ID:</label> <input type="text" class="form-control" id="sProfitId" />
							</div>
							<div class="form-group">
								<button type="button" class="btn" style="margin-bottom: 0px !important;" id="btn-advanced-search">
									<i class="fa fa-search"></i> 查询
								</button>
							</div>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						今日下注总金额：<span id="sp_today_total_money"></span>元，所有总下注金额：<span id="sp_total_money"></span>元，体验金今日下注总金额：<span id="sp_give_point_today_total_money"></span>元，体验金下注总金额：<span
							id="sp_give_point_total_money"
						></span>元，彩金今日下注总金额：<span id="sp_point_today_total_money"></span>元，彩金下注总金额：<span id="sp_point_total_money"></span>元。
					</div>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1">序号</th>
								<th class="column_len2">下注ID</th>
								<th class="column_len2">账号</th>
								<th class="column_len6">赛事</th>
								<th class="column_len2">金额</th>
								<th class="column_len2">金额类型</th>
								<th class="column_len3">中奖状态</th>
								<th class="column_len2">奖金发放状态</th>
								<th class="column_len2">团队奖状态</th>
								<th class="column_len1">状态</th>
								<th class="column_len3">下注时间</th>
								<th class="column_len2">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="table_footer.jsp"%>
</body>
</html>