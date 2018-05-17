/*所有加了 J_alink 类名的链接都不跳转
*a 标签class传入 J_alink  样式名会自动调用这个方法
*例如更新数据状态 只在当前页面刷新而不跳转
*/

if($('a.J_alink').length){
	$('a.J_alink').on('click',function(e){
		e.preventDefault();
		var $_this = this,
			$this = $($_this),
			href = $this.prop('href')
			var index = layer.msg('正在提交，请耐心等待...', {
				  icon: 16
				  ,shade: 0.01
				});
		$.getJSON(href).done(function (data) {
				if(data.status) {  
					if(data.url) {
						layer.close(index);
						layer.msg(data.info+"....",{icon:1},function(){
							location.href = data.url;
						});
					}else{
						layer.close(index);
						layer.msg(data.info+"....",{icon:1,time:1000},function(){
							reloadPage(window);
						});
					}
				} else {  
					layer.close(index);
					layer.alert(data.info,{title:'信息提示',icon:0}, function(){
						 window.location.reload();
					});
				}  
		});
	})
}

if($('a.J_alinkaa').length){
	$('a.J_alinkaa').on('click',function(e){
		e.preventDefault();
		var $_this = this,
			$this = $($_this),
			href = $this.prop('href')
			var index = layer.msg('正在提交，请耐心等待...', {
				  icon: 16
				  ,shade: 0.01
				});
		$.getJSON(href).done(function (data) {
				if(data.status) {  
					if(data.url) {
						layer.close(index);
						//layer.msg(""+data.info+"....",{icon:1},function(){
							location.href = data.url;
						//});
					}else{
						layer.close(index);
						layer.msg(""+data.info+"....",{icon:1,time:1000},function(){
							reloadPage(window);
						});
					}
				} else {  
					layer.close(index);
					layer.alert(data.info,{title:'信息提示',icon:0}, function(){
						 window.location.reload();
					});
				}  
		});
	})
}

/*
* 新增tab
 */
if($('a.J_menuItem').length){
    $('a.J_menuItem').on('click',function(e){
        e.preventDefault();
        var $_this = this,
            $this = $($_this),
			title =$this.text(),
            href = $this.prop('href'),
        	jq = top.jQuery;
        var p = '<a href="javascript:;" class="active J_menuTab" data-id="'+href+'">' + title + ' <i class="fa fa-times-circle"></i></a>';
        jq(".J_menuTab").removeClass("active");
        var n = '<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="' + href + '" frameborder="0" data-id="' + href + '" seamless></iframe>';
        jq(".J_mainContent").find("iframe.J_iframe").hide().parents(".J_mainContent").append(n);
        jq(".J_menuTabs .page-tabs-content").append(p);

    })
}


/*所有的删除操作，删除数据后刷新页面
*a 标签class传入 J_del  样式名会自动调用这个方法
*例如删除数据，会弹出是否删除，并在当前页面刷新
*/
if($('a.J_del').length){
	$('a.J_del').on('click',function(e){
		e.preventDefault();
		var $_this = this,
			$this = $($_this),
			href = $this.prop('href'),
			msg = $this.data('msg');
			title=$this.data('title');
			if(title=='' || title==undefined){
				title='';	
			}
		if(msg == '' || msg == undefined){
			msg = '确定要删除吗？'+title+'';
		}
		if($this.parent().parent().parent().parent().parent().next().length==0){
			var no_l=0;
		}else{
			var no_l=1;
		};
		layer.alert(msg,{
			time: 0,btn: ['确定', '取消'],
			title:'删除提示！',
			yes: function(index){
				layer.close(index);
				$.getJSON(href).done(function (data) {
					if(data.status) {  
						if(data.url) {
							layer.msg(""+data.info+"....",{icon:1,time:2000},function(){
								location.href = data.url;
							});
							
						}else{
							layer.msg(""+data.info+"....",{icon:1,time:2000},function(){
								if(no_l==0){
									reloadPage(window)
								};
							});
						}
					} else {  
						layer.alert(data.info,{title:'温馨提示',icon:0});
					}  
			  });
			}
		});
	})
} 
/*form表单提交方法
*使用异步验证提交  validate
*要使用这个方法必须传入 common.js   以及 validate目录下的 validate.js   和  messages.js
*<script src="$public/js/validate/validate.js"></script> 
*<script src="$public/js/validate/messages.js"></script> 
*<script src="$public/js/common.js"></script>
*form 表单 id="commentForm"
*表单内的文本框这些如果必须填写必须传入 required aria-required="true"
*表单提交按钮使用 type="submit"
*示例：
*<form class="form-horizontal m-t" action="" method="post" id="commentForm">
*<input type="text" name="catname"  value="" required aria-required="true">
*<button class="btn btn-primary" type="submit">保存</button>
*</form>
*提交系统后采用  I('post.') 方法获取或者 $_POST 方法获取
*返回成功提示用  $this->success("成功",'Index');  提示：返回提示和要跳转的连接，如果连接处填写  close  表示关闭弹窗（如果用弹窗打开当前表单页面），如果连接第二个参数不存在就直接刷新当前页面
*返回失败提示用 $this->error("错误");
*/
if($('#commentForm').length) {
	layui.use('layer', function(){
    $("#commentForm").validate({
        submitHandler: function () {
            var data = $("#commentForm").serialize();
            var data_id = $(".btn-info").attr('data-id');
            var url = $("#commentForm").attr('action');
              $("#commentForm").find('button[target-form]').attr("disabled", true);   
              var index = layer.msg('正在提交，请耐心等待...', {icon: 16 ,shade: 0.01});
            $.ajax({
                url: url,
                type: "post",
                dataType: "json",
                data: data,
                success: function (res) {
                	//alert(res.status);
                    if (res.status) {
                        if (res.url) {
                            //关闭弹窗
                            if (res.url == 'close') {
                            	layer.close(index);
                                layer.msg("" + res.info + "....", {icon: 1, time: 2000}, function () {
                                    //如果是弹窗打开的页面
                                    window.parent.location.reload();
                                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                    parent.layer.close(index);
                                });
                            } else {
                            	layer.close(index);
                                layer.confirm("" + res.info + "....", {
                                    btn: ['继续操作', '关闭页面'],
                                    yes: function () {
                                        reloadPage(window);
                                    },
                                    btn2: function () {
                                    	if(data_id == '1'){
                                    		parent.layer.closeAll();
                                    		parent.window.location.reload();
                                    	}else{
                                    		location.href = res.url;
                                    	}
                                    }
                                });
                            }
                        } else {
                        	layer.close(index);
                            layer.msg("" + res.info + "....", {icon: 1, time: 1000}, function () {
                                reloadPage(window);
                            });
                        }
                    } else {
                    	layer.close(index);
                        layer.alert(res.info, {title: '温馨提示', icon: 0});
                        $("#commentForm").find('button[target-form]').attr("disabled", false);
                    }
                }
            });
        }
    });
});
}

if($('#commentForm1').length) {
	layui.use('layer', function(){
    $("#commentForm1").validate({
        submitHandler: function () {
            var data = $("#commentForm1").serialize();
            var url = $("#commentForm1").attr('action');
              $("#commentForm1").find('button[target-form]').attr("disabled", true);   
              var index = layer.msg('正在提交，请耐心等待...', {icon: 16 ,shade: 0.01});
            $.ajax({
                url: url,
                type: "post",
                dataType: "json",
                data: data,
                success: function (res) {
                    if (res.status) {
                        if (res.url) {
                            //关闭弹窗
                            if (res.url == 'close') {
                            	layer.close(index);
                                layer.msg("" + res.info + "....", {icon: 1, time: 2000}, function () {
                                    //如果是弹窗打开的页面
                                    window.parent.location.reload();
                                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                    parent.layer.close(index);
                                });
                            } else {
                            	layer.close(index);
                                layer.confirm("" + res.info + "....", {
                                    btn: ['继续操作', '关闭页面'],
                                    yes: function () {
                                        reloadPage(window);
                                    },
                                    btn2: function () {
                                        parent.layer.closeAll();
                                    }
                                });
                            }
                        } else {
                        	layer.close(index);
                            layer.msg("" + res.info + "....", {icon: 1, time: 1000}, function () {
                                reloadPage(window);
                            });
                        }
                    } else {
                    	layer.close(index);
                        layer.alert(res.info, {title: '温馨提示', icon: 0});
                        $("#commentForm1").find('button[target-form]').attr("disabled", false);
                    }
                }
            });
        }
    });
});
}

//不支持placeholder浏览器下对placeholder进行处理
if (document.createElement('input').placeholder !== '') {
	$('[placeholder]').focus(function () {
		var input = $(this);
		if (input.val() == input.attr('placeholder')) {
			input.val('');
			input.removeClass('placeholder');
		}
	}).blur(function () {
		var input = $(this);
		if (input.val() == '' || input.val() == input.attr('placeholder')) {
			input.addClass('placeholder');
			input.val(input.attr('placeholder'));
		}
	}).blur().parents('form').submit(function () {
		$(this).find('[placeholder]').each(function () {
			var input = $(this);
			if (input.val() == input.attr('placeholder')) {
				input.val('');
			}
		});
	});
}
//复选框全选
 $("#SelectAll").click(
	function(){
		if(this.checked){
			$("input[name='checkname']").each(function(){this.checked=true;});
		}else{
			$("input[name='checkname']").each(function(){this.checked=false;});
		}
	}
);	
$(".filetext").dblclick(function(){
	var img=$(this).val();
	if(!img){
		return false;	
	}
	layer.open({
	  type: 1,
	  title: "图片查看",
	  shadeClose:true,
	  area: ['100%', '100%'], //宽高
	  content: '<img src="'+img+'">'
	});

})
//重新刷新页面，使用location.reload()有可能导致重新提交
function reloadPage(win) {
    var location = win.location;
    location.href = location.pathname + location.search;
}
/*上传插件
*/
function uphtml(_obj,obj,url){
	$(_obj).addClass(obj);
	layer.open({ 
	  type: 2, 
	  title: "上传文件",
	  closeBtn:1,
	  shadeClose: true,
	  scrollbar: false,
	  id: 'uphtml',
	  shade: 0.8,
	  area: ['60%', '450px'],  
	  content: url+"&obj="+obj,
	})
}
/*创建文件列表
* url  文件地址
* div 接收域类名
* num 上传的数量
*/
function fileActive(data,div,num,fileext){
	if(num=='1'){
		div.find("input").val("").val(data.url);
		layer.closeAll();
	}else{
		var this_num=div.parent().find('.pics > ul >li').length;
		if(this_num==num){
			layui.use('layer', function(){ 
				layer.msg("已达选择上限");
			})	
		}else{
			var fileImgSrc = "/statics/js/webuploader/images/";
			if(fileext=='txt'){
				src = fileImgSrc + "text.png";
			}else if(fileext=='zip'){
				src = fileImgSrc + "zip.png";
			}else if(fileext=='docx'){
				src = fileImgSrc + "docx.png";
			}else if(fileext=='pptx'){
				src = fileImgSrc + "pptx.png";
			}else if(fileext=='xlk'){
				src = fileImgSrc + "xlsx.png";
			}else{
				src = data.url;
			}	
			var html='<li><img src="'+src+'" data-name="'+data.name+'"><input name="pics[]" value="'+src+'" type="hidden"><i class="fa fa-close" data-name="'+data.name+'" data-url="'+data.url+'" onclick="delfile(this)"></i></li>';
			div.parent().find('.pics > ul').append(html);
			layer.closeAll();
		}
	}
}
//清除图片列表
function delfile(obj){
	$(obj).parent().remove();
}
//弹窗加载页面
function layerfrm(title,width,height,url){
	layer.open({
	  type: 2,
	  title: title,
	  closeBtn:1,
	  resize:true,
	  maxmin: true,
	  shadeClose: true,
	  shade: 0.8,
	  area: [width, height],
	  content: url
	})	
}
/*文本框修改
function input(obj,url){
	var _this=$(obj);
	var txt=_this.text();	
	var input = $("<input type='text' value='"+ txt +"' class='form-control'>");
	_this.html(input);
	input.click(function () {
        return false;
    });
	//获取焦点
    input.trigger("focus");
    //文本框失去焦点后提交内容，重新变为文本
    input.blur(function () {
        var newtxt = $(this).val();
        //判断文本有没有修改
        if (newtxt != txt) {
			if(url){
				$.ajax({  
					url : url,  
					type : "post",  
					dataType : "json",  
					data: data,  
					success : function(res) {  
						if(res.status) {  
							if(res.url) { 
								layer.alert(""+res.info+"....",{icon:1},function(){
									location.href = res.url;
								});
							}else{
								layer.msg(""+res.info+"....",{icon:1,time:1000},function(){
									reloadPage(window);
								});
							}
						} else {  
							layer.alert(res.info,{title:'温馨提示',icon:0});
						}  
					}  
				}); 	
			}
            _this.html(newtxt);
		} else {
            _this.html(newtxt);
        }
	})
}*/
/*设置数据状态
*示例：
*当前显示->设置成不显示：
*<a class="btn mg0 btn-info btn-xs" onClick="SetStatus(this,'info','status','{$vo.id}','0','{:U('Content/status_sort')}')">显示</a>
*当前不显示->设置成显示：
<a class="btn mg0 btn-danger btn-xs" onClick="SetStatus(this,'danger','status','{$vo.id}','1','{:U('Content/status_sort')}')">显示</a>
*参数介绍：
* btn-info 蓝色显示状态   btn-danger 红色不显示状态
*SetStatus() 方法
*this 自身  ，info或者danger对应当前class的btn-值，id 当前数据id值 ，0或者1或者其他自己设定的值，url连接
*/
function SetStatus(obj,cls,type,id,status,url){
	if(cls=="info"){
		var cl="danger";	
	}else{
		var cl="info";	
	}
	if(status==1){
		var st=0;	
	}else{
		var st=1;	
	}
	$.ajax({  
		url :url,  
		type : "post",  
		dataType : "json",  
		data: {id:id,type:type,status:status},  
		success : function(res) {  
			if(res.status) {  
				$(obj).removeClass("btn-"+cls);
				$(obj).addClass("btn-"+cl);
				$(obj).attr("onclick","return SetStatus(this,'"+cl+"','"+type+"','"+id+"','"+st+"','"+url+"');");
			} else {  
				layer.alert(res.info,{title:'温馨提示',icon:0});
			}  
		}  
	});
}
