<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 意见反馈</title>
<script type="text/javascript">
$().ready(function(){
	//菜单加颜色提示
	$("#my").attr("style","color:#2885EC");
	
	$(".icon-back").on("click",function(){
		history.back();
	});
	
	$("#submit").on("click",function(){
		var contents = $("#content").val().trim();
		if(contents == ""){
			Alert("请留下您的意见");
			return;
		}
		$.post("${ctx}/webApp/user/suggest",{content:contents},function(data){
			if(data){
				Alert("提交成功");
				reload();
			}else{
				Alert("提交失败");
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
		<span class="ind-title">意见反馈&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span>
	  </div>
	</nav>
	<br/>
	<div class="list">
		<form role="form">
		  <div class="form-group">
		       <textarea placeholder="请留下您的意见 ……小嘟在此感谢啦！" id="content" name="content" rows="5" class="form-control"></textarea>
		   </div>
		  <div class="form-group">
		    <div class="col-xs-offset-4 col-xs-8">
		      <button class="btn btn-primary" id="submit" type="button"> 提 交 </button>
		    </div>
		  </div>
		</form>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
