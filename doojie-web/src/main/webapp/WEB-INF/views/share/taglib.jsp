<%
response.setHeader("Cache-Control", "no-cache,no-store,max-age=0,must-revalidate");
response.setHeader("Expires", "Mon,12 May 2001 00:20:00 GMT");
%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
<!--
   var contextPath='<%=request.getContextPath()%>';
 //-->   
 
function getLocalTime(nS) {
	return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " "); 
}
</script>
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" />
