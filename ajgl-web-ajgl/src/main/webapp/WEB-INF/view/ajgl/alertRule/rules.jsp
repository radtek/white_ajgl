<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 预警规则设置</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>

</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-xtgl.jsp"%>
		<div id="c-center-right">

			<div id="c-center-right-content">

				<ol class="breadcrumb m-ui-breadcrumb">
					<li><a href="#">系统管理</a></li>
					<li><a href="#">预警规则设置</a></li>
					<li><a href="#">办案区预警</a></li>
				</ol>
				<!-- 预警规则公用模块 -->
				<%@include file="/WEB-INF/view/util/alertRuleCommentByCase.jsp"%>
				</div>
				</div>
		
		</div>
		<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript">
	var ssmk = $.common.Constant.BAQMK();	//获取所属模块
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/alertRule/rulesCases.js"></script>

</html>