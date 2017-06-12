<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="${ctx}/static/front/js/jquery.qrcode.min.js"></script>
<title>${website_name} | 验码消费</title>
<script type="text/javascript">
$().ready(function(){
	//菜单加颜色提示
	$("#my").attr("style","color:#2885EC");
	
	$(".icon-back").on("click",function(){
		history.back();
	});
	
	$("#submit").on("click",function(){
		window.location.href = "${ctx}/webApp/user/greateCheckCode/"+${cardId};
	});
	
	$('#qrcode').qrcode("${code}");
});
</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">验码消费&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span>
	  </div>
	</nav>
	<br/>
	<div class="list">
		<form role="form" >
			<div class="form-group" style="text-align: center;">
		  	   <samp style="color:orange;font-size:15px;">温馨提示:消费码5分钟后过期，请及时到商家处验证</samp><br><br>
		   </div>
		  <div class="form-group" style="text-align: center;">
		       <span style="font-family:Arial, Helvetica, sans-serif; font-size:38px;color:#5f5f5f;">${code}</span>
		       <br>
		       <span id="qrcode"></span>
		   </div>
		  <div class="form-group">
		    <div class="col-xs-offset-4 col-xs-8">
		      <button class="btn btn-primary" id="submit" type="button"> 重新生成 </button>
		    </div>
		  </div>
		</form>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
