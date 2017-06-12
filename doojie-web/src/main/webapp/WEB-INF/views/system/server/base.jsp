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
	$("#baseServerForm").validate({
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
	
	//如果保存成功
	if("${flag}" == "true"){
		artDialog.tips('操作成功！', 1.5);
	}else if("${flag}" == "false"){
		artDialog.tips('操作失败！', 1.5);
	}
});


function doBack(){
	history.back();
}

//保存
function doAdd(){
		$("#baseServerForm").attr("action","${ctx}/bussiness/server/baseSave");
		if($("#baseServerForm").valid()){
			$.blockUI({ message: $('#showLoading')});
			$("#baseServerForm").submit();
		}
 	}
</script>
<title>基础洗车服务</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />服务管理&gt;<font color="#FF9900">基础洗车服务</font>
	</h2>
	<fieldset>
		<legend><strong>基础洗车服务</strong></legend>
	<form id="baseServerForm" action="" method="post">
	<input type="hidden" id="type" name="type" value="${ids[0]}"/>
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr style="font-weight:bold;">
					<td>车型</td>
					<td>
						市场价
					</td>
					<td>
						会员价
					</td>
					<td>
						是否显示
					</td>
				</tr>
				<tr>
					<td>
						五座
						<input type="hidden" id="id" name="id" value="${ids[0]}"/>
						<input type="hidden" id="carType" name="carType" value="1"/>
					</td>
					<td>
						<input type="text"  id="price1" name="price" value="${prices[0]}"/>
					</td>
					<td>
						<input type="text"  id="vipPrice1" name="vipPrice" value="${vipPrices[0]}"/>
					</td>
					<td>
						<input type="checkbox" id="isShow" name="isShow" value="01" <c:if test="${isShow[0] eq 1 }"> checked</c:if>/>
					</td>
				</tr>
				<tr>
					<td>
						七座<input type="hidden" id="id" name="id" value="${ids[1]}"/>
						<input type="hidden" id="carType" name="carType" value="2"/>
					</td>
					<td>
						<input type="text"  id="price2" name="price" value="${prices[1]}"/>
					</td>
					<td>
						<input type="text"  id="vipPrice2" name="vipPrice" value="${vipPrices[1]}"/>
					</td>
					<td>
						<input type="checkbox" id="isShow" name="isShow" value="11" <c:if test="${isShow[1] eq 1 }"> checked</c:if>/>
					</td>
				</tr>
				<tr>
					<td>
						七座以上<input type="hidden" id="id" name="id" value="${ids[2]}"/>
						<input type="hidden" id="carType" name="carType" value="3"/>
					</td>
					<td>
						<input type="text"  id="price3" name="price" value="${prices[2]}"/>
					</td>
					<td>
						<input type="text"  id="vipPrice3" name="vipPrice" value="${vipPrices[2]}"/>
					</td>
					<td>
						<input type="checkbox" id="isShow" name="isShow" value="21" <c:if test="${isShow[2] eq 1 }"> checked</c:if> />
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