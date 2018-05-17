function initTable(getMoreData){
	window.onscroll=function(){
		var a=document.body.scrollHeight;
		var b=document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
		var d=document.documentElement.clientHeight;
		if(a-b-d<=1){
			getMoreData();
		}
	}
};
function more(hasMore,url,length,preParseData,noData,objData,listItem,listItemAttr,divMoreItem){
	if(!hasMore){
		return;
	}
	listItem="#"+listItem;
	var pageindex=parseInt($(listItem).attr(listItemAttr));
	if(isNaN(pageindex)){
		hasMore=false;
		return;
	}else{
		$.ajax({type:"POST",url:url,data:{start:pageindex*length,length:length},success:function(data){
			if(preParseData!=null&&preParseData!=undefined){
				preParseData(pageindex,data,length,noData,objData,listItem,listItemAttr,divMoreItem);
			}
			parseData(pageindex,data,length,noData,objData,listItem,listItemAttr,divMoreItem);
			if(pageindex<1){
				CloseLoading();
			}
		},beforeSend:function(XMLHttpRequest){
			if(pageindex<1){
				Loading("正在加载...");
			}
			$("#loading_p").show();
		}});
	}
};
function parseData(pageindex,data,length,noData,objData,listItem,listItemAttr,divMoreItem){
	divMoreItem="#"+divMoreItem;
	var list=data.data;
	var content="";
	if(isNull(list)||list.length<1){
		hasMore=false;
		$(divMoreItem).hide();
		if(pageindex==0){
			content=noData();
			$(listItem).html(content);
		}
	}else{
		if(list.length>=length){
			hasMore=true;
			$(divMoreItem).show();
		}else{
			hasMore=false;
			$(divMoreItem).hide();
		}
		$(listItem).attr(listItemAttr,(pageindex+1));
		for( var i in list){
			content+=objData(list[i]);
		}
		$(listItem).append(content);
	}
};
// function noData() {
// return '<div class="Noorder"><img src="<%=commonImgPath%>/notavailable.png" width="16%"><p class="greys mt20">暂时没有业务员哦</p></div>';
// }
// function data(myObj) {
// return '<div class="item" onclick="goUrl(\'user?userId=' + myObj.userId + '\');"><div class="iteminner"><p class="greys mt20">用户账号：' + myObj.userId
// + '</p></div></div>';
// }
