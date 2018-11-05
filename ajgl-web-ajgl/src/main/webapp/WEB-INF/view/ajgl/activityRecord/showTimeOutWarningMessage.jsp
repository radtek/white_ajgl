<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformAskRoom" class="validform m-ui-layer-body">
    <h2> >>讯询过程发生的超时预警消息列表</h2>
    <br>
	<div align="center">
	  <table id="mesTable" style="width: 80%">
	  </table>
	
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/activityRecord/showTimeOutWarningMessage.js"></script>
</html>