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
	$("#form").attr("action","${ctx}/system/bussiness/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}

function toAdd(){
	location.href = '${ctx}/system/bussiness/add';
}

//查询详情
function searchItem(op){
	//弹框显示
	artDialog.open('${ctx}/system/bussiness/get/'+op, 
				{
					title:"详情",
					width:650,
					height:340,
					lock:true,
        			style:'succeed noClose'
        		}
	);
}

//重置密码
function resetPass(id){
	artDialog.confirm('确定要密码重置吗？', function(){
		$.get("${ctx}/system/bussiness/restPass/"+id,function(data){
			if(data){
				artDialog.tips('重置成功', 1.5);
			}else{
				artDialog.tips('重置失败', 1.5);
			}
		},"json");
	});
}


//审核
function doAudit(id){
	artDialog.confirm('确定要审核通过吗？', function(){
		$.get("${ctx}/system/bussiness/audit/"+id,function(data){
			if(data){
				artDialog.tips('审核成功', 1);
				reload();
			}else{
				artDialog.tips('审核失败', 1);
			}
		},"json");
	});
}

//修改
function toUpdate(id){
	location.href = "${ctx}/system/bussiness/edit/"+id;
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		doSearch();
    }, 1000);
}


</script>
<title>商家列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />后台管理&gt;<font color="#FF9900">商家列表</font>
	</h2>
	<fieldset>
		<legend><strong>商家列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">商家名称：
						<input type="text" id="name" name="name" value="${name}"/>
					</td>
						
					<td align="left" width="25%">商家账户：
						<input type="text" id="username" name="username" value="${username}"/>
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
					<th width="7%">商家账户</th>
			        <th width="12%">商家名称</th>
			        <th width="9%">联系电话</th>
			        <th width="15%">地址</th>
			        <th width="9%">创建时间</th>
			        <th width="9%">修改时间</th>
			        <th width="9%">状态</th>
			        <th width="9%">是否认证</th>
			        <th width="15%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='8' style="color:red; text-align: center;">很抱歉，您所查找的商家不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${list}" var="bussinessVo">
		        	<tr>
		        		<td>${bussinessVo.bussiness.username}&nbsp;</td>
		        		<td>${bussinessVo.bussiness.name}&nbsp;</td>
		        		<td>${bussinessVo.bussiness.tel}&nbsp;</td>
		        		<td>${bussinessVo.bussiness.address}&nbsp;</td>
		        		<td>${bussinessVo.createDate}&nbsp;</td>
		        		<td>${bussinessVo.modifyDate}&nbsp;</td>
		        		<td>${bussinessVo.bussiness.status eq 1 ? "已审核" :"待审核"}</td>
		        		<td style="text-align: center;"><c:if test="${bussinessVo.bussiness.isAuthor eq null || bussinessVo.bussiness.isAuthor == 0 }"><img title="未认证" src="${ctx}/static/images/author2.png"/></c:if><c:if test="${bussinessVo.bussiness.isAuthor == 1}"><img title="已认证" src="${ctx}/static/images/author1.png"/></c:if></td>
		        		<td align="left">
	        				<a href="javascript:void(0);"  onclick="toUpdate(${bussinessVo.bussiness.id})">修改</a>
							<c:if test="${bussinessVo.bussiness.status eq 0}">
								<a href="javascript:void(0);" onclick="doAudit(${bussinessVo.bussiness.id})">审核</a>
							</c:if>
							<a href="javascript:void(0);"  onclick="searchItem(${bussinessVo.bussiness.id})">查看详情</a>
							<a href="javascript:void(0);"  onclick="resetPass(${bussinessVo.bussiness.id})">密码重置</a>
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