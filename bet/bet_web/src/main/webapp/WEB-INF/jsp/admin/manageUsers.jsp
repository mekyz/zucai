<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>用户管理</title>
<meta name="pageId" content="manageUsers" />
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
		param.sReferrerId=$("#sReferrerId").val();
		param.sStatus=$("#sStatus").val();
		return param;
	};
	$(function(){
		var userIndex=1;
		var $table=$('#data_table');
		var d=LR_WEB.DATA_TABLES.DEFAULT_OPTION;
		d.ajax=function(data,callback,settings){
			getDataFromServer("ajaxGetUserInfoList",data,callback,settings,function(result){
				userIndex=result.start+1;
			});
		};
		d.columns=[
				{data:"id",orderable:false,searchable:false,render:function(data,type,row,meta){
					return userIndex++;
				}},
				{data:"userId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},
				{data:"referrerId",render:LR_WEB.DATA_TABLES.RENDER.USER_REDIRECT},
				{data:"name"},
				{data:"number"},
				{data:"point",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"freezePoint",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},{data:"givePoint",render:LR_WEB.DATA_TABLES.RENDER.CONVERT_RMB},<%-- 
				{data:"picUrl",orderable:false,searchable:false,render:function(data,type,row,meta){
					if(!isNull(data)){
						return "<img src='<%=basePath%>"+data+"' width='40px;'/>";
				}
				return data;
			}},{data:"sex",render:LR_WEB.DATA_TABLES.RENDER.SEX_TYPE}, --%>{data:"userType",render:LR_WEB.DATA_TABLES.RENDER.USER_TYPE},{data:"sysStatus",render:function(data,type,row,meta){
				if(data==LR_WEB.STATUS.ENABLED){
					return "是";
				}return "否";
			}},{data:"status",render:LR_WEB.DATA_TABLES.RENDER.STATUS},
			{data:"regDateLong",render:LR_WEB.DATA_TABLES.RENDER.DATE_LONG},{orderable:false,searchable:false,render:function(data,type,row,meta){
				data='<div class="dropdown"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">菜单<span class="caret"></span></button><ul class="dropdown-menu">';
				if(row.status==LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeStatus(\''+row.userId+'\','+LR_WEB.STATUS.DISABLED+');">禁用</a></li>';
				}else{
					data+='<li><a onclick="changeStatus(\''+row.userId+'\','+LR_WEB.STATUS.ENABLED+');">启用</a></li>';
				}
				if(row.userType<LR_WEB.USER_TYPE.AGENT1){
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT1+');">设为一星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT2+');">设为二星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT3+');">设为三星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT4+');">设为四星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT5+');">设为五星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT6+');">设为六星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT7+');">设为七星代理</a></li>';
				}else if(row.userType<LR_WEB.USER_TYPE.AGENT2){
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT2+');">设为二星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT3+');">设为三星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT4+');">设为四星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT5+');">设为五星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT6+');">设为六星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT7+');">设为七星代理</a></li>';
				}else if(row.userType<LR_WEB.USER_TYPE.AGENT3){
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT3+');">设为三星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT4+');">设为四星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT5+');">设为五星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT6+');">设为六星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT7+');">设为七星代理</a></li>';
				}else if(row.userType<LR_WEB.USER_TYPE.AGENT4){
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT4+');">设为四星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT5+');">设为五星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT6+');">设为六星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT7+');">设为七星代理</a></li>';
				}else if(row.userType<LR_WEB.USER_TYPE.AGENT5){
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT5+');">设为五星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT6+');">设为六星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT7+');">设为七星代理</a></li>';
				}else if(row.userType<LR_WEB.USER_TYPE.AGENT6){
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT6+');">设为六星代理</a></li>';
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT7+');">设为七星代理</a></li>';
				}else if(row.userType<LR_WEB.USER_TYPE.AGENT7){
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.AGENT7+');">设为七星代理</a></li>';
				}
				if(row.userType<LR_WEB.USER_TYPE.MEMBER){
					//data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.MEMBER+');">设为会员</a></li>';
				}else{
					data+='<li><a onclick="changeUserType(\''+row.userId+'\','+LR_WEB.USER_TYPE.COMMON+');">设为普通用户</a></li>';
				}
				if(row.sysStatus!=LR_WEB.STATUS.ENABLED){
					data+='<li><a onclick="changeUserSysStatus(\''+row.userId+'\','+LR_WEB.STATUS.ENABLED+');">设为系统用户</a></li>';
				}
				data+='<li><a onclick="resetPwd(\''+row.userId+'\');">重置登录密码</a></li>';
				data+='<li><a onclick="resetPwd2(\''+row.userId+'\');">重置二级密码</a></li>';
				data+='<li><a target="_blank" href="goUserInfo?userId='+row.userId+'">进入会员前台</a></li>';
				data+='</ul></div>';
				return data;
			}}];
		d.aaSorting=[[10,"desc"]];
		var _table=$table.DataTable(d);
		initTableBtns(_table);
		//updateUserBalanceLogs();
	});
	// 修改类型
	function changeUserType(userId,userType){
		confirmDialog("确定要更改用户类型吗？","ajaxUpdateUserType",{"userId":userId,"userType":userType},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 修改状态
	function changeStatus(userId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateUserInfoStatus",{"userId":userId,"status":status},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 修改状态
	function changeUserSysStatus(userId,status){
		confirmDialog("确定要更改状态吗？","ajaxUpdateUserInfoSysStatus",{"userId":userId,"status":status},function(data){
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
				showMsg("请输入新登录密码！");
				return;
			}
			ajaxTipJson("ajaxUpdateUserPwd",{"userId":userId,"newPwd":pwd0},true,"请稍后...",function(data){
				showSuccessMsg("提示",data,function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
		},null);
	};
	// 重置二级密码
	function resetPwd2(userId){
		var content='新密码：<input type="text" id="pwd0" value=""/>';
		showChooseMsg("请输入新密码",content,'确定','取消',function(){
			var pwd0=$("#pwd0").val();
			if(isNull(pwd0)){
				showMsg("请输入新二级密码！");
				return;
			}
			ajaxTipJson("ajaxUpdateUserPwd2",{"userId":userId,"newPwd":pwd0},true,"请稍后...",function(data){
				showSuccessMsg("提示",data,function(){
					refreshPage();
				});
			},function(msg){
				showMsg(msg);
			});
		},null);
	};
	// 检查级别
	function checkUserValidateDate(){
		confirmDialog("确定要检查级别吗？","ajaxCheckUserValidateDate",{},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	// 检查父节点
	function checkUserParents(){
		confirmDialog("确定要检查父节点吗？","ajaxCheckUserParents",{},function(data){
			showSuccessMsg("提示",data,function(){
				refreshPage();
			});
		},function(msg){
			showMsg(msg);
		});
	};
	function updateUserBalanceLogs(){
		var start=0;
		var length=10;
		getUsers(start,length);
	};
	function getUsers(start,length){
		ajaxDataJson("ajaxGetUserInfoList","start="+start+"&length="+length,function(data){
			var list=data.data;
			if(list.length>0){
				for(var i=0;i<list.length;i++){
					var userInfo=list[i];
					checkBalance(userInfo);
				}
				if(list.length==length){
					start+=list.length;
					getUsers(start,length);
				}
			}
		});
	}
	function checkBalance(userInfo){
		if(userInfo.givePoint<0){
		ajaxDataJson("ajaxUpdateUserInfoGivePoint","userId="+userInfo.userId,function(msg){
			 
				showLog(userInfo.id+msg);
			
		});
		}
		/* 
		ajaxDataJson("ajaxGetUserBalanceLogMoney","userId="+userInfo.userId+"&moneyUnit="+LR_WEB.MONEY_UNIT.POINT,function(msg){
			var money=msg;
			var b1=userInfo.point+userInfo.freezePoint;
			var b2=money;
			if(b1!=b2){
				showLog(userInfo.id+' 余额异常(b1='+b1+',b2='+b2+')：'+userInfo.userId+','+convertRmb(userInfo.point)+','+convertRmb(userInfo.freezePoint)+','+userInfo.givePoint+'/'
					+convertRmb(money)+','+givePoint+',点击修正:${serverUrl}admin/ajaxUpdateUserBalance?userId='+userInfo.userId);
			}
		});
		ajaxDataJson("ajaxGetUserBalanceLogMoney","userId="+userInfo.userId+"&moneyUnit="+LR_WEB.MONEY_UNIT.GIVE_POINT,function(msg){
			var givePoint=msg;
			var b1=(userInfo.point+userInfo.freezePoint);
			var b2=(money);
			if(b1!=b2){
				showLog(userInfo.id+' 余额异常(b1='+b1+',b2='+b2+')：'+userInfo.userId+','+convertRmb(userInfo.point)+','+convertRmb(userInfo.freezePoint)+','+userInfo.givePoint+'/'
					+convertRmb(money)+','+givePoint+',点击修正:${serverUrl}admin/ajaxUpdateUserBalance?userId='+userInfo.userId);
			}else{
				//showLog(userInfo.id+' 余额(b1='+b1+',b2='+b2+')：'+userInfo.userId+','+convertRmb(userInfo.balance)+','+convertRmb(userInfo.freezeBalance)+','+userInfo.point+'/'+convertRmb(money)+','+point);
			}
		}); */
	}
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<div class="row">
						<div class="col-sm-6">
							<h2>用户列表</h2>
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
									<button type="button" class="btn btn-primary pull-right" onclick="checkUserValidateDate()">
										<i class="fa"></i> 检查用户级别
									</button>
									<button type="button" class="btn btn-primary pull-right" onclick="checkUserParents()">
										<i class="fa"></i> 检查用户父节点
									</button>
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
								<label for="sReferrerId">推荐人账号:</label> <input type="text" class="form-control" id="sReferrerId" />
							</div>
							<div class="form-group">
								<label for="sNumber">手机号码:</label> <input type="text" class="form-control" id="sNumber" />
							</div>
							<div class="form-group">
								<label for="sUserType">用户类型:</label> <select class="form-control" id="sUserType">
									<option value="">全部类型</option>
									<c:forEach items="${userTypeList}" var="userType" varStatus="status">
										<option value="${userType.type}">${userType.desc}</option>
									</c:forEach>
								</select>
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
								<th class="column_len2">推荐人</th>
								<th class="column_len2">姓名</th>
								<th class="column_len2">手机号码</th>
								<th class="column_len2">余额</th>
								<th class="column_len2">冻结金额</th>
								<th class="column_len2">体验金</th>
								<!-- <th class="column_len2">头像</th>
								<th class="column_len2">性别</th> -->
								<th class="column_len2">等级</th>
								<th class="column_len2">系统用户</th>
								<th class="column_len2">状态</th>
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