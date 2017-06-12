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
	$("#form").attr("action","${ctx}/system/employee/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

function toAdd(){
	location.href = '${ctx}/system/employee/add';
}

//设置员工工作状态
function doSetWorkStatus(employeeId){
	//弹框显示
	artDialog.open('${ctx}/system/employee/toSetWorkStatus/'+employeeId, 
				{
					title:"设置员工工作状态",
					width:550,
					height:220,
					lock:true,
        			style:'succeed noClose'
        		}
	);
}

//启用
function doOpenAccount(id,op){
	var tip = "禁用";
	if(op == 1){
		tip = "开通";
	}
	artDialog.confirm('确定要'+tip+'吗？', function(){
		$.get("${ctx}/system/employee/openAccount/"+id+"/"+op,function(data){
			if(data == 1){
				artDialog.tips(tip+'成功', 1.5);
				reload();
			}else if(data == 2){
				artDialog.tips(tip+'失败', 1.5);
			}else if(data == 3){
				artDialog.tips('离职员工禁止开通账户', 1.5);
			}else{
				artDialog.tips('此员工工作状态开启中,不得禁用', 2);
			}
		},"json");
	});
}

//修改
function toUpdate(id){
	location.href = "${ctx}/system/employee/edit/"+id;
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1500);
}


</script>
<title>员工列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />员工管理&gt;<font color="#FF9900">员工列表</font>
	</h2>
	<fieldset>
		<legend><strong>员工列表</strong></legend>
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
						<input name="searchButton" type="button" value="添加" onclick="toAdd()" />
					</td>
				</tr>
				
			</table>
		</div>
			
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="7%">员工编号</th>
			        <th width="7%">姓名</th>
			        <th width="5%">年龄</th>
			        <th width="5%">学历</th>
			        <th width="5%">证件类型</th>
			        <th width="5%">证件号</th>
			        <th width="5%">状态</th>
			        <th width="5%">职位</th>
			        <th width="7%">手机号</th>
			        <th width="5%">入职时间</th>
			        <th width="10%">创建时间</th>
			        <th width="10%">修改时间</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='13' style="color:red; text-align: center;">很抱歉，您所查找的员工不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="EmployeeVo">
		        	<tr>
		        		<td>${EmployeeVo.employee.number}&nbsp;</td>
		        		<td>${EmployeeVo.employee.name}&nbsp;</td>
		        		<td>${EmployeeVo.employee.age}</td>
		        		<td><c:if test="${EmployeeVo.employee.qualification eq 1}">研究生</c:if><c:if test="${EmployeeVo.employee.qualification eq 2}">本科</c:if><c:if test="${EmployeeVo.employee.qualification eq 3}">专科</c:if><c:if test="${EmployeeVo.employee.qualification eq 4}">高中</c:if><c:if test="${EmployeeVo.employee.qualification eq 5}">初中</c:if>&nbsp;</td>
		        		<td><c:if test="${EmployeeVo.employee.certificateType eq 1}">身份证</c:if><c:if test="${EmployeeVo.employee.certificateType eq 2}">军官证</c:if><c:if test="${EmployeeVo.employee.certificateType eq 3}">驾驶证</c:if>&nbsp;</td>
		        		<td>${EmployeeVo.employee.certificateNumber}&nbsp;</td>
		        		<td><c:if test="${EmployeeVo.employee.status eq 1}">在职</c:if><c:if test="${EmployeeVo.employee.status eq 2}">离职</c:if><c:if test="${EmployeeVo.employee.status eq 3}">休假</c:if>&nbsp;</td>
		        		<td><c:if test="${EmployeeVo.employee.position eq 1}">洗车员</c:if><c:if test="${EmployeeVo.employee.position eq 2}">维修员</c:if><c:if test="${EmployeeVo.employee.position eq 3}">保养员</c:if><c:if test="${EmployeeVo.employee.position eq 4}">客服</c:if><c:if test="${EmployeeVo.employee.position eq 5}">财务</c:if>&nbsp;</td>
		        		<td>${EmployeeVo.employee.phone}&nbsp;</td>
		        		<td>${EmployeeVo.entryDate }&nbsp;</td>
		        		<td>${EmployeeVo.createDate}&nbsp;</td>
		        		<td>${EmployeeVo.modifyDate}&nbsp;</td>
		        		<td align="left">
							<c:if test="${EmployeeVo.employee.password eq null || EmployeeVo.employee.password eq '-1'}">
								<a href="javascript:void(0);" onclick="doOpenAccount(${EmployeeVo.employee.id},1)">开通账户</a>
								<a href="javascript:void(0);"  onclick="toUpdate(${EmployeeVo.employee.id})">修改</a>
							</c:if>
							<c:if test="${EmployeeVo.employee.password ne null && EmployeeVo.employee.password ne '-1'}">
								<a href="javascript:void(0);" onclick="doOpenAccount(${EmployeeVo.employee.id},2)">禁用账户</a>
								<c:if test="${EmployeeVo.employee.isLoginSystem != 1}">
								<a href="javascript:void(0);" onclick="doSetWorkStatus(${EmployeeVo.employee.id})">设置工作状态</a>
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