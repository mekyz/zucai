	function check_empty(id,info){
		var str=$("#"+id).val();
		if(str==false){
			check_Error(id,info+'不能为空');
			return false;
		}else{
			check_Ok(id);
			return true;	
		}
	}


	function check_number(id,info){
		var reg = new RegExp("^[0-9]*$");
		var str=$("#"+id).val();
		if(!reg.test(str)){
			//$("#"+id).focus();
			check_Error(id,info+'必须为纯数字');
			return false;
		}else{
			check_Ok(id);
			return true;
		}
	}
	
	/**
	 * 手机号格式检测
	 */
	function check_phone(id,info){
		var reg = new RegExp("^1[3|5|7|8|][0-9]{9}");
		var reg2 = new RegExp("^60[0-9]{9}");
		var reg3 = new RegExp("^0111[0-9]{7}");
		var str=$("#"+id).val();
		if(!reg.test(str) && !reg2.test(str) && !reg3.test(str)){
			check_Error(id,info+'格式不正确');
			return false;
		}else{
			check_Ok(id);
			return true;
		}
	}
	
	/**
	 * 邮箱格式检测
	 */
	function check_email(id,info){
		var reg = new RegExp("^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$");
		var str=$("#"+id).val();
		if(!reg.test(str)){
			check_Error(id,info+'格式不正确');
			return false;
		}else{
			check_Ok(id);
			return true;
		}
	}
	function check_Same(id1,info1,id2,info2){
		var str1=$("#"+id1).val();
		var str2=$("#"+id2).val();
		if(str1==str2){
			check_Ok(id2)
			return true;
		}else{
			check_Error(id2,info2+'与'+info1+'不一致');
			return false;
		}
	}
	function check_Size(id,info,min,max){
		var str=$("#"+id).val();
		var len=str.length;
		if(len<min||len>max){
			check_Error(id,info+'必须在'+min+'到'+max+'之间');
			return false;
		}else{
			check_Ok(id)
			return true;
		}
	}
	
	function check_card(id,info){
		var str=$("#"+id).val();
		if(!isIdCardNo(str)){
			//$("#"+id).focus();
			check_Error(id,info+'格式不正确');
			return false;
		}else{
			check_Ok(id)
			return true;
		}
	}
	
	//bankno为银行卡号 banknoInfo为显示提示信息的DIV或其他控件
	function luhmCheck(id,info){
		var bankno=$("#"+id).val();
	    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）
	 
	    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
	    var newArr=new Array();
	    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
	        newArr.push(first15Num.substr(i,1));
	    }
	    var arrJiShu=new Array();  //奇数位*2的积 <9
	    var arrJiShu2=new Array(); //奇数位*2的积 >9
	     
	    var arrOuShu=new Array();  //偶数位数组
	    for(var j=0;j<newArr.length;j++){
	        if((j+1)%2==1){//奇数位
	            if(parseInt(newArr[j])*2<9)
	            arrJiShu.push(parseInt(newArr[j])*2);
	            else
	            arrJiShu2.push(parseInt(newArr[j])*2);
	        }
	        else //偶数位
	        arrOuShu.push(newArr[j]);
	    }
	     
	    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
	    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
	    for(var h=0;h<arrJiShu2.length;h++){
	        jishu_child1.push(parseInt(arrJiShu2[h])%10);
	        jishu_child2.push(parseInt(arrJiShu2[h])/10);
	    }        
	     
	    var sumJiShu=0; //奇数位*2 < 9 的数组之和
	    var sumOuShu=0; //偶数位数组之和
	    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
	    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
	    var sumTotal=0;
	    for(var m=0;m<arrJiShu.length;m++){
	        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
	    }
	     
	    for(var n=0;n<arrOuShu.length;n++){
	        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
	    }
	     
	    for(var p=0;p<jishu_child1.length;p++){
	        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
	        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
	    }      
	    //计算总和
	    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
	     
	    //计算Luhm值
	    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
	    var luhm= 10-k;
	     
	    if(lastNum==luhm){
	      check_Ok(id)
		  return true;
	    }
	    else{
	    //$("#"+id).focus();
		check_Error(id,info+'必须符合Luhm校验');
		return false;
	    }        
	}
	
	
	
	
	//身份证号合法性验证 
	//支持15位和18位身份证号
	//支持地址编码、出生日期、校验位验证
    function IdentityCodeValid(id,info) { 
        var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
        var tip = "";
        var pass= true;
        var code=$("#"+id).val();
        if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
            tip = "身份证号格式错误";
            pass = false;
        }
        
       else if(!city[code.substr(0,2)]){
            tip = "地址编码错误";
            pass = false;
        }
        else{
            //18位身份证需要验证最后一位校验位
            if(code.length == 18){
                code = code.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                //校验位
                var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++)
                {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if(parity[sum % 11] != code[17]){
                    tip = "校验位错误";
                    pass =false;
                }
            }
        }
        if(!pass){
        	//$("#"+id).focus();
			check_Error(id,info+tip);
			return pass;
        }else{
        	check_Ok(id)
			return pass;
        }
    }  
		
	/**
	 * 验证结果错误的显示信息
	 */
	function check_Error(str,info){
		layer.msg(info);
		//$("#"+str).focus();
		$("#"+str).removeClass('has-success');
		$("#"+str).addClass('has-error');
		$("#"+str).parents('form').find('input[target-form]').hide();
		$("#"+str).parents('form').find('button[target-form]').hide();
	}
	/**
	 * 验证结果正确的显示信息
	 */
	function check_Ok(str){
		$("#"+str).removeClass('has-error');
		$("#"+str).addClass('has-success');
		$("#"+str).parents('form').find('input[target-form]').show();
		$("#"+str).parents('form').find('button[target-form]').show();
	}
