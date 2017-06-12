<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/views/share/taglib.jsp"%>
<%@include file="/WEB-INF/views/share/fontMeta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>${website_name} | 我的卡包</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-style" role="navigation">
	  <div class="ik-ind-container">
	  	<span class="icon-back"><span class="glyphicon glyphicon-menu-left"></span>&nbsp;</span>
		<span class="ind-title">我的卡包&nbsp;</span>
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
		
		//对于用户 ：验码消费 --->跳转到显示5分钟有效期的码 ---> key = userId + cardId,value = 效验码 ,当null会重新生成码否则缓存中取已存在的
		//对于商家: key = 效验码, value=userId + cardId ，取缓存中的效验码，null 则提示效验码过期，请出示有效验证码
		function goCheckCode(op){
			window.location.href = "${ctx}/webApp/user/greateCheckCode/"+op;
		}
		
		function init() {
			showLoading();
			//循环数据
			$.post("${ctx}/webApp/user/myCard",function(data) {
				if(data == "-100"){
					//将页面记录到cookie，用于登录后跳转
					$.cookie("target_herf",window.location.href + '', { path: '${ctx}' });
					//跳转登录页
					window.location.href="${ctx}/webApp/login";
				}else{
					if (data != "" && data != null) {
						var listContent = "";
                        var listContent2 = "";
						$(data).each(
								function(index) {
									var cardVo = data[index];
									/**         加载列表                           **/
                                    if(cardVo.isExpired == 1){
                                        listContent2 +='<div class="panel panel-default">';
                                        listContent2 +='<div class="panel-heading">';
                                        listContent2 +='<h3 class="panel-title" style="font-weight:bold;"><span class="glyphicon glyphicon-credit-card"></span>&nbsp;'+cardVo.productName+'</h3>';
                                        listContent2 +='</div>';
                                        listContent2 +='<table class="table">';
                                        listContent2 +='<tr>';
                                        listContent2 +='<td>可用次数:</td>';
                                        listContent2 +='<td class = "gongli">';
                                        listContent2 +=cardVo.consumeNumber+'&nbsp;';
                                        listContent2 +='</td>';
                                        listContent2 +='<td>已用次数:</td>';
                                        listContent2 +='<td class = "gongli">';
                                        listContent2 +=cardVo.consumeNumberAlready+'&nbsp;';
                                        listContent2 +='</td>';
                                        listContent2 +='</tr>';
                                        listContent2 +='<tr>';
                                        listContent2 +='<td>使用方式:</td>';
                                        listContent2 +='<td>'
                                        if(cardVo.productIsDoor == 0){
                                            listContent2 +='到店';
                                        }
                                        if(cardVo.productIsDoor == 1){
                                            listContent2 +='上门';
                                        }
                                        listContent2 +='</td>';
                                        listContent2 +='<td>是否过期:</td>';
                                        listContent2 +='<td >'
                                        listContent2 +='<span style="color:red;">已过期</span>';
                                        listContent2 +='</td>';
                                        listContent2 +='</tr>';
                                        listContent2 +='<tr>';
                                        listContent2 +='<td colspan="2">有效期至:</td>';
                                        listContent2 +='<td colspan="2">'+cardVo.expiredDate+'</td>';
                                        listContent2 +='</tr>';
                                        if(cardVo.isExpired == 0 && cardVo.consumeNumber > 0 && cardVo.status == 0){
                                            listContent2 +='<tr style="text-align:right;">';
                                            listContent2 +='<td colspan="4"><a href="javascript:;" onclick="goCheckCode('+cardVo.id+')">验码消费</a></td>';
                                            listContent2 +='</tr>';
                                        }else if(cardVo.isExpired == 0 && cardVo.consumeNumber > 0 && cardVo.status == 2){
                                            listContent2 +='<tr style="text-align:right;">';
                                            listContent2 +='<td colspan="4"><span style="text-decoration: line-through;color: #ccc;">此卡使用中不能验码消费</span></td>';
                                            listContent2 +='</tr>';
                                        }else if(cardVo.isExpired == 0 && cardVo.consumeNumber == 0 && cardVo.status == 0){
                                            listContent2 +='<tr style="text-align:right;">';
                                            listContent2 +='<td colspan="4"><span style="text-decoration: line-through;color: #ccc;">此卡无可用次数不能验码消费</span></td>';
                                            listContent2 +='</tr>';
                                        }
                                        listContent2 +='</table>';
                                        listContent2 +='</div>';
                                    }else{
                                        listContent +='<div class="panel panel-default">';
                                        listContent +='<div class="panel-heading">';
                                        listContent +='<h3 class="panel-title" style="font-weight:bold;"><span class="glyphicon glyphicon-credit-card"></span>&nbsp;'+cardVo.productName+'</h3>';
                                        listContent +='</div>';
                                        listContent +='<table class="table">';
                                        listContent +='<tr>';
                                        listContent +='<td>可用次数:</td>';
                                        listContent +='<td class = "gongli">';
                                        listContent +=cardVo.consumeNumber+'&nbsp;';
                                        listContent +='</td>';
                                        listContent +='<td>已用次数:</td>';
                                        listContent +='<td class = "gongli">';
                                        listContent +=cardVo.consumeNumberAlready+'&nbsp;';
                                        listContent +='</td>';
                                        listContent +='</tr>';
                                        listContent +='<tr>';
                                        listContent +='<td>使用方式:</td>';
                                        listContent +='<td>'
                                        if(cardVo.productIsDoor == 0){
                                            listContent +='到店';
                                        }
                                        if(cardVo.productIsDoor == 1){
                                            listContent +='上门';
                                        }
                                        listContent +='</td>';
                                        listContent +='<td>是否过期:</td>';
                                        listContent +='<td >'
                                        listContent +='未过期';
                                        listContent +='</td>';
                                        listContent +='</tr>';
                                        listContent +='<tr>';
                                        listContent +='<td colspan="2">有效期至:</td>';
                                        listContent +='<td colspan="2">'+cardVo.expiredDate+'</td>';
                                        listContent +='</tr>';
                                        if(cardVo.isExpired == 0 && cardVo.consumeNumber > 0 && cardVo.status == 0){
                                            listContent +='<tr style="text-align:right;">';
                                            listContent +='<td colspan="4"><a href="javascript:;" onclick="goCheckCode('+cardVo.id+')">验码消费</a></td>';
                                            listContent +='</tr>';
                                        }else if(cardVo.isExpired == 0 && cardVo.consumeNumber > 0 && cardVo.status == 2){
                                            listContent +='<tr style="text-align:right;">';
                                            listContent +='<td colspan="4"><span style="text-decoration: line-through;color: #ccc;">此卡使用中不能验码消费</span></td>';
                                            listContent +='</tr>';
                                        }else if(cardVo.isExpired == 0 && cardVo.consumeNumber == 0 && cardVo.status == 0){
                                            listContent +='<tr style="text-align:right;">';
                                            listContent +='<td colspan="4"><span style="text-decoration: line-through;color: #ccc;">此卡无可用次数不能验码消费</span></td>';
                                            listContent +='</tr>';
                                        }
                                        listContent +='</table>';
                                        listContent +='</div>';
                                    }
								});
						$("#listContent").append(listContent).append(listContent2);
					} else {
						$("#contentDownContent").show();
						$("#contentDownContent").html("您的卡包暂无都捷卡,请购卡");
					}
				}
				hideLoading();
			}, "json");
		}
	</script>
</body>
</html>
