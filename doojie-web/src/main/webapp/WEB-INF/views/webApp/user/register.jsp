<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 注册</title>
<script type="text/javascript">
var isExist = false;
var geshi = false;
	$().ready(function() {

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
		
		
		
		$("#mobile").on("blur",function(){
			var mob = $("#mobile").val().trim();
			if(mob != ""){
				if (/^13\d{9}$/g.test(mob) || (/^15[0-35-9]\d{8}$/g.test(mob))
						|| (/^18[0-9]\d{8}$/g.test(mob))) {
					$.get("${ctx}/webApp/isExistsUser",{mobile:mob},function(data){
						if(data){
							Alert("该账户被已被注册");
							isExist = true;
						}else{
							isExist = false;
						}
					});
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
		
		//我的推荐
		$("#register").on("click", function() {
			doSubmit();
		});
	});
	
	function doSubmit(){
		var mob = $("#mobile").val().trim();
		if(mob == ""){
			Alert("请输入手机号");
			return;
		}
		if(geshi){
			Alert("请输入正确的手机号");
			return;
		}
		if(isExist){
			Alert("该账户被已被注册");
			return;
		}
		var pwd = $("#psword").val();
		if(pwd == ""){
			Alert("请输入密码");
			return;
		}
		var rpwd = $("#confirmPwd").val();
		if(rpwd == ""){
			Alert("请输入确认密码");
			return ;
		}
		if(pwd != rpwd){
			Alert("密码与确认密码不同");
			return;
		}
		var isCheck = $("#isAgreement").is(':checked');
		if(!isCheck){
			Alert("请先阅读并勾选用户注册协议");
			return;
		}
		showLoading("注册中...");
		$.post("${ctx}/webApp/doRegister",{mobile:mob,password:pwd},function(data){
			hideLoading();
			if(data != "false"){
				Alert("注册成功");
				var href = $.cookie('target_herf');
				$.cookie("isLogin",true, { path: '${ctx}' });
				if(typeof(href) == "undefined" || href == "null"){
					setTimeout(function () {
						window.location.href = data;
				    }, 800);
				}else{
					window.location.href = data;
				}
			}else{
				Alert("注册失败,请稍后重试");
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
			class="ind-title">注册&nbsp;</span> <span class="ind-person">&nbsp;</span>
	</div>
	</nav>
	<br />
	<div class="container" style="font-size:16px;">
		<form class="form-horizontal" role="form">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
				<div class="col-sm-10">
					<input type="tel" class="form-control" id="mobile"
						placeholder="手机号">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword3" class="col-sm-2 control-label">密
					码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="psword"
						placeholder="密 码">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword3" class="col-sm-2 control-label">确认密码</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="confirmPwd"
						name="password" placeholder="确认密码">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10" style="text-align: right;">
			      	<input type="checkbox" id="isAgreement" checked = "checked"/>
			      	<a href="javascript:;" onclick="javascript:window.location.href='${ctx}/webApp/userProctocol'">我已阅读并同意《用户注册协议》</a>
				</div>
				<br/>
				<div class="col-sm-offset-2 col-sm-10">
					<a class="btn btn-primary btn-block btn-large" id="register">注&nbsp;&nbsp;&nbsp;&nbsp; 册</a>
				</div>
			</div>
		</form>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
