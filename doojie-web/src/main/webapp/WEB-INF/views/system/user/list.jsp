<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script type="text/javascript">

//搜索
function doSearch(){
	$("#form").attr("action","${ctx}/system/user/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}


//查询详情
function getUserProductList(userId){
	//弹框显示
	artDialog.open('${ctx}/system/user/userProductList/'+userId, 
				{
					title:"用户购买商品列表",
					width:1100,
					height:450,
					lock:true,
        			style:'succeed noClose'
        		}
	);
}
</script>
<title>用户列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />后台管理&gt;<font color="#FF9900">用户列表</font>
	</h2>
	<fieldset>
		<legend><strong>用户列表</strong></legend>
	<form id="form" action="" method="post">
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="7%">手机号</th>
			        <th width="12%">邮箱</th>
			        <th width="9%">创建时间</th>
			        <th width="9%">来源</th>
			        <th width="9%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='4' style="color:red; text-align: center;">很抱歉，还没有注册用户</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="userVo">
		        	<tr>
		        		<td>${userVo.user.mobile}&nbsp;</td>
		        		<td>${userVo.user.email}&nbsp;</td>
		        		<td>${userVo.createDate}&nbsp;</td>
		        		<td>${userVo.user.source eq 1 ? "注册" :"微信"}</td>
		        		
		        		<td><a href="javascript:void(0);" onclick="getUserProductList(${userVo.user.id})">查看购买商品</a></td>
		        	</tr>
		        </c:forEach>
		       	</c:if>
			</table>
	        <!-- 插入分页 -->
	        <%@ include file="/WEB-INF/views/share/fenye.jsp" %>
			</form>
			</fieldset>
			<div class="clr"></div>
	</div>
	<div id="showLoading" style="display:none;border: 1px solid #999999;padding:15px 10px;color:#006699;font-size:14px; ">
	    <img src="${ctx}/static/images/loading.gif">&nbsp;&nbsp;<b>正在处理，请稍候..</b>
	  </div>
</body>
</html>