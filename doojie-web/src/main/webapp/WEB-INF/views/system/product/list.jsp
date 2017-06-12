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
	
	$("#type").val(${type});
	$("#category").val(${category});
});

//搜索
function doSearch(){
	$("#form").attr("action","${ctx}/system/product/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

function toAdd(){
	location.href = '${ctx}/system/product/add';
}

//查询详情
function searchItem(id){
	//弹框显示
	artDialog.open('${ctx}/system/product/get/'+id, 
				{
					title:"详情",
					width:650,
					height:340,
					lock:true,
        			style:'succeed noClose'
        		}
	);
}

//删除
function doDel(id){
	artDialog.confirm('确定要删除吗？', function(){
		$.get("${ctx}/system/product/del/"+id,function(data){
			if(data == 1){
				artDialog.tips('删除成功', 1.5);
				reload();
			}else if(data == 3){
				artDialog.tips('此服务在套餐中使用,不可删除', 1.5);
			}else{
				artDialog.tips('删除失败', 1.5);
			}
		},"json");
	});
}

//启用
function doEnable(id,op){
	var tip = "下架";
	if(op == 1){
		tip = "上架";
	}
	artDialog.confirm('确定要'+tip+'吗？', function(){
		$.get("${ctx}/system/product/enable/"+id+"/"+op,function(data){
			if(data == 1){
				artDialog.tips(tip+'成功', 1.5);
				reload();
			}else{
				artDialog.tips(tip+'失败', 1.5);
			}
		},"json");
	});
}

//修改
function toUpdate(id){
	location.href = "${ctx}/system/product/edit/"+id;
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1500);
}


</script>
<title>商品列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />商品管理&gt;<font color="#FF9900">商品列表</font>
	</h2>
	<fieldset>
		<legend><strong>商品列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">分类：
						<select id="category" name="category">
							<option value="">全部</option>
							<option value="1">洗车</option>
							<option value="2">维修</option>
							<option value="3">保养</option>
							<option value="4">家政保洁</option>
						</select>
					</td>
						
					<td align="left" width="25%">类型：
						<select id="type" name="type">
							<option value="">全部</option>
							<option value="1">卡</option>
							<option value="2">券</option>
						</select>
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
					<th width="7%">商品编号</th>
			        <th width="10%">商品名称</th>
			        <th width="6%">使用方式</th>
			        <th width="6%">商品类型</th>
			        <th width="5%">原价</th>
			        <th width="6%">商品分类</th>
			        <th width="5%">折扣</th>
			        <th width="1%">折扣价</th>
			        <th width="4%">次数</th>
			        <th width="5%">有效期</th>
			        <th width="8%">适用区域</th>
			        <th width="7%">状态</th>
			        <th width="7%">是否上架</th>
			        <th width="8%">创建时间</th>
			        <th width="8%">修改时间</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='14' style="color:red; text-align: center;">很抱歉，您所查找的商品不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="productVo">
		        	<tr>
		        		<td>${productVo.product.productCode}&nbsp;</td>
		        		<td>${productVo.product.name}&nbsp;</td>
		        		<td><c:if test="${productVo.product.isDoor eq null || productVo.product.isDoor eq 0}">到店</c:if><c:if test="${productVo.product.isDoor eq 1}">上门</c:if>&nbsp;</td>
		        		<td>${productVo.product.productType eq 1 ? "卡" : "券"}</td>
		        		<td><fmt:formatNumber value="${productVo.product.price/100}" type="currency" pattern="￥.00"/>&nbsp;</td>
		        		<td><c:if test="${productVo.product.productCategory eq 1}">洗车</c:if><c:if test="${productVo.product.productCategory eq 2}">维修</c:if><c:if test="${productVo.product.productCategory eq 3}">保养</c:if><c:if test="${productVo.product.productCategory eq 4}">家政保洁</c:if></td>
		        		<td>${productVo.product.discount}&nbsp;</td>
		        		<td><fmt:formatNumber value="${productVo.product.discountPrice/100}" type="currency" pattern="￥0.00"/>&nbsp;</td>
		        		<td>${productVo.product.number}&nbsp;</td>
		        		<td><c:if test="${productVo.product.monthNumber eq 12}">一年</c:if><c:if test="${productVo.product.monthNumber ne 12}">${productVo.product.monthNumber}个月</c:if>&nbsp;</td>
		        		<td>${productVo.regionName}&nbsp;</td>
		        		<td><c:if test="${productVo.product.status eq 1}">正常</c:if></td>
		        		<td>${productVo.product.isShow eq 1 ? "是" : "否"}</td>
		        		<td>${productVo.createDate}&nbsp;</td>
		        		<td>${productVo.modifyDate}&nbsp;</td>
		        		<td align="left">
							<c:if test="${productVo.product.isShow eq 2}">
								<a href="javascript:void(0);" onclick="doEnable(${productVo.product.id},1)">上架</a>
								<a href="javascript:void(0);"  onclick="toUpdate(${productVo.product.id})">修改</a>
								<a href="javascript:void(0);"  onclick="doDel(${productVo.product.id})">删除</a>
							</c:if>
							<c:if test="${productVo.product.isShow eq 1}">
								<a href="javascript:void(0);" onclick="doEnable(${productVo.product.id},2)">下架</a>
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