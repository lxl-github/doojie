<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>Insert title here</title>
<style type="text/css">
#mapContainer{
			position: absolute;
			top:0;
			left: 0;
			right:0;
			bottom:0;
		}
</style>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=a7866b1a9c7db096f680d6d7b26ae081"></script>
</head>
<body>
	<div id="mapContainer"></div>
	<script type="text/javascript">
		//初始化地图对象，加载地图
		var map = new AMap.Map("mapContainer",{
			resizeEnable: true, 
			view:new AMap.View2D({
				zoom:15
			})
		});
		//地图中添加地图操作ToolBar插件
		map.plugin(["AMap.ToolBar"],function() {		
			var toolBar = new AMap.ToolBar(); 
			map.addControl(toolBar);		
		});
		
	
		function addmarker(i,bussiness) {
			var lngX = bussiness.lng;
			var latY = bussiness.lat;
		    var markerOption = {
		        map:map,
		        icon:"http://webapi.amap.com/images/" + (i + 1) + ".png",
		        //复杂图标
// 				icon: new AMap.Icon({    
// 					//图标大小
// 					size:new AMap.Size(28,37),
// 					//大图地址
// 					image:"http://webapi.amap.com/images/custom_a_j.png", 
// 					imageOffset:new AMap.Pixel(-28,0)
// 				}),
		        position:new AMap.LngLat(lngX, latY),
		        topWhenMouseOver:true
		    };
		    var mar = new AMap.Marker(markerOption);
		    var infoWindow = new AMap.InfoWindow({
		        content:"<h3><font color=\"#00a6ac\">"+bussiness.name+"</font></h3><h4>电话:"+bussiness.tel+"</h4><h4>地址:"+bussiness.address+"</h4>",
		        size:new AMap.Size(230, 120),
		        autoMove:true,
		        offset:{x:0, y:-15}
		    });
		  
// 		    mar.setTitle(bussiness.name);
		    var aa = function (e) {infoWindow.open(map, mar.getPosition());};
		    AMap.event.addListener(mar, "click", aa);
		    
		}
	
		$().ready(function() {
			init();
			
		});
			
		function init() {
			$.get("${ctx}/webApp/list/1", function(data) {
				if (data != null) {
					$(data.results).each(
							function(index) {
								var bussiness = data.results[index];
								addmarker(index,bussiness);
							});
					map.setFitView();
				} else {
					alert("没有数据");
				}
			}, "json");
		}
	</script>
</body>
</html>
