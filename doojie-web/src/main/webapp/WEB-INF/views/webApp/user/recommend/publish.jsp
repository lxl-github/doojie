<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 评价</title>
<style type="text/css">
.rating {
  *zoom: 1;
}
.rating a {
  float: left;
  display: block;
  width: 26px;
  height: 26px;
  position: relative;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABsAAABOCAYAAADRl20RAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OUMwOUI4NjQxQ0Y1MTFFMjlGOThBQTVDRENEQURDNUQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OUMwOUI4NjUxQ0Y1MTFFMjlGOThBQTVDRENEQURDNUQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo5QzAwNDIyNjFDRjUxMUUyOUY5OEFBNUNEQ0RBREM1RCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5QzAwNDIyNzFDRjUxMUUyOUY5OEFBNUNEQ0RBREM1RCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PmSPr8EAAASsSURBVHjavFlpiFNXFE6eMWaqcaGjMSOtrfpLrYpLcSqKqKh16kKeMg6jiFNLR4SCIEp/tBSFUmhBwQX/uNA/LpWWYq1iW1CxWLQVFRxkHNzTwaWdcamijsbvpCdwfd68d89N3hz4SPK27517z/Ldm2hVVVVEaFHgLeCy9EYnIrdK4G2L+6zIaCi6A73DJqPr0/x9QNhkfYGuiofRMMlUbxLA62GRdQFSmvkLhawfENOQOWGQ6QKiK8+jkcUEF6eKHB8IPDe4/xmRJYGh0sjyvEQq4JqHwCkiuwTcBcYA3SLlt1vAaeBpYc7+AY4BbWUmugicJKJ8OCeTycKJDuAGe9e7RJKn7M0Vb4CoRhN9DmgH3rGsnfdpfoD/dNGos2vAPWAsUCEg+hs4y6MkyjPyrklA1MFD12Gb1CkBWSyoVjoB51LC+UrbklUq7cTU+vsVB8fiLSlJzwCPNeeo7fTxG+dioqa/JneaOFLJbgLDNQWaXvJfiWc00XHl923gqEJE9oSj70+Pl2mpZ2klnM97SLzWyuWu4GUFV6B2E7Iok5E3Z1sbVz4yCIy8l+mt6ympR3BTfYUsqhGpPWmSQXI1IIq1PQyENPyDgAsmc0Zl6mqAFllRdEwbVz4BLmjnLJvN6l993TfFnjcF+ATY6DeuuufaVPWFwBDg3bBFKs1Hhr/Xhk02Q2msC6X3OxZDqGrGSWGRvQbM8RyrC4usBujhOeZKOoNjOYRqDZ1uvGSNrv16mmEX/oFbiNcOABsMnvGQHjIS+MqnKJsMb03ANSSEM9FcLkfVYjJ+7JUsEgR2EKh//tmqtvyc4csRfIxm9VouywHrgA+IKD9n5JlSD7txzfuoRCJaOywGyf6XAkQlU0g/xMdmy4UGNdsMiJqNQh8XbsPHROC6kIjmfTzQLM0z0uurBUTUB+uBB7ZJXSMg6xlUK/3IaL5mC4cxY0s2FehlQebYkLlFjh8CGlgZ6yRgtYgMoU+la64mdygl3gd2AMOAPYKXLOrZZM/y5xCvRLcrx+5wJ3BZiqtkUQmZq4TzMmCWT859z2p4N/9+k1eswfIbQ0gvMA84TESDqqtNEpu8rLt04sR3+NwCzOc8DfSM5PPnwEwfIu2I4PqCl4+1zVNXG8mG/Pp7MS9IBnwMbPJztWXahLKIVJIBy21agQ1ZLe91jQqbLMHB0ymKuIZ38QqaMdQ9YlXODfQrTaWSJTm5O0URz2EJrtoCXhx2iiJO8eLQTBEP/uW4iSKOsyKOa879GJTgbA9irBe/lAyHx+Zq2pHXaEfVzZcrlKapXLUrQ1DEP5GGRPlqd7iO/Rb5f0P6rzKS0NbFFxRYRPRKIYaHCRanDSUS0cPrQfJzYNUHaSMvg2wUMe0xuyBqMQp9XLiVpUFWSLQLeI8eIc2zP4BPBUS0UlkU0ex6h6GIaVNzgm0FSQjJSlLEMzS7A0HmRiz3iF2fJCV5d1tz7g1gnIgMoR/XLCooAJbw8W2sovZpbp8v9WxK5OU/fw6wIv5WOXaLW0ytx8uMlGyBUgmW0iLcJ+f2erwcXEwMOZohjHGjPMgP2WkQGF4vXVPP6K3WsASQVpCCl9r94xcCDACHgA63y6lwZwAAAABJRU5ErkJggg%3D%3D) top left;
}
.rating a.rated {
  background-position: 0 -26px;
}
.rating a.hover {
  background-position: 0 -52px;
}
.rating.small a {
  float: left;
  display: block;
  width: 12px;
  height: 12px;
  position: relative;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABsAAABOCAYAAADRl20RAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OUMwOUI4NjQxQ0Y1MTFFMjlGOThBQTVDRENEQURDNUQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OUMwOUI4NjUxQ0Y1MTFFMjlGOThBQTVDRENEQURDNUQiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo5QzAwNDIyNjFDRjUxMUUyOUY5OEFBNUNEQ0RBREM1RCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5QzAwNDIyNzFDRjUxMUUyOUY5OEFBNUNEQ0RBREM1RCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PmSPr8EAAASsSURBVHjavFlpiFNXFE6eMWaqcaGjMSOtrfpLrYpLcSqKqKh16kKeMg6jiFNLR4SCIEp/tBSFUmhBwQX/uNA/LpWWYq1iW1CxWLQVFRxkHNzTwaWdcamijsbvpCdwfd68d89N3hz4SPK27517z/Ldm2hVVVVEaFHgLeCy9EYnIrdK4G2L+6zIaCi6A73DJqPr0/x9QNhkfYGuiofRMMlUbxLA62GRdQFSmvkLhawfENOQOWGQ6QKiK8+jkcUEF6eKHB8IPDe4/xmRJYGh0sjyvEQq4JqHwCkiuwTcBcYA3SLlt1vAaeBpYc7+AY4BbWUmugicJKJ8OCeTycKJDuAGe9e7RJKn7M0Vb4CoRhN9DmgH3rGsnfdpfoD/dNGos2vAPWAsUCEg+hs4y6MkyjPyrklA1MFD12Gb1CkBWSyoVjoB51LC+UrbklUq7cTU+vsVB8fiLSlJzwCPNeeo7fTxG+dioqa/JneaOFLJbgLDNQWaXvJfiWc00XHl923gqEJE9oSj70+Pl2mpZ2klnM97SLzWyuWu4GUFV6B2E7Iok5E3Z1sbVz4yCIy8l+mt6ympR3BTfYUsqhGpPWmSQXI1IIq1PQyENPyDgAsmc0Zl6mqAFllRdEwbVz4BLmjnLJvN6l993TfFnjcF+ATY6DeuuufaVPWFwBDg3bBFKs1Hhr/Xhk02Q2msC6X3OxZDqGrGSWGRvQbM8RyrC4usBujhOeZKOoNjOYRqDZ1uvGSNrv16mmEX/oFbiNcOABsMnvGQHjIS+MqnKJsMb03ANSSEM9FcLkfVYjJ+7JUsEgR2EKh//tmqtvyc4csRfIxm9VouywHrgA+IKD9n5JlSD7txzfuoRCJaOywGyf6XAkQlU0g/xMdmy4UGNdsMiJqNQh8XbsPHROC6kIjmfTzQLM0z0uurBUTUB+uBB7ZJXSMg6xlUK/3IaL5mC4cxY0s2FehlQebYkLlFjh8CGlgZ6yRgtYgMoU+la64mdygl3gd2AMOAPYKXLOrZZM/y5xCvRLcrx+5wJ3BZiqtkUQmZq4TzMmCWT859z2p4N/9+k1eswfIbQ0gvMA84TESDqqtNEpu8rLt04sR3+NwCzOc8DfSM5PPnwEwfIu2I4PqCl4+1zVNXG8mG/Pp7MS9IBnwMbPJztWXahLKIVJIBy21agQ1ZLe91jQqbLMHB0ymKuIZ38QqaMdQ9YlXODfQrTaWSJTm5O0URz2EJrtoCXhx2iiJO8eLQTBEP/uW4iSKOsyKOa879GJTgbA9irBe/lAyHx+Zq2pHXaEfVzZcrlKapXLUrQ1DEP5GGRPlqd7iO/Rb5f0P6rzKS0NbFFxRYRPRKIYaHCRanDSUS0cPrQfJzYNUHaSMvg2wUMe0xuyBqMQp9XLiVpUFWSLQLeI8eIc2zP4BPBUS0UlkU0ex6h6GIaVNzgm0FSQjJSlLEMzS7A0HmRiz3iF2fJCV5d1tz7g1gnIgMoR/XLCooAJbw8W2sovZpbp8v9WxK5OU/fw6wIv5WOXaLW0ytx8uMlGyBUgmW0iLcJ+f2erwcXEwMOZohjHGjPMgP2WkQGF4vXVPP6K3WsASQVpCCl9r94xcCDACHgA63y6lwZwAAAABJRU5ErkJggg%3D%3D) top left;
}
.rating.small a.rated {
  background-position: 0 -13px;
}
.rating.small a.hover {
  background-position: 0 -26px;
}
.rating:before,
.rating:after {
  display: table;
  content: "";
}
.rating:after {
  clear: both;
}
</style>
<script type="text/javascript">
//序列化form表单位json
(function($){
	$.fn.serializeJson=function(){
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
				serializeObj[this.name].push(this.value);
				}else{
				serializeObj[this.name]=[serializeObj[this.name],this.value];
				}
			}else{
				serializeObj[this.name]=this.value;
			}
		});
		return serializeObj;
	};
})(jQuery);

$().ready(function(){
	//菜单加颜色提示
	$("#index").attr("style","color:#2885EC");
	
	$(".icon-back").on("click",function(){
		history.back();
	});
	
	if("${recommend.recommendIndex}" != null || "${recommend.recommendIndex}" != ""){
		$('#rating').RatingValue("${recommend.recommendIndex}");
		$('#reasons').val("${recommend.reasons}");
	}
	
// 	$("#myRecommend").on("click",function(){
// 		$.get("${ctx}/webApp/user/isEnableRecommend",function(data){
// 			if(data == true){
// 				$('#rating').RatingValue(5.0);
// 				$('#reasons').val('');
// 			}else{
// 				//将页面记录到cookie，用于登录后跳转
// 				$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
// 				//跳转登录页
// 				window.location.href="${ctx}/webApp/login";
// 			}
// 		});
// 	});
	
	
	$("#submit").on("click",function(){
		var rated = $('.rated').length;
		if(rated == 0){
			Alert("请选择服务指数");
			return;
		}
		
		var reasons = $("#reasons").val().trim();
		if(reasons == ""){
			Alert("请输入评价内容");
			return;
		}
		
		$("input[name='recommendIndex']").val(rated);
		
		var reasons = $("#reasons").val().trim();
		if(reasons == ""){
			Alert("请输入评价内容");
			return;
		}
		var formData = $("#recommendForm").serializeJson();
		var jsons = JSON.stringify(formData);
		$.post("${ctx}/webApp/user/myRecommend",{json:jsons},function(data){
			if(data == "-100"){
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/login";
			}else{
				if(data){
					Alert("评价成功");
					reload();
				}else{
					Alert("评价失败");
				}
			}
		});
	});
});

//重新载入页面
function reload(){
	setTimeout(function () { 
		location.reload();
    }, 500);
}

</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">评价&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div class="list">
		<div class="panel panel-default">
			<div class="panel-body">
				<form id = "recommendForm">
				<input type="hidden" name="bussinessId" value="${recommend.bussinessId}"/>
				<input type="hidden" name="orderId" value="${recommend.orderId}"/>
				<input type="hidden" name="id" value="${recommend.id}"/>
				<input type="hidden" name="recommendIndex" value="${recommend.recommendIndex}"/>
					<div class="form-group">
					服务指数:&nbsp;&nbsp;<div id="rating" class="rating" data-role="rating" ></div>
				   </div>
					<div class="form-group">
				       <textarea placeholder="请写下您的评价内容！" id="reasons" name="reasons" rows="5" class="form-control"></textarea>
				   </div>
				  <div class="form-group" style="text-align: center;">
				      <button class="btn btn-primary" id="submit" type="button"> 提 交 </button>
				  </div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
