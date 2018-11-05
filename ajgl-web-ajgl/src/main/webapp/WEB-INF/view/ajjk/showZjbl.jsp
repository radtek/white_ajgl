<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title></title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body class="m-ui-layer-body">
	<input type="hidden" id="blid"
		value=<%=request.getParameter("blid")%>>
	<img id="zjbl" src="" style="width: 800px; display: none;">
</body>
<script type="text/javascript">
$.ajax({
	url:context +'/showZjbl/searchZjbl.action',
	type:'post',
	data:{blid : $("#blid").val()},
	dataType:'json',
	success:function(successData){
		$("#zjbl").attr("src" , "data:image/png;base64," + successData.base64Str);
		$("#zjbl").show();
	}
});


</script>
</html>