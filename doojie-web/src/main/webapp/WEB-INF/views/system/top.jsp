<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/share/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>头部</title>

<%@ include file="/WEB-INF/views/share/meta.jsp" %>

<link href="${ctx}/static/css/common.css" rel="stylesheet"/>
<script type="text/javascript">
$().ready(function(){
	disptime();
});
function disptime()
{
	var time= new Date();
	var str = 
	time.getUTCFullYear()+"年"+
	(time.getUTCMonth()+1)+"月"+
	time.getUTCDate()+"日   "+
	time.getHours()+"时"+
	((time.getUTCMinutes()<10)?"0"+time.getUTCMinutes():time.getUTCMinutes())+"分"+
	((time.getUTCSeconds()<10)?"0"+time.getUTCSeconds():time.getUTCSeconds())+"秒";
	
	var Week = ['日','一','二','三','四','五','六'];  
	str += ' 星期' + Week[time.getDay()];  
	
	$("#clock").html(str);
	var myTime=setTimeout("disptime()",1000);
}

function doLogOut(){
	if(confirm('确实要退出吗？')){
		window.top.location.href="${ctx}/system/logOut";
	}
	
}

function doPass(){
	window.top.location.href="${ctx}/system/toUpdatePass";
}

function doIndex(){
	window.top.location.href="${ctx}/system/index";
}
</script>
</head>
<%-- <img src="${ctx}/static/images/topbgleft1.gif"  /> --%>
<body>
<div class="header">
<div class="flt">都捷生活后台管理平台</div>
<div class="toplogin">欢迎您！${employee.number}<a href="javascript:doLogOut()">[退出]</a><a href="${ctx}/system/toUpdatePass"  target="rhs" >[修改密码]</a><a target="rhs" href="${ctx}/system/index">[工作面板]</a></div>
<div class="toplogin">v0811.1</div>
<div class="topTime"><span id="clock"></span></div>
</div>
</body>
</html>
