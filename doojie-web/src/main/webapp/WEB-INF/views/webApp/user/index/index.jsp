<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 首页</title>
<link rel="stylesheet" href="${ctx}/static/front/css/flickity-docs.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/front/js/flickity-docs.min.js"></script>
<script type="text/javascript">
$().ready(function(){
	
	hideLoading();
	//菜单加颜色提示
	$("#index").attr("style","color:#2885EC");
	
	//到店预约
	$("#toStore").on("click",function (){
		window.location.href = "${ctx}/webApp/toStore";
	});
	
	//上门预约
	$("#toDoor").on("click",function (){
		var url = "${ctx}/webApp/toDoorAppointment";
		
		$.get("${ctx}/webApp/user/isEnableYuYue",function(data){
			if(data == true){
				window.location.href= url;
			}else{
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_herf",url, { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/login";
			}
		});
	});
	
	//天气指南
	$("#weather").on("click",function (){
		window.location.href = "${ctx}/webApp/weather";
	});
});


</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back">&nbsp;</span>
		<span class="ind-title">首页&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div style="margin-top:1px;" class="gallery js-flickity gallery--o-dots" data-flickity-options="{&quot;pageDots&quot;:true,&quot;percentPosition&quot;: false,&quot;autoPlay&quot;: true,&quot;prevNextButtons&quot;:false}"> 
	   <img class="gallery-cell" src="${ctx}/static/front/images/banner.png" />
	   <%--<img class="gallery-cell" src="${ctx}/static/front/images/22.jpg" /> --%>
	  </div>
	<br/>
	<a href="javascript:;" id="weather" class="list-group-item" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-cloud"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;天气指南
	</a>
	<br/>
	<a href="javascript:;" id="toStore" class="list-group-item" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-home"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;到店预约
	</a>
	<br/>
	<%--<a href="javascript:;" id="toDoor" class="list-group-item" style="font-size: 17px;line-height: 25px;">--%>
		<%--<span style="color:#2885EC;"><span class="glyphicon glyphicon-briefcase"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;上门预约--%>
	<%--</a>--%>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
