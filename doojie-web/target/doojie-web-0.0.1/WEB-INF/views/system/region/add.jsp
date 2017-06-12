<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script type="text/javascript">
$().ready(function(){
	$("#level").val(${level}+1);
	//验证 start
	$("#regionForm").validate({
		debug:false,
		rules:{
			name:{
				required: true,
				maxlength:20,
				remote:{
 					url: "${ctx}/system/region/check",
 					data: {
 							name: function() {
 	                           return $.trim($("#name").val());
 						    },
	                        parentId: function() {
	 	                       return $.trim($("#parentId").val());
	                        }
                     },
 		       	    type: 'POST',
 		       	    dataType: 'json',
 		       	    dataFilter:function(data,type){
 		       	    	return data;
 		       	    }
 				}
			},
			code: {
			    required: true,
			    remote:{
 					url: "${ctx}/system/region/check/code",
 					data: {
 							name: function() {
 	                        return $.trim($("#code").val());
 						   },
	                        parentId: function() {
	 	                        return $.trim($("#parentId").val());
	                        }
                     },
 		       	    type: 'POST',
 		       	    dataType: 'json',
 		       	    dataFilter:function(data,type){
 		       	    	return data;
 		       	    }
 				}
			},
			level:{
				selectNone:true
			}
		},
		messages:{
			name:{
				required:"请输入区域名称",
				maxlength:$.format("最多{0}个字符！")
 				,remote:"该区域名称已经存在"
			},
			code: {
			    required: "请输入区域代码"
			    ,remote:"该区域代码已经存在"
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
		$("#regionForm").attr("action","${ctx}/system/region/save");
		if($("#regionForm").valid()){
			 $.blockUI({ message: $('#showLoading')});
			 $("#regionForm").submit(); 
		}
 	}
</script>
<title>添加区域</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />区域管理&gt;<font color="#FF9900">添加区域</font>
	</h2>
	<fieldset>
		<legend><strong>添加区域</strong></legend>
	<form id="regionForm" action="" method="post">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="9%">上级名称：</td>
					<td colspan="3">
						<c:if test="${parentId eq 0}">中国</c:if><c:if test="${parentId ne 0}">${regionName}</c:if>
						<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
					</td>
				</tr>
				<tr >
					<td align="left" width="9%">区域名称：</td>
					<td colspan="3">
						<input type="text" id="name" name="name" value=""/>
					</td>
				</tr>
				<tr>
					<td align="left" width="9%">区域代码：</td>
					<td colspan="3">
						<input type="text"  id="code" name="code" value=""/>
					</td>
					
				</tr>
				<tr>
					<td align="left">区域等级：</td>
					<td colspan="3">
						<select id="level" name="level">
							<option value="0">请选择</option>
							<option value="1">一级</option>
							<option value="2">二级</option>
							<option value="3">三级</option>
						</select>
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