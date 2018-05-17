jQuery.extend({createUploadIframe:function(id,uri){
	// create frame
	var frameId='jUploadFrame'+id;
	var iframeHtml='<iframe id="'+frameId+'" name="'+frameId+'" style="position:absolute; top:-9999px; left:-9999px"';
	if(window.ActiveXObject){
		if(typeof uri=='boolean'){
			iframeHtml+=' src="'+'javascript:false'+'"';
		}else if(typeof uri=='string'){
			iframeHtml+=' src="'+uri+'"';
		}
	}
	iframeHtml+=' />';
	jQuery(iframeHtml).appendTo(document.body);
	return jQuery('#'+frameId).get(0);
},createUploadForm:function(id,fileElementId,data){
	// create form
	var formId='jUploadForm'+id;
	var fileId='jUploadFile'+id;
	var form=jQuery('<form action="" method="POST" name="'+formId+'" id="'+formId+'" enctype="multipart/form-data"></form>');
	if(data){
		for( var i in data){
			jQuery('<input type="hidden" name="'+i+'" value="'+data[i]+'" />').appendTo(form);
		}
	}
	var oldElement=jQuery('#'+fileElementId);
	var newElement=jQuery(oldElement).clone();
	jQuery(oldElement).attr('id',fileId);
	jQuery(oldElement).before(newElement);
	jQuery(oldElement).appendTo(form);
	// set attributes
	jQuery(form).css('position','absolute');
	jQuery(form).css('top','-1200px');
	jQuery(form).css('left','-1200px');
	jQuery(form).appendTo('body');
	return form;
},ajaxFileUpload:function(s){
	// introduce global settings, allowing the client to modify them for all requests, not only timeout
	s=jQuery.extend({},jQuery.ajaxSettings,s);
	var id=new Date().getTime()
	var form=jQuery.createUploadForm(id,s.fileElementId,(typeof (s.data)=='undefined'?false:s.data));
	var io=jQuery.createUploadIframe(id,s.secureuri);
	var frameId='jUploadFrame'+id;
	var formId='jUploadForm'+id;
	// Watch for a new set of requests
	if(s.global&&!jQuery.active++){
		jQuery.event.trigger("ajaxStart");
	}
	var requestDone=false;
	// Create the request object
	var xml={}
	if(s.global) jQuery.event.trigger("ajaxSend",[xml,s]);
	// Wait for a response to come back
	var uploadCallback=function(isTimeout){
		var io=document.getElementById(frameId);
		try{
			if(io.contentWindow){
				xml.responseText=io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
				xml.responseXML=io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
			}else if(io.contentDocument){
				xml.responseText=io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
				xml.responseXML=io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
			}
		}catch(e){
			jQuery.handleError(s,xml,null,e);
		}
		if(xml||isTimeout=="timeout"){
			requestDone=true;
			var status;
			try{
				status=isTimeout!="timeout"?"success":"error";
				// Make sure that the request was successful or notmodified
				if(status!="error"){
					// process the data (runs the xml through httpData regardless of callback)
					var data=jQuery.uploadHttpData(xml,s.dataType);
					// If a local callback was specified, fire it and pass it the data
					if(s.success) s.success(data,status);
					// Fire the global callback
					if(s.global) jQuery.event.trigger("ajaxSuccess",[xml,s]);
				}else jQuery.handleError(s,xml,status);
			}catch(e){
				status="error";
				// jQuery.handleError(s, xml, status, e);
			}
			// The request was completed
			if(s.global) jQuery.event.trigger("ajaxComplete",[xml,s]);
			// Handle the global AJAX counter
			if(s.global&&!--jQuery.active) jQuery.event.trigger("ajaxStop");
			// Process result
			if(s.complete) s.complete(xml,status);
			jQuery(io).unbind()
			setTimeout(function(){
				try{
					jQuery(io).remove();
					jQuery(form).remove();
				}catch(e){
					jQuery.handleError(s,xml,null,e);
				}
			},100)
			xml=null
		}
	}
	// Timeout checker
	if(s.timeout>0){
		setTimeout(function(){
			// Check to see if the request is still happening
			if(!requestDone) uploadCallback("timeout");
		},s.timeout);
	}
	try{
		var form=jQuery('#'+formId);
		jQuery(form).attr('action',s.url);
		jQuery(form).attr('method','POST');
		jQuery(form).attr('target',frameId);
		if(form.encoding){
			jQuery(form).attr('encoding','multipart/form-data');
		}else{
			jQuery(form).attr('enctype','multipart/form-data');
		}
		jQuery(form).submit();
	}catch(e){
		jQuery.handleError(s,xml,null,e);
	}
	jQuery('#'+frameId).load(uploadCallback);
	return {abort:function(){}};
},uploadHttpData:function(r,type){
	var data=!type;
	data=type=="xml"||data?r.responseXML:r.responseText;
	// If the type is "script", eval it in global context
	if(type=="script") jQuery.globalEval(data);
	// Get the JavaScript object, if JSON is used.
	if(type=="json"){
		showLog("上传图片返回结果:"+JSON.stringify(data));
		eval("data = "+data);
	}
	// evaluate scripts within html
	if(type=="html") jQuery("<div>").html(data).evalScripts();
	return data;
}});
// 上传图片
function fileUpload(fileElementId,sortId,pic,picUrl,uploadPicDir){
	var loadi=loading('正在上传图片...');
	// 开始上传
	$.ajaxFileUpload({url:"ajaxUploadPic?sortId="+sortId, // 需要链接到服务器地址
	secureuri:false,fileElementId:fileElementId, // 文件选择框的id属性
	dataType:'text', // 服务器返回的格式，可以是json
	success:function(data,status){
		// 去掉系统多余的字符串
		data=trimPicResponseText(data);
		data=toJson(data);
		layer.close(loadi);
		if(data.code===0){
			var picInfo=toJson(data.msg);
			var path=uploadPicDir+picInfo.picUrl;
			$("#"+pic).attr("src",path);
			$("#"+picUrl).val(picInfo.picUrl);
			showMsg("图片上传成功！");
		}else{
			showMsg(data.msg);
		}
	},error:function(data,status,e){
		// 去掉系统多余的字符串
		data=trimPicResponseText(data);
		data=toJson(data);
		showMsg(data.msg);
	}});
};
// 上传图片
function fileUploadCallback(fileElementId,sortId,uploadCallback){
	var loadi=loading('正在上传图片...');
	// 开始上传
	$.ajaxFileUpload({url:"ajaxUploadPic?sortId="+sortId, // 需要链接到服务器地址
	secureuri:false,fileElementId:fileElementId, // 文件选择框的id属性
	dataType:'text', // 服务器返回的格式，可以是json
	success:function(data,status){
		// 去掉系统多余的字符串
		data=trimPicResponseText(data);
		data=toJson(data);
		layer.close(loadi);
		if(uploadCallback!=null){
			uploadCallback(data);
		}
	},error:function(data,status,e){
		// 去掉系统多余的字符串
		data=trimPicResponseText(data);
		data=toJson(data);
		showMsg(data.msg);
	}});
};
