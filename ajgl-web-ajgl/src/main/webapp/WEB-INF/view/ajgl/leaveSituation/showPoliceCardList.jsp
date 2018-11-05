<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="validformHandlingAreaReceipt" class="validform m-ui-layer-body">
<div class="m-ui-layer-box" style="width: 600px;">
	<div class="m-ui-layer-content">
		<p class="row-mar mar-top-sm">负责本次讯(询)问的办案民警</p>
		<table id="policeTable" class="table table-bordered table-hover m-ui-table-no-paginate" cellspacing="0" width="100%">
	    </table>
	</div>
</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/leaveSituation/showPoliceCardList.js"></script>
</html>