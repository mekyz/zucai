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
		window.location.href="ttree_m";
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
	<style>
@media ( max-width :768px) {
	.panel .panel-heading h6 {
		color: #fff;
	}
	.panel .panel-heading h6 {
		height: 20px; width: auto; display: inline-block; font-size: 14px; line-height: 25px; font-weight: 400; letter-spacing: 0;
	}
}
</style>
	<link rel="stylesheet" href="<%=betDir%>/Public/static/ztree/zTreeStyle/zTreeStyle.css" type="text/css">
	<div class="row">
		<div class="col-sm-12">
			<div class="panel panel-default bk-bg-white">
				<div class="panel-heading bk-bg-white">
					<h6>
						<i class="fa fa-indent red"></i>会员拓扑图
					</h6>
					<div class="panel-actions"></div>
				</div>
				<div class="panel-heading bk-bg-white bk-margin-10">
					<h6 class="pull-left">
						<b><font color="#ffffff">当前您共有1代会员</font></b>
					</h6>
					<div class="pull-left">
						<button class="btn btn-sm btn-info" style="margin: 10px 0 0 15px;">
							团队总人数：<span id="totalCount"></span>
						</button>
						<!-- <button class="btn btn-sm btn-success" style="margin: 10px 0 0 15px;">有效会员：0</button>
						<button class="btn btn-sm btn-danger" style="margin: 10px 0 0 15px;">未激活会员：1</button> -->
					</div>
					<div class="btn-group pull-right">
						<!-- <a class="btn btn-sm btn-info" href="ttree">返回根节点</a> -->
						<button class="btn btn-sm btn-info" id="expandBtn">全部展开</button>
						<button class="btn btn-sm btn-success" id="resetBtn">全部折叠</button>
					</div>
				</div>
				<div class="panel-body">
					<div class="dd">
						<ul style="width: 100%; overflow: auto;" class="ztree" id="tree">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=betDir%>/Public/static/ztree/zTreeJs/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=betDir%>/Public/static/ztree/zTreeJs/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=betDir%>/Public/static/ztree/zTreeJs/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript">
		var zTree;
		var demoMsg={async:"正在进行异步加载，请等一会儿再点击...",expandAllOver:"全部展开完毕",asyncAllOver:"后台异步加载完毕",asyncAll:"已经异步加载完毕，不再重新加载",expandAll:"已经异步加载完毕，使用 expandAll 方法"};
		var setting={async:{enable:true,url:"ajaxGetAllUserSubInfoList",autoParam:["userId"],dataFilter:filter,type:"userType"},data:{key:{name:"title",title:"title"}},view:{nameIsHTML:true,
		//dblClickExpand: false
		},callback:{beforeAsync:beforeAsync,onAsyncSuccess:onAsyncSuccess,onAsyncError:onAsyncError,
		//onClick: onClick
		}};
		//3
		function filter(treeId,parentNode,childNodes){
			if(!childNodes){
				return null;
			}
			for(var i=0,l=childNodes.length;i<l;i++){
				var userId=childNodes[i].userId;
				childNodes[i].icon="<%=betDir%>/Public/static/ztree/zTreeStyle/img/diy/pop.png";
				childNodes[i].target="_self";
				if(childNodes[i].activeStatus==1){
					childNodes[i].name=childNodes[i].name+"<span style='color:red'>[未激活]</span>";
					childNodes[i].name="请激活";
				}else{
					childNodes[i].number=childNodes[i].number+"["+childNodes[i].name+"]";
					if(userId!='${userInfo.userId}'){
						childNodes[i].number=childNodes[i].number+"<span style='color:green;'>[第"+(childNodes[i].depth)+"代]</span><span style='color:green;'>[第"+(i+1)+"个会员]</span>";
					}else{
						childNodes[i].number=childNodes[i].number+"<span style='color:green;'>[第"+(childNodes[i].depth)+"代]</span><span style='color:green;'>[第"+0+"个会员]</span>";
					}
				}
			}
			return childNodes;
		}
		//2
		function beforeAsync(){
			curAsyncCount++;
		}
		function onClick(e,treeId,treeNode){
			var zTree=$.fn.zTree.getZTreeObj("tree");
			zTree.expandNode(treeNode);
		}
		//4
		function onAsyncSuccess(event,treeId,treeNode,msg){
			curAsyncCount--;
			if(curStatus=="expand"){
				//alert('1');
				expandNodes(treeNode.children);
			}else if(curStatus=="async"){
				asyncNodes(treeNode.children);
			}
			if(curAsyncCount<=0){
				//alert('2');
				if(curStatus!="init"&&curStatus!=""){
					$("#demoMsg").text((curStatus=="expand")?demoMsg.expandAllOver:demoMsg.asyncAllOver);
					asyncForAll=true;
				}
				curStatus="";
			}
		}
		function onAsyncError(event,treeId,treeNode,XMLHttpRequest,textStatus,errorThrown){
			alert('onAsyncError');
			curAsyncCount--;
			if(curAsyncCount<=0){
				curStatus="";
				if(treeNode!=null) asyncForAll=true;
			}
		}
		var curStatus="init",curAsyncCount=0,asyncForAll=false,goAsync=false;
		function expandNodes(nodes){
			if(!nodes) return;
			curStatus="expand";
			var zTree=$.fn.zTree.getZTreeObj("tree");
			for(var i=0,l=nodes.length;i<l;i++){
				zTree.expandNode(nodes[i],true,false,false);
				if(nodes[i].isParent&&nodes[i].zAsync){
					expandNodes(nodes[i].children);
				}else{
					goAsync=true;
				}
			}
		}
		function asyncAll(){
			// alert('asyncAll');
			if(!check()){
				return;
			}
			var zTree=$.fn.zTree.getZTreeObj("tree");
			if(asyncForAll){
				$("#demoMsg").text(demoMsg.asyncAll);
			}else{
				asyncNodes(zTree.getNodes());
				if(!goAsync){
					$("#demoMsg").text(demoMsg.asyncAll);
					curStatus="";
				}
			}
		}
		function asyncNodes(nodes){
			// alert('asyncNodes');
			if(!nodes) return;
			curStatus="async";
			var zTree=$.fn.zTree.getZTreeObj("tree");
			for(var i=0,l=nodes.length;i<l;i++){
				if(nodes[i].isParent&&nodes[i].zAsync){
					asyncNodes(nodes[i].children);
				}else{
					goAsync=true;
					zTree.reAsyncChildNodes(nodes[i],"refresh",true);
				}
			}
		}
		function reset(){
			//alert('reset');
			if(!check()){
				return;
			}
			asyncForAll=false;
			goAsync=false;
			$("#demoMsg").text("");
			$.fn.zTree.init($("#tree"),setting);
		}
		function check(){
			//alert('check');
			if(curAsyncCount>0){
				$("#demoMsg").text(demoMsg.async);
				return false;
			}
			return true;
		}
		function expandNode(){
			var zTree=$.fn.zTree.getZTreeObj("tree"),nodes=zTree.getSelectedNodes();
			zTree.expandAll(true);
			/* 
			type=e.data.type,
			if(type.indexOf("All")<0&&nodes.length==0){
				alert("请先选择一个父节点");
			}
			if(type=="expandAll"){
				zTree.expandAll(true);
			}else if(type=="collapseAll"){
				zTree.expandAll(false);
			}else{
				var callbackFlag=$("#callbackTrigger").attr("checked");
				for(var i=0,l=nodes.length;i<l;i++){
					zTree.setting.view.fontCss={};
					if(type=="expand"){
						zTree.expandNode(nodes[i],true,null,null,callbackFlag);
					}else if(type=="collapse"){
						zTree.expandNode(nodes[i],false,null,null,callbackFlag);
					}else if(type=="toggle"){
						zTree.expandNode(nodes[i],null,null,null,callbackFlag);
					}else if(type=="expandSon"){
						zTree.expandNode(nodes[i],true,true,null,callbackFlag);
					}else if(type=="collapseSon"){
						zTree.expandNode(nodes[i],false,true,null,callbackFlag);
					}
				}
			} */
		}
		$(function(){
			$.fn.zTree.init($("#tree"),setting);
			$("#resetBtn").bind("click",reset);
			$("#expandBtn").bind("click",{type:"collapseAll"},expandNode);
		});
		$(function(){
			ajaxDataJson("ajaxGetAllUserSubInfoListCount","",function(res){
				var count=res;
				$("#totalCount").html(count);
			},function(errmsg){});
		});
	</script>
</body>
</html>