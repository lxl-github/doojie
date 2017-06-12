<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/share/taglib.jsp" %>
<script type="text/javascript">
<!--
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
//-->
</script>

<div class="page mT10">
	<div class="fl">共${page.totalRecord}条记录，每页显示${page.pageSize}条，共${page.totalPage}页</div>
	<div class="fr">
	<c:if test="${page.totalRecord > 0 }">
		<input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
		<c:if test="${page.totalPage != 0 }">
			<c:if test="${page.currentPage != 1 }">
				<a href="javascript:topage('1') ;">首页</a>
				<a href="javascript:topage('${page.currentPage -1 }');">上一页</a>
			</c:if>
		</c:if>
		<c:forEach begin="1" end="${page.totalPage }" var="sp">
			<c:if test="${sp != page.currentPage }">
				<a href="javascript:topage('${sp }');">${sp }</a> 
			</c:if>
			<c:if test="${sp == page.currentPage }"> 
				<b>${sp }</b>
			</c:if>
		</c:forEach>
		<c:if test="${page.currentPage  != page.totalPage }">
			<a href="javascript:topage('${page.currentPage +1 }');">下一页</a>
			<a href="javascript:topage('${page.totalPage }');">末页</a>
		</c:if>
	</c:if>
	</div>
</div>
<br/>