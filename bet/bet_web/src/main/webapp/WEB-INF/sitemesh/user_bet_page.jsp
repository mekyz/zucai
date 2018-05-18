<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="com.bet.orms.*"%>
<%@ page import="com.bet.utils.*"%>
<%@ page import="com.lrcall.lrweb.common.utils.*"%>
<%
	String basePath = request.getContextPath() + "/";
	String commonDir = "skin/common";
	String mobileDir = "skin/mobile";
	String pcDir = "skin/pc";
	String commonImgPath = basePath + commonDir + "/images";
	String commonJsPath = basePath + commonDir + "/js";
	String commonCssPath = basePath + commonDir + "/css";
	String mobileImgPath = basePath + mobileDir + "/images";
	String mobileJsPath = basePath + mobileDir + "/js";
	String mobileCssPath = basePath + mobileDir + "/css";
	String pcImgPath = basePath + pcDir + "/images";
	String pcJsPath = basePath + pcDir + "/js";
	String pcCssPath = basePath + pcDir + "/css";
	String betDir = basePath + "skin/bet";
	String preUrl = (String) session.getAttribute(SessionManage.SessionUser.USER_PRE_URL.getName());
	if (StringTools.isNull(preUrl)) {
		preUrl = "";
	}
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<title><sitemesh:write property='title'>DM SUPER CLUB</sitemesh:write></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="shortcut icon" href="<%=betDir%>/Public/Home/h+/ico/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon.png" />
<link rel="apple-touch-icon" sizes="57x57" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-57x57.png" />
<link rel="apple-touch-icon" sizes="72x72" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-72x72.png" />
<link rel="apple-touch-icon" sizes="76x76" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-76x76.png" />
<link rel="apple-touch-icon" sizes="114x114" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-114x114.png" />
<link rel="apple-touch-icon" sizes="120x120" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-120x120.png" />
<link rel="apple-touch-icon" sizes="144x144" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-144x144.png" />
<link rel="apple-touch-icon" sizes="152x152" href="<%=betDir%>/Public/Home/h+/ico/apple-touch-icon-152x152.png" />
<link href="<%=betDir%>/Public/Home/h+/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/vendor/skycons/css/skycons.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/vendor/css/pace.preloader.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/css/jquery.mmenu.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/css/add-ons.min.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/js/layui/css/layui.css" rel="stylesheet">
<link href="<%=betDir%>/Public/Home/h+/js/bootkit/css/bootkit.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/js/select2/select2.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/js/scrollbar/css/mCustomScrollbar.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/js/jquery-datatables-bs3/css/datatables.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/js/owl-carousel/css/owl.carousel.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/js/owl-carousel/css/owl.theme.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/js/magnific-popup/css/magnific-popup.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/css/page.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/css/jquery.ticker.css" rel="stylesheet" />
<script src="<%=betDir%>/Public/Home/h+/js/jquery.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery-2.1.1.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery-ui.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/vendor/skycons/js/skycons.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/pace.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/layui/layui.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/slimscroll.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery.mmenu.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/core.min.js?v=1"></script>
<script src="<%=betDir%>/Public/Home/h+/js/sparkline/js/jquery.sparkline.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/modernizr/js/modernizr.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/bootkit/js/bootkit.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/scrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/select2/select2.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery-datatables/media/js/jquery.dataTables.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery-datatables/extras/TableTools/js/dataTables.tableTools.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery-datatables-bs3/js/datatables.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/table-advanced.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/owl-carousel/js/owl.carousel.js"></script>
<script type="text/javascript" src="<%=betDir%>/Public/Home/h+/js/ui-carousels.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/magnific-popup/js/magnific-popup.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/ui-elements.js"></script>
<script type="text/javascript">
layui.use('layer', function(){ 
	var layer = layui.layer;
		layer.config({
		  //extend: 'espresso/style.css',
		  //skin: 'layer-ext-espresso'
		});
	});
var browser={versions:function(){
	var u=navigator.userAgent,app=navigator.appVersion;
	return {//移动终端浏览器版本信息   
	trident:u.indexOf('Trident')>-1, //IE内核  
	presto:u.indexOf('Presto')>-1, //opera内核  
	webKit:u.indexOf('AppleWebKit')>-1, //苹果、谷歌内核  
	gecko:u.indexOf('Gecko')>-1&&u.indexOf('KHTML')==-1, //火狐内核  
	mobile:!!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端  
	ios:!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
	android:u.indexOf('Android')>-1||u.indexOf('Linux')>-1, //android终端或者uc浏览器  
	iPhone:u.indexOf('iPhone')>-1, //是否为iPhone或者QQHD浏览器  
	iPad:u.indexOf('iPad')>-1, //是否iPad    
	webApp:u.indexOf('Safari')==-1, //是否web应该程序，没有头部与底部  
	weixin:u.indexOf('MicroMessenger')>-1, //是否微信   
	qq:u.match(/\sQQ/i)==" qq" //是否QQ  
	};
}(),language:(navigator.browserLanguage||navigator.language).toLowerCase()};
function isMobileWeb(){
	if(browser.versions.mobile||browser.versions.ios||browser.versions.android||browser.versions.iPhone||browser.versions.iPad||browser.versions.weixin||browser.versions.qq){
		return true;
	}
	return false;
}
</script>
<link href="<%=betDir%>/Public/Home/h+/css/style.css" rel="stylesheet" />
<link href="<%=betDir%>/Public/Home/h+/css/color/color3.css" rel="stylesheet" />
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
<link href="<%=betDir%>/Public/Home/h+/css/new-file.css" rel="stylesheet" />
<style>
.layui-layer-content {
	text-align: center !important
}

.canvasBg {
	position: absolute; left: 0px; top: 0px; width: 100%; height: 100%;
}

#canvas {
	display: block; width: 100%; height: 100%;
}

@media ( max-width : 767px) {
	#time {
		display: none;
	}
}
</style>
<style>
body {
	font-family: '微软雅黑';
}

table td {
	text-align: center;
}

html, body {
	height: 100%; overflow: auto;
}

body.sidebar-hide .navbar {
	margin-left: 0 !important; width: 100% !important;
}

marquee {
	border-radius: 15px
}

marquee a {
	font-weight: bolder; margin-right: 25px
}
</style>
<%-- 引入js文件 --%>
<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
<script src="<%=mobileJsPath%>/common.js?v=20180505"></script>
<script>
	var getUserPreUrl = function() {
	    var pre_url = "<%=preUrl%>";
		return pre_url;
	};
</script>
<sitemesh:write property='head' />
</head>
<body>
	<%-- 内容区 --%>
	<div class="navbar" role="navigation">
		<div class="container-fluid container-nav">
			<ul class="nav navbar-nav navbar-actions navbar-left">
				<li class="visible-xs visible-sm"><a href="#" id="sidebar-menu"><i class="fa fa-navicon"></i></a></li>
			</ul>
			<div class="navbar-left" style="width: calc(100% - 500px)">
				<div class="ticker-news-box hidden-xs" style="width: 100%;">
					<span class="text-uppercase mrm pull-left text-info"><font color="#ffffff"></font></span>
					<marquee id="m_news" direction=left behavior=scroll loop=-1 scrollamount=5 scrolldelay=10 align=top bgcolor=#ffffff height=30 width=100% onmouseover=this.stop() onmouseout=this.start()>
						<%-- <a href="/index.php?s=/home/article/shownews/id/112.html"><font color="#368ee0">篮球赛事发布公告</font></a><a href="/index.php?s=/home/article/shownews/id/111.html"><font color="#368ee0">篮球赛事上线公告</font></a><a
							href="/index.php?s=/home/article/shownews/id/109.html"
						><font color="#368ee0">2018-3-29 23:45 德地区 吕内堡vs布伦斯维克青年队 比赛推迟</font></a><a href="/index.php?s=/home/article/shownews/id/94.html"><font color="#368ee0">推荐会员入金活动</font></a> --%>
					</marquee>
				</div>
				<%-- <script src="<%=betDir%>/Public/Home/h+/js/jquery.ticker.js"></script>
				<script>
                $('#news-update').ticker({
                    controls: false,
                    titleText: ''
                });
                </script> --%>
			</div>
			<div class="navbar-right">
				<span style="float: left; margin-right: 15px;"> <a style="cursor: pointer; color: #FFFFFF;" href="index?lang=en_US"><img src="<%=betDir%>/Public/Home/h+/images/en.jpg"
						style="margin-right: 7px;"
					/>English</a> <a style="cursor: pointer; color: #FFFFFF;" href="index?lang=zh_CN"><img src="<%=betDir%>/Public/Home/h+/images/zh.jpg" style="margin: 0 7px;" />中文</a>
				</span>
				<div id="time" class="pull-left" style="padding-right: 20px; color: #fff">
					<span id="wite-date" class="hidden-xs"></span> &nbsp; <span id="wite-hours"></span> : <span id="wite-min"></span> : <span id="wite-sec"></span>
				</div>
				<div class="pull-left" style="padding-right: 20px; color: #fff">
					&nbsp; <span><a href="logout"><i class="fa fa-power-off" style="color: #ffffff"></i> <font color="#ffffff">退出</font></a></span>
				</div>
				<script>      
                    (function(){
                        var monthNames = [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ];
                        var dayNames= ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]
                        var newDate = new Date();
                        newDate.setDate(newDate.getDate());
                        $('#wite-date').html(newDate.getFullYear()+"-"+monthNames[newDate.getMonth()]+"-"+newDate.getDate()+" "+dayNames[newDate.getDay()]);
                        setInterval( function() {
                            var seconds = new Date().getSeconds();
                            $("#wite-sec").html(( seconds < 10 ? "0":"" ) + seconds);
                        },1000);
                        setInterval( function() {
                            var minutes = new Date().getMinutes();
                            $("#wite-min").html(( minutes < 10 ? "0":"" ) + minutes);
                        },1000);
                        setInterval( function() {
                            var hours = new Date().getHours();
                            $("#wite-hours").html(( hours < 10 ? "0" : "" ) + hours);
                        }, 1000);
                    })();
                    </script>
			</div>
		</div>
	</div>
	<script>
	var width = document.body.clientWidth;
	if(width>991){
		$('.navbar').css({'width':width-240,'padding':'0 10px 0 0px','marginLeft':'240px'});
	}
	$(window).resize(function(){
		var width = document.body.clientWidth;
		if(width>991){
			$('.navbar').css({'width':width-240,'padding':'0 10px 0 0px','marginLeft':'240px'});
		}
		if(width<991){
			$('.navbar').css({'width':'100%','padding':'0 10px 0 0px','marginLeft':'0px'});
		}
	})
</script>
	<div class="container-fluid content">
		<div class="row">
			<style>
@media only screen and (max-width: 767px) {
	.userbox:after {
		background: none
	}
	.userbox .hidden-xs {
		display: inline-block !important;
	}
}
</style>
			<div class="sidebar">
				<div class="sidebar-collapse">
					<div class="sidebar-header" style="background: none !important;">
						<img src="<%=betDir%>/Uploads/Picture/2018-03-01/5a9768310187f.png" class="img-responsive" alt="" style="width: auto; margin: 0 auto;" />
					</div>
					<div class="sidebar-menu">
						<nav id="menu" class="nav-main" role="navigation">
							<ul class="nav nav-sidebar" style="padding-top: 0;">
								<div class="panel-body text-center">
									<div class="flag">
										<div class="userbox">
											<div class="star">
												<%
													byte userType = ((UserInfo) session.getAttribute(SessionManage.SessionUser.USER_INFO.getName()))
															.getUserType();
													if (userType == 10) {
												%><span class="on"></span>  
												<%
													} else if (userType == 20) {
												%><span class="on"></span> <span class="on"></span>  
												<%
													} else if (userType == 30) {
												%><span class="on"></span> <span class="on"></span> <span class="on"></span> 
												<%
													} else if (userType == 40) {
												%><span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span>  
												<%
													} else if (userType == 50) {
												%><span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span>  
												<%
													} else if (userType == 60) {
												%><span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span>  
												<%
													} else if (userType == 70) {
												%><span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span> <span class="on"></span><span class="on"></span>
												<%
													} else {
												%> 
												<%
													}
												%>
											</div>
											<style>
.star {
	position: relative; top: -8px;
}

.star span {
	width: 25px; height: 25px; display: inline-block; background: url("<%=betDir%>/Public/Home/h+/images/yin-star.png") center center no-repeat; background-size: 90%; margin-left: 3px;
}

.star span.on {
	background: url("<%=betDir%>/Public/Home/h+/images/jin-star.png") no-repeat; background-size: 100%;
}
</style>
											<a href="#" class="dropdown-toggle" data-toggle="dropdown">
												<%-- <figure class="profile-picture hidden-xs">
													<%
														UserInfo userInfo = ((UserInfo) session.getAttribute(SessionManage.SessionUser.USER_INFO.getName()));
														if (userInfo.getPicUrl() == null || userInfo.getPicUrl().length() < 1) {
													%><img src="<%=betDir%>/Public/Home/h+/login/img/hg1.png" class="img-circle" alt="" style="" />
													<%
														} else {
													%><img src="<%=basePath%>${sessionScope.get('session_user_info').picUrl}" class="img-circle" alt="" style="" />
													<%
														}
													%>
												</figure> --%>
												<div class="profile-info" style="text-align: left;">
													<span class="name">账户：${sessionScope.get('session_user_info').userId}</span> <span class="name">可用彩金：${sessionScope.get('session_user_info').point/100}</span> <span class="name">体验彩金：${sessionScope.get('session_user_info').givePoint/100}</span>
												</div>
											</a>
										</div>
									</div>
								</div>
								<div style="text-align: center; margin-bottom: 10px;">
									<a href="rechargereply" type="button" class="btn btn-xs btn-danger" style="margin-right: 20px; color: white !important;">充值</a> <a href="wdreply" type="button" class="btn btn-xs  btn-success"
										style="margin-right: 15px; color: white !important;"
									>提现</a>
								</div>
								<li><a href="index" title="首页"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <i class="fa fa-home" aria-hidden="true"></i> <span>首页</span>
								</a></li>
								<li class="nav-parent"><a href="javascript:;" title="会员管理"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <i class="fa fa-user " aria-hidden="true"></i> <span>会员管理</span>
								</a>
									<ul class="nav nav-children">
										<li><a href="updatemembermsg"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 修改资料</span>
										</a></li>
										<li><a href="updatepwd"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 修改密码</span>
										</a></li>
										<li><a href="user_info"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 会员信息</span>
										</a></li>
									</ul></li>
								<li class="nav-parent"><a href="javascript:;" title="团队管理"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <i class="fa fa-users " aria-hidden="true"></i> <span>团队管理</span>
								</a>
									<ul class="nav nav-children">
										<li><a href="userindex"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 直推会员</span>
										</a></li>
										<li><a href="registuser"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 注册会员</span>
										</a></li>
										<%-- <li><a href="ttree"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 推荐关系</span>
										</a></li> --%>
										<li><a href="team_bets"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 团队信息</span>
										</a></li>
									</ul></li>
								<li class="nav-parent"><a href="javascript:;" title="奖金管理"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <i class="fa fa-krw " aria-hidden="true"></i> <span>奖金管理</span>
								</a>
									<ul class="nav nav-children">
										<li><a href="teamBonus"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 团队奖金</span>
										</a></li>
										<li><a href="teamLeaderBonus"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 领袖奖金</span>
										</a></li>
										<li><a href="bonus"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 奖金明细</span>
										</a></li>
										<li><a href="bonus_share"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 分享奖金明细</span>
										</a></li>
										<li><a href="bonus_agent"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 代理奖金明细</span>
										</a></li>
										<li><a href="bonus_agent_same"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 代理平级奖金明细</span>
										</a></li>
										<li><a href="bonus_benfit"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 福利奖金明细</span>
										</a></li>
										<li><a href="bonus_benfit_same"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 福利平级奖金明细</span>
										</a></li>
										<li><a href="financialflow"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 财务流水</span>
										</a></li>
									</ul></li>
								<li class="nav-parent"><a href="javascript:;" title="财务管理"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <i class="fa fa-rmb " aria-hidden="true"></i> <span>财务管理</span>
								</a>
									<ul class="nav nav-children">
										<li><a href="wdreply"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 提现申请</span>
										</a></li>
										<li><a href="wdrecord"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 提现记录</span>
										</a></li>
										<li><a href="rechargereply"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 充值申请</span>
										</a></li>
										<li><a href="rechargerecord"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 充值记录</span>
										</a></li>
										<li><a href="tfreply"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 会员转币</span>
									    </a></li> 
										<li><a href="tfrecord"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 转币记录</span>
										</a></li>
									</ul></li>
								<li class="nav-parent"><a href="javascript:;" title="足球赛事"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <i class="fa fa-gift  " aria-hidden="true"></i> <span>足球赛事</span>
								</a> 
									<ul class="nav nav-children">
										<li><a href="index"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 赛事列表</span>
										</a></li>
										<li><a href="transaction_detail"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 投注记录</span>
										</a></li>
										<li><a href="userBetReturnList"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 投注撤回记录</span>
										</a></li>
										<li><a href="game_log"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 投注结果</span>
										</a></li>
										<li><a href="game_result"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 赛事结果</span>
										</a></li>
									</ul></li>
								<li class="nav-parent"><a href="javascript:;" title="帮助中心"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <i class="fa fa-comments-o " aria-hidden="true"></i> <span>帮助中心</span>
								</a>
									<ul class="nav nav-children">
										<li><a href="news"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 公司公告</span>
										</a></li>
										<li><a href="pews"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 常见问题</span>
										</a></li>
										<li><a href="service_list"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 在线客服</span>
										</a></li>
										<li><a href="adviceList"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 意见反馈</span>
										</a></li>
										<li><a href="link_list"> <img src="<%=betDir%>/Public/Home/h+/images/nav-bj.jpg" /> <span> 友情链接</span>
										</a></li>
									</ul></li>
							</ul>
						</nav>
					</div>
				</div>
				<div class="sidebar-footer" style="display:none;">
					<div class="small-chart-visits">
						<script type="text/javascript">
			    var a=15;var b=16;var c=13;var d=12;var e=35;var f=16;var g=200;var h=105;var i=150;
				var sparklineLineVisitsData = [a,b, c, d,e, f, g,h, i, 15, 30, 45];
					$("#sparklineLineVisits").sparkline(sparklineLineVisitsData, {
						type: 'bar',
						width: '130',
						height: '30',
						lineColor: '#368ee0'
					});
			</script>
					</div>
				</div>
			</div>
			<div class="main ">
				<script type="text/javascript">
			var orderColumn='';
		var orderDir='desc';
		function setOrderDir(){
			if(orderDir=='desc'){
				orderDir='asc';
			}else{
				orderDir='desc';
			}
		}
		function resetOrderDir(){
			orderDir='asc';
		}
		function setOrderColumn(column){
			if(orderColumn==column){
				setOrderDir();
			}else{
				orderColumn=column;
				resetOrderDir();
			}
			getMoreData();
		}
		</script>
				<sitemesh:write property='body' />
			</div>
		</div>
	</div>
	<script src="<%=betDir%>/Public/Home/h+/js/validate/validate.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/validate/messages.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/common.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/check.js"></script>
	<script>
	function getNews(){
		ajaxDataJson("ajaxGetNewsList",{"topStatus":0},function(data){
			var list=data.data;
			var content='';
			for(var i=0;i<list.length;i++){
				var newsInfo=list[i];
				content+='<a href="shownews/'+newsInfo.newsId+'"><font color="#368ee0">'+newsInfo.title+'</font></a>';
			}
			$("#m_news").html(content);
		},function(errmsg){$("#m_news").html('暂无公告');});
	}
	getNews();
	</script>
</body>
</html>
