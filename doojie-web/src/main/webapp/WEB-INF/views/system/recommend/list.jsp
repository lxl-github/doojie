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
	
	$("#isTop").val(${isTop});
});

//搜索
function doSearch(){
	$("#form").attr("action","${ctx}/system/recommend/list");
	$.blockUI({ message: $('#showLoading')});
	$("#form").submit();
}


//置顶
function doIsTop(id,op){
	var tip = "开启置顶";
	if(op == 0){
		tip = "取消置顶";
	}
	artDialog.confirm('确定要'+tip+'吗？', function(){
		$.get("${ctx}/system/recommend/isTop/"+id+"/"+op,function(data){
			if(data){
				artDialog.tips(tip+'成功', 1.5);
				reload();
			}else{
				artDialog.tips(tip+'失败', 1.5);
			}
		},"json");
	});
}

//重新载入页面
function reload(){
	setTimeout(function () { 
		location.reload();
    }, 1500);
}


</script>
<title>评论列表</title>
</head>
<body>
	<div class="rightbox boxborder" >
	<h2>
		<img src="${ctx}/static/images/conner.jpg" width="17" height="23" hspace="5" align="absmiddle" />后台管理&gt;<font color="#FF9900">推荐商家理由列表</font>
	</h2>
	<fieldset>
		<legend><strong>评论列表</strong></legend>
	<form id="form" action="" method="get">
		<div class="huibox">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="tab_02">
				<tr>
					<td align="left" width="25%">评论商家：
						<input type="text" id="name" name="name" value="${name}"/>
					</td>
						
					<td align="left" width="25%">是否置顶：
						<select id="isTop" name="isTop">
							<option value="">全部</option>
							<option value="1">已置顶</option>
							<option value="0">未置顶</option>
						</select>
					</td>
					<td align="left" width="50%">&nbsp;
						<input name="searchButton" type="button" value="查询" onclick="doSearch()" />
					</td>
				</tr>
				
			</table>
		</div>
			
			<table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_hui">
				<tr>
					<th width="7%">评论用户</th>
			        <th width="12%">评论商家</th>
			        <th width="29%">评论内容</th>
			        <th width="5%">服务指数</th>
			        <th width="5%">是否置顶</th>
			        <th width="9%">创建时间</th>
			        <th width="9%">修改时间</th>
			        <th width="7%">操作</th>
		        </tr>
		        <c:if test="${page.totalRecord == 0}">
				  	<tr>
				  		<td colspan='8' style="color:red; text-align: center;">很抱歉，您所查找的评论不存在</td>
				  	</tr>
				</c:if>
				<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${page.results}" var="recommendVo">
		        	<tr>
		        		<td>${recommendVo.mobile}&nbsp;</td>
		        		<td>${recommendVo.bName}&nbsp;</td>
		        		<td>${recommendVo.reasons}&nbsp;</td>
		        		<td><c:if test="${recommendVo.recommendIndex eq 1}">一星</c:if><c:if test="${recommendVo.recommendIndex eq 2}">二星</c:if><c:if test="${recommendVo.recommendIndex eq 3}">三星</c:if><c:if test="${recommendVo.recommendIndex eq 4}">四星</c:if><c:if test="${recommendVo.recommendIndex eq 5}">五星</c:if>&nbsp;</td>
		        		<td>${recommendVo.isTop eq 1 ? "已置顶" :"未置顶"}&nbsp;</td>
		        		<td>${recommendVo.createDate}&nbsp;</td>
		        		<td>${recommendVo.modifyDate}&nbsp;</td>
		        		
		        		<td align="left">
							<c:if test="${recommendVo.isTop eq 0}">
								<a href="javascript:void(0);" onclick="doIsTop(${recommendVo.id},1)">开启置顶</a>
							</c:if>
							<c:if test="${recommendVo.isTop eq 1}">
								<a href="javascript:void(0);" onclick="doIsTop(${recommendVo.id},0)">取消置顶</a>
							</c:if>
							&nbsp;
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