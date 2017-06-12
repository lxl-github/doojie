<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx}/static/front/css/timeline.css" type="text/css" />
<title>${website_name} | 交易详情</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">交易详情&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<section id="listContent"  class="list"></section>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
		$().ready(function(){
			//菜单加颜色提示
			$("#my").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			init();
		});
		
		function init() {
			showLoading();
			//循环数据
			$.post("${ctx}/webApp/user/myTradeDetail",{tradeId:${tradeId}},function(data) {
				if(data == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/login";
				}else{
					if (data != null) {
						var listContent = "";
						listContent +='<div class="panel panel-default">';
// 						listContent +='<div class="panel-heading">';
// 						listContent +='<h3 class="panel-title" style="font-weight:bold;"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;交易信息</h3>';
// 						listContent +='</div>';
						listContent +='<table class="table">';
						listContent +='<tr>';
						listContent +='<td width="30%">交易编号:</td>';
						listContent +='<td>'+data.tradeCode+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>支付方式:</td>';
						listContent +='<td>';
						if(data.payMode == 1){
							listContent +='微信&nbsp;';
						}else if(data.payMode == 2){
							listContent +='支付宝&nbsp;';
						}
						listContent +='</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>支付金额:</td>';
						listContent +='<td>'+data.money+'元</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>支付时间:</td>';
						listContent +='<td>'+data.payDate+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>流水号:</td>';
						listContent +='<td>'+data.serialNumber+'</td>';
						listContent +='</tr>';
						listContent +='</table>';
						listContent +='</div>';
						
						listContent +='<div class="panel panel-default">';
						listContent +='<div class="panel-body" style="background-color:#eeeaaa;font-weight: bold;">购卡信息</div>';
// 						listContent +='<div class="panel-heading">';
// 						listContent +='<h3 class="panel-title" style="font-weight:bold;"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;购买信息</h3>';
// 						listContent +='</div>';
						listContent +='<table class="table">';
						listContent +='<tr>';
						listContent +='<td width="30%">购卡名称:</td>';
						listContent +='<td>'+data.userCardVo.productName+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>使用方式:</td>';
						listContent +='<td>';
						if(data.userCardVo.productIsDoor == 0){
							listContent +='到店&nbsp;';
						}else if(data.userCardVo.productIsDoor == 1){
							listContent +='上门&nbsp;';
						}
						listContent +='</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>可用次数:</td>';
						listContent +='<td>'+data.userCardVo.number+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>有效期至:</td>';
						listContent +='<td>'+data.userCardVo.expiredDate+'</td>';
						listContent +='</tr>';
						listContent +='</table>';
						listContent +='</div>';
						$("#listContent").html(listContent);
					} else {
						$("#contentDownContent").show();
						$("#contentDownContent").html("暂无数据");
					}
				}
				hideLoading();
			}, "json");
		}
	</script>
</body>
</html>
