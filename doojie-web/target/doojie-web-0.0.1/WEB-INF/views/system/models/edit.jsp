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
	$("#modelsForm").validate({
		debug:false,
		rules:{
			modelName:{
				required: true,
				maxlength:20,
				remote:{
 					url: "${ctx}/system/models/check",
 					data: {
 							name: function() {
 	                        return $.trim($("#modelName").val());
 						},
 						brandId:function() {
 	                        return $.trim($("#brandId").val());
 						},
 						originalName:function() {
 	                        return $.trim($("#originalName").val());
 						}
                     },
 		       	    type: 'POST',
 		       	    dataType: 'json',
 		       	    dataFilter:function(data,type){
 		       	    	return data;
 		       	    }
 				}
			}
		},
		messages:{
			modelName:{
				required:"请输入型号名称",
				maxlength:$.format("最多{0}个字符！")
 				,remote:"该型号名称已经存在"
			}
		}
	});
	//验证 end
});


function doBack(){
	history.back();
}

//保存
function doUpdate(){
		$("#modelsForm").attr("action","${ctx}/system/models/update");
		if($("#modelsForm").valid()){
			 $.blockUI({ message: $('#showLoading')});
			 $("#modelsForm").submit(); 
		}
 	}
</script>
<title>修改型号</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />品牌管理&gt;<font color="#FF9900">修改型号</font>
	</h2>
	<fieldset>
		<legend><strong>修改型号</strong></legend>
	<form id="modelsForm" action="" method="post">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td align="left" width="9%">型号名称：</td>
					<td colspan="3">
						<input type="text" id="modelName" name="modelName" value="${models.modelName}"/>
						<input type="hidden" id="id" name="id" value="${models.id}"/>
						<input type="hidden" id="brandId" name="brandId" value="${models.brandId}"/>
						<input type="hidden" id="originalName" name="originalName" value="${models.modelName}"/>
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