<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/static/js/jquery/jquery-1.7.2.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	function topage(currentPage, isAjax) {
		//如果不是ajax请求的分页,则正常提交表单
		if(isAjax != null && isAjax != "") {
			$("#currentPage").val(currentPage);
			//如果是ajax请求的分页，则进行ajax提交表单
			var path = $("#form").attr("action");
			$.ajax({
				url : path,
				data : $("#form").serialize(),
				type : "post",
				success : function(data) {
					$("#abc").html(data);
				}
			});
		} else {
			$("#currentPage").val(currentPage);
			$("#form").submit();
		}
		
	}

</script>
</head>
<body>
<form id="form" action="${ctx }/activity/list">
<c:if test="${activityVoList ne null }">
<c:forEach var="activity" items="${activityVoList}">
	<span>活动图片：<img src="${activity.picImg}"/></span><br/>
	<span>活动标题：${activity.title}</span><br/>
	<span>类型：${activity.categreyName}</span><br/>
	<span>地址：${activity.address}</span><br/>
	<span>人均：${activity.perCapita}</span><br/>
	<span>活动详情：${activity.content}</span><br/>
	<span>经度：${activity.jingdu}</span><br/>
	<span>纬度：${activity.weidu}</span><br/>
	<span><fmt:formatDate value="${activity.startTime}" pattern="MM月dd日"/> </span><br/>
	<span><fmt:formatDate value="${activity.endTime}" pattern="MM月dd日"/></span>
</c:forEach>
</c:if>
<br/>

<div class="page mT10">
	<div class="fl">共${pagination.totalRecord}条记录，每页显示${pagination.pageSize}条，共${pagination.totalPage}页</div>
	<div class="fr">
	<c:if test="${pagination.totalRecord > 0 }">
		<input type="hidden" id="currentPage" name="currentPage" value="${pagination.currentPage}"/>
		<c:if test="${pagination.totalPage != 0 }">
			<c:if test="${pagination.currentPage != 1 }">
				<a href="javascript:topage('1') ;">首页</a>
				<a href="javascript:topage('${pagination.currentPage -1 }');">上一页</a>
			</c:if>
		</c:if>
		<c:forEach begin="1" end="${pagination.totalPage }" var="sp">
			<c:if test="${sp != pagination.currentPage }">
				<a href="javascript:topage('${sp }');">${sp }</a> 
			</c:if>
			<c:if test="${sp == pagination.currentPage }"> 
				<b>${sp }</b>
			</c:if>
		</c:forEach>
		<c:if test="${pagination.currentPage  != pagination.totalPage }">
			<a href="javascript:topage('${pagination.currentPage +1 }');">下一页</a>
			<a href="javascript:topage('${pagination.totalPage }');">末页</a>
		</c:if>
	</c:if>
	</div>
</div>
</form>
</body>
</html>