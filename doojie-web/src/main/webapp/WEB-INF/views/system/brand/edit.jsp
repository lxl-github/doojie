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
	if("${flag}" == "uploadFaile"){
		artDialog.tips('上传失败！', 1.5);
	}
	$("#brandSearch").val("${brand.brandSearch}");
	//验证 start
	$("#brandForm").validate({
		debug:false,
		rules:{
			brandName:{
				required: true,
				maxlength:20,
				remote:{
 					url: "${ctx}/system/brand/check",
 					data: {
 						name: function() {
 	                        return $.trim($("#brandName").val());
 						},
 						origialName:function() {
 	                        return $.trim($("#origialName").val());
 						}
                     },
 		       	    type: 'POST',
 		       	    dataType: 'json',
 		       	    dataFilter:function(data,type){
 		       	    	return data;
 		       	    }
 				}
			},
			brandSearch:{
				selectNone:true
			}
		},
		messages:{
			brandName:{
				required:"请输入品牌名称",
				maxlength:$.format("最多{0}个字符！")
 				,remote:"该品牌名称已经存在"
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
		$("#brandForm").attr("action","${ctx}/system/brand/update");
		if($("#brandForm").valid()){
			 $.blockUI({ message: $('#showLoading')});
			 $("#brandForm").submit(); 
		}
 	}
</script>
<title>修改品牌</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />品牌管理&gt;<font color="#FF9900">修改品牌</font>
	</h2>
	<fieldset>
		<legend><strong>修改品牌</strong></legend>
	<form id="brandForm" action="" method="post" enctype ="multipart/form-data">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td align="left" width="9%">品牌名称：</td>
					<td colspan="3">
						<input type="hidden" id="id" name="id" value="${brand.id}"/>
						<input type="hidden" id="origialName" name="origialName" value="${brand.brandName}"/>
						<input type="text" id="brandName" name="brandName" value="${brand.brandName}"/>
					</td>
				</tr>
				<tr>
					<td align="left" width="9%">品牌logo：</td>
					<td colspan="3">
						<input type="file"  id="logoFile" name="logoFile" value="${brand.brandLogo}"/>
						<input type="hidden" id="logoPath" name="logoPath" value="${brand.logoPath}"/>
						<img height="50" src="${ctx}/static/brandLogo/${brand.brandLogo}"/>
					</td>
				</tr>
				<tr>
					<td align="left">品牌检索字母：</td>
					<td colspan="3">
						<select id="brandSearch" name="brandSearch">
						<option value="0">请选择</option>
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
						<option value="E">E</option>
						<option value="F">F</option>
						<option value="G">G</option>
						<option value="H">H</option>
						<option value="I">I</option>
						<option value="J">J</option>
						<option value="K">K</option>
						<option value="L">L</option>
						<option value="M">M</option>
						<option value="N">N</option>
						<option value="O">O</option>
						<option value="P">P</option>
						<option value="Q">Q</option>
						<option value="R">R</option>
						<option value="S">S</option>
						<option value="T">T</option>
						<option value="U">U</option>
						<option value="V">V</option>
						<option value="W">W</option>
						<option value="X">X</option>
						<option value="Y">Y</option>
						<option value="Z">Z</option>
						</select>
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