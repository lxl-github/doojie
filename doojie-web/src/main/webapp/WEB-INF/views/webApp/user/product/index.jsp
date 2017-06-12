<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 购卡</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">购卡&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div id="contentTip" style="font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 25px;background-color: #ccc;"></div>
	<div id="table" >
     </div>
    <div style="margin-top: 50%;text-align: center;">
	<a id="getBrandWCPayRequest" class="btn btn-primary " >微信&nbsp;&nbsp;支付</a>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
	$().ready(function(){
		//菜单加颜色提示
		$("#shopping").attr("style","color:#2885EC");
		
		$(".icon-back").on("click",function(){
			history.back();
		});
		
	});
	
	
	function init(regionName){
		//取区域
		$.post("${ctx}/webApp/getRegionIdByName",{name:regionName},function(data) {
			//循环数据
			$.post("${ctx}/webApp/user/productList",{regionId:data},function(data2) {
				if(data2 == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/login";
				}else{
					if (data2.length > 0 || data2 != "") {
						var listContent = "";
						$(data2).each(
								function(index) {
									var product = data2[index];
									/**         加载列表                           **/
									listContent +='<label class="list-group-item" for="productId'+product.id+'" >';
									listContent +='<input type="radio" id="productId'+product.id+'" name="productId" value="'+product.id+'"/>';
									listContent += product.name+'&nbsp;&nbsp;<span style="text-align:right">'+product.discountPrice/100+'元&nbsp;&nbsp;&nbsp;<span style="text-decoration: line-through;color: #ccc;">('+product.price/100+'元)</span></span>';
									var yxq = "一年";
									if(product.monthNumber < 12 ){
										yxq = product.monthNumber + "个月";
									}
									listContent +='<br/>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #ccc;font-size:12px;">有效期('+yxq+')</span>';
									listContent +='</label>';
								});
						$("#table").append(listContent);
						$("#contentTip").hide();
					} else {
						$("#getBrandWCPayRequest").attr("disabled","disabled");
						$("#contentTip").html("暂无可销售的都捷卡");
					}
				}
			}, "json");
		});
	}
	
	//百度地图API功能
	function loadJScript() {
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = "http://api.map.baidu.com/api?v=2.0&ak=90c5e28f45b86faf2299dc6c80ef6097&callback=geo";
		document.body.appendChild(script);
	}
	
	//获取位置
	function getLocation(){
		var geolocation = new BMap.Geolocation();
		if(!geolocation){
			$("#contentTip").html("定位失败");
		}else{
			//增加显示定位中。。。提示
			$("#contentTip").html("定位中...");
			geolocation.getCurrentPosition(showPosition, locationError,{
			    timeout: 5000,
			    maximumAge:86400000,
			    enableHighAccuracy: true
			});
		}
	}
	
	//定位失败
	function locationError(error){
	    switch(error.code) {
	        case error.TIMEOUT:
	          //连接超时，请重试
	        	console.log("timeout");
	            break;
	        case error.POSITION_UNAVAILABLE:
	          //无法定位
	        	console.log("timeout2");
	            break;
	        case error.PERMISSION_DENIED:
	        	//拒绝使用位置共享服务
	            break;
	        case error.UNKNOWN_ERROR:
	        //未知错误
	        	console.log("timeout3");
	            break;
	        default:
	        	console.log("timeout4");
				break;
	    }
	}
	
	//定位成功
	function showPosition(position) {
			//回调函数
			translateCallback = function (baiduPoint){
				var lng = baiduPoint.lng;
				var lat = baiduPoint.lat;
				
				$.ajax({
			    	type : "GET",
			    	url : "http://api.map.baidu.com/geocoder/v2/?ak=90c5e28f45b86faf2299dc6c80ef6097&callback=renderReverse&location=" + lat + "," + lng + "&output=json&pois=0",
			    	cache : true,
			    	async : true,
			    	timeout: 3000,
			    	dataType : "jsonp",
			    	jsonp: "callback",
			    	jsonpCallback:"success_jsonpCallback",
			    	success : function(data){
			    		if(data.status==0){
							//增加显示数据加载中。。。提示
							$("#contentTip").html("数据加载中...");
							var city = data.result.addressComponent.city;
							var address = "";
							address += data.result.addressComponent.province;
						    address += data.result.addressComponent.city;
						    address += data.result.addressComponent.district;
						    address += data.result.addressComponent.street;
						    address += data.result.addressComponent.streetNumber ==undefined ? "" : data.result.addressComponent.streetNumber;
							var area = data.result.addressComponent.city;
							setStorage("location",lat+"||"+lng);
							setStorage("area",area);
							setStorage("address",address);
							init(area.substring(0,area.length-1));
					}
		    	},
		    	error : function(XMLHttpRequest, textStatus, errorThrown){
		    		init("张家口");
		    	}
			});
	    };
	  //获取位置信息
		translateCallback(position.point);
	}
	
	//开始定位
	function geo(){
		$("#contentTip").html("数据加载中...");
		//获取本地存储
		var location = getStorage("location");
		if(location == null || location == ""){
			cleanStorageAll();
			getLocation();
		}else{
			var area = getStorage("area");
			init(area.substring(0,area.length-1));
		}
	};
	window.onload = loadJScript;
	</script>
	<script type="text/javascript" src="${ctx}/static/front/js/jweixin-1.0.0.js"> </script>
	<script type="text/javascript">
// 	var appid = '${appId}';
// 	var noncestr = '${nonceStr}';
// 	var sign = '${signature}';
// 	var timesTamp = '${timestamp}';
// 	wx.config({
// 	      debug:false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要l查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
// 	      appId:appid, // 必填，公众号的唯一标识
// 	      timestamp:timesTamp, // 必填，生成签名的时间戳
// 	      nonceStr:noncestr, // 必填，生成签名的随机串
// 	      signature:sign,// 必填，签名，见附录1
// 	      jsApiList:['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
// 	  });
	
	function onBridgeReady(){
		if (typeof WeixinJSBridge == "undefined"){
			Alert("微信支付必须在微信内置浏览器中使用");
			return;
		}
		$('#getBrandWCPayRequest').click(function(e){
			var flag = false;
			$('input[name="productId"]').each(function(){
				if($(this).attr("checked")){
					flag = true;
				}
			})
			if(!flag){
				Alert("请选择要购买的卡");
				return;
			}
			
			if(confirm("确认要支付吗？")){
					showLoading("支付中...");
					var pId = $('input[name="productId"]:checked').val();
					// 循环数据
					$.post("${ctx}/webApp/user/wxPay",{productId:pId},function(data) {
						if(data == "-100"){
							// 将页面记录到cookie，用于登录后跳转
							$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
							// 跳转登录页
							window.location.href="${ctx}/webApp/login";
							return;
						}else if(data == null){
							hideLoading();
							Alert("请求支付错误,请稍后再试");
							return;
						}

                        alert(data.appId);
                        alert(data.timeStamp);
                        alert(data.packageValue);
                        alert(data.signType);
                        alert(data.paySign);
                        alert(data.nonceStr);
						
						WeixinJSBridge.invoke(
							       'getBrandWCPayRequest', {
							           "appId":data.appId,   // 公众号名称，由商户传入
							           "timeStamp":data.timeStamp,         // 时间戳，自1970年以来的秒数
							           "nonceStr":data.nonceStr, // 随机串
							           "package":data.packageValue,     
							           "signType":data.signType,         // 微信签名方式：
							           "paySign": data.paySign // 微信签名
							       },
							       function(res){     
							           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
							        	   if(confirm("支付成功,是否查看我的卡券？")){
												window.location.href= "${ctx}/webApp/toMyCard";	
											}else{
												location.reload();
											}
							           }else{
							        	   if(res.err_msg == "get_brand_wcpay_request:cancel"){
		                   	  		    	 Alert("您已取消支付");
		                   	  		     } else if(res.err_msg == "get_brand_wcpay_request:fail"){
		                   	  		    	 Alert("支付失败");
		                   	  		     }else{
		                   	  		    	 alert(res.err_msg);
		                   	  		     }
							        	 hideLoading();
							           }   // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回
							       }
							   );
					});
				}
		});
		}
	
		if (typeof WeixinJSBridge == "undefined"){
		   if( document.addEventListener ){
		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		   }else if (document.attachEvent){
		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		   }
		}else{
		   onBridgeReady();
		}
	
	wx.error(function (res) {
		  alert(res.errMsg);
	});
	</script>
</body>
</html>
