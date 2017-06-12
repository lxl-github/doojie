<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script type="text/javascript">
function doCloase(){
	art.dialog.close();
}

function doDistributionOrder(){
	var id = $("input[type='radio']:checked").val();
	if(id == null){
		artDialog.tips('请选择派单人员！', 2);
		return;
	}
	var orderId = $("#orderId").val();
	//调用派单方法---》将订单状态改为3，增加订单跟踪记录，增加员工工作记录
	var oldStatus = $("#oldStatus").val();
	
	$.get("${ctx}/system/order/distributionOrder/"+oldStatus+"/"+orderId+"/"+id,function(data){
		if(data == 1){
			artDialog.tips('派单成功！', 1.5);
			
			setTimeout(function(){  
				reload();
			}, 1200);
		}else if(data == 2){
			artDialog.tips('派单失败，请稍后再试！', 1.5);
		}else if(data == 3){
			artDialog.tips('系统错误！', 1.5);
		}else if(data == 4){
			artDialog.tips('无法派单，请刷新订单列表，查看状态！', 1.5);
		}
	},"json");
}

//重新载入页面
function reload(){
	var win = art.dialog.open.origin;  
	win.location.reload();
}
</script>
<title>员工列表</title>
</head>
<body>
	<div class="rightbox" >
	<form id="form" action="${ctx}/system/order/selectEmployee" method="get">
	<input type="hidden" id="orderType" name="orderType" value="${orderType}"/>
	<input type="hidden" id="regionId" name="regionId" value="${regionId}"/>
	<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
	<input type="hidden" id="oldStatus" name="oldStatus" value="${oldStatus}"/>
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="7%">单选</th>
					<th width="7%">员工编号</th>
			        <th width="7%">姓名</th>
			        <th width="5%">状态</th>
			        <th width="5%">职位</th>
			        <th width="7%">手机号</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='5' style="color:red; text-align: center;">很抱歉，暂无此地区派单人员</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="employee">
		        	<tr>
		        		<td><input type="radio" id="id" name="id" value="${employee.id}"/>&nbsp;</td>
		        		<td>${employee.number}&nbsp;</td>
		        		<td>${employee.name}&nbsp;</td>
		        		<td><c:if test="${employee.status eq 1}">在职</c:if><c:if test="${employee.status eq 2}">离职</c:if><c:if test="${employee.status eq 3}">休假</c:if>&nbsp;</td>
		        		<td><c:if test="${employee.position eq 1}">洗车员</c:if><c:if test="${employee.position eq 2}">维修员</c:if><c:if test="${employee.position eq 3}">保养员</c:if><c:if test="${employee.position eq 4}">客服</c:if><c:if test="${employee.position eq 5}">财务</c:if>&nbsp;</td>
		        		<td>${employee.phone}&nbsp;</td>
		        	</tr>
		        </c:forEach>
		       	</c:if>
			</table>
	        <!-- 插入分页 -->
	        <%@ include file="/WEB-INF/views/share/fenye.jsp" %>
			</form>
			<div class="clr"></div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td colspan="2" align="right" >&nbsp;
						<input name="saveButton" type="button" value="派单" onclick="doDistributionOrder()" />
					</td>
					<td colspan="2" align="left">
						<input name="clearButton" type="button" value="不派" onclick="doCloase()" />
					</td>
				</tr>
			</table>
	</div>
	<div id="showLoading" style="display:none;border: 1px solid #999999;padding:15px 10px;color:#006699;font-size:14px; ">
	    <img src="${ctx}/static/images/loading.gif">&nbsp;&nbsp;<b>正在处理，请稍候..</b>
	  </div>
</body>
</html>