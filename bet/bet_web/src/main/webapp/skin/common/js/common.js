 /* 对Date的扩展，将 Date 转化为指定格式的String<br>
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，<br>
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)<br>
 * 例子：<br>
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423<br>
 * (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.Format = function(fmt){
	var o = {"M+":this.getMonth() + 1, // 月份
			"d+":this.getDate(), // 日
			"h+":this.getHours(), // 小时
			"m+":this.getMinutes(), // 分
			"s+":this.getSeconds(), // 秒
			"q+":Math.floor((this.getMonth() + 3) / 3), // 季度
			"S":this.getMilliseconds()// 毫秒
	};
	if(/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for( var k in o){
		if(new RegExp("(" + k + ")").test(fmt)){
			fmt = fmt.replace(RegExp.$1,(RegExp.$1.length == 1)?(o[k]):(("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};
/**
 * 格式化日期时间<br>
 * 
 * @param tm
 *            long类型的时间
 * @param f
 *            格式化字符串
 * @returns
 */
function getMyFormatDateTime(tm,f){
	if(tm == '' || tm == undefined || tm.length < 1){
		return "";
	}else{
		var startTM = new Date();
		startTM.setTime(tm);
		startTM = startTM.Format(f);
		return startTM;
	}
};
/**
 * 格式化日期时间<br>
 * 格式如2017-05-05 12:00:00
 * 
 * @param tm
 *            long类型的时间
 * @returns
 */
function getFormatDateTime(tm){
	return getMyFormatDateTime(tm,'yyyy-MM-dd hh:mm:ss');
};
/**
 * 格式化日期时间<br>
 * 格式如05-05 12:00:00(没有年份)
 * 
 * @param tm
 *            long类型的时间
 * @returns
 */
function getFormatSimpleDateTime(tm){
	return getMyFormatDateTime(tm,'MM-dd hh:mm:ss');	
};
/**
 * 获取日期的long类型
 * 
 * @param tm
 *            时间，格式如2017-05-05
 * @returns
 */
function getDateLong(tm){
	if(tm == '' || tm == undefined || tm.length < 1){
		return "";
	}else{
		var strDate = tm.split("-");
		var a = new Date(strDate[0],(strDate[1] - parseInt(1)),strDate[2],0,0,0);
		return a.getTime();
	}
};
/**
 * 获取日期的long类型
 * 
 * @param tm
 *            时间，格式如2017-05-05 12:00:00
 * @returns
 */
function getDateTimeLong(tm){
	if(tm == '' || tm == undefined || tm.length < 1){
		return "";
	}else{
		var strArray = tm.split(" ");
		var strDate = strArray[0].split("-");
		var strTime = strArray[1].split(":");
		var a = new Date(strDate[0],(strDate[1] - parseInt(1)),strDate[2],strTime[0],strTime[1],strTime[2]);
		return a.getTime();
	}
};
/**
 * 获取倒计时类型的时间<br>
 * 比如2:52
 * 
 * @param tm
 *            long类型的时间
 * @returns
 */
function getFormatTime(tm){
	if(tm == '' || tm == undefined || tm.length < 1){
		return "";
	}else{
		if(tm > 60 * 60){
			return parseInt(tm / 3600) + ":" + parseInt((tm % 3600) / 60) + ":" + (tm % 60);
		}else if(tm > 60){
			return parseInt(tm / 60) + ":" + (tm % 60);
		}else{
			return "0:" + tm;
		}
	}
};
/**
 * 获取当前时间的long类型
 * 
 * @returns
 */
function getCurrentTimeLong(){
	return new Date().getTime();
};
/**
 * 获取指定时间的long类型
 * 
 * @param year
 *            年
 * @param month
 *            月
 * @param day
 *            日
 * @returns
 */
function getTimeLong(year,month,day){
	var a = new Date(year,month - 1,day,0,0,0);
	return a.getTime();
};
/**
 * 获取指定时间相对于当前时间过去的字符串
 * 
 * @param tm
 *            long类型的时间
 * @returns
 */
function getRelativeTime(tm){
	var currentTm = new Date().getTime();
	var subTm = currentTm - tm;
	if(subTm >= 0){
		var oneMinute = 60 * 1000;
		var minute = subTm / oneMinute;
		if(minute < 1){
			return "刚刚";
		}else if(minute < 30){
			return parseInt(minute) + "分钟前";
		}else if(minute < 60){
			return "半小时前";
		}else{
			var hour = minute / 60;
			if(hour < 24){
				return parseInt(hour) + "小时前";
			}else{
				return getFormatDateTime(tm);
			}
		}
	}else{
		return getFormatDateTime(tm);
	}
}
function getTimeDate(timeStr){
	var index=timeStr.indexOf(' ');
	if(index>0){
		timeStr=timeStr.substring(0,index);
	}
	return timeStr;
};
function getWeekDay(tmLong){
	var date=new Date(tmLong);
	date.setTime(tmLong);
	var day=date.getDay();
	if(day==0){
		return "星期日";
	}else if(day==1){
		return "星期一";
	}else if(day==2){
		return "星期二";
	}else if(day==3){
		return "星期三";
	}else if(day==4){
		return "星期四";
	}else if(day==5){
		return "星期五";
	}else if(day==6){
		return "星期六";
	}
	return "";
};
/**
 * 判断字符串是否为空
 * 
 * @param str
 *            需要判断的字符串
 * @returns
 */
function isNull(str){
	if(typeof(str)== 'undefined'||!str ||str == '' ||  str.length < 1){
		return true;
	}
	return false;
};
/**
 * 定义全局变量
 */
var LR_WEB = {
		LIST_LEN:20,
		RETURN_CODE:{SUCCESS:0,PASSWORD_ERROR:-20000,FORBIDDEN_ERROR:-300,FAIL:1},
		STATUS:{ENABLED:0,DISABLED:1,INVALIDE:2,UNKNOWN:3},
		SEX_TYPE:{MALE:1,FEMALE:2,UNKNOWN:3},
		PLATFORM_TYPE:{ANDROID:"android",IOS:"ios",OTHER:"other"},
		USER_TYPE:{COMMON:0,MEMBER:5,AGENT1:10,AGENT2:20,AGENT3:30,AGENT4:40,AGENT5:50,AGENT6:60,AGENT7:70},
		APPLY_STATUS:{APPLY:0,PAYED:5,VERIFY_SUCCESS:10,VERIFY_FAIL:20,PROCESSED:30},
		ADMIN_TYPE:{ADMIN:0,CUSTOMER:20},
		SMS_CODE_TYPE:{REGISTER:1,RESET_PWD:2,USER_AUTH:3,NUMBER_AUTH:4},
		CLIENT_POSITION:{PAGE_START:'START',PAGE_INDEX:'INDEX',PAGE_AGENT_INDEX:'AGENT_INDEX',PAGE_SHOP_INDEX:'SHOP_INDEX'},
		CLIENT_TYPE:{BANNER:'BANNER',BUTTON:'BUTTON'},
		MONEY_UNIT:{POINT:11,GIVE_POINT:13},
		USER_BALANCE_LOG_TYPE:{WITHDRAW:'withdraw',GIVE_POINT:'give_point',GIVE_POINT_INVALIDE:'gp_invalide',RECHARGE:'recharge',EXCHANGE_OUT:'exchange_out',EXCHANGE_IN:'exchange_in',BET:'bet',BET_RETURN:'bet_return',WIN_BET:'win_bet',WIN_BET_RETURN:'win_bet_rt',TEAM_SHARE:'team_share',TEAM_AGENT:'team_agent',TEAM_AGENT_SAME_LEVEL:'team_agent_sl',TEAM_BENFIT:'team_benfit',TEAM_BENFIT_SAME_LEVEL:'team_benfit_sl',TEAM_LEADER5_BENFIT:'team_l5_benfit',TEAM_LEADER6_BENFIT:'team_l6_benfit',TEAM_LEADER7_BENFIT:'team_l7_benfit'},
		PAY_METHOD:{PAY_POINT:'point',PAY_WECHART:'wechart',PAY_WECHART_H5:'wechart_h5',PAY_WECHART_SHAOMA:'wechart_shaoma',PAY_WECHAT_MP:'wechat_mp'}, 
		PAY_TYPE:{PAY_BALANCE:'pay_balance',PAY_UPGRADE:'pay_upgrade',PAY_PRODUCT:'pay_product',PAY_TRANSFER:'pay_transfer',PAY_TAKEOUT_ORDER:'pay_takeout_order'},
		PAYEE_TYPE:{PAY_ONLINE:'PAY_ONLINE',UNIONPAY:'UNIONPAY',ALIPAY:'ALIPAY'},
		BODAN_TYPE:{FULL:'FULL',HALF:'HALF',SCORE:'SCORE'},
		PAY_PLATFORM:{MINLE_PAY:'minlepay'},
		TEAM_PROFIT_TYPE:{SHARE:'1',AGENT:'2',SAME_LEVEL1:'3',BENEFIT:'4',SAME_LEVEL2:'5'},
		DATA_TABLES:{
			DEFAULT_OPTION:{ // DataTables初始化选项
				language:{
					"lengthMenu":"每页 <select><option value='10'>10</option><option value='50'>50</option><option value='100'>100</option><option value='500'>500</option></select> 条记录",
					"zeroRecords":"没有找到记录","info":"第 _PAGE_ 页 ( 总共 _PAGES_ 页，_TOTAL_条记录 )","infoEmpty":"无记录","infoFiltered":"(从 _MAX_ 条记录过滤)","emptyTable":"没有找到记录","infoPostFix":"",
					"loadingRecords":"正在加载数据...","processing":"正在查询","search":"搜索:","paginate":{"first":"首页","last":"末页","next":"下一页","previous":"上一页"},},"paginate":false,"bPaginate":true, // 翻页功能
					"bLengthChange":false, // 改变每页显示数据数量
					"bFilter":true, // 过滤功能
					"bSort":false, // 排序功能
					"bInfo":true,// 页脚信息
					"bAutoWidth":false,// 自动宽度
					"bJQueryUI":false, // 是否使用 jQury的UI theme
					"pagingType":"simple_numbers","processing":true,"serverSide":true,"searching":false},COLUMN:{CHECKBOX:{ // 复选框单元格
						className:"td-checkbox",orderable:false,width:"30px",data:null,render:function(data,type,row,meta){
							return '<input type="checkbox" class="iCheck">';
						}}},RENDER:{ // 常用render可以抽取出来，如日期时间、头像等
							ELLIPSIS:function(data,type,row,meta){
								data = data || "";
								return '<span title="' + data + '">' + data + '</span>';
							},DATE_LONG:function(data,type,row,meta){
								return getFormatDateTime(data);
							},STATUS:function(data,type,row,meta){
								if(data == LR_WEB.STATUS.ENABLED){return "已启用";}else if(data == LR_WEB.STATUS.DISABLED){return "已禁用";}else if(data == LR_WEB.STATUS.INVALIDE){return "已失效";}else{"未知";}
							},CONVERT_RMB:function(data,type,row,meta){
								return convertRmb(data);
							},USER_TYPE:function(data,type,row,meta){
								return getUserTypeString(data);
							},APPLY_STATUS:function(data,type,row,meta){
								return getApplyStatusString(data);
							},ADMIN_TYPE:function(data,type,row,meta){
								return getAdminTypeString(data);
							},SEX_TYPE:function(data,type,row,meta){
								return getSexTypeString(data);
							},USER_REDIRECT:function(data,type,row,meta){
								if(isNull(data)){
									return data;
								}else{
									return "<a href='userInfo?userId=" + data + "'>" + data + "</a>";
								}
							},SMS_CODE_TYPE:function(data,type,row,meta){
								return getSmsCodeTypeString(data);
							},CLIENT_POSITION:function(data,type,row,meta){
								return getClientPositionString(data);
							},CLIENT_TYPE:function(data,type,row,meta){
								return getClientTypeString(data);
							},MONEY_UNIT:function(data,type,row,meta){
								return getMoneyUnitString(data);
							},USER_BALANCE_LOG_TYPE:function(data,type,row,meta){
								return getUserBalanceLogTypeString(data);
							},PAY_METHOD:function(data,type,row,meta){
								return getPayMethodString(data);
							},PAY_TYPE:function(data,type,row,meta){
								return getPayTypeString(data);
							},PAYEE_TYPE:function(data,type,row,meta){
								return getPayeeTypeString(data);
							},BODAN_TYPE:function(data,type,row,meta){
								return getBodanTypeString(data);
							},PAY_PLATFORM:function(data,type,row,meta){
								return getPayPlatformString(data);
							},TEAM_PROFIT_TYPE:function(data,type,row,meta){
								return getTeamProfitTypeString(data);
							}}}};
/**
 * 支付平台
 * 
 * @param data
 *            类型
 * @returns
 */
function getPayPlatformString(data){
	if(data == LR_WEB.PAY_PLATFORM.MINLE_PAY){
		return "民乐支付";
	}else{
		return "未知";
	}
};
function getTeamProfitTypeString(data){
	if(data == LR_WEB.TEAM_PROFIT_TYPE.SHARE){
		return "分享佣金";
	}else if(data == LR_WEB.TEAM_PROFIT_TYPE.AGENT){
		return "代理佣金";
	}else if(data == LR_WEB.TEAM_PROFIT_TYPE.SAME_LEVEL1){
		return "代理佣金平级奖";
	}else if(data == LR_WEB.TEAM_PROFIT_TYPE.BENEFIT){
		return "福利奖";
	}else if(data == LR_WEB.TEAM_PROFIT_TYPE.SAME_LEVEL2){
		return "福利平级奖";
	}else{
		return "未知";
	}
};
/**
 * 波胆类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getBodanTypeString(data){
	if(data == LR_WEB.BODAN_TYPE.FULL){
		return "全场";
	}else if(data == LR_WEB.BODAN_TYPE.HALF){
		return "半场";
	}else if(data == LR_WEB.BODAN_TYPE.SCORE){
		return "进球数";
	}else{
		return "未知";
	}
};
/**
 * 收款类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getPayeeTypeString(data){
	if(data == LR_WEB.PAYEE_TYPE.PAY_ONLINE){
		return "在线支付";
	}else if(data == LR_WEB.PAYEE_TYPE.UNIONPAY){
		return "银联转账";
	}else if(data == LR_WEB.PAYEE_TYPE.ALIPAY){
		return "支付宝";
	}else{
		return "未知";
	}
};
/**
 * 付款类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getPayTypeString(data){
	if(data == LR_WEB.PAY_TYPE.PAY_BALANCE){
		return "余额充值";
	}else if(data == LR_WEB.PAY_TYPE.PAY_UPGRADE){
		return "用户升级";
	}else if(data == LR_WEB.PAY_TYPE.PAY_PRODUCT){
		return "商品支付";
	}else if(data == LR_WEB.PAY_TYPE.PAY_TRANSFER){
		return "当面付";
	}else if(data == LR_WEB.PAY_TYPE.PAY_TAKEOUT_ORDER){
		return "外卖订单支付";
	}else{
		return "未知";
	}
};
/**
 * 用户余额变动记录类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getUserBalanceLogTypeString(data){
	if(data == LR_WEB.USER_BALANCE_LOG_TYPE.WITHDRAW){
		return "余额提现";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.GIVE_POINT){
		return "注册赠送";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.GIVE_POINT_INVALIDE){
		return "体验彩金已到有效期";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.RECHARGE){
		return "用户充值";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.EXCHANGE_OUT){
		return "用户转出";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.EXCHANGE_IN){
		return "用户转入";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.BET){
		return "用户下注";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.BET_RETURN){
		return "撤回下注";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.WIN_BET){
		return "用户下注中奖";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.WIN_BET_RETURN){
		return "用户下注中奖返还本金";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_SHARE){
		return "团队分享佣金";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_AGENT){
		return "团队代理佣金";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_AGENT_SAME_LEVEL){
		return "团队代理佣金平级奖";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_BENFIT){
		return "团队福利奖";
	}else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_BENFIT_SAME_LEVEL){
		return "团队福利平级奖";
	} else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_LEADER5_BENFIT){
		return "VIP领袖奖";
	} else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_LEADER6_BENFIT){
		return "钻石领袖奖";
	} else if(data == LR_WEB.USER_BALANCE_LOG_TYPE.TEAM_LEADER7_BENFIT){
		return "超级领袖奖";
	} else{
		return "未知类型";
	}
};
/**
 * 支付方式
 * 
 * @param data
 *            类型
 * @returns
 */
function  getPayMethodString(data){
	if(data == LR_WEB.PAY_METHOD.PAY_POINT){
		return "积分支付";
	}else if(data == LR_WEB.PAY_METHOD.PAY_WECHART){
		return "微信公众号支付";
	}else if(data == LR_WEB.PAY_METHOD.PAY_WECHART_H5){
		return "微信H5支付";
	}else if(data == LR_WEB.PAY_METHOD.PAY_WECHART_SHAOMA){
		return "微信扫码支付";
	}else if(data == LR_WEB.PAY_METHOD.PAY_WECHAT_MP){
		return "微信小程序支付";
	}else{
		return "未知";
	}
};
/**
 * 金额单位
 * 
 * @param data
 *            单位
 * @returns
 */
function getMoneyUnitString(data){
	if(data == LR_WEB.MONEY_UNIT.POINT){
		return "彩金钱包";
	}else if(data == LR_WEB.MONEY_UNIT.GIVE_POINT){
		return "体验彩金";
	}else{
		return "未知";
	}
};
/**
 * 客户端位置类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getClientPositionString(data){
	if(data == LR_WEB.CLIENT_POSITION.PAGE_START){
		return "启动页";
	}else if(data == LR_WEB.CLIENT_POSITION.PAGE_INDEX){
		return "首页";
	}else if(data == LR_WEB.CLIENT_POSITION.PAGE_AGENT_INDEX){
		return "代理商首页";
	}else if(data == LR_WEB.CLIENT_POSITION.PAGE_SHOP_INDEX){
		return "商家首页";
	}else{
		return "未知";
	}
};
/**
 * 客户端功能类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getClientTypeString(data){
	if(data == LR_WEB.CLIENT_TYPE.BANNER){
		return "展示图";
	}else if(data == LR_WEB.CLIENT_TYPE.BUTTON){
		return "功能按钮图";
	}else{
		return "未知";
	}
};
/**
 * 短信验证码类型
 * 
 * @param data
 *            类型
 * @returns
 */
function  getSmsCodeTypeString(data){
	if(data == LR_WEB.SMS_CODE_TYPE.REGISTER){
		return "注册验证码";
	}else if(data == LR_WEB.SMS_CODE_TYPE.RESET_PWD){
		return "重置密码验证码";
	}else if(data == LR_WEB.SMS_CODE_TYPE.USER_AUTH){
		return "实名认证验证码";
	}else if(data == LR_WEB.SMS_CODE_TYPE.NUMBER_AUTH){
		return "手机号码验证码";
	}else{
		return "未知";
	}
};
/**
 * 管理员类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getAdminTypeString(data){
	if(data == LR_WEB.ADMIN_TYPE.ADMIN){
		return "管理员";
	}else if(data == LR_WEB.ADMIN_TYPE.CUSTOMER){
		return "客服";
	}else{
		return "未知类型";
	}
};
/**
 * 性别
 * 
 * @param data
 *            类型
 * @returns
 */
function getSexTypeString(data){
	if(data == LR_WEB.SEX_TYPE.MALE){
		return "男";
	}else if(data == LR_WEB.SEX_TYPE.FEMALE){
		return "女";
	}else if(data == LR_WEB.SEX_TYPE.UNKNOWN){
		return "隐藏";
	}else{
		return "未知";
	}
};
/**
 * 申请状态
 * 
 * @param data
 *            状态
 * @returns
 */
function getApplyStatusString(data){
	if(data == LR_WEB.APPLY_STATUS.APPLY){
		return "已申请";
	}else if(data == LR_WEB.APPLY_STATUS.PAYED){
		return "已支付";
	}else if(data == LR_WEB.APPLY_STATUS.VERIFY_SUCCESS){
		return "审核成功";
	}else if(data == LR_WEB.APPLY_STATUS.VERIFY_FAIL){
		return "审核失败";
	}else if(data == LR_WEB.APPLY_STATUS.PROCESSED){
		return "已处理";
	}else{
		return "未知状态";
	}
};
/**
 * 用户类型
 * 
 * @param data
 *            类型
 * @returns
 */
function getUserTypeString(data){
	if(data == LR_WEB.USER_TYPE.COMMON){
		return "普通用户";
	}else if(data == LR_WEB.USER_TYPE.MEMBER){
		return "会员";
	}else if(data == LR_WEB.USER_TYPE.AGENT1){
		return "一星代理";
	}else if(data == LR_WEB.USER_TYPE.AGENT2){
		return "二星代理";
	}else if(data == LR_WEB.USER_TYPE.AGENT3){
		return "三星代理";
	}else if(data == LR_WEB.USER_TYPE.AGENT4){
		return "四星代理";
	}else if(data == LR_WEB.USER_TYPE.AGENT5){
		return "五星代理";
	}else if(data == LR_WEB.USER_TYPE.AGENT6){
		return "白金代理";
	}else if(data == LR_WEB.USER_TYPE.AGENT7){
		return "红钻代理";
	}else{
		return "未知类型";
	}
};
/**
 * 转换价格从分到元
 * 
 * @param data
 *            价格(单位分)
 * @returns
 */
function convertRmb(data){
	var yuan = (data / 100).toFixed(2);
	return yuan;
};
/**
 * 输出价格到文档
 * 
 * @param data
 *            价格(单位分)
 * @returns
 */
function writePrice(data){
	document.write(convertRmb(data));
};
/**
 * 输出日期到文档
 * 
 * @param data
 *            long类型的时间
 * @returns
 */
function writeDate(data){
	document.write(getFormatDateTime(data));
};
/**
 * 取得当前页面的地址
 * 
 * @param url
 *            页面URL
 * @returns
 */
function getCurrentPageUrl(){
	var str=location.href; // 取得整个地址栏
	return str;
};
/**
 * 取得当前页面的地址(不带参数)
 * 
 * @param url
 *            页面URL
 * @returns
 */
function getCurrentPageUrlNoParam(){
	var str=location.href; // 取得整个地址栏
	var index = str.indexOf("?");
	if(index>0){
		str = str.substring(0,index);
	}
	return str;
};
/**
 * 取得当前页面的地址
 * 
 * @param url
 *            页面URL
 * @returns
 */
function getCurrentPageUrl(){
	var str=location.href; // 取得整个地址栏
	return str;
};
function getCurrentPageUrl2(){
	var str=location.href; // 取得整个地址栏
	str=str.replace(new RegExp("&","gm"),",,,,");
	return str;
};
/**
 * 取得当前页面的地址(不带参数)
 * 
 * @param url
 *            页面URL
 * @returns
 */
function getCurrentPageUrlNoParam(){
	var str=location.href; // 取得整个地址栏
	var index = str.indexOf("?");
	if(index>0){
		str = str.substring(0,index);
	}
	return str;
};
/**
 * 跳转到指定的页面
 * 
 * @param url
 *            页面URL
 * @returns
 */
function goUrl(url){
	window.location.href = url;
};
/**
 * 刷新页面
 * 
 * @returns
 */
function refreshPage(){
	window.location.reload();
};
/**
 * 打印log
 * 
 * @param str
 *            要打印的字符串
 * @returns
 */
function showLog(str){
	console.log(str);
};
/**
 * 去掉上传图片返回的多余字符串
 * 
 * @param data
 *            上传图片返回的字符串
 * @returns
 */
function trimPicResponseText(data){

	var index = data.indexOf('>');
	if(index > 0){
		data = data.substring(index + 1);
	}
	index = data.indexOf('<');
	if(index > 0){
		data = data.substring(0,index);
	}
	return data;
};
/**
 * 转换成json对象
 * 
 * @param data
 *            字符串对象
 * @returns
 */
function toJson(data){
	return eval('(' + data + ')');
};
/**
 * 显示对话框
 * 
 * @param str
 *            显示的字符串
 * @returns
 */
function showMsg(str){
	alert(str);
};
/**
 * 通过ajax调用接口
 * 
 * @param url
 *            接口地址
 * @param params
 *            接口参数
 * @param callbackSuccessFunc
 *            成功执行的函数
 * @param callbackFailFunc
 *            失败执行的函数
 * @returns
 */
function ajax(url,params,callbackSuccessFunc,callbackFailFunc){
	$.ajax({url:url,data:params,type:'post',async:true,cache:false,dataType:'text',success:function(data){
		callbackSuccessFunc(data);
	},error:function(){
		if(callbackFailFunc != null){
			callbackFailFunc();
		}else{
			// showMsg("调用接口("+url+"?"+params+")返回错误！");
			showLog("调用接口("+url+"?"+params+")返回错误！");
		}
	}});
};
/**
 * 通过ajax获取json数据列表
 * 
 * @param url
 *            接口地址
 * @param params
 *            接口参数
 * @param callbackFunc
 *            获取数据成功执行的函数
 * @returns
 */
function ajaxDataJson(url,params,callbackFunc){
	$.ajax({url:url,data:params,type:'post',async:true,cache:false,dataType:'json',success:function(data){
		callbackFunc(data);
	},error:function(){
		// showMsg("调用接口("+url+"?"+params+")返回错误！");
		showLog("调用接口("+url+"?"+params+")返回错误！");
	}});
};
/**
 * 通过ajax获取json数据
 * 
 * @param url
 *            接口地址
 * @param params
 *            接口参数
 * @param callbackSuccessFunc
 *            获取数据成功执行的函数
 * @param callbackFailFunc
 *            获取数据失败执行的函数
 * @returns
 */
function ajaxJson(url,params,callbackSuccessFunc,callbackFailFunc,errFunc){
	$.ajax({url:url,data:params,type:'post',async:true,cache:false,dataType:'json',success:function(data){
		if(data.code === LR_WEB.RETURN_CODE.SUCCESS){
			callbackSuccessFunc(data.msg);
		}else{
			if(callbackFailFunc != null){
				callbackFailFunc(data.msg);
			}else{
				showMsg(data.msg);
			}
		}
	},error:function(){
		if(errFunc){
			errFunc();
		}else{
			// showMsg("调用接口("+url+"?"+params+")返回错误！");
		}
		showLog("调用接口("+url+"?"+params+")返回错误！");
	}});
};
/**
 * 获取Meta内容
 * 
 * @param name
 *            Meta名称
 * @returns
 */
function getMetaContent(name){
	return $('meta[name="' + name + '"]').attr("content");
};
/**
 * 通过ajax获取值绑定到元素上
 * 
 * @param cid
 *            元素id
 * @param url
 * @param params
 * @returns
 */
function bindContent(cid,url,params){
	ajaxDataJson(url,params,function(data){
		$("#" + cid).html(data);
	});
};
function bindContentMoney(cid,url,params){
	ajaxDataJson(url,params,function(data){
		$("#"+cid).html((data/100).toFixed(2));
	});
};
/**
 * 生成一个随机字符串<br>
 * 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
 * 
 * @param len
 *            随机字符串长度
 * @returns 随机字符串
 */
function genRandomStr(len){
	len = len || 32;
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
	var maxPos = $chars.length;
	var str = '';
	for(i = 0;i < len;i++){
		str += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return str;
};
/**
 * 获取字符串真实的长度，即汉字会占2个长度
 * 
 * @param str
 *            字符串
 * @returns 字符串长度
 */
function getRealLength(str) {    
	var len = 0;    
	for (var i=0; i<str.length; i++) {
		var ch = str.charCodeAt(i);
		if (ch>127 || ch==94) {    
			len += 2;    
		} else {    
			len ++;    
		}    
	}    
	return len;    
};
/**
 * 获取一个随机数
 * 
 * @param Min
 *            最小值
 * @param Max
 *            最大值
 * @returns
 */
function GetRandomNum(Min,Max){
	if(Min==Max){
		return Max;
	}
	var Range = Max - Min;
	var Rand = Math.random();
	return (Min + Math.round(Rand * Range));
};

/**
 * 判断是否是微信浏览器
 * 
 * @returns
 */
function isWechart(){
	var wechatInfo=navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i);if(!wechatInfo){return false;}else{return true;}
}
// 创建随机数对象
function createRandomObj(key,weight){
    var obj = new Object();
    obj.Key = key;
    obj.Weight = weight;
    return obj;
}
// 获取随机数对象键值（可按权重）
function GetRandomObjKey(randomObjList){
    if(randomObjList.length <= 0)
	    return 0;
    var tempList = [];
    var min = 0,max = 0;
    for(var i = 0;i < randomObjList.length;i++){
	    max += randomObjList[i].Weight;
	    for(var j = 0;j < randomObjList[i].Weight;j++){
		    tempList.push(randomObjList[i].Key);
	    }
    }
    if(tempList.length > 0){
	    var randomNum = GetRandomNum(0,tempList.length - 1);
	    return tempList[randomNum];
    }else{
	    return 0;
    }
}
String.prototype.replaceAll=function(f,e){// 吧f替换成e
    var reg=new RegExp(f,"g"); // 创建正则RegExp对象
    return this.replace(reg,e); 
}
function jsonParse(str){
	str=str.replaceAll("\r\n","<br>");
	str=str.replaceAll("\n","<br>");
	str=str.replaceAll("\t","    ");
	return JSON.parse(str);
}
