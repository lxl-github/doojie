<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 评价列表</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">评价列表&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div id="contentTip" style="font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 25px;background-color: #ccc;"></div>
	<div id="list" >
		<section id="listContent" class="list">
	      
    	</section>
	</div>
	<div id="contentDownTip"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
	var pageNumber = 1;
	var totalPage = 0;
	var flag = true;
		$().ready(function(){
			//菜单加颜色提示
			$("#index").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			
			//页面下拉加载数据
			window.onscroll = function () { 
				if(flag){
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
						 init(${bussinessId},cupage);
					 }
				}
			}
			init(${bussinessId},pageNumber);
		});
		
		function init(id,pageNum) {
			showLoading();
			flag = false;
			//增加显示数据加载中。。。提示
			$("#contentTip").html("数据加载中...");
			//循环数据
			$.post("${ctx}/webApp/recommendList",{bussinessId:id,currentPage:pageNum},function(data) {
				if (data.totalPage > 0) {
					//总页数
					totalPage = data.totalPage;
					pageNumber = pageNum;
					var listContent = "";
					$(data.results).each(
							function(index) {
								var recommendVo = data.results[index];
								var mobile = mobileReplace(recommendVo.mobile);
								/**         加载列表                           **/
								listContent +='<div class="panel panel-default">';
								listContent +='<div class="panel-heading">';
								listContent +='<h3 class="panel-title" style="font-weight:bold;"><span class="glyphicon glyphicon-user"></span>&nbsp;'+mobile+'</h3>';
								listContent +='</div>';
								listContent +='<div class="panel-body">'+recommendVo.reasons+'</div>';
								listContent +='<table class="table">';
								listContent +='<tr>';
								listContent +='<td>服务指数:</td>';
								listContent +='<td class = "gongli">';
								if(recommendVo.recommendIndex == 1){
									listContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}else if(recommendVo.recommendIndex == 2){
									listContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>';
								}else if(recommendVo.recommendIndex == 3){
									listContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}else if(recommendVo.recommendIndex == 4){
									listContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}else if(recommendVo.recommendIndex == 5){
									listContent +='<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;';
								}
								listContent +='</td>';
								listContent +='</tr>';
								listContent +='<tr>';
								listContent +='<td>评价时间:</td>';
								listContent +='<td>'+recommendVo.createDate+'</td>';
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
					$("#contentDownContent").html("还没有评价，沙发等你来抢");
				}
				hideLoading();
			}, "json");
			flag = true;
		}
		
	</script>
</body>
</html>
