<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>      
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/base/skin/utilPart.jsp"%>
<title>指挥调度平台</title>
<!-- <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" /> -->

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
        <img src="<%=request.getContextPath()%>/images/bg-404.png">
        <p class="text">抱歉，页面不小心错误啦，您的操作没能成功...</p>
    </div>
    
</body></html>