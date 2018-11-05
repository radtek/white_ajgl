<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
<style type="text/css">
	.noprint{visibility:hidden}   
</style>
</head>
<body class="m-ui-layer-body">
<div class="m-ui-layer-box" style="width:400px;">
<div class="m-ui-layer-content">
 <div class="alert alert-info"> 
<p class="row row-mar">物品编号：<span id="code"></span></p>
<p class="row row-mar">物品名称：<span id="name"></span></p>
<p class="row row-mar">物品特征：<span id="feature"></span></p>
<p class="row row-mar">物品特性：<span id="typeName"></span></p>
</div>
<div class="text-center" style="padding:15px;">
<div id="tdcImg"></div>
<p class="color-gray mar-top">该物品的二维码</p>
</div>
</div>
<!--内容end-->
</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/util/articleQrcodeAlert.js"></script>
</html>