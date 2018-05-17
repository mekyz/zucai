//表格管理变量
var tableManage = {fuzzySearch:true,getQueryCondition:function(data){
	var param = data;
	// 组装查询参数
	param.fuzzySearch = tableManage.fuzzySearch;
	if(tableManage.fuzzySearch){
		param.search.value = $("#fuzzy-search").val();
	}else{
		param = initQueryParams(param);
	}
	return param;
}};
function initQueryParams(param){}
// 初始化表格按钮
function initTableBtns(_table){
	$("#btn-simple-search").click(function(){
		tableManage.fuzzySearch = true;
		_table.draw();
	});
	$("#btn-advanced-search").click(function(){
		tableManage.fuzzySearch = false;
		_table.draw();
	});
	$("#toggle-advanced-search").click(function(){
		$("i",this).toggleClass("fa-angle-double-down fa-angle-double-up");
		$("#div-advanced-search").slideToggle("fast");
	});
	$('.dataTables_filter').css('display','none');
	$(".dataTables_length").css('padding-left','15px');
	$(".dataTables_length").css('padding-right','15px');
	$(".dataTables_info").css('padding-left','15px');
	$(".dataTables_info").css('padding-right','15px');
}
// 获取服务器数据
function getDataFromServer(url,data,callback,settings,successFunc){
	var param = tableManage.getQueryCondition(data);
	$.ajax({type:"POST",url:url,cache:false, // 禁用缓存
	data:param, // 传入已封装的参数
	dataType:"json",success:function(result){
		successFunc(result);
		callback(result);
	},error:function(XMLHttpRequest,textStatus,errorThrown){
		showMsg("查询失败");
	}});
};