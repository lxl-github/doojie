<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script src="${ctx}/static/js/Calendar.js" type="text/javascript"></script>
<script type="text/javascript">
$().ready(function(){
	//如果保存成功
	if("${flag}" == "true"){
		artDialog.tips('设置成功！', 1.5);
	}else if("${flag}" == "false"){
		artDialog.tips('设置失败！', 1.5);
	}
		
	$("#status").val(${employeeWorkStatus.status});
	//验证 start
	$("#employeeForm").validate({
		debug:false,
		rules:{
			regionId: {
				selectNone:true
			}
		},
		messages:{
			regionId:{
				selectNone:"请选择负责区域",
			}
		}
	});
	//验证 end
});


function doCloase(){
	art.dialog.close();
}

//保存
function doAdd(){
		$("#employeeForm").attr("action","${ctx}/system/employee/setWorkStatus");
		if($("#employeeForm").valid()){
			 $.blockUI({ message: $('#showLoading')});
			 $("#employeeForm").submit(); 
		}
 	}
 	
</script>
<title>设置员工工作状态</title>
</head>
<body>
	<div class="rightbox" >
	<form id="employeeForm" action="" method="post">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" colspan="2" >员工编号：</td>
					<td align="left" colspan="6">
					${employee.number}
					<input type="hidden"  id="id" name="id" value="${employeeWorkStatus.id}"/>
					<input type="hidden"  id="employeeId" name="employeeId" value="${employee.id}"/>
					</td>
				</tr>
				<tr>
					<td align="left" colspan="2">工作状态：</td>
					<td align="left" colspan="2">
					<select id="status" name="status">
							<option value="1">空闲</option>
							<option value="2">清洗中</option>
						</select>
					</td>
					<td align="left" colspan="2">负责区域：</td>
					<td align="left" colspan="2">
					<select id="regionId" name="regionId">
						<c:if test="${fn:length(regionList) gt 0}">
							<c:forEach items="${regionList}" var="region">
							<c:if test="${region.id eq employeeWorkStatus.regionId}">
								<option value="${region.id}" selected="selected">${region.name}</option>
							</c:if>
							<c:if test="${region.id ne employeeWorkStatus.regionId}">
								<option value="${region.id}">${region.name}</option>
							</c:if>
							</c:forEach>
							</select>
						</c:if>
						<c:if test="${fn:length(regionList) eq 0}">
						<option value="0">请选择</option>
						</select>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="left" colspan="2">是否启用：</td>
					<td align="left" colspan="6">
						<input type="checkbox" id="isEnabled" name="isEnabled" <c:if test="${employeeWorkStatus.isEnabled eq 1}"> checked="checked" </c:if> value="1"/>
					</td>
				</tr>
				<tr >
					<td colspan="4" align="right" >&nbsp;
						<input name="saveButton" type="button" value="设置 " onclick="doAdd()" />
					</td>
					<td colspan="4" align="left">
						<input name="clearButton" type="button" value="关闭" onclick="doCloase()" />
					</td>
				</tr>
			</table>
		</div>
	</form>
	</div>
	<div id="showLoading" style="display:none;border: 1px solid #999999;padding:15px 10px;color:#006699;font-size:14px; ">
	    <img src="${ctx}/static/images/loading.gif">&nbsp;&nbsp;<b>正在处理，请稍候..</b>
	  </div>
</body>
</html>