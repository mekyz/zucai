<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp"%>
<base href="<%=currentPath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title></title>
<script src="<%=betDir%>/Public/Home/h+/js/jquery.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/jquery-2.1.1.min.js"></script>
<script src="<%=betDir%>/Public/Home/h+/js/layui/layui.js"></script>
<script src="<%=commonJsPath%>/common.js?v=20180505"></script>
<script src="<%=mobileJsPath%>/common.js?v=20180505"></script>
</head>
<body>
	<style>
/* min-width */
@media screen and (min-width: 900px) {
	html, body {
		font-size: 14px !important;
	}
}
/* min-width & max-width */
@media screen and (min-width: 600px) and (max-width: 900px) {
	html, body {
		font-size: 13px !important;
	}
}

@media screen and (max-width: 600px) {
	html, body {
		font-size: 12px !important;
	}
}
/* max device width */
@media screen and (max-width: 480px) {
	html, body {
		font-size: 12px !important;
	}
}

em, i {
	font-style: normal;
}

.box {
	width: 100%; font-size: 1.25rem; font-family: "微软雅黑";
}

.box-title {
	margin-top: 20px;
}

.box-title em {
	float: right; margin-right: 10px;
}

.box-title>span, .box-title>p {
	margin-left: 10px;
}

.box-title>p {
	color: #1a619f;
}

.box-title>p b {
	margin: 0 5px; font-size: 1.5rem;
}

.box-title>p em {
	color: red; font-weight: bolder;
}

.box table {
	width: 100%; text-align: center;
}

.box table th {
	background: #1a619f; padding: 5px 0; color: #FFFFFF;
}

.box table td {
	padding: 10px 0; position: relative;
}

.box table input[type='text'] {
	width: 8rem; display: inline-block; padding: 7px; border-radius: 4px; border: 1px solid #dcdcdc;
}

.box table td em {
	position: absolute; top: 14px; left: -5px;
}

.box table td span {
	font-size: 1rem; float: right;
}

.box table td a {
	display: inline-block; width: 5.5rem; height: 35px; text-align: center; line-height: 35px; border: 1px solid #dcdcdc; cursor: pointer;
}

.box table td a.hover {
	background: #2e81e1; border-color: #2e81e1; color: #FFFFFF;
}

.box table td label {
	float: left; margin-left: 7px;
}

.box table td label input {
	position: relative; top: 2px;
}

.box table td p {
	float: right; margin: 0;
}

.box table td button {
	width: 40%; padding: 5px 0;
}

.layui-layer-content {
	text-align: center !important
}

.box>h2 {
	font-size: 18px; font-weight: normal; text-align: center; padding: 7px 0; background: #1f5392; /* Old browsers */
	background: -moz-linear-gradient(left, #1f5392 0%, #458dca 35%, #458dca 65%, #1f5392 100%); /* FF3.6-15 */
	background: -webkit-linear-gradient(left, #1f5392 0%, #458dca 35%, #458dca 65%, #1f5392 100%); /* Chrome10-25,Safari5.1-6 */
	background: linear-gradient(to right, #1f5392 0%, #458dca 35%, #458dca 65%, #1f5392 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#1f5392', endColorstr='#1f5392', GradientType=1); /* IE6-9 */ color: #ffffff;
}

.box>h2 em {
	color: red; margin: 0 5px; color: #fff900;
}
</style>
	<div class="box">
		<h2>
			您正在<em>反对</em>波胆 ${matchProfitInfo.score1}:${matchProfitInfo.score2}
		</h2>
		<form method="post" class="form-horizontal " id="commentForm">
			<input type="hidden" id="profitId" name="profitId" value="${matchProfitInfo.profitId}" />
			<div class="box-title">
				<span><em>波胆</em> <script>
					writeDate('${matchInfo.matchDate}');
				</script> [${matchInfo.matchName}]</span>
				<p>
					<em>${matchProfitInfo.score1}:${matchProfitInfo.score2}</em>${matchInfo.homeTeam}<i>(主)</i><b>vs</b>${matchInfo.awayTeam}
				</p>
			</div>
			<table>
				<tr>
					<th style="width: 11rem;">交易金额</th>
					<th>获利 %</th>
					<th>预估获利</th>
				</tr>
				<tr>
					<td><input type="text" name="money" id="money" value="" /></td>
					<td><em>x</em>${matchProfitInfo.profitPercent/100} %</td>
					<td><em>=</em><i class="profit"></i></td>
				</tr>
				<!-- <tr>
					<td colspan="3"><span>*税费：5 %</span></td>
				</tr> -->
				<tr>
					<td colspan="3"><a data-money="100" onclick="paymoney(this)">100</a><a data-money="500" onclick="paymoney(this)">500</a><a data-money="1000" onclick="paymoney(this)">1000</a> <a
						data-money="${userInfo.point/100}" onclick="paymoney(this)"
					>全部</a></td>
				</tr>
				<tr>
					<td colspan="3" style="padding: 5px 0"><label> <input type="radio" name="moneytype" id="radio" value="11" checked="checked" />彩金钱包
					</label>
						<p style="width: 160px; text-align: left; display: inline;">可用彩金：${userInfo.point/100}</p></td>
				</tr>
				<tr>
					<td colspan="3" style="padding: 5px 0"><label> <input type="radio" name="moneytype" id="radio" value="13" />体验彩金
					</label>
						<p style="width: 160px; text-align: left; display: inline;">可用彩金：${userInfo.givePoint/100}</p></td>
				</tr>
				<tr>
					<td colspan="3">
						<button type="button" onclick="formSubmit()" class="btn btn-sm btn-info" target-form='form-horizontal' data-id="1" style="color: red;">确定交易</button>
						<button type="button" class="btn false" style="color: #777;">取消交易</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script src="<%=betDir%>/Public/Home/h+/js/validate/validate.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/validate/messages.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/common.js"></script>
	<script src="<%=betDir%>/Public/Home/h+/js/check.js"></script>
	<script>
		function paymoney(o){
			var money=parseInt($(o).attr('data-money'));
			$('#money').val(money);
			$('.profit').html(money*'${matchProfitInfo.profitPercent}'/10000);
		}
		$('#money').blur(function(){
			var money=parseInt($(this).val());
			$('.profit').html(money*'${matchProfitInfo.profitPercent}'/10000);
		});
		$('table tr td a').click(function(){
			$(this).addClass('hover').siblings().removeClass('hover');
		});
		$('.false').click(function(){
			parent.layer.closeAll();
		});
		function formSubmit(){
			var profitId=$("#profitId").val();
			var money=$("#money").val();
			var moneyUnit=$("input[name='moneytype']:checked").val();
			$.ajax({url:"ajaxAddUserBetInfo",type:"post",data:{'profitId':profitId,'money':money*100,'moneyUnit':moneyUnit},success:function(res){
				if(res.code==0){
					//parent.layer.closeAll();
					$("#money").val('');
					layer.msg('下注成功！');
				}else{
					layer.msg(res.msg);
				}
			}});
		}
	</script>
</body>
</html>