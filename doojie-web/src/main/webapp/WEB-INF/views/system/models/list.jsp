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
	
});

//搜索
function doSearch(){
	$("#form").attr("action","${ctx}/system/models/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

function toAdd(){
	location.href = '${ctx}/system/models/add?brandId='+${brandId};
}

//删除
function doDel(id){
	artDialog.confirm('确定要删除吗？', function(){
		$.get("${ctx}/system/models/del/"+id,function(data){
			if(data == 1){
				artDialog.tips('删除成功', 1.5);
				reload();
			}else{
				artDialog.tips('删除失败', 1.5);
			}
		},"json");
	});
}


//修改
function toUpdate(id){
	location.href = "${ctx}/system/models/edit/"+id;
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1500);
}

function doBack(){
	location.href = "${ctx}/system/brand/list";
}

</script>
<title>型号列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />品牌管理&gt;<font color="#FF9900">型号列表</font>
	</h2>
	<fieldset>
		<legend><strong>型号列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="50%">&nbsp;
						<input type="hidden" id="brandId" name="brandId" value="${brandId}"/>
						<input name="searchButton" type="button" value="查询" onclick="doSearch()" />
						<input name="searchButton" type="button" value="添加" onclick="toAdd()" />
						<input name="clearButton" type="button" value="返回品牌列表" onclick="doBack()" />
					</td>
				</tr>
			</table>
		</div>
			
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="17%">型号名称</th>
			        <th width="10%">创建时间</th>
			        <th width="10%">修改时间</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='4' style="color:red; text-align: center;">很抱歉，您所查找的型号不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="model">
		        	<tr>
		        		<td>${model.modelName}&nbsp;</td>
		        		<td><script>
		        			document.write(getLocalTime(${model.createTime}));
		        		</script>
		        		&nbsp;</td>
		        		<td><script>
		        			document.write(getLocalTime(${model.modifyTime}));
		        		</script>&nbsp;</td>
		        		<td align="left">
	        				<a href="javascript:void(0);"  onclick="toUpdate(${model.id})">修改</a>
							<a href="javascript:void(0);"  onclick="doDel(${model.id})">删除</a>
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