<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>案管客户端</title>
<script>

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/base/js/basePart.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/base/js/layerAlert.js"></script>
<!-- 换肤  -->
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="bookmark" type="image/x-icon" href="<%=request.getContextPath()%>/agt/images/favicon.ico">
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/custom/default/style/frame.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/custom/default/style/cs.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/custom/default/js/util.js"></script>
</head>

<body class="cs-bg">
<div class="cs-content" style="width:100%">
<div class="cs-content-top" style="width:100%" ><h2>研判结果查看</h2></div>
<div class="cs-content-main">
<div class="m-ui-table">
                  <table id="resConTable" class="table table-hover m-ui-table-whole" cellspacing="0" width="100%">
                      <!-- <thead>
                          <tr>
                              <th width="20%">研判类型</th>
                              <th width="60%">推送原因</th>
                              <th width="20%">推送时间</th>
                        </tr>
                      </thead>
              
                      <tbody id="resCon">             
                         
                                                      
                      </tbody> -->
                  </table>
              </div>
</div>
 </div>
</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/agt/agtTaskbar/result.js"></script> 
</html>