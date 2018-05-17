<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>提现管理</title>
<meta name="pageId" content="manageWithdrawInfos" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sStatus=$("#sStatus").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetWithdrawInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[{data:"",orderable:false,searchable:false,render:function(data,type,row,meta){
			data='';
			data+='<input type="checkbox" id="ck'+userIndex+'" name="ck'+userIndex+'" value="'+row.withdrawId+'" onclick="ckClicked(\'ck'+userIndex+'\');"/>';
			return data;
		}},{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
			return userIndex++;
		}},{data:"withdrawId"},{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},{data:"money",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},
			{data:"userMoney",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"payeeName"},{data:"remark"},{data:"status",render:function(data,type,row,meta){
				if(data===LR_WEB.APPLY_STATUS.APPLY){
					return '已申请，等待审核';
				}else if(data===LR_WEB.APPLY_STATUS.VERIFY_SUCCESS){
					return '审核成功，等待打款';
				}else if(data===LR_WEB.APPLY_STATUS.VERIFY_FAIL){
					return '审核失败';
				}else if(data===LR_WEB.APPLY_STATUS.PAYED){
					return '已付款，等待收款';
				}else if(data===LR_WEB.APPLY_STATUS.PROCESSED){
					return '已完成';
				}
			}},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				data+='<li><a href="withdrawInfoEdit?withdrawId='+row.withdrawId+'">查看</a></li>';
				if(row.status===LR_WEB.APPLY_STATUS.PAYED){
					data+='<li><a onclick="changeFinishStatus(\''+row.withdrawId+'\');">已收款</a></li>';
				}
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[9,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
	});
	function changeFinishStatus(withdrawId){
		confirmDialog("确定用户已收款了吗？","ajaxUpdateWithdrawInfoProcessed",{'withdrawId':withdrawId},function(data){
			showSuccessMsg("提示","已收款成功！",function(){
				refreshPage();
			});
		},function(errmsg){
			showMsg(errmsg);
		});
	}
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
			showMsg('还没有选择提现记录！');
			return;
		}
		confirmDialog("确定要批量更改状态吗？","ajaxUpdateWithdrawInfoVerifyBat",{"withdrawIds":eIds,"status":status},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 批量已收款
	function updateWithdrawInfoProcessedBat(){
		var eIds='';
		$('input:checkbox').each(function(index,domEle){
			if(index>0){
				if(domEle.checked){
					eIds+=$(this).val()+',';
				}
			}
		});
		if(isNull(eIds)){
			showMsg('还没有选择提现记录！');
			return;
		}
		confirmDialog("确定要批量更改状态吗？","ajaxUpdateWithdrawInfoProcessedBat",{"withdrawIds":eIds},function(data){
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
							<h2>提现列表</h2>
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
									<button type="button" class="btn btn-primary pull-right" onclick="goUrl('exportWithdrawToExcel')">
										<i class="fa"></i> 导出审核通过的提现信息
									</button>
									<button type="button" class="btn btn-primary pull-right" onclick="changeStatusBat(10)">批量审核通过</button>
									<button type="button" class="btn btn-primary pull-right" onclick="updateWithdrawInfoProcessedBat()">批量已收款</button>
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
								<label for="sStatus">提现状态:</label> <select class="form-control" id="sStatus">
									<option value="">全部状态</option>
									<c:forEach items="${applyStatusList}" var="applyStatus" varStatus="status">
										<option value="${applyStatus.status}">${applyStatus.desc}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<button type="button" class="btn" style="margin-bottom: 0px !important;" id="btn-advanced-search">
									<i class="fa fa-search"></i> 查询
								</button>
							</div>
						</form>
					</div>
				</div>
				<div class="x_content">
					<table id="data_table" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th class="column_len1"><input id="select_all" name="select_all" type="checkbox" onclick="selectAll();" /></th>
								<th class="column_len1">序号</th>
								<th class="column_len2">提现ID</th>
								<th class="column_len2">用户ID</th>
								<th class="column_len2">提现金额</th>
								<th class="column_len2">实得金额</th>
								<th class="column_len2">收款人</th>
								<th class="column_len6">备注</th>
								<th class="column_len2">状态</th>
								<th class="column_len3">申请时间</th>
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