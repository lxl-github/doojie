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
	<section id="recommendContent"  class="list"></section>
	<div id="gengzong" style="display: none;" class="list panel panel-default">
		<div class="panel-body" style="background-color:#eeeaaa;font-weight: bold;">跟踪信息</div>
		<section id="cd-timeline" class="cd-container"></section>
	</div>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
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
		
		function goRecommend(orderId){
			window.location.href = "${ctx}/webApp/user/toRecommend/"+orderId;
		}
		
		function goConfirm(op,status){
			if(confirm("确认消费完成?")){
				$.post("${ctx}/webApp/user/orderConfim",{orderId:op,oldStatus:status},function(data) {
					if(data == "-100"){
						//将页面记录到cookie，用于登录后跳转
						$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
						//跳转登录页
						window.location.href="${ctx}/webApp/login";
					}else{
						if(data == true){
							Alert("确认成功");
							reload();
						}else{
							Alert("确认失败,请稍后再试");
						}
					}
				});
			}
		}
		
		function init() {
			//循环数据
			$.post("${ctx}/webApp/user/myOrderDetail",{orderId:${orderId}},function(data) {
				if(data == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/login";
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
							listContent +='<span>下单成功</span>&nbsp;';
						}else if(data.status == 2){
							listContent +='<span >已取消</span>&nbsp;';
						}else if(data.status == 3){
							listContent +='<span>已派单</span>&nbsp;';
						}else if(data.status == 4){
							listContent +='<span>处理中</span>&nbsp;';
						}else if(data.status == 5){
							listContent +='<span>处理完成</span>&nbsp;';
						}else if(data.status == 6){
							listContent +='<span>无效订单</span>&nbsp;';
						}else if(data.status == 7){
							listContent +='<span>消费完成</span>&nbsp;';
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
						var brandName = "";
						var modelName = "";
						var color = "";
						if(data.brand!=null){  brandName = data.brand.brandName +'-';}
						if(data.models!=null){  modelName = data.models.modelName;}
						if(data.color!=null){  color = data.color;}
						listContent +='<td>'+brandName + modelName +'&nbsp;'+ color +'&nbsp;'+ data.plateNumber+'</td>';
						listContent +='</tr>';
						if(data.orderOwn == 2){
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
						}else{
							listContent +='<tr>';
							listContent +='<td>位置信息:</td>';
							listContent +='<td>'+data.regionCity.name+' - '+data.regionDistrict.name+' - '+data.regionBussinessDistrict.name +'-'+ data.regionDetail +'&nbsp;</td>';
							listContent +='</tr>';
							if(data.status != 1 && data.status != 2 && data.status != 6){
								listContent +='<tr>';
								listContent +='<td>处理人:</td>';
								listContent +='<td>'+data.employeeWorkRecordVo.employee.name+'&nbsp;</td>';
								listContent +='</tr>';
								listContent +='<tr>';
								listContent +='<td>处理人电话:</td>';
								listContent +='<td><a href="tel:'+data.employeeWorkRecordVo.employee.phone+'"'+data.employeeWorkRecordVo.employee.phone+'</a>&nbsp;</td>';
								listContent +='</tr>';
							}else if(data.employeeWorkRecordVo != null && data.employeeWorkRecordVo.status == 2){
								listContent +='<tr>';
								listContent +='<td>处理前照片:</td>';
								listContent +='<td><img src="${ctx}/static/washImg/'+data.employeeWorkRecordVo.washBeforePhoto+'/>&nbsp;</td>';
								listContent +='</tr>';
							}else if(data.employeeWorkRecordVo != null && data.employeeWorkRecordVo.status == 3){
								listContent +='<tr>';
								listContent +='<td>处理后照片:</td>';
								listContent +='<td><img src="${ctx}/static/washImg/'+data.employeeWorkRecordVo.washAfterPhoto+'/>&nbsp;</td>';
								listContent +='</tr>';
							}
						}
						if(data.status == 5){
							listContent +='<tr>';
							listContent +='<td colspan="2"><a href="javascript:;" onclick="goConfirm('+data.id+','+data.status+')"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;确认</a></td>';
							listContent +='</tr>';
						}
						
						listContent +='</table>';
						listContent +='</div>';
						$("#listContent").html(listContent);
						
						var recommendContent = '';
						if(data.orderOwn == 2){
							if(data.isRecommend == 1 && data.status == 7){
								recommendContent +='<a href="javascript:;" onclick="goRecommend('+data.id+')">';
								recommendContent +='<div class="panel panel-default">';
								recommendContent +='<table class="table">';
								recommendContent +='<tr>';
								recommendContent +='<td class = "gongli" style="font-size:20px;">';
								if(data.recommend.recommendIndex == 1){
									recommendContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}else if(data.recommend.recommendIndex == 2){
									recommendContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}else if(data.recommend.recommendIndex == 3){
									recommendContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}else if(data.recommend.recommendIndex == 4){
									recommendContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}else if(data.recommend.recommendIndex == 5){
									recommendContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}
								recommendContent +='</td>';
								recommendContent +='<td>';
								recommendContent +='<span>'+data.recommend.recommendIndex+' 分</span>';
								recommendContent +='</td>';
								recommendContent +='</tr>';
								recommendContent +='</table>';
								recommendContent +='</div>';
								recommendContent +='</a>';
							}else if(data.isRecommend == 0 && data.status == 7){
								recommendContent +='<a href="javascript:;" onclick="goRecommend('+data.id+')">';
								recommendContent +='<div class="panel panel-default">';
								recommendContent +='<table class="table">';
								recommendContent +='<tr>';
								recommendContent +='<td><span class="glyphicon glyphicon-duplicate"></span>&nbsp;评价</td>';
								recommendContent +='</tr>';
								recommendContent +='</table>';
								recommendContent +='</div>';
								recommendContent +='</a>';
							}
						}
						
						$("#recommendContent").html(recommendContent);
						
						var timeline = '';
                        var count = 1;
						$(data.orderTrackList).each(
								function(index) {
									var orderTrack = data.orderTrackList[index];
                                    /**         加载列表                           **/
                                    timeline +='<div class="cd-timeline-block">';
                                    if(count == 1){
                                        timeline +='<div class="cd-timeline-img"></div>';
                                    }else{
                                        timeline +='<div class="cd-timeline-img2"></div>';
                                    }
                                    timeline +='<div class="cd-timeline-content">';
                                    timeline +='<span class="cd-content" >'+orderTrack.content+'</sapn>';
                                    timeline +='<span class="cd-date">'+orderTrack.createDate+'</span>';
                                    timeline +='</div>';
                                    timeline +='</div>';
                                    count = count + 1;
								});
						$("#gengzong").show();
						$("#cd-timeline").html(timeline);
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
