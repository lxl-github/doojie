<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp" %>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>都街网  | 商家后台管理平台 —— 登录</title>
<style type="text/css">
body{ background:url(${ctx}/static/images/bglogin.gif) repeat-x top;}
*{padding:0;margin:0;border:0;}
.login{width:901px;margin:0 auto;padding:135px 0 0 0;}
.centerleft{float:left;margin:0 0 0 45px;width:390px; font-family:'微软雅黑', Arial; font-size:14px;color:#fff;}
.centerleft h1{ font-size:20px;padding:0 0 15px 0;}
.centerleft p{ text-indent:2em;padding:0 0 10px 0; line-height:24px;margin:0; display:block;}
.centertab{ float:left;margin:0 0 0 110px; font-size:14px; font-family:"宋体", Arial;color:#406c8d;}
.centertab td{ padding:5px 0; font-weight:bold;}
.centertab .input01{height:28px;line-height:28px;border:1px solid #d9e6eb; font-family:Verdana, Geneva, sans-serif;color:#666;}
.centertab th{color:#f60; font-size:12px; font-weight:normal;}
.loginfoot{ position:absolute; bottom:0;left:0; width:100%;border-top:1px solid #eee; background:#fafafa;padding:15px 0; text-align:center;color:#999; font:12px Verdana, Geneva, sans-serif;}
</style>
<script type="text/javascript">
$().ready(function (){
	if(window.self != window.top){
		window.top.location="${ctx}/bussiness/login";
	}
	if("${param.flag eq 1}" == "true"){
		$("#errorTip").html("账户不存在");
	}
	if("${param.flag eq 2}" == "true"){
		$("#errorTip").html("密码错误");
	}
	$("#userName").focus();
	/**
	$("#userName,#userPwd,#verifyCode").keypress(function(){
		$("#errorTip").html("");
	});
	**/
	//enter触发登录事件
	$(document).keypress(function(event) { 
		event = window.event || event;
		if (event.keyCode == 13) { 
			doSubmit();
		} 
	}); 
	//验证 start
	$("#form").validate({
		onfocusout:false,
		onkeyup:false,
		rules:{
			userName:{
				required: true,
				maxlength:20
			},
			userPwd:{
				required: true,
				maxlength:20
			},
			verifyCode:{
				required:true,
				rangelength:[4,4],
				remote:{
					url: "${ctx}/bussiness/ajaxCheckVerifyCode",
		       	    type: 'POST',
		       	 	async:false,
		       	    dataType: 'json'
				}
			}
		},
		messages:{
			userName:{
				required:"请输入用户名",
				maxlength:"用户名不能超过20长度"
			},
			userPwd:{
				required:"请输入密码",
				maxlength:"密码不能超过20长度"
			},
			verifyCode:"请输入正确的验证码"
		},
		wrapper: "li",
		errorLabelContainer:"#errorTip"
	});
	//验证 end
});
function changeImg(){
	var timestamp = (new Date()).valueOf();
	var url = "${ctx }/verifyCodeServlet" ;
	if(url.indexOf("&") >= 0){
		url = url + "tamp=" + timestamp;
	}else{
		url = url + "?timestamp=" + timestamp;
	}	
	$("#verifyCodeImg").attr("src",url);
}

function doSubmit(){
	if($("#form").valid()){
		$("#form").submit();
	}else{
		$("#verifyCode").val("");
		changeImg();
	}
}
function doReset(){
	changeImg();
	$("#form")[0].reset();
}
</script>
</head>

<body>
<div class="login">
<form id="form" action="${ctx}/bussiness/doLogin" method="post">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="${ctx}/static/images/bglogo.png" width="901" height="113" /></td>
  </tr>
  <tr>
    <td height="303" align="left" valign="top" background="${ctx}/static/images/bgcenter.png">
     	<div class="centerleft">
       		 <h1>商家后台管理平台</h1>
             <p>XXXXXXXXXXXXXXXXXXXXXXXXXXX</p> <p>XXXXXX</p><p></p>
        </div>
        <table border="0" cellspacing="0" cellpadding="0" class="centertab">
          <tr>
            <td align="right">用户名：</td>
            <td colspan="2">
              <input name="userName" type="text" id="userName" class="input01" size="28" />
            </td>
          </tr>
          <tr>
            <td align="right">密&nbsp;&nbsp;码：</td>
            <td colspan="2"><input name="userPwd" type="password" id="userPwd" class="input01" size="28" /></td>
          </tr>
          <tr>
            <td align="right">验证码：</td>
            <td><input name="verifyCode" type="text" id="verifyCode"  class="input01" size="16" style=" width:95px;"/></td>
            <td>
				<a href="javascript:changeImg();">
                		<img src="${ctx}/verifyCodeServlet" border="0" id="verifyCodeImg" style="margin-bottom:-5px;"/>
                	</a>
			</td>
          </tr>
          <tr>
            <td height="80">&nbsp;</td>
            <td><a href="javascript:void(0);" onclick = "doSubmit();"><img src="${ctx}/static/images/button_r1.png" width="95" height="34" /></a></td>
            <td align="right" ><a href="javascript:void(0);" onclick = "doReset();"><img src="${ctx}/static/images/button_r2.png" width="95" height="34" /></a></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <th colspan="2" align="left" id="errorTip"></th>
          </tr>
    </table>
        
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        </form>
</div>
<div class="loginfoot"><span style="color:#F60;">建议使用IE8及以上版本</span><br/>@copy 2015 都街网</div>
</body>
</html>
