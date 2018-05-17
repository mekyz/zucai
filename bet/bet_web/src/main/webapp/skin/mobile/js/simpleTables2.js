var vueApp;
function initData(app){
	vueApp=app;
}
function loadingTip(){
	// Loading("正在加载...");
	//layer.open({type:2,content:'正在加载...'});
}
function closeLoadingTip(){
	// CloseLoading();
	//layer.closeAll();
}
function getDataList(url,objData,afterParseData){
	if(vueApp.isLoading){
		return;
	}
	var pageIndex=vueApp.currentPage;
	if(isNaN(pageIndex)){
		return;
	}else{
		vueApp.isLoading=true;
		$.ajax({type:"POST",url:url,data:{start:(pageIndex-1)*vueApp.LIST_LEN,length:vueApp.LIST_LEN},success:function(data){
			closeLoadingTip();
			var list=data.data;
			if(isNull(list)||list.length<1){
				if(pageIndex<=1){
					$("#"+vueApp.divNoData).show();
					$("#"+vueApp.divPageFooter).hide();
					vueApp.totalRecords=0;
					vueApp.totalPage=0;
					vueApp.currentPage=1;
				}
			}else{
				$("#"+vueApp.divNoData).hide();
				$("#"+vueApp.divPageFooter).show();
				vueApp.totalRecords=data.recordsTotal;
				vueApp.totalPage=parseInt(data.recordsTotal/vueApp.LIST_LEN)+(data.recordsTotal%vueApp.LIST_LEN>0?1:0);
				for( var i in list){
					objData(list[i]);
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
function goFirstPage(){
	if(vueApp.isLoading){
		return;
	}
	vueApp.currentPage=1;
	getMoreData();
}
function goPrePage(){
	if(vueApp.isLoading){
		return;
	}
	var pageIndex=vueApp.currentPage;
	if(pageIndex>1){
		pageIndex=pageIndex-1;
	}else{
		pageIndex=1;
	}
	vueApp.currentPage=pageIndex;
	getMoreData();
}
function goNextPage(){
	if(vueApp.isLoading){
		return;
	}
	var pageIndex=vueApp.currentPage;
	var pageTotal=vueApp.totalPage;
	if(pageIndex<pageTotal){
		pageIndex=pageIndex+1;
	}else{
		pageIndex=pageTotal;
	}
	vueApp.currentPage=pageIndex;
	getMoreData();
}
function goLastPage(){
	if(vueApp.isLoading){
		return;
	}
	vueApp.currentPage=vueApp.totalPage;
	getMoreData();
}