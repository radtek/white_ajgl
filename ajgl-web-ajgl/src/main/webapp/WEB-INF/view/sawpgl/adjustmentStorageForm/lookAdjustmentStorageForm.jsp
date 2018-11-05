<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理 – 涉案物品位置调整单</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body id="asfValidform" class="validform">
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>物品存储位置调整单详情</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>物品存储位置调整单详情</h1>
						<div class="m-ui-caozuobox">
							<button id="refresh" class="btn btn-default">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button id="cancel" class="btn btn-default">返回</button>
							<!--  
							<button class="btn btn-primary">打印</button>
							-->
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="alert" style="width: 900px; padding-top: 20px; margin-top: 20px; height: 150px; position: relative; background-color: #f8f8f8; border-color: #ddd;">
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">调整单编号：</label>
						</div>
						<div id="code" class="col-xs-2 m-label-right"></div>
						<div class="col-xs-2">
							<label class="control-label">调整时间：</label>
						</div>
						<div id="adjustTime" class="col-xs-2 m-label-right"></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">调整原因：</label>
						</div>
						<div id="reason" class="col-xs-6 m-label-right" style="word-wrap: break-word; word-break: break-all"></div>
					</div>

					<div id="asfCodeImg" style="position: absolute; right: 10px; bottom: 10px; width: 170px;">
						
					</div>
				</div>
				<!--  
				<div class="m-ui-title3">
					<h2>
						涉案物品
						<button class="btn btn-primary btn-sm">查看物品图片</button>
					</h2>
				</div>
				-->
				<div class="m-ui-table">
					<table id="asfTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
						
					</table>
				</div>
				<p class="alert alert-warning">
					合计：<span id="existingNumberSum" class="font20 color-red1"></span>
				</p>
				<div class="color-gray text-right">
					最新修改人：<span id="modifyPeopleName"></span> <span style="margin-left: 50px;">最新修改时间：<span id="modifyTime"></span></span>
				</div>
				<!--结束-->
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/base/skin/footPart.jsp"%>
</body>
<script type="text/javascript">
	var asfId = "${param.asfId}";
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/adjustmentStorageForm/lookAdjustmentStorageForm.js"></script>
</html>
