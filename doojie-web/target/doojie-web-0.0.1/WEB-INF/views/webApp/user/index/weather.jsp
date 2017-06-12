<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 天气指南</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">天气指南&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div id="contentTip" style="font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 25px;background-color: #ccc;"></div>
	<div class="list" id="weatherDiv">
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
	$(function(){
		showLoading();
		//菜单加颜色提示
		$("#index").attr("style","color:#2885EC");
		
		$(".icon-back").on("click",function(){
			history.back();
		});
		
	});
	
	//百度地图API功能
	function loadJScript() {
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = "http://api.map.baidu.com/api?v=2.0&ak=90c5e28f45b86faf2299dc6c80ef6097&callback=geo";
		document.body.appendChild(script);
	}
	//开始定位
	function geo(){
		//获取本地存储
		var location = getStorage("location");
		if(location == null || location == ""){
			cleanStorageAll();
			getLocation();
		}else{
			var locationArr = location.split("||");
			var lat = locationArr[0];
			var lng = locationArr[1];
			function postion(){};
			postion.point = new BMap.Point(lng,
					lat);
			showPosition(postion);
		}
	}
	window.onload = loadJScript; 
	
	function getLocation(){
		var geolocation = new BMap.Geolocation();
		if(!geolocation){
			$("#contentTip").html("定位失败");
		}else{
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
//		          Alert("定位连接超时，请重试");
	        	console.log("定位连接超时，请重试");
	            break;
	        case error.POSITION_UNAVAILABLE:
//		        	Alert("无法定位");
	        	console.log("无法定位");
	            break;
	        case error.PERMISSION_DENIED:
//		        	Alert("拒绝使用位置共享服务");
				console.log("拒绝使用位置共享服务");
	            break;
	        case error.UNKNOWN_ERROR:
//		        	Alert("未知错误");
	        	console.log("未知错误");
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
			    	url : "http://api.map.baidu.com/geocoder/v2/?ak=90c5e28f45b86faf2299dc6c80ef6097&location=" + lat + "," + lng + "&output=json&pois=0",
			    	cache : true,
			    	async : true,
			    	timeout: 3000,
			    	dataType : "jsonp",
			    	jsonp: "callback",
			    	jsonpCallback:"success_jsonpCallback",
			    	beforeSend:function(){
			    		$("#contentTip").html("天气加载中......");
			    	},
			    	success : function(data){
			    		if(data.status==0){
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
							$.ajax({
						    	type : "GET",
						    	url : "http://api.map.baidu.com/telematics/v3/weather?location="+area+"&output=json&ak=90c5e28f45b86faf2299dc6c80ef6097",
						    	cache : true,
						    	async : true,
						    	timeout: 3000,
						    	dataType : "jsonp",
						    	jsonp: "callback",
						    	jsonpCallback:"success_jsonpCallback",
						    	success : function(data){
						    		if(data.error == 0){
						    			var data_result = data.results[0];
						    			var data_index = data.results[0].index;
						    			var data_weather_data = data.results[0].weather_data[0];
						    			var str = '<div class="panel-heading">';
						    			str += '<h3 class="panel-title" style="font-weight:bold;">'+data_result.currentCity+'</h3>';
						    			str += '</div>';
						    			str += '<div class="panel-body">'+data_weather_data.date+' &nbsp;&nbsp;'+data_weather_data.temperature+'</div>';
						    			str += '<div class="panel-body"><img src="'+data_weather_data.dayPictureUrl+'"/> '+data_weather_data.weather+'</div>';
						    			str += '<div class="panel-body">风力：'+data_weather_data.wind+'</div>';
						    		    var kqstr = "优";
						    		    if(data_result.pm25 <= 100 && data_result.pm25 >= 50){
						    		    	kqstr = "良";
						    		    }else if(data_result.pm25 <= 150 && data_result.pm25 >= 101){
						    		    	kqstr = "轻度污染";
						    		    }else if(data_result.pm25 <= 200 && data_result.pm25 >= 151){
						    		    	kqstr = "中度污染";
						    		    }else if(data_result.pm25 <= 300 && data_result.pm25 >= 201){
						    		    	kqstr = "重度污染";
						    		    }else if(data_result.pm25 > 300){
						    		    	kqstr = "严重污染";
						    		    }
						    		    str += '<div class="panel-body">空气指数：'+data_result.pm25+' &nbsp;&nbsp;&nbsp;&nbsp;空气质量： <span style="font-weight:bold;">'+kqstr+'</span></div>';
						    		    if(data_index != ""){
                                            str += '<div class="panel-body">'+data.results[0].index[1].tipt+'：'+data.results[0].index[1].des+'</div>';
                                        }
										$("#weatherDiv").html(str);
						    		}
						    	}
							});
					}
			    	hideLoading();
		    	},
		    	complete: function(){
		    		$("#contentTip").hide();
		    	},
		    	error : function(XMLHttpRequest, textStatus, errorThrown){
		    		
		    	}
		    });
		};
		translateCallback(position.point);
	}
	</script>
</body>
</html>
