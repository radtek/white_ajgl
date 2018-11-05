<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>案管客户端</title>
<!-- <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" /> -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/bootstrap/dist/css/bootstrap.custom.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/bootstrap/jquery-ui-bootstrap/css/custom-theme/jquery-ui-1.10.0.custom.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/dataTables/css/jquery.dataTables.custom.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/zTree/css/zTreeStyle/zTreeStyle.css">
<!-- icheck -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/icheck/skins/all.css?v=1.0.2" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/chosen/css/chosen.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/select2/css/select2.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/library/font-awesome/css/font-awesome.css" />
<!---------新增font-awesome图标库------------>
<!--[if lte IE 6]>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/agt/common/library/bootstrap/bsie/css/bootstrap-ie6.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/agt/common/library/bootstrap/bsie/css/ie.css">
<![endif]-->

<link rel="stylesheet" href="<%=request.getContextPath()%>/agt/common/base/style/base.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/jquery/jquery-ui-fix-focus.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/layer/layer.fixbug.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/laydate/laydate.dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/layPage/laypage.dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/laytpl/laytpl.dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/zTree/js/jquery.ztree.excheck-3.5.js"></script>

<!-- icheck -->
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/icheck/js/icheck.js?v=1.0.2"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/icheck/js/custom.bugfix.js?v=1.0.2"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/select2/js/select2.full.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/chosen/js/chosen.jquery.js"></script>

<!-- bootstrap -->
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/bootstrap/js/bootstrap.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript" src="<%=request.getContextPath()%>/agt/common/library/bootstrap/bsie/js/bootstrap-ie.js"></script>
<![endif]-->

<script>

var clickOrder = 'null'; 
var clickListOrder = 'null'; 

if(clickOrder=="null"){
	clickOrder = "0" ;
} ;
if(clickListOrder=="null"){
	clickListOrder = "0" ;
} ;

var isReSize = true ;
var firstLoad = true ;
var formCode='<%=request.getParameter("formCode")%>';
var context='<%=request.getContextPath()%>';
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
<div class="cs-content">
<div class="cs-content-top" id="divMouseMove">
<h2>消息提醒列表</h2>
<a id="btn-close-window" href="####" style="transform: rotate(0deg);"></a>
</div>
<div class="cs-content-main">
<div class="m-ui-title3"><h2>预警消息提醒</h2></div>
<div class="alert alert-warning">
                  <table id="" class="table table-hover" cellspacing="0" width="100%" style="margin-bottom:0; ">
                      <thead>
                          <tr>
                              <th width="30%">预警时间</th>
                              <th>预警内容</th>
                        </tr>
                      </thead>
              
                      <tbody id="messageAlert">     
                                                              
                    </tbody>
                  </table>
</div>
</div>
 </div>
 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/agt/agtTaskbar/messageAlert.js"></script> 
</html>