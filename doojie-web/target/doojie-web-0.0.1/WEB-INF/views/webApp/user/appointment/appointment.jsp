<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 上门预约</title>
</head>
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
	var geshi = false;
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
			window.location.href='${ctx}/webApp/user/shopCard';
		});
		//初始化数据
		initOrderType(${myCard});
		
		alert("区域商圈为目前所开通区域,下单请注意您是否在此区域");
	});
	
	//切换服务类型
	function orderChangeType(op){
		$.post("${ctx}/webApp/getMyCard/"+op+"/0",function(data){
			var datas = $.parseJSON(data);
			initOrderType(datas);
		});
	}
	
	function initOrderType(datas){
		if(datas != ""){
			$("#cards").empty();
			$.each(datas,function(i,myCard){
				var isExpired = "未过期";
				var disabled = "false";
				if(myCard.isExpired == 1){
					isExpired = "已过期";
					disabled = "true";
				}
				if(disabled == "true"){
					var cards = document.getElementById("cards");
					var opp = new Option(myCard.productName+"("+isExpired+")",myCard.id);
					opp.disabled = disabled;
					cards.options.add(opp);
				}else{
					var cards = document.getElementById("cards");
					var opp = new Option(myCard.productName+"("+isExpired+")",myCard.id);
					cards.options.add(opp);
				}
			});
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
		if(number == "" || !/^[A-Za-z0-9]+$/.test(number) || number.length > 5){
			Alert("请输入5位数字或字母的车牌号");
			return;
		}
		
		var chooseCard = $("#cards").val();
		if(chooseCard == "" || chooseCard == null){
			Alert("请选择都捷卡");
			return;
		}
		showLoading("提交中...");
		
		var formData = $("#form").serializeJson();
		var jsons = JSON.stringify(formData);
		$.post("${ctx}/webApp/doStoreAppointment",{json:jsons},function(data){
			if(data){
				if(confirm("下单成功,是否查看订单？")){
					window.location.href= "${ctx}/webApp/user/order";	
				}else{
					reload();
				}
			}else{
				Alert("下单失败");
				hideLoading();
			}
		});
	}
</script>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	<div class="ik-ind-container">
		<span class="icon-back"><span
			class="glyphicon glyphicon-menu-left"></span>&nbsp;</span> <span
			class="ind-title">上门预约&nbsp;</span> <span class="ind-person">&nbsp;</span>
	</div>
	</nav>
	<div class="list">
		<div class="panel panel-default">
			<div class="panel-body">
			<form class="form-horizontal"  role="form">
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="mobile" name="mobile"
							placeholder="手机号" value="${userMobile}" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="bussinessName" class="col-sm-2 control-label">区域商圈</label>
					<div class="col-sm-10">
						<select class="form-control" id="city" name="city">
							<option value="1" >北京</option>
							<option value="2" disabled="disabled">石家庄</option>
							<option value="3" disabled="disabled">张家口</option>
							</select>
						<select class="form-control" id="district" name="district">
							<option value="1" >东城区</option>
							<option value="2" disabled="disabled">朝阳区</option>
						</select>
						<select class="form-control" id="bussinessDistrict" name="bussinessDistrict">
							<option value="1" >朝阳门商区</option>
							<option value="2" disabled="disabled">大悦城商区</option>
							<option value="3" disabled="disabled">东单商区</option>
						</select>
						<input type="text" class="form-control" id="address" name="address"
							placeholder="详细地址" value="" >
					</div>
				</div>
				<div class="form-group">
					<label for="youhui" class="col-sm-2 control-label">服务类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="orderType" onchange="orderChangeType(this.value)" name="orderType">
							<option value="1">洗车</option>
							<option value="2">维修</option>
							<option value="3">保养</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">车辆信息</label>
					<div class="col-sm-10">
					<select class="form-control" id="plate" name="plate">
						<option value="京">京</option>
						<option value="津">津</option>
						<option value="沪">沪</option>
						<option value="渝">渝</option>
						<option value="冀">冀</option>
						<option value="豫">豫</option>
						<option value="云">云</option>
						<option value="辽">辽</option>
						<option value="黑">黑</option>
						<option value="湘">湘</option>
						<option value="皖">皖</option>
						<option value="鲁">鲁</option>
						<option value="新">新</option>
						<option value="苏">苏</option>
						<option value="浙">浙</option>
						<option value="赣">赣</option>
						<option value="鄂">鄂</option>
						<option value="桂">桂</option>
						<option value="甘">甘</option>
						<option value="晋">晋</option>
						<option value="蒙">蒙</option>
						<option value="陕">陕	</option>
						<option value="吉">吉</option>
						<option value="闽">闽</option>
						<option value="贵">贵</option>
						<option value="粤">粤</option>
						<option value="青">青</option>
						<option value="藏">藏</option>
						<option value="川">川</option>
						<option value="宁">宁</option>
						<option value="琼">琼</option>
						</select>
					<select class="form-control" id="letter" name="letter">
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
						<option value="E">E</option>
						<option value="F">F</option>
						<option value="G">G</option>
						<option value="H">H</option>
						<option value="I">I</option>
						<option value="J">J</option>
						<option value="K">K</option>
						<option value="L">L</option>
						<option value="M">M</option>
						<option value="N">N</option>
						<option value="O">O</option>
						<option value="P">P</option>
						<option value="Q">Q</option>
						<option value="R">R</option>
						<option value="S">S</option>
						<option value="T">T</option>
						<option value="U">U</option>
						<option value="V">V</option>
						<option value="W">W</option>
						<option value="X">X</option>
						<option value="Y">Y</option>
						<option value="Z">Z</option>
						</select>
						<input type="text" class="form-control" id="number"
						placeholder="5位数字或字母的车牌号">
					</div>
				</div>
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">预约时间</label>
					<div class="col-sm-10">
						<select class="form-control" id="appiontmentDate" name="appiontmentDate">
							<c:forEach items="${appiontmentDate}" var="item">
								<option value="${item.value}">${item.key}</option>
							</c:forEach>
						</select>
						<select  class="form-control" id="time" name="time">
							  <option value="07:00~09:00">07:00~09:00</option>
							  <option value="09:00~11:00">09:00~11:00</option>
							  <option value="12:00~14:00">12:00~14:00</option>
							  <option value="14:00~16:00">14:00~16:00</option>
							  <option value="16:00~18:00">16:00~18:00</option>
							 </select>
					</div>
				</div>
				<div class="form-group" id="doojieCard">
					<label for="youhui" class="col-sm-2 control-label">都捷卡</label>
					<div class="col-sm-10">
						<select class="form-control" id="cards" name="cards">
						</select>
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
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
