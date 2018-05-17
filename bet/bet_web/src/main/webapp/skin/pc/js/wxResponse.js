//选择文本回复
function selectResponseText() {
	ajaxDataJson("ajaxGetWxResponseTextList", "", function(data) {
		var list = data.data;
		var content = "<select id='responseId0'>";
		for ( var index in list) {
			content += "<option value ='" + list[index].responseId + "'>" + list[index].content + "</option>";
		}
		content += "</select>";
		showChooseMsg("请选择文本回复", content, '确定', '取消', function() {
			var responseId = $("#responseId0").val();
			$("#responseId").val(responseId);
			layer.closeAll();
		}, null);
	});
}
// 选择图文回复
function selectResponseNews() {
	ajaxDataJson("ajaxGetWxResponseNewsList", "", function(data) {
		var list = data.data;
		var content = "<select id='responseId0'>";
		for ( var index in list) {
			content += "<option value ='" + list[index].responseId + "'>" + list[index].title + "</option>";
		}
		content += "</select>";
		showChooseMsg("请选择图文回复", content, '确定', '取消', function() {
			var responseId = $("#responseId0").val();
			$("#responseId").val(responseId);
			layer.closeAll();
		}, null);
	});
}
// 选择多图文回复
function selectResponseMultiNews() {
	ajaxDataJson("ajaxGetWxResponseMultiNewsInfoList", "", function(data) {
		var list = data.data;
		var content = "<select id='responseId0'>";
		for ( var index in list) {
			content += "<option value ='" + list[index].responseId + "'>" + list[index].responseId + "</option>";
		}
		content += "</select>";
		showChooseMsg("请选择多图文回复", content, '确定', '取消', function() {
			var responseId = $("#responseId0").val();
			$("#responseId").val(responseId);
			layer.closeAll();
		}, null);
	});
}
// 调用回复
function getResponseId() {
	var type = $("#responseType").val();
	if (type == "text") {
		selectResponseText();
	}
	else if (type == "news") {
		selectResponseNews();
	}
	else if (type == "mutli_news") {
		selectResponseMultiNews();
	}
}