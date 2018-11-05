<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>涉案物品-储物箱</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
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
					<li class="active">暂存物品储物箱维护</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>暂存物品储物箱维护</h1>
						<div class="m-ui-caozuobox">
							<button class="btn btn-primary" id="newLocker">新增</button>
							<button class="btn btn-success" id="modify">修改</button>
							<button class="btn btn-danger" id="delete" style="display:none;">删除</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div id="c-center-right-content-bar">
						<div class="m-ui-title3">
							<h2>暂存物品保管区</h2>
						</div>
						<div class="list-group" id="storageArea" style="max-height: 500px; overflow-y: auto;  overflow-x: hidden; margin-right: 0;">
						</div>
					</div>
					<div id="c-center-right-content-con">
						<div class="right-inner" style="padding-top: 10px">
							<div class="m-ui-table">
								<table id="transitStoreLockerTable"
									class="table table-bordered table-hover m-ui-table-whole"
									cellspacing="0" width="100%">
									
								</table>
							</div>
						</div>
					</div>
				</div>
				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/transitStoreLocker/transitStoreLockerList.js"></script>
</html>