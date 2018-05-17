<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>代理商管理</title>
<meta name="pageId" content="manageAgentUsers" />
<meta name="groupId" content="users" />
<meta name="bodyStyle" content="" />
<%@ include file="table_header.jsp"%>
<script src="<%=commonJsPath%>/jedate/jedate.min.js"></script>
<script>
	function initQueryParams(param){
		param.sUserId=$("#sUserId").val();
		param.sName=$("#sName").val();
		param.sNumber=$("#sNumber").val();
		param.sUserType=$("#sUserType").val();
		param.sStatus=$("#sStatus").val();
		return param;
	};
	$(function(){
			var userIndex=1;
			var $table=$('#data_table');
			var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
			d.ajax=function(data,callback,settings){
				getDataFromServer("ajaxGetUserInfoList?userType="+LR_WEB.USER_TYPE.AGENT,data,callback,settings,function(result){
					userIndex=result.start+1;
				});
			};
			d.columns=[
				{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
					return userIndex++;
				}},
				{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},
				{data:"nickname"},
				{data:"partnerId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},
				{data:"agentId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},
				{data:"picUrl",orderable:false,searchable:false,render:function(data,type,row,meta){
					if(!isNull(data)){
						return "<img src='<%=basePath%>"+data+"' width='40px;'/>";
				}
				return data;
			}},{data:"sex",render:LR_WEB.DATA_TABLES.RENDER.SEX_TYPE},{data:"userType",render:LR_WEB.DATA_TABLES.RENDER.USER_TYPE},{data:"leftSendCount"},
			{data:"countValidateDateLong",render:function(data,type,row,meta){
				var tm=getFormatDateTime(data);
				return getTimeDate(tm);
			}},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},{data:"addDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},
			{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				var btnStatus='';
				if(row.status==LR_WEB.STATUS.ENABLED){
					btnStatus='<li><a onclick="changeStatus(\''+row.userId+'\','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					btnStatus='<li><a onclick="changeStatus(\''+row.userId+'\','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				if(row.userType<LR_WEB.USER_TYPE.PARTNER){
					var btnPartner='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.PARTNER+');">设为合伙人</a></li>';
					var btnToPartner='<li><a onclick="changeUserPartner(\''+row.userId+'\');">划拨给合伙人</a></li>';
					data+=btnPartner+btnToPartner;
				}
				if(row.userType<LR_WEB.USER_TYPE.AGENT){
					var btnAgent='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT+');">设为代理商</a></li>';
					data+=btnAgent;
				}
				if(row.userType<LR_WEB.USER_TYPE.MEMBER){
					var btnMember='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.MEMBER+');">设为会员</a></li>';
					data+=btnMember;
				}else{
					var btnCommon='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.COMMON+');">设为普通用户</a></li>';
					data+=btnCommon;
				}
				var btnResetPwd='<li><a onclick="resetPwd(\''+row.userId+'\');">重置密码</a></li>';
				var btnAddCount='<li><a onclick="addCount(\''+row.userId+'\');">增加消息条数</a></li>';
				data+=btnStatus+btnResetPwd+btnAddCount;
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[11,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
	});
	// 修改类型
	function changeUserType(userId,userType){
		confirmDialog("确定要更改用户类型吗？","ajaxUpdateUserType","userId="+userId+"&userType="+userType,function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 划拨合伙人
	function changeUserPartner(userId,partnerId){
		ajaxDataJson("ajaxGetUserInfoList","userType="+LR_WEB.USER_TYPE.PARTNER,function(data){
			var partnerList=data.data;
			if(partnerList.length<1){
				showMsg('还没有合伙人！');
				return;
			}else{
				var content='合伙人：<select id="partnerId0">';
				for( var index in partnerList){
					var partnerInfo=partnerList[index];
					content+="<option value ='"+partnerInfo.userId+"'>"+partnerInfo.nickname+"("+partnerInfo.userId+")"+"</option>";
				}
				content+="</select>";
				showChooseMsg("请选择合伙人",content,'确定','取消',function(){
					var partnerId0=$("#partnerId0").val();
					if(isNull(partnerId0)){
						showMsg("请选择合伙人！");
						return;
					}
					ajaxTipJson("ajaxUpdateUserPartner","userId="+userId+"&partnerId="+partnerId0,true,"请稍后...",function(data){
						showSuccessMsg("提示",data,function(){
							refreshPage();
						});
					},function(errmsg){
						showMsg(errmsg);
					});
				},null);
			}
		});
	};
	// 修改状态
	function changeStatus(userId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateUserInfoStatus","userId="+userId+"&status="+status,function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 重置密码
	function resetPwd(userId){
		var content='新密码：<input type="text" id="pwd0" value=""/>';
		showChooseMsg("请输入新密码",content,'确定','取消',function(){
			var pwd0=$("#pwd0").val();
			if(isNull(pwd0)){
				showMsg("请输入新密码！");
				return;
			}
			ajaxTipJson("ajaxUpdateUserPwd","userId="+userId+"&newPwd="+pwd0,true,"请稍后...",function(data){
				showSuccessMsg("提示",data,function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
		},null);
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
							<h2>代理商列表</h2>
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
								<label for="sUserId">用户账号:</label> <input type="text" class="form-control" id="sUserId" />
							</div>
							<div class="form-group">
								<label for="sNumber">手机号码:</label> <input type="text" class="form-control" id="sNumber" />
							</div>
							<div class="form-group">
								<label for="sStatus">账号状态:</label> <select class="form-control" id="sStatus">
									<option value="">全部状态</option>
									<option value="0">已启用</option>
									<option value="1">已禁用</option>
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
								<th class="column_len1">序号</th>
								<th class="column_len2">账号</th>
								<th class="column_len2">昵称</th>
								<th class="column_len2">合伙人</th>
								<th class="column_len2">代理商</th>
								<th class="column_len2">头像</th>
								<th class="column_len1">性别</th>
								<th class="column_len2">等级</th>
								<th class="column_len2">剩余条数</th>
								<th class="column_len2">有效期</th>
								<th class="column_len1">状态</th>
								<th class="column_len3">注册时间</th>
								<th class="column_len2">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div><%@ include file="table_footer.jsp"%>
</body>
</html>