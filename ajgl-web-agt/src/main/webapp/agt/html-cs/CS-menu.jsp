<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/common/library/comet/comet.jsp"%>
<%@include file="/WEB-INF/custom/agt/skin/top.jsp"%>
<!-- 换肤  -->
<link rel="icon" type="image/x-icon" href="../images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon"
	href="../images/favicon.ico">
<link rel="bookmark" type="image/x-icon" href="../images/favicon.ico">
<link rel="stylesheet" href="../custom/default/style/frame.css">
<link rel="stylesheet" href="../custom/default/style/cs.css">
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
<%--  var roomNum='<%=request.getParameter("RoomNum")%>'; --%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/library/msghint/js/msghint.js"></script>
<object id="dsspocxObject" classid="CLSID:1D1A0ACA-3A53-4589-88B8-94A0DDC47EA6" onreadystatechange="" style="display: none;"></object>
<body class="cs-bg">
	<div class="cs-menu-box">
		<iframe class="menu-box-1" src="" frameborder="0"></iframe>
		<iframe style="height: 90px" class="menu-box-2" src="" frameborder="0"></iframe>
		<div class="cs-menu"><button id="openWriting" class="btn btn-bl" onclick="onload()">打开笔录系统</button>
        </div>
		<div class="exit">
			<button class="btn" onclick="exitProgram()">退出办案通</button>
		</div>
	</div>
</body>
<script type="text/javascript" src="../scripts/CS-menu.js"></script>
</html>