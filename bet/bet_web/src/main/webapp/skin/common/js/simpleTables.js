var vueApp;
function initData(app){
	vueApp=app;
}
function loadingTip(){
// Loading("正在加载...");
// layer.open({type:2,content:'正在加载...'});
}
function closeLoadingTip(){
// CloseLoading();
// layer.closeAll();
}
function getDataList(url,objData,parseObjData){
	if(vueApp.isLoading){
		return;
	}
	var pageIndex=objData.currentPage;
	if(isNaN(pageIndex)){
		return;
	}else{
		vueApp.isLoading=true;
		var len=objData.LIST_LEN;
		$.ajax({type:"POST",url:url,data:{start:(pageIndex-1)*len,length:len},success:function(data){
			closeLoadingTip();
			var list=data.data;
			if(isNull(list)||list.length<1){
				if(pageIndex<=1){
					$("#"+objData.divNoData).show();
					$("#"+objData.divData).hide();
					objData.totalRecords=0;
					objData.totalPage=0;
					objData.currentPage=1;
				}
			}else{
				$("#"+objData.divNoData).hide();
				$("#"+objData.divData).show();
				objData.totalRecords=data.recordsTotal;
				objData.totalPage=parseInt(data.recordsTotal/objData.LIST_LEN)+(data.recordsTotal%objData.LIST_LEN>0?1:0);
				for( var i in list){
					parseObjData(list[i]);
				}
			}
			vueApp.isLoading=false;
		},beforeSend:function(XMLHttpRequest){
			loadingTip();
		},error:function(returndata){
			closeLoadingTip();
		}});
	}
}
function goFirstPage(objData){
	if(vueApp.isLoading){
		return;
	}
	objData.currentPage=1;
	getMoreData();
}
function goPrePage(objData){
	if(vueApp.isLoading){
		return;
	}
	var needUpdate=false;
	var pageIndex=objData.currentPage;
	if(pageIndex>1){
		pageIndex=pageIndex-1;
		needUpdate=true;
	}else{
		pageIndex=1;
	}
	objData.currentPage=pageIndex;
	getMoreData();
}
function goNextPage(objData){
	if(vueApp.isLoading){
		return;
	}
	var pageIndex=objData.currentPage;
	var pageTotal=objData.totalPage;
	if(pageIndex<pageTotal){
		pageIndex=pageIndex+1;
	}else{
		pageIndex=pageTotal;
	}
	objData.currentPage=pageIndex;
	getMoreData();
}
function goLastPage(objData){
	if(vueApp.isLoading){
		return;
	}
	objData.currentPage=objData.totalPage;
	getMoreData();
}
function goPage(objData,input){
	if(vueApp.isLoading){
		return;
	}
	var pageIndex=$(input).val();
	var pageTotal=objData.totalPage;
	if(pageIndex<pageTotal&&pageIndex>0){
		pageIndex=pageIndex+1;
		objData.currentPage=pageIndex;
		getMoreData();
	}
}