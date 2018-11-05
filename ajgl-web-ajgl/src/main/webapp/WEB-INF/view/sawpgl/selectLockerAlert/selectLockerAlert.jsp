<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
<style type="text/css">
.noprint {
	visibility: hidden
}
</style>
</head>
<body class="m-ui-layer-body">

	<div class="m-ui-layer-box">

		<div class="m-ui-layer-content">
            <table id="lockerTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
									
			</table>
      
		</div>
	</div>

</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/selectLockerAlert/selectLockerAlert.js"></script>
</html>