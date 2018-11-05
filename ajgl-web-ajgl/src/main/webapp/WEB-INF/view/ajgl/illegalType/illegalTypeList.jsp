<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>

<html>
<head>
<title>案件管理 – 违规类型</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-ajgl.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>办案区管理</li>
					<li>基本档案</li>
					<li>违规类型</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>违规类型</h1>
						<div class="m-ui-caozuobox">
							<button id="new" class="btn btn-primary">新增</button>
							<button id="update" class="btn btn-success">修改</button>
							<button id="delete" class="btn btn-danger">删除</button>
							<button id="enable" class="btn btn-success">启用</button>
							<button id="disable" class="btn btn-danger">停用</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->

				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="illegalTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%"></table>
					</div>
				</div>
				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/ajgl/illegalType/illegalTypeList.js"></script>
</html>