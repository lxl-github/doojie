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
	$("#bussinessForm").validate({
		debug:false,
		rules:{
			name:{
				required: true,
				maxlength:50
			},
			tel: {
			    required: true,
			    isTel:true
			},
			address: {
			    required: true
			},
			location:{
				required:true
			},
			province:{
				selectNone:true
			},
			city:{
				selectNone:true
			}
		},
		messages:{
			name:{
				required:"请输入商家名称",
				maxlength:$.format("最多{0}个字符！")
			},
			tel:{
				required:"请输入联系电话"
			},
			address: {
			    required: "请输入商家地址"
			},
			location:{
				required:"请输入位置坐标"
			}
		}
	});
	//验证 end
	
	//初始化区域
	BindProvince();
	BindCity();
	
	//选中区域的值
	$("#province").val("${bussiness.province}");
	selectedCity("${bussiness.province}");
	$("#city").val("${bussiness.city}");
	
});


function doBack(){
	history.back();
}

//修改
function doUpdate(){
		$("#bussinessForm").attr("action","${ctx}/system/bussiness/update");
		if($("#bussinessForm").valid()){
			if($.trim($("#password").val())!=""){
				if(($("#password").val()).length<6||($("#password").val()).length>18){
					artDialog.tips('密码要在6-18个字符之间！', 1.5);
					return;
				}
 			}
			
			$.blockUI({ message: $('#showLoading')});
			$("#bussinessForm").submit();
		}
 	}
</script>
<title>添加商家</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />商家管理&gt;<font color="#FF9900">编辑商家</font>
	</h2>
	<fieldset>
		<legend><strong>编辑商家</strong></legend>
	<form id="bussinessForm" action="" method="post">
	<!-- 用于保存和修改 -->
	<input type="hidden" id="id" name="id" value="${bussiness.id }"/>
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td align="left" width="9%">商家账户：</td>
					<td width="35%">
						<input type="text" id="username" readonly="readonly" name="username" value="${bussiness.username }"/>
					</td>
					<td align="left" width="9%">商家密码：</td>
					<td width="35%">
						<input type="password"  id="password" name="password" value=""/>
						<span id="sm">说明:如果不修改则还是原始密码</span>
					</td>
				</tr>
				<tr>
					<td align="left">商家名称：</td>
					<td>
						<input type="text" id="name" name="name" value="${bussiness.name }"/>
					</td>
						
					<td align="left" >联系电话：</td>
					<td>
						<input type="text" id="tel" name="tel" value="${bussiness.tel}"/>
						<span id="sm">例如:01088888888 或 1308888888或4001231234</span>
					</td>
					
				</tr>
				<tr>
					<td align="left">区域：</td>
					<td>
						<select id="province" name="province" onChange="selectMoreCity(this)"></select>&nbsp;&nbsp;
					<select id="city" name="city" ></select>
					</td>
						
					<td align="left">地址：</td>
					<td>
						<input type="text" id="address" name="address" value="${bussiness.address }"/>
					</td>
					
				</tr>
				<tr>
					<td align="left" >位置坐标：</td>
					<td>
						<input type="text" id="location" name="location" value="${bussiness.lng },${bussiness.lat}"/> <a target="_blank" href="http://api.map.baidu.com/lbsapi/getpoint/index.html">获取位置</a>
					</td>
					<td align="left">开户行：</td>
					<td>
						<input type="text" id="bank" name="bank" value="${bussiness.bank }"/>
					</td>
				</tr>
				<tr>
					<td align="left" >银行账号：</td>
					<td>
						<input type="text" id="bankAccount" name="bankAccount" value="${bussiness.bankAccount }"/>
					</td>
					<td align="left">银行账户名：</td>
					<td>
						<input type="text" id="bankAccountName" name="bankAccountName" value="${bussiness.bankAccountName }"/>
					</td>
				</tr>
				<tr>
					<td align="left" >是否已认证：</td>
					<td>
						<input type="radio" id="isAuthor" name="isAuthor" <c:if test="${bussiness.isAuthor eq null || bussiness.isAuthor == 0}"> checked="checked" </c:if> value="0"/>否
						<input type="radio" id="isAuthor" name="isAuthor" <c:if test="${bussiness.isAuthor == 1}"> checked="checked" </c:if> value="1"/>是
					</td>
					<td align="left">营业执照：</td>
					<td>
						<input type="file" id="zhizhao" name="zhizhao" value=""/>
					</td>
				</tr>
				<tr>
					<td align="left" width="10%">备注：</td>
					<td colspan="3" align="left" >
						<textarea rows="4" cols="56" id="remark" name="remark">${bussiness.remark}</textarea>
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