<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 消费验码</title>
<script type="text/javascript">
$().ready(function(){
	$(".icon-back").on("click",function(){
		history.back();
	});
	
	$("#submit").on("click",function(){
		var code = $("#code").val().trim();
		if(code == ""){
			Alert("请输入或扫描10位验证码");
			return ;
		}
		showLoading("效验中...");
		$.post("${ctx}/webApp/merchant/checkCode/"+code,function(data){
			if(data == "-100"){
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_merchant_herf",window.location.href + '', { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/merchantLogin";
			}else if(data == "fail" || data == "exception"){
				Alert("验证失败,请稍后再试");
			}else if(data == "uncode"){
				Alert("验证码过期或不存在,请输入有效验证码");
			}else if(data == "unused"){
				Alert("此卡在使用中或消费次数已用完");
			}else{
				if(confirm("效验成功,订单生成,是否查看？")){
					window.location.href= "${ctx}/webApp/toMerchantOrderDetail/"+data;
				}else{
					location.reload();
				}
			}
			hideLoading();
		});
	});
});

</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">消费验码&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span>
	  </div>
	</nav>
	<br/>
	<div class="list">
		  <div class="form-group">
		  	   <input type="text"  style="height:55px;font-weight:bold; font-size:29px;" class="form-control" id="code"
						placeholder="请输入或扫描10位验证码">
		  </div>
		  <div class="form-group">
		      <span class="pull-left"><button class="btn btn-primary btn-block" type="button" id="scanQRCode1" >去扫描</button></span>
		      <span class="add-on pull-right"><button class="btn btn-primary btn-block" id="submit" type="button">开始验证</button></span>
		  </div>
	</div>
	<%@include file="/WEB-INF/views/common/loading.jsp"%>
	<script type="text/javascript" src="${ctx}/static/front/js/jweixin-1.0.0.js"> </script>
	<script type="text/javascript">
	wx.config({
	      debug:false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要l查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	      appId:'${appId}', // 必填，公众号的唯一标识
	      timestamp:${timestamp}, // 必填，生成签名的时间戳
	      nonceStr:'${nonceStr}', // 必填，生成签名的随机串
	      signature:'${signature}',// 必填，签名，见附录1
	      jsApiList:['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	  });
	
	wx.ready(function () {
		// 9.1.2 扫描二维码并返回结果
		  document.querySelector('#scanQRCode1').onclick = function () {
		    wx.scanQRCode({
		      needResult: 1,
		      desc: 'scanQRCode desc',
		      success: function (res) {
		    	  $("#code").val(res.resultStr);
		      }
		    });
		  };
	});
	  
	wx.error(function (res) {
		  Alert(res.errMsg);
	});
	</script>
</body>
</html>
