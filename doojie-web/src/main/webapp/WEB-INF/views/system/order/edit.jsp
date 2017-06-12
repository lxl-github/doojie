<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script src="${ctx}/static/js/Calendar.js" type="text/javascript"></script>
<title>订单修改</title>
<script type="text/javascript">
$().ready(function(){
	if("${flag}" == "false"){
		artDialog.tips('操作失败！', 1.5);
	}else if("${flag}" == "changeStatus"){
		artDialog.tips('订单状态已变更,无法修改！', 2);
	}else if("${flag}" == "timeLess"){
		artDialog.tips('预约日期不能小于当前日期！', 2);
	}else if("${flag}" == "hourLess"){
		artDialog.tips('预约时段区间不能小于当前时段！', 2);
	}
	$("#time").val("${fn:split(orderDetailVo.appointmentTime,' ')[1]}");
});

function doBack(){
	location.href = "${ctx}/system/order/list";
}

//保存
function doUpdate(){
		$("#orderForm").attr("action","${ctx}/system/order/update");
		 $.blockUI({ message: $('#showLoading')});
		 $("#orderForm").submit(); 
 	}
</script>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />订单管理&gt;<font color="#FF9900">订单修改</font>
	</h2>
	<fieldset>
		<legend><strong>订单修改</strong></legend>
		<form id="orderForm" method="post" action="">
		<input name="clearButton" type="button" value="返回" onclick="doBack()" />
		<input name="clearButton" type="button" value="保存" onclick="doUpdate()" />
		<input type="hidden" id="orderId" name="orderId" value="${orderDetailVo.id}"/>
		<div class="huibox" >
			<table style="background-color: orange;" width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">
					<strong>基本信息</strong>
					</td>
				</tr>
			</table>
			<table  width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left"  width="8%">
						订单号：
					</td>
					<td align="left" >
					 ${orderDetailVo.orderSn}
					</td>
				</tr>
				<tr>
					<td align="left"  width="8%">
						订单状态：
					</td>
					<td align="left" >
					<c:if test="${orderDetailVo.status eq 1}">下单成功</c:if><c:if test="${orderDetailVo.status eq 2}">取消订单</c:if><c:if test="${orderDetailVo.status eq 3}">已派单</c:if><c:if test="${orderDetailVo.status eq 4}">处理中</c:if><c:if test="${orderDetailVo.status eq 5}">处理完成</c:if><c:if test="${orderDetailVo.status eq 6}">无效订单</c:if><c:if test="${orderDetailVo.status eq 7}">消费完成</c:if>
					</td>
				</tr>
				<tr>
					<td align="left"  width="8%">
						服务类型：
					</td>
					<td align="left" >
					<c:if test="${orderDetailVo.orderType eq 1}">洗车</c:if><c:if test="${orderDetailVo.orderType eq 2}">维修</c:if><c:if test="${orderDetailVo.orderType eq 3}">保养</c:if>
					</td>
				</tr>
				<tr>
					<td align="left"  width="8%">
						预约电话：
					</td>
					<td align="left">
					 ${orderDetailVo.mobileNumber}
					</td>
					
				</tr>
				<tr>
				<td align="left"  width="8%">
						预约时间：
					</td>
					<td align="left">
					 ${orderDetailVo.appointmentTime}
					 <input id="appointmentDate" onclick="new Calendar().show(this,this);" readonly="readonly" name="appointmentDate" value="${fn:split(orderDetailVo.appointmentTime,' ')[0]}">&nbsp;
<%-- 					 <input id="time" name="time" value="${fn:split(orderDetailVo.appointmentTime,' ')[1]}"> --%>
					 <select id="time" name="time">
					  <option value="07:00~09:00">07:00~09:00</option>
					  <option value="09:00~11:00">09:00~11:00</option>
					  <option value="12:00~14:00">12:00~14:00</option>
					  <option value="14:00~16:00">14:00~16:00</option>
					  <option value="16:00~18:00">16:00~18:00</option>
					 </select>
					</td>
				</tr>
				<tr>
					<td align="left"  width="8%">
						车辆信息：
					</td>
					<td align="left">
						${orderDetailVo.brand.brandName} - ${orderDetailVo.models.modelName} &nbsp; ${orderDetailVo.color} &nbsp; ${orderDetailVo.plateNumber}
					</td>
				</tr>
				<c:if test="${orderDetailVo.orderOwn eq 2 }">
				<tr>
					<td align="left"  width="8%">
						车行名称：
					</td>
					<td align="left">
					 ${orderDetailVo.bussiness.name}&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left"  width="8%">
						车行电话：
					</td>
					<td align="left">
					 ${orderDetailVo.bussiness.tel}&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left"  width="8%">
						车行地址：
					</td>
					<td align="left">
					 ${orderDetailVo.bussiness.address}&nbsp;
					</td>
				</tr>
				</c:if>
				<c:if test="${orderDetailVo.orderOwn eq 1 }">
				<tr>
					<td align="left"  width="8%">
						位置信息：
					</td>
					<td align="left">
					 ${orderDetailVo.regionCity.name} - ${orderDetailVo.regionDistrict.name} - ${orderDetailVo.regionBussinessDistrict.name} - ${orderDetailVo.regionDetail} &nbsp;
					</td>
				</tr>
				<c:if test="${orderDetailVo.status ne 1 && orderDetailVo.status ne 2 && orderDetailVo.status ne 6}">
				<tr>
					<td align="left"  width="8%">
						处理人：
					</td>
					<td align="left">
					 ${orderDetailVo.employeeWorkRecordVo.employee.name}&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left"  width="8%">
						处理人电话：
					</td>
					<td align="left">
					 ${orderDetailVo.employeeWorkRecordVo.employee.phone}&nbsp;
					</td>
				</tr>
				<c:if test="${orderDetailVo.employeeWorkRecordVo.status eq 2}">
				<tr>
					<td align="left"  width="8%">
						处理前照片：
					</td>
					<td align="left">
					 <img src="${ctx}/static/washImg/${orderDetailVo.employeeWorkRecordVo.washBeforePhoto}"/>&nbsp;
					</td>
				</tr>
				</c:if>
				<c:if test="${orderDetailVo.employeeWorkRecordVo.status eq 3}">
				<tr>
					<td align="left"  width="8%">
						处理后照片：
					</td>
					<td align="left">
					 <img src="${ctx}/static/washImg/${orderDetailVo.employeeWorkRecordVo.washAfterPhoto}"/>&nbsp;
					</td>
				</tr>
				</c:if>
				</c:if>
				</c:if>
			</table>
		</div>
		</form>
		<div class="huibox" >
			<table width="100%" style="background-color: orange;" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">
					<strong>跟踪信息</strong>
					</td>
				</tr>
			</table>
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="7%">内容</th>
			        <th width="8%">记录时间</th>
		        </tr>
		        <c:if test="${fn:length(orderDetailVo.orderTrackList) eq 0}">
				  	<tr>
				  		<td colspan='2' style="color:red; text-align: center;">很抱歉，没有跟踪记录</td>
				  	</tr>
				</c:if>
				<c:if test="${fn:length(orderDetailVo.orderTrackList) gt 0}">
		        <c:forEach items="${orderDetailVo.orderTrackList}" var="orderTrack">
		        	<tr>
		        		<td>${orderTrack.content}&nbsp;</td>
		        		<td>${orderTrack.createDate}&nbsp;</td>
		        	</tr>
		        </c:forEach>
		       	</c:if>
			</table>
		</div>
		
			</fieldset>
			<div class="clr"></div>
	</div>
	<div id="showLoading" style="display:none;border: 1px solid #999999;padding:15px 10px;color:#006699;font-size:14px; ">
	    <img src="${ctx}/static/images/loading.gif">&nbsp;&nbsp;<b>正在处理，请稍候..</b>
	  </div>
</body>
</html>