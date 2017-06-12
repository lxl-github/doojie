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
	$("#form").attr("action","${ctx}/system/user/userProductList");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

</script>
<title>用户购买商品列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<fieldset>
		<legend><strong>用户购买商品列表</strong></legend>
	<form id="form" action="" method="post">
			<input type = "hidden" id="userId" name="userId" value="${userId}"/>
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="10%">商品名称</th>
			        <th width="7%">可用数量</th>
			        <th width="7%">已用数量</th>
<!-- 			        <th width="9%">消费凭证码</th> -->
					<th width="7%">使用方式</th>
			        <th width="3%">类型</th>
			        <th width="9%">购买时间</th>
			        <th width="9%">有效截止日期</th>
			        <th width="6%">状态</th>
			        <th width="9%">是否过期</th>
			        <th width="9%">交易编号</th>
			        <th width="9%">支付方式</th>
			        <th width="9%">支付金额</th>
			        <th width="9%">流水号</th>
			        <th width="9%">支付时间</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='14' style="color:red; text-align: center;">很抱歉，还没有购买商品</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="userProductVo">
		        	<tr>
		        		<td>${userProductVo.productName}&nbsp;</td>
		        		<td>${userProductVo.consumeNumber}&nbsp;</td>
		        		<td>${userProductVo.consumeNumberAlready}&nbsp;</td>
<%-- 		        		<td>${userProductVo.consumeCode}&nbsp;</td> --%>
						<td><c:if test="${userProductVo.productIsDoor eq null || userProductVo.productIsDoor eq 0}">到店</c:if><c:if test="${userProductVo.productIsDoor eq 1}">上门</c:if>&nbsp;</td>
		        		<td>${userProductVo.productType eq 1 ? "卡" :"券"}&nbsp;</td>
		        		<td>${userProductVo.createDate}&nbsp;</td>
		        		<td>${userProductVo.expiredDate}&nbsp;</td>
		        		<td><c:if test="${userProductVo.status eq 1}">已使用</c:if><c:if test="${userProductVo.status eq 0}">未使用</c:if><c:if test="${userProductVo.status eq 2}">使用中</c:if>&nbsp;</td>
		        		<td>${userProductVo.isExpired eq 1 ? "过期" :"未过期"}&nbsp;</td>
		        		<td>${userProductVo.tradeRecord.tradeCode}&nbsp;</td>
		        		<td>${userProductVo.tradeRecord.payMode eq 1 ? "微信支付" : "支付宝"}&nbsp;</td>
		        		<td><fmt:formatNumber value="${userProductVo.tradeRecord.money}" type="currency" pattern="￥.00"/>&nbsp;</td>
		        		<td>${userProductVo.tradeRecord.serialNumber}&nbsp;</td>
		        		<td>${userProductVo.tradeRecord.payDate}&nbsp;</td>
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