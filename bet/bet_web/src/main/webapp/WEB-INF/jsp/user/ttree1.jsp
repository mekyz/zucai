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
		var parentId='';
		var setting={view:{selectedMulti:false},async:{enable:true,url:"ajaxGetUserSubInfoList1?userId="+parentId,autoParam:["userId","name"],dataFilter:filter},
			callback:{beforeClick:beforeClick,beforeAsync:beforeAsync,onAsyncError:onAsyncError,onAsyncSuccess:onAsyncSuccess}};
		function filter(treeId,parentNode,childNodes){
			if(!childNodes){
				return null;
			}
			var len=childNodes.length;
			for(var i=0;i<len;i++){
				var child=childNodes[i];
				child.icon="<%=betDir%>/Public/static/ztree/zTreeStyle/img/diy/pop.png";
				child.target="_self";
				var userId=child.userId;
				if(userId!='${userInfo.userId}'){
					child.name=child.userId+"[第"+(i+1)+"个会员]";
				}else{
					child.name=child.userId+"[第"+0+"个会员]";
				}
				if(child.activeStatus==1){
					child.name=child.name+"[未激活]";
				}
			}
			return childNodes;
		}
		function beforeClick(treeId,treeNode){
			parentId=treeNode.child;
			return true;
			/* if(!treeNode.isParent){
				alert("请选择父节点");
				return false;
			}else{
				return true;
			} */
		}
		var log,className="dark";
		function beforeAsync(treeId,treeNode){
			className=(className==="dark"?"":"dark");
			showLog("[ beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;"+((!!treeNode&&!!treeNode.name)?treeNode.name:"root"));
			return true;
		}
		function onAsyncError(event,treeId,treeNode,XMLHttpRequest,textStatus,errorThrown){
			showLog("[ onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;"+((!!treeNode&&!!treeNode.name)?treeNode.name:"root"));
		}
		function onAsyncSuccess(event,treeId,treeNode,msg){
			showLog("[ onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;"+((!!treeNode&&!!treeNode.name)?treeNode.name:"root"));
		}
		function showLog(str){
			if(!log) log=$("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length>8){
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function refreshNode(e){
			var zTree=$.fn.zTree.getZTreeObj("tree"),type=e.data.type,silent=e.data.silent,nodes=zTree.getSelectedNodes();
			if(nodes.length==0){
				alert("请先选择一个父节点");
			}
			for(var i=0,l=nodes.length;i<l;i++){
				zTree.reAsyncChildNodes(nodes[i],type,silent);
				if(!silent) zTree.selectNode(nodes[i]);
			}
		}
		$(function(){
			$.fn.zTree.init($("#tree"),setting);
			//$("#resetBtn").bind("click",reset);
			//$("#expandBtn").bind("click",{type:"collapseAll"},expandNode);
			ajaxDataJson("ajaxGetAllUserSubInfoListCount","",function(res){
				var count=res;
				$("#totalCount").html(count);
			},function(errmsg){});
		});
	</script>
</body>
</html>