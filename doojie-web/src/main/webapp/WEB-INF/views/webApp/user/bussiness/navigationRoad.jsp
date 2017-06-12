<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | ${name}</title>
<link rel="stylesheet" href="${ctx}/static/front/css/map.css" type="text/css" />
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">${fn:substring(name,0,13)}&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div id="contentTip" style="font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 25px;background-color: #ccc;"></div>
	<div id="allmap"></div>
	<div id="r-result"></div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
		var map = null;
		$().ready(function(){
			//菜单加颜色提示
			$("#xichehang").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			showLoading();
		});
		
		//百度地图API功能
		function loadJScript() {
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "http://api.map.baidu.com/api?v=2.0&ak=90c5e28f45b86faf2299dc6c80ef6097&callback=geo";
			document.body.appendChild(script);
		}
	
		function init(lat,lng) {
			map.enableScrollWheelZoom(true);
			
			 //添加缩放平移控件
			  var navigationControl = new BMap.NavigationControl({
			    // 靠右下角位置
			    anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
			    // LARGE类型
			    type: BMAP_NAVIGATION_CONTROL_LARGE,
			    // 启用显示定位
			    enableGeolocation: true
			  });
			  map.addControl(navigationControl);
			
			  
			  // 添加定位控件
			  var geolocationControl = new BMap.GeolocationControl({
					// 靠右上角位置
				    anchor: BMAP_ANCHOR_TOP_RIGHT,
				    // LARGE类型
				    type: BMAP_NAVIGATION_CONTROL_LARGE,
				    // 启用显示定位
				    enableGeolocation: true
			  });
			  geolocationControl.addEventListener("locationSuccess", function(e){
				  // 定位成功事件
				    var address = '';
				    address += e.addressComponent.province;
				    address += e.addressComponent.city;
				    address += e.addressComponent.district;
				    address += e.addressComponent.street;
				    address += e.addressComponent.streetNumber;
					cleanStorageAll();
					setStorage("location",e.point.lat+"||"+e.point.lng);
					setStorage("area",e.addressComponent.city);
					setStorage("address",address);
				  	init(e.point.lat,e.point.lng);
			  });
			  geolocationControl.addEventListener("locationError",function(error){
			    // 定位失败事件
			    switch(error.code){  
			        case error.TIMEOUT:  
			        	Alert("连接超时，请重试");  
			            break;  
			        case error.PERMISSION_DENIED:  
			        	Alert("您拒绝了使用位置共享服务，查询已取消");  
			            break;  
			        case error.POSITION_UNAVAILABLE:  
			        	Alert("亲爱的火星网友，非常抱歉，我们暂时无法为您所在的星球提供位置服务");  
			            break;  
			    }  
			  });
			  map.addControl(geolocationControl);
			  
			
			  
			// 复杂的自定义覆盖物
			function ComplexCustomOverlay(point, text, mouseoverText){
				this._point = point;
				this._text = text;
				this._overText = mouseoverText;
			}
			ComplexCustomOverlay.prototype = new BMap.Overlay();
			ComplexCustomOverlay.prototype.initialize = function(map){
				this._map = map;
				var div = this._div = document.createElement("div");
				div.style.position = "absolute";
				div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
				div.style.background = "url(${ctx}/static/images/blue.gif) repeat-x 0 -33px";
				div.style.color = "white";
				div.style.height = "21px";
				div.style.padding = "2px";
				div.style.lineHeight = "18px";
				div.style.whiteSpace = "nowrap";
				div.style.MozUserSelect = "none";
				div.style.fontSize = "12px";
				div.style.cursor = "pointer";
				var span = this._span = document.createElement("span");
				div.appendChild(span);
				span.appendChild(document.createTextNode(this._text));
				var that = this;
				 
				var arrow = this._arrow = document.createElement("div");
				arrow.style.background = "url(${ctx}/static/images/blue.gif) no-repeat -8px -100px";
				arrow.style.position = "absolute";
				arrow.style.width = "30px";
				arrow.style.height = "12px";
				arrow.style.top = "19px";
				arrow.style.left = "10px";
				arrow.style.overflow = "hidden";
				div.appendChild(arrow);
				var leftBar = this._leftBar = document.createElement("div");
				leftBar.style.background = "url(${ctx}/static/images/blue.gif) no-repeat -12px -2px";
				leftBar.style.position = "absolute";
				leftBar.style.width = "11px";
				leftBar.style.height = "24px";
				leftBar.style.top = "0px";
				leftBar.style.left = "-10px";
				leftBar.style.overflow = "hidden";
				div.appendChild(leftBar);
				 
				var rightBar = this._rightBar = document.createElement("div");
				rightBar.style.background = "url(${ctx}/static/images/blue.gif) no-repeat -22px -2px";
				rightBar.style.position = "absolute";
				rightBar.style.width = "11px";
				rightBar.style.height = "24px";
				rightBar.style.top = "0px";
				rightBar.style.right = "-10px";
				rightBar.style.overflow = "hidden";
				div.appendChild(rightBar);
				div.onmouseover = function(){
				this.style.background = "url(${ctx}/static/images/blue2.gif) repeat-x 0 -33px";
				this.getElementsByTagName("span")[0].innerHTML = that._overText;
				arrow.style.background = "url(${ctx}/static/images/blue2.gif) no-repeat -8px -100px";
				leftBar.style.background = "url(${ctx}/static/images/blue2.gif) no-repeat -12px -2px";
				rightBar.style.background = "url(${ctx}/static/images/blue2.gif) no-repeat -22px -2px";
				}
				 
				div.onmouseout = function(){
				this.style.background = "url(${ctx}/static/images/blue.gif) repeat-x 0 -33px";
				this.getElementsByTagName("span")[0].innerHTML = that._text;
				arrow.style.background = "url(${ctx}/static/images/blue.gif) no-repeat -8px -100px";
				leftBar.style.background = "url(${ctx}/static/images/blue.gif) no-repeat -12px -2px";
				rightBar.style.background = "url(${ctx}/static/images/blue.gif) no-repeat -22px -2px";
				}
				 
				map.getPanes().labelPane.appendChild(div);
				return div;
			}

			ComplexCustomOverlay.prototype.draw = function(){
				var map = this._map;
				var pixel = map.pointToOverlayPixel(this._point);
				this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
				this._div.style.top = pixel.y - 30 + "px";
			}

			ComplexCustomOverlay.prototype.addEventListener = function(event,fun){
				this._div['on'+event] = fun;
			}
			
								
			/**          加载地图                       **/
			var b_lng = "${lng}";
			var b_lat = "${lat}";
			var b_name = "${name}";
			var point = new BMap.Point(b_lng,
					b_lat);
			
			//初始化商家位置
			var point = new BMap.Point(b_lng,b_lat);
			// 创建自定义标注
			var marker = new ComplexCustomOverlay(point, b_name, b_name);
			map.addOverlay(marker);// 将标注添加到地图中
			
			var p1 = new BMap.Point(lng,lat);
			var p2 = new BMap.Point(b_lng,b_lat);
			var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map,panel: "r-result", autoViewport: true}});
			driving.search(p1, p2);
			$("#contentTip").hide();
			hideLoading();
		}
		
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
					
					//设置中心点的缩放大小
					map.centerAndZoom(baiduPoint,15);
					window.setTimeout(function(){
						 map.panTo(baiduPoint);
					}, 1000);
					
					var mk = new BMap.Marker(baiduPoint);
					map.addOverlay(mk);
					
					$.ajax({
				    	type : "GET",
				    	url : "http://api.map.baidu.com/geocoder/v2/?ak=90c5e28f45b86faf2299dc6c80ef6097&location=" + lat + "," + lng + "&output=json&pois=0",
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
								init(lat,lng);
						}
			    	},
			    	error : function(XMLHttpRequest, textStatus, errorThrown){
			    		
			    	}
				});
		    };
		  	//获取位置信息
			translateCallback(position.point);
		}
		
		//开始定位
		function geo(){
			// 百度地图API功能
			map = new BMap.Map("allmap");
			//获取本地存储
			var location = getStorage("location");
			if(location == null || location == ""){
				cleanStorageAll();
				getLocation();
			}else{
				var locationArr = location.split("||");
				var lat = locationArr[0];
				var lng = locationArr[1];
				$("#contentTip").html("数据加载中...");
				init(lat,lng);
			}
		};

		window.onload = loadJScript; 
	</script>
</body>
</html>
