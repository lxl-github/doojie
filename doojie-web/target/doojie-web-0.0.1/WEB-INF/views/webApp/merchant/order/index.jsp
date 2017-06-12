<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 我的订单</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">我的订单&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div id="contentTip" style="font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 25px;background-color: #ccc;"></div>
	<div id="list" >
		<section id="listContent"  class="list">
	      
    	</section>
	</div>
	<div id="contentDownTip"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<script type="text/javascript">
		var pageNumber = 1;
		var totalPage = 0;
		var flag = true;
		function orderDetail(orderId){
			window.location.href = "${ctx}/webApp/toMerchantOrderDetail/"+orderId;
		}
		$().ready(function(){
			//菜单加颜色提示
			$("#order").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			
			//页面下拉加载数据
			window.onscroll = function () {
				if(falg){
					$("#contentDownTip").show();
					$("#contentDownTip").html('<span><img src="${ctx}/static/front/images/small_loading.gif"/></span>&nbsp;加载中...');
					 if (getScrollTop() + getClientHeight() == getScrollHeight()) {
						 var cupage = pageNumber*1+1;
						 if(cupage > totalPage){
							 $("#contentDownTip").hide();
							 $("#contentDownContent").show();
							 $("#contentDownContent").html("已经到底部了");
							 setTimeout(function(){
								 $("#contentDownContent").hide();
							 },500);
							 return;
						 }
						 init(cupage);
					 }
				}
			}
			init(pageNumber);
		});
		
		function init(pageNum) {
			flag = false;
			//增加显示数据加载中。。。提示
			$("#contentTip").html("数据加载中...");
			//循环数据
			$.post("${ctx}/webApp/merchant/myOrder",{currentPage:pageNum},function(data) {
				if(data == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_merchant_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/merchantLogin";
				}else{
					if (data.totalPage > 0) {
						//总页数
						totalPage = data.totalPage;
						pageNumber = pageNum;
						var listContent = "";
						$(data.results).each(
								function(index) {
									var order = data.results[index];
									/**         加载列表                           **/
									listContent +='<div class="panel panel-default">';
									listContent +='<div class="panel-heading">';
									listContent +='<h3 class="panel-title" style="font-weight:bold;"><span class="glyphicon glyphicon-leaf"></span>&nbsp;'+order.orderSn+'</h3>';
									listContent +='</div>';
// 									listContent +='<div class="panel-body" style="background-color:#eee;"></div>';
									listContent +='<table class="table">';
									listContent +='<tr>';
									listContent +='<td>预约时间:</td>';
									listContent +='<td>'+order.appointmentTime+'</td>';
									listContent +='</tr>';
									listContent +='<tr>';
									listContent +='<td>订单状态:</td>';
									listContent +='<td class = "gongli">';
									if(order.status == 1){
										listContent +='<span>待处理</span>&nbsp;';
									}else if(order.status == 2){
										listContent +='<span >已取消</span>&nbsp;';
									}else if(order.status == 4){
										listContent +='<span>处理中</span>&nbsp;';
									}else if(order.status == 5){
										listContent +='<span>待客户确认</span>&nbsp;';
									}else if(order.status == 7){
										listContent +='<span>已完成</span>&nbsp;';
									}
									listContent +='</td>';
									listContent +='</tr>';
									listContent +='<tr>';
									listContent +='<td colspan="2"><a href="javascript:;" onclick="orderDetail('+order.id+')"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;订单详情</a></td>';
									listContent +='</tr>';
									listContent +='</table>';
									listContent +='</div>';
									
								});
						$("#contentTip").hide();
						$("#contentDownTip").hide();
						$("#listContent").append(listContent);
					} else {
						$("#contentTip").hide();
						$("#contentDownTip").hide();
						$("#contentDownContent").show();
						$("#contentDownContent").html("您还没有订单");
					}
				}
			}, "json");
			flag = true;
		}
	</script>
</body>
</html>
