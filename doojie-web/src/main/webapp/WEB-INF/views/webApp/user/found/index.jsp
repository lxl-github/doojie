<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 发现</title>
<script type="text/javascript">
$().ready(function(){
// 	var isLogin = $.cookie("isLogin");
// 	if(isLogin != "undefined" && isLogin == "true"){
// 		var href = $.cookie('target_herf');
// 		if(href == "null" || href == "undefined"){
// 			window.location.href = "${ctx}/webApp/user/my";
// 		}else{
// 			window.location.href = href;
// 		}
// 	}
	
	hideLoading();
	//菜单加颜色提示
	$("#found").attr("style","color:#2885EC");
	
	$(".icon-back").on("click",function(){
		history.back();
	});
	//推荐圈
	$("#recommend").on("click",function (){
		window.location.href = "${ctx}/webApp/recommend";
	});
// 	//天气指南
// 	$("#weather").on("click",function (){
// 		window.location.href = "${ctx}/webApp/weather";
// 	});
});


</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">发现&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<br/>
	<a href="javascript:;" id="recommend" class="list-group-item" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-thumbs-up"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;推荐圈
	</a>
<!-- 	<br/> -->
<!-- 	<a href="javascript:;" id="weather" class="list-group-item" style="font-size: 17px;line-height: 25px;"> -->
<!-- 		<span style="color:#2885EC;"><span class="glyphicon glyphicon-cloud"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;天气指南 -->
<!-- 	</a> -->
<!-- 	<br/> -->
<!-- 	<a href="javascript:;" id="carGuide" class="list-group-item" style="font-size: 17px;line-height: 25px;"> -->
<!-- 		<span style="color:#2885EC;"><span class="glyphicon glyphicon-bed"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;爱车指导 -->
<!-- 	</a> -->
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
