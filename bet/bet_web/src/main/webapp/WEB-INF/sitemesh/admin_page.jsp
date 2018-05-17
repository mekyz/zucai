<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="com.bet.utils.*"%>
<%@ page import="com.bet.orms.AdminInfo"%>
<%@ page import="com.lrcall.lrweb.common.utils.StringTools"%>
<%
	String basePath = request.getContextPath() + "/";
	String commonDir = "skin/common";
	String mobileDir = "skin/mobile";
	String pcDir = "skin/pc";
	String thirdDir = "../skin/third";
	String commonImgPath = basePath + commonDir + "/images";
	String commonJsPath = basePath + commonDir + "/js";
	String commonCssPath = basePath + commonDir + "/css";
	String mobileImgPath = basePath + mobileDir + "/images";
	String mobileJsPath = basePath + mobileDir + "/js";
	String mobileCssPath = basePath + mobileDir + "/css";
	String pcImgPath = basePath + pcDir + "/images";
	String pcJsPath = basePath + pcDir + "/js";
	String pcCssPath = basePath + pcDir + "/css";
	String preUrl = (String) session.getAttribute(SessionManage.SessionAdmin.ADMIN_PRE_URL.getName());
	if (StringTools.isNull(preUrl))
	{
		preUrl = "";
	}
	AdminInfo adminInfo = (AdminInfo) session.getAttribute(SessionManage.SessionAdmin.ADMIN_INFO.getName());
	int userLevel = adminInfo.getUserLevel();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%-- 设置标题 --%>
<title><sitemesh:write property='title'>DM SUPER CLUB</sitemesh:write></title>
<%-- 设置图标 --%>
<link rel="shortcut icon" href="<%=commonImgPath%>/logo.png" />
<%-- 设置兼容模式 --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%-- 设置字符集 --%>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<%-- 设置移动设备屏幕参数 --%>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<%-- 设置作者 --%>
<meta name="author" content="DM SUPER CLUB" />
<%-- 设置描述 --%>
<meta name="description" content="DM SUPER CLUB">
<%-- 浏览器不会自动调整文件的大小,也就是说是固定大小,不会随着浏览器拉伸缩放 --%>
<meta name="MobileOptimized" content="240" />
<%-- 添加到主屏幕后，全屏显示 --%>
<meta name="apple-touch-fullscreen" content="yes" />
<%-- 如果内容设置为YES，Web应用程序运行在全屏模式;否则，它不会。默认行为是使用Safari浏览器显示网页内容 --%>
<meta name="apple-mobile-web-app-capable" content="yes" />
<%-- 设置不缓存 --%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Expires" content="0">
<!-- Bootstrap -->
<link rel="stylesheet" href="<%=thirdDir%>/vendors/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="<%=thirdDir%>/vendors/font-awesome/css/font-awesome.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="<%=thirdDir%>/vendors/iCheck/skins/flat/green.css">
<!-- jQuery -->
<script src="<%=thirdDir%>/vendors/jquery/dist/jquery.min.js"></script>
<script src="<%=commonJsPath%>/layer/layer.js"></script>
<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
<script src="<%=pcJsPath%>/common.js?v=20180505"></script>
<sitemesh:write property='head' />
<!-- Custom Theme Style -->
<link rel="stylesheet" href="<%=thirdDir%>/build/css/custom.min.css">
<style type="text/css">
.profile_info {
	width: 100%; float: none; text-align: center;
}

.profile_pic {
	width: 100%; float: none;
}

.img-circle.profile_img {
	width: 30%; margin-left: 35%;
}

.dropdown-menu {
	left: auto; right: 0;
}
</style>
</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="index" class="site_title"><img src="<%=commonImgPath%>/logo.png" style="width: 30px; height: 30px;" /> <span>DM SUPER CLUB</span></a>
					</div>
					<div class="clearfix"></div>
					<!-- menu profile quick info -->
					<div class="profile clearfix">
						<div class="profile_pic">
							<%
								if (!StringTools.isNull(adminInfo.getPicUrl()))
								{
							%><img src="<%=basePath%><%=adminInfo.getPicUrl()%>" alt="..." class="img-circle profile_img">
							<%
								}
								else
								{
							%><img src="<%=commonImgPath%>/logo.png" alt="..." class="img-circle profile_img">
							<%
								}
							%>
						</div>
						<div class="profile_info">
							<h2>
								欢迎您,<%
								if (adminInfo.getName() != null)
								{
							%><%=adminInfo.getName()%>
								<%
									}
									else
									{
								%><%=adminInfo.getUserId()%>
								<%
									}
								%>
							</h2>
						</div>
					</div>
					<!-- /menu profile quick info -->
					<br />
					<!-- sidebar menu -->
					<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
						<div class="menu_section">
							<h3>数据管理</h3>
							<ul class="nav side-menu">
								<li><a><i class="fa fa-users"></i>用户管理<span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="manageUsers">用户列表</a></li>
										<li><a href="manageAdviceTypeInfos">意见反馈类型管理</a></li>
										<li><a href="manageAdvice">意见反馈管理</a></li>
									</ul></li>
								<li><a><i class="fa fa-users"></i>数据管理<span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="manageTeamInfos">球队信息管理</a></li>
										<li><a href="manageMatchInfos">赛事信息管理</a></li>
										<li><a href="manageUnOpenMatchInfos">未开奖赛事管理</a></li>
										<li><a href="manageMatchProfitInfos">赛事波胆管理</a></li>
										<li><a href="manageUserBetInfos">用户下注管理</a></li>
										<li><a href="manageUserBetReturnInfos">下注撤回管理</a></li>
										<li><a href="manageWithdrawInfos">用户提现管理</a></li>
										<li><a href="manageUserRechargeInfos">用户充值管理</a></li>
										<li><a href="manageUserExchangeInfos">用户转账管理</a></li>
									</ul></li>
							</ul>
						</div>
						<div class="menu_section">
							<h3>数据统计</h3>
							<ul class="nav side-menu">
								<li><a><i class="fa fa-bar-chart"></i>数据统计<span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="manageUserBalanceLogInfos">用户余额记录管理</a></li>
										<li><a href="manageUserBonusInfos">用户奖金管理</a></li>
										<li><a href="manageTeamProfitInfos">团队奖管理</a></li>
										<li><a href="manageTeamLeaderProfitInfos">团队领导奖管理</a></li>
										<li><a href="manageMatchStatisticsLogInfos">赛事统计信息</a></li>
										<li><a href="manageDayStatisticsLogInfos">每日数据统计</a></li>
										<li><a href="manageMonthStatisticsLogInfos">每月数据统计</a></li>
										<li><a href="manageDayUserStatisticsLogInfos">用户每日数据统计</a></li>
									</ul></li>
							</ul>
						</div>
						<div class="menu_section">
							<h3>基础设置</h3>
							<ul class="nav side-menu">
								<li><a><i class="fa fa-bell-o"></i>消息管理 <span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="manageNewsSorts">消息类别管理</a></li>
										<li><a href="manageNews">消息管理</a></li>
										<li><a href="manageMsgInfos">用户消息管理</a></li>
									</ul></li>
								<li><a><i class="fa fa-android"></i>客户端管理 <span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="manageClients">客户端管理</a></li>
										<!-- <li><a href="manageClientBannerInfos">客户端Banner管理</a></li> -->
										<li><a href="manageSmsCodeConfigs">验证码配置管理</a></li>
									</ul></li>
								<li><a><i class="fa fa-wrench"></i>更多 <span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="changePwd">修改密码</a></li>
										<%
											if (userLevel == 0)
											{
										%><!-- <li><a href="manageAdmins">客服管理</a></li> -->
										<%
											}
											else
											{
										%><li><a href="adminEdit">修改我的信息</a></li>
										<%
											}
										%>
										<li><a href="manageConfig">参数设置</a></li>
										<li><a href="managePayeeInfos">收款信息管理</a></li>
										<li><a href="managePayConfigInfos">支付配置管理</a></li>
										<li><a href="manageMatchProfitTemplateInfos">波胆模板管理</a></li>
										<li><a href="manageTeamProfitRateInfos">团队返利比率管理</a></li>
										<li><a href="manageTeamLeaderProfitConfigInfos">领导奖返利比率管理</a></li>
										<li><a href="manageUserTypeConditionInfos">用户升级条件管理</a></li>
									</ul></li>
							</ul>
						</div>
					</div>
					<!-- /sidebar menu -->
					<!-- /menu footer buttons -->
					<div class="sidebar-footer hidden-small">
						<a data-toggle="tooltip" data-placement="top" title="主页" href="index"> <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
						</a><a data-toggle="tooltip" data-placement="top" title="参数设置" href="manageConfig"> <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="关于我们" href="about"> <span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="退出登录" href="logout"> <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
						</a>
					</div>
					<!-- /menu footer buttons -->
				</div>
			</div>
			<!-- top navigation -->
			<div class="top_nav">
				<div class="nav_menu">
					<nav>
						<div class="nav toggle">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>
						<ul class="nav navbar-nav navbar-right">
							<li class=""><a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false"> <%
 	if (!StringTools.isNull(adminInfo.getPicUrl()))
 	{
 %><img src="<%=basePath%><%=adminInfo.getPicUrl()%>" alt=""> <%
 	}
 	else
 	{
 %><img src="<%=commonImgPath%>/logo.png" alt=""> <%
 	}
 %> <%
 	if (adminInfo.getName() != null)
 	{
 %><%=adminInfo.getName()%> <%
 	}
 	else
 	{
 %><%=adminInfo.getUserId()%> <%
 	}
 %><span class=" fa fa-angle-down"></span>
							</a>
								<ul class="dropdown-menu dropdown-usermenu pull-right">
									<li><a href="adminEdit?userId=<%=adminInfo.getUserId()%>"> <span>账户信息</span>
									</a></li>
									<li><a href="logout"><i class="fa fa-sign-out pull-right"></i>退出登录</a></li>
								</ul></li>
						</ul>
					</nav>
				</div>
			</div>
			<!-- /top navigation -->
			<!-- page content -->
			<div class="right_col" role="main">
				<sitemesh:write property='body' />
			</div>
			<!-- /page content -->
			<!-- footer content -->
			<footer>
				<div class="pull-right">© 2016-2020</div>
				<div class="clearfix"></div>
			</footer>
			<!-- /footer content -->
		</div>
	</div>
	<!-- Bootstrap -->
	<script src="<%=thirdDir%>/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script src="<%=thirdDir%>/vendors/fastclick/lib/fastclick.js"></script>
	<!-- iCheck -->
	<script src="<%=thirdDir%>/vendors/iCheck/icheck.min.js"></script>
	<!-- Custom Theme Scripts -->
	<script src="<%=thirdDir%>/build/js/custom.min.js"></script>
</body>
</html>
