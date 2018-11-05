<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/common/library/comet/comet.jsp"%>
<%@include file="/WEB-INF/custom/agt/skin/top.jsp"%>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>案管客户端</title>

<script>
var ContextPath = '<%=request.getContextPath()%>'; 
var context = '<%=request.getContextPath()%>'; 
</script>
<script type="text/javascript" src="../common/base/js/basePart.js"></script>
<script type="text/javascript" src="../common/base/js/layerAlert.js"></script>
<!-- 换肤  -->
<link rel="icon" type="image/x-icon" href="../images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon" href="../images/favicon.ico">
<link rel="bookmark" type="image/x-icon" href="../images/favicon.ico">
<link rel="stylesheet" href="../custom/default/style/frame.css">
<link rel="stylesheet" href="../custom/default/style/cs.css">
<script type="text/javascript" src="../custom/default/js/util.js"></script>
</head>
<body class="cs-bg cs-menubg">
<div class="cs-menu cs-menubg-b">
<div class="row"><button class="btn btn-yp" onclick="clk3()"><span class="text">研判结果查看</span><span class="cs-number" id="researchCount"></span></button></div>
</div>
</body>
<script type="text/javascript" src="../scripts/menu-2.js"></script>
</html>