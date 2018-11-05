<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>      
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/base/skin/utilPart.jsp"%>

<script>
var queryString = '<%=request.getQueryString()%>' ;
var sign = $.util.parseUrlQueryStr(queryString) ;
$(document).ready(function() {	
	var url = context + "/jsp/auth/denied.jsp" ;
	if(sign.sign=="denied"){
		//window.top.location.href = url ;
	}
});
</script>
</head>

<style type="text/css">
body{background: #f4f9ff; font-family: Microsoft Yahei;}
.box404{width: 670px; margin:150px auto 0 auto; text-align: center;}
.box404 button{ background:none; border:1px solid #40a8e8;color:#249ce6; padding: 8px 25px; font-size: 16px; border-radius: 2px;font-family: Microsoft Yahei;}
.box404 button:hover{ background:#40a8e8; border-color:#249ce6;color:#fff;}   
.box404 p{margin-bottom: 30px; font-size: 16px;} 
</style>
</head>
<body>
    <div class="box404">
        <img src="<%=request.getContextPath()%>/images/bg-sorry.png">
        <p class="text">服务内部错误，请联系分局管理员。</p>
    </div>
</body>
</html>