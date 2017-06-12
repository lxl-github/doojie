<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script type="text/javascript">
$().ready(function(){
	//验证 start
	$("#serverForm").validate({
		debug:false,
		rules:{
			price: {
			    required: true
			},
			vipPrice:{
				required:true
			}
		},
		messages:{
			price: {
			    required: "请输入服务价格"
			},
			vipPrice:{
				required:"请输入会员价格"
			}
		}
	});
	//验证 end
});


function doBack(){
	history.back();
}

//修改
function doUpdate(){
		$("#serverForm").attr("action","${ctx}/bussiness/server/update");
		if($("#serverForm").valid()){
			$.blockUI({ message: $('#showLoading')});
			$("#serverForm").submit();
		}
 	}
</script>
<title>编辑服务</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />服务管理&gt;<font color="#FF9900">编辑服务</font>
	</h2>
	<fieldset>
		<legend><strong>编辑服务</strong></legend>
	<form id="serverForm" action="" method="post">
		<input type="hidden" id="id" name="id" value="${server.id}">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td align="left" width="9%">服务名称：</td>
					<td colspan="3">
						<input type="text" id="name" readonly="readonly" name="name" value="${server.name}"/>
					</td>
				</tr>
				<tr>
				<td align="left" width="9%">服务价格：</td>
					<td colspan="3">
						<input type="text"  id="price" name="price" value="${server.price}"/>
					</td>
					
				</tr>
				<tr>
				<td align="left">会员价格：</td>
					<td colspan="3">
						<input type="text" id="vipPrice" name="vipPrice" value="${server.vipPrice}"/>
					</td>
					
				</tr>
				<tr>
					<td align="left" width="10%">备注：</td>
					<td colspan="3" align="left" >
						<textarea rows="4" cols="56" id="remark" name="remark">${server.remark}</textarea>
					</td>
				</tr>
				<tr >
					<td colspan="2" align="right" >&nbsp;
						<input name="saveButton" type="button" value="保存 " onclick="doUpdate()" />
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