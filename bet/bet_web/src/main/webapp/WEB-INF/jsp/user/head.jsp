<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	String basePath = request.getContextPath() + "/";
	String commonDir = "skin/common";
	String mobileDir = "skin/mobile";
	String pcDir = "skin/pc";
	String betDir = basePath + "skin/bet";
	String commonImgPath = basePath + commonDir + "/images";
	String commonJsPath = basePath + commonDir + "/js";
	String commonCssPath = basePath + commonDir + "/css";
	String mobileImgPath = basePath + mobileDir + "/images";
	String mobileJsPath = basePath + mobileDir + "/js";
	String mobileCssPath = basePath + mobileDir + "/css";
	String pcImgPath = basePath + pcDir + "/images";
	String pcJsPath = basePath + pcDir + "/js";
	String pcCssPath = basePath + pcDir + "/css";
	String currentPath = basePath + "user/";
	final int TAB_COUNT = 3;
%>
