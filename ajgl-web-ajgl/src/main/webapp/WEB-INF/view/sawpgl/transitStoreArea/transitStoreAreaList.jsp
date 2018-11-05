<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>基本档案</li>
					<li class="active">暂存物品保管区维护</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>暂存物品保管区维护</h1>
						<div class="m-ui-caozuobox">
							<button id="saNew" class="btn btn-primary">新增</button>
							<button id="saUpdate" class="btn btn-success">修改</button>
							<button id="saDelete" class="btn btn-danger" style="display:none;">删除</button>
							<button id="saEnabled" class="btn btn-success">启用</button>
							<button id="saDisable" class="btn btn-danger">停用</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->

				<div id="c-center-right-content-block">
					<div class="m-ui-table">
						<table id="transitStoreAreaTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
							
						</table>
					</div>
				</div>
				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/transitStoreArea/transitStoreAreaList.js"></script>
</html>