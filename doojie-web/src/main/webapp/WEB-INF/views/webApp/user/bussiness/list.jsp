<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 洗车行</title>
<link rel="stylesheet" href="${ctx}/static/front/css/map.css" type="text/css" />
<style type="text/css">
#allmap {
	width: 100%;
	height: 80%;
}

</style>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">洗车行</span>
	     <span  class="ind-person" style="padding-top:3px;font-size:18px;" id="map">
<!-- 	     <span class="glyphicon glyphicon-map-marker"></span> -->
	     &nbsp;地图
	     </span> 
	  </div>
	</nav>
	<div id="locationShow" style="font-weight:bold;font-size:14px;color:black;text-align:center;display:none; line-height: 25px;"><span class="ind-person" >当前位置：</span><span id="locationTip"></span></div>
	<div id="contentTip" style="font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 25px;background-color: #ccc;"></div>
	<div id="allmap"  style="display:none;"></div>
	<div id="list" >
		<section id="listContent" class="list">
	      
    	</section>
	</div>
	<div id="contentDownTip" style="display:none;" class="list"><a onclick="loadMore()" class="btn btn-primary btn-block btn-large" >加载更多</a></div>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
	var map = null;
	var pageNumber = 1;
	var totalPage = 0;
	var a_lng = "";
	var a_lat = "";
	var a_area = "";
	var show = true;
		function detail(id,lng_p,lat_p){
			window.location.href = "${ctx}/webApp/detail/"+lng_p+"/"+lat_p+"/"+id;
		}
		
		function yuyue(id,name){
			var url = "${ctx}/webApp/toStoreAppointment/"+id+"/"+name+"/1";
			
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
		
		//加载更多数据
		function loadMore() { 
			showLoading();
			 if (getScrollTop() + getClientHeight() == getScrollHeight()) {
				 var cupage = pageNumber*1+1;
				 if(cupage > totalPage){
					 hideLoading();
					 showLoading("没有了");
					 setTimeout(function(){
						 hideLoading();
					 },500);
					 return;
				 }
				 init(a_lat,a_lng,a_area,cupage);
			 }
		}
		
		$().ready(function(){
			
			showLoading();
			
			//菜单加颜色提示
			$("#index").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			//菜单加颜色提示
			$("#xichehang").attr("style","color:#2885EC");
			
			
			
			$("#map").on("click",function(){
				if(show){
					$("#list").hide();
					$("#allmap").show();
// 					$("#map").html('<span class="glyphicon glyphicon-list ind_iconb"></span>&nbsp;列表');
					$("#map").html('&nbsp;列表');
					$("#locationShow").hide();
					$("#contentDownTip").hide();
					show = false;
				}else{
					$("#list").show();
					$("#allmap").hide();
// 					$("#map").html('<span class="glyphicon glyphicon-map-marker"></span>&nbsp;地图');
					$("#map").html('&nbsp;地图');
					$("#contentDownTip").show();
					$("#locationShow").show();
					show = true;
				}
				
			});
		});
		
		//百度地图API功能
		function loadJScript() {
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "http://api.map.baidu.com/api?v=2.0&ak=90c5e28f45b86faf2299dc6c80ef6097&callback=geo";
			document.body.appendChild(script);
		}
	
		
		function init(p_lat,p_lng,b_area,pageNum) { 
			
			var centerPoint = new BMap.Point(p_lng,
					p_lat);
			//设置中心点的缩放大小
			map.centerAndZoom(centerPoint,13);
			
			var mk = new BMap.Marker(centerPoint);
			map.addOverlay(mk);
			
			window.setTimeout(function(){
				 map.panTo(centerPoint);
			}, 2500);
			
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
				    address += e.addressComponent.streetNumber ==undefined ? "" : e.addressComponent.streetNumber;
					$("#locationTip").html(address);
					$("#listContent").html("");
					cleanStorageAll();
					setStorage("location",e.point.lat+"||"+e.point.lng);
					setStorage("area",e.addressComponent.city);
					setStorage("address",address);
					map.clearOverlays();
					init(e.point.lat,e.point.lng,e.addressComponent.city,1);
			  });
			  geolocationControl.addEventListener("locationError",function(error){
			    // 定位失败事件
			    switch(error.code){  
			        case error.TIMEOUT:  
// 			        	Alert("连接超时，请重试");
			        	console.log("连接超时，请重试");
			            break;  
			        case error.PERMISSION_DENIED:  
// 			        	Alert("您拒绝了使用位置共享服务，查询已取消"); 
			        	console.log("您拒绝了使用位置共享服务，查询已取消");
			            break;  
			        case error.POSITION_UNAVAILABLE:  
// 			        	Alert("亲爱的火星网友，非常抱歉，我们暂时无法为您所在的星球提供位置服务");  
			        	console.log("亲爱的火星网友，非常抱歉，我们暂时无法为您所在的星球提供位置服务");
			            break;  
			    }
			    map.clearOverlays();
			    init(a_lat,a_lng,a_area,1);
			  });
			  map.addControl(geolocationControl);
			  
			// 地图复杂的自定义覆盖物
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
			 
			//循环数据
			$.post("${ctx}/webApp/list",{lng:p_lng,lat:p_lat,currentPage:pageNum,area:b_area},function(data) {
				if (data.totalPage > 0) {
					if(show){
						$("#contentDownTip").show();
					}
					//总页数
					totalPage = data.totalPage;
					pageNumber = pageNum;
					a_lng = p_lng;
					a_lat = p_lat;
					a_area = b_area;
					var listContent = "";
					$(data.results).each(
							function(index) {
								var bussinessVo = data.results[index];
								
								/**         加载列表                           **/
								
								listContent += '<ul class="list-group">';
								listContent += '<li class="list-group-item"><span class="glyphicon glyphicon-leaf"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="li_blod">'+bussinessVo.bussiness.name+'</span></li>';
								listContent += '<li class="list-group-item"><span class="glyphicon glyphicon-phone-alt">&nbsp;</span><span><a href="tel:'+bussinessVo.bussiness.tel+'">'+bussinessVo.bussiness.tel+' </a></span><span class="pull-right gongli"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;'+bussinessVo.distance+'公里</span></li>';
								listContent += '<li class="list-group-item"><span class="glyphicon glyphicon-road">&nbsp;</span><span>'+bussinessVo.bussiness.address+' </span></li>';
								listContent += '<li class="list-group-item text-center">';
								listContent += '  <button onclick="detail('+bussinessVo.bussiness.id+','+bussinessVo.bussiness.lng+','+bussinessVo.bussiness.lat+')" class="btn btn-primary">查看详情</button>';
								listContent += '&nbsp;&nbsp;&nbsp;&nbsp;';
								if(bussinessVo.bussiness.isAuthor == null || bussinessVo.bussiness.isAuthor == 0){
									listContent += '<button disabled="disabled" onclick="yuyue('+bussinessVo.bussiness.id+',\''+bussinessVo.bussiness.name+'\')" class="btn btn-primary">我要预约</button>';
								}else{
									listContent += '<button onclick="yuyue('+bussinessVo.bussiness.id+',\''+bussinessVo.bussiness.name+'\')" class="btn btn-primary">我要预约</button>';
								}
								
								listContent += '</li>';
								listContent += '</ul>';
								
								/**          加载地图                       **/
								
								
								var point = new BMap.Point(bussinessVo.bussiness.lng,
										bussinessVo.bussiness.lat);
								// 创建自定义标注
								var marker = new ComplexCustomOverlay(point, bussinessVo.bussiness.name, bussinessVo.bussiness.name);
								map.addOverlay(marker);// 将标注添加到地图中
								
								//设置信息框位置
								var opts = {
									width : 200, // 信息窗口宽度
									height : 142, // 信息窗口高度
									title : "<b>" + bussinessVo.bussiness.name + "</b>", // 信息窗口标题
									enableMessage : false,//设置允许信息窗发送短息
									message : "查看详情",
									offset:new BMap.Size(0,-16)
								}
								var yuyue = "";
								if(bussinessVo.bussiness.isAuthor == null || bussinessVo.bussiness.isAuthor == 0){
									yuyue = '<a href="javascript:;"><button disabled="disabled" onclick="yuyue('+bussinessVo.bussiness.id+',\''+bussinessVo.bussiness.name+'\')" class="btn btn-primary">我要预约</button></a>';
								}else{
									yuyue = '<a href="javascript:;"><button onclick="yuyue('+bussinessVo.bussiness.id+',\''+bussinessVo.bussiness.name+'\')" class="btn btn-primary">我要预约</button></a>';
								}
								//整理弹出信息框的内容
								var content = '<div style="float:left;width:250px;">电话：'+bussinessVo.bussiness.tel+'<br><span style="display:inlin-block;">地址：'+bussinessVo.bussiness.address+'</span><br/><span style="display:inlin-block;">距离：'+bussinessVo.distance+'公里</span><p style="padding-top:3px;padding-left:23px;"><a href="javascript:;" onclick="detail('+bussinessVo.bussiness.id+','+bussinessVo.bussiness.lng+','+bussinessVo.bussiness.lat+')"><button  class="btn btn-primary">查看详情</button></a>&nbsp;&nbsp;&nbsp;&nbsp;'+yuyue+'</p></div>';
								var info = "<div class='iw_poi_content'>"+content+"</div>";
								var infoWindow = new BMap.InfoWindow(info,opts); // 创建信息窗口对象 
								var tmpfun = map.onclick; 
								map.onclick = null; 
								marker.addEventListener("touchstart", function() {
									map.onclick = tmpfun; 
									map.openInfoWindow(infoWindow, point); //开启信息窗口
								});
							});
					$("#contentTip").hide();
					$("#listContent").append(listContent);
				} else {
					$("#contentTip").hide();
					$("#contentDownTip").hide();
					$("#contentDownContent").show();
					 $("#contentDownContent").html("暂无数据");
				}
				hideLoading();
			}, "json");
		}
		
		function getLocation(){
			var geolocation = new BMap.Geolocation();
			if(!geolocation){
				init(null,null,null,1);
				//Alert("不支持定位");
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
			$("#contentTip").html("定位失败,请点击洗车行再试");
			hideLoading();
			init(a_lat,a_lng,a_area,pageNumber);
		    switch(error.code) {
		        case error.TIMEOUT:
//			          Alert("定位连接超时，请重试");
		        	console.log("定位连接超时，请重试");
		            break;
		        case error.POSITION_UNAVAILABLE:
//			        	Alert("无法定位");
		        	console.log("无法定位");
		            break;
		        case error.PERMISSION_DENIED:
//			        	Alert("拒绝使用位置共享服务");
					console.log("拒绝使用位置共享服务");
		            break;
		        case error.UNKNOWN_ERROR:
//			        	Alert("未知错误");
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
							$("#locationShow").show();
							$("#locationTip").html(address);
							setStorage("location",lat+"||"+lng);
							setStorage("area",area);
							setStorage("address",address);
							init(lat,lng,area,pageNumber);
						}
			    	},
			    	error : function(XMLHttpRequest, textStatus, errorThrown){
			    		init(a_lat,a_lng,a_area,pageNumber);
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
				var area = getStorage("area");
				var address = getStorage("address");
				$("#locationShow").show();
				$("#locationTip").html(address);
				$("#contentTip").html("数据加载中...");
				init(lat,lng,area,pageNumber);
			}
		};

		window.onload = loadJScript; 
	</script>
</body>
</html>
