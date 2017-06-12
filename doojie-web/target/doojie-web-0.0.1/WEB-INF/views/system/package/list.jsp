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
	$("#form").attr("action","${ctx}/bussiness/package/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

function toAdd(){
	location.href = '${ctx}/bussiness/package/add';
}

//查询详情
function searchItem(id){
	//弹框显示
	artDialog.open('${ctx}/bussiness/package/get/'+id, 
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
		$.get("${ctx}/bussiness/package/del/"+id,function(data){
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
	var tip = "停用";
	if(op == 1){
		tip = "启用";
	}
	artDialog.confirm('确定要'+tip+'吗？', function(){
		$.get("${ctx}/bussiness/package/enable/"+id+"/"+op,function(data){
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
	location.href = "${ctx}/bussiness/package/edit/"+id;
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1500);
}


</script>
<title>套餐列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />服务管理&gt;<font color="#FF9900">套餐列表</font>
	</h2>
	<fieldset>
		<legend><strong>套餐列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">服务名称：
						<input type="text" id="name" name="name" value="${name}"/>
					</td>
						
					<td align="left" width="25%">状态：
						<select id="status" name="status">
							<option value="">全部</option>
							<option value="1">启用</option>
							<option value="0">停用</option>
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
					<th width="17%">套餐名称</th>
			        <th width="7%">套餐价格</th>
			        <th width="7%">会员价</th>
			        <th width="10%">创建时间</th>
			        <th width="10%">修改时间</th>
			        <th width="9%">状态</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='7' style="color:red; text-align: center;">很抱歉，您所查找的套餐不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="packageVo">
		        	<tr>
		        		<td>${packageVo.packageServer.name}&nbsp;</td>
		        		<td><fmt:formatNumber value="${packageVo.packageServer.price/100}" type="currency" pattern="￥.00"/>&nbsp;</td>
		        		<td><fmt:formatNumber value="${packageVo.packageServer.vipPrice/100}" type="currency" pattern="￥.00"/>&nbsp;</td>
		        		<td>${packageVo.createDate}&nbsp;</td>
		        		<td>${packageVo.modifyDate}&nbsp;</td>
		        		<td>${packageVo.packageServer.status eq 1 ? "已启用" :"未启用"}</td>
		        		<td align="left">
	        				<a href="javascript:void(0);"  onclick="toUpdate(${packageVo.packageServer.id})">修改</a>
							<c:if test="${packageVo.packageServer.status eq 0}">
								<a href="javascript:void(0);" onclick="doEnable(${packageVo.packageServer.id},1)">启用</a>
							</c:if>
							<c:if test="${packageVo.packageServer.status eq 1}">
								<a href="javascript:void(0);" onclick="doEnable(${packageVo.packageServer.id},0)">停用</a>
							</c:if>
							<a href="javascript:void(0);"  onclick="doDel(${packageVo.packageServer.id})">删除</a>
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