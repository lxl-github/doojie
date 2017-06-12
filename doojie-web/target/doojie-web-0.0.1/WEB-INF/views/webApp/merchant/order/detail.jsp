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
<title>${website_name} | 订单详情</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">订单详情&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<section id="listContent"  class="list"></section>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<%@include file="/WEB-INF/views/common/loading.jsp"%>
	<script type="text/javascript">
		$().ready(function(){
			//菜单加颜色提示
			$("#order").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			showLoading();
			init();
		});
		
		//重新载入页面
		function reload(){
			setTimeout(function () { 
				location.reload();
		    }, 500);
		}
		
		//开始处理
		function goStartHandle(op,status){
			if(confirm("确认订单信息?")){
				showLoading("处理中...");
				$.post("${ctx}/webApp/merchant/orderStartHandle",{orderId:op,oldStatus:status},function(data) {
					if(data == "-100"){
						//将页面记录到cookie，用于登录后跳转
						$.cookie("target_merchant_herf",window.location.href + '', { path: '${ctx}' });
						//跳转登录页
						window.location.href="${ctx}/webApp/merchantLogin";
					}else{
						if(data == true){
							Alert("操作成功");
							reload();
						}else{
							Alert("操作失败,请稍后再试");
						}
						hideLoading();
					}
				});
			}
		}
		
		//处理完成
		function goCompleteHandle(op,status){
			if(confirm("确认处理完成?")){
				showLoading("处理中...");
				$.post("${ctx}/webApp/merchant/orderCompleteHandle",{orderId:op,oldStatus:status},function(data) {
					if(data == "-100"){
						//将页面记录到cookie，用于登录后跳转
						$.cookie("target_merchant_herf",window.location.href + '', { path: '${ctx}' });
						//跳转登录页
						window.location.href="${ctx}/webApp/merchantLogin";
					}else{
						if(data == true){
							Alert("操作成功");
							reload();
						}else{
							Alert("操作失败,请稍后再试");
						}
						hideLoading();
					}
				});
			}
		}
		
		function init() {
			//循环数据
			$.post("${ctx}/webApp/merchant/myOrderDetail",{orderId:${orderId}},function(data) {
				if(data == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_merchant_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/merchantLogin";
				}else{
					if (data != null) {
						var listContent = "";
						listContent +='<div class="panel panel-default">';
						listContent +='<table class="table">';
						listContent +='<tr>';
						listContent +='<td width="30%">订单号:</td>';
						listContent +='<td>'+data.orderSn+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td >订单状态:</td>';
						listContent +='<td class = "gongli">';
						if(data.status == 1){
							listContent +='<span>待处理</span>&nbsp;';
						}else if(data.status == 2){
							listContent +='<span >已取消</span>&nbsp;';
						}else if(data.status == 4){
							listContent +='<span>处理中</span>&nbsp;';
						}else if(data.status == 5){
							listContent +='<span>待客户确认</span>&nbsp;';
						}else if(data.status == 7){
							listContent +='<span>已完成</span>&nbsp;';
						}
						listContent +='</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>服务类型:</td>';
						if(data.orderType == 1){
							listContent +='<td>洗车</td>';
						}else if (data.orderType == 2){
							listContent +='<td>维修</td>';
						}else if (data.orderType == 3){
							listContent +='<td>保养</td>';
						}
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>预约电话:</td>';
						listContent +='<td>'+data.mobileNumber+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>预约时间:</td>';
						listContent +='<td>'+data.appointmentTime+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>车辆信息:</td>';
						listContent +='<td>'+ data.plateNumber+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>车行名称:</td>';
						listContent +='<td>'+data.bussiness.name+'</td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>车行电话:</td>';
						listContent +='<td><a herf="tel:'+data.bussiness.tel+'">'+data.bussiness.tel+'</a></td>';
						listContent +='</tr>';
						listContent +='<tr>';
						listContent +='<td>车行地址:</td>';
						listContent +='<td>'+data.bussiness.address+'</td>';
						listContent +='</tr>';
						if(data.status == 1){
							listContent +='<tr>';
							listContent +='<td colspan="2"><a href="javascript:;" onclick="goStartHandle('+data.id+','+data.status+')"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;确认订单</a></td>';
							listContent +='</tr>';
						}
						
						if(data.status == 4){
							listContent +='<tr>';
							listContent +='<td colspan="2"><a href="javascript:;" onclick="goCompleteHandle('+data.id+','+data.status+')"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;处理完成</a></td>';
							listContent +='</tr>';
						}
						
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
