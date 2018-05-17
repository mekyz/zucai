LR_WEB.DATA_TABLES.DEFAULT_OPTION={ // DataTables初始化选项
	language:{
		"lengthMenu":"每页 <select><option value='10'>10</option><option value='50'>50</option><option value='100'>100</option><option value='200'>200</option><option value='300'>300</option><option value='500'>500</option></select> 条记录",
		"zeroRecords":"没有找到记录","info":"第 _PAGE_ 页 ( 总共 _PAGES_ 页，_TOTAL_条记录 )","infoEmpty":"无记录","infoFiltered":"(从 _MAX_ 条记录过滤)","emptyTable":"没有找到记录","infoPostFix":"","loadingRecords":"正在加载数据...",
		"processing":"正在查询","search":"搜索:","paginate":{"first":"首页","last":"末页","next":"下一页","previous":"上一页"},},"paginate":true,"bPaginate":true, // 翻页功能
	"bLengthChange":true, // 改变每页显示数据数量
	"bFilter":true, // 过滤功能
	"bSort":true, // 排序功能
	"bInfo":true,// 页脚信息
	"bAutoWidth":false,// 自动宽度
	"bJQueryUI":false, // 是否使用 jQury的UI theme
	"pagingType":"simple_numbers","processing":true,"serverSide":true,"searching":true};
// 显示对话框
function showMsg(str){
	layer.alert(str,{title:"提示",
	// offset : LR_WEB.DIALOG_TOP
	});
};
// 等待对话框
var loading=function(tips){
	// PC版loading
	var index=layer.load(0);
	return index;
};
// 判断是否为空
function checkNull(itemId,tips){
	var v="#"+itemId;
	var value=$(v).val();
	if(isNull(value)){
		showMsg(tips);
		// alert(tips);
		return false;
	}
	return true;
};
// 提交表单
var submitForm=function(formName,postUrl,showTips,tips,successFunc,failFunc){
	var v="#"+formName;
	var formItem=$(v);
	var url=formItem.attr("action");
	if(!isNull(postUrl)){
		url=postUrl;
	}
	var index=null;
	if(showTips){
		index=loading(tips);
	}
	$.post(url,formItem.serialize(),function(data){
		if(showTips){
			layer.close(index);
		}
		if(data.code===LR_WEB.RETURN_CODE.SUCCESS){
			successFunc(data.msg);
		}else{
			if(failFunc!=null){
				failFunc(data.msg);
			}else{
				showMsg(data.msg);
			}
		}
	});
	return false;
}
// 确定对话框
var confirmDialog=function(tips,url,params,successFunc,failFunc){
	layer.open({content:tips,btn:['是的','取消'],shadeClose:false,
	// fixed : false,
	// offset : LR_WEB.DIALOG_TOP,
	yes:function(){
		ajaxJson(url,params,successFunc,failFunc);
	},no:function(){}});
}
function ajaxTipJson(url,params,showTips,tips,callbackSuccessFunc,callbackFailFunc){
	var index=null;
	if(showTips){
		index=loading(tips);
	}
	ajaxJson(url,params,function(data){
		if(showTips){
			layer.close(index);
		}
		if(callbackSuccessFunc!=null){
			callbackSuccessFunc(data);
		}
	},function(msg){
		if(showTips){
			layer.close(index);
		}
		if(callbackFailFunc!=null){
			callbackFailFunc(msg);
		}
	});
};
function showChooseMsg(title,content,btn1Str,btn2Str,btn1CallbackFunc,btn2CallbackFunc){
	if(isNull(title)){
		title="提示";
	}
	if(isNull(btn1Str)){
		title="确定";
	}
	if(isNull(btn2Str)){
		title="取消";
	}
	layer.open({title:title,content:content,btn:[btn1Str,btn2Str],shadeClose:false,
	yes:function(){
		if(btn1CallbackFunc!=null){
			btn1CallbackFunc();
		}
		layer.closeAll();
	},no:function(){
		if(btn2CallbackFunc!=null){
			btn2CallbackFunc();
		}
	}});
};
function showSuccessMsg(title,msg,callbackFunc){
	if(isNull(title)){
		title="提示";
	}
	layer.alert(msg,{title:title,},function(index){
		if(callbackFunc!=null){
			callbackFunc();
		}
		layer.close(index);
	});
};