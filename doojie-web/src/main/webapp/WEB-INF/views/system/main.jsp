<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<title>都捷生活  | 后台管理平台</title>
</head>
<frameset rows="80,*" cols="*" framespacing="6" frameborder="no"
		border="3" bordercolor="#D5D5D5">		
		<frame src="${ctx}/system/top" name="topFrame" scrolling="no" noresize/>
		<frameset rows="*" cols="12%,*" framespacing="1"
			frameborder="yes" framespacing="1">
			<frame name="rls" src="${ctx}/system/left" noresize
				frameborder="0" scrolling="auto"/>			
			<frame name="rhs" src="${ctx}/system/index"
				frameborder="0" scrolling="yes"/>
		</frameset>
	</frameset>
</html>
