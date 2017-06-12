<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
<!--
#divJeepDiv {
	left: 0px;
	top: 0px;
	opacity: 0.6;
	background: #000;
	filter: alpha(opacity = 60);
	overflow: hidden;
	position: fixed;
	height: 100%;
	width: 100%;
	z-index: 3500;
	display: none;
	text-align: center;
}
-->
</style>
<script type="text/javascript">
	$().ready(function() {
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	});

	function showLoading(content) {
		if (content == "" || content == null) {
			content = "载入中...";
		}
		$("#loadingContent").html(content);
		$("#divJeepDiv").show();
	}

	function hideLoading() {
		$("#divJeepDiv").hide();
	}
</script>
<div id="divJeepDiv">
	<div
		style="position: fixed; top: 50%; left: 50%; margin-top: -40px; /* height数值的一半 */ margin-left: -50px; /* width数值的一半 */ -webkit-box-shadow: 0 0 3px rgba(0, 0, 0, .2); -moz-box-shadow: 0 0 3px rgba(0, 0, 0, .2); box-shadow: 0 0 3px rgba(0, 0, 0, .2); border: 1px solid #dedede; -moz-border-radius: 15px; /* Gecko browsers */ -webkit-border-radius: 15px; /* Webkit browsers */ border-radius: 15px; padding-top: 15px; background: #fff; font-size: 20px; font-weight: bold; width: 100px; height: 80px;">
		<img alt="" src="${ctx}/static/images/loading.gif"><br />
		<span id="loadingContent"></span>
	</div>
</div>

