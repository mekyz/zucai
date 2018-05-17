<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<title>DM SUPER CLUB</title>
<%-- 配置信息开始 --%>
<meta name="pageId" content="index" />
<meta name="groupId" content="index" />
<meta name="bodyStyle" content="" />
<%-- 配置信息结束 --%>
<script src="<%=commonJsPath%>/sockjs/sockjs.min.js"></script>
<script src="<%=commonJsPath%>/stomp/stomp.min.js"></script>
<script>
	var sock;
	var stomp;
	function connect(){
		var url=$("#url").val();
		sock=new SockJS(url);
		stomp=Stomp.over(sock);
		stomp.connect({},function(frame){
			stomp.subscribe('/topic/hello',function(returnInfo){
				$("#receiveContent").val(JSON.parse(returnInfo.body).msg);
				var code=JSON.parse(returnInfo.body).code;
				var msg=JSON.parse(returnInfo.body).msg;
				if(code==0){
					showLog(msg);
				}else{}
			});
			stomp.subscribe('/topic/addUserWithdraw',function(returnInfo){
				$("#receiveContent").val(JSON.parse(returnInfo.body).msg);
			});
		});
	}
	function sendMsg(){
		var content=$("#content").val();
		console.log('准备发送消息：'+content);
		var userRedpackLogInfo={'userId':content,'deviceId':content};
		var payload=JSON.stringify(userRedpackLogInfo);
		stomp.send($("#addr").val(),{},payload);//发送消息
	}
	function disconnect(){
		stomp.disconnect();
	}
	function sendSubscr(){
		ajaxJson("user/ajaxSubscr","msg="+$("#content").val(),function(data){},function(errmsg){
			showMsg(errmsg);
		});
	}
</script>
</head>
<body>
	<input type="text" id="url" value="user/stomp" style="width: 100%; height: 40px;" placeholder="请输入URL" />
	<button type="button" class="btn btn-primary btn-lg btn-block" onclick="connect();">连接</button>
	<button type="button" class="btn btn-primary btn-lg btn-block" onclick="disconnect();">断开</button>
	<input type="text" id="addr" value="/app/hello" style="width: 100%; height: 40px;" placeholder="请输入地址" />
	<textarea id="content" style="width: 100%; height: 100px;" placeholder="请输入内容"></textarea>
	<button type="button" class="btn btn-primary btn-lg btn-block" onclick="sendMsg();">发送</button>
	<button type="button" class="btn btn-primary btn-lg btn-block" onclick="sendSubscr();">发送订阅消息</button>
	<textarea id="receiveContent" style="width: 100%; height: 100px;"></textarea>
</body>
</html>