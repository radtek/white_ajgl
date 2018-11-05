<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/base/javaPart.jsp"%>
<html>
<head>
<title>案件管理-涉案物品再入库</title>
<%@include file="/WEB-INF/base/basePart.jsp"%>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/base/skin/topPart.jsp"%>
	<div id="c-center"class="validform">
		<%@include file="/WEB-INF/base/skin/leftPart-sawp.jsp"%>
		<div id="c-center-right">
			<div id="c-center-right-content">
				<ol class="breadcrumb m-ui-breadcrumb">
					<li>涉案物品管理</li>
					<li>查看涉案物品再入库</li>
				</ol>
				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>物品再入库单详情</h1>
						<div class="m-ui-caozuobox">
							<button id="refresh" class="btn btn-default">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button id="bsfPrint" class="btn btn-success">打印</button>
							<button id="cancel" class="btn btn-default">返回</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div class="alert" style="width: 900px; padding-top: 20px; margin-top: 20px; position: relative; background-color: #f8f8f8; border-color: #ddd;">
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">再入库单编号：</label>
						</div>
						<div class="col-xs-2">
							<div id="code" class="col-xs-2 m-label-right"></div>
						</div>
						<div class="col-xs-2">
							<label class="control-label">再入库日期：</label>
						</div>
						<div class="col-xs-2">
							<div id="updatedTime" class="m-label-right"></div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">对应案件编号：</label>
						</div>
						<div class="col-xs-6">
							<div id="caseCode" class="m-label-right"></div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">出库单编号：</label>
						</div>
						<div id="outStorageFormCode" class="m-label-right"></div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">出库类型：</label>
						</div>
						<div class="col-xs-6">
							<div id="typeName" class="m-label-right"></div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">案件名称：</label>
						</div>
						<div class="col-xs-6">
							<div id="caseName" class="m-label-right"></div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">办案民警：</label>
						</div>
						<div class="col-xs-6">
							<div id="polices" class="m-label-right"></div>
						</div>
					</div>
					<div class="row row-mar">
						<div class="col-xs-2">
							<label class="control-label">备注：</label>
						</div>
						<div class="col-xs-6">
							<div id="remark" class="m-label-right" style="word-wrap: break-word; word-break: break-all"></div>
						</div>
					</div>

					<div id="bsfCodeImg" style="position: absolute; right: 10px; bottom: 10px; width: 170px;">
						
					</div>
				</div>
				<div class="m-ui-table">
					<table id="bsfTable" class="table table-bordered table-hover m-ui-table-whole" cellspacing="0" width="100%">
						
					</table>
				</div>
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
	var bsfId = "${param.bsfId}";
</script>
<script type="text/javascript"
	src="<%=context%>/scripts/sawpgl/backStorageForm/lookBackStorageForm.js"></script>
</html>