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
	$("#form").attr("action","${ctx}/system/order/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

//查询详情
function searchItem(id){
	location.href = '${ctx}/system/order/detail/'+id;
}


//取消
function doCancel(id,oldStatus){
	artDialog.confirm('是否取消订单？', function(){
		$.get("${ctx}/system/order/cancel/"+id+"/"+oldStatus,function(data){
			if(data == 1){
				artDialog.tips('取消成功', 1.5);
				reload();
			}else if(data == 2){
				artDialog.tips('取消失败', 1.5);
			}else{
				artDialog.tips('订单状态已变更，取消失败，请刷新列表',1.5);
			}
		},"json");
	});
}

//取消
function doAudit(id,oldStatus){
	art.dialog({
	    content: '是否审核通过？',
	    button: [
	        {
	            name: '同意',
	            callback: function () {
	            	$.get("${ctx}/system/order/audit/"+id+"/"+oldStatus+"/1",function(data){
	    				if(data == 1){
	    					artDialog.tips('审核成功', 1.5);
	    					reload();
	    				}else if(data == 2){
	    					artDialog.tips('审核失败', 1.5);
	    				}
	    			},"json");
	                return false;
	            },
	            focus: true
	        },
	        {
	            name: '不同意',
	            callback: function () {
	            	$.get("${ctx}/system/order/audit/"+id+"/"+oldStatus+"/2",function(data){
	    				if(data == 1){
	    					artDialog.tips('审核成功', 1.5);
	    					reload();
	    				}else if(data == 2){
	    					artDialog.tips('审核失败', 1.5);
	    				}
	    			},"json");
	            }
	        }
	    ]
	});
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1500);
}

//手工派单
function doSelectEmployee(orderType,orderId,regionId,status){
// 		artDialog.data('orderType', orderType);
// 		artDialog.data('orderId', orderId);
// 		artDialog.data('regionId',regionId);
		artDialog.open('${ctx}/system/order/selectEmployee?orderType='+orderType+"&orderId="+orderId+"&regionId="+regionId+"&status="+status, 
				{
					title:"手工派单",
					width:750,
					height:440,
					lock:true,
					style:'succeed noClose'
				},false
		);
}


//修改
function toUpdate(id){
	location.href = "${ctx}/system/order/edit/"+id;
}
</script>
<title>订单列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />订单管理&gt;<font color="#FF9900">订单列表</font>
	</h2>
	<fieldset>
		<legend><strong>订单列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">订单号:
						<input id="orderSn" name="orderSn" type="text"/>
					</td>
					<td align="left" width="50%">&nbsp;
						<input name="searchButton" type="button" value="查询" onclick="doSearch()" />
					</td>
				</tr>
				
			</table>
		</div>
			
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="7%">订单号</th>
			        <th width="8%">手机号</th>
			        <th width="5%">服务类型</th>
			        <th width="15%">预约时间</th>
			        <th width="5%">状态</th>
			        <th width="5%">订单所属</th>
			        <th width="10%">创建时间</th>
			        <th width="7%">审核状态</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='9' style="color:red; text-align: center;">很抱歉，没有订单</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="order">
		        	<tr>
		        		<td>${order.orderSn}&nbsp;</td>
		        		<td>${order.mobileNumber}&nbsp;</td>
		        		<td><c:if test="${order.orderType eq 1}">洗车</c:if><c:if test="${order.orderType eq 2}">维修</c:if><c:if test="${order.orderType eq 3}">保养</c:if></td>
		        		<td>${order.appointmentTime }&nbsp;</td>
		        		<td><c:if test="${order.status eq 1}">下单成功</c:if><c:if test="${order.status eq 2}">取消订单</c:if><c:if test="${order.status eq 3}">已派单</c:if><c:if test="${order.status eq 4}">处理中</c:if><c:if test="${order.status eq 5}">处理完成</c:if><c:if test="${order.status eq 6}">无效订单</c:if><c:if test="${order.status eq 7}">消费完成</c:if></td>
		        		<td>${order.orderOwn eq 1 ? "自营" : "商家"}</td>
		        		<td>${order.createDate}&nbsp;</td>
		        		<td><c:if test="${order.auditStatus eq 0}">未审核 </c:if><c:if test="${order.auditStatus eq 1}">通过</c:if><c:if test="${order.auditStatus eq 2}">未通过</c:if>&nbsp;</td>
		        		<td align="left">
							<c:if test="${order.status eq 1 && order.auditStatus eq 0}">
								<a href="javascript:void(0);"  onclick="doAudit(${order.id},${order.status})">审核</a>
							</c:if>
							<a href="javascript:void(0);"  onclick="searchItem(${order.id})">详情</a>
							<c:if test="${order.status eq 1 && order.auditStatus eq 1}">
							<a href="javascript:void(0);"  onclick="doCancel(${order.id},${order.status})">取消订单</a>
							</c:if>
							<c:if test="${order.auditStatus eq 1}">
								<c:if test="${order.status eq 1 && order.orderOwn eq 1}">
								<a href="javascript:void(0);"  onclick="doSelectEmployee(${order.orderType},${order.id},${order.bussinessDistrict},${order.status})">手工派单</a>
								</c:if>
								<c:if test="${order.status eq 1 || order.status eq 3 }">
									<a href="javascript:void(0);" title="状态为下单成功和已派单可修改"  onclick="toUpdate(${order.id})">修改</a>
								</c:if>
							</c:if>
		        		</td>
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