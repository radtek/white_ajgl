<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>涉案物品管理 – 预警规则设置</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
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
					<li><a href="#">涉案物品预警</a></li>
				</ol>
				<!-- 预警规则公用模块 -->
				<%@include file="/WEB-INF/view/util/alertRuleComment.jsp"%>
			</div>
		</div>
		</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript">
	var ssmk = $.common.Constant.SAWPMK();	//获取所属模块
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/alertRule/rules.js"></script>
</html>