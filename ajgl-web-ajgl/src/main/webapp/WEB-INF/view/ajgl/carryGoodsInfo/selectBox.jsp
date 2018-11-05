<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body class="m-ui-layer-body" style="width: 1350px; margin-left: 25px; margin-top: 25px;">
	<div class="m-ui-searchbox">
	<div class="m-ui-searchbox-con">
		<div id="boxDiv"></div>
	</div>
	</div>
	<script type="text/javascript"
		src="<%=context%>/scripts/ajgl/carryGoodsInfo/selectBox.js"></script>
</body>
</html>