<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 我的都捷</title>
<style type="text/css">
.mybg {
  width: 100%;
/* 	background-image:url(${ctx}/static/images/banner_01.png?v=201406241538); */
  filter: alpha(opacity=80);
  -moz-opacity: 0.8;
  opacity: 0.8;
  line-height:60px;
  text-align:center;
  font-size:16px;
  color:#000;
  font-weight:bold;
  font-family:"华文楷体", "幼圆", "新宋体", "微软雅黑", "宋体";
}
</style>
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
	$("#my").attr("style","color:#2885EC");
	
	$(".icon-back").on("click",function(){
		history.back();
	});
	//我的评价
	$("#myRecommend").on("click",function (){
		window.location.href = "${ctx}/webApp/toMyRecommendList";
	});
	//我的账户
// 	$("#myAccount").on("click",function (){
// 		window.location.href = "${ctx}/webApp/toMyAccount";
// 	});
	
	$("#myTrade").on("click",function (){
		window.location.href = "${ctx}/webApp/toMyTrade";
	});
	//我的卡包
	$("#myCard").on("click",function (){
		window.location.href = "${ctx}/webApp/toMyCard";
	});
	//意见反馈
	$("#suggest").on("click",function (){
		window.location.href = "${ctx}/webApp/uesr/toSuggest";
	});
	//退出
	$("#loginOut").on("click",function (){
		if(confirm("是否要退出？")){
			showLoading("退出中...");
			$.post("${ctx}/webApp/user/loginOut",function(data){
				$.cookie("isLogin",false, { path: '${ctx}' });
				$.cookie("target_herf",null, { path: '${ctx}' });
				window.location.href="${ctx}/webApp/login";
			});
		}
	});
});


</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">我的都捷&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span>
	  </div>
	</nav>
<%-- 	<img src="${ctx}/static/front/images/3.png" width="80" height="80"/> --%>
	<div class="mybg">&nbsp;&nbsp;欢迎 ${user.mobile} 的使用</div>
<!-- 	<a href="javascript:;" class="list-group-item" id="myAccount" style="font-size: 17px;line-height: 25px;"> -->
<!-- 		<span style="color:#2885EC;"><span class="glyphicon glyphicon-user"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;我的账户 -->
<!-- 	</a> -->
	<a href="javascript:;" class="list-group-item" id="myTrade" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-usd"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;我的交易
	</a>
	<a href="javascript:;" class="list-group-item" id="myCard" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-credit-card"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;我的卡包
	</a>
	<a href="javascript:;" class="list-group-item" id="myRecommend" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-thumbs-up"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;我的评价
	</a>
<!-- 	<a href="javascript:;" class="list-group-item" style="font-size: 17px;line-height: 25px;"> -->
<!-- 		<span style="color:#2885EC;"><span class="glyphicon glyphicon-gift"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;我要兑换 -->
<!-- 	</a> -->
	<a href="javascript:;" class="list-group-item" id="suggest" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-pencil"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;意见反馈
	</a>
	<br/>
	<a href="javascript:;" class="list-group-item" id="loginOut" style="font-size: 17px;line-height: 25px;">
		<span style="color:#2885EC;"><span class="glyphicon glyphicon-log-out"></span></span>&nbsp;&nbsp;&nbsp;&nbsp;退出
	</a>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
