<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<title>默认首页</title>
<style type="text/css">
/*预约*/
.time{width:550px;}
.tab01 table{background:#8cc63e;padding:3px;width:100%;margin:6px 0 3px 0;height:23px;}
.tab01 table td a:link,.tab01 table td a:visited,.tab01 table td a:hover{padding:3px;color:#fff; text-decoration:none;}
.tab02 table{border:solid #8cc63e;border-width:1px 0 0 1px;width:100%;color:#000;}
.tab02 table th,.tab02 table td{border:solid #8cc63e;border-width:0 1px 1px 0;width:70px;}
.tab02 table th{padding:3px 0;}
.tab02 table td{line-height:23px;}
.tab02 table .h{height:30px;padding:8px;}
.tab02 table .a{background:#ffffd9;}
.tab02 table .b{background:#eff7ff;}
.tab02 table .c{background:#fff5f8;}
.tab02 table .d{border:4px solid #e9e8e0;width:26px;padding:0 0 0 10px;}
.tab02 table .r{background:#f93;color:#fff;height:30px;padding:8px;overflow:hidden;}
.tab02 table .r a:link,.tab02 table .r a:visited,.tab02 table .r a:hover{color:#fff;}
.tab02 table .d a:link,.tab02 table .d a:visited,.tab02 table .d a:hover{color:#045a9c;}
</style>
</head>
<body style="margin: 0; padding: 0;">
	<div style="margin: 15px 10px;">
	<h2>工作面板</h2>
		<p style="margin: 10px;">
			<em
				style="float: right; font-style: normal; font-size: 12px; color: #666;">
				<b style="color: #f00; font-size: 14px;">上次登录：</b>
<!-- 				本次登录 -->
<%-- 				 <fmt:formatDate value="${currentLoginInfo.loginTime}" pattern="yyyy-MM-dd HH:mm:ss" /> &nbsp;&nbsp;  --%>
				  <c:if test="${ empty beforeLoginInfo}">无</c:if>
						<c:if test="${!empty beforeLoginInfo}">
							<fmt:formatDate value="${beforeLoginInfo.loginTime}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</c:if></em>
			<span><b style="color: #f00; font-size: 14px;">欢迎使用</b>
			</span>
		</p>
		是否开启自动派单：<input type="radio" id="isAuto" name="isAuto" value="1"/>是 <input type="radio" checked="checked" id="isAuto" name="isAuto" value="0"/>否
	</div>
</body>
</html>