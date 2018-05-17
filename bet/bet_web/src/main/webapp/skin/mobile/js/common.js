LR_WEB.DATA_TABLES.DEFAULT_OPTION = { // DataTables初始化选项
    language:{"zeroRecords":"没有找到记录","info":"第 _PAGE_ 页 ( 总共 _PAGES_ 页，_TOTAL_条记录 )","infoEmpty":"无记录","infoFiltered":"(从 _MAX_ 条记录过滤)","emptyTable":"没有找到记录","infoPostFix":"",
        "loadingRecords":"正在加载数据...","processing":"正在查询","search":"搜索:","paginate":{"first":"首页","last":"末页","next":"下一页","previous":"上一页"},},"paginate":false,"bPaginate":true, // 翻页功能
    "bLengthChange":false, // 改变每页显示数据数量
    "bFilter":true, // 过滤功能
    "bSort":false, // 排序功能
    "bInfo":false,// 页脚信息
    "bAutoWidth":false,// 自动宽度
    "bJQueryUI":false, // 是否使用 jQury的UI theme
    "pagingType":"full","processing":false,"serverSide":true,"searching":false};
// 显示对话框
function showMsg(str){
	layer.open({content:str,
	// fixed : false,
	// offset : LR_WEB.DIALOG_TOP,
	btn:['确定']});
};
// 等待对话框
var loading = function(tips){
	// mobile版loading
	var loading = layer.open({type:2,content:tips});
	return loading;
};
// 判断是否为空
function checkNull(itemId,tips){
	var v = "#" + itemId;
	var value = $(v).val();
	if(isNull(value)){
		showMsg(tips);
		return false;
	}
	return true;
};
// 提交表单
var submitForm = function(formName,postUrl,showTips,tips,successFunc,failFunc){
	var v = "#" + formName;
	var formItem = $(v);
	var url = formItem.attr("action");
	if(!isNull(postUrl)){
		url = postUrl;
	}
	var index = null;
	if(showTips){
		index = loading(tips);
	}
	$.post(url,formItem.serialize(),function(data){
		if(showTips){
			layer.close(index);
		}
		if(data.code === LR_WEB.RETURN_CODE.SUCCESS){
			successFunc(data.msg);
		}else{
			if(failFunc != null){
				failFunc(data.msg);
			}else{
				showMsg(data.msg);
			}
		}
	});
	return false;
};
// 确定对话框
var confirmDialog = function(tips,url,params,successFunc,failFunc){
	layer.open({content:tips,btn:['是的','取消'],shadeClose:false,
	// offset : LR_WEB.DIALOG_TOP,
	yes:function(){
		ajaxJson(url,params,successFunc,failFunc,null);
	},no:function(){}});
};
var confirmDialogs = function(params){
	var btns = params.btns;
	if(btns == undefined){
		btns = ['是的','取消'];
	}
	layer.open({content:params.tips,btn:btns,shadeClose:false,yes:params.yes,no:params.no});
};
function showChooseMsg(title,content,btn1Str,btn2Str,btn1CallbackFunc,btn2CallbackFunc){
	// if (isNull(title)) {
	// title = "提示";
	// }
	if(isNull(btn1Str)){
		title = "确定";
	}
	if(isNull(btn2Str)){
		title = "取消";
	}
	if(isNull(title)){
		layer.open({content:content,btn:[btn1Str,btn2Str],shadeClose:false,yes:function(){
			if(btn1CallbackFunc != null){
				btn1CallbackFunc();
			}
			layer.closeAll();
		},no:function(){
			if(btn2CallbackFunc != null){
				btn2CallbackFunc();
			}
		}});
	}else{
		layer.open({title:title,content:content,btn:[btn1Str,btn2Str],shadeClose:false,yes:function(){
			if(btn1CallbackFunc != null){
				btn1CallbackFunc();
			}
			layer.closeAll();
		},no:function(){
			if(btn2CallbackFunc != null){
				btn2CallbackFunc();
			}
		}});
	}
};
function showSuccessMsg(title,msg,btn1Str,callbackFunc){
	if(isNull(title)){
		title = "提示";
	}
	if(isNull(btn1Str)){
		title = "确定";
	}
	layer.open({content:msg,btn:[btn1Str],shadeClose:false,yes:function(){
		if(callbackFunc != null){
			callbackFunc();
		}
		layer.closeAll();
	}});
};
var MenuInfo = {icon:"",title:"",link:"",event:null};
var addMenu = function(menuInfo){
	var info = "<li><a";
	if(!isNull(menuInfo.link)){
		info += " href=\"" + menuInfo.link + "\"";
	}
	if(!isNull(menuInfo.event)){
		info += " onclick=\"" + menuInfo.event + "\"";
	}
	info += ">";
	info += menuInfo.title;
	info += "</a></li>";
	$("#menu_id").append(info);
};
var addSeparator = function(){
	$("#menu_id").append("<li role=\"separator\" class=\"divider\"></li>");
};
function adjustCalendar(){
	var winWidth = document.documentElement.clientWidth;// 可见区域宽度
	var winHeight = document.documentElement.clientHeight;// 可见区域高度
	var jedatebox = $("#jedatebox");
	var width = jedatebox.css("width").replace("px","");
	var height = jedatebox.css("height").replace("px","");
	$("#jedatebox").css("position","fixed");
	$("#jedatebox").css("top",(winHeight - height) / 2 + "px");
	$("#jedatebox").css("left",(winWidth - width) / 2 + "px");
};