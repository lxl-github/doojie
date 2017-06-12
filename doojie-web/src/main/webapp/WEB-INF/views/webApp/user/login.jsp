<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 登录</title>
<script type="text/javascript">
	var geshi = false;
	$().ready(function() {
		hideLoading();
// 		var isLogin = $.cookie("isLogin");
// 		if(isLogin != "undefined" && isLogin == "true"){
// 			var href = $.cookie('target_herf');
// 			if(href == "null" || href == "undefined"){
// 				window.location.href = "${ctx}/webApp/user/my";
// 			}else{
// 				window.location.href = href;
// 			}
// 		}

		$(".icon-back").on("click", function() {
			history.back();
		});
		//用户注册
		$("#register").on("click", function() {
			window.location.href = "${ctx}/webApp/register";
		});
		
		
		
		$("#mobile").on("blur",function(){
			var mob = $("#mobile").val().trim();
			if(mob != ""){
				if (/^13\d{9}$/g.test(mob) || (/^15[0-35-9]\d{8}$/g.test(mob))
						|| (/^18[0-9]\d{8}$/g.test(mob))) {
					geshi = false;
				}else{
					Alert("请输入正确的手机号");
					geshi = true;
				}
			}
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
	function doSubmit(){
		var mob = $("#mobile").val().trim();
		var pwd = $("#psword").val().trim();
		if(mob == ""){
			Alert("请输入手机号");
			return;
		}
		if(geshi){
			Alert("请输入正确的手机号");
			return;
		}
		if(pwd == ""){
			Alert("请输入密码");
			return;
		}
		showLoading("登录中...");
		$.post("${ctx}/webApp/doLogin",{mobile:mob,password:pwd},function(data){
			hideLoading();
			if(data == "1"){
				Alert("账户不存在");
			}else if(data == "2"){
				Alert("账户密码错误");
			}else if(data == "0"){
				Alert("登录成功");
				setTimeout(function () {
					$.cookie("isLogin",true, { path: '${ctx}' });
					var href = $.cookie('target_herf');
					if(typeof(href) == "undefined" || href == "null"){
						window.location.href = "${ctx}/webApp/user/my";
					}else{
						window.location.href = href;
					}
					
			    }, 800);
			}else{
				Alert("登录成功");
				setTimeout(function () {
					$.cookie("isLogin",true, { path: '${ctx}' });
					window.location.href = data;
			    }, 800);
			}
		});
	}
</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	<div class="ik-ind-container">
		<span class="icon-back"><span
			class="glyphicon glyphicon-menu-left"></span>&nbsp;</span> <span
			class="ind-title">登录&nbsp;</span> <span class="ind-person">&nbsp;</span>
	</div>
	</nav>
	<br />
	<div class="container" style="font-size:16px;">
		<form class="form-horizontal"  role="form">
			<div class="form-group">
				<label for="mobile" class="col-sm-2 control-label">手机号</label>
				<div class="col-sm-10">
					<input type="tel" class="form-control" id="mobile"
						placeholder="手机号">
				</div>
			</div>
			<div class="form-group">
				<label for="psword" class="col-sm-2 control-label">密 码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="psword"
						placeholder="密 码">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10" style="text-align: right">
					<a id="register"><label>用户注册</label></a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a class="btn btn-primary btn-block btn-large" id="login">登  &nbsp;&nbsp;&nbsp;&nbsp;录</a>
				</div>
			</div>
		</form>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
