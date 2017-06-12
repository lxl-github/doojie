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
			name:{
				required: true,
				maxlength:20,
				remote:{
 					url: "${ctx}/bussiness/server/check",
 					data: {
 							name: function() {
 	                        return $.trim($("#name").val());
 						}
                     },
 		       	    type: 'POST',
 		       	    dataType: 'json',
 		       	    dataFilter:function(data,type){
 		       	    	return data;
 		       	    }
 				}
			},
			price: {
			    required: true
			},
			vipPrice:{
				required:true
			}
		},
		messages:{
			name:{
				required:"请输入服务名称",
				maxlength:$.format("最多{0}个字符！")
 				,remote:"该服务名称已经存在"
			},
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

//保存
function doAdd(){
		$("#serverForm").attr("action","${ctx}/bussiness/server/save");
		if($("#serverForm").valid()){
			$.blockUI({ message: $('#showLoading')});
			$("#serverForm").submit();
		}
 	}
</script>
<title>添加服务</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />服务管理&gt;<font color="#FF9900">添加服务</font>
	</h2>
	<fieldset>
		<legend><strong>添加服务</strong></legend>
	<form id="serverForm" action="" method="post">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td align="left" width="9%">服务名称：</td>
					<td colspan="3">
						<input type="text" id="name" name="name" value=""/>
					</td>
				</tr>
				<tr>
				<td align="left" width="9%">服务价格：</td>
					<td colspan="3">
						<input type="text"  id="price" name="price" value=""/>
					</td>
					
				</tr>
				<tr>
				<td align="left">会员价格：</td>
					<td colspan="3">
						<input type="text" id="vipPrice" name="vipPrice" value=""/>
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