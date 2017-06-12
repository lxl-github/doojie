<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 到店预约</title>
</head>
<style type="text/css">
/* .bg { */
/*   width: 100%; */
/*   height: 100%; */
/*   background: #000; */
/*   filter: alpha(opacity=60); */
/*   -moz-opacity: 0.6; */
/*   opacity: 0.6; */
/*   display: none; */
/*   z-index: 9998; */
/*   top: 0; */
/*   left: 0;  */
/*   overflow: hidden; */
/*   position: fixed; */
/* } */

.slidediv {
  width: 100%;
  height: 100%;
  display: none;
  background: #fff;
  z-index: 9999;
  position: fixed;
  bottom: 0; 
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
	
	$().ready(function() {

		$(".icon-back").on("click", function() {
			history.back();
		});
		
		//下单
		$("#appiontment").on("click", function() {
			doSubmit();
		});
		
		//去购卡
		$("#toshopping").on("click", function() {
			window.location.href='${ctx}/webApp/user/test/shopCard?showwxpaytitle=1';
		});
		
		$("#orderType").val(${type});
		
		//初始化数据
		initOrderType(${myCard});
	});
	
	
	//切换服务类型
	function orderChangeType(op){
		$.post("${ctx}/webApp/getMyCard/"+op+"/0",function(data){
			if(data == "-100"){
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_herf",url, { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/login";
			}else{
				var datas = $.parseJSON(data);
				initOrderType(datas);
			}
		});
	}
	
	function initOrderType(datas){
		if(datas != ""){
			var userCards = "";
			$.each(datas,function(i,myCard){
				if(myCard.isExpired == 1){
					userCards +='<label class="list-group-item" for="userCard'+i+'" >';
					userCards +='<input type="radio" id="userCard'+i+'" name="userCard" disabled="disabled" value="'+myCard.id+'"/>';
					userCards += '<span style="text-decoration: line-through;color: #ccc;">'+myCard.productName+'(已过期)</span>';
					userCards +='</label>';
				}else{
					if(myCard.status == 2){
						userCards +='<label class="list-group-item" for="userCard'+i+'" >';
						userCards +='<input type="radio" id="userCard'+i+'" name="userCard" disabled="disabled" value="'+myCard.id+'"/>';
						userCards += '<span style="text-decoration: line-through;color: #ccc;">'+myCard.productName+'(使用中)</span>';
						userCards +='</label>';
					}else if(myCard.status == 1 || myCard.consumeNumber == "0"){
						userCards +='<label class="list-group-item" for="userCard'+i+'" >';
						userCards +='<input type="radio" id="userCard'+i+'" name="userCard" disabled="disabled" value="'+myCard.id+'"/>';
						userCards += '<span style="text-decoration: line-through;color: #ccc;">'+myCard.productName+'(无可用次数)</span>';
						userCards +='</label>';
					}else{
						userCards +='<label class="list-group-item" for="userCard'+i+'" >';
						userCards +='<input type="radio" id="userCard'+i+'" name="userCard" value="'+myCard.id+'"/>';
						userCards += '<span>'+myCard.productName+'</span>';
						userCards +='</label>';
					}
				}
			});
			$("#userCards").html(userCards);
			$("#doojieCard").show();
			$("#appiontment").attr("style","display:block");
			$("#toshopping").attr("style","display:none");
		}else{
			$("#doojieCard").hide();
			$("#appiontment").attr("style","display:none");
			$("#toshopping").attr("style","display:block");
		}
	}
	
	function doSubmit(){
		var number = $("#number").val().trim();
		if(number == "" || number == null || number.length == 0){
			Alert("请选择车辆信息");
			return;
		}
		
		var appiontmentDate = $("#appiontmentDate").val().trim();
		if(appiontmentDate == "" || appiontmentDate == null || appiontmentDate.length == 0){
			Alert("请选择预约时间");
			return;
		}
		
		var chooseCard = $("#cards").val();
		if(chooseCard == "" || chooseCard == null){
			Alert("请选择都捷卡");
			return;
		}
		
		if(confirm("确认要提交吗？")){
			showLoading("提交中...");
			
			var formData = $("#form").serializeJson();
			var jsons = JSON.stringify(formData);
			$.post("${ctx}/webApp/doStoreAppointment",{json:jsons},function(data){
				if(data > 10){
					if(confirm("下单成功,是否查看订单？")){
						window.location.href= "${ctx}/webApp/user/order";	
					}else{
						location.reload();
					}
				}else if(data == 0){
					Alert("下单失败,请稍后再试");
					hideLoading();
				}else if(data == 2){
					Alert("下单失败,都捷卡被使用中或无消费次数");
					hideLoading();
				}else if(data == 4){
					Alert("下单失败,时间段已过期,请重新选择");
					hideLoading();
				}
			});
		}
	}
	
	//去选
	function goChoose(op){
		if(op == 1){
			$("#chooseCard").show();
		}else if(op == 2){
			$("#chooseTime").show();
		}else if(op == 3){
			$("#chooseCarInfo").show();
		}
	}
	//关闭
	function closeChoose(op){
		if(op == 1){
			$("#chooseCard").hide();
		}else if(op == 2){
			$("#chooseTime").hide();
		}else if(op == 3){
			$("#chooseCarInfo").hide();
		}
	}
	
	//确认
	function goConfirm(op){
		if(op == 1){
			submit1();
		}else if(op == 2){
			submit2();
		}else if(op == 3){
			submit3();
		}
	}
	
	//重选
	function goRest(op){
		if(op == 1){
			rest1();
		}else if(op == 2){
			rest2();
		}else if(op == 3){
			rest3();
		}else{//全部重选
			rest1();
			rest2();
			rest3();
		}
	}
	
	function submit1(){
		var flag = false;
		$('input[name="userCard"]').each(function(){
			if($(this).attr("checked")){
				flag = true;
			}
		})
		if(!flag){
			Alert("请选择一张卡");
			return;
		}else{
			$("#cards").val($('input[name="userCard"]:checked').val());
			$("#cardsName").html($('input[name="userCard"]:checked').next('span').html());
			$("#chooseCard").hide();
		}
	}
	
	function submit2(){
		var flag = false;
// 		$('input[name="cappiontmentDate"]').each(function(){
// 			if($(this).attr("checked")){
// 				flag = true;
// 			}
// 		})
		var date = $("#cappiontmentDate").val();
		if(date == "0"){
			Alert("请选择日期");
			return;
		}else{
			var time = $('input[name="ctime"]:checked').val();
			if(time == "" || time == null){
				Alert("请选择时段");
				return;
			}
			//$('input[name="cappiontmentDate"]:checked').val()
			$("#appiontmentDate").val(date);
			$("#time").val(time);
			$("#appiontmentDateName").html($("#cappiontmentDate").find("option:selected").text()+" "+time);
			$("#chooseTime").hide();
		}
	}
	
	function submit3(){
		
		
		var cplateflag = false;
		var cletterflag = false;
// 		$('input[name="cplate"]').each(function(){
// 			if($(this).attr("checked")){
// 				cplateflag = true;
// 			}
// 		});

		var cplate = $("#cplate").val();
		
		$('input[name="cletter"]').each(function(){
			if($(this).attr("checked")){
				cletterflag = true;
			}
		});
		
		if(cplate == "0"){
			Alert("请选择省份简称");
			return;
		}else if(!cletterflag){
			Alert("请选择区域简称");
			return;
		}else{
			var cnumber = $("#cnumber").val().trim();
			if(cnumber == "" || !/^[A-Za-z0-9]+$/.test(cnumber) || cnumber.length > 5){
				Alert("请输入5位数字或字母的车牌号");
				return;
			}
			//$('input[name="cplate"]:checked').val()
			$("#plate").val(cplate);
			$("#letter").val($('input[name="cletter"]:checked').val());
			$("#number").val(cnumber.toUpperCase());
			//$('input[name="cplate"]:checked').val()
			$("#carInfoName").html($("#cplate").find("option:selected").text()+$('input[name="cletter"]:checked').val()+"•"+cnumber.toUpperCase());
			$("#chooseCarInfo").hide();
		}
	}
	
	//重选
	function rest1(){
		$('input[name="userCard"]').each(function(){
			$(this).attr("checked",false);
		})
		$("#cards").val('');
		$("#cardsName").html('请选择都捷卡');
	}
	
	function rest2(){
// 		$('input[name="cappiontmentDate"]').each(function(){
// 			$(this).attr("checked",false);
// 		})
		$("#cappiontmentDate").val('0');
		$("#times").html('');
		$("#appiontmentDate").val('');
		$("#time").val('');
		$("#appiontmentDateName").html('请选择预约时间');
	}
	
	function rest3(){
// 		$('input[name="cplate"]').each(function(){
// 			$(this).attr("checked",false);
// 		})
		$("#cplate").val('0');
		$('input[name="cletter"]').each(function(){
			$(this).attr("checked",false);
		})
		$("#plate").val('');
		$("#letter").val('');
		$("#cnumber").val('');
		$("#number").val('');
		$("#carInfoName").html('请选择车辆信息');
	}
	
	function goChange(op){
		if(op == "0"){
			return;
		}
		$("#times").html("<div style='text-align: center;'><strong>加载中......</strong></div>");
		$.post("${ctx}/webApp/getTimeSpan/"+op,function(data){
			if(data == "-100"){
				//将页面记录到cookie，用于登录后跳转
				$.cookie("target_herf",url, { path: '${ctx}' });
				//跳转登录页
				window.location.href="${ctx}/webApp/login";
			}else{
				var datas = $.parseJSON(data);
				var content = '<table style="text-align:left;" class="table" id="times">';
				var tmp = 0;
				$.each(datas,function(i,item){
					tmp = tmp + 1;
					if(tmp == 1){
						content += '<tr>';
					}
					
					if(item.isShow == 1){
						content += '<td><label for="ctime'+i+'"><input type="radio" disabled="disabled" id="ctime'+i+'" name="ctime" value="'+item.time+'"><span style="text-decoration: line-through;color: #ccc;">'+item.time+'</span></label></td> ';
					}else{
						content += '<td><label for="ctime'+i+'"><input type="radio"  id="ctime'+i+'" name="ctime" value="'+item.time+'"><span>'+item.time+'</span></label></td> ';
					}
					
					if(tmp == 2){
						content += '</tr>';
						content += '<tr>';
						tmp = 0;
					}
					
					if(datas.length == i){
						content += '</tr>';
					}
				});
				content += '</table>';
				$("#times").html(content);
			}
		});
	}
</script>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	<div class="ik-ind-container">
		<span class="icon-back"><span
			class="glyphicon glyphicon-menu-left"></span>&nbsp;</span> <span
			class="ind-title">到店预约&nbsp;</span> <span class="ind-person">&nbsp;</span>
	</div>
	</nav>
	<div class="list">
		<div class="panel panel-default">
			<div class="panel-body">
			<form class="form-horizontal" id="form"  role="form">
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="mobile" name ="mobile"
							placeholder="手机号" value="${userMobile}" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="bussinessName" class="col-sm-2 control-label">洗车行</label>
					<div class="col-sm-10">
						<input type="text" id="bussinessName" value="${bussinessName}" readonly="readonly" class="form-control"
						placeholder="洗车行">
						<input type="hidden" id="bussinessId" name="bussinessId" value="${bussinessId}"/>
					</div>
				</div>
				<div class="form-group">
					<label for="orderType" class="col-sm-2 control-label">服务类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="orderType" onchange="orderChangeType(this.value)" name="orderType">
							<option value="1">洗车</option>
<!-- 							<option value="2">维修</option> -->
<!-- 							<option value="3">保养</option> -->
						</select>
					</div>
				</div>
				<div class="form-group">
<!-- 					<label for="mobile" class="col-sm-2 control-label"></label> -->
					<div class="col-sm-10">
					<a href="javascript:;" onclick="goChoose(3)">
							<div class="panel panel-default ">
								<table class="table">
									<tr>
										<td>
											<input type="hidden" id="plate" name="plate"/>
											<input type="hidden" id="letter" name="letter"/>
											<input type="hidden" id="number" name="number"/>
											<span id="carInfoName" name="carInfoName">
												请选择车辆信息
											</span>
										</td>
									</tr>
								</table>
							</div>
						</a>
					</div>
				</div>
				<div class="form-group">
<!-- 					<label for="mobile" class="col-sm-2 control-label"></label> -->
					<div class="col-sm-10">
						<a href="javascript:;" onclick="goChoose(2)">
							<div class="panel panel-default">
								<table class="table">
									<tr>
										<td>
											<input type="hidden" name="appiontmentDate" id="appiontmentDate" value=""/>
											<input type="hidden" name="time" id="time" value=""/>
											<span id="appiontmentDateName" name="appiontmentDateName">
												请选择预约时间
											</span>
										</td>
									</tr>
								</table>
							</div>
						</a>
					</div>
				</div>
				<div class="form-group" id="doojieCard">
<!-- 					<label for="orderType" class="col-sm-2 control-label"></label> -->
					<div class="col-sm-10">
						<input type="hidden" id="cards" name="cards" value=""/>
						<a href="javascript:;" onclick="goChoose(1)">
							<div class="panel panel-default">
								<table class="table">
									<tr>
										<td>
											<span id="cardsName" name="cardsName">
												请选择都捷卡
											</span>
										</td>
									</tr>
								</table>
							</div>
						</a>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<a class="btn btn-primary btn-block btn-large" id="appiontment">下 &nbsp;&nbsp;&nbsp;&nbsp;单</a>
						<a class="btn btn-primary btn-block btn-large" style="display:none;" id="toshopping">去&nbsp;&nbsp;购&nbsp;&nbsp;卡</a>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
	<!-- 选卡 -->
	<div id="chooseCard" class="slidediv">
		<strong style="float:right;font-size:30px;width:50px;text-align: center;line-height:20px;padding-top:10px; margin-bottom:10px;" class="closes" onclick="closeChoose(1)"><span class="glyphicon glyphicon-remove-circle"></span></strong>
		<br/>
		<div style="clear:both;width:90%;margin:0 auto;" >
				<div class="form-group">
					   <div id="userCards">
							
					   </div>
			   </div>
			  <div class="form-group" style="text-align: center;">
			      <button class="btn btn-primary" onclick="goConfirm(1)" id="submit" type="button"> 确定 </button>&nbsp;&nbsp;&nbsp;&nbsp; <button onclick="goRest(1)" class="btn btn-primary" id="rest" type="button"> 重选 </button>&nbsp;&nbsp;&nbsp;&nbsp; <button onclick="closeChoose(1)" class="btn btn-primary closes" type="button">关 闭 </button>
			  </div>
		</div>
	</div>
	
	<!-- 选预约时间 -->
	<div id="chooseTime" class="slidediv">
		<strong style="float:right;font-size:30px;width:50px;text-align: center;line-height:20px;padding-top:10px; margin-bottom:10px;" class="closes" onclick="closeChoose(2)"><span class="glyphicon glyphicon-remove-circle"></span></strong>
		<br/>
		<div style="clear:both;width:90%;margin:0 auto;" >
				<div class="form-group" >
					<div class="col-sm-10">
						<select class="form-control" onchange="goChange(this.value)" id="cappiontmentDate" name="cappiontmentDate">
					  	<option value="0">请选择日期</option>
					  	<c:forEach items="${appiontmentDate}" var="item">
					   	<option value="${item.value}">${item.key}</option>
					   </c:forEach>
					  </select>
					</div>
				</div>
				<div class="form-group">
					<div id="times">
					
					</div>
			   </div>
			  <div class="form-group" style="text-align: center;">
			      <button class="btn btn-primary" onclick="goConfirm(2)" id="submit" type="button"> 确定 </button>&nbsp;&nbsp;&nbsp;&nbsp; <button onclick="goRest(2)" class="btn btn-primary" id="rest" type="button"> 重选 </button>&nbsp;&nbsp;&nbsp;&nbsp; <button onclick="closeChoose(2)" class="btn btn-primary closes" type="button">关 闭 </button>
			  </div>
		</div>
	</div>
	
	<!-- 选车辆信息 -->
	<div id="chooseCarInfo" class="slidediv">
		<strong style="float:right;font-size:30px;width:50px;text-align: center;line-height:20px;padding-top:10px; margin-bottom:10px;" class="closes" onclick="closeChoose(3)"><span class="glyphicon glyphicon-remove-circle"></span></strong>
		<br/>
		<div style="clear:both;width:90%;margin:0 auto;" >
			<div class="form-group" >
				<div class="col-sm-10">
					<select class="form-control"  id="cplate" name="cplate">
				  	<option value="0">请选择省份简称</option>
				  	<c:forEach items="${plateMap}" var="plate">
				   	<option value="${plate.value}">${plate.key}</option>
				   </c:forEach>
				  </select>
				</div>
			</div>
			<div class="form-group">
<!-- 				   <table class="table" id="car"> -->
<!-- 				   <tr> -->
<!-- 						<td colspan="8"> -->
<!-- 						<input type="text" class="form-control" id="cnumber" name="cnumber" -->
<!-- 					placeholder="5位数字或字母的车牌号"> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<%-- 				   <c:forEach items="${plateMap}" var="plate" varStatus="currentStatus"> --%>
<%-- 				   	<c:if test="${currentStatus.first}"> --%>
<!-- 				   		<tr> -->
<%-- 				   	</c:if> --%>
<%-- 				   	<td><label for="cplate${plate.value}"><input type="radio" id="cplate${plate.value}" name="cplate" value="${plate.value}">${plate.key}</label></td>  --%>
<%-- 				   	<c:if test="${currentStatus.count % 8 == 0}">					   		 --%>
<!-- 				   		</tr> -->
<!-- 				   		<tr> -->
<%-- 				   	</c:if> --%>
<%-- 				   	<c:if test="${currentStatus.last}"> --%>
<!-- 				   		</tr> -->
<%-- 				   	</c:if> --%>
<%-- 				   </c:forEach> --%>
<!-- 				   </table> -->
				   <table class="table" id="car">
					<c:forEach items="${letterMap}" var="letter" varStatus="currentStatus">
				   	<c:if test="${currentStatus.first}">
				   		<tr>
				   	</c:if>
				   	<td><label for="cletter${letter.value}"><input type="radio" id="cletter${letter.value}" name="cletter" value="${letter.value}">${letter.key}</label></td> 
				   	<c:if test="${currentStatus.count % 6 == 0}">
				   		</tr>
				   		<tr>
				   	</c:if>
				   	<c:if test="${currentStatus.last}">
				   		</tr>
				   	</c:if>
				   </c:forEach>
				</table>
				<table class="table" id="car">
					<tr>
						<td>
						<input type="text" class="form-control" id="cnumber" name="cnumber"
					placeholder="5位数字或字母的车牌号">
						</td>
					</tr>
				</table>
			</div>
		    <div class="form-group" style="text-align: center;">
		      <button class="btn btn-primary" onclick="goConfirm(3)" id="submit" type="button"> 确定 </button>&nbsp;&nbsp;&nbsp;&nbsp; <button onclick="goRest(3)" class="btn btn-primary" id="rest" type="button"> 重选 </button>&nbsp;&nbsp;&nbsp;&nbsp; <button onclick="closeChoose(3)" class="btn btn-primary closes" type="button">关 闭 </button>
		    </div>
		</div>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
