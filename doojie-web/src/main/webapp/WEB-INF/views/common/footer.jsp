<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
<!--
#divJeepDiv  
        {  
            left: 0px;  
            top: 0px;  
            opacity: 0.6;  
            background: #000;  
            filter: alpha(opacity=60);  
            overflow: hidden;  
            position: fixed; 
            height: 100%;  
            width: 100%;  
            z-index: 3500;  
            display:none;
            text-align:center;
        } 
-->
</style>
<script type="text/javascript">
$().ready(function(){
	$('html,body').animate({scrollTop:0},'slow');
	$("#index").on("click",function(){
		showLoading();
		window.location.href='${ctx}/webApp/index';	
		//$("#index").attr("style","color:#2885EC");
	});
	//订单
	$("#order").on("click",function (){
		showLoading();
		$.get("${ctx}/webApp/user/isOrder",function(data){
			if(data == true){
				window.location.href= "${ctx}/webApp/user/order";
			}else{
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_herf","${ctx}/webApp/user/order", { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/login";
			}
		});
		//$("#order").attr("style","color:#2885EC");
	});
	
	//购卡
	$("#shopping").on("click",function (){
		showLoading();
		$.get("${ctx}/webApp/user/isShopCard",function(data){
			if(data == true){
				window.location.href= "${ctx}/webApp/user/test/shopCard?showwxpaytitle=1";
			}else{
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_herf","${ctx}/webApp/user/test/shopCard?showwxpaytitle=1", { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/login";
			}
		});
		//$("#my").attr("style","color:#2885EC");
	});
	
	$("#my").on("click",function (){
		showLoading();
		$.get("${ctx}/webApp/user/isMy",function(data){
			if(data == true){
				window.location.href= "${ctx}/webApp/user/my";
			}else{
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_herf","${ctx}/webApp/user/my", { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/login";
			}
		});
		//$("#my").attr("style","color:#2885EC");
	});
});

function showLoading(content){
	if(content == "" || content == null){
		content = "载入中...";
	}
	$("#loadingContent").html(content);
	$("#divJeepDiv").show();
}

function hideLoading(){
	$("#divJeepDiv").hide();
}
</script>
<section style="height:50px"></section>
<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
     <ul class="list-inline ind_listHeight" id="footerUl">
         <li id="index"><span class="glyphicon glyphicon-home"></span>&nbsp;首页</li>
         <li id="order"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;订单</li>
         <li id="shopping"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;购卡</li>
         <li id="my"><span class="glyphicon glyphicon-user"></span>&nbsp;我的</li>
     </ul>
</nav>
<div id="divJeepDiv"><div style="position:fixed; top:50%;left:50%;margin-top:-40px; /* height数值的一半 */
    margin-left:-50px; /* width数值的一半 */
    -webkit-box-shadow:0 0 3px rgba(0,0,0,.2);
    -moz-box-shadow:0 0 3px rgba(0,0,0,.2);
    box-shadow:0 0 3px rgba(0,0,0,.2); 
    border: 1px solid #dedede;
    -moz-border-radius: 15px;      /* Gecko browsers */
    -webkit-border-radius: 15px;   /* Webkit browsers */
    border-radius:15px;padding-top:15px;background:#fff;font-size:20px;font-weight:bold;width:100px;height:80px;"><img alt="" src="${ctx}/static/images/loading.gif"><br/><span id="loadingContent"></span></div></div>

