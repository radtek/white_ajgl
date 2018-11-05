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
<p class="row row-mar">编号：<span id="code"></span></p>
<p class="row row-mar">储物箱位置：<span id="location"></span></p>
<p class="row row-mar">所属物证保管区：<span id="storageArea"></span></p>
<p class="row row-mar">备注：<span id="remark"></span></p>
</div>
<div class="text-center" style="padding:15px;">
<div id="tdcImg"></div>
<p class="color-gray mar-top">该储物箱的二维码</p>
</div>
</div>
<!--内容end-->
</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/lockerMaintain/lookLocker.js"></script>
</html>