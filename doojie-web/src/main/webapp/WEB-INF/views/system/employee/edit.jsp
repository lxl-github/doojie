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
	$("#position").val(${employee.position});
	$("#qualification").val(${employee.qualification});
	$("#certificateType").val(${employee.certificateType});
	$("#status").val(${employee.status});
	//验证 start
	$("#employeeForm").validate({
		debug:false,
		rules:{
			name:{
				required: true,
				maxlength:20
			},
			age:{
				required:true,
				number:true
			},
			phone:{
				required:true
			},
			qualification: {
				selectNone:true
			},
			certificateNumber:{
				required:true,
				number:true
			},
			entryTime:{
				required:true
			}
		},
		messages:{
			name:{
				required:"请输入员工姓名",
				maxlength:$.format("最多{0}个字符！")
			},
			age: {
			    required: "请输入年龄"
			},
			phone:{
				required:"请输入手机号"
			},
			certificateNumber:{
				required:"请输入证件号",
			},
			entryTime:{
				required:"请选择入职时间"
			}
		}
	});
	//验证 end
	
	if("${employee.isLoginSystem}" == 1){
		$("#isLoginSystem").attr("checked","checked");
		$("#role").show();
	}
});


function doBack(){
	history.back();
}

//保存
function doAdd(){
	$("#employeeForm").attr("action","${ctx}/system/employee/update");
	if($("#employeeForm").valid()){
		 $.blockUI({ message: $('#showLoading')});
		 $("#employeeForm").submit(); 
	}
 }
 
//选择角色
function selectRole(){
	if($("#isLoginSystem").attr("checked") == "checked"){
		$("#role").show();
	}else{
		$("#role").hide();
	}
}
</script>
<title>修改员工</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />员工管理&gt;<font color="#FF9900">修改员工</font>
	</h2>
	<fieldset>
		<legend><strong>修改员工</strong></legend>
	<form id="employeeForm" action="" method="post">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td align="left" width="9%">员工姓名：</td>
					<td colspan="3">
						<input type="hidden"  id="id" name="id" value="${employee.id}"/>
						<input type="text" id="name" name="name" value="${employee.name}"/>
					</td>
				</tr>
				<tr>
					<td align="left" width="9%">员工编号：</td>
					<td colspan="3">
					${employee.number}
						<input type="hidden"  id="number" name="number" value="${employee.number}"/>
					</td>
					
				</tr>
				<tr>
					<td align="left">年龄：</td>
					<td colspan="3">
						<input type="text" id="age" name="age" value="${employee.age}"/>
					</td>
					
				</tr>
				<tr>
					<td align="left">手机号：</td>
					<td colspan="3">
						<input type="text" id="phone" name="phone" value="${employee.phone}"/>
					</td>
					
				</tr>
				<tr>
					<td align="left">学历：</td>
					<td colspan="3">
					<select id="qualification" name="qualification">
							<option value="1">研究生</option>
							<option value="2">本科</option>
							<option value="3">专科</option>
							<option value="4">高中</option>
							<option value="5">初中</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">证件类型：</td>
					<td colspan="3">
					<select id="certificateType" name="certificateType">
							<option value="1">身份证</option>
							<option value="2">军官证</option>
							<option value="3">驾驶证</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">证件号：</td>
					<td colspan="3">
					<input type="text"  id="certificateNumber" name="certificateNumber" value="${employee.certificateNumber}" />
					</td>
				</tr>
				<tr>
					<td align="left">上班状态：</td>
					<td colspan="3">
					<select id="status" name="status">
							<option value="1">在职</option>
							<option value="2">离职</option>
							<option value="3">休假</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">入职时间：</td>
					<td colspan="3">
						<input type="text"  onclick="new Calendar().show(this,this);" readonly="readonly"   id="entryDate" name="entryDate" value="${entryDate}" />
					</td>
				</tr>
				<tr>
					<td align="left">职位：</td>
					<td colspan="3">
					<select id="position" name="position">
							<option value="1">洗车员</option>
							<option value="2">维修员</option>
							<option value="3">保养员</option>
							<option value="4">客服</option>
							<option value="5">财务</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left" width="9%">是否可以登录后台：</td>
					<td colspan="3">
						<input type="checkbox" onchange="selectRole()"  id="isLoginSystem" name="isLoginSystem" value="1"/>
					</td>
				</tr>
				<tr id="role" style="display:none;">
					<td align="left" width="9%">角色选择：</td>
					<td colspan="3">
						<input type="checkbox" id="" name="" value="1"/>
					</td>
				</tr>
				<tr>
					<td align="left" width="10%">备注：</td>
					<td colspan="3" align="left" >
						<textarea rows="4" cols="56" id="remark" name="remark"></textarea>
					</td>
				</tr>
				<tr >
					<td colspan="2" align="right" >&nbsp;
						<input name="saveButton" type="button" value="保存 " onclick="doAdd()" />
					</td>
					<td colspan="2" align="left">
						<input name="clearButton" type="button" value="返回" onclick="doBack()" />
					</td>
				</tr>
			</table>
		</div>
	</form>
	</fieldset>
	<div class="clr"></div>
	</div>
	<div id="showLoading" style="display:none;border: 1px solid #999999;padding:15px 10px;color:#006699;font-size:14px; ">
	    <img src="${ctx}/static/images/loading.gif">&nbsp;&nbsp;<b>正在处理，请稍候..</b>
	  </div>
</body>
</html>