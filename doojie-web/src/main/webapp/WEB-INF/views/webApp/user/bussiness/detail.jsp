<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 车行详情</title>
<script type="text/javascript">

$().ready(function(){
	//菜单加颜色提示
	$("#index").attr("style","color:#2885EC");
	
	$(".icon-back").on("click",function(){
		history.back();
	});
});

function yuyue(id,name,type){
	var url = "${ctx}/webApp/toStoreAppointment/"+id+"/"+name+"/"+type;
	
	$.get("${ctx}/webApp/user/isEnableYuYue",function(data){
		if(data == true){
			window.location.href= url;
		}else{
			//将页面记录到cookie，用于登录后跳转
			$.cookie("target_herf",url, { path: '${ctx}' });
			//跳转登录页
			window.location.href="${ctx}/webApp/login";
		}
	});
}


function navigation(lng,lat,name){
	window.location.href = "${ctx}/webApp/navigation/"+lng+"/"+lat+"/"+name;
}

function toRecommendList(id){
	window.location.href = "${ctx}/webApp/toRecommendList/"+id;
}

</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">车行详情&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div class="list">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title" style="font-weight:bold;"> ${bussinessVo.bussiness.name }</h3>
			</div>
			<div class="panel-body">地址:${bussinessVo.bussiness.city}${bussinessVo.bussiness.address}</div>
			<table class="table">
				<tr>
					<c:if test="${bussinessVo.bussiness.isAuthor eq null || bussinessVo.bussiness.isAuthor == 0}">
						<td><span style="color: red;"><span class="glyphicon glyphicon-remove-sign"></span></span>&nbsp;未认证</td>
					</c:if>
					<c:if test="${bussinessVo.bussiness.isAuthor == 1}">
						<td><span style="color: green;"><span class="glyphicon glyphicon-ok-sign"></span></span>&nbsp;已认证</td>
					</c:if>
					<td><a href="javascript:;" onclick="navigation(${bussinessVo.bussiness.lng},${bussinessVo.bussiness.lat},'${bussinessVo.bussiness.name}')"><span class="glyphicon glyphicon-send"></span>&nbsp;导航路线</a></td>
					<td><a href="tel:${bussinessVo.bussiness.tel}"><span class="glyphicon glyphicon-earphone"></span>&nbsp;联系电话</a></td>
				</tr>
			</table>
		</div>
		
<!-- 		<div class="panel panel-default"> -->
<!-- 			<div class="panel-heading"> -->
<!-- 				<h3 class="panel-title">洗车服务</h3> -->
<!-- 			</div> -->
<!-- 			<div class="panel-body"> -->
<!-- 				<table class="table"> -->
<!-- 					<tr style="background-color: #eee;"> -->
<!-- 						<th>车型</th> -->
<!-- 						<th>市场价</th> -->
<!-- 						<th>会员价</th> -->
<!-- 					</tr> -->
<%-- 					<c:if test="${bussinessVo.baseServerList ne null}"> --%>
<%-- 					<c:forEach items="${bussinessVo.baseServerList}" var="baseServer"> --%>
<!-- 						<tr> -->
<%-- 							<td><c:if test="${baseServer.carType eq 1 }">五座</c:if><c:if test="${baseServer.carType eq 2 }">七座</c:if><c:if test="${baseServer.carType eq 3 }">七座以上</c:if></td> --%>
<%-- 							<td style="text-decoration: line-through;color: #ccc;">${baseServer.price/100}元</td> --%>
<%-- 							<td>${baseServer.vipPrice/100}元</td> --%>
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${bussinessVo.baseServerList eq null}"> --%>
<!-- 					<tr> -->
<!-- 						<td colspan="3" class="gongli"><span class="glyphicon glyphicon-eye-close"></span>&nbsp;这家伙太懒，连服务都没设置</td> -->
<!-- 					</tr> -->
<%-- 					</c:if> --%>
<!-- 				</table> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">评价列表 (${page.totalRecord}条)<span class="pull-right "><a href="javascript:toRecommendList(${bussinessVo.bussiness.id});">更多&nbsp;<span class="glyphicon glyphicon-arrow-right"></span></a></h3>
			</div>
			<table class="table">
			<c:if test="${page.totalRecord == 0}">
			  	<tr>
			  		<td colspan='2' style="color:red; text-align: center;">还没有评价，沙发等你来抢</td>
			  	</tr>
			</c:if>
			<c:if test="${page.totalRecord != 0}">
		        <c:forEach items="${page.results}" var="recommendVo">
			  	<tr><td colspan="2">
			  	${recommendVo.mobile}
			  	</td></tr>
				<tr>
					<td colspan="2">${recommendVo.reasons}</td>
				</tr>
				<tr>
					<td class = "gongli">
						<c:if test="${recommendVo.recommendIndex == 1}">
						<span class="glyphicon glyphicon-star"></span>&nbsp;
						</c:if>
						<c:if test="${recommendVo.recommendIndex == 2}">
						<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;
						</c:if>
						<c:if test="${recommendVo.recommendIndex == 3}">
						<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;
						</c:if>
						<c:if test="${recommendVo.recommendIndex == 4}">
						<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;
						</c:if>
						<c:if test="${recommendVo.recommendIndex == 5}">
						<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;<span class="glyphicon glyphicon-star"></span>&nbsp;
						</c:if>
					</td>
					<td ><span class="pull-right ">${recommendVo.createDate}</span></td>
				</tr>
			</c:forEach>
			</c:if>
			</table>
		</div>
		<c:if test="${bussinessVo.bussiness.isAuthor ne null && bussinessVo.bussiness.isAuthor != 0}">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">基本服务</h3>
			</div>
				<table class="table">
					<tr style="align-content: center;">
						<td>
						全天(8:00~20:00)
						</td>
						<td>
						爱车清洗
						</td>
						<td>
							<span class="pull-right "><a href="javascript:;"><button <c:if test="${bussinessVo.bussiness.isAuthor eq null || bussinessVo.bussiness.isAuthor == 0}"> disabled="disabled" </c:if> onclick="yuyue(${bussinessVo.bussiness.id},'${bussinessVo.bussiness.name}',1)" class="btn btn-primary">预约</button></a></span>&nbsp;
						</td>
					</tr>
				</table>
		</div>
		</c:if>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
