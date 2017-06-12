<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script type="text/javascript">
$().ready(function(){
	//如果保存成功
	if("${flag}" == "true"){
		artDialog.tips('操作成功！', 1.5);
	}else if("${flag}" == "false"){
		artDialog.tips('操作失败！', 1.5);
	}
});

//搜索
function doSearch(){
	$("#form").attr("action","${ctx}/system/employee/workRecordList");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}


//订单信息
function showOrderDetail(orderId){
	//弹框显示
	artDialog.open('${ctx}/system/order/detail/'+orderId, 
				{
					title:"订单信息",
					width:1100,
					height:450,
					lock:true,
        			style:'succeed noClose'
        		}
	);
}

</script>
<title>工作记录列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />员工管理&gt;<font color="#FF9900">工作记录列表</font>
	</h2>
	<fieldset>
		<legend><strong>工作记录列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">员工编号：
						<input type="text" id="number" name="number" value="${number}"/>
					</td>
						
					<td align="left" width="50%">&nbsp;
						<input name="searchButton" type="button" value="查询" onclick="doSearch()" />
					</td>
				</tr>
				
			</table>
		</div>
			
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="7%">员工号</th>
			        <th width="7%">订单号</th>
			        <th width="5%">状态</th>
			        <th width="5%">开始时间</th>
			        <th width="5%">结束时间</th>
			        <th width="5%">洗前照片</th>
			        <th width="5%">洗后照片</th>
			        <th width="5%">创建时间</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='8' style="color:red; text-align: center;">很抱歉，没有工作记录</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="employeeWorkRecordVo">
		        	<tr>
		        		<td>${employeeWorkRecordVo.employee.number}&nbsp;</td>
		        		<td><a href="javascript:void(0);" onclick="showOrderDetail(${employeeWorkRecordVo.order.id})">${employeeWorkRecordVo.order.orderSn}</a>&nbsp;</td>
		        		<td><c:if test="${employeeWorkRecordVo.status eq 1}">未处理</c:if><c:if test="${employeeWorkRecordVo.status eq 2}">处理中</c:if><c:if test="${employeeWorkRecordVo.status eq 3}">处理完</c:if>&nbsp;</td>
		        		<td>${employeeWorkRecordVo.startDate}&nbsp;</td>
		        		<td>${employeeWorkRecordVo.endDate}&nbsp;</td>
		        		<td><img width="50" height="50" src="${ctx}/static/washImg/${employeeWorkRecordVo.washBeforePhoto}"/>&nbsp;</td>
		        		<td><img width="50" height="50" src="${ctx}/static/washImg/${employeeWorkRecordVo.washAfterPhoto}"/>&nbsp;</td>
		        		<td>${employeeWorkRecordVo.createDate}&nbsp;</td>
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