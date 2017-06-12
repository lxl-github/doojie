<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/static/js/jquery/jquery-1.7.2.min.js"></script>
<script src="${ctx}/static/js/Calendar.js" type="text/javascript"></script>
<script type="text/javascript">
function doSave(){
	$("#activityForm").attr("action","${ctx}/activity/system/add.html");
	$("#activityForm").submit();
}
</script>
<title>新增活动</title>
</head>
<body>

<form id="activityForm" method="post" action="">
<span>标题：</span><input id="title" name="title"/><br/>
<span>分类：</span>
<select id="categreyId" name="categreyId">
	<c:forEach var="entry" items="${categreyMap}">
		<option value="${entry.key}">${entry.value}</option>
	</c:forEach>
</select><br/>
<span>开始时间：</span><input id="startTime" readonly="readonly" onclick="new Calendar().show(this)" name="startDate"/><br/>
<span>结束时间：</span><input id="endTime" readonly="readonly" onclick="new Calendar().show(this)"  name="endDate"/><br/>
<span>活动周期：</span><input id="activityTime" name="activityTime"/><br/>
<span>人均：</span><input id="perCapita" name="perCapita"/><br/>
<span>区域：</span><select id="areaid" name="areaid">
		<option value="1">北京</option>
</select><br/>
<span>位置：</span>地图取位置<br/>
<span>地址：<input type="text" name="address" /> </span>
<span>活动内容：</span><textarea id="content" name="content" rows="5" cols="10"></textarea><br/>
<input name="save" type="button" value="保存" onclick="doSave()" />
</form>
</body>
</html>