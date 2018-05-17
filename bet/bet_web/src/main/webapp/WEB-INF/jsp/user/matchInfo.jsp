<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<head>
<meta charset="UTF-8">
<title></title>
</head>
<!-- style="background: url(<%=betDir%>/Public/Home/h+/images/timg.jpg)" -->
<body >
	<style>
.foot-mobile {
	height: auto; overflow: hidden; min-height: 100%; padding-top: 18px; margin: 0 13px; display: block;
}

.foot-mobile ul {
	margin: 0; padding: 0;
}

.foot-mobile ul li {
	width: 100%;
	/* background: rgba(255,255,255,1); */ margin-bottom: 7px; border-radius: 5px; position: relative;
}

.foot-team {
	width: 100%; height: auto; overflow: hidden;
}

.foot-team .div {
	width: 33.33%; float: left; color: #959595; text-align: center;
}

.foot-team .div b {
	width: 100%; display: block; color: #212121;
}

.foot-team .div p, .foot-team .div span {
	margin: 0; color: #212121; font-size: 13px; margin-bottom: 3px;
}

.foot-mobile ul li>p {
	display: block; width: 33.33%; float: left; text-align: center;
}

.foot-mobile ul li>p span {
	display: block;
}

.foot-mobile ul li>p em {
	font-size: 18px; font-weight: bold; font-style: normal; margin-top: 5px; display: block;
}

.foot-mobile button {
	width: 100%; padding: 10px 0; background: #00abef; border: none; border-radius: 3px; color: #FFFFFF; margin-top: 20px;
}
</style>
	<div class="foot-mobile">
		<ul>
			<li style="padding-bottom: 20px;">
				<div class="foot-team">
					<div class="div">
						<c:choose>
							<c:when test="${matchInfo.homeTeamPicUrl!=null && matchInfo.homeTeamPicUrl.length() > 0}">
								<img src="<%=commonImgPath%>/${matchInfo.homeTeamPicUrl}" style="height: 35px;">
							</c:when>
							<c:otherwise>
								<img src="<%=commonImgPath%>/ic_header.png" style="height: 35px;">
							</c:otherwise>
						</c:choose>
						<b style="">${matchInfo.homeTeam}</b>
					</div>
					<div class="div">
						<p>&nbsp;</p>
						<span>${matchInfo.matchName}</span>
					</div>
					<div class="div">
						<c:choose>
							<c:when test="${matchInfo.awayTeamPicUrl!=null && matchInfo.awayTeamPicUrl.length() > 0}">
								<img src="<%=commonImgPath%>/${matchInfo.awayTeamPicUrl}" style="height: 35px;">
							</c:when>
							<c:otherwise>
								<img src="<%=commonImgPath%>/ic_header.png" style="height: 35px;">
							</c:otherwise>
						</c:choose>
						<b style="">${matchInfo.awayTeam}</b>
					</div>
				</div>
			</li>
			<li style="border-top: 1px solid #DCDCDC; padding-top: 10px;">
				<p>
					<span>全场波胆</span> <em>${matchInfo.finalScore1}:${matchInfo.finalScore2}</em>
				</p>
				<p>
					<span>上半场波胆</span> <em>${matchInfo.halfScore1}:${matchInfo.halfScore2}</em>
				</p>
				<p>
					<span>总进球数/角球数</span> ${matchInfo.handicap}
				</p>
			</li>
		</ul>
		<button type="button" onclick="aaaa()">确定</button>
	</div>
	<script>
		function aaaa(){
			parent.layer.closeAll();
		}
	</script>
</body>
</html>