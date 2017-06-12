<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/share/meta.jsp"%>
<script type="text/javascript">
$().ready(function(){
	$("#prodcutType").val(${product.productType});
	$("#isDoor").val(${product.isDoor});
	$("#prodcutCategory").val(${product.productCategory});
	$("#monthNumber").val(${product.monthNumber});
	//验证 start
	$("#productForm").validate({
		debug:false,
		rules:{
			name:{
				required: true,
				maxlength:20,
				remote:{
 					url: "${ctx}/system/product/check",
 					data: {
 							name: function() {
 	                        return $.trim($("#name").val());
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
			productType: {
				selectNone:true
			},
			price:{
				required:true,
				number:true
			},
			discount:{
				required:true,
				isDiscount:true,
				isNotOne:true
			},
			number:{
				required:true,
				number:true
			}
		},
		messages:{
			name:{
				required:"请输入商品名称",
				maxlength:$.format("最多{0}个字符！")
 				,remote:"该商品名称已经存在"
			},
			price: {
			    required: "请输入原价"
			},
			discount:{
				required:"请输入折扣"
			},
			number:{
				required:"请输入数量"
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
		$("#productForm").attr("action","${ctx}/system/product/update");
		if($("#productForm").valid()){
			 $.blockUI({ message: $('#showLoading')});
			 $("#productForm").submit(); 
		}
 	}
 	
function cal(){
	var price = $("#price").val();
	var dis = $("#discount").val();
	if(price != null && dis != null){
		if(dis <= 1 && dis > 0){
			$("#disPrice").val(parseInt(price)*parseFloat(dis));
			$("#discountPrice").val($("#disPrice").val()*100);
		}else{
			$("#disPrice").val("");
			$("#discountPrice").val("");
		}
	}else{
		$("#disPrice").val("");
		$("#discountPrice").val("");
	}
}
</script>
<title>修改商品</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />商品管理&gt;<font color="#FF9900">修改商品</font>
	</h2>
	<fieldset>
		<legend><strong>修改商品</strong></legend>
	<form id="productForm" action="" method="post">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr >
					<td align="left" width="9%">商品名称：</td>
					<td colspan="3">
						<input type="text" id="name" name="name" value="${product.name}"/>
						<input type="hidden" id="origialName" name="origialName" value="${product.name}"/>
						<input type="hidden" id="id" name="id" value="${product.id}"/>
					</td>
				</tr>
				<tr>
					<td align="left" width="9%">商品编号：</td>
					<td colspan="3">
						<input type="text" readonly="readonly" id="productCode" name="productCode" value="${product.productCode}"/>
					</td>
					
				</tr>
				<tr>
					<td align="left">使用方式：</td>
					<td colspan="3">
						<select id="isDoor" name="isDoor">
							<option value="0">到店</option>
							<option value="1">上门</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">商品类型：</td>
					<td colspan="3">
						<select id="productType" name="productType">
							<option value="1">卡</option>
							<option value="2">券</option>
						</select>
					</td>
					
				</tr>
				<tr>
					<td align="left">原价：</td>
					<td colspan="3">
					<input type="text" onblur="cal()" id="price" name="price" value="${product.price}"/>
					</td>
				</tr>
				<tr>
					<td align="left">折扣：</td>
					<td colspan="3">
					<input type="text"  onblur="cal()" id="discount" name="discount" value="${product.discount}"/>注:请保留俩位小数
					</td>
				</tr>
				<tr>
					<td align="left">折扣价：</td>
					<td colspan="3">
					<input type="text"  id="disPrice" name="disPrice" value="${product.discountPrice}" readonly="readonly"/>
					<input type="hidden"  id="discountPrice" name="discountPrice" value="${product.discountPrice*100}" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="left">数量：</td>
					<td colspan="3">
					<input type="text"  id="number" name="number" value="${product.number }" />
					</td>
				</tr>
				<tr>
					<td align="left">有效期：</td>
					<td colspan="3">
					<select  id="monthNumber" name="monthNumber">
					<option value="1">1个月</option>
					<option value="3">3个月</option>
					<option value="6">6个月</option>
					<option value="9">9个月</option>
					<option value="12">一年</option>
					</select>
					</td>
				</tr>
				<tr>
					<td align="left">商品分类：</td>
					<td colspan="3">
						<select id="productCategory" name="productCategory">
							<option value="1">洗车</option>
							<option value="2">维修</option>
							<option value="3">保养</option>
							<option value="4">家政保洁</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">适用区域：</td>
					<td colspan="3">
					<select id="regionId" name="regionId">
						<c:if test="${fn:length(regionList) gt 0}">
							<c:forEach items="${regionList}" var="region">
							<c:if test="${region.id eq product.regionId}">
								<option value="${region.id}" selected="selected">${region.name}</option>
							</c:if>
							<c:if test="${region.id ne product.regionId}">
								<option value="${region.id}">${region.name}</option>
							</c:if>
							</c:forEach>
							</select>
						</c:if>
						<c:if test="${fn:length(regionList) eq 0}">
						<option value="0">请选择</option>
						</select>
							请到区域列表中添加区域&nbsp;<a href="${ctx}/system/region/add">去添加</a>
						</c:if>
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