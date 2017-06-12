<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 商家登录</title>
<script type="text/javascript">
	$().ready(function() {
		hideLoading();

		$(".icon-back").on("click", function() {
			history.back();
		});
		
		
		//enter触发登录事件
		$(document).keypress(function(event) {
			event = window.event || event;
			if (event.keyCode == 13) {
				doSubmit();
			}
		});
		
		
		//登录
		$("#login").on("click", function() {
			doSubmit();
		});
	});
	
	function changeImg() {
		var timestamp = (new Date()).valueOf();
		var url = "${ctx }/verifyCodeServlet";
		if (url.indexOf("&") >= 0) {
			url = url + "tamp=" + timestamp;
		} else {
			url = url + "?timestamp=" + timestamp;
		}
		$("#verifyCodeImg").attr("src", url);
	}
	
	function doSubmit(){
		var uname = $("#userName").val().trim();
		var pwd = $("#userPwd").val().trim();
		var code = $("#verifyCode").val().trim();
		if(uname == ""){
			Alert("请输入用户名");
			return;
		}
		if(pwd == ""){
			Alert("请输入密码");
			return;
		}
		
		if(code == ""){
			Alert("请输入验证码");
			return;
		}
		
		
		$.post("${ctx}/webApp/ajaxCheckVerifyCode",{verifyCode:code},function(data){
			if(data){
				showLoading("登录中...");
				$.post("${ctx}/webApp/doMerchantLogin",{userName:uname,userPwd:pwd},function(data){
					hideLoading();
					if(data == 1){
						Alert("账户不存在");
					}else if(data == 2){
						Alert("账户密码错误");
					}else{
						Alert("登录成功");
						setTimeout(function () {
							$.cookie("isMerchantLogin",true, { path: '${ctx}' });
							var href = $.cookie('target_merchant_herf');
							if(typeof(href) == "undefined" || href == "null"){
								window.location.href = "${ctx}/webApp/toMerchantMyIndex";
							}else{
								window.location.href = href;
							}
					    }, 800);
					}
				});
			}else{
				Alert("验证码错误");
			}
		});
	}
</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	<div class="ik-ind-container">
<!-- 		<span class="icon-back"><span -->
<!-- 			class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>  -->
			<span
			class="ind-title">商家登录&nbsp;</span> <span class="ind-person">&nbsp;</span>
	</div>
	</nav>
	<br />
	<div class="container" style="font-size:16px;">
		<form class="form-horizontal"  role="form">
			<div class="form-group">
				<label for="userName" class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="userName"
						placeholder="用户名">
				</div>
			</div>
			<div class="form-group">
				<label for="userPwd" class="col-sm-2 control-label">密 码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="userPwd"
						placeholder="密 码">
				</div>
			</div>
			<div class="form-group">
			<label for="verifyCode" class="col-sm-2 control-label">验证码</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="verifyCode"
								id="verifyCode" placeholder="验证码" maxlength="4" />
								<label style="padding-top: 15px;">
							<a href="javascript:changeImg();"> <img
								src="${ctx}/verifyCodeServlet" border="0" id="verifyCodeImg"
								style="width: 120px;height:35px;" />
							</a>
							<a href="javascript:changeImg();"> 换一张</a>
							</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a class="btn btn-primary btn-block btn-large" id="login">登  &nbsp;&nbsp;&nbsp;&nbsp;录</a>
				</div>
			</div>
		</form>
	</div>
	<%@include file="/WEB-INF/views/common/loading.jsp"%>
</body>
</html>
