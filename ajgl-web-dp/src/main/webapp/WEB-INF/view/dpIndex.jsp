<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>页面索引</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/dp/common/library/bootstrap/dist/css/bootstrap.custom.css">
<style>
body {
	line-height: 2em;
}

a {
	color: #0055cc;
	padding: 5px 10px;
	text-decoration: none;
	font-size: 14px;
}

a:hover {
	color: #ff7800;
	text-decoration: underline;
}

.link {
	margin-left: 20px;
	color: #999;
}

.container {
	width: auto;
}

li {
	margin-bottom: 15px;
}

body, p, hr, p, blockquote, dl, dt, dd, ul, ol, li, pre, form, fieldset,
	legend, button, input, textarea, th, td, img {
	font-family: 'Tahoma', 'Arial', 'Times New Roman', 'LiHei Pro Medium',
		'SimSun', 'Microsoft YaHei', SimHei;
}

h1, h2, h3, h4, h5, h6 {
	font-family: 'Microsoft YaHei';
}

h3 a {
	font-size: 22px;
}

.gray {
	color: #ccc;
}
</style>
</head>
<body>
	<h1 align="center">大屏</h1>
	<div class="container">
		<div class="row" style="width: 300px; margin: 0 auto;">
			<div style="padding: 20px;">
				<ul>
					<li><a id="crownCaseBtn" target="_blank">大屏展示-刑事案件</a></li>
					<li><a id="administrativeCaseBtn" target="_blank">大屏展示-行政案件</a></li>
					<li><a id="askRoomBtn" target="_blank">大屏展示-讯（询）问</a></li>
					<!-- <li><a id="personDetailsBtn" target="_blank">大屏展示-嫌疑人详情</a></li> -->
				</ul>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/dpIndex.js"></script>
</html>