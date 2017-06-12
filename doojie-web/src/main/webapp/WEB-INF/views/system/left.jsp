<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/views/share/taglib.jsp"%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${ctx}/static/css/style/tree.css"
	type="text/css">
</head>
<script type="text/javascript" src="${ctx}/static/js/tree.js"></script>
<body style="background-color: #F8F8F8;" leftmargin="0" topmargin="0"
	onmouseover="self.status='：您好！';return true">
	<table>
		<tr>
			<td>
				<div class="indentLink">
					<script type="text/javascript">
							${mis3newTree}
						</script>
				</div>
			</td>
		</tr>
	</table>
</body>
</HTML>
