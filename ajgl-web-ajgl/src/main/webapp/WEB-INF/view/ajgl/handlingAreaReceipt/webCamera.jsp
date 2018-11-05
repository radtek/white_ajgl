<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body class="m-ui-layer-body">
<div class="camera-box">
<div class="row">
<div class="camera-area">
<div id="" style="width:358px; height:441px; overflow:hidden;"><div id="webcam2"></div></div>
<span class="area-corner area-corner-1"></span>
<span class="area-corner area-corner-2"></span>
<span class="area-corner area-corner-3"></span>
<span class="area-corner area-corner-4"></span>
</div>
<div class="photo-area">
<div class="photo-preview"><canvas id="canvas" width="358" height="441"></canvas></div>
</div>
</div>
<p style="padding-top:5px;text-align:center;"> 
<button class="btn btn-primary btn-sm qiezi"><span class="icon-camera mar-right-sm"></span>拍照</button>&nbsp;</p>
<!--内容end-->
</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/handlingAreaReceipt/jqWebcam.js"></script>
<script type="text/javascript"
	src="<%=context%>/common/library/jqueryWebcam/jquery.webcam.js"></script>
</html>