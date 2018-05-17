<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>赛事管理</title>
<meta name="pageId" content="manageMatchInfos" />
<meta name="groupId" content="manageClients" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script src="<%=commonJsPath%>/ajaxfileupload.js"></script>
<script src="<%=commonJsPath%>/jedate/jedate.min.js"></script>
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
			getDataFromServer("ajaxGetMatchInfoList?startDateLong="+startDateLong+"&endDateLong="+endDateLong+"&status="+status,data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"",orderable:false,searchable:false,render:function(data,type,row,meta){
			data='';
			data+='<input type="checkbox" id="ck'+userIndex+'" name="ck'+userIndex+'" value="'+row.phaseId+'" onclick="ckClicked(\'ck'+userIndex+'\');"/>';
			return data;
		}},{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"phaseId"},{data:"matchName"},{data:"matchNum"},{data:"matchDate",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{data:"",orderable:false,searchable:false,render:function(data,type,row,meta){
				return row.homeTeam+' VS '+row.awayTeam;
			}},{data:"",orderable:false,searchable:false},{data:"sortIndex"},{data:"halfScore1",render:function(data,type,row,meta){
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
				data+='<li><a onclick="removeMatchBonus('+row.phaseId+');">撤销奖金发放</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[5,"desc"]];
		d.rowCallback=function(row,data,displayIndex,displayIndexFull){
			ajaxDataJson("ajaxGetUserBetTotalMoney","phaseId="+data.phaseId,function(msg){
				$('td:eq(7)',row).html(convertRmb(msg));
			},function(errmsg){});
			return row;
		};
		_table=$table.DataTable(d);
		initTableBtns(_table);
		$("#btn-add").click(function(){
			goUrl("matchInfoAdd");
		});
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
		$("#btnTodayMatch").click(function(){
			if(btnTodayMatch){
				btnTodayMatch=false;
				startDateLong='';
				endDateLong='';
				$(this).css('background-color','buttonface');
			}else{
				btnTodayMatch=true;
				var current=getCurrentTimeLong();
				var currentDate=new Date();
				startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate());
				endDateLong=startDateLong+24*60*60*1000-1;
				$(this).css('background-color',"rgb(243, 117, 56)");
				$("#btnTomorrowMatch").css('background-color','buttonface');
			}
			btnTomorrowMatch=false;
			tableManage.fuzzySearch=false;
			_table.draw();
		});
		$("#btnTomorrowMatch").click(function(){
			if(btnTomorrowMatch){
				btnTomorrowMatch=false;
				startDateLong='';
				endDateLong='';
				$(this).css('background-color','buttonface');
			}else{
				btnTomorrowMatch=true;
				var current=getCurrentTimeLong();
				var currentDate=new Date();
				startDateLong=getTimeLong(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate())+24*60*60*1000;
				endDateLong=startDateLong+24*60*60*1000-1;
				$(this).css('background-color',"rgb(243, 117, 56)");
				$("#btnTodayMatch").css('background-color','buttonface');
			}
			btnTodayMatch=false;
			tableManage.fuzzySearch=false;
			_table.draw();
		});
		$("#btnOnlineMatch").click(function(){
			if(btnOnlineMatch){
				btnOnlineMatch=false;
				status='';
				$(this).css('background-color','buttonface');
			}else{
				btnOnlineMatch=true;
				status=LR_WEB.STATUS.ENABLED;
				$(this).css('background-color',"rgb(243, 117, 56)");
				$("#btnOfflineMatch").css('background-color','buttonface');
			}
			btnOfflineMatch=false;
			tableManage.fuzzySearch=false;
			_table.draw();
		});
		$("#btnOfflineMatch").click(function(){
			if(btnOfflineMatch){
				btnOfflineMatch=false;
				status='';
				$(this).css('background-color','buttonface');
			}else{
				btnOfflineMatch=true;
				status=LR_WEB.STATUS.DISABLED;
				$(this).css('background-color',"rgb(243, 117, 56)");
				$("#btnOnlineMatch").css('background-color','buttonface');
			}
			btnOnlineMatch=false;
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
	//撤销
	function removeMatchBonus(phaseId){
		confirmDialog("确定要更改状态吗？","ajaxRemoveMatchBonus",{"phaseId":phaseId},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	function importData(fileUrl){
		ajaxTipJson("ajaxImportMatchData","configId="+configId+"&content="+value,true,"正在更新，请稍后...",function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},null);
	};
	// 上传文件
	function uploadFile(){
		var loadi=loading('正在上传文件...');
		// 开始上传
		$.ajaxFileUpload({url:"ajaxImportMatchData", // 需要链接到服务器地址
		secureuri:false,fileElementId:"dataFile", // 文件选择框的id属性
		dataType:'text', // 服务器返回的格式，可以是json
		success:function(data,status){
			layer.close(loadi);
			data=trimPicResponseText(data);
			data=toJson(data);
			if(data.code===0){
				//showMsg("文件上传成功，正在导入数据！");
				showMsg(data.msg);
			}else{
				showMsg(data.msg);
			}
		},error:function(data,status,e){
			data=trimPicResponseText(data);
			data=toJson(data);
			showMsg("上传失败："+data.msg);
		}});
	};
	// 上传文件
	function uploadFile2(){
		var loadi=loading('正在上传文件...');
		// 开始上传
		$.ajaxFileUpload({url:"ajaxImportMatchData2", // 需要链接到服务器地址
		secureuri:false,fileElementId:"xlsFile", // 文件选择框的id属性
		dataType:'text', // 服务器返回的格式，可以是json
		success:function(data,status){
			layer.close(loadi);
			data=trimPicResponseText(data);
			data=toJson(data);
			if(data.code===0){
				showMsg(data.msg);
			}else{
				showMsg(data.msg);
			}
		},error:function(data,status,e){
			data=trimPicResponseText(data);
			data=toJson(data);
			showMsg("上传失败："+data.msg);
		}});
	};
	/* // 计算比赛的中奖情况
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
	}; */
	function selectAll(){
		var eIds=document.getElementsByName("select_all");
		var ckd=eIds[0].checked;
		$('input:checkbox').each(function(index,domEle){
			if(index>0){
				domEle.checked=ckd;
			}
		});
	}
	function ckClicked(ckId){
		var eIds=document.getElementsByName(ckId);
		var ckd=eIds[0].checked;
		if(!ckd){
			document.getElementsByName("select_all")[0].checked=false;
		}
	}
	// 批量修改状态
	function changeStatusBat(status){
		var eIds='';
		$('input:checkbox').each(function(index,domEle){
			if(index>0){
				if(domEle.checked){
					eIds+=$(this).val()+',';
				}
			}
		});
		if(isNull(eIds)){
			showMsg('还没有选择赛事！');
			return;
		}
		confirmDialog("确定要批量更改状态吗？","ajaxUpdateMatchInfoStatusBat",{"phaseIds":eIds,"status":status},function(data){
			showSuccessMsg("提示","更新状态成功！",function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
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
									<button type="button" class="btn btn-primary pull-right" id="btn-add">
										<i class="fa fa-plus"></i> 添加
									</button>
									<!-- <button type="button" class="btn btn-primary pull-right" onclick="updateMatchBonus()">检查用户中奖</button>
									<button type="button" class="btn btn-primary pull-right" onclick="updateUserBetTeamBonus()">检查团队奖</button>
									<button type="button" class="btn btn-primary pull-right" onclick="updateCheckTeamBonusJob()">汇总团队奖</button>
									<button type="button" class="btn btn-primary pull-right" onclick="updateTeamBonus()">发放团队奖</button> -->
									<button type="button" class="btn btn-primary pull-right" onclick="changeStatusBat(0)">批量上线</button>
									<button type="button" class="btn btn-primary pull-right" onclick="changeStatusBat(1)">批量下线</button>
									<button type="button" class="btn btn-primary pull-right">
										<a href="javascript:;" class="upload">导入未开始的比赛 <input class="change" type="file" id="dataFile" name="upload" accept=".xls" onchange="uploadFile()" />
										</a>
									</button>
									<button type="button" class="btn btn-primary pull-right">
										<a href="javascript:;" class="upload">导入比赛结果 <input class="change" type="file" id="xlsFile" name="upload" accept=".xls" onchange="uploadFile2()" />
										</a>
									</button>
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
							<div class="form-group">
								<label for="sHomeTeam">比赛日期:</label> <input type="text" class="form-control" id="date" onClick="jeDate({dateCell:'#date',isTime:true,format:'YYYY-MM-DD'});" />
							</div>
							<button type="button" class="btn" id="btn-advanced-search1">
								<i class="fa fa-search"></i> 查询
							</button>
							<button type="button" class="btn" id="btnTodayMatch">今日赛事</button>
							<button type="button" class="btn" id="btnTomorrowMatch">明日赛事</button>
							<button type="button" class="btn" id="btnOnlineMatch">已上线</button>
							<button type="button" class="btn" id="btnOfflineMatch">未上线</button>
						</form>
					</div>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1"><input id="select_all" name="select_all" type="checkbox" onclick="selectAll();" /></th>
								<th class="column_len1">序号</th>
								<th class="column_len2">赛事ID</th>
								<th class="column_len2">赛事</th>
								<th class="column_len2">轮次</th>
								<th class="column_len2">比赛时间</th>
								<th class="column_len6">比赛球队</th>
								<th class="column_len2">下注金额</th>
								<th class="column_len2">优先级</th>
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