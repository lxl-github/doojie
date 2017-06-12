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
	
	$("#status").val(${status});
});

//搜索
function doSearch(){
	$("#form").attr("action","${ctx}/system/brand/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

function toAdd(){
	location.href = '${ctx}/system/brand/add';
}

//查询详情
function searchItem(id){
	//弹框显示
	artDialog.open('${ctx}/system/brand/get/'+id, 
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
		$.get("${ctx}/system/brand/del/"+id,function(data){
			if(data == 1){
				artDialog.tips('删除成功', 1.5);
				reload();
			}else if(data == 3){
				artDialog.tips('此品牌下有型号,不可删除', 2);
			}else{
				artDialog.tips('删除失败', 1.5);
			}
		},"json");
	});
}


//修改
function toUpdate(id){
	location.href = "${ctx}/system/brand/edit/"+id;
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1500);
}

</script>
<title>品牌列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />品牌管理&gt;<font color="#FF9900">品牌列表</font>
	</h2>
	<fieldset>
		<legend><strong>品牌列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="50%">&nbsp;
						<input name="searchButton" type="button" value="查询" onclick="doSearch()" />
						<input name="searchButton" type="button" value="添加" onclick="toAdd()" />
					</td>
				</tr>
			</table>
		</div>
			
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="17%">品牌名称</th>
			        <th width="7%">品牌logo</th>
			        <th width="7%">品牌检索字母</th>
			        <th width="10%">创建时间</th>
			        <th width="10%">修改时间</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='6' style="color:red; text-align: center;">很抱歉，您所查找的品牌不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="brand">
		        	<tr>
		        		<td>${brand.brandName}&nbsp;</td>
		        		<td><img height="50" src="${ctx}/static/brandLogo/${brand.brandLogo}"/>&nbsp;</td>
		        		<td>${brand.brandSearch}&nbsp;</td>
		        		<td><script type="text/javascript">
		        			document.write(getLocalTime(${brand.createTime}));
		        		</script>
		        		&nbsp;</td>
		        		<td><script type="text/javascript">
		        			document.write(getLocalTime(${brand.modifyTime}));
		        		</script>&nbsp;</td>
		        		<td align="left">
	        				<a href="javascript:void(0);"  onclick="toUpdate(${brand.id})">修改</a>
							<a href="javascript:void(0);"  onclick="doDel(${brand.id})">删除</a>
							<a  href="${ctx}/system/models/list?brandId=${brand.id}">型号列表</a>
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