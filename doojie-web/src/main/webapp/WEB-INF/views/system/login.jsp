<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<meta name="keywords" content="都捷生活,都捷洗车" />
<meta name="description" content="都捷生活  | 后台管理平台 —— 登录" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/static/css/login.css" rel="stylesheet"></link>
<link href="${ctx}/static/jquery-validation/1.9.0/milk.css"
	rel="stylesheet"></link>
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/messages_cn.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/static/js/jquery.placeholder.js"></script>
<title>都捷生活  | 后台管理平台 —— 登录</title>
<script type="text/javascript">
	$().ready(function() {
		if (window.self != window.top) {
			window.top.location = "${ctx}/system/login";
		}
		if ("${param.flag eq 1}" == "true") {
			$("#errorTip").html("<span style='color:#F63;font-weight:bold;'>账户不存在</span>");
		}
		if ("${param.flag eq 2}" == "true") {
			$("#errorTip").html("<span style='color:#F63;font-weight:bold;'>密码错误</span>");
		}
		if ("${param.flag eq 3}" == "true") {
			$("#errorTip").html("<span style='color:#F63;font-weight:bold;'>账户不可登录</span>");
		}
		$("#userName").focus();

		//enter触发登录事件
		$(document).keypress(function(event) {
			event = window.event || event;
			if (event.keyCode == 13) {
				doSubmit();
			}
		});
		//验证 start
		$("#form").validate({
			onfocusout : false,
			onkeyup : false,
			rules : {
				userName : {
					required : true,
					maxlength : 5
				},
				userPwd : {
					required : true,
					maxlength : 20
				},
				verifyCode : {
					required : true,
					rangelength : [ 4, 4 ],
					remote : {
						url : "${ctx}/system/ajaxCheckVerifyCode",
						type : 'POST',
						async : false,
						dataType : 'json'
					}
				}
			},
			messages : {
				userName : {
					required : "请输入工号",
					maxlength : "工号不能超过5长度"
				},
				userPwd : {
					required : "请输入密码",
					maxlength : "密码不能超过20长度"
				},
				verifyCode : "请输入正确的验证码"
			},
			wrapper : "li",
			errorLabelContainer : "#errorTip"
		});
		//验证 end
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

	function doSubmit() {
		$(".bottom").html(
				'<a href="javascript:;" class="button btn-green"> 登录中...</a>');
		if ($("#form").valid()) {
			$("#form").submit();
		} else {
			$("#verifyCode").val("");
			changeImg();
			$(".bottom")
					.html(
							'<a onclick = "doSubmit();" href="javascript:;" class="button btn-green"> 登 录</a>');
		}
	}
	function doReset() {
		changeImg();
		$("#form")[0].reset();
	}
</script>
</head>
<body>
	<div class="wrap">
		<div class="banner-show" id="js_ban_content">
			<div class="cell bns-01">
				<div class="con">
					<i>都捷生活后台管理平台</i>
					<div class="con2">
						<i>都捷生活是一个基于微信及位置开发的汽车后市场服务O2O平台，线上查找附近的洗车行，在线购卡，在线下单，线下消费，让您洗车、修车、保养车更方便、更便宜！</i>
					</div>
				</div>

			</div>
		</div>
		<div class="container">
			<div class="register-box">
				<div class="reg-slogan">管理登录</div>
				<div class="reg-form">
					<form id="form" action="${ctx}/system/doLogin" method="post">
						<div class="cell">
							<label> 
								<input type="text" name="userName" id="userName"
								placeholder="工号" class="text" />
								</label>
						</div>
						<div class="cell">
							<label> <input type="password" name="userPwd"
								id="userPwd" placeholder="密 码" class="text" /></label>
						</div>
						<!-- !短信验证码 -->
						<div class="cell vcode">
							<label> <input type="text" name="verifyCode"
								id="verifyCode" placeholder="验证码" class="text" maxlength="4" /></label>
							<a href="javascript:changeImg();"> <img
								src="${ctx}/verifyCodeServlet" border="0" id="verifyCodeImg"
								style="margin-bottom: -5px;" />
							</a><span> <a href="javascript:changeImg();"> 换一张</a></span>
						</div>
						<div class="bottom">
							<a onclick="doSubmit();" href="javascript:;"
								class="button btn-green"> 登 录</a>
						</div>
						<div id="errorTip" style="padding-top: 15px; font-size: 15px;">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
