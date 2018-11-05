<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<%@include
	file="/common/library/iweboffice2000/iweboffice2000-import.jsp"%>
<script>
	$(document).ready(function() {
	});
</script>
<style>
.webOffice-statusBar{
display:none;
}
</style>
</head>
<input type="hidden" id="attId"
		value=<%=request.getParameter("attId")%>>
<body class="m-ui-layer-body">
	<div class="m-ui-layer-searchbox">
		<div id="iwebOffice-container">
			<div class="webOffice-objDiv">
				<object class="webOffice-obj" width="100%" height="1200px"
					classid="clsid:<%=com.taiji.pubsec.weboffice.iweboffice2000.util.Constant.getClsid()%>"
					codebase="<%=com.taiji.pubsec.weboffice.iweboffice2000.util.Constant.getCodebase()%>"> </object>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajjk/showWord.js"></script>
</html>