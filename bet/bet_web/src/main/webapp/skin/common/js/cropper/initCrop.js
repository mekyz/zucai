window.onload=function(){
	'use strict';
	var screenWidth=$(window).width();
	var screenHeight=$(window).height();
	var Cropper=window.Cropper;
	var image=document.getElementById(initCropParams.imgContainerId);
	var isUndefined=function(obj){
		return typeof obj==='undefined';
	};
	var options={minContainerHeight:screenHeight,minContainerWidth:screenWidth,aspectRatio:initCropParams.aspectRatio,// 裁剪框比例 1：1
	viewMode:1,// 显示
	guides:false,// 裁剪框虚线 默认true有
	dragMode:"move",build:function(e){ // 加载开始
	// 可以放你的过渡效果
	},built:function(e){ // 加载完成
		$('#'+initCropParams.divContainerId).show();
	},zoom:function(e){},background:true,// 容器是否显示网格背景
	movable:true,// 是否能移动图片
	cropBoxMovable:false,// 是否允许拖动裁剪框
	cropBoxResizable:true,// 是否允许拖动 改变裁剪框大小
	};
	var cropper=new Cropper(image,options);
	function preventDefault(e){
		if(e){
			if(e.preventDefault){
				e.preventDefault();
			}else{
				e.returnValue=false;
			}
		}
	}
	if(typeof document.createElement('cropper').style.transition==='undefined'){
		$('button[data-method="rotate"]').prop('disabled',true);
		$('button[data-method="scale"]').prop('disabled',true);
	}
	// Methods
	document.getElementById(initCropParams.actionsId).onclick=function(event){
		var e=event||window.event;
		var target=e.target||e.srcElement;
		var result;
		var input;
		var data;
		if(!cropper){
			return;
		}
		while(target!==this){
			if(target.getAttribute('data-method')){
				break;
			}
			target=target.parentNode;
		}
		if(target===this||target.disabled||target.className.indexOf('disabled')>-1){
			return;
		}
		data={method:target.getAttribute('data-method'),target:target.getAttribute('data-target'),option:target.getAttribute('data-option'),secondOption:target.getAttribute('data-second-option')};
		if(data.method){
			if(typeof data.target!=='undefined'){
				input=document.querySelector(data.target);
				if(!target.hasAttribute('data-option')&&data.target&&input){
					try{
						data.option=JSON.parse(input.value);
					}catch(e){}
				}
			}
			if(data.method==='getCroppedCanvas'){
				// data.option = JSON.parse(data.option);
				data.option=initCropParams.imgSize;
			}
			result=cropper[data.method](data.option,data.secondOption);
			switch(data.method){
				case 'scaleX':
				case 'scaleY':
					target.setAttribute('data-option',-data.option);
					break;
				case 'getCroppedCanvas':
					if(result){
						// var quality = initCropParams.quality;
						// if(typeof (quality) == "undefined"){
						// quality = 0.4;
						// }
						var quality=1;
						fileImg=result.toDataURL('image/jpg',quality);
						$('#'+initCropParams.showImgId).attr("src",fileImg).show();
						uploadPicForm();
					}
					break;
				case 'destroy':
					$("#"+initCropParams.inputImageId).val("");
					$('#'+initCropParams.divContainerId).hide();
					break;
			}
			if(typeof result==='object'&&result!==cropper&&input){
				try{
					input.value=JSON.stringify(result);
				}catch(e){}
			}
		}
	};
	// Import image
	var inputImage=document.getElementById(initCropParams.inputImageId);
	var URL=window.URL||window.webkitURL;
	var blobURL;
	if(URL){
		inputImage.onchange=function(){
			var files=this.files;
			var file;
			if(cropper&&files&&files.length){
				file=files[0];
				if(/^image\/\w+/.test(file.type)){
					blobURL=URL.createObjectURL(file);
					cropper.reset().replace(blobURL);
				}else{
					window.alert('请选择图片.');
				}
			}
			$(inputImage).find("img").hide();
		};
	}else{
		inputImage.disabled=true;
		inputImage.parentNode.className+=' disabled';
	}
};