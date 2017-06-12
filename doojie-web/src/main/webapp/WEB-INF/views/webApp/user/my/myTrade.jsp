<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 我的交易</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">我的交易&nbsp;</span>
	     <span  class="ind-person">&nbsp;</span> 
	  </div>
	</nav>
	<div id="list" >
		<section id="listContent" class="list">
	      
    	</section>
	</div>
	<div id="contentDownContent"  style="display:none;font-weight:bold;font-size:14px;color:red;text-align:center;line-height: 35px;"></div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<script type="text/javascript">
		$().ready(function(){
			//菜单加颜色提示
			$("#my").attr("style","color:#2885EC");
			
			$(".icon-back").on("click",function(){
				history.back();
			});
			
			init();
		});
		
		function tradeDetail(tradeId){
			window.location.href = "${ctx}/webApp/toMyTradeDetail/"+tradeId;
		}
		
		function init() {
			showLoading();
			//循环数据
			$.post("${ctx}/webApp/user/myTrade",function(data) {
				if(data == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/login";
				}else{
					if (data != "" && data != null) {
						var listContent = "";
						$(data).each(
								function(index) {
									var trade = data[index];
									/**         加载列表                           **/
									listContent +='<div class="panel panel-default">';
									listContent +='<table class="table">';
									listContent +='<tr>';
									listContent +='<td colspan="2">交易编号:</td>';
									listContent +='<td colspan="2"><a href="javascript:;" onclick="tradeDetail('+trade.id+')">'+trade.tradeCode+'</a></td>';
									listContent +='</tr>';
//									listContent +='<tr>';
//									listContent +='<td>支付方式:</td>';
//									listContent +='<td>';
//									if(trade.payMode == 1){
//										listContent +='微信&nbsp;';
//									}else if(trade.payMode == 2){
//										listContent +='支付宝&nbsp;';
//									}
//									listContent +='</td>';
//									listContent +='<td>支付金额:</td>';
//									listContent +='<td>'+trade.money+'元</td>';
//									listContent +='</tr>';
									listContent +='<tr>';
									listContent +='<td colspan="2">支付时间:</td>';
									listContent +='<td colspan="2">'+trade.payDate+'</td>';
									listContent +='</tr>';
									listContent +='<tr>';
									listContent +='<td colspan="4" style="text-align: right;"><a href="javascript:;" onclick="tradeDetail('+trade.id+')">&nbsp;查看详情</a></td>';
									listContent +='</tr>';
									listContent +='</table>';
									listContent +='</div>';
									
								});
						$("#listContent").append(listContent);
					} else {
						$("#contentDownContent").show();
						$("#contentDownContent").html("您没有交易记录");
					}
				}
				hideLoading();
			}, "json");
		}
	</script>
</body>
</html>
