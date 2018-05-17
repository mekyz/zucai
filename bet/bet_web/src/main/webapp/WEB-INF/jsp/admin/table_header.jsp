<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	String basePath1 = request.getContextPath() + "/";
	String commonDir1 = "skin/common";
	String mobileDir1 = "skin/mobile";
	String pcDir1 = "skin/pc";
	String thirdDir1 = "../skin/third";
	String commonImgPath1 = basePath1 + commonDir1 + "/images";
	String commonJsPath1 = basePath1 + commonDir1 + "/js";
	String commonCssPath1 = basePath1 + commonDir1 + "/css";
	String mobileImgPath1 = basePath1 + mobileDir1 + "/images";
	String mobileJsPath1 = basePath1 + mobileDir1 + "/js";
	String mobileCssPath1 = basePath1 + mobileDir1 + "/css";
	String pcImgPath1 = basePath1 + pcDir1 + "/images";
	String pcJsPath1 = basePath1 + pcDir1 + "/js";
	String pcCssPath1 = basePath1 + pcDir1 + "/css";
	String currentPath1 = basePath1 + "admin/";
%>
<link href="<%=thirdDir1%>/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
<link href="<%=thirdDir1%>/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
<link href="<%=thirdDir1%>/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
<link href="<%=thirdDir1%>/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
<link href="<%=thirdDir1%>/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
<script src="<%=pcJsPath1%>/tables.js"></script>
<link rel="stylesheet" href="<%=pcCssPath1%>/manageExt.css">