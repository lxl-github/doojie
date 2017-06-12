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
	$("#form").attr("action","${ctx}/system/region/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

function toAdd(){
	location.href = '${ctx}/system/region/add?parentId='+${parentId};
}


//删除
function doDel(id){
	artDialog.confirm('确定要删除吗？并且会删除相应的下级区域', function(){
		$.get("${ctx}/system/region/del/"+id,function(data){
			if(data == 1){
				artDialog.tips('删除成功', 1.5);
				reload();
			}else if(data == 3){
				artDialog.tips('此区域存在开通的下级区域,需禁用后在操作', 2);
			}else{
				artDialog.tips('删除失败', 1.5);
			}
		},"json");
	});
}

//启用
function doEnable(id,op){
	var tip = "禁用";
	if(op == 1){
		tip = "开通";
	}
	artDialog.confirm('确定要'+tip+'吗？', function(){
		$.get("${ctx}/system/region/enable/"+id+"/"+op,function(data){
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
	location.href = "${ctx}/system/region/edit/"+id;
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1500);
}

function doBack(){
	history.back();
}

</script>
<title>区域列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />区域管理&gt;<font color="#FF9900">区域列表</font>
	</h2>
	<fieldset>
		<legend><strong>区域列表</strong></legend>
	<form id="form" action="" method="get">
		<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">区域名称：
						<input type="text" id="name" name="name" value="${name}"/>
					</td>
						
					<td align="left" width="25%">是否开通：
						<select id="status" name="status">
							<option value="">全部</option>
							<option value="1">已开通</option>
							<option value="0">未开通</option>
						</select>
					</td>
					<td align="left" width="50%">&nbsp;
						<input name="searchButton" type="button" value="查询" onclick="doSearch()" />
						<input name="searchButton" type="button" value="添加" onclick="toAdd()" />
						<c:if test="${parentId gt 0}">
						<input name="clearButton" type="button" value="返回上级" onclick="doBack()" />
						</c:if>
					</td>
				</tr>
				
			</table>
		</div>
			
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="17%">区域名称</th>
			        <th width="7%">区域代码</th>
			        <th width="7%">是否可添加下级</th>
			        <th width="10%">是否开通</th>
			        <th width="9%">等级</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='6' style="color:red; text-align: center;">很抱歉，您所查找的区域不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="region">
		        	<tr>
		        		<td>${region.name}&nbsp;</td>
		        		<td>${region.code}&nbsp;</td>
		        		<td><c:if test="${region.level ne 3 }"><a href="${ctx}/system/region/list?parentId=${region.id}" >下级</a></c:if>&nbsp;</td>
		        		<td>${region.isEnabled eq 1 ? "已开通" :"未开通"}</td>
		        		<td>${region.level}级&nbsp;</td>
		        		<td align="left">
							<c:if test="${region.isEnabled eq 0}">
								<a href="javascript:void(0);" onclick="doEnable(${region.id},1)">开通</a>
								<a href="javascript:void(0);"  onclick="toUpdate(${region.id})">修改</a>
								<a href="javascript:void(0);"  onclick="doDel(${region.id})">删除</a>
							</c:if>
							<c:if test="${region.isEnabled eq 1}">
								<a href="javascript:void(0);" onclick="doEnable(${region.id},0)">禁用</a>
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