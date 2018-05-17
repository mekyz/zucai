<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>未开奖赛事管理</title>
<meta name="pageId" content="manageUnOpenMatchInfos" />
<meta name="groupId" content="manageClients" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sPhaseId=$("#sPhaseId").val();
		param.sMatchName=$("#sMatchName").val();
		param.sHomeTeam=$("#sHomeTeam").val();
		return param;
	};
	var _table;
	var startDateLong='';
	var endDateLong='';
	var status='';
	var btnTodayMatch=false,btnTomorrowMatch=false,btnOnlineMatch=false,btnOfflineMatch=false;
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			endDateLong=getCurrentTimeLong()-90*60*1000;
			getDataFromServer("ajaxGetMatchInfoList?startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&finalResultStatus="+LR_WEB.STATUS.DISABLED,data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"phaseId"},{data:"matchDate",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{data:"",orderable:false,searchable:false,render:function(data,type,row,meta){
			return row.homeTeam+' VS '+row.awayTeam;
		}},{data:"",orderable:false,searchable:false},{data:"halfScore1",render:function(data,type,row,meta){
			if(!isNull(row.halfScore1)&&!isNull(row.halfScore2)||row.halfScore1==0||row.halfScore2==0){
				return row.halfScore1+':'+row.halfScore2;
			}else{
				return "未开奖";
			}
		}},{data:"finalScore1",render:function(data,type,row,meta){
			if(!isNull(row.finalScore1)&&!isNull(row.finalScore2)||row.finalScore1==0||row.finalScore2==0){
				return row.finalScore1+':'+row.finalScore2;
			}else{
				return "未开奖";
			}
		}},{data:"status",render:function(data,type,row,meta){
			if(data==LR_WEB.STATUS.ENABLED){
				return '已上线';
			}
			return '已下线';
		}},{orderable:false,searchable:false,render:function(data,type,row,meta){
			data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
			data+='<li><a href="matchInfoEdit?phaseId='+row.phaseId+'">修改</a></li>';
			if(row.status==LR_WEB.STATUS.ENABLED){
				data+='<li><a onclick="changeStatus('+row.phaseId+','+LR_WEB.STATUS.DISABLED+');">下线</a></li>';
			}else{
				data+='<li><a onclick="changeStatus('+row.phaseId+','+LR_WEB.STATUS.ENABLED+');">上线</a></li>';
			}
			data+='<li><a href="manageMatchProfitInfos?phaseId='+row.phaseId+'">调整比分</a></li>';
			data+='<li><a onclick="deleteUserBets('+row.phaseId+');">撤回下注</a></li>';
			data+='</ul></div>';
			return data;
		}}];
		d.aaSorting=[[2,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			ajaxDataJson("ajaxGetUserBetTotalMoney","phaseId="+data.phaseId,function(msg){
				$('td:eq(4)',row).html(convertRmb(msg));
			},function(errmsg){});
			return row;
		};
		_table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-advanced-search1").click(function(){
			var date=$("#date").val();
			if(date){
				var year=date.substr(0,4);
				var month=date.substr(5,2);
				var day=date.substr(8);
				startDateLong=getTimeLong(year,month,day);
				endDateLong=startDateLong+24*60*60*1000-1;
			}
			tableManage.fuzzySearch=false;
			_table.draw();
		});
	});
	// 修改状态
	function changeStatus(phaseId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateMatchInfoStatus",{"phaseId":phaseId,"status":status},function(data){
			showSuccessMsg("提示","更新状态成功！",function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 撤销下注
	function deleteUserBets(phaseId){
		var content='备注：<input type="text" id="remark0" value="赛事未进行"/>';
		showChooseMsg("请输入备注",content,'确定','取消',function(){
			var remark0=$("#remark0").val();
			if(isNull(remark0)){
				showMsg("请输入备注信息！");
				return;
			}
			ajaxTipJson("ajaxDeleteUserBets",{"phaseId":phaseId,"remark":remark0},true,"请稍后...",function(data){
				showSuccessMsg("提示",data,function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
		},null);
	};
</script>
<style>
.upload {
	padding: 4px 10px; height: 20px; line-height: 20px; position: relative; border: 1px solid #999; text-decoration: none; color: #ffffff;
}

.change {
	position: absolute; overflow: hidden; right: 0; top: 0; opacity: 0;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>球队列表</h2>
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
								</div>
							</div>
						</div>
					</div>
					<div class="row" id="div-advanced-search">
						<form class="form-inline well">
							<div class="form-group">
								<label for="sPhaseId">赛事ID:</label> <input type="text" class="form-control" id="sPhaseId" />
							</div>
							<div class="form-group">
								<label for="sMatchName">赛事:</label> <input type="text" class="form-control" id="sMatchName" />
							</div>
							<div class="form-group">
								<label for="sHomeTeam">主队名称:</label> <input type="text" class="form-control" id="sHomeTeam" />
							</div>
							<button type="button" class="btn" id="btn-advanced-search1">
								<i class="fa fa-search"></i> 查询
							</button>
						</form>
					</div>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1">序号</th>
								<th class="column_len2">赛事ID</th>
								<th class="column_len2">比赛时间</th>
								<th class="column_len6">比赛球队</th>
								<th class="column_len2">下注金额</th>
								<th class="column_len2">半场结果</th>
								<th class="column_len2">全场结果</th>
								<th class="column_len1">状态</th>
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