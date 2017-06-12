<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 推荐圈</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">推荐圈&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div id="locationShow" style="font-weight:bold;font-size:14px;color:black;text-align:center;display:none; line-height: 25px;"><span class="ind-person" >当前位置：</span><span id="locationTip"></span></div>
	<div id="contentTip" style="font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 25px;background-color: #ccc;"></div>
	<div id="list" >
		<section id="listContent" class="list">
	      
    	</section>
	</div>
	<div id="contentDownTip"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
	var map = null;
	var pageNumber = 1;
	var totalPage = 0;
	var a_lng = "";
	var a_lat = "";
	var a_area = "";
	var flag = true;
		function detail(id,lng_p,lat_p){
			window.location.href = "${ctx}/webApp/detail/"+lng_p+"/"+lat_p+"/"+id;
		}
		
		function toRecommendList(id){
			window.location.href = "${ctx}/webApp/toRecommendList/"+id;
		}
		
		$().ready(function(){
			//菜单加颜色提示
			$("#found").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			
			//页面下拉加载数据
			window.onscroll = function () { 
				if(flag){
					$("#contentDownTip").show();
					$("#contentDownTip").html('<span><img src="${ctx}/static/front/images/small_loading.gif"/></span>&nbsp;加载中...');
					 if (getScrollTop() + getClientHeight() == getScrollHeight()) {
						 var cupage = pageNumber*1+1;
						 if(cupage > totalPage){
							 $("#contentDownTip").hide();
							 $("#contentDownContent").show();
							 $("#contentDownContent").html("已经到底部了");
							 setTimeout(function(){
								 $("#contentDownContent").hide();
							 },500);
							 return;
						 }
						 init(a_lat,a_lng,a_area,cupage);
					 }
				}
			}
		});
		
		function init(p_lat,p_lng,b_area,pageNum) {
			flag = false;
			//增加显示数据加载中。。。提示
			$("#contentTip").html("数据加载中...");
			//循环数据
			$.post("${ctx}/webApp/user/recommendBussinessList",{lng:p_lng,lat:p_lat,currentPage:pageNum,area:b_area},function(data) {
				if(data == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/login";
				}else{
					if (data.totalPage > 0) {
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
									/**         加载列表                          <span class="pull-right "><span class="glyphicon glyphicon-thumbs-up"></span>'+bussinessVo.recommendCount+'</span> **/
									listContent +='<div class="panel panel-default">';
									listContent +='<div class="panel-heading">';
									listContent +='<h3 class="panel-title" style="font-weight:bold;">'+bussinessVo.bussiness.name+'</h3>';
									listContent +='</div>';
									listContent +='<div class="panel-body">地址:'+bussinessVo.bussiness.area+bussinessVo.bussiness.address+'</div>';
									listContent +='<table class="table">';
									listContent +='<tr>';
									listContent +='<td><a href="javascript:detail('+bussinessVo.bussiness.id+','+p_lng+','+p_lat+');"><span class="glyphicon glyphicon-duplicate"></span>&nbsp;详情</a></td>';
									listContent +='<td><span class="gongli"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;'+bussinessVo.distance+'公里</span></td>';
									listContent +='<td><a href="javascript:toRecommendList('+bussinessVo.bussiness.id+');"><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;理由('+bussinessVo.recommendCount+')</a></td>';
									listContent +='</tr>';
									listContent +='</table>';
									listContent +='</div>';
									
								});
						$("#contentTip").hide();
						$("#contentDownTip").hide();
						$("#listContent").append(listContent);
					} else {
						$("#contentTip").hide();
						$("#contentDownTip").hide();
						$("#contentDownContent").show();
						$("#contentDownContent").html("暂无数据");
					}
				}
				hideLoading();
			}, "json");
			flag = true;
		}
		
		//开始定位
		function geo(){
			showLoading();
			var geolocation = new BMap.Geolocation();
//			var geolocation = navigator.geolocation;
			if(!geolocation){
				$("#contentTip").html("定位失败");
				hideLoading();
// 				init(null,null,null,1);
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
			//定位失败
			function locationError(error){
				$("#contentTip").html("定位失败");
				init(a_lat,a_lng,a_area,pageNumber);
			    switch(error.code) {
			        case error.TIMEOUT:
// 			          Alert("定位连接超时，请重试");
			        	console.log("定位连接超时，请重试");
			            break;
			        case error.POSITION_UNAVAILABLE:
// 			        	Alert("无法定位");
			        	console.log("无法定位");
			            break;
			        case error.PERMISSION_DENIED:
// 			        	Alert("拒绝使用位置共享服务");
						console.log("拒绝使用位置共享服务");
			            break;
			        case error.UNKNOWN_ERROR:
// 			        	Alert("未知错误");
			        	console.log("未知错误");
			            break;
			        default:
			        	console.log("timeout4");
						break;
			    }
			}
			
			//定位成功
			function showPosition(position) {
// 				var point = {
// 						lati: position.coords.latitude,
// 						longi: position.coords.longitude
// 					};
// 					//GPS位置
// 					var gpsPoint = new BMap.Point(point.longi,point.lati);

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
								var address = "";
								address += data.result.addressComponent.province;
							    address += data.result.addressComponent.city;
							    address += data.result.addressComponent.district;
							    address += data.result.addressComponent.street;
							    address += data.result.addressComponent.streetNumber ==undefined ? "" : data.result.addressComponent.streetNumber;
								var area = data.result.addressComponent.city;
								$("#locationShow").show();
								$("#locationTip").html(address);
								init(lat,lng,area,pageNumber);
							}
				    	},
				    	error : function(XMLHttpRequest, textStatus, errorThrown){
				    		init(a_lat,a_lng,a_area,pageNumber);
				    	}
				    });
				};
				translateCallback(position.point);
// 				//开始转换
// 				BMap.Convertor.translate(gpsPoint,0,translateCallback);
			}
		};

		//百度地图API功能
		function loadJScript() {
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "http://api.map.baidu.com/api?v=2.0&ak=90c5e28f45b86faf2299dc6c80ef6097&callback=geo";
			document.body.appendChild(script);
		}
		
		window.onload = loadJScript; 
	</script>
</body>
</html>
