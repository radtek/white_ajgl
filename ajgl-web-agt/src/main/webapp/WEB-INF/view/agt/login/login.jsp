<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include file="/WEB-INF/custom/agt/skin/top.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>案管客户端</title>
<script>
<%-- var roomId='<%=request.getParameter("roomId")%>'; --%>
var context='<%=request.getContextPath()%>';
</script>
<!-- 换肤  -->
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="bookmark" type="image/x-icon" href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/custom/default/style/frame.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/custom/default/style/cs.css">


<script type="text/javascript" src="<%=request.getContextPath()%>/agt/custom/default/js/custominput.js"></script><!-- checkbox radio插件 -->
<script>
$(document).ready(function() {		
	$("#cs-select").select2({
  minimumResultsForSearch: Infinity
}) ;
});
</script>
<script type="text/javascript">

$(function(){
    $('input').customInput();
    $('.toggle').each(function(){
    $('div:first',this).addClass('first');
    $('div:last',this).addClass('last');	
	}); 
});

</script>
</head>

<body class="cs-bg">
<object id="dsscocx" classid="CLSID:1D1A0ACA-3A53-4589-88B8-94A0DDC47EA6" onreadystatechange="" style="display: none;"></object>
<div class="cs-login-box">
  <div class="box1" id="divMouseMove">
  <a id="btn-close-window" href="####" style="transform: rotate(0deg);"></a>
  <br/>
    <div class="row">
      <div class="col-xs-4"><span class="cslogo"></span>办案通</div>
      <div class="col-xs-8 text-right" id="formCode"></div>
    </div>
    <div class="row row-mar" style="margin-top:20px">
      <div class="col-xs-3">
        <label class="control-label" style="margin-top:5px;">讯问室：</label>
      </div>
      <div class="col-xs-4">
        <label class="control-label" style="margin-top:5px;" id="roomName"></label>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-3">
        <label class="control-label">主办民警：</label>
      </div>
      <div class="col-xs-8 m-label-right">
	       <form action="#" method="post" id="radioDiv">
	       </form>

      </div>
    </div>
  </div>
  <div class="box2">
  <div class="row imgbg">
  <div class="col-xs-6 imgbg-left">
              <div class="content" > <p id="hostPoliceName"></p>  
              <p class="color-gray" id="hostPoliceCode"></p>
              </div>
</div>
  <div class="col-xs-6 imgbg-right">
               <div class="content" > <p id="suspectName"></p> 
              <p class="color-gray" id="suspectID"></p>
              </div>
  </div>
  </div>
  <div class="row text-center"><button class="btn btn-primary btn-login" id="loginBtn">确认登录</button>
  </div>
  </div>
</div>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/agt/login/login.js"></script>   
	<!--关闭按钮旋转-->
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/custom/default/js/jQueryRotate.2.2.js"></script>
	
</body>
</html>